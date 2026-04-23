package bci.app.work;

import bci.core.LibraryManager;
import bci.core.Obra;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import static bci.app.work.Prompt.searchTerm;

import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;


/**
 * Perform search according to miscellaneous criteria.
 */
class DoPerformSearch extends Command<LibraryManager> {

  DoPerformSearch(LibraryManager receiver) {
    super(Label.PERFORM_SEARCH, receiver);
	addStringField("term",searchTerm() );
  }
  

 
  @Override
  protected final void execute() throws CommandException {
    String t = stringField("term").toLowerCase();
    Collection<Obra> lines = _receiver.searchWorks(); 
    Collection<String> results = lines.stream().filter(o -> o.matches(t))                  
    .sorted(Comparator.comparingInt(Obra::getId))  
    .map(Obra::toString)                            
    .collect(Collectors.toList());
    for (String result : results) {
      _display.addLine(result);
    }
    _display.display();
  }

}