package bci.core;

import java.util.ArrayList;
import java.util.List;

public class Faltoso implements Comportamento {

    @Override
    public String label() { return "FALTOSO"; }

    @Override
    public int maximoRequisicoes() { return 1; }

    @Override
    public int prazoDiasPara(Obra obra) {
        int n = obra.getQuantidadeExemplares();
        if (n == 1) return 2;
        if (n <= 5) return 2;
        return 2;
    }

	@Override
	public Comportamento proximoEstado(int n) {
    if (n == 3) return new Normal();
    return this;
	}

	@Override
    public boolean verificaPrecoLimite() {
        return false; // nao pode requisitar obra superior a 25 euros
    }

	@Override
    public int getLimiteRequisicoes() {
        return 1;
    }

    // Classe Faltoso
    @Override
    public Comportamento processarDevolucao(List<Boolean> ultimasDevolucoes) {
        int size = ultimasDevolucoes.size();
        if (size == 3) {
            int countTrue = 0;
            for (Boolean b : ultimasDevolucoes) {
                if (b) countTrue++;
            }
            if (countTrue == 3) {
                return new Normal(); // 3 no prazo -> Normal
            }
        }
        
        if(size == 5) {
            List<Boolean> lista = new ArrayList<>(ultimasDevolucoes);
            lista = lista.subList(size - 3, size);
            int countTrue = 0;
            for (Boolean b : lista) {
                if (b) countTrue++;
            }
            if (countTrue == 3) {
                return new Normal(); // 3 no prazo -> Normal
            }
        }
        return this; // mantém Faltoso
    }

}