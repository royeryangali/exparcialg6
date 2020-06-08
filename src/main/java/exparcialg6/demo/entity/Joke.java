package exparcialg6.demo.entity;

import exparcialg6.demo.dto.MisPedidos;

import java.util.List;

public class Joke {
    private Pedido pedido;
    private List<MisPedidos> misPedidos;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<MisPedidos> getMisPedidos() {
        return misPedidos;
    }

    public void setMisPedidos(List<MisPedidos> misPedidos) {
        this.misPedidos = misPedidos;
    }
}
