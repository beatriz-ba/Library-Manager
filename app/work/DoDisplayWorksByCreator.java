package bci.app.work;

import bci.core.LibraryManager;
import bci.app.exception.NoSuchCreatorException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.core.exception.NoSuchCreatorExceptionCore;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import bci.core.Obra;

/**
 * Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

  DoDisplayWorksByCreator(LibraryManager receiver) {
    super(Label.SHOW_WORKS_BY_CREATOR, receiver);
    addStringField("creatorId", Prompt.creatorId());
  }

  @Override
  protected final void execute() throws CommandException {
    String creatorId = stringField("creatorId");
    try {
      Collection<Obra> obras = _receiver.showWorksByCreator(creatorId);
      List<Obra> obrasOrdenadas = new ArrayList<>(obras);
      obrasOrdenadas.sort((o1, o2) -> o1.getTitulo().compareToIgnoreCase(o2.getTitulo()));
    for (Obra work : obrasOrdenadas) {
      _display.addLine(work.toString());
    }
    } catch (NoSuchCreatorExceptionCore e) {
      throw new NoSuchCreatorException(creatorId);
    }
    }
}


