package display;

/*
 Copyright 2007 Zimmer Design Services.
 Written by Michael Zimmer - mike@zimmerdesignservices.com
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import java.util.ResourceBundle;

import locale.UTF8Control;

/**
 * The Properties JDialog to be able to edit the configurations.
 */
@SuppressWarnings("serial")
public class PreferencesWindow extends JDialog {

  private static final ResourceBundle BUNDLE =
      ResourceBundle.getBundle("locale.Preferences", new UTF8Control()); //$NON-NLS-1$

  private DrawArea drawArea;
  private JColorChooser colorChooser;
  private Component window = this;

  private Color tempSC;
  private Color tempSTC;
  private Color tempLTC;

  /** Creates new form Pref */
  public PreferencesWindow(Frame parent, DrawArea da) {
    super(parent, true);
    drawArea = da;
    tempSC = drawArea.getDefSC();
    tempSTC = drawArea.getDefSTC();
    tempLTC = drawArea.getDefLTC();
    this.setTitle(BUNDLE.getString("preferences_title")); //$NON-NLS-1$
    colorChooser = drawArea.getColorChooser();
    initComponents();
    setLocationRelativeTo(parent);
  }

  private Color getColor(String type) {
    if (type.equals("SC")) {
      return tempSC;
    } else if (type.equals("STC")) {
      return tempSTC;
    } else if (type.equals("LTC")) {
      return tempLTC;
    } else {
      return Color.black;
    }
  }

  private void setColor(String type, Color c) {
    if (type.equals("SC")) {
      tempSC = c;
    } else if (type.equals("STC")) {
      tempSTC = c;
    } else if (type.equals("LTC")) {
      tempLTC = c;
    }
  }

  private void setUpColorBox(JLabel jLabel, String type) {
    jLabel.setPreferredSize(new Dimension(50, 20));
    jLabel.setMinimumSize(new Dimension(50, 20));
    jLabel.setOpaque(true);
    jLabel.setVisible(true);
    jLabel.setBackground(getColor(type));
    jLabel.setBorder(new LineBorder(Color.black, 1));
  }

  // GEN-BEGIN:variables
  // Variables declaration - do not modify
  private JComboBox<String> drawAreaFontComboBox;
  private JComboBox<String> drawAreaFontSizeComboBox;
  private JButton fontColorButton;
  private JComboBox<String> tableFontComboBox;
  private JComboBox<String> tableFontSizeComboBox;
  private JPanel globalTablePanel;
  private JCheckBox gridCheckbox;
  private JTextField gridSTextField;
  private JTextField lineWidthTextField;
  private JButton jButton1;
  private JButton jButton2;
  private JLabel jLabel1; // "space between columns"
  private JLabel jLabel2; // space between columns "pixels"
  private JLabel jLabel3; // "Table Font"
  private JLabel jLabel5; // "Font" in Draw Area box
  private JLabel gridSize; // ?
  private JLabel jLabel8; // grid size "pixels"
  private JLabel jLabel9; // default state width "pixels"
  private JLabel jLabel10; // default state height "pixels"
  private JLabel lineWidth; // "Line Width"
  private JLabel pixels; // lineWidth "pixels"
  private JPanel jPanel2;
  private JTextField spaceTextField;
  private JCheckBox tableVisCheckbox;
  private JLabel SC1;
  private JLabel SC2;
  private JLabel STC1;
  private JLabel STC2;
  private JLabel LTC1;
  private JLabel LTC2;
  private JFormattedTextField SPHField;
  private JLabel SPW;
  private JLabel SPH;
  private JFormattedTextField SPWField;

