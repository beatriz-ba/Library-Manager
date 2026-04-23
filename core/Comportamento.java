package bci.core;

import java.util.List;
import java.io.Serializable;

public interface Comportamento extends Serializable {
    String label();                  // Nome do comportamento (NORMAL, FALTOSO, CUMPRIDOR)
    int maximoRequisicoes();         // Limite de obras que o utente pode requisitar
    int prazoDiasPara(Obra obra);    // Quantos dias pode ficar com a obra
    Comportamento proximoEstado(int n);
	boolean verificaPrecoLimite();
	int getLimiteRequisicoes();
    Comportamento processarDevolucao(List<Boolean> ultimasDevolucoes);
}
