# 🧠 Atividade - Introdução à Concorrência com

# Threads em Java

## Informações do Autor

```
Nome: Roussian Gaioso
Data de atualização: 16/05/
```
## Regras de Entrega

```
Grupos de até 5 integrantes.
Trabalhos idênticos entre grupos diferentes implicarão em nota zero.
O grupo é responsável pela entrega. Arquivos corrompidos ou ausentes não serão considerados.
Os nomes dos integrantes devem constar no arquivo readme.md na raiz da submissão.
Caso a submissão seja reaberta, os trabalhos submetidos após a data prevista terão suas notas
reduzidas pela metade.
A atividade compõe parte da Nota Processual.
```
##  Exercício 1 — Simulação de Processamento de Pedidos

Deseja-se simular um sistema de controle de pedidos onde múltiplas tarefas retiram unidades de um
estoque compartilhado. Um pool de threads (ExecutorService) é utilizado para processar esses pedidos
simultaneamente, cada um como uma tarefa Callable. O objetivo é registrar o resultado de cada
tentativa de retirada.

Analise se o comportamento do sistema está de acordo com o número de itens disponíveis inicialmente. Em
caso de comportamento inesperado, investigue o motivo com base nos conceitos vistos em aula e
proponha uma solução que preserve a paralelização sem comprometer a integridade do sistema.

### Código fornecido:

```
import java.util.concurrent.*;
import java.util.*;
```
```
public class SistemaPedidos {
```
```
public static void main(String[] args) {
```
```
EstoqueCentral estoque = new EstoqueCentral();
```
```
ExecutorService executor = Executors.newFixedThreadPool( 4 );
List<Future<String>> resultados = new ArrayList<>();
```
```
for (int i = 0 ; i < 10 ; i++) {
Future<String> resultado = executor.submit(new Pedido(estoque,
```

i + 1 ));
resultados.add(resultado);
}

executor.shutdown();

for (Future<String> futuro : resultados) {
try {
System.out.println(futuro.get());
} catch (InterruptedException | ExecutionException e) {
System.out.println("Erro ao obter resultado do pedido.");
}
}

System.out.println("Quantidade restante em estoque: " +
estoque.getQuantidade());
}
}

class EstoqueCentral {
private int quantidade = 5 ;

public boolean retirarProduto() {
if (quantidade > 0 ) {
try {
Thread.sleep( 100 ); // simula tempo de verificação e
retirada
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

import java.util.concurrent.*;

class Pedido implements Callable<String> {
private EstoqueCentral estoque;
private int idPedido;

public Pedido(EstoqueCentral estoque, int idPedido) {


```
this.estoque = estoque;
this.idPedido = idPedido;
}
```
```
public String call() {
boolean sucesso = estoque.retirarProduto();
if (sucesso) {
return "Pedido #" + idPedido + " atendido com sucesso.";
} else {
return "Pedido #" + idPedido + " recusado: estoque
insuficiente.";
}
}
}
```
### ✅ Tarefas

```
1. Execute o sistema. Analise se o número de pedidos atendidos condiz com a quantidade de itens
disponíveis inicialmente no estoque.
2. Descreva tecnicamente qualquer comportamento incorreto identificado.
3. Fundamente sua explicação utilizando os conceitos de concorrência, paralelismo e acesso a recurso
compartilhado ministrados em aula.
4. Implemente e justifique tecnicamente uma solução que corrija o problema observado sem eliminar o
uso de múltiplas threads.
5. Compare o resultado obtido com o comportamento original. Justifique a consistência da nova versão.
```
##  Exercício 2 — Simulador de Votos em Pesquisa Online

O código abaixo simula um sistema de votação, em que múltiplas tarefas incrementam um contador global
de votos. O programa utiliza Callable, Future e um pool de threads (ExecutorService). O objetivo é
que o número final de votos registrados coincida exatamente com o número de tarefas submetidas.

Analise se o comportamento do sistema corresponde à expectativa. Em caso negativo, investigue
tecnicamente o motivo e proponha uma solução robusta que preserve o paralelismo sem afetar a
integridade da contagem.

### Código fornecido:

```
import java.util.concurrent.*;
import java.util.*;
```
```
public class SimuladorVotacao {
```
```
public static void main(String[] args) {
```
```
ContadorVotos contador = new ContadorVotos();
ExecutorService executor = Executors.newFixedThreadPool( 6 );
List<Future<Void>> tarefas = new ArrayList<>();
```

for (int i = 0 ; i < 1000 ; i++) {
Future<Void> resultado = executor.submit(new
Votacao(contador));
tarefas.add(resultado);
}

executor.shutdown();

for (Future<Void> tarefa : tarefas) {
try {
tarefa.get();
} catch (InterruptedException | ExecutionException e) {
System.out.println("Erro ao processar votação.");
}
}

System.out.println("Total de votos esperados: 1000");
System.out.println("Total de votos registrados: " +
contador.getTotalVotos());
}
}

class ContadorVotos {
private int totalVotos = 0 ;

public void registrarVoto() {
totalVotos++;
}

public int getTotalVotos() {
return totalVotos;
}
}

class Votacao implements Callable<Void> {
private ContadorVotos contador;

public Votacao(ContadorVotos contador) {
this.contador = contador;
}

public Void call() {
contador.registrarVoto();
return null;
}
}


### ✅ Tarefas

```
1. Execute o sistema. Verifique se o número total de votos registrados é igual a 1000.
2. Caso ocorra divergência, explique tecnicamente por que o resultado é incorreto, considerando os
conceitos de atomicidade e concorrência.
3. Implemente uma alternativa segura que preserve o uso de ExecutorService e Callable, mas
garanta a consistência da contagem de votos.
4. Justifique por que o uso de sincronização explícita é necessário mesmo em cenários onde se utiliza
um pool de threads.
5. Compare o comportamento da versão corrigida com a versão original, apontando tecnicamente a
diferença e os ganhos obtidos.
```

