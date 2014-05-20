/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gui.toolkit;

import graph.GraphFactoryAEFD;
import graph.Model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

import abstractGraph.conditions.Variable;
import display.FizzimGui;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;

/**
 * The ListCutPaste example illustrates cut, copy, paste
 * and drag and drop using three instances of JList.
 * The TransferActionListener class listens for one of
 * the CCP actions and, when one occurs, forwards the
 * action to the component which currently has the focus.
 */
/**
 * This classes allows to create a window that will display all the identifiers
 * that are present in the current model under edition.
 * 
 */
@SuppressWarnings("serial")
public class IdentifiersToolKit extends JPanel {
  ListTransferHandler lh;

  public IdentifiersToolKit(FizzimGui fizzim) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    JButton btnNewButton = new JButton("Mettre à jour");

    add(btnNewButton);

    UpperPart upper_part = new UpperPart(fizzim);// new JPanel();
    add(upper_part);

    btnNewButton.addActionListener(new UpdateLists(upper_part));

    JPanel middle_part = new JPanel();
    add(middle_part);

    JScrollPane lower_part = new JScrollPane();
    add(lower_part);

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setRows(10);
    lower_part.setViewportView(textArea);

    TextAreaOutputStream textOut = new TextAreaOutputStream(textArea);
    PrintStream outStream = new PrintStream(textOut, true);

