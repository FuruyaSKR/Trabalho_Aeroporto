package trabalho_aeroporto;

import java.util.LinkedList;
import java.util.Queue;

public class FilaAeronavesDecolagem {
    private Queue<Aviao> fila;

    public FilaAeronavesDecolagem() {
        fila = new LinkedList<>();
    }

    public void adicionar(Aviao aviao) {
        fila.add(aviao);
    }

    public Aviao remover() {
        return fila.poll();
    }

    public Aviao consultarProximo() {
        return fila.peek();
    }

    public boolean estaVazia() {
        return fila.isEmpty();
    }

    public int tamanho() {
        return fila.size();
    }
}
