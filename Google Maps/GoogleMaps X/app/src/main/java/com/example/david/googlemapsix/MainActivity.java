package com.gob.gep.sfa.dds.autopue;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gob.gep.sfa.dds.autopue.Datos.BdHelperAutoPue;
import com.gob.gep.sfa.dds.autopue.Entidades.Auto;
import com.gob.gep.sfa.dds.autopue.Entidades.AutorizacionPago;
import com.gob.gep.sfa.dds.autopue.Entidades.EntidadReferencia;
import com.gob.gep.sfa.dds.autopue.Servicios.wsAutorizacionBancaria;
import com.gob.gep.sfa.dds.autopue.Utils.AnalyticsApplication;
import com.gob.gep.sfa.dds.autopue.Utils.utilidades;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.commons.codec.DecoderException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class DetallePagoLinea extends AppCompatActivity {

    private TextView txvTituloFechaVigencia, compartir, txvDescFechaPgl, txvDescPgl, txvDescOperacionPgl, txvDescAutorizacionPgl, txvDescNombrePgl, txvDescNoTarjetaPgl, txvDescMontoPgl, txvDescConceptoPgl, txvReferenciaLinea, txvFechaVigencia;
    private String nombreContribuyente, nuTarjeta, conceptoServicio, monto, status, fechaFinal, CCtype, folioPagos, folioAutorizacion, codigoError, mensajeError, mensajeRechazado, noReferencia, cadenaResultado;
    private utilidades utils;
    private BdHelperAutoPue hp;
    private wsAutorizacionBancaria confirmarPago;
    private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_detallepagolinea);
        /*---------------------------------------------------------------------------*/
        //Analitycs
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("DetallePagoLinea");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        /*---------------------------------------------------------------------------*/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setSupportActionBar(utilidades.generarToolbar(this, "Mi pago en Línea"));
        compartir = (TextView) findViewById(R.id.btnCompartirBarra);
        hp = new BdHelperAutoPue(getApplicationContext());
        agregarElementos();
        utils = new utilidades();
        conceptoServicio = getIntent().getExtras().getString("concepto");

        pintarDetalle(hp.obtenerConfiguraciones());


    }

    private void agregarElementos() {
        txvTituloFechaVigencia = (TextView) findViewById(R.id.txvTituloVencimientoPgl);
        txvReferenciaLinea = (TextView) findViewById(R.id.txvDecNoReferenciaLinea);
        txvDescFechaPgl = (TextView) findViewById(R.id.txvDescFechaPgl);
        txvDescPgl = (TextView) findViewById(R.id.txvDescPgl);
        txvDescOperacionPgl = (TextView) findViewById(R.id.txvDescOperacionPgl);
        txvDescAutorizacionPgl = (TextView) findViewById(R.id.txvDescAutorizacionPgl);
        txvDescNombrePgl = (TextView) findViewById(R.id.txvDescNombrePgl);
        txvDescNoTarjetaPgl = (TextView) findViewById(R.id.txvDescNoTarjetaPgl);
        txvDescMontoPgl = (TextView) findViewById(R.id.txvDescMontoPgl);
        txvDescConceptoPgl = (TextView) findViewById(R.id.txvDescConceptoPgl);
        txvFechaVigencia = (TextView) findViewById(R.id.txvDescVencimientoPgl);

    }

    private void pintarDetalle(String respuesta) {
        try {
            //respuesta = utils.decrypt(respuesta, "A2832DE3C0B2289253D4B383404E8C1C");

            String r = utils.decrypt(respuesta, Auto.ModeloAuto.getKey());
            if (utils.obtenerValorXml(r, "<response>", "</response>", 10).equalsIgnoreCase("error")) {
                /*codigoError = utils.obtenerValorXml(respuesta, "<cd_error>", "</cd_error>", 10);
                mensajeError = utils.obtenerValorXml(respuesta, "<nb_error>", "</nb_error>", 10);*/
                String mensaje = "Error\n" +
                        "Ocurrió un error al realizar el pago\n" +
                        "\"No se realizó ningún cargo a su tarjeta\"\n" +
                        "Favor de intentarlo más tarde.\n";
                //utilidades.mostrarAlerta2(this, "AutoPue", mensaje + " " + codigoError + " - " + mensajeError);
                utilidades.mostrarAlerta3(this, new Intent(this, PrincipalAutos.class), "AutoPue", mensaje + " " + utils.obtenerValorXml(r, "<cd_error>", "</cd_error>", 10) + " - " + utils.obtenerValorXml(r, "<nb_error>", "</nb_error>", 10));
                //.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    /*finish();
                    startActivity(new Intent(DetallePagoLinea.this,PrincipalAutos.class));*/

            } else if (utils.obtenerValorXml(r, "<response>", "</response>", 10).trim().equalsIgnoreCase("denied")) {
                mensajeRechazado = "Cobro declinado\n" +
                        "\"No se realizó ningún cargo a su tarjeta\"" +
                        "La operación fue Declinada por su Banco Emisor \n" +
                        "  Favor de intentar con otra tarjeta.\n";
                utilidades.mostrarAlerta3(this, new Intent(this, PrincipalAutos.class), "AutoPue", mensajeRechazado);

                // startActivity(new Intent(this,PrincipalAutos.class));
                //startActivity(new Intent(DetallePagoLinea.this,PrincipalAutos.class));

            } else if (utils.obtenerValorXml(r, "<response>", "</response>", 10).equalsIgnoreCase("unauthenticated")) {
                String _3dsecure = utils.obtenerValorXml(r, "<r3ds_responseDescription>", "</r3ds_responseDescription>", 26);
                String mensaje = "";
                utilidades.mostrarAlerta(this, "AutoPue", _3dsecure);

            } else if (utils.obtenerValorXml(r, "<response>", "</response>", 10).trim().equalsIgnoreCase("approved")) {

                txvReferenciaLinea.setText(utils.obtenerValorXml(r, "<reference>", "</reference>", 11));
                txvDescFechaPgl.setText(utils.obtenerValorXml(r, "<date>", "</date>", 6) + " " + utils.obtenerValorXml(r, "<time>", "</time>", 6));
                txvDescPgl.setText(utils.obtenerValorXml(r, "<cc_type>", "</cc_type>", 9));
                txvDescOperacionPgl.setText(utils.obtenerValorXml(r, "<foliocpagos>", "</foliocpagos>", 13));
                txvDescAutorizacionPgl.setText(utils.obtenerValorXml(r, "<auth>", "</auth>", 6));
                txvDescNombrePgl.setText(utils.obtenerValorXml(r, "<cc_name>", "</cc_name>", 9));
                String numero = utils.obtenerValorXml(r, "<cc_number>", "</cc_number>", 11);
                txvDescNoTarjetaPgl.setText("**** **** **** " + numero.substring((numero.length() - 4)));
                //}
                txvFechaVigencia.setText(utils.obtenerValorXml(r, "<cc_expmonth>", "</cc_expmonth>", 13) + " / " + utils.obtenerValorXml(r, "<cc_expyear>", "</cc_expyear>", 12));
                txvDescMontoPgl.setText(String.valueOf(utils.getFormatoMoneda(Float.valueOf(String.valueOf(utils.obtenerValorXml(r, "<amount>", "</amount>", 8)))) + " MXN"));
                txvDescConceptoPgl.setText(conceptoServicio);
                //Autorizo el pago con ingress en el flujo normal
                AutorizacionPago pago = new AutorizacionPago();
                pago.setIdServicio(8000);
                pago.setNombreServicio("");
                pago.setIdtipoPago(1);
                pago.setClaveBanco(69);//66
                pago.setReferencia(utils.obtenerValorXml(r, "<reference>", "</reference>", 11));
                pago.setReferenciaExt("");
                pago.setImporte(Long.valueOf(utilidades.formatoMonedaLinea(txvDescMontoPgl.getText().toString().replace("MXN", "").trim())));
                pago.setIdEstatus(0);
                pago.setEstatus("ACTIVE");
                pago.setFecha(Long.valueOf(utilidades.formatoFecha(utils.obtenerValorXml(r, "<date>", "</date>", 6))));
                pago.setHora(Long.valueOf(utilidades.formatoHora(utils.obtenerValorXml(r, "<time>", "</time>", 6))));
                pago.setAutorizacion(Integer.valueOf(utils.obtenerValorXml(r, "<auth>", "</auth>", 6)));
                pago.setMoneda("MXP");
                pago.setExtras("");
                autorizacionPago(pago);
                EntidadReferencia referenciaPagada=hp.obtenerReferenciaVigencia(utils.obtenerValorXml(r, "<reference>", "</reference>", 11));
                if(referenciaPagada!=null){
                    referenciaPagada.setEstatusReferencia(3);
                    referenciaPagada.setIdTipoFormaPago(3);
                    referenciaPagada.setFechaPago(utilidades.getDate());
                    referenciaPagada.setStatusFormaPagoLinea(0);
                    registrarPago(referenciaPagada);
                }
                //Flujo alterno
            } else if (utils.obtenerValorXml(r, "<nb_response>", "</nb_response>", 13).equalsIgnoreCase("approved")) {
                txvReferenciaLinea.setText(utils.obtenerValorXml(r, "<nb_referencia>", "</nb_referencia>", 15).replaceAll(".","").trim());
                txvDescFechaPgl.setText(utils.obtenerValorXml(r, "<fh_registro>", "</fh_registro>", 13));
                txvDescPgl.setText(utils.obtenerValorXml(r, "<cc_tp>", "</cc_tp>", 7));
                txvDescOperacionPgl.setText(utils.obtenerValorXml(r, "<nu_operaion>", "</nu_operaion>", 13));
                txvDescAutorizacionPgl.setText(utils.obtenerValorXml(r, "<nu_auth>", "</nu_auth>", 9));
                txvDescNombrePgl.setText(utils.obtenerValorXml(r, "<cc_nombre>", "</cc_nombre>", 11));
                String numero = utils.obtenerValorXml(r, "<cc_number>", "</cc_number>", 11);
                txvDescNoTarjetaPgl.setText("**** **** **** " + utils.obtenerValorXml(r, "<cc_num>", "</cc_num>", 9));
                //}
                txvFechaVigencia.setVisibility(View.GONE);// setText(utils.obtenerValorXml(r,"<cc_expmonth>","</cc_expmonth>",13)+" / "+ utils.obtenerValorXml(r,"<cc_expyear>","</cc_expyear>",12));
                txvTituloFechaVigencia.setVisibility(View.GONE);
                txvDescMontoPgl.setText(String.valueOf(utils.getFormatoMoneda(Float.valueOf(String.valueOf(utils.obtenerValorXml(r, "<nu_importe>", "</nu_importe>", 12)))) + " MXN"));


                txvDescConceptoPgl.setText(conceptoServicio);
                EntidadReferencia referenciaPagada=hp.obtenerReferenciaVigencia(utils.obtenerValorXml(r, "<reference>", "</reference>", 11));
                if(referenciaPagada!=null){
                    referenciaPagada.setEstatusReferencia(3);
                    referenciaPagada.setIdTipoFormaPago(3);
                    referenciaPagada.setFechaPago(utilidades.getDate());
                    referenciaPagada.setStatusFormaPagoLinea(0);
                    registrarPago(referenciaPagada);

                }

                //Autorizo el pago con ingress en el flujo alterno
           /*     AutorizacionPago pago = new AutorizacionPago();
                pago.setIdServicio(8000);
                pago.setNombreServicio("");
                pago.setIdtipoPago(1);
                pago.setClaveBanco(66);
                pago.setReferencia(utils.obtenerValorXml(r, "<nb_referencia>", "</nb_referencia>", 15));
                pago.setReferenciaExt("");
                pago.setImporte(Long.valueOf(utilidades.formatoMonedaLinea(utilidades.formatoMonedaLinea(txvDescMontoPgl.getText().toString().replace("MXN", "").trim()))));
                pago.setIdEstatus(0);
                pago.setEstatus("ACTIVE");
                pago.setFecha(Long.valueOf(utilidades.formatoFechaAlterno(utils.obtenerValorXml(r, "<fh_registro>", "</fh_registro>", 13))));
                pago.setHora(Long.valueOf(utilidades.formatoHoraAlterno(utils.obtenerValorXml(r, "<fh_registro>", "</fh_registro>", 13))));
                pago.setAutorizacion(Integer.valueOf(utils.obtenerValorXml(r, "<nu_auth>", "</nu_auth>", 9)));
                pago.setMoneda("MXP");
                pago.setExtras("");
                autorizacionPago(pago);*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void regresar(View view) {
        finish();
    }*/
    private String obtenerCadena(String cadena) {
        try {
            cadenaResultado = utils.decrypt(cadena, Auto.ModeloAuto.getKey());
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (DecoderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return cadenaResultado;
    }

    public void regresar(View view) {
        startActivity(new Intent(DetallePagoLinea.this, PrincipalAutos.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetallePagoLinea.this, PrincipalAutos.class));
        finish();
    }

    private void autorizacionPago(AutorizacionPago pagoConfirmado) {
        if (pagoConfirmado != null) {
            confirmarPago = new wsAutorizacionBancaria();
            String mensaje = confirmarPago.wsobtenerEstatusReferencia(pagoConfirmado).getMensajeProceso();
            Log.e("Pago linea", mensaje);
        } else {
            Log.e("Error Detalle Pago Linea", "Error al autorizar el pago");
        }
    }
    private boolean registrarPago(EntidadReferencia entidadPago){
        boolean exitoso=false;
        if(entidadPago!=null){
            exitoso=  hp.actualizarRefrenciaPago(entidadPago);
        }
        return exitoso;
    }
}
