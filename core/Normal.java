package bci.core;

import java.util.List;

public class Normal implements Comportamento {

    @Override
    public String label() { return "NORMAL"; }

    @Override
    public int maximoRequisicoes() { return 3; }

    @Override
    public int prazoDiasPara(Obra obra) {
        int n = obra.getQuantidadeExemplares();
        if (n == 1) return 3;
        if (n <= 5) return 8;
        return 15;
    }

    @Override
    public Comportamento proximoEstado(int n) {
        if (n == 5)  return new Cumpridor();
        if (n == 3)  return new Faltoso();
        return this;
    }

	@Override
	public boolean verificaPrecoLimite() {
        return false; // Normal não pode requisitar obra (>25€)
    }

	@Override
    public int getLimiteRequisicoes() {
        return 3;
    }

    // Classe Normal
    @Override
    public Comportamento processarDevolucao(List<Boolean> ultimasDevolucoes) {
        int size = ultimasDevolucoes.size();
    
        if (size == 3) {
            int countFalse = 0;
            for (Boolean b : ultimasDevolucoes) {
                if (!b) countFalse++;
            }
            if (countFalse == 3) {
                return new Faltoso(); // 3 atrasos -> Faltoso
            }
        } else if (size == 5) {
            int countTrue = 0;
            for (Boolean b : ultimasDevolucoes) {
                if (b) countTrue++;
            }
            if (countTrue == 5) {
                return new Cumpridor(); // 5 no prazo -> Cumpridor
            }
        }
    
        return this; // mantém estado atual
    }
}
