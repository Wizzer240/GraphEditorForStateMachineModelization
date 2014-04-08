package tests;

/*
 Copyright 2014 Jean-Baptiste Lespiau jeanbaptiste.lespiau@gmail.com

 This file is part of Fizzim.

 Fizzim is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 3 of the License, or
 (at your option) any later version.

 Fizzim is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import gui.EdgeEditorWindow;
import gui.GeneralEditorWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

@SuppressWarnings("serial")
public class TestGUI extends JFrame {
  private final Action action = new SwingAction();
  JFrame frame;

  public TestGUI(String name) {
    super(name);
    frame = this;
    JButton btnShow = new JButton("Show");
    btnShow.setAction(action);
    getContentPane().add(btnShow, BorderLayout.CENTER);
  }

  public static void main(String[] args) {
    JFrame frame = new TestGUI("FrameDemo");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JLabel emptyLabel = new JLabel("");
    emptyLabel.setPreferredSize(new Dimension(175, 100));
    frame.getContentPane().add(emptyLabel, BorderLayout.EAST);

    // Display the window.
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

  }

  private class SwingAction extends AbstractAction {
    public SwingAction() {
      putValue(NAME, "SwingAction");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }

    public void actionPerformed(ActionEvent e) {
      GeneralEditorWindow window = new EdgeEditorWindow(frame, "Edge Editor");
      window.setVisible(true);

      // JPanel panel = new EdgeEditorPanel();
      /*
       * JPanel panel = new PropertyPanel();
       * EditorWindow f2 = new EditorWindow(frame, "Edit State2");
       * f2.setPanel(panel);
       */
    }
  }
}
