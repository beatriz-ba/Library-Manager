package bci.app.user;

import bci.core.LibraryManager;
import bci.core.Notificacao;
import bci.core.exception.NoSuchUserExceptionCore;

import java.util.Collection;

import bci.app.exception.NoSuchUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

  DoShowUserNotifications(LibraryManager receiver) {
    super(Label.SHOW_USER_NOTIFICATIONS, receiver);
    addIntegerField("id", Prompt.userId());
  }

  @Override
  protected final void execute() throws CommandException {
    int id = integerField("id");
    try {
      for (Notificacao notification : _receiver.showNotificationsUser(id)) {
        _display.addLine(notification.getMensagem());
      }
      _display.display();
    } catch (NoSuchUserExceptionCore e) {
      throw new NoSuchUserException(id);
    }
  }
}
