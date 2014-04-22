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

import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import locale.UTF8Control;
import display.DrawArea;
import entities.StateObj;

/**
 * The Edge Editor is simply a general editor in which we have set the central
 * panel to be the edge editor panel
 */
@SuppressWarnings("serial")
public class StateEditorWindow extends GeneralEditorWindow {
  private static final ResourceBundle locale =
      ResourceBundle.getBundle("locale.Editors", new UTF8Control());

  public StateEditorWindow(Component parent, String name,
      DrawArea draw_area, StateObj state) {
    super(parent, name, new JPanel());
    JPanel content = new StateEditorPanel(this, draw_area, state);
    setPanel(content);
    display();
  }

  public StateEditorWindow(Component parent, DrawArea draw_area, StateObj state) {
    this(parent, locale.getString("state_editor_title"), draw_area, state);
  }
}
