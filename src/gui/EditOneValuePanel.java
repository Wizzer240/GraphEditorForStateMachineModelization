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

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import javax.swing.border.EtchedBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * A simple panel with a label and a text area to edit a value
 * 
 * @details Developed using WindowBuilder
 */
@SuppressWarnings("serial")
public class EditOneValuePanel extends JPanel {

  public EditOneValuePanel(String text) {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 47, 334, 0 };
    gridBagLayout.rowHeights = new int[] { 49, 0 };
    gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
    gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
    setLayout(gridBagLayout);

    /* Label */
    JLabel lblEvents = new JLabel(text);
    GridBagConstraints gbc_lblEvents = new GridBagConstraints();
    gbc_lblEvents.anchor = GridBagConstraints.NORTHWEST;
    gbc_lblEvents.insets = new Insets(0, 0, 5, 0);
    gbc_lblEvents.gridx = 0;
    gbc_lblEvents.gridy = 0;
    add(lblEvents, gbc_lblEvents);
    // setPreferredSize(new Dimension(450, 72));

    /* Text Area */
    JTextArea events_textArea = new JTextArea();
    events_textArea.setLineWrap(true);
    events_textArea
        .setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    events_textArea.setRows(3);
    events_textArea.setColumns(30);
    GridBagConstraints gbc_events_textArea = new GridBagConstraints();
    gbc_events_textArea.anchor = GridBagConstraints.NORTHWEST;
    gbc_events_textArea.insets = new Insets(0, 0, 5, 0);
    gbc_events_textArea.gridx = 1;
    gbc_events_textArea.gridy = 0;
    gbc_events_textArea.fill = GridBagConstraints.BOTH;
    gbc_events_textArea.weightx = 1;
    gbc_events_textArea.weighty = 1;
    add(events_textArea, gbc_events_textArea);
  }
}
