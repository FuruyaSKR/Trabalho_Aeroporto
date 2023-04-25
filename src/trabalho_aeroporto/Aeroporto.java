import java.util.*;
import java.util.Queue;

public class Aeroporto {
	private static final int MAX_PRATELEIRAS = 2;
	private static final int MAX_FILA_ATERRISSAGEM = 20;
	private static final int MAX_FILA_DECOLAGEM = 20;
	private static final int MAX_AVIOES_ATERRISSANDO_RESERVA = 2;

	private final Queue<Aviao> filaAterrissagemPista1 = new LinkedList<>();
	private final Queue<Aviao> filaAterrissagemPista2 = new LinkedList<>();
	private final Queue<Aviao> filaDecolagemPista1 = new LinkedList<>();
	private final Queue<Aviao> filaDecolagemPista2 = new LinkedList<>();
	private final ArrayList<Aviao> avioesAterrissaram = new ArrayList<>();
	private final ArrayList<Aviao> avioesDecolaram = new ArrayList<>();
	private int tempoTotalEsperaAterrissagem = 0;
	private int tempoTotalEsperaDecolagem = 0;
	private int avioesAterrissandoReserva = 0;
	private int tempoSimulacao = 0;
	private final Random gerador = new Random();

	public void run() {
		System.out.print("Digite o tempo de simulação (em unidades de tempo): ");
		Scanner sc = new Scanner(System.in);
		int tempoMaximo = sc.nextInt();

		while (tempoSimulacao < tempoMaximo) {
			// Chegam novas aeronaves para aterrissagem
			int numAeronavesAterrissagem = gerador.nextInt(3);
			for (int i = 0; i < numAeronavesAterrissagem; i++) {
				if (filaAterrissagemPista1.size() <= filaAterrissagemPista2.size()) {
					if (filaAterrissagemPista1.size() < MAX_PRATELEIRAS) {
						int combustivel = gerador.nextInt(20) + 1;
						Aviao aeronave = new Aviao(getProximoIdAterrissagem(), combustivel);
						if (pistaOcupadaPista1) {
							filaEsperaPista1.add(aeronave);
							System.out.println("Avião " + aeronave.getId()
									+ " entrou na fila de espera para aterrissagem na pista 1 com "
									+ aeronave.getCombustivel() + " unidades de combustível.");
						} else {
							pistaOcupadaPista1 = true;
							processarPista(filaAterrissagemPista1, avioesAterrissaram);
						}
					}
				} else {
					if (filaAterrissagemPista2.size() < MAX_PRATELEIRAS) {
						int combustivel = gerador.nextInt(20) + 1;
						Aviao aeronave = new Aviao(getProximoIdAterrissagem(), combustivel);
						if (pistaOcupadaPista2) {
							filaEsperaPista2.add(aeronave);
							System.out.println("Avião " + aeronave.getId()
									+ " entrou na fila de espera para aterrissagem na pista 2 com "
									+ aeronave.getCombustivel() + " unidades de combustível.");
						} else {
							pistaOcupadaPista2 = true;
							processarPista(filaAterrissagemPista2, avioesAterrissaram);
						}
					}
				}
			}

			// Chegam novas aeronaves para decolagem
			int numAeronavesDecolagem = gerador.nextInt(3);
			for (int i = 0; i < numAeronavesDecolagem; i++) {
				if (filaDecolagemPista1.size() <= filaDecolagemPista2.size()) {
					if (filaDecolagemPista1.size() < MAX_PRATELEIRAS) {
						Aviao aeronave = new Aviao(getProximoIdDecolagem(), gerador.nextInt(20) + 1);
						filaDecolagemPista1.add(aeronave);
						System.out.println("Avião " + aeronave.getId() + " entrou na fila de decolagem na pista 1.");
					} else {
						if (filaDecolagemPista2.size() < MAX_PRATELEIRAS) {
							Aviao aeronave = new Aviao(getProximoIdDecolagem(), gerador.nextInt(20) + 1);
							filaDecolagemPista2.add(aeronave);
							System.out
									.println("Avião " + aeronave.getId() + " entrou na fila de decolagem na pista 2.");
						}
					}
				}
			}

			// Processar pista de decolagem 1 e 2
			if (!pistaOcupada(filaDecolagemPista1) && !filaDecolagemPista1.isEmpty()) {
				processarDecolagem(filaDecolagemPista1, avioesDecolaram);
			}
			if (!pistaOcupada(filaDecolagemPista2) && !filaDecolagemPista2.isEmpty()) {
				processarDecolagem(filaDecolagemPista2, avioesDecolaram);
			}

			// Processar pista de aterrissagem 1 e 2
			if (!pistaOcupada(filaAterrissagemPista1) && !filaAterrissagemPista1.isEmpty()) {
				processarAterrissagem(filaAterrissagemPista1, avioesAterrissaram);
			}
			if (!pistaOcupada(filaAterrissagemPista2) && !filaAterrissagemPista2.isEmpty()) {
				processarAterrissagem(filaAterrissagemPista2, avioesAterrissaram);
			}

			// Chegada de aviões em reserva para aterrissagem
			if (avioesAterrissandoReserva < MAX_AVIOES_ATERRISSANDO_RESERVA) {
				int numAeronavesReserva = gerador.nextInt(2);
				for (int i = 0; i < numAeronavesReserva; i++) {
					int combustivel = gerador.nextInt(20) + 1;
					Aviao aeronave = new Aviao(getProximoIdAterrissagem(), combustivel);
					filaAterrissagemPista1.add(aeronave);
					avioesAterrissandoReserva++;
					System.out.println(
							"Avião " + aeronave.getId() + " entrou na fila de reserva para aterrissagem na pista 1 com "
									+ aeronave.getCombustivel() + " unidades de combustível.");
				}
			}
			tempoSimulacao++;
		}
		System.out.println("\n*** Simulação encerrada ***");
		System.out.println("Número total de aterrissagens: " + avioesAterrissaram.size());
		System.out.println("Número total de decolagens: " + avioesDecolaram.size());
		System.out.println("Tempo médio de espera para aterrissagem: "
				+ (tempoTotalEsperaAterrissagem / avioesAterrissaram.size()));
		System.out.println(
				"Tempo médio de espera para decolagem: " + (tempoTotalEsperaDecolagem / avioesDecolaram.size()));
	}

/**
* Verifica se a pista está ocupada por alguma aeronave
* @param fila a fila de espera da pista
* @return true se a pista estiver ocupada, false caso contrário
*/
private boolean pistaO

