package exparcialg6.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

    @Id
    @Column(name="idusuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Integer idusuario;

    @Column(nullable = false, name="nombre")
    private
    String nombre;

    @Column(nullable = false, name="apellido")
    private
    String apellido;

    @Column(nullable = false, name="dni")
    private
    Integer dni;

    @Column(nullable = false, name="correo")
    private
    String correo;

    @Column(nullable = false, name="rol")
    private
    String rol;

    @Column(nullable = false, name="activo")
    private
    int activo;

    private String password;

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
