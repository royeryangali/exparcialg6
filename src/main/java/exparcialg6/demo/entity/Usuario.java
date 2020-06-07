package exparcialg6.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

    @Id
    @Column(name="idusuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Integer idusuario;

    @NotBlank
    @Size(min =2, max = 40, message = "El nombre debe estar entre 2 y 40 caracteres")
    @Column(nullable = false, name="nombre")
    private
    String nombre;

    @NotBlank
    @Size(min =2, max = 40, message = "El nombre debe estar entre 2 y 40 caracteres")
    @Column(nullable = false, name="apellido")
    private
    String apellido;

    @NotBlank
    @Digits(integer = 8, fraction = 0, message = "Debe ser entero y de 8 digitos")
    @Positive(message = "Debe ser positivo")
    @Column(nullable = false, name="dni")
    private Integer dni;

    @Column(name="correo")
    private
    String correo;

    @Column(name="password")
    private
    String password;

    @Column(name="rol")
    private
    String rol;

    @Column(name="activo")
    private
    int activo;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
}
