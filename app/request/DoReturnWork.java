package bci.app.request;

import bci.core.LibraryManager;
import bci.core.Obra;
import bci.core.Utente;
import bci.core.exception.NoSuchWorkExceptionCore;
import bci.app.exception.NoSuchUserException;
import bci.app.exception.NoSuchWorkException;
import bci.app.exception.WorkNotBorrowedByUserException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.core.exception.NoSuchUserExceptionCore;
import bci.core.exception.NoSuchWorkExceptionCore;


/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

  DoReturnWork(LibraryManager receiver) {
    super(Label.RETURN_WORK, receiver);
    addIntegerField("userId", bci.app.user.Prompt.userId());
    addIntegerField("workId", bci.app.work.Prompt.workId());
  }

  @Override
  protected final void execute() throws CommandException {
    int userId = integerField("userId");
    int workId = integerField("workId");
    if(!_receiver.getLibrary().existeObra(workId)){
      throw new NoSuchWorkException(workId);
    } 
    if(!_receiver.getLibrary().existeUtente(userId)) {
      throw new NoSuchUserException(userId);
    }
    int multa = _receiver.returnWork(userId, workId);
    if(multa == -1) {
      throw new WorkNotBorrowedByUserException(workId, userId);
    }
    if(multa > 0) {
      String mensagem = Message.showFine(userId, multa);
      _display.popup(mensagem);
      _display.display();
      boolean n = Form.confirm(Prompt.finePaymentChoice());
      if (n) {
        _receiver.payFine(userId);
      } else {
      _receiver.NotPayFine(userId, multa);
      }
    }
    _display.display();

  }

}