  // End of variables declaration//GEN-END:variables

  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // GEN-BEGIN:initComponents
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
  private void initComponents() {
    globalTablePanel = new JPanel();
    tableVisCheckbox = new JCheckBox();
    jLabel1 = new JLabel();
    spaceTextField = new JTextField();
    jLabel2 = new JLabel();
    jLabel3 = new JLabel();
    tableFontComboBox = new JComboBox<String>();
    tableFontSizeComboBox = new JComboBox<String>();
    fontColorButton = new JButton();
    jPanel2 = new JPanel();
    jLabel5 = new JLabel();
    drawAreaFontComboBox = new JComboBox<String>();
    drawAreaFontSizeComboBox = new JComboBox<String>();
    gridCheckbox = new JCheckBox();
    gridSize = new JLabel();
    gridSTextField = new JTextField();
    lineWidthTextField = new JTextField();
    lineWidth = new JLabel();
    pixels = new JLabel();
    jLabel8 = new JLabel();
    jLabel9 = new JLabel();
    jLabel10 = new JLabel();
    jButton1 = new JButton();
    jButton2 = new JButton();
    SC1 = new JLabel();
    SC2 = new JLabel();
    STC1 = new JLabel();
    STC2 = new JLabel();
    LTC1 = new JLabel();
    LTC2 = new JLabel();
    SPW = new JLabel();
    SPH = new JLabel();
    SPWField = new JFormattedTextField(NumberFormat
        .getIntegerInstance());
    SPHField = new JFormattedTextField(NumberFormat
        .getIntegerInstance());

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    globalTablePanel
        .setBorder(BorderFactory.createTitledBorder("Global Table"));
    tableVisCheckbox.setSelected(drawArea.getTableVis());
    tableVisCheckbox.setText(BUNDLE
        .getString("preferences_table_visible_checkbox")); //$NON-NLS-1$
    tableVisCheckbox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    tableVisCheckbox.setMargin(new Insets(0, 0, 0, 0));

    jLabel1.setText(BUNDLE.getString("preferences_space_between_columns")); //$NON-NLS-1$

    spaceTextField.setText(String.valueOf(drawArea.getSpace()));

    jLabel2.setText(BUNDLE.getString("preferences_pixels")); //$NON-NLS-1$

    jLabel3.setText(BUNDLE.getString("preferences_table_font")); //$NON-NLS-1$

    // default state size
    SPW.setText(BUNDLE.getString("preferences_default_state_width")); //$NON-NLS-1$
    SPH.setText(BUNDLE.getString("preferences_default_state_height")); //$NON-NLS-1$
    SPWField.setValue(new Integer(drawArea.getStateW()));
    SPWField.setColumns(4);
    SPHField.setValue(new Integer(drawArea.getStateH()));
    SPHField.setColumns(4);

    // default colors
    SC1.setText(BUNDLE.getString("preferences_default_state_color")); //$NON-NLS-1$
    STC1.setText(BUNDLE.getString("preferences_default_transition_color")); //$NON-NLS-1$
    LTC1.setText(BUNDLE.getString("preferences_default_loopback_color")); //$NON-NLS-1$

    setUpColorBox(SC2, "SC");
    setUpColorBox(STC2, "STC");
    setUpColorBox(LTC2, "LTC");

    SC2.addMouseListener(new MouseListener() {

      // JColorChooser colorChooser = new JColorChooser();
      ActionListener colorSel = new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          SC2.setBackground(colorChooser.getColor());
          setColor("SC", colorChooser.getColor());
        }
      };

      public void mouseClicked(MouseEvent e)
      {
        JDialog dialog;
        dialog = JColorChooser.createDialog(window, "Choose Color", true,
            colorChooser, colorSel, null);
        dialog.setVisible(true);
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }
    });

    STC2.addMouseListener(new MouseListener() {

      // JColorChooser colorChooser = new JColorChooser();
      ActionListener colorSel = new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          STC2.setBackground(colorChooser.getColor());
          setColor("STC", colorChooser.getColor());
        }
      };

      public void mouseClicked(MouseEvent e)
      {
        JDialog dialog;
        dialog = JColorChooser.createDialog(window, "Choose Color", true,
            colorChooser, colorSel, null);
        dialog.setVisible(true);
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }
    });

    LTC2.addMouseListener(new MouseListener() {

      // JColorChooser colorChooser = new JColorChooser();
      ActionListener colorSel = new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          LTC2.setBackground(colorChooser.getColor());
          setColor("LTC", colorChooser.getColor());
        }
      };

      public void mouseClicked(MouseEvent e)
      {
        JDialog dialog;
        dialog = JColorChooser.createDialog(window, "Choose Color", true,
            colorChooser, colorSel, null);
        dialog.setVisible(true);
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }
    });

    // get list of fonts
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] fontNames = env.getAvailableFontFamilyNames();

    tableFontComboBox.setModel(new DefaultComboBoxModel<String>(fontNames));
    tableFontComboBox.setSelectedItem(drawArea.getTableFont().getFontName());

    tableFontSizeComboBox.setModel(new DefaultComboBoxModel<String>(
        new String[] { "8", "9", "10", "11", "12", "14", "16", "18", "20",
            "22", "24", "30", "36", "42", "48", "72" }));
    tableFontSizeComboBox.setSelectedItem(String.valueOf(drawArea
        .getTableFont()
        .getSize()));

    fontColorButton.setBackground(drawArea.getTableColor());
    // fontColorButton.setAlignmentX(0.5F);
    // fontColorButton.setOpaque(true);
    fontColorButton.setPreferredSize(new Dimension(20, 20));
    fontColorButton.setBorder(new LineBorder(Color.black, 1));
    fontColorButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        fontColorButtonActionPerformed(evt);
      }
    });

    GroupLayout globalTablePanelLayout = new GroupLayout(globalTablePanel);
    globalTablePanel.setLayout(globalTablePanelLayout);
    globalTablePanelLayout
        .setHorizontalGroup(globalTablePanelLayout
            .createParallelGroup(Alignment.LEADING)
            .addGroup(globalTablePanelLayout
                .createSequentialGroup()
                .addContainerGap()
                .addGroup(globalTablePanelLayout
                    .createParallelGroup(Alignment.LEADING)
                    .addComponent(tableVisCheckbox)
                    .addGroup(globalTablePanelLayout
                        .createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(spaceTextField,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(globalTablePanelLayout
                        .createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(
                            ComponentPlacement.RELATED)
                        .addComponent(tableFontComboBox,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(tableFontSizeComboBox,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(
                            ComponentPlacement.RELATED)
                        .addComponent(fontColorButton,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(136, Short.MAX_VALUE)));
    globalTablePanelLayout.setVerticalGroup(globalTablePanelLayout
        .createParallelGroup(Alignment.LEADING)
        .addGroup(globalTablePanelLayout
            .createSequentialGroup()
            .addComponent(tableVisCheckbox)
            .addPreferredGap(
                ComponentPlacement.RELATED)
            .addGroup(globalTablePanelLayout
                .createParallelGroup(Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(spaceTextField,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(globalTablePanelLayout
                .createParallelGroup(Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(tableFontComboBox,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE)
                .addComponent(tableFontSizeComboBox,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE)
                .addComponent(fontColorButton,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    jPanel2.setBorder(BorderFactory
        .createTitledBorder("Draw Area"));
    jLabel5.setText(BUNDLE.getString("preferences_font")); //$NON-NLS-1$

    drawAreaFontComboBox.setModel(new DefaultComboBoxModel<String>(fontNames));
    drawAreaFontComboBox.setSelectedItem(drawArea.getFont().getFontName());
    drawAreaFontSizeComboBox.setModel(new DefaultComboBoxModel<String>(
        new String[] { "8", "9", "10", "11", "12", "14", "16", "18", "20",
            "22", "24", "30", "36", "42", "48", "72" }));
    drawAreaFontSizeComboBox.setSelectedItem(String.valueOf(drawArea
        .getFont()
        .getSize()));

    gridCheckbox.setSelected(drawArea.getGrid());
    gridCheckbox.setText(BUNDLE.getString("preferences_grid")); //$NON-NLS-1$
    gridCheckbox.setBorder(BorderFactory.createEmptyBorder(0,
        0, 0, 0));
    gridCheckbox.setMargin(new Insets(0, 0, 0, 0));

    gridSize.setText(BUNDLE.getString("preferences_grid_size")); //$NON-NLS-1$

    gridSTextField.setText(String.valueOf(drawArea.getGridSpace()));

    lineWidth.setText(BUNDLE.getString("preferences_line_width")); //$NON-NLS-1$
    pixels.setText(BUNDLE.getString("preferences_pixels")); //$NON-NLS-1$

    lineWidthTextField.setText(String.valueOf(drawArea.getLineWidth()));
    lineWidthTextField.setColumns(2);

    // jLabel7.setText("Grid Size:");

    // gridSTextField.setText(String.valueOf(drawArea.getGridSpace()));

    jLabel8.setText(BUNDLE.getString("preferences_pixels")); //$NON-NLS-1$
    jLabel9.setText(BUNDLE.getString("preferences_pixels")); //$NON-NLS-1$
    jLabel10.setText(BUNDLE.getString("preferences_pixels")); //$NON-NLS-1$
    GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(jPanel2Layout.
        createParallelGroup(Alignment.LEADING).addGroup(jPanel2Layout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
                .addGroup(jPanel2Layout
                    .createSequentialGroup()
                    .addComponent(jLabel5)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(drawAreaFontComboBox,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(drawAreaFontSizeComboBox,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
                .addComponent(gridCheckbox)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(gridSize)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(gridSTextField,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(jLabel8))
                .addGroup(jPanel2Layout
                    .createSequentialGroup()
                    .addComponent(lineWidth)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lineWidthTextField,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(pixels))
                .addGroup(jPanel2Layout
                    .createSequentialGroup()
                    .addComponent(SPW)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(SPWField,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(jLabel9))
                .addGroup(jPanel2Layout
                    .createSequentialGroup()
                    .addComponent(SPH)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(SPHField,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(jLabel10))
                .addGroup(jPanel2Layout
                    .createSequentialGroup()
                    .addComponent(SC1)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(SC2))
                .addGroup(jPanel2Layout
                    .createSequentialGroup()
                    .addComponent(STC1)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(STC2))
                .addGroup(jPanel2Layout
                    .createSequentialGroup()
                    .addComponent(LTC1)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(LTC2)))
            .addContainerGap(208, Short.MAX_VALUE)));

    jPanel2Layout.setVerticalGroup(jPanel2Layout
        .createParallelGroup(Alignment.LEADING)
        .addGroup(
            jPanel2Layout
                .createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(drawAreaFontComboBox,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(drawAreaFontSizeComboBox,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(gridCheckbox)
                .addPreferredGap(ComponentPlacement.RELATED,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(gridSize)
                    .addComponent(gridSTextField,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(lineWidth)
                    .addComponent(lineWidthTextField,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(pixels))
                .addPreferredGap(ComponentPlacement.RELATED,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(SPW)
                    .addComponent(SPWField,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(ComponentPlacement.RELATED,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(SPH)
                    .addComponent(SPHField,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(ComponentPlacement.RELATED,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(SC1)
                    .addComponent(SC2))
                .addPreferredGap(ComponentPlacement.RELATED,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(STC1)
                    .addComponent(STC2))
                .addPreferredGap(ComponentPlacement.RELATED,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(LTC1)
                    .addComponent(LTC2))
                .addContainerGap()));

    jButton1.setText(BUNDLE.getString("preferences_cancel")); //$NON-NLS-1$
    jButton1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    jButton2.setText(BUNDLE.getString("preferences_ok")); //$NON-NLS-1$
    jButton2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
        .addGroup(layout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(jPanel2,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addComponent(globalTablePanel,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addGroup(Alignment.TRAILING,
                    layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
            .addContainerGap()));
    layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(globalTablePanel,
                GroupLayout.PREFERRED_SIZE,
                GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(jPanel2,
                GroupLayout.PREFERRED_SIZE,
                GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(jButton1).addComponent(jButton2))
            .addContainerGap()));
    pack();
  }// </editor-fold>//GEN-END:initComponents

  ActionListener okListener = new ActionListener() {
    // Called when user clicks ok
    public void actionPerformed(ActionEvent evt) {
      // Note: The source is an internal button in the dialog
      // and should not be used

      // Get selected color
      fontColorButton.setBackground(colorChooser.getColor());
      // fontColorButton.
    }
  };

  protected void fontColorButtonActionPerformed(ActionEvent evt) {

    // colorChooser = new JColorChooser();
    colorChooser.setColor(fontColorButton.getBackground());
    // colorChooser.setVisible(true);
    JDialog dialog = JColorChooser.createDialog(fontColorButton,
        "Pick a Color",
        true,  // modal
        colorChooser,
        okListener,  // OK button handler
        null); // no CANCEL button handler

    dialog.setVisible(true);

    // Make the renderer reappear.
    // fireEditingStopped();

  }

  // GEN-FIRST:event_jButton1ActionPerformed
  private void jButton1ActionPerformed(ActionEvent evt) {
    dispose();
  }// GEN-LAST:event_jButton1ActionPerformed

  // GEN-FIRST:event_jButton2ActionPerformed
  private void jButton2ActionPerformed(ActionEvent evt) {

    drawArea.setTableVis(tableVisCheckbox.isSelected());
    try {
      drawArea.setSpace(Integer.parseInt(spaceTextField.getText()));
    } catch (NumberFormatException nfe) {
      drawArea.setSpace(20);
    }
    drawArea.setTableFont(new Font(
        (String) tableFontComboBox.getSelectedItem(),
        Font.PLAIN, Integer.parseInt((String) tableFontSizeComboBox
            .getSelectedItem())));

    drawArea.setTableColor(fontColorButton.getBackground());

    drawArea.setFont(new Font((String) drawAreaFontComboBox.getSelectedItem(),
        Font.PLAIN, Integer.parseInt((String) drawAreaFontSizeComboBox
            .getSelectedItem())));

    drawArea.setLineWidth(Integer.parseInt(lineWidthTextField.getText()));
    try {
      drawArea.setGrid(gridCheckbox.isSelected(), Integer
          .parseInt(gridSTextField.getText()));
    } catch (NumberFormatException nfe) {
      drawArea.setGrid(gridCheckbox.isSelected(), 25);
    }

    drawArea.setDefSC(tempSC);
    drawArea.setDefSTC(tempSTC);
    drawArea.setDefLTC(tempLTC);
    drawArea.setStateW(Integer.parseInt(SPWField.getText()));
    drawArea.setStateH(Integer.parseInt(SPHField.getText()));
    drawArea.updateGlobalTable();

    dispose();
  }// GEN-LAST:event_jButton2ActionPerformed

}
