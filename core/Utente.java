package bci.core;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Utente implements Serializable, Observador {
    private int _id;
    private String _nome;
    private String _email;
    private boolean _ativo;
    private Comportamento _comportamento;
    private List<Requisicao> _requisicoesUtente;
    private int _multa;
    private List<Notificacao> _notificacoes;
    private Library _library;
    private boolean _interessadoRequisicao;
    private List<Boolean> _obrasDevolvidas;

    Utente (int id, String nome, String email, Library biblioteca) {
        _id = id;
        _nome = nome;
        _email = email;
        _ativo = true;
        _comportamento = new Normal();
        _requisicoesUtente = new ArrayList<>();
        _multa = 0;
        _notificacoes = new ArrayList<>();
        _library = biblioteca;
        _interessadoRequisicao = false;
        _obrasDevolvidas = new ArrayList<>();
    }
    
    public int getId() {
        return _id;
    }

    public String getNome () {
        return _nome;
    }

    String getEstado() {
        if (_ativo) {
            return "ACTIVO";
        }
        return "SUSPENSO";
    }

    Comportamento getComportamento() {
        return _comportamento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_id).append(" - ")
          .append(_nome).append(" - ")
          .append(_email).append(" - ")
          .append(_comportamento.label()).append(" - ")
          .append(getEstado());

        if (_multa >= 0 && _ativo == false) {
            sb.append(" - ").append("EUR").append(" ").append(_multa);
        }
        return sb.toString();
    }

    @Override
    public boolean isInteressadoRequisicao() {
        return _interessadoRequisicao;
    }

    @Override
    public void setInteressadoRequisicao(boolean interessado) {
        _interessadoRequisicao = interessado;
    }

    @Override
    public void update (Notificacao notificacao){
        _notificacoes.add(notificacao);
    }
    
    Collection<Notificacao> getNotificacoes() {
        List<Notificacao> noti = new ArrayList<>(_notificacoes);
        _notificacoes.clear();
        return noti;
    }

    Collection<Requisicao> getRequisicoes() {
        return Collections.unmodifiableCollection(_requisicoesUtente);
    }

    int registarRequisicao(Obra obra){
        int data = _library.getCurrentDate() + _comportamento.prazoDiasPara(obra);
        Requisicao nova = new Requisicao(_library.getCurrentDate(), data, obra, this);
        if (obra.contemInteressadoDisponibilidade(this)) {
            obra.removeInteressadoDisponibilidade(this);
        }
        obra.alterarExemplaresDisponiveis(-1);
        _requisicoesUtente.add(nova);
        _library.addRequisicao(nova);
        obra.notificarRequisicao();
        return data;
    }

    int devolverObra(int obraId) {
        Requisicao requi = null;
        for (Requisicao r : _requisicoesUtente) {
            if (r.getObra().getId() == obraId) {
                requi = r;
                break;
            }
        }
        if (requi == null) {
            return -1;
        }

        requi.getObra().alterarExemplaresDisponiveis(1);

        boolean devolvidoNoPrazo = requi.getDataLimite() >= _library.getCurrentDate();
        _obrasDevolvidas.add(devolvidoNoPrazo);

        ArrayList<Boolean> lista = new ArrayList<>(_obrasDevolvidas);
        int tamanho = lista.size();
        List<Boolean> ultimos = null;
        if (tamanho >= 5) {
            ultimos = lista.subList(tamanho - 5, tamanho);
        } else if (tamanho >= 3 && tamanho < 5) {
            ultimos = lista.subList(tamanho - 3, tamanho);
        }

        if (ultimos != null) {
            Comportamento novoComportamento = _comportamento.processarDevolucao(ultimos);
            if (novoComportamento != _comportamento) {
                _comportamento = novoComportamento;
            }
        }

        Requisicao requisicao = requi;
        _requisicoesUtente.remove(requi);
        _library.removeRequisicao(requi);

        if (requisicao.getDataLimite() >= _library.getCurrentDate()){
            return 0;
        }
        return _multa + ((_library.getCurrentDate() - requisicao.getDataLimite()) * 5);
    }

    void pagarMulta() {
        if (_multa > 0 && !_ativo) {
            _multa = 0;
        }
        boolean temAtraso = false;
    
        for (Requisicao r : _requisicoesUtente) {
            if (r.getDataLimite() < _library.getCurrentDate()) {
                temAtraso = true;
                break;
            }
        }
        _ativo = !temAtraso;
    }
    

    void NaoPagarMulta(int m) {
        _ativo = false;
        _multa = m;
    }
    

    void setEstado(boolean b) {
        _ativo = b;
    }
}
