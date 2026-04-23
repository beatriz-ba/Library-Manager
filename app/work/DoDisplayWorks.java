package bci.app.work;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bci.core.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import bci.core.Obra;

class DoDisplayWorks extends Command<LibraryManager> {

  DoDisplayWorks(LibraryManager receiver) {
    super(Label.SHOW_WORKS, receiver);
  }
  
  @Override
  protected final void execute() {
    // pede à LibraryManager a lista completa dos toStrings das obras
    Collection<Obra> obras = _receiver.showAllWorks();
    List<Obra> obrasOrdenadas = new ArrayList<>(obras);
    obrasOrdenadas.sort((o1, o2) -> Integer.compare(o1.getId(), o2.getId())); 

    for (Obra obra : obrasOrdenadas) {
      _display.addLine(obra.toString());
  }
  }
  }

