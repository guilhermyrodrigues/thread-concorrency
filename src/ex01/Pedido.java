package ex01;

import java.util.concurrent.Callable;

public class Pedido implements Callable<String>{

    private EstoqueCentral estoque;
    private int idPedido;

    public Pedido(EstoqueCentral estoque, int idPedido) {
        this.estoque = estoque;
        this.idPedido = idPedido;
    }

    @Override
    public String call() throws Exception {
      boolean sucesso = estoque.retirarProduto();
      if (sucesso) {
          return "Pedido #" + idPedido + " atendido com sucesso!";
      } else {
          return "Pedido #" + idPedido + " recusado: estoque insuficiente!";
      }
    }
    
}
