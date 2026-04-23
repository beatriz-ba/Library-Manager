package bci.core;

public interface Observador {
	void update(Notificacao notificacao);
    boolean isInteressadoRequisicao();
	void setInteressadoRequisicao(boolean interessado);
}
