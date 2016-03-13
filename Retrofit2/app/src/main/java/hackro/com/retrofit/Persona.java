package hackro.com.retrofit;

/**
 * Created by hackro on 10/01/16.
 */
public class Persona {

    private   Integer id_usuario;
    private String   nombre;

    public Persona(Integer id_usuario, String nombre) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
    }

    public Persona() {
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
