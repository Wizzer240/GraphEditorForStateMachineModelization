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

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.border.EmptyBorder;

import attributes.ObjAttribute;
import display.DrawArea;
import display.TransPropertiesPanel;
import entities.StateObj;
import entities.TransitionObj;

/**
 * The edge editor pannel is composed of two tabs:
 * - the first one allows to modify the value of attributes
 * - the second one allows to modify all the fields of all attributes and the
 * color of the edge
 */
@SuppressWarnings("serial")
public class TransitionEditorPanel extends JPanel {
  private static final ResourceBundle locale =
      ResourceBundle.getBundle("locale.Editors");
  private JTabbedPane tabbedPane;

  /**
   * Create the panel that is used in the editor of the states
   * 
   * @param window
   *          The parent window used to position opened windows and to retrieve
   *          the ok and cancel bouton
   * @param draw_area
   *          The drawing area to update after modifications
   * @param transition
   *          The edge being edited
   */
  public TransitionEditorPanel(GeneralEditorWindow window, DrawArea draw_area,
      TransitionObj transition, Vector<StateObj> states, boolean is_loopback, StateObj state) {
    setBorder(new EmptyBorder(7, 7, 7, 7));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    add(tabbedPane);

    JComponent first_tab = new JPanel();
    first_tab.setLayout(new BoxLayout(first_tab, BoxLayout.Y_AXIS));

    LinkedList<ObjAttribute> attributes = transition.getAttributeList();
    for (ObjAttribute one_attribute : attributes) {
      String name = (String) one_attribute.get(0);
      String value = (String) one_attribute.get(1);

      JPanel panel = new EditOneValuePanel(name + ": ", value);
      first_tab.add(panel);

    }

    tabbedPane.addTab(locale.getString("general_tab"), null, first_tab,
        locale.getString("general_tab_description"));

    JComponent second_tab = new TransPropertiesPanel(window, draw_area, transition, states, is_loopback, state);
    tabbedPane.addTab(locale.getString("details_tab"), null, second_tab,
        locale.getString("details_tab_description"));
  }
}
