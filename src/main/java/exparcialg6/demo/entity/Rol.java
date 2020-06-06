package exparcialg6.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="rol")
public class Rol {

    @Id
    @Column(name="idrol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idrol;
    String nombre;




}
