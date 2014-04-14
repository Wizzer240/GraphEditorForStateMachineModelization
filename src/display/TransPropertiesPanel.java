package display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;

import attributes.EnumVisibility;
import attributes.ObjAttribute;
import entities.StateObj;
import entities.StateTransitionObj;
import entities.TransitionObj;
import gui.GeneralEditorWindow;

public class TransPropertiesPanel extends JPanel {

  TransitionObj trans;
  DrawArea drawArea;
  StateObj start;
  StateObj end;
  StateObj pref;
  Vector<StateObj> stateObjs;
  boolean loopback = false;
  boolean stub = false;
  LinkedList<LinkedList<ObjAttribute>> globalList;
  private JDialog parent_window;
  Component window = this;
  JColorChooser colorChooser;
  private JButton TPDelete;
  private JLabel TPLabel;
  private JButton TPNew;
  private JScrollPane TPScroll;
  private JTable TPTable;
  private JComboBox jComboBox1;
  private JComboBox jComboBox2;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JCheckBox jCheckBox1;

  // End of variables declaration//GEN-END:variables
  /** Creates new form TransP */
  public TransPropertiesPanel(GeneralEditorWindow parent_window, DrawArea DA,
      TransitionObj t, Vector<StateObj> states, boolean is_loopback,
      StateObj state) {
    // super(parent, modal);
    trans = t;
    drawArea = DA;
    stateObjs = states;
    loopback = is_loopback;
    pref = state;
    globalList = drawArea.getGlobalList();
    colorChooser = drawArea.getColorChooser();
    if (trans.getType() == 1) {
      StateTransitionObj t1 = (StateTransitionObj) t;
      stub = t1.getStub();
    }
    this.parent_window = parent_window;
    initComponents();

    /*
     * We add the update and cancel actions to the OK and Cancel button of the
     * parent window
     */
    parent_window.getBtnOk().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        TPOKActionPerformed(evt);
      }
    });

    parent_window.getBtnCancel().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        TPCancelActionPerformed(evt);
      }
    });
  }

  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // GEN-BEGIN:initComponents
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
  private void initComponents() {
    TPLabel = new JLabel();
    TPScroll = new JScrollPane();
    TPTable = new JTable();
    TPNew = new JButton();
    TPDelete = new JButton();
    jLabel1 = new JLabel();
    jLabel2 = new JLabel();
    jLabel3 = new JLabel();
    jComboBox1 = new JComboBox();
    jComboBox2 = new JComboBox();
    jCheckBox1 = new JCheckBox();

    if (!loopback) {
      // setTitle("Edit State Transition Properties");
      TPLabel.setText("Edit the properties of the selected state transition:");
    } else {
      // setTitle("Edit Loopback Transition Properties");
      TPLabel
          .setText("Edit the properties of the selected loopback transition:");
    }
    TPTable.setModel(new MyTableModel(trans, parent_window, globalList, 4));
    TPTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    // use dropdown boxes
    String[] options = new String[] { "No", "Yes", "Only non-default" };
    TableColumn column = TPTable.getColumnModel().getColumn(2);
    column.setCellEditor(new MyJComboBoxEditor(options));

    column = TPTable.getColumnModel().getColumn(5);
    column.setPreferredWidth(TPTable.getRowHeight());
    column.setCellEditor(new MyJColorEditor(colorChooser));
    column.setCellRenderer(new MyJColorRenderer());
    TPNew.setVisible(false);
    TPDelete.setVisible(false);

    TPScroll.setViewportView(TPTable);

    TPNew.setText("New");
    TPNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        TPNewActionPerformed(evt);
      }
    });

    TPDelete.setText("Delete");
    TPDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        TPDeleteActionPerformed(evt);
      }
    });

    if (!loopback) {
      jLabel1.setText("Start State:");
      jLabel2.setText("End State:");
    } else {
      jLabel1.setText("State:");
      jLabel2.setVisible(false);
      jCheckBox1.setVisible(false);
    }

    jLabel3.setPreferredSize(new Dimension(50, 20));
    jLabel3.setMinimumSize(new Dimension(50, 20));
    jLabel3.setOpaque(true);
    jLabel3.setVisible(true);

    // set background color to color of transition and add action listener
    jLabel3.setBackground(trans.getColor());
    jLabel3.setBorder(new LineBorder(Color.black, 1));
    jLabel3.addMouseListener(new MouseListener() {

      ActionListener colorSel = new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          jLabel3.setBackground(colorChooser.getColor());
          trans.setColor(colorChooser.getColor());
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

    int size = stateObjs.size();

    jComboBox1.setModel(new DefaultComboBoxModel(stateObjs));
    if (trans.getStartState() == null) {

      if (!loopback) {
        jComboBox1.setSelectedIndex(size - 2);
        start = stateObjs.get(size - 2);
      } else {
        if (pref == null)
        {
          jComboBox1.setSelectedIndex(size - 1);
          start = stateObjs.get(size - 1);
        } else {
          int index = 0;
          for (int i = 0; i < stateObjs.size(); i++) {
            if (stateObjs.get(i).equals(pref)) {
              index = i;
              break;
            }
          }
          jComboBox1.setSelectedIndex(index);
          start = pref;
        }

      }

    }
    else
    {
      start = trans.getStartState();
      jComboBox1.setSelectedIndex(stateObjs.indexOf(start));
    }
    if (!loopback)
    {
      jComboBox2.setModel(new DefaultComboBoxModel(stateObjs));
      if (trans.getEndState() == null)
      {
        jComboBox2.setSelectedIndex(size - 1);
        end = stateObjs.get(size - 1);
      }
      else
      {
        end = trans.getEndState();
        jComboBox2.setSelectedIndex(stateObjs.indexOf(end));
      }
    }
    else
      jComboBox2.setVisible(false);

    jComboBox1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        StartStateActionPerformed(evt);
      }
    });

    if (!loopback)
    {
      jComboBox2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          EndStateActionPerformed(evt);
        }
      });
    }

    if (!loopback)
    {
      jCheckBox1.setText("Stub?");
      jCheckBox1.setSelected(stub);
      jCheckBox1.setBorder(BorderFactory.createEmptyBorder(0, 0,
          0, 0));
      jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));
    }

    GroupLayout layout = new GroupLayout(this);
    layout.setHorizontalGroup(layout
        .createParallelGroup(Alignment.LEADING)
        .addGroup(layout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(TPScroll, GroupLayout.DEFAULT_SIZE, 636,
                        Short.MAX_VALUE)
                    .addComponent(TPLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout
                            .createParallelGroup(Alignment.LEADING, false)
                            .addGroup(layout
                                .createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(
                                    ComponentPlacement.RELATED,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE)
                                .addComponent(jComboBox2,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout
                                .createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(
                                    ComponentPlacement.RELATED,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE)
                                .addComponent(jComboBox1,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout
                                .createSequentialGroup()
                                .addComponent(TPNew)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(TPDelete)))
                        .addGroup(layout
                            .createParallelGroup(Alignment.LEADING)
                            .addGroup(layout
                                .createSequentialGroup()
                                .addGap(42)
                                .addComponent(jLabel3,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42)
                                .addComponent(jCheckBox1)))
                        .addGap(174)))
            .addContainerGap())
        );
    layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(TPLabel)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(TPScroll, GroupLayout.PREFERRED_SIZE, 151,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(TPNew)
                .addComponent(TPDelete))
            .addGap(22)
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(jComboBox1,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(jComboBox2,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE)
                .addComponent(jCheckBox1))
            .addContainerGap())
        );
    setLayout(layout);
    // pack();
  }

  private void StartStateActionPerformed(ActionEvent evt) {
    JComboBox cb = (JComboBox) evt.getSource();
    StateObj selState = (StateObj) cb.getSelectedItem();
    start = selState;

  }

  private void EndStateActionPerformed(ActionEvent evt) {
    JComboBox cb = (JComboBox) evt.getSource();
    StateObj selState = (StateObj) cb.getSelectedItem();
    end = selState;
  }

  private void TPDeleteActionPerformed(ActionEvent evt) {
    // delete selected rows
    int[] rows = TPTable.getSelectedRows();
    for (int i = rows.length - 1; i > -1; i--) {
      int type = trans.getAttributeList().get(rows[i]).getEditable(0);
      if (type != ObjAttribute.GLOBAL_FIXED && type != ObjAttribute.ABS) {
        trans.getAttributeList().remove(rows[i]);
        TPTable.revalidate();
      } else {
        JOptionPane.showMessageDialog(this,
            "Cannot delete a global attribute.\n"
                + "Must be removed from global attribute properties.",
            "error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void TPCancelActionPerformed(ActionEvent evt) {
    drawArea.cancel();
    parent_window.dispose();
  }

  private void TPOKActionPerformed(ActionEvent evt) {
    TPTable.editCellAt(0, 0);
    if (drawArea.checkTransNames()) {
      if (!loopback) {
        if (start != end)
          trans.initTrans(start, end);
        boolean b = jCheckBox1.isSelected();
        if (b != stub) {
          if (trans.getType() == 1) {
            StateTransitionObj t1 = (StateTransitionObj) trans;
            t1.setStub(b);
          }
        }

        if (start != end) {

          drawArea.commitUndo();
          parent_window.dispose();
        } else {
          JOptionPane.showMessageDialog(this,
              "'Start State' and 'End State' must be different.",
              "error",
              JOptionPane.ERROR_MESSAGE);
        }
      } else {
        trans.initTrans(start);
        drawArea.commitUndo();
        parent_window.dispose();
      }
    } else {
      JOptionPane.showMessageDialog(this,
          "Two different transitions cannot have the same name.",
          "error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void TPNewActionPerformed(ActionEvent evt) {
    ObjAttribute newObj =
        new ObjAttribute("", "", EnumVisibility.NO, "", "", Color.black, "", "");
    trans.getAttributeList().addLast(newObj);
    TPTable.revalidate();
  }

}