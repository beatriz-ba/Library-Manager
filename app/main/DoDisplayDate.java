package bci.app.main;

import bci.core.LibraryManager;
import pt.tecnico.uilib.menus.Command;

//import para usar Message
import bci.app.main.Message;

/**
 * 4.1.2. Display the current date.
 */
class DoDisplayDate extends Command<LibraryManager> {

  DoDisplayDate(LibraryManager receiver) {
    super(Label.DISPLAY_DATE, receiver);
  }

  @Override
  protected final void execute() {
    //pedir a data atual ao core (através do LibraryManager)
    int currentDate = _receiver.getCurrentDate();

    //adicionar a linha formatada
    _display.addLine(Message.currentDate(currentDate));

    //mostrar no ecrã
    _display.display();
  }
}
