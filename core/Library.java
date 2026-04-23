package bci.core;


import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import bci.core.exception.*;

/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {

  /** Serial number for serialization. */
  @Serial
  private static final long serialVersionUID = 202501101348L;

  private Map<Integer, Utente> _utentes;
  private Map<Integer, Obra> _obras;
  private Map<String, Criador> _criadores;
  private Set<Requisicao> _requisicoes;
  private int _proximoIdUtente = 1;
  private int _proximoIdObra = 1;
  private int _currentDate = 1;
  private boolean _modified;
  private List<Regra> _regras;

  /**
   * Creates an empty library with no users, works, creators or requests.
   */
  Library () {
    _utentes = new HashMap<>();
    _obras = new HashMap<>();
    _criadores = new HashMap<>();
    _requisicoes = new HashSet<>();
    _regras = new ArrayList<>();
    _regras.add(new Regra1());
    _regras.add(new Regra2());
    _regras.add(new Regra3());
    _regras.add(new Regra4());
    _regras.add(new Regra5());
    _regras.add(new Regra6());
  }
  
  /**
   * Returns the current date of the library.
   *
   * @return current date in days.
   */
  int getCurrentDate () {
    return _currentDate;
  }

  /**
   * Advances the current date by the specified number of days.
   *
   * @param days number of days to advance.
   */
  void advanceDate(int days) {
    if (days > 0) {
        _currentDate += days;
        _modified = true;
    }

    for (Requisicao r : _requisicoes) {
        if (r != null && r.getUtente() != null) {
            if (r.getDataLimite() < _currentDate) {
                r.getUtente().setEstado(false);
            }
        }
    }
  }

  /**
   * Returns whether the library has been modified since the last save.
   *
   * @return true if modified, false otherwise.
   */
  boolean getModif() {
    return _modified;
  }

  /**
   * Sets the modified status of the library.
   *
   * @param a true if the library was modified, false otherwise.
   */
  void setModif(boolean a) {
    _modified = a;
  }

  /**
   * Registers a new user in the library.
   *
   * @param nome  user's name.
   * @param email user's email address.
   * @return unique identifier of the new user.
   */
  int addUtente (String nome, String email) {
    int id = _proximoIdUtente++;
    Utente novoUtente = new Utente(id, nome, email, this);
    _utentes.put(id, novoUtente);
    _modified = true;
    return novoUtente.getId();
  }

  /**
   * Returns a specific user from the library.
   *
   * @param id user identifier.
   * @return the user with the given id.
   * @throws NoSuchUserExceptionCore if the user does not exist.
   */
  public Utente getUtente (int id) throws NoSuchUserExceptionCore {
    if (!_utentes.containsKey(id)){
      throw new NoSuchUserExceptionCore();
    }
    return _utentes.get(id);
  }

  /**
   * Returns all users registered in the library.
   *
   * @return a collection with all users.
   */
  Collection<Utente> getUtentes() {
    return Collections.unmodifiableCollection(_utentes.values());
  }

  /**
   * Returns a specific work from the library.
   *
   * @param id work identifier.
   * @return the work with the given id.
   * @throws NoSuchWorkExceptionCore if the work does not exist.
   */
  public Obra getObra (int id) throws NoSuchWorkExceptionCore {
    if (!_obras.containsKey(id)) {
      throw new NoSuchWorkExceptionCore();
    }
    return _obras.get(id);
  }

  /**
   * Returns all works available in the library.
   *
   * @return a collection with all works.
   */
  Collection<Obra> getObras() {
    return Collections.unmodifiableCollection(_obras.values());
  }

  /**
   * Returns all works created by a specific creator.
   *
   * @param nome name of the creator.
   * @return a list of works created by that creator.
   * @throws NoSuchCreatorExceptionCore if the creator does not exist.
   */
  Collection<Obra> getObrasCriadores (String nome) throws NoSuchCreatorExceptionCore {
    if (!_criadores.containsKey(nome)) {
      throw new NoSuchCreatorExceptionCore();
    }
    return Collections.unmodifiableList(_criadores.get(nome).getObras());
  }

  /**
   * Registers a new book in the library.
   *
   * @param title    the book title.
   * @param authors  list of authors who wrote the book.
   * @param price    price of the book.
   * @param category book category.
   * @param isbn     unique ISBN code of the book.
   * @param copies   number of copies available.
   */
  void registerBook(String title, List<Criador> authors, int price, Categoria category, String isbn, int copies) {
    int n = _proximoIdObra++;
    Livro livro = new Livro(this, n, copies, title, category, price, isbn, authors);
    this._obras.put(n, livro);
    for (Criador criador : authors) {
      criador.addObras(livro);
    }
    _modified = true;
  }

  /**
   * Registers a new DVD in the library.
   *
   * @param title     the DVD title.
   * @param director  the creator (director) of the DVD.
   * @param price     price of the DVD.
   * @param category  DVD category.
   * @param igac      IGAC registration number.
   * @param copies    number of copies available.
   */
  void registerDvd(String title, Criador director, int price, Categoria category, String igac, int copies) {
    int n = _proximoIdObra++;
    Dvd dvd = new Dvd(this, n, copies, title, category, price, igac, director);
    this._obras.put(n, dvd);
    director.addObras(dvd);
    _modified = true;
  }

  /**
   * Registers a creator in the library.  
   * If the creator already exists, returns the existing one.
   *
   * @param name creator's name.
   * @return the existing or newly created creator.
   */
  Criador registerCreator(String name) {
    if (_criadores.containsKey(name)) {
      return _criadores.get(name);
    }
    Criador criador = new Criador(name);
    _criadores.put(name, criador);
    _modified = true;
    return criador;
  }
  
  /**
   * Read text input file at the beginning of the program and populates the
   * the state of this library with the domain entities represented in the text file.
   * 
   * @param filename name of the text input file to process
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    MyParser parser = new MyParser(this);
    parser.parseFile(filename);
  }

  Collection<Notificacao> getNotificacoesUtente(int id) throws NoSuchUserExceptionCore {
    if (!_utentes.containsKey(id)) {
      throw new NoSuchUserExceptionCore();
    }
    return _utentes.get(id).getNotificacoes();
  }

  void removerCriador(String nome) {
    _modified = true;
    _criadores.remove(nome);
  }

  void changeInventario(int id, int valor) throws NotEnoughIventoryException, NoSuchWorkExceptionCore{
    Obra obra = getObra(id);
    if (obra.alterarInventario(valor)) {
      _obras.remove(id);
    }
  }
  
  public int validarRegras(Utente utente, Obra obra) {
    for (int i = 0; i < _regras.size(); i++) {
        if (!_regras.get(i).validar(utente, obra)) {
            return i + 1; // Avoids expensive indexOf call
        }
    }
    return 0;
  }

  void adicionarInteressadoDisponibilidade(Utente utente, Obra obra) {
    obra.addInteressadoDisponibilidade(utente);
  }

  int requisitarObra(Utente utente, Obra obra) {
    return utente.registarRequisicao(obra);
  }

  void addRequisicao(Requisicao r) {
    _requisicoes.add(r);
  }

  public boolean existeObra(int id) {
    if(_obras.containsKey(id)){
      return true;
    }
    return false;
  }

  public boolean existeUtente(int id) {
    if(_utentes.containsKey(id)){
      return true;
    }
    return false;
  }

  int returnWork(int userId, int workId) {
    Utente u = _utentes.get(userId);
    return u.devolverObra(workId);
  }

  void removeRequisicao(Requisicao r) {
    _requisicoes.remove(r);
  }

  void payFine(int userId) {
    _utentes.get(userId).pagarMulta();
  }

  void NotPayFine(int userId, int m) {
    _utentes.get(userId).NaoPagarMulta(m);
  }

  public boolean estaSuspenso (int id) {
    if(_utentes.get(id).getEstado().equals("SUSPENSO")) {
      return true;
    }
    return false;
  }
}