    FizzimGui.out_stream.add(outStream);
    FizzimGui.err_stream.add(outStream);
  }

  class UpperPart extends JPanel {

    FizzimGui fizzim;
    JList<String> list1, list2, list3;

    public UpperPart(FizzimGui fizzim) {
      super(new BorderLayout());
      this.fizzim = fizzim;

      lh = new ListTransferHandler();

      JPanel panel = new JPanel(new GridLayout(1, 3));
      DefaultListModel<String> indicators = new DefaultListModel<String>();

      list1 = new JList<String>(indicators);
      list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JScrollPane sp1 = new JScrollPane(list1);
      sp1.setPreferredSize(new Dimension(100, 200));
      list1.setDragEnabled(true);
      list1.setTransferHandler(lh);
      list1.setDropMode(DropMode.ON_OR_INSERT);
      setMappings(list1);
      JPanel first_column = new JPanel(new BorderLayout());
      first_column.add(sp1, BorderLayout.CENTER);
      first_column.setBorder(BorderFactory.createTitledBorder("Indicateurs"));
      panel.add(first_column);

      DefaultListModel<String> list2Model = new DefaultListModel<String>();
      list2 = new JList<String>(list2Model);
      list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list2.setDragEnabled(true);
      JScrollPane sp2 = new JScrollPane(list2);
      sp2.setPreferredSize(new Dimension(100, 200));
      list2.setTransferHandler(lh);
      list2.setDropMode(DropMode.INSERT);
      setMappings(list2);
      JPanel pan2 = new JPanel(new BorderLayout());
      pan2.add(sp2, BorderLayout.CENTER);
      pan2.setBorder(BorderFactory.createTitledBorder("????"));
      panel.add(pan2);

      DefaultListModel<String> list3Model = new DefaultListModel<String>();

      list3 = new JList<String>(list3Model);
      list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list3.setDragEnabled(true);
      JScrollPane sp3 = new JScrollPane(list3);
      sp3.setPreferredSize(new Dimension(100, 200));
      list3.setTransferHandler(lh);
      list3.setDropMode(DropMode.ON);
      setMappings(list3);
      JPanel pan3 = new JPanel(new BorderLayout());
      pan3.add(sp3, BorderLayout.CENTER);
      pan3.setBorder(BorderFactory.createTitledBorder("????"));
      panel.add(pan3);

      setPreferredSize(new Dimension(100, 300));
      add(panel, BorderLayout.CENTER);
    }

    /**
     * Create an Edit menu to support cut/copy/paste.
     */
    public JMenuBar createMenuBar() {
      JMenuItem menuItem = null;
      JMenuBar menuBar = new JMenuBar();
      JMenu mainMenu = new JMenu("Edit");
      mainMenu.setMnemonic(KeyEvent.VK_E);
      TransferActionListener actionListener = new TransferActionListener();

      menuItem = new JMenuItem("Copier");
      menuItem.setActionCommand((String) TransferHandler.getCopyAction().
          getValue(Action.NAME));
      menuItem.addActionListener(actionListener);
      menuItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
      menuItem.setMnemonic(KeyEvent.VK_C);
      mainMenu.add(menuItem);

      menuItem = new JMenuItem("Mettre à jour");
      menuItem.addActionListener(new UpdateLists(fizzim, list1, list2, list3));
      menuItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
      menuItem.setMnemonic(KeyEvent.VK_A);
      mainMenu.add(menuItem);

      menuItem = new JMenuItem("Paste");
      menuItem.setActionCommand((String) TransferHandler.getPasteAction().
          getValue(Action.NAME));
      menuItem.addActionListener(actionListener);
      menuItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
      menuItem.setMnemonic(KeyEvent.VK_P);
      mainMenu.add(menuItem);

      menuBar.add(mainMenu);
      return menuBar;
    }

    /**
     * Add the cut/copy/paste actions to the action map.
     */
    private void setMappings(JList<?> list) {
      ActionMap map = list.getActionMap();
      map.put(TransferHandler.getCopyAction().getValue(Action.NAME),
          TransferHandler.getCopyAction());
      map.put(TransferHandler.getPasteAction().getValue(Action.NAME),
          TransferHandler.getPasteAction());
    }

    /**
     * Create the GUI and show it. For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    /*
     * public static void createAndShowGUI(FizzimGui fizzim) {
     * // Create and set up the window.
     * JFrame frame = new JFrame("Toolkit");
     * 
     * // Create and set up the menu bar and content pane.
     * IdentifiersToolKit demo = new IdentifiersToolKit(fizzim);
     * frame.setJMenuBar(demo.createMenuBar());
     * demo.setOpaque(true); // content panes must be opaque
     * frame.setContentPane(demo);
     * 
     * // Display the window.
     * frame.pack();
     * frame.setLocationRelativeTo(null);
     * frame.setVisible(true);
     * }
     * 
     * public static void main(String[] args) {
     * // Schedule a job for the event-dispatching thread:
     * // creating and showing this application's GUI.
     * javax.swing.SwingUtilities.invokeLater(new Runnable() {
     * public void run() {
     * // Turn off metal's use of bold fonts
     * UIManager.put("swing.boldMetal", Boolean.FALSE);
     * createAndShowGUI(null);
     * }
     * });
     * }
     */

  }

  /**
   * The actionListener to update the lists.
   */
  class UpdateLists implements ActionListener {

    FizzimGui fizzim;
    JList<String> l1;
    JList<String> l2;
    JList<String> l3;

    public UpdateLists(UpperPart upper_part) {
      this(upper_part.fizzim, upper_part.list1, upper_part.list2,
          upper_part.list3);
    }

    public UpdateLists(FizzimGui fizzim,
        JList<String> l1, JList<String> l2, JList<String> l3) {
      super();
      this.fizzim = fizzim;
      this.l1 = l1;
      this.l2 = l2;
      this.l3 = l3;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      String file_name = "temporary_for_indicators";
      File f = new File(file_name);
      fizzim.saveFile6lines(f);

      GraphFactoryAEFD builder;
      try {
        builder = new GraphFactoryAEFD(file_name);
        Model m = builder.buildModel("None");

        DefaultListModel<String> listModel =
            (DefaultListModel<String>) l1.getModel();
        listModel.removeAllElements();

        Iterator<Variable> it = m.iteratorExistingVariables();
        while (it.hasNext()) {
          Variable var = it.next();
          listModel.addElement(var.toString());
        }

      } catch (IOException ex) {
        // TODO Auto-generated catch block
        ex.printStackTrace();
      }

    }
  }
}
