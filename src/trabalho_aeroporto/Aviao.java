package trabalho_aeroporto;

public class Aviao {
	private int id;
	private int combustivel;
	private int tempoChegada;

	public Aviao(int id, int combustivel, int tempoChegada) {
		this.id = id;
		this.combustivel = combustivel;
		this.tempoChegada = tempoChegada;
	}

	public int getId() {
		return id;
	}

	public int getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(int combustivel) {
		this.combustivel = combustivel;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTempoChegada(int tempoChegada) {
		this.tempoChegada = tempoChegada;
	}

	public void diminuirCombustivel() {
		combustivel--;
	}

	public int getTempoChegada() {
		return this.tempoChegada;
	}
}