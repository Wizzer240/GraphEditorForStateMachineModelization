package display;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.AbstractCellEditor;

import attributes.EnumVisibility;
import attributes.ObjAttribute;

//Written by: Michael Zimmer - mike@zimmerdesignservices.com

/*
 Copyright 2007 Zimmer Design Services

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

class MyJColorRenderer extends JLabel implements TableCellRenderer {

  public MyJColorRenderer() {
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable arg0, Object arg1,
      boolean arg2, boolean arg3, int arg4, int arg5) {
    Color newColor = (Color) arg1;
    setBackground(newColor);
    return this;
  }

}

// http://java.sun.com/docs/books/tutorial/uiswing/components/table.html

class MyJColorEditor extends AbstractCellEditor implements ActionListener,
    TableCellEditor {

  Color currColor;
  JButton button;
  JColorChooser colorChooser;
  JDialog dialog;

  public MyJColorEditor(JColorChooser c)
  {
    button = new JButton();
    button.setActionCommand("edit");
    button.addActionListener(this);
    button.setBorderPainted(false);

    colorChooser = new JColorChooser();
    dialog = JColorChooser.createDialog(button,
        "Pick a Color",
        true,  // modal
        colorChooser,
        this,  // OK button handler
        null); // no CANCEL button handler

  }

  public Object getCellEditorValue() {
    return currColor;
  }

  public void actionPerformed(ActionEvent e) {
    if ("edit".equals(e.getActionCommand())) {
      button.setBackground(currColor);
      colorChooser.setColor(currColor);
      dialog.setVisible(true);

      // Make the renderer reappear.
      fireEditingStopped();

    } else { // User pressed dialog's "OK" button.
      currColor = colorChooser.getColor();
    }

  }

  public Component getTableCellEditorComponent(JTable arg0, Object arg1,
      boolean arg2, int arg3, int arg4) {
    currColor = (Color) arg1;
    return button;
  }

}

class MyJComboBoxEditor extends DefaultCellEditor {
  public MyJComboBoxEditor(Object[] items) {
    super(new JComboBox(items));
  }
}

/**
 * 
 * @author __USER__
 */
class GlobalProperties extends javax.swing.JDialog {

  LinkedList<LinkedList<ObjAttribute>> globalLists;
  DrawArea drawArea;

  EnumVisibility[] options = EnumVisibility.values();
  // pz
  // String[] outputOptions = new String[] {"reg","comb","regdp"};
  // String[] outputOptions = new String[] {"reg","comb","regdp","flag"};
  String[] outputOptions = new String[] { "statebit", "comb", "regdp", "flag" };
  String[] reset_signal = new String[] { "posedge", "negedge", "positive",
      "negative" };
  MyJComboBoxEditor reset_signal_editor = new MyJComboBoxEditor(reset_signal);
  String[] clock = new String[] { "posedge", "negedge" };
  MyJComboBoxEditor clock_editor = new MyJComboBoxEditor(clock);
  String[] resetType = new String[] { "allzeros", "allones", "anyvalue" };
  MyJComboBoxEditor resetType_editor = new MyJComboBoxEditor(resetType);
  String[] stateObjs;
  MyJComboBoxEditor stateSelect_editor;
  private JTable currTable = null;
  private int currTab = 0;
  int[] editable = { ObjAttribute.GLOBAL_FIXED, ObjAttribute.GLOBAL_VAR,
      ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR,
      ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR,
      ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR };
  int[] editable2 = { ObjAttribute.ABS, ObjAttribute.GLOBAL_VAR,
      ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR,
      ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR,
      ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR };

  JColorChooser colorChooser;

  /** Creates new form GlobalP */
  public GlobalProperties(DrawArea DA, java.awt.Frame parent, boolean modal,
      LinkedList<LinkedList<ObjAttribute>> globals, int tab) {
    super(parent, modal);
    globalLists = globals;
    drawArea = DA;
    stateObjs = drawArea.getStateNames();
    stateSelect_editor = new MyJComboBoxEditor(stateObjs);
    colorChooser = drawArea.getColorChooser();
    initComponents();
    GPTabbedPane.setSelectedIndex(tab);
    currTab = tab;
    setTable(tab);

  }

