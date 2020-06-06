package exparcialg6.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="rol")
public class Rol {

    @Id
    @Column(name="idrol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Integer idrol;
    @Column(nullable = false, name="nombre")
    private
    String nombre;


    public Integer getIdrol() {
        return idrol;
    }

    public void setIdrol(Integer idrol) {
        this.idrol = idrol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
