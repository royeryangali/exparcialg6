package exparcialg6.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @Column(name="idusuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idusuario;
    String nombre;
    String apellido;
    Integer dni;
    String correo;
    @ManyToOne
    @JoinColumn(name = "idrol")
    Rol rol;



}
