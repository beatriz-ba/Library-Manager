package bci.app.request;

import bci.core.LibraryManager;
import bci.app.exception.NoSuchUserException;
import bci.app.exception.NoSuchWorkException;
import bci.app.exception.BorrowingRuleFailedException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.core.Obra;
import bci.core.Utente;
import bci.core.exception.NoSuchUserExceptionCore;
import bci.core.exception.NoSuchWorkExceptionCore;

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

  DoRequestWork(LibraryManager receiver) {
    super(Label.REQUEST_WORK, receiver);
    addIntegerField("UserId", bci.app.user.Prompt.userId());
    addIntegerField("workId", bci.app.work.Prompt.workId());
  }

  @Override
  protected final void execute() throws CommandException {
    int userId = integerField("UserId");
    int workId = integerField("workId");
    try {
      Obra obra = _receiver.getLibrary().getObra(workId);
      Utente utente = _receiver.getLibrary().getUtente(userId);
      int result = _receiver.getLibrary().validarRegras(utente, obra);
        if (result != 3 && result != 0 ) {
          throw new BorrowingRuleFailedException(userId, workId, result);
        }
        if (result == 3) {
          boolean n = Form.confirm(Prompt.returnNotificationPreference());
          if (n) {
            _receiver.adicionarInteressadoDisponibilidade(utente, obra);
          }
        }
        if(result == 0) {
          int i =_receiver.requisitar(utente, obra);
          String mensagem = Message.workReturnDay(workId, i);
          _display.popup(mensagem);
          _display.display();
        }
      }
      catch (NoSuchWorkExceptionCore e1){
        throw new NoSuchWorkException(workId);
      }
      catch (NoSuchUserExceptionCore e2) {
        throw new NoSuchUserException(userId);
      }
    }
}
