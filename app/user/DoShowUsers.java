package bci.app.user;

import bci.core.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import bci.core.Utente;
//FIXME add more imports if needed

/**
 * 4.2.4. Show all users.
 */
class DoShowUsers extends Command<LibraryManager> {

  DoShowUsers(LibraryManager receiver) {
    super(Label.SHOW_USERS, receiver);

  }

  @Override
  protected final void execute() {
    Collection<Utente> utentes = _receiver.showAllUsers();
    List<Utente> utentesOrdenados = new ArrayList<>(utentes);
    utentesOrdenados.sort((u1, u2) -> u1.getNome().compareToIgnoreCase(u2.getNome()));
    for (Utente user : utentesOrdenados) {
      _display.addLine(user.toString());
    }
    _display.display();
  }
}