package bci.core;

import java.io.Serializable;

public class Notificacao implements Serializable{
	private TipoNotificacao _tipoNotificacao;
	private String _mensagem;
  
	Notificacao(TipoNotificacao tipoNotificacao, String mensagem){
		_tipoNotificacao = tipoNotificacao;
		_mensagem = mensagem;
	}

	public String getMensagem() {
		if (_tipoNotificacao.getValue()==0){
			return "DISPONIBILIDADE: "+ _mensagem;
		}
	return "REQUISIÇÃO: "+ _mensagem;
		}
	}


