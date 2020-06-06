package exparcialg6.demo.entity;


import javax.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @Column(name = "idproducto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idartesano;

    @Column(nullable = false, name = "nombre")
    private String nombre;

    @Column(nullable = false, name = "descripcion")
    private String descripcion;

    @Column(nullable = false, name = "precio")
    private Double precio;

    @Column(nullable = false, name = "foto")
    private String foto;

    @Column(nullable = false, name = "stock")
    private Integer stock;

    @Column(nullable = false, name = "codigo")
    private String codigo;


    public Integer getIdartesano() {
        return idartesano;
    }

    public void setIdartesano(Integer idartesano) {
        this.idartesano = idartesano;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
