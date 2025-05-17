package ex02;

import java.util.concurrent.Callable;

public class Votacao implements Callable<Void>{
    private ContadorVotos contador;

    public Votacao(ContadorVotos contador) {
        this.contador = contador;
    }

    public Void call() {
        contador.registrarVoto();
        return null;
    }
    
}
