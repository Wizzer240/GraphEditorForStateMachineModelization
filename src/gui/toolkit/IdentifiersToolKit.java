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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;

import abstractGraph.conditions.BooleanVariable;
import abstractGraph.conditions.EnumeratedVariable;
import abstractGraph.events.CommandEvent;
import abstractGraph.events.ExternalEvent;
import abstractGraph.events.SynchronisationEvent;
import display.FizzimGui;

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
public class IdentifiersToolKit extends JSplitPane {
  ListTransferHandler lh;

  public static void main(String args[]) {
    JFrame frame = new JFrame();
    FizzimGui fizzim = new FizzimGui();

    frame.add(new IdentifiersToolKit(new FizzimGui()));
    frame.pack();
    frame.setVisible(true);
  }

  public IdentifiersToolKit(FizzimGui fizzim) {
    super(JSplitPane.VERTICAL_SPLIT);
    // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    /* The upper part gets all the extra space */
    setResizeWeight(1);

    UpperPart upper_part = new UpperPart(fizzim);// new JPanel();
    setTopComponent(upper_part);

    /*
     * Bottom component contains buttons and a text area. The latter needs to be
     * initialized first
     */
    final JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setRows(8);

    TextAreaOutputStream textOut = new TextAreaOutputStream(textArea);
    PrintStream outStream = new PrintStream(textOut, true);

    FizzimGui.out_stream.add(outStream);
    FizzimGui.err_stream.add(outStream);

    JPanel bottom_panel = new JPanel();
    bottom_panel.setLayout(new BoxLayout(bottom_panel, BoxLayout.Y_AXIS));
    // bottom_panel.setLayout(new BorderLayout());
    bottom_panel.setPreferredSize(new Dimension(200, 200));
    // bottom_panel.setMaximumSize(new Dimension(200, 30));

    JPanel buttons_panel = new JPanel();
    bottom_panel.add(buttons_panel);
    buttons_panel.setLayout(new BoxLayout(buttons_panel, BoxLayout.X_AXIS));

    Font myFont = new Font("Arial", Font.ROMAN_BASELINE | Font.BOLD, 12);

    JButton refresh_button = new JButton("Rafraichir");
    refresh_button.setMargin(new Insets(1, 1, 1, 1));
    refresh_button.setFont(myFont);
    buttons_panel.add(refresh_button);

    JButton clear_button = new JButton("Effacer l'historique");
    clear_button.setMargin(new Insets(1, 1, 1, 1));
    clear_button.setFont(myFont);
    buttons_panel.add(clear_button);
    clear_button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        textArea.setText(null);
      }
    });

    refresh_button.addActionListener(new UpdateLists(upper_part));

    JScrollPane errors_panel = new JScrollPane();
    errors_panel.setViewportView(textArea);
    bottom_panel.add(errors_panel);

    setBottomComponent(bottom_panel);

  }

  class UpperPart extends JPanel {

    FizzimGui fizzim;
    JList<String> list1, list2, list3;

    public UpperPart(FizzimGui fizzim) {
      super(new BorderLayout());
      this.fizzim = fizzim;

      lh = new ListTransferHandler();

      JPanel panel = new JPanel(new GridLayout(1, 3));
      SortedListModel indicators = new SortedListModel();

      list1 = new JList<String>(indicators);
      // list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JScrollPane sp1 = new JScrollPane(list1);
      // sp1.setPreferredSize(new Dimension(100, 200));
      list1.setDragEnabled(true);
      list1.setTransferHandler(lh);
      list1.setDropMode(DropMode.ON_OR_INSERT);
      setMappings(list1);
      JPanel first_column = new JPanel(new BorderLayout());
      first_column.add(sp1, BorderLayout.CENTER);
      first_column.setBorder(BorderFactory.createTitledBorder("Evts externes"));
      panel.add(first_column);

      SortedListModel list2Model = new SortedListModel();
      list2 = new JList<String>(list2Model);
      // list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list2.setDragEnabled(true);
      JScrollPane sp2 = new JScrollPane(list2);
      // sp2.setPreferredSize(new Dimension(100, 200));
      list2.setTransferHandler(lh);
      list2.setDropMode(DropMode.INSERT);
      setMappings(list2);
      JPanel pan2 = new JPanel(new BorderLayout());
      pan2.add(sp2, BorderLayout.CENTER);
      pan2.setBorder(BorderFactory.createTitledBorder("Inds et syns"));
      panel.add(pan2);

      SortedListModel list3Model = new SortedListModel();

      list3 = new JList<String>(list3Model);
      // list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list3.setDragEnabled(true);
      JScrollPane sp3 = new JScrollPane(list3);
      // sp3.setPreferredSize(new Dimension(100, 200));
      list3.setTransferHandler(lh);
      list3.setDropMode(DropMode.ON);
      setMappings(list3);
      JPanel pan3 = new JPanel(new BorderLayout());
      pan3.add(sp3, BorderLayout.CENTER);
      pan3.setBorder(BorderFactory.createTitledBorder("Commandes"));
      panel.add(pan3);

      // setPreferredSize(new Dimension(200, 400));
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
        builder = new GraphFactoryAEFD(null);
        Model m = builder.buildModel(file_name, "None");

        SortedListModel listModel;

        listModel = (SortedListModel) l1.getModel();
        listModel.removeAllElements();
        Iterator<ExternalEvent> it_ext = m.iteratorExternalEvents();
        while (it_ext.hasNext()) {
          listModel.addElement(it_ext.next().toString());
        }

        listModel = (SortedListModel) l2.getModel();
        listModel.removeAllElements();

        Iterator<EnumeratedVariable> it = m.iteratorExistingVariables();
        while (it.hasNext()) {
          BooleanVariable var = (BooleanVariable) it.next();
          listModel.addElement(var.toString());
        }

        Iterator<SynchronisationEvent> it2 = m.iteratorSyns();
        while (it2.hasNext()) {
          listModel.addElement(it2.next().toString());
        }

        listModel = (SortedListModel) l3.getModel();
        listModel.removeAllElements();
        Iterator<CommandEvent> it_cmds = m.iteratorCommands();
        while (it_cmds.hasNext()) {
          listModel.addElement(it_cmds.next().toString());
        }

      } catch (IOException ex) {
        ex.printStackTrace();
      }

    }
  }
}
