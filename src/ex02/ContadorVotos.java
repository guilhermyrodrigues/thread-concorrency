package ex02;

import java.util.concurrent.atomic.AtomicInteger;

public class ContadorVotos {

private AtomicInteger totalVotos = new AtomicInteger(0);

    public void registrarVoto() {
        totalVotos.incrementAndGet();
    }


    public int getTotalVotos() {
        return totalVotos.get();
    }
    
}
