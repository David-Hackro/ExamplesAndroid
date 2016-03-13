package com.example.hackro.rijndael;

import android.util.Log;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by hackro on 20/11/15.
 */
public class Metodos {



    public String testEncryptRijndael(String value,String key) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        BlockCipher engine = new RijndaelEngine(256);
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine), new ZeroBytePadding());

        byte[] keyBytes = key.getBytes();
        cipher.init(true, new KeyParameter(keyBytes));

        byte[] input = value.getBytes();
        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];

        int cipherLength = cipher.processBytes(input, 0, input.length, cipherText, 0);
        cipher.doFinal(cipherText, cipherLength);

        String result = new String(Base64.encode(cipherText));
        //Log.e("testEncryptRijndael : " , result);
        return  result;
    }


    public String testDecryptRijndael(String value,String key) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        BlockCipher engine = new RijndaelEngine(256);
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine), new ZeroBytePadding());

        byte[] keyBytes = key.getBytes();
        cipher.init(false, new KeyParameter(keyBytes));

        byte[] output = Base64.decode(value.getBytes());
        byte[] cipherText = new byte[cipher.getOutputSize(output.length)];

        int cipherLength = cipher.processBytes(output, 0, output.length, cipherText, 0);
        int outputLength = cipher.doFinal(cipherText, cipherLength);
        outputLength += cipherLength;

        byte[] resultBytes = cipherText;
        if (outputLength != output.length) {
            resultBytes = new byte[outputLength];
            System.arraycopy(
                    cipherText, 0,
                    resultBytes, 0,
                    outputLength
            );
        }

        String result = new String(resultBytes);
return  result;
    }


    public  String encriptar(String texto)
            throws NoSuchAlgorithmException {
        MessageDigest md;
        String SHA512 = "SHA-512";
        String output = "";
        try {
            md = MessageDigest.getInstance(SHA512);
            md.update(texto.getBytes());
            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                output += s;
            }
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        Log.d("SHA", "Output: " + output);

        String temp = output.substring(10, 19);
        temp = output.substring(0, 20);

        return temp;

    }

}
