package bci.core;
import java.io.*;
import java.util.Collection;
import bci.core.exception.*;



/**
 * The façade class. Represents the manager of this application. It manages the current
 * library and works as the interface between the core and user interaction layers.
 */
public class LibraryManager {


  /** The object doing all the actual work. */
  /* The current library */
  private Library _library;
  private String _filename = null;

  /**
   * Constructs a new LibraryManager.
   */
  public LibraryManager () {
    this._library = new Library();
  }

  

  // Returns the current Library
  public Library getLibrary () {
    return this._library;
  }

  /**
   * Saves the serialized application's state into the file associated to the current library
   *
   * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
   * @throws MissingFileAssociationException if the current library does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   **/
  public void saveAs(String filename) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
      oos.writeObject(_library);
      _filename = filename;
	  _library.setModif(false);
    }
  }

    public void save() throws MissingFileAssociationException, IOException {
      if (_filename == null || _filename.isBlank())
        throw new MissingFileAssociationException();
      saveAs(_filename);
    }

  /**
   * Loads the previously serialized application's state as set it as the current library.
   *
   * @param filename name of the file containing the serialized application's state
   *        to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *         an error while processing this file.
   **/
  public void load(String filename) throws UnavailableFileException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) { 
      _library = (Library) ois.readObject();
      _filename = filename;
      _library.setModif(false);
    } catch (FileNotFoundException e) {
      throw new UnavailableFileException(filename);
    } catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * Read text input file and initializes the current library (which should be empty)
   * with the domain entities representeed in the import file.
   *
   * @param datafile name of the text input file
   * @throws ImportFileException if some error happens during the processing of the
   * import file.
   **/
  public void importFile(String datafile) throws ImportFileException {
    try {
      if (datafile != null && !datafile.isEmpty())
        _library.importFile(datafile);
    } catch (IOException | UnrecognizedEntryException e) {
      throw new ImportFileException(datafile, e);
    }
  } 

  /**
   * Gets the modification status of the library.
   * @return true if the library has been modified, false otherwise.
   */
  public boolean getModified() {
    return _library.getModif();
  }

  /**
   * Gets the current date from the library system.
   *
   * @return the current date as an integer
   */
  public int getCurrentDate () {
    return _library.getCurrentDate();
  }

  /**
   * Advances the date in the library system by the specified number of days.
   * 
   * @param days the number of days to advance the date by
   */
  public void advanceDate (int days) {
    _library.advanceDate(days);
  }

  /**
   * Registers a new user in the library.
   *
   * @param nome user’s name.
   * @param email user’s email address.
   * @return unique identifier of the new user.
   */
  public int registarUtente (String nome, String email) {
    return _library.addUtente(nome, email);
  }

   /**
   * Displays information about a specific work.
   *
   * @param id work identifier.
   * @return textual description of the work.
   * @throws NoSuchWorkExceptionCore if the work does not exist.
   */
  public String showWork (int id) throws NoSuchWorkExceptionCore {
    return _library.getObra(id).toString();
  }

   /**
   * Returns a list with all works in the library, ordered by their identifier.
   *
   * @return list of all works as formatted strings.
   */
  public Collection<Obra> showAllWorks() {
    return _library.getObras();
	}

    /**
   * Returns a list of all works created by a specific creator, ordered alphabetically by title.
   *
   * @param creatorId identifier of the creator.
   * @return list of works created by the specified creator.
   * @throws NoSuchCreatorExceptionCore if the creator does not exist.
   */
  public Collection<Obra> showWorksByCreator(String creatorId) throws NoSuchCreatorExceptionCore {
    return _library.getObrasCriadores(creatorId);
  }


  /**
   * Returns the information of a specific user.
   *
   * @param id user identifier.
   * @return textual description of the user.
   * @throws NoSuchUserExceptionCore if the user does not exist.
   */
  public String showUser (int id) throws NoSuchUserExceptionCore {
    return _library.getUtente(id).toString();
  }

   /**
   * Returns a list with all users in the library, ordered alphabetically by name.
   *
   * @return list of users as formatted strings.
   */
  public Collection<Utente> showAllUsers () {
    return _library.getUtentes();
  }

  public Collection<Notificacao> showNotificationsUser (int id) throws NoSuchUserExceptionCore {
    return _library.getNotificacoesUtente(id);
  }


  public Collection<Obra> searchWorks() {
    return _library.getObras();
  }

  public void changeInventario(int id, int valor) throws NotEnoughIventoryException, NoSuchWorkExceptionCore {
    _library.setModif(true);
    _library.changeInventario(id, valor);
  }

  public void adicionarInteressadoDisponibilidade(Utente u, Obra o) {
    _library.setModif(true);
    _library.adicionarInteressadoDisponibilidade(u, o);
  }

  public int requisitar(Utente u, Obra o) {
    _library.setModif(true);
    return _library.requisitarObra(u,o);
  }

  public int returnWork(int userId, int workId){
    _library.setModif(true);
    return _library.returnWork(userId, workId);
  }

  public void payFine(int userId) {
    _library.setModif(true);
    _library.payFine(userId);
  }

  public void NotPayFine(int userId, int m) {
    _library.setModif(true);
    _library.NotPayFine(userId, m);
  }

}

