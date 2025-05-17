package ex01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SistemaPedidos {

    public static void main(String[] args) {
        EstoqueCentral estoque = new EstoqueCentral();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<String>> resultados = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<String> resultado = executor.submit(new Pedido(estoque, i + 1));
            resultados.add(resultado);
        }

        for (Future<String> future : resultados) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Erro ao obter resultado do pedido: " + e.getMessage());
            }
        }

        executor.shutdown();

        System.out.println("Quantidade de produtos restantes no estoque: " + estoque.getQuantidade());
    }
}