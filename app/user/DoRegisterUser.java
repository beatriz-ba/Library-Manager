package bci.app.user;

import bci.core.LibraryManager;
import bci.app.user.Message;
import bci.app.exception.UserRegistrationFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

  DoRegisterUser(LibraryManager receiver) {
    super(Label.REGISTER_USER, receiver);
    addStringField("nome", Prompt.userName());
    addStringField("email", Prompt.userEMail());
  }

  @Override
  protected final void execute() throws CommandException {
    String nome = stringField("nome");
    String email = stringField("email");
    if (nome == null || nome.isBlank() || email == null || email.isBlank()){
      throw new UserRegistrationFailedException(nome, email);
    }
    int n = _receiver.registarUtente(nome, email);
    String mensagem = Message.registrationSuccessful(n);
    _display.popup(mensagem);
    _display.display();
  }
}