	public void processarPista() {
		if (this.pistaDisponivel) {
			if (this.filaPousos.size() > 0) {
				Aviao aviao = this.filaPousos.poll();
				System.out.println("Aviso: Avião " + aviao.getNumeroVoo() + " autorizado para pouso.");
				this.pistaDisponivel = false;
				this.pistaOcupada = aviao;
			} else if (this.filaDecolagens.size() > 0) {
				Aviao aviao = this.filaDecolagens.poll();
				System.out.println("Aviso: Avião " + aviao.getNumeroVoo() + " autorizado para decolagem.");
				this.pistaDisponivel = false;
				this.pistaOcupada = aviao;
			} else {
				System.out.println("Aviso: A pista está livre.");
			}
		} else {
			System.out.println("Aviso: A pista está ocupada pelo avião " + this.pistaOcupada.getNumeroVoo() + ".");
		}
	}

	private int getProximoIdAterrissagem() {
		return avioesAterrissaram.size() + 1;
	}

	private int getProximoIdDecolagem() {
		return avioesDecolaram.size() + 1;
	}

	private void processarPista(Queue<Aviao> filaPista, ArrayList<Aviao> listaAvioes) {
		if (!filaPista.isEmpty()) {
			Aviao aeronave = filaPista.peek();
			if (aeronave.getCombustivel() <= 0) {

				// aeronave sem combustível, remove da fila e adiciona à lista de avioes que
				// aterrissaram ou decolaram
				aeronave = filaPista.poll();
				listaAvioes.add(aeronave);
				if (filaPista == filaAterrissagemPista1 || filaPista == filaAterrissagemPista2) {
					System.out.println("Avião " + aeronave.getId() + " aterrou com sucesso!");
					tempoTotalEsperaAterrissagem += (tempoSimulacao - aeronave.getTempoChegada());
				} else {
					System.out.println("Avião " + aeronave.getId() + " decolou com sucesso!");
					tempoTotalEsperaDecolagem += (tempoSimulacao - aeronave.getTempoChegada());
				}
			} else {
				// reduzir o combustível da aeronave em 1 unidade
				aeronave.setCombustivel(aeronave.getCombustivel() - 1);
			}
		}
	}

}