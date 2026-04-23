package bci.app.main;

import bci.core.LibraryManager;
import bci.app.exception.FileOpenFailedException;
import bci.core.exception.MissingFileAssociationException;
import bci.core.exception.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.io.IOException;

class DoOpenFile extends Command<LibraryManager> {

  DoOpenFile(LibraryManager receiver) {
    super(Label.OPEN_FILE, receiver);
}

@Override
protected final void execute() throws CommandException {
    // Primeiro tenta guardar se houver modificações
    if (_receiver.getModified() && Form.confirm(Prompt.saveBeforeExit())) {
        try {
            _receiver.save(); // lança MissingFileAssociationException ou IOException
        } catch (MissingFileAssociationException e) {
            // Não há ficheiro associado → pede nome e usa saveAs
            String filename = Form.requestString(Prompt.newSaveAs());
            try {
                _receiver.saveAs(filename); // lança IOException
            } catch (IOException ex) {
                _display.popup("Erro ao guardar: " + ex.getMessage());
                return;
            }
        } catch (IOException e) {
            _display.popup("Erro ao guardar: " + e.getMessage());
            return;
        }
    }

    // Depois abre o ficheiro
    String filename = Form.requestString(Prompt.openFile());
    try {
        _receiver.load(filename); // lança UnavailableFileException se houver problema
    } catch (UnavailableFileException e) {
        throw new FileOpenFailedException(e); // transforma em exceção do comando
    }
}








}
