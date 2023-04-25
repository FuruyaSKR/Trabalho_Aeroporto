package trabalho_aeroporto;

import java.util.concurrent.TimeUnit;

public class Pista {
    private int num;
    private boolean livre;
    private Aviao aeronaveAtual;

    public Pista(int num) {
        this.num = num;
        this.livre = true;
        this.aeronaveAtual = null;
    }

    public int getNum() {
        return num;
    }

    public boolean estaLivre() {
        return livre;
    }

    public synchronized boolean haAeronave() {
        return aeronaveAtual != null;
    }

    public synchronized void aterrissar(Aviao aviao) throws InterruptedException {
        while (!livre) {
            wait();
        }
        System.out.println("Pista " + num + " liberada para aterrissagem do avião " + aviao.getId());
        livre = false;
        aeronaveAtual = aviao;
        TimeUnit.SECONDS.sleep(2); // simulando aterrissagem
        System.out.println("Avião " + aviao.getId() + " pousou na pista " + num);
    }

    public synchronized void decolar(Aviao aviao) throws InterruptedException {
        while (!livre) {
            wait();
        }
        System.out.println("Pista " + num + " liberada para decolagem do avião " + aviao.getId());
        livre = false;
        aeronaveAtual = aviao;
        TimeUnit.SECONDS.sleep(2); // simulando decolagem
        System.out.println("Avião " + aviao.getId() + " decolou da pista " + num);
    }

    public synchronized void liberarPista() {
        livre = true;
        aeronaveAtual = null;
        notifyAll();
    }
}
