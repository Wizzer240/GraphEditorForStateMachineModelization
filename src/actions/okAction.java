package actions;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class okAction extends AbstractAction {
  Window window;

  public okAction(Window w, String name, String short_description) {
    window = w;
    putValue(NAME, name);
    putValue(SHORT_DESCRIPTION, short_description);
  }

  public void actionPerformed(ActionEvent e) {
    window.dispose();
  }
}