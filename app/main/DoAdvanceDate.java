package bci.app.main;

import bci.core.LibraryManager;
import pt.tecnico.uilib.menus.Command;

class DoAdvanceDate extends Command<LibraryManager> {

  DoAdvanceDate(LibraryManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    //adicionar campo de input: número de dias
    addIntegerField("days", Prompt.daysToAdvance());
  }

  @Override
  protected final void execute() {
    //ler o número de dias introduzido pelo utilizador
    int days = integerField("days");

    //só avança se for positivo
    if (days > 0) {
    _receiver.advanceDate(days);
    }
  }
}
