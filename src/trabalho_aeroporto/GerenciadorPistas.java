package trabalho_aeroporto;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorPistas {
    private List<Pista> pistas;
    private int pistaAtual = 0;

    public GerenciadorPistas(int quantidadePistas) {
        pistas = new ArrayList<Pista>();
        for (int i = 0; i < quantidadePistas; i++) {
            pistas.add(new Pista(i));
        }
    }

    public synchronized Pista getPistaLivreDecolagem() throws InterruptedException {
        while (true) {
            if (haPistaLivreDecolagem()) {
                Pista pista = pistas.get(pistaAtual);
                pista.setOcupada(true);
                pistaAtual = (pistaAtual + 1) % pistas.size();
                return pista;
            } else {
                wait();
            }
        }
    }

    public synchronized void liberarPista(Pista pista) {
        pista.setOcupada(false);
        notifyAll();
    }

    public synchronized boolean haPistaLivreDecolagem() {
        for (Pista pista : pistas) {
            if (!pista.isOcupada() && pista.isLiberadaParaDecolagem()) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean haPistaLivreAterrissagem() {
        for (Pista pista : pistas) {
            if (!pista.isOcupada() && pista.isLiberadaParaAterrissagem()) {
                return true;
            }
        }
        return false;
    }
}
