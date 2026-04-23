package bci.core;

import java.util.List;

public class Cumpridor implements Comportamento {

    @Override
    public String label() { return "CUMPRIDOR"; }

    @Override
    public int maximoRequisicoes() { return 5; }

    @Override
    public int prazoDiasPara(Obra obra) {
        int n = obra.getQuantidadeExemplares();
        if (n == 1) return 8;
        if (n <= 5) return 15;
        return 30;
	}
	@Override
    public Comportamento proximoEstado(int n) {
    	if (n < 5) return new Normal(); // perdeu o padrão de 5 boas
    	return this; // mantém-se cumpridor
	}

	@Override
    public boolean verificaPrecoLimite() {
        return true; // Cumpridor pode requisitar obra a qualquer preço
    }

	@Override
    public int getLimiteRequisicoes() {
        return 5;
    }

    // Classe Cumpridor
    @Override
    public Comportamento processarDevolucao(List<Boolean> ultimasDevolucoes) {
        int size = ultimasDevolucoes.size();
        if (size == 5) {
            int countTrue = 0;
            for (Boolean b : ultimasDevolucoes) {
                if (b) countTrue++;
            }
            if (countTrue < 5) {
                return new Normal(); 
            }
        }
        if (size == 3) {
            int countFalse = 0;
            for (Boolean b : ultimasDevolucoes) {
                if (!b) countFalse++;
            }
            if (countFalse > 1) {
                return new Normal(); 
            }
        }
        return this; // mantém Cumpridor
    }

    }