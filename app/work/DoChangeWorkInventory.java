package bci.app.work;

import bci.core.LibraryManager;
import bci.core.exception.NoSuchWorkExceptionCore;
import bci.core.exception.NotEnoughIventoryException;
import bci.app.exception.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {
  DoChangeWorkInventory(LibraryManager receiver) {
    super(Label.CHANGE_WORK_INVENTORY, receiver);
    addIntegerField("id", Prompt.workId());
    addIntegerField("value", Prompt.amountToDecrement());
  }

  @Override
  protected final void execute() throws CommandException {
    int id = integerField("id");
    int value = integerField("value");
    try {
      _receiver.changeInventario(id, value);
    }
    catch (NotEnoughIventoryException e) {
      String mensagem = Message.notEnoughInventory(id, value);
      _display.popup(mensagem);
      _display.display();
    }
    catch (NoSuchWorkExceptionCore e) {
      throw new NoSuchWorkException(id);
    }
  }
}
