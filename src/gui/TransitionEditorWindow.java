package gui;

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

import java.awt.Window;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JPanel;

import locale.UTF8Control;
import display.DrawArea;
import entities.StateObj;
import entities.TransitionObj;

/**
 * The Edge Editor is simply a general editor in which we have set the central
 * panel to be the edge editor panel
 */
@SuppressWarnings("serial")
public class TransitionEditorWindow extends GeneralEditorWindow {
  private static final ResourceBundle locale =
      ResourceBundle.getBundle("locale.Editors", new UTF8Control());

  public TransitionEditorWindow(Window parent, String name,
      DrawArea draw_area, TransitionObj transition, Vector<StateObj> states,
      boolean is_loopback, StateObj state) {
    super(parent, name, new JPanel());
    JPanel content = new TransitionEditorPanel(this, draw_area, transition,
        states,
        is_loopback, state);
    setPanel(content);
    display();
  }

  public TransitionEditorWindow(Window parent, DrawArea draw_area,
      TransitionObj transition, Vector<StateObj> states,
      boolean is_loopback, StateObj state) {
    this(parent, locale.getString("trans_editor_title"), draw_area, transition,
        states, is_loopback, state);
  }
}
