package ex01;

public class EstoqueCentral {
    private int quantidade = 5;

    public synchronized boolean retirarProduto() {
        if (quantidade > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
            quantidade--;
            return true;
        } else {
            return false;
        }
    }
    public int getQuantidade() {
            return quantidade;
        }
}
