package display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;

import attributes.EnumVisibility;
import attributes.ObjAttribute;
import entities.StateObj;

class StateProperties extends javax.swing.JDialog {
  StateObj state;
  DrawArea drawArea;
  Component window = this;
  JColorChooser colorChooser;
  LinkedList<LinkedList<ObjAttribute>> globalList;

  /**
   * Creates new form StateProperties
   * 
   * @param drawArea
   */

  public StateProperties(DrawArea DA, java.awt.Frame parent, boolean modal,
      StateObj s) {
    super(parent, modal);
    state = s;
    oldName = new String(state.getName());
    drawArea = DA;
    globalList = drawArea.getGlobalList();
    colorChooser = drawArea.getColorChooser();
    initComponents();
  }

  private void initComponents() {
    SPLabel = new javax.swing.JLabel();
    SPScroll = new javax.swing.JScrollPane();
    SPTable = new javax.swing.JTable();
    SPW = new javax.swing.JLabel();
    SPH = new javax.swing.JLabel();
    SPC = new javax.swing.JLabel();
    SPWField = new javax.swing.JFormattedTextField(NumberFormat
        .getIntegerInstance());
    SPHField = new javax.swing.JFormattedTextField(NumberFormat
        .getIntegerInstance());
    SPCancel = new javax.swing.JButton();
    SPOK = new javax.swing.JButton();
    SPNew = new javax.swing.JButton();
    SPDelete = new javax.swing.JButton();

    setResizable(true);

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Edit State Properties");
    SPLabel.setText("Edit the properties of the selected state:");

    // Type column
    SPTable.setModel(new MyTableModel(state, this, globalList, 3));
    SPTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    // use dropdown boxes
    String[] options = new String[] { "No", "Yes", "Only non-default" };
    TableColumn column = SPTable.getColumnModel().getColumn(2);
    column.setCellEditor(new MyJComboBoxEditor(options));

    // Color column
    column = SPTable.getColumnModel().getColumn(5);
    column.setPreferredWidth(SPTable.getRowHeight());
    column.setCellEditor(new MyJColorEditor(colorChooser));
    column.setCellRenderer(new MyJColorRenderer());

    SPNew.setVisible(false);
    SPDelete.setVisible(false);

    SPScroll.setViewportView(SPTable);

    SPW.setText("Width:");
    SPH.setText("Height:");

    SPC.setPreferredSize(new Dimension(50, 20));
    SPC.setMinimumSize(new Dimension(50, 20));
    SPC.setOpaque(true);
    SPC.setVisible(true);

    // set background color to color of transition and add action listener
    SPC.setBackground(state.getColor());
    SPC.setBorder(new LineBorder(Color.black, 1));
    SPC.addMouseListener(new MouseListener() {

      ActionListener colorSel = new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          SPC.setBackground(colorChooser.getColor());
          state.setColor(colorChooser.getColor());
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

    SPWField.setValue(new Integer(state.getWidth()));
    SPWField.setColumns(10);
    SPHField.setValue(new Integer(state.getHeight()));
    SPHField.setColumns(10);

    SPCancel.setText("Cancel");
    SPCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        SPCancelActionPerformed(evt);
      }
    });

    SPOK.setText("OK");
    SPOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        SPOKActionPerformed(evt);
      }
    });

    SPNew.setText("New");
    SPNew.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        SPNewActionPerformed(evt);
      }
    });

    SPDelete.setText("Delete");
    SPDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        SPDeleteActionPerformed(evt);
      }
    });

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
                                SPScroll,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                480,
                                Short.MAX_VALUE)
                            .add(SPLabel)
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(
                                                SPW)
                                            .add(
                                                SPH)
                                    )
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(
                                                SPHField,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(
                                                SPWField,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(
                                        42,
                                        42,
                                        42)
                                    .add(SPC)
                                    .add(
                                        259,
                                        259,
                                        259)
                                    .add(
                                        SPOK)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        SPCancel))
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        SPNew)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        SPDelete)))
                    .addContainerGap()));
    layout
        .setVerticalGroup(layout
            .createParallelGroup(
                org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(SPLabel)
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        SPScroll,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        151,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(
                                org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(SPNew).add(
                                SPDelete))
                    .add(
                        layout
                            .createParallelGroup(
                                org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED,
                                        55,
                                        Short.MAX_VALUE)
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.BASELINE)
                                            .add(
                                                SPCancel)
                                            .add(
                                                SPOK))

                                    .addContainerGap())
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        22,
                                        22,
                                        22)
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.BASELINE)
                                            .add(
                                                SPW)
                                            .add(
                                                SPWField,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

                                            .add(
                                                SPC))
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.BASELINE)
                                            .add(
                                                SPH)
                                            .add(
                                                SPHField,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap()))));
    pack();
  }// </editor-fold>//GEN-END:initComponents

  // GEN-FIRST:event_SPNewActionPerformed
  private void SPNewActionPerformed(java.awt.event.ActionEvent evt) {
    ObjAttribute newObj = new ObjAttribute("", "", EnumVisibility.NO, "", "",
        Color.black, "", "");
    state.getAttributeList().addLast(newObj);
    SPTable.revalidate();
  }// GEN-LAST:event_SPNewActionPerformed

  // GEN-FIRST:event_SPDeleteActionPerformed
  private void SPDeleteActionPerformed(java.awt.event.ActionEvent evt) {
    // delete selected rows
    int[] rows = SPTable.getSelectedRows();
    for (int i = rows.length - 1; i > -1; i--)
    {
      int type = state.getAttributeList().get(rows[i]).getEditable(0);
      if (type != ObjAttribute.GLOBAL_FIXED && type != ObjAttribute.ABS)
      {
        state.getAttributeList().remove(rows[i]);
        SPTable.revalidate();
      }
      else
      {
        JOptionPane.showMessageDialog(this,
            "Cannot delete a global attribute.\n"
                + "Must be removed from global attribute properties.",
            "error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
    // notify attribute list?
  }// GEN-LAST:event_SPDeleteActionPerformed

  // GEN-FIRST:event_SPOKActionPerformed
  private void SPOKActionPerformed(java.awt.event.ActionEvent evt) {
    SPTable.editCellAt(0, 0);
    if (drawArea.checkStateNames())
    {
      // temp
      try {
        SPWField.commitEdit();
        SPHField.commitEdit();
      } catch (ParseException e) {
        // TODsO Auto-generated catch block
        e.printStackTrace();
      }
      for (int j = 0; j < globalList.get(0).size(); j++)
      {
        if (globalList.get(0).get(j).getName().equals("reset_state")
            && globalList.get(0).get(j).getValue().equals(oldName))
        {
          globalList.get(0).get(j).setValue(state.getName());
        }
      }
      int width = ((Number) SPWField.getValue()).intValue();
      int height = ((Number) SPHField.getValue()).intValue();
      state.setSize(width, height);
      // make transitions redraw
      state.setStateModifiedTrue();
      drawArea.updateTransitions();
      drawArea.updateStates();
      drawArea.updateGlobalTable();
      drawArea.commitUndo();
      dispose();
    }
    else
    {
      JOptionPane.showMessageDialog(this,
          "Two different states cannot have the same name.",
          "error",
          JOptionPane.ERROR_MESSAGE);
    }
  }// GEN-LAST:event_SPOKActionPerformed

  // GEN-FIRST:event_SPCancelActionPerformed
  private void SPCancelActionPerformed(java.awt.event.ActionEvent evt) {
    drawArea.cancel();
    dispose();
  }// GEN-LAST:event_SPCancelActionPerformed

  /**
   * @param args
   *          the command line arguments
   */

  // GEN-BEGIN:variables
  // Variables declaration - do not modify
  private javax.swing.JButton SPCancel;
  private javax.swing.JButton SPDelete;
  private javax.swing.JLabel SPH;
  private javax.swing.JFormattedTextField SPHField;
  private javax.swing.JLabel SPLabel;
  private javax.swing.JButton SPNew;
  private javax.swing.JButton SPOK;
  private javax.swing.JScrollPane SPScroll;
  private javax.swing.JTable SPTable;
  private javax.swing.JLabel SPW;
  private javax.swing.JLabel SPC;
  private javax.swing.JFormattedTextField SPWField;
  private String oldName;

  // End of variables declaration//GEN-END:variables

}