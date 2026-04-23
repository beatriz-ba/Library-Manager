package bci.core;

import java.util.List;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import bci.core.exception.NotEnoughIventoryException;

public abstract class Obra implements Serializable {
    protected Library _library;
    private Set<Observador> _interresadosRequisicao;
    private Set<Observador> _interessadosDisponibilidade;
    private int _id;
    private int _quantidadeExemplares;
    private String _titulo;
    private Categoria _categoria;
    private int _exemplaresDisponiveis;
    private int _preco;

    Obra (Library library, int id, int quantidadeExemplares, String titulo, Categoria categoria, int preco) {
        _library = library;
        _interresadosRequisicao = new HashSet<>();
        _interessadosDisponibilidade = new HashSet<>();
        _id = id;
        _quantidadeExemplares = quantidadeExemplares;
        _titulo = titulo;
        _categoria = categoria;
        _exemplaresDisponiveis = quantidadeExemplares;
        _preco = preco;
    }

    boolean alterarInventario(int valor) throws NotEnoughIventoryException {
        if (valor < 0 && Math.abs(valor) > _exemplaresDisponiveis ) {
            throw new NotEnoughIventoryException();
        }
        alterarExemplaresDisponiveis(valor);
        _quantidadeExemplares += valor;
        if (_quantidadeExemplares == 0) {
            inventarioAZero();
            return true;
        }
        return false;
    }

    void alterarExemplaresDisponiveis(int valor) {
        boolean wasZero = _exemplaresDisponiveis == 0;
        _exemplaresDisponiveis += valor;   
        if (wasZero && valor > 0) {
            notificarDisponibilidade();
        }
    }

    boolean isDisponivel() {
        return _exemplaresDisponiveis > 0;
    }

    void addInteressadoRequisicao(Observador observador) {
        _interresadosRequisicao.add(observador);
    }    

    void addInteressadoDisponibilidade(Observador observador) {
        _interessadosDisponibilidade.add(observador);
    }

    void removeInteressadoRequisicao(Observador observador) {
        _interresadosRequisicao.remove(observador);
    }

    void removeInteressadoDisponibilidade(Observador observador) {
        _interessadosDisponibilidade.remove(observador);
    }

    boolean contemInteressadoDisponibilidade(Utente u) {
        if(_interessadosDisponibilidade.contains(u)) {
            return true;
        }
        return false;
    }

    void notificarDisponibilidade() {
        for (Observador o : _interessadosDisponibilidade) {
            Notificacao noti = new Notificacao(TipoNotificacao.DISPONIBILIDADE, toString());
            o.update(noti);
        }
    }

    void notificarRequisicao() {
        for (Observador o: _interresadosRequisicao) {
            o.update(new Notificacao(TipoNotificacao.REQUISICAO, toString()));
        }
    }

    abstract String getTipo();
    abstract List<Criador> getCriador();
    abstract void inventarioAZero();

    public String getTitulo() {
        return _titulo;
    }

    public int getId() {
        return _id;
    }

    Categoria getCategoria() {
        return _categoria;
    }

    int getPreco() {
        return _preco;
    }

    //verifica se o título contém o termo
    public boolean matches(String termo) {
        if (termo == null) return false;
        return _titulo.toLowerCase().contains(termo.toLowerCase());
    }

    //Função auxiliar reutilizável para comparação case-insensitive
    protected static boolean contemIgnorandoMaiusculas(String texto, String termo) {
        if (texto == null || termo == null) return false;
        return texto.toLowerCase().contains(termo.toLowerCase());
    }

    @Override
    public String toString() {
        return _id + " - " + 
               _exemplaresDisponiveis + " de " + 
               _quantidadeExemplares + " - " + 
               getTipo() + " - " + 
               _titulo + " - " + 
               _preco + " - " + 
               _categoria.getname() + " - ";
    }

    int getQuantidadeExemplares(){
        return _quantidadeExemplares;
    }
}
