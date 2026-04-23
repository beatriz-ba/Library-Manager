package bci.core;

import java.io.Serializable;

public class Requisicao implements Serializable {
	private int _dataInicio;
	private int _dataLimite;
	private boolean _devolvido;
	private Obra _obra;
	private Utente _utente;

	Requisicao (int dataInicio, int dataLimite, Obra obra, Utente utente) {
		_dataInicio = dataInicio;
		_dataLimite = dataLimite;
		_devolvido = false;
		_obra = obra;
		_utente = utente;
	}

	public int getDataInicio() {
		return _dataInicio;
	}

	public int getDataLimite() {
		return _dataLimite;
	}

	public boolean estadoDevolvido() {
		return _devolvido;
	}
	
	public Obra getObra() {
        return _obra;
    }

    public Utente getUtente() {
        return _utente;
    }
}
