package bci.app.work;

import bci.core.LibraryManager;
import bci.app.exception.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.core.exception.NoSuchWorkExceptionCore;
/**
 * Command to display a work.
 */
class DoDisplayWork extends Command<LibraryManager> {

  DoDisplayWork(LibraryManager receiver) {
    super(Label.SHOW_WORK, receiver);
    addIntegerField("id", Prompt.workId());

  }

  @Override
  protected final void execute() throws CommandException {
    int id = integerField("id");
    try {
      // Pede ao LibraryManager a informação da obra
      String info = _receiver.showWork(id);

      // Mostra no ecrã
      _display.addLine(info);
      _display.display();

    } 
    catch (NoSuchWorkExceptionCore e) {
      // Se a obra não existir, lança exceção tratada pelo framework
      throw new NoSuchWorkException(id);
    }
  }
}
