package trabalho_aeroporto;

import java.util.concurrent.BlockingQueue;

public class GerenciadorAeronaves implements Runnable {
    private BlockingQueue<Aviao> filaAterrissagem;
    private int idProximaAeronave;
    
    public GerenciadorAeronaves(BlockingQueue<Aviao> filaAterrissagem) {
        this.filaAterrissagem = filaAterrissagem;
        this.idProximaAeronave = 1;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                // cria uma nova aeronave e adiciona na fila de aterrissagem
                Aviao aeronave = new Aviao(idProximaAeronave);
                idProximaAeronave++;
                filaAterrissagem.put(aeronave);
                
                // aguarda um tempo aleatório antes de criar a próxima aeronave
                Thread.sleep((long)(Math.random() * 5000));
            }
        } catch (InterruptedException e) {
            System.out.println("Gerenciador de aeronaves interrompido.");
        }
    }
}