  private void setcolumnwidths(JTable table) {
    TableColumn column;

    // Name
    column = table.getColumnModel().getColumn(0);
    column.setPreferredWidth(40);
    // Default value
    column = table.getColumnModel().getColumn(1);
    column.setPreferredWidth(15);
    // Visibility
    column = table.getColumnModel().getColumn(2);
    column.setPreferredWidth(30);
    // Type
    column = table.getColumnModel().getColumn(3);
    column.setPreferredWidth(10);
    // Comment
    column = table.getColumnModel().getColumn(4);
    column.setPreferredWidth(100);
    // Color
    column = table.getColumnModel().getColumn(5);
    column.setPreferredWidth(5);
    // UserAtts
    column = table.getColumnModel().getColumn(6);
    column.setPreferredWidth(100);
    // Resetval
    column = table.getColumnModel().getColumn(7);
    column.setPreferredWidth(15);
  }

  private void initComponents() {

    GPLabel = new javax.swing.JLabel();
    GPLabel2 = new javax.swing.JLabel();
    GPTabbedPane = new javax.swing.JTabbedPane();
    GPScrollMachine = new javax.swing.JScrollPane();
    GPTableMachine = new javax.swing.JTable()
    {
      public TableCellEditor getCellEditor(int row, int column)
      {
        int modelColumn = convertColumnIndexToModel(column);
        String name = (String) this.getValueAt(row, 0);
        if (modelColumn == 3 && name.equals("reset_signal"))
          return (TableCellEditor) reset_signal_editor;
        else if (modelColumn == 3 && name.equals("clock"))
          return (TableCellEditor) clock_editor;
        else if (modelColumn == 3 && name.equals("reset_state"))
          return (TableCellEditor) resetType_editor;
        else if (modelColumn == 1 && name.equals("reset_state"))
          return (TableCellEditor) stateSelect_editor;
        else
          return super.getCellEditor(row, column);
      }
    };
    GPScrollState = new javax.swing.JScrollPane();
    GPTableState = new javax.swing.JTable();
    GPScrollTrans = new javax.swing.JScrollPane();
    GPTableTrans = new javax.swing.JTable();
    GPScrollInputs = new javax.swing.JScrollPane();
    GPTableInputs = new javax.swing.JTable();
    GPScrollOutputs = new javax.swing.JScrollPane();
    GPTableOutputs = new javax.swing.JTable();
    GPCancel = new javax.swing.JButton();
    GPOK = new javax.swing.JButton();
    GPOption1 = new javax.swing.JButton();
    GPOption2 = new javax.swing.JButton();
    GPOption3 = new javax.swing.JButton();
    GPOption4 = new javax.swing.JButton();
    GPOption5 = new javax.swing.JButton();
    GPOption6 = new javax.swing.JButton();

    setTitle("Edit Global Properties");
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(true); // controls resizability on global attributes table
    setPreferredSize(new java.awt.Dimension(900, 400)); // sets default size of
                                                        // global attributes
                                                        // table

    TableColumn column;

    GPLabel
        .setText("Here you can change the global attributes of all objects.  Once an attribute is added, its default");

    GPLabel2
        .setText("value can be overridden by right clicking on an object and selecting to 'Edit Properties.'");

    GPTableMachine.setModel(new AttributesTableModel(
        (LinkedList<ObjAttribute>) globalLists.get(0), globalLists));
    GPTableMachine
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    column = GPTableMachine.getColumnModel().getColumn(2);
    column.setCellEditor(new MyJComboBoxEditor(options));
    column = GPTableMachine.getColumnModel().getColumn(5);
    // column.setPreferredWidth(GPTableMachine.getRowHeight());
    column.setCellEditor(new MyJColorEditor(colorChooser));
    column.setCellRenderer(new MyJColorRenderer());
    GPScrollMachine.setViewportView(GPTableMachine);
    GPTabbedPane.addTab("State Machine", GPScrollMachine);

    GPTableInputs.setModel(new AttributesTableModel(
        (LinkedList<ObjAttribute>) globalLists.get(1), globalLists));
    GPTableInputs
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    column = GPTableInputs.getColumnModel().getColumn(2);
    column.setCellEditor(new MyJComboBoxEditor(options));
    column = GPTableInputs.getColumnModel().getColumn(5);
    // column.setPreferredWidth(GPTableInputs.getRowHeight());
    column.setCellEditor(new MyJColorEditor(colorChooser));
    column.setCellRenderer(new MyJColorRenderer());
    GPScrollInputs.setViewportView(GPTableInputs);
    GPTabbedPane.addTab("Inputs", GPScrollInputs);

    GPTableOutputs.setModel(new AttributesTableModel(
        (LinkedList<ObjAttribute>) globalLists.get(2), globalLists));
    GPTableOutputs
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    // Visibility
    column = GPTableOutputs.getColumnModel().getColumn(2);
    column.setCellEditor(new MyJComboBoxEditor(options));
    // Type
    column = GPTableOutputs.getColumnModel().getColumn(3);
    column.setCellEditor(new MyJComboBoxEditor(outputOptions));
    // Color
    column = GPTableOutputs.getColumnModel().getColumn(5);
    // column.setPreferredWidth(GPTableOutputs.getRowHeight());
    column.setCellEditor(new MyJColorEditor(colorChooser));
    column.setCellRenderer(new MyJColorRenderer());
    GPScrollOutputs.setViewportView(GPTableOutputs);
    GPTabbedPane.addTab("Outputs", GPScrollOutputs);

    GPTableState.setModel(new AttributesTableModel(
        (LinkedList<ObjAttribute>) globalLists.get(3), globalLists));
    GPTableState
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    column = GPTableState.getColumnModel().getColumn(2);
    column.setCellEditor(new MyJComboBoxEditor(options));
    column = GPTableState.getColumnModel().getColumn(5);
    // column.setPreferredWidth(GPTableState.getRowHeight());
    column.setCellEditor(new MyJColorEditor(colorChooser));
    column.setCellRenderer(new MyJColorRenderer());
    GPScrollState.setViewportView(GPTableState);
    GPTabbedPane.addTab("States", GPScrollState);

    GPTableTrans.setModel(new AttributesTableModel(
        (LinkedList<ObjAttribute>) globalLists.get(4), globalLists));
    GPTableTrans
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    column = GPTableTrans.getColumnModel().getColumn(2);
    column.setCellEditor(new MyJComboBoxEditor(options));
    column = GPTableTrans.getColumnModel().getColumn(5);
    column.setCellEditor(new MyJColorEditor(colorChooser));
    column.setCellRenderer(new MyJColorRenderer());
    GPScrollTrans.setViewportView(GPTableTrans);
    GPTabbedPane.addTab("Transitions", GPScrollTrans);

    // set default column widths
    setcolumnwidths(GPTableMachine);
    setcolumnwidths(GPTableInputs);
    setcolumnwidths(GPTableOutputs);
    setcolumnwidths(GPTableState);
    setcolumnwidths(GPTableTrans);

    GPTabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e)
      {
        GPTabbedPaneActionPerformed(e);
      }
    });

    GPCancel.setText("Cancel");
    GPCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GPCancelActionPerformed(evt);
      }
    });

    GPOK.setText("OK");
    GPOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GPOKActionPerformed(evt);
      }
    });

    GPOption1.setText("Delete");
    GPOption1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GPOption1ActionPerformed(evt);
      }
    });

    GPOption2.setText("User");
    GPOption2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GPOption2ActionPerformed(evt);
      }
    });

    GPOption3.setText("Option3");
    GPOption3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GPOption3ActionPerformed(evt);
      }
    });

    GPOption4.setText("Option4");
    GPOption4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GPOption4ActionPerformed(evt);
      }
    });

    GPOption5.setText("Option5");
    GPOption5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GPOption5ActionPerformed(evt);
      }
    });

    GPOption6.setText("Option6");
    GPOption6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GPOption6ActionPerformed(evt);
      }
    });
    GPOption6.setVisible(false);

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
        getContentPane());
    getContentPane().setLayout(layout);
    layout
        .setHorizontalGroup(layout
            .createParallelGroup(
                org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        layout
                            .createParallelGroup(
                                org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        GPOK)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        GPCancel))
                            .add(
                                GPTabbedPane,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                480,
                                Short.MAX_VALUE)
                            .add(GPLabel)
                            .add(GPLabel2)
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        GPOption1)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        GPOption2)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        GPOption3)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        GPOption4)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        GPOption5)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        GPOption6)))
                    .addContainerGap()));
    layout
        .setVerticalGroup(layout
            .createParallelGroup(
                org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(GPLabel)
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(GPLabel2)
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        GPTabbedPane,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        179,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(
                                org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(GPOption1).add(
                                GPOption2).add(
                                GPOption3).add(GPOption4).add(GPOption5).add(
                                GPOption6))
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED,
                        40, Short.MAX_VALUE)
                    .add(
                        layout
                            .createParallelGroup(
                                org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(GPCancel)
                            .add(GPOK))
                    .addContainerGap()));
    pack();
  }// </editor-fold>//GEN-END:initComponents

  protected void GPTabbedPaneActionPerformed(ChangeEvent e) {
    int tab = GPTabbedPane.getSelectedIndex();
    currTab = tab;
    setTable(tab);
    currTable.revalidate();

  }

  private void setTable(int tab)
  {
    if (tab == 0)
    {
      currTable = GPTableMachine;
      GPOption3.setVisible(true);
      GPOption3.setText("Reset");
      if (checkNames(currTable, "reset_state")
          && checkNames(currTable, "reset_signal"))
        GPOption3.setEnabled(false);
      else
        GPOption3.setEnabled(true);
      GPOption4.setVisible(false);
      GPOption5.setVisible(false);
      GPOption6.setVisible(false);
    }
    if (tab == 1)
    {
      currTable = GPTableInputs;
      GPOption3.setVisible(true);
      GPOption3.setEnabled(true);
      GPOption3.setText("Input");
      GPOption4.setVisible(true);
      GPOption4.setText("Multibit Input");
      GPOption5.setVisible(false);
      GPOption6.setVisible(false);
    }
    if (tab == 2)
    {
      currTable = GPTableOutputs;
      GPOption3.setVisible(true);
      GPOption3.setEnabled(true);
      GPOption3.setText("Output");
      GPOption4.setVisible(true);
      GPOption4.setText("Multibit Output");
      GPOption5.setVisible(true);
      GPOption5.setText("Flag");
      GPOption6.setVisible(false);
    }
    if (tab == 3)
    {
      currTable = GPTableState;
      GPOption3.setVisible(false);
      GPOption4.setVisible(false);
      GPOption5.setVisible(false);
      GPOption6.setVisible(false);
    }
    if (tab == 4)
    {
      currTable = GPTableTrans;
      GPOption3.setVisible(true);
      GPOption3.setText("Graycode");
      if (checkNames(currTable, "graycode"))
        GPOption3.setEnabled(false);
      else
        GPOption3.setEnabled(true);
      GPOption4.setVisible(true);
      GPOption4.setText("Output");
      GPOption5.setVisible(true);
      GPOption5.setText("Priority");
      if (checkNames(currTable, "priority"))
        GPOption5.setEnabled(false);
      else
        GPOption5.setEnabled(true);
      GPOption6.setVisible(false);

    }
  }

  // GEN-FIRST:event_GPNewActionPerformed
  private void GPOption1ActionPerformed(java.awt.event.ActionEvent evt) {

    int[] rows = currTable.getSelectedRows();
    int tab1 = GPTabbedPane.getSelectedIndex();
    LinkedList<ObjAttribute> list = globalLists.get(tab1);
    for (int i = rows.length - 1; i > -1; i--)
    {
      ObjAttribute obj = list.get(rows[i]);
      if (obj.getEditable(0) != ObjAttribute.ABS)
      {
        if ((obj.getName().equals("reset_signal") || obj.getName().equals(
            "reset_state"))
            && currTab == 0)
          GPOption3.setEnabled(true);
        if (obj.getName().equals("graycode") && currTab == 4)
          GPOption3.setEnabled(true);
        if (obj.getName().equals("priority") && currTab == 4)
          GPOption5.setEnabled(true);

        // if output being deleted, delete in states and trans
        if (currTab == 2 && obj.getType().equals("reg"))
        {
          removeAttribute(3, obj.getName());
        }
        if (currTab == 2 && obj.getType().equals("regdp"))
        {
          removeAttribute(3, obj.getName());
        }
        if (currTab == 2 && obj.getType().equals("comb"))
        {
          removeAttribute(3, obj.getName());
          // removeAttribute(2,obj.getName());
        }
        if (currTab == 2 && obj.getType().equals("flag"))
        {
          removeAttribute(3, obj.getName());
        }
        if (obj.getType().equals("output")
            && checkNames(GPTableOutputs, obj.getName()) && currTab != 2)
        {
          JOptionPane.showMessageDialog(this,
              "Must remove from outputs tab",
              "error",
              JOptionPane.ERROR_MESSAGE);
        }
        else
          list.remove(rows[i]);
      }
      else
      {
        JOptionPane.showMessageDialog(this,
            "Row cannot be removed",
            "error",
            JOptionPane.ERROR_MESSAGE);
      }
      currTable.revalidate();
    }

  }// GEN-LAST:event_GPNewActionPerformed

  // GEN-FIRST:event_GPDeleteActionPerformed
  private void GPOption2ActionPerformed(java.awt.event.ActionEvent evt) {

    ObjAttribute newObj = new ObjAttribute("", "", EnumVisibility.NO, "", "",
        Color.black, "", "", editable);
    int tab1 = GPTabbedPane.getSelectedIndex();
    globalLists.get(tab1).addLast(newObj);
    if (currTab == 2)
      currTable.setValueAt("reg", globalLists.get(2).size() - 1, 3);

    currTable.revalidate();

  }// GEN-LAST:event_GPDeleteActionPerformed

  private void GPOption3ActionPerformed(java.awt.event.ActionEvent evt) {
    if (currTab == 0)
    {

      if (!checkNames(currTable, "reset_signal"))
      {
        globalLists.get(0).add(
            new ObjAttribute("reset_signal", "resetN", EnumVisibility.NO,
                "negedge", "",
                Color.black, "", "",
                editable2));
      }
      if (!checkNames(currTable, "reset_state"))
      {
        globalLists.get(0).add(
            new ObjAttribute("reset_state", "state0", EnumVisibility.NO, "",
                "", Color.black,
                "", "",
                editable2));
      }

      GPOption3.setEnabled(false);
      currTable.revalidate();
    }
    if (currTab == 1)
    {
      globalLists.get(1).add(
          new ObjAttribute("in", "", EnumVisibility.NO, "", "", Color.black,
              "", "",
              editable));

      currTable.revalidate();
    }
    if (currTab == 2)
    {
      globalLists.get(2).add(
          new ObjAttribute("out", "", EnumVisibility.NONDEFAULT, "", "",
              Color.black, "", "",
              editable));
      currTable.setValueAt("reg", globalLists.get(2).size() - 1, 3);

      currTable.revalidate();
    }
    if (currTab == 4)
    {
      if (!checkNames(currTable, "graycode"))
      {
        globalLists.get(4).add(
            new ObjAttribute("graycode", "", EnumVisibility.YES,
                "", "", Color.black, "", "",
                editable));
      }
      GPOption3.setEnabled(false);
      currTable.revalidate();
    }

  }

  private void removeAttribute(int tab, String name)
  {
    for (int i = 0; i < globalLists.get(tab).size(); i++)
    {
      ObjAttribute obj = globalLists.get(tab).get(i);
      if (obj.getName().equals(name) && obj.getType().equals("output"))
        globalLists.get(tab).remove(i);

    }
  }

  private void GPOption4ActionPerformed(java.awt.event.ActionEvent evt) {

    if (currTab == 1)
    {
      globalLists.get(1).add(
          new ObjAttribute("in[1:0]", "", EnumVisibility.NO, "", "",
              Color.black, "", "",
              editable));

      currTable.revalidate();
    }
    if (currTab == 2)
    {
      globalLists.get(2).add(
          new ObjAttribute("out[1:0]", "", EnumVisibility.NONDEFAULT, "", "",
              Color.black, "", "",
              editable));
      currTable.setValueAt("reg", globalLists.get(2).size() - 1, 3);

      currTable.revalidate();
    }
    if (currTab == 4)
    {
      globalLists.get(4).add(
          new ObjAttribute("", "", EnumVisibility.YES, "output", "",
              Color.black, "", "",
              editable));

      currTable.revalidate();
    }

  }

  private void GPOption5ActionPerformed(java.awt.event.ActionEvent evt) {

    if (currTab == 2)
    {
      globalLists.get(2).add(
          new ObjAttribute("flag", "", EnumVisibility.NONDEFAULT, "", "",
              Color.black,
              "suppress_portlist", "",
              editable));
      currTable.setValueAt("flag", globalLists.get(2).size() - 1, 3);

      currTable.revalidate();
    }
    if (currTab == 4)
    {
      if (!checkNames(currTable, "priority"))
      {
        globalLists.get(4).add(
            new ObjAttribute("priority", "1000", EnumVisibility.YES, "", "",
                Color.black, "",
                "",
                editable));
      }
      GPOption5.setEnabled(false);
      currTable.revalidate();
    }
  }

  private void GPOption6ActionPerformed(java.awt.event.ActionEvent evt) {

  }

  private boolean checkNames(JTable currTable2, String string) {
    for (int i = 0; i < currTable2.getRowCount(); i++)
    {
      if (currTable2.getValueAt(i, 0).equals(string))
        return true;
    }
    return false;
  }

  // GEN-FIRST:event_GPOKActionPerformed
  private void GPOKActionPerformed(java.awt.event.ActionEvent evt) {
    GPTableMachine.editCellAt(0, 0);
    GPTableState.editCellAt(0, 0);
    GPTableTrans.editCellAt(0, 0);
    GPTableInputs.editCellAt(0, 0);
    GPTableOutputs.editCellAt(0, 0);
    int error = 0;
    for (int i = 0; i < globalLists.size(); i++)
    {
      for (int j = 0; j < globalLists.get(i).size(); j++)
      {
        if (i == 2 && !globalLists.get(i).get(j).getType().equals("reg")
            && !globalLists.get(i).get(j).getType().equals("comb")
            && !globalLists.get(i).get(j).getType().equals("regdp")
            && !globalLists.get(i).get(j).getType().equals("flag"))
          error = 2;
        for (int k = j + 1; k < globalLists.get(i).size(); k++)
        {
          if (globalLists.get(i).get(j).getName().equals(
              globalLists.get(i).get(k).getName()))
            error = 1;
        }
      }
    }
    if (error == 0)
    {
      drawArea.updateStates();
      drawArea.updateTrans();
      drawArea.updateGlobalTable();
      drawArea.commitUndo();
      dispose();
    }
    else if (error == 1)
    {
      JOptionPane.showMessageDialog(this,
          "Two rows cannot contain the same name",
          "error",
          JOptionPane.ERROR_MESSAGE);
    }
    else if (error == 2)
    {
      JOptionPane.showMessageDialog(this,
          "An output must have a type set",
          "error",
          JOptionPane.ERROR_MESSAGE);
    }

  }// GEN-LAST:event_GPOKActionPerformed

  // GEN-FIRST:event_GPCancelActionPerformed
  private void GPCancelActionPerformed(java.awt.event.ActionEvent evt) {
    drawArea.cancel();
    dispose();
  }// GEN-LAST:event_GPCancelActionPerformed

  // GEN-BEGIN:variables
  // Variables declaration - do not modify
  private javax.swing.JButton GPCancel;
  private javax.swing.JButton GPOption1;
  private javax.swing.JButton GPOption2;
  private javax.swing.JButton GPOption3;
  private javax.swing.JButton GPOption4;
  private javax.swing.JButton GPOption5;
  private javax.swing.JButton GPOption6;
  private javax.swing.JLabel GPLabel;
  private javax.swing.JLabel GPLabel2;
  private javax.swing.JButton GPOK;
  private javax.swing.JScrollPane GPScrollMachine;
  private javax.swing.JScrollPane GPScrollState;
  private javax.swing.JScrollPane GPScrollTrans;
  private javax.swing.JScrollPane GPScrollInputs;
  private javax.swing.JScrollPane GPScrollOutputs;
  private javax.swing.JTabbedPane GPTabbedPane;
  private javax.swing.JTable GPTableMachine;
  private javax.swing.JTable GPTableState;
  private javax.swing.JTable GPTableTrans;
  private javax.swing.JTable GPTableInputs;
  private javax.swing.JTable GPTableOutputs;
  // End of variables declaration//GEN-END:variables

}
