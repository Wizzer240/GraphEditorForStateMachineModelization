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
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.border.EmptyBorder;

import display.DrawArea;
import display.StatePropertiesPanel;
import entities.StateObj;

/**
 * The edge editor pannel is composed of two tabs:
 * - the first one allows to modify the value of attributes
 * - the second one allows to modify all the fields of all attributes and the
 * color of the edge
 */
@SuppressWarnings("serial")
public class EdgeEditorPanel extends JPanel {
  private JTabbedPane tabbedPane;

  public EdgeEditorPanel(GeneralEditorWindow window, DrawArea draw_area, StateObj state) {
    setBorder(new EmptyBorder(7, 7, 7, 7));
    setLayout(new BorderLayout(0, 0));
    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    add(tabbedPane);

    JComponent simplePanel = new JPanel();
    simplePanel.setLayout(new BoxLayout(simplePanel, BoxLayout.Y_AXIS));

    JPanel panel = new EditOneValuePanel();
    simplePanel.add(panel);
    JPanel panel2 = new EditOneValuePanel();
    simplePanel.add(panel2);

    tabbedPane.addTab("General", null, simplePanel, "Only values editing");
    JComponent simplePanel2 = new StatePropertiesPanel(window, draw_area, state);
    tabbedPane.addTab("Details", null, simplePanel2, "Only values editing");
  }

}
