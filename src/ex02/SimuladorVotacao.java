package ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimuladorVotacao {

    public static void main(String[] args) {
        
        ContadorVotos contador = new ContadorVotos();
        ExecutorService executor = Executors.newFixedThreadPool(6);
        List<Future<Void>> tarefas = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Future<Void> resultado = executor.submit(new Votacao(contador));
            tarefas.add(resultado);
        }

        executor.shutdown();

        for (Future<Void> tarefa : tarefas) {
            try {
                tarefa.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Erro ao processsar votação");
            }
        }

        System.out.println("Total de votos esperados: 1000");
        System.out.println("Total de votos registrados: " + contador.getTotalVotos());
    }
    
}
