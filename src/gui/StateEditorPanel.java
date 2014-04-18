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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import attributes.ObjAttribute;
import display.DrawArea;
import display.StatePropertiesPanel;
import entities.StateObj;

import javax.swing.JTextField;

/**
 * The edge editor pannel is composed of two tabs:
 * - the first one allows to modify the value of attributes
 * - the second one allows to modify all the fields of all attributes and the
 * color of the edge
 */
@SuppressWarnings("serial")
public class StateEditorPanel extends JPanel {
  private static final ResourceBundle locale =
      ResourceBundle.getBundle("locale.Editors");
  private JTabbedPane tabbedPane;
  private JTextField textField;
  private StatePropertiesPanel second_tab;

  /**
   * Create the panel that is used in the editor of the states
   * 
   * @param window
   *          The parent window used to position opened windows and to retrieve
   *          the ok and cancel bouton
   * @param draw_area
   *          The drawing area to update after modifications
   * @param state
   *          The state being edited
   */
  public StateEditorPanel(GeneralEditorWindow window, DrawArea draw_area,
      StateObj state) {
    setBorder(new EmptyBorder(7, 7, 7, 7));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    add(tabbedPane);

    final JComponent first_tab = new JPanel();
    second_tab = new StatePropertiesPanel(window,
        draw_area, state);

    LinkedList<ObjAttribute> attributes = state.getAttributeList();
    ObjAttribute name_attribute = attributes.get(0);

    String name = (String) name_attribute.get(0);

    if (name.equals("name")) {
      /* For the name, we do put a simple textField */
      first_tab.add(new JLabel("Name: "));
      textField = new JTextField((String) name_attribute.get(1), 10);
      first_tab.add(textField);
      tabbedPane.addChangeListener(new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
          updateData(tabbedPane.getSelectedIndex());
        }
      });
    }

    tabbedPane.addTab(locale.getString("general_tab"), null, first_tab,
        locale.getString("general_tab_description"));

    tabbedPane.addTab(locale.getString("details_tab"), null, second_tab,
        locale.getString("details_tab_description"));

    /*
     * We add the update and cancel actions to the OK and Cancel button of the
     * parent window
     */
    window.getBtnOk().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        updateData(1);
        second_tab.SPOKActionPerformed(evt);
      }
    });

    window.getBtnCancel().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        second_tab.SPCancelActionPerformed(evt);
      }
    });
  }

  /*
   * This updates the given tab with the data of other tabs.
   * 
   * @details This is used when changing tabs
   */
  public void updateData(int tab_selected) {
    if (tab_selected == 0) { // We select the general tab
      /* We force the commit the current cell of tab 2 */
      second_tab.getTable().getCellEditor(0, 1).stopCellEditing();
      /* We get the value of the name field */
      String name =
          (String) second_tab.getTable().getModel().getValueAt(0, 1);
      textField.setText(name);
    } else if (tab_selected == 1) { // We select the details tab
      /* We update the value of the name in the JTable */
      String general_name = textField.getText();
      second_tab.getTable().getModel().setValueAt(general_name, 0, 1);

    }
  }
}
