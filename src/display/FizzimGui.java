package display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import entities.GeneralObj;
import entities.LoopbackTransitionObj;
import entities.StateObj;
import entities.TransitionObj;
import attributes.EnumGlobalList;
import attributes.EnumVisibility;
import attributes.ObjAttribute;

//Written by: Michael Zimmer - mike@zimmerdesignservices.com

/*
 Copyright 2007-2011 Zimmer Design Services

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

/* This file was originally created with matisse GUI Builder for MyEclipse.
 * Due to bugs and limitations, in is now being manually edited
 */
public class FizzimGui extends javax.swing.JFrame {

  static String currVer = "14.02.28";
  static String action_field = "Action";
  static String condition_field = "Garde";
  static String event_field = "Evènements";

  // pointer to global lists
  LinkedList<ObjAttribute> globalMachineAttributes;
  LinkedList<ObjAttribute> globalInputsAttributes;
  LinkedList<ObjAttribute> globalOutputsAttributes;
  LinkedList<ObjAttribute> globalStateAttributes;
  LinkedList<ObjAttribute> globalTransAttributes;

  LinkedList<LinkedList<ObjAttribute>> globalList;
  int maxH = 1296;
  int maxW = 936;
  boolean loading = false;

  /** Creates new form FizzimGui */
  public FizzimGui() {

    ImageIcon icon = new ImageIcon("icon.png");
    this.setIconImage(icon.getImage());
    // create global lists
    globalList = new LinkedList<LinkedList<ObjAttribute>>();
    globalMachineAttributes = new LinkedList<ObjAttribute>();
    globalInputsAttributes = new LinkedList<ObjAttribute>();
    globalOutputsAttributes = new LinkedList<ObjAttribute>();
    globalStateAttributes = new LinkedList<ObjAttribute>();
    globalTransAttributes = new LinkedList<ObjAttribute>();

    globalList.add(globalInputsAttributes);
    globalList.add(globalOutputsAttributes);
    globalList.add(globalMachineAttributes);
    globalList.add(globalStateAttributes);
    globalList.add(globalTransAttributes);

    drawArea1 = new DrawArea(globalList);

    drawArea1.setPreferredSize(new Dimension(maxW, maxH));

    initComponents();
    setTitle("Fizzim: State Machine GUI editor");
    setLocationRelativeTo(null);
    // custom initComponents

    drawArea1.setJFrame(this);
    initGlobal();

    drawArea1.updateStates();
    drawArea1.updateTrans();

  }

  private void initGlobal() {
    // set up required global attributes
    // 0=machine, 1=inputs, 2=outputs, 3=states, 4=trans
    int[] editable = { ObjAttribute.ABS, ObjAttribute.GLOBAL_VAR,
        ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR,
        ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR,
        ObjAttribute.GLOBAL_VAR };
    globalList.get(EnumGlobalList.MACHINE).add(
        new ObjAttribute("name", "def_name", EnumVisibility.NO, "", "",
            Color.black, "", "",
            editable));
    globalList.get(EnumGlobalList.MACHINE).add(
        new ObjAttribute("clock", "clk", EnumVisibility.NO,
            "posedge", "", Color.black, "", "", editable));

    globalList.get(EnumGlobalList.STATES).add(
        new ObjAttribute("name", "def_name", EnumVisibility.YES,
            "def_type", "", Color.black, "", "", editable));

    globalList.get(EnumGlobalList.TRANSITIONS).add(
        new ObjAttribute("name", "def_name", EnumVisibility.NO,
            "def_type", "", Color.black, "", "", editable));

    /* User defined properties */
    globalList.get(EnumGlobalList.STATES).add(
        new ObjAttribute("Ind. vrais", "", EnumVisibility.YES,
            "def_type", "", Color.black, "", "", editable));

    globalList.get(EnumGlobalList.TRANSITIONS).add(
        new ObjAttribute(event_field, "", EnumVisibility.YES,
            "def_type", "", Color.black, "", "", editable));
    globalList.get(EnumGlobalList.TRANSITIONS).add(
        new ObjAttribute(condition_field, "", EnumVisibility.YES,
            "def_type", "", Color.black, "", "", editable));
    globalList.get(EnumGlobalList.TRANSITIONS).add(
        new ObjAttribute(action_field, "", EnumVisibility.YES,
            "def_type", "", Color.black, "", "", editable));

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
    java.awt.GridBagConstraints gridBagConstraints;

    FileOpenAction = new MyJFileChooser("fzm");
    FileSaveAction = new MyJFileChooser("fzm");
    FileSave6LinesAction = new MyJFileChooser("txt");
    ExportChooser = new MyJFileChooser("png");
    jPanel3 = new javax.swing.JPanel();
    jTabbedPane1 = new MyJTabbedPane();
    jScrollPane1 = new javax.swing.JScrollPane();
    jPanel1 = new javax.swing.JPanel();
    MenuBar = new javax.swing.JMenuBar();
    FileMenu = new javax.swing.JMenu();
    FileItemNew = new javax.swing.JMenuItem();
    FileItemOpen = new javax.swing.JMenuItem();
    FileItemSave = new javax.swing.JMenuItem();
    FileItemSaveAs = new javax.swing.JMenuItem();
    FileItemSaveAs6Lines = new javax.swing.JMenuItem();
    FileExport = new javax.swing.JMenu("Export to...");
    FileExportClipboard = new javax.swing.JMenuItem();
    FileExportPNG = new javax.swing.JMenuItem();
    FileExportJPEG = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JSeparator();
    FilePref = new javax.swing.JMenuItem();
    FileItemPageSetup = new javax.swing.JMenuItem();
    FileItemPrint = new javax.swing.JMenuItem();
    jSeparator2 = new javax.swing.JSeparator();
    FileItemExit = new javax.swing.JMenuItem();
    EditMenu = new javax.swing.JMenu();
    EditItemUndo = new javax.swing.JMenuItem();
    EditItemRedo = new javax.swing.JMenuItem();
    EditItemDelete = new javax.swing.JMenuItem();
    GlobalMenu = new javax.swing.JMenu();
    GlobalItemMachine = new javax.swing.JMenuItem();
    GlobalItemStates = new javax.swing.JMenuItem();
    GlobalItemTransitions = new javax.swing.JMenuItem();
    jSeparator3 = new javax.swing.JSeparator();
    GlobalItemInputs = new javax.swing.JMenuItem();
    GlobalItemOutputs = new javax.swing.JMenuItem();
    HelpMenu = new javax.swing.JMenu();
    HelpItemHelp = new javax.swing.JMenuItem();
    jSeparator4 = new javax.swing.JSeparator();
    HelpItemAbout = new javax.swing.JMenuItem();

    FileOpenAction.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileOpenActionActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("Fizzim");
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(java.awt.event.ComponentEvent evt) {
        formComponentResized(evt);
      }
    });
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jPanel3.setLayout(new java.awt.GridBagLayout());

    jPanel3.setMinimumSize(new java.awt.Dimension(100, 100));
    jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
    // jTabbedPane1.setMinimumSize(getScrollPaneSize());
    // jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 685));
    // jScrollPane1.setMaximumSize(new Dimension(maxW, maxH));
    // jScrollPane1.setMinimumSize(new Dimension(maxW, maxH));
    // jScrollPane1.setPreferredSize(new Dimension(maxW, maxH));
    jPanel1 = drawArea1;
    org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(
        jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(0, 1294,
        Short.MAX_VALUE));
    jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(0, 1277,
        Short.MAX_VALUE));
    jScrollPane1.setViewportView(jPanel1);

    // pages
    /*
     * 
     * System.err.println("test");
     * 
     * try {
     * throw new RuntimeException("Test");
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     */

    jTabbedPane1.addBlankTab("Create New Page", new JPanel());
    jTabbedPane1.addTab("Page 1", jScrollPane1);
    jTabbedPane1.setSelectedIndex(1);
    jTabbedPane1.setBackgroundAt(0, new Color(200, 200, 200));
    jTabbedPane1.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        TabChanged(e);
      }
    });

    jTabbedPane1.addMouseListener(new MouseListener() {

      public void mouseClicked(MouseEvent arg0) {
        if (arg0.getButton() == MouseEvent.BUTTON3 || arg0.getModifiers() == 20)
        {
          JTabbedPane tabbedPane = (JTabbedPane) arg0.getSource();
          int tab = tabbedPane.indexAtLocation(arg0.getX(), arg0.getY());
          if (tab > 0)
            renameTab(tab);
        }
      }

      public void mouseEntered(MouseEvent arg0) {
      }

      public void mouseExited(MouseEvent arg0) {
      }

      public void mousePressed(MouseEvent arg0) {
      }

      public void mouseReleased(MouseEvent arg0) {
      }

    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    jPanel3.add(jTabbedPane1, gridBagConstraints);

    getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

    Dimension coord = getScrollPaneSize();
    if (coord.height > maxH)
      coord.setSize(coord.width, maxH);
    if (coord.width > maxW)
      coord.setSize(maxW + 23, coord.height);

    jTabbedPane1.setMinimumSize(coord);
    jTabbedPane1.setSize(coord);
    jPanel3.doLayout();
    jPanel3.repaint();

    FileMenu.setText("File");
    FileMenu.setMnemonic(java.awt.event.KeyEvent.VK_F);
    FileItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
        java.awt.event.KeyEvent.VK_N,
        java.awt.event.InputEvent.CTRL_MASK));
    FileItemNew.setMnemonic(java.awt.event.KeyEvent.VK_N);
    FileItemNew.setText("New");
    FileItemNew.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileItemNewActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemNew);

    FileItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
        java.awt.event.KeyEvent.VK_O,
        java.awt.event.InputEvent.CTRL_MASK));
    FileItemOpen.setMnemonic(java.awt.event.KeyEvent.VK_O);
    FileItemOpen.setText("Open");
    FileItemOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileItemOpenActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemOpen);

    FileItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
        java.awt.event.KeyEvent.VK_S,
        java.awt.event.InputEvent.CTRL_MASK));
    FileItemSave.setMnemonic(java.awt.event.KeyEvent.VK_S);
    FileItemSave.setText("Save");
    FileItemSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileItemSaveActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemSave);

    FileItemSaveAs.setText("Save As");
    FileItemSaveAs.setMnemonic(java.awt.event.KeyEvent.VK_A);
    FileItemSaveAs.setDisplayedMnemonicIndex(5);
    FileItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileItemSaveAsActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemSaveAs);

    /* Save as 6 lines file */
    FileItemSaveAs6Lines.setText("Save As 6 lines");
    FileItemSaveAs6Lines.setMnemonic(java.awt.event.KeyEvent.VK_L);
    FileItemSaveAs6Lines.setDisplayedMnemonicIndex(11);
    FileItemSaveAs6Lines.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileItemSaveAs6LinesActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemSaveAs6Lines);

    // export

    FileExportClipboard.setText("Clipboard");
    FileExportClipboard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
        java.awt.event.KeyEvent.VK_F2, 0));
    FileExportClipboard.setMnemonic(java.awt.event.KeyEvent.VK_C);
    FileExportClipboard.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileExportClipboardActionPerformed(evt);
      }
    });
    FileExport.add(FileExportClipboard);

    FileExportPNG.setText("PNG");
    FileExportPNG.setMnemonic(java.awt.event.KeyEvent.VK_P);
    FileExportPNG.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileExportPNGActionPerformed(evt);
      }
    });
    FileExport.add(FileExportPNG);

    FileExportJPEG.setText("JPEG");
    FileExportJPEG.setMnemonic(java.awt.event.KeyEvent.VK_J);
    FileExportJPEG.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileExportJPEGActionPerformed(evt);
      }
    });
    FileExport.add(FileExportJPEG);
    FileExport.setMnemonic(java.awt.event.KeyEvent.VK_E);

    FileMenu.add(FileExport);

    FileMenu.add(jSeparator1);

    FilePref.setText("Preferences");
    FilePref.setMnemonic(java.awt.event.KeyEvent.VK_R);
    FilePref.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FilePrefActionPerformed(evt);
      }
    });

    FileMenu.add(FilePref);

    FileItemPageSetup.setText("Page Setup");
    FileItemPageSetup.setMnemonic(java.awt.event.KeyEvent.VK_U);
    FileItemPageSetup
        .addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            FileItemPageSetupActionPerformed(evt);
          }
        });

    FileMenu.add(FileItemPageSetup);

    FileItemPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
        java.awt.event.KeyEvent.VK_P,
        java.awt.event.InputEvent.CTRL_MASK));
    FileItemPrint.setMnemonic(java.awt.event.KeyEvent.VK_P);
    FileItemPrint.setText("Print");
    FileItemPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileItemPrintActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemPrint);

    FileMenu.add(jSeparator2);

    FileItemExit.setText("Exit");
    FileItemExit.setMnemonic(java.awt.event.KeyEvent.VK_X);
    FileItemExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileItemExitActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemExit);

    MenuBar.add(FileMenu);

    EditMenu.setText("Edit");
    EditMenu.setMnemonic(java.awt.event.KeyEvent.VK_E);
    EditItemUndo.setMnemonic(java.awt.event.KeyEvent.VK_U);
    EditItemUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
        java.awt.event.KeyEvent.VK_Z,
        java.awt.event.InputEvent.CTRL_MASK));
    EditItemUndo.setText("Undo");
    EditItemUndo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        EditItemUndoActionPerformed(evt);
      }
    });

    EditMenu.add(EditItemUndo);
    EditItemRedo.setMnemonic(java.awt.event.KeyEvent.VK_R);
    EditItemRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
        java.awt.event.KeyEvent.VK_Y,
        java.awt.event.InputEvent.CTRL_MASK));
    EditItemRedo.setText("Redo");
    EditItemRedo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        EditItemRedoActionPerformed(evt);
      }
    });

    EditMenu.add(EditItemRedo);
    EditItemDelete.setMnemonic(java.awt.event.KeyEvent.VK_D);
    EditItemDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
        java.awt.event.KeyEvent.VK_DELETE, 0));
    EditItemDelete.setText("Delete");
    // EditItemDelete.setVisible(false);
    EditItemDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        EditItemDeleteActionPerformed(evt);
      }
    });

    EditMenu.add(EditItemDelete);

    MenuBar.add(EditMenu);

    GlobalMenu.setText("Global Attributes");
    GlobalMenu.setMnemonic(java.awt.event.KeyEvent.VK_G);

    GlobalItemMachine.setText("State Machine");
    GlobalItemMachine.setMnemonic(java.awt.event.KeyEvent.VK_M);
    GlobalItemMachine
        .addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            GlobalItemMachineActionPerformed(evt);
          }
        });

    GlobalMenu.add(GlobalItemMachine);

    GlobalItemInputs.setMnemonic(java.awt.event.KeyEvent.VK_I);
    GlobalItemInputs.setText("Inputs");
    GlobalItemInputs.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GlobalItemInputsActionPerformed(evt);
      }
    });

    GlobalMenu.add(GlobalItemInputs);

    GlobalItemOutputs.setMnemonic(java.awt.event.KeyEvent.VK_O);
    GlobalItemOutputs.setText("Outputs");
    GlobalItemOutputs
        .addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            GlobalItemOutputsActionPerformed(evt);
          }
        });

    GlobalMenu.add(GlobalItemOutputs);

    GlobalMenu.add(jSeparator3);

    GlobalItemStates.setText("States");
    GlobalItemStates.setMnemonic(java.awt.event.KeyEvent.VK_S);
    GlobalItemStates.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        GlobalItemStatesActionPerformed(evt);
      }
    });

    GlobalMenu.add(GlobalItemStates);

    GlobalItemTransitions.setText("Transitions");
    GlobalItemTransitions.setMnemonic(java.awt.event.KeyEvent.VK_T);
    GlobalItemTransitions
        .addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            GlobalItemTransitionsActionPerformed(evt);
          }
        });

    GlobalMenu.add(GlobalItemTransitions);

    MenuBar.add(GlobalMenu);

    HelpMenu.setMnemonic(java.awt.event.KeyEvent.VK_H);
    HelpMenu.setText("Help");
    HelpItemHelp.setMnemonic(java.awt.event.KeyEvent.VK_H);
    HelpItemHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
        java.awt.event.KeyEvent.VK_F1, 0));
    HelpItemHelp.setText("Help");
    HelpItemHelp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        HelpItemHelpActionPerformed(evt);
      }
    });

    HelpMenu.add(HelpItemHelp);

    HelpMenu.add(jSeparator4);

    HelpItemAbout.setMnemonic(java.awt.event.KeyEvent.VK_A);
    HelpItemAbout.setText("About");

    HelpItemAbout.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        HelpItemAboutActionPerformed(evt);
      }
    });
    HelpMenu.add(HelpItemAbout);

    MenuBar.add(HelpMenu);

    setJMenuBar(MenuBar);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  protected void HelpItemAboutActionPerformed(ActionEvent evt) {
    new HelpItemAboutActionPerformed();
  }

  /**
   * This is used to display the Help image
   */
  @SuppressWarnings("serial")
  static class HelpItemAboutActionPerformed extends JWindow
  {
    HelpItemAboutActionPerformed() {
      /* Display the help */
      JLabel label = null;
      URL url = getClass().getResource("splash.png");
      if (url != null)
      {
        ImageIcon imgIcon = new ImageIcon(url);
        label = new JLabel(imgIcon);
      } else
        System.err.println("Couldn't find the file : "
            + getClass().getResource("splash.png"));

      Dimension labelSize = label.getPreferredSize();
      label.setBounds(0, 0, (int) labelSize.getWidth(), (int) labelSize
          .getHeight());

      JLabel vers = new JLabel("v" + currVer);
      vers.setFont(new Font("Arial", Font.BOLD, 22));
      Dimension versSize = vers.getPreferredSize();
      vers.setBounds(176, 65, (int) versSize.getWidth(), (int) versSize
          .getHeight());

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

      JLayeredPane layer = new JLayeredPane();
      layer.setPreferredSize(labelSize);
      layer.setOpaque(false);
      layer.add(label, new Integer(0));
      layer.add(vers, new Integer(1));
      getContentPane().add(layer);
      pack();
      layer.setLocation(screenSize.width / 2 - (labelSize.width / 2),
          screenSize.height / 2 - (labelSize.height / 2));

      setLocation(screenSize.width / 2 - (labelSize.width / 2),
          screenSize.height / 2 - (labelSize.height / 2));
      setVisible(true);
      addMouseListener(new MouseAdapter()
      {
        public void mousePressed(MouseEvent e)
        {
          setVisible(false);
          dispose();
        }
      });
    }
  }

  protected void HelpItemHelpActionPerformed(ActionEvent evt) {
    JOptionPane
        .showMessageDialog(
            this,
            "Help is available online.  Try http://www.fizzim.com/index.php/tutorial",
            "Online Tutorial",
            JOptionPane.INFORMATION_MESSAGE);
  }

  protected void FilePrefActionPerformed(ActionEvent evt) {
    new Pref(this, true, drawArea1).setVisible(true);

  }

  protected void FileExportPNGActionPerformed(ActionEvent evt) {

    try {
      ExportChooser.setCurrentDirectory(currFile);
      ExportChooser.showSaveDialog(this);
    } catch (java.awt.HeadlessException e1) {
      e1.printStackTrace();
    }
    if (ExportChooser.getSelected())
      tryToSave(ExportChooser.getSelectedFile(), "png", true);

  }

  private void exportFile(File file, String type)
  {
    try {
      ImageIO.write(getImage(), type, file);
    } catch (IOException e) {
    }
  }

  protected void FileExportJPEGActionPerformed(ActionEvent evt) {
    try {
      ExportChooser.setCurrentDirectory(currFile);
      ExportChooser.showSaveDialog(this);
    } catch (java.awt.HeadlessException e1) {
      e1.printStackTrace();
    }
    if (ExportChooser.getSelected())
      tryToSave(ExportChooser.getSelectedFile(), "jpg", true);
  }

  protected void FileExportClipboardActionPerformed(ActionEvent evt) {
    BufferedImage bufferedImage = new BufferedImage(maxW, maxH,
        BufferedImage.TYPE_INT_RGB);
    Graphics2D tempG = bufferedImage.createGraphics();
    drawArea1.unselectObjs();
    drawArea1.paintComponent(tempG);

    // get rid of white space
    int lX = -1;
    int rX = -1;
    int tY = -1;
    int bY = -1;
    int h = bufferedImage.getHeight() - 1;
    int w = bufferedImage.getWidth() - 1;

    // get top
    for (int y = 0; y < h; y++)
      for (int x = 0; x < w; x++)
        if (bufferedImage.getRGB(x, y) != -1)
          bY = y;

    // get bottom
    for (int y = h; y >= 0; y--)
      for (int x = 0; x < w; x++)
        if (bufferedImage.getRGB(x, y) != -1)
          tY = y;

    // get left
    for (int x = 0; x < w; x++)
      for (int y = 0; y < h; y++)
        if (bufferedImage.getRGB(x, y) != -1)
          rX = x;

    // get right
    for (int x = w; x >= 0; x--)
      for (int y = 0; y < h; y++)
        if (bufferedImage.getRGB(x, y) != -1)
          lX = x;

    // if it worked correctly, make a cropped bufferedimage
    if (lX != -1 && rX != -1 && tY != -1 && bY != -1)
    {
      // System.out.println(lX +" "+rX+" "+tY+" "+bY);
      // if(lX-1<0)
      // lX=1;
      // if(tY-1<0)
      bufferedImage = bufferedImage.getSubimage(lX - 1, tY - 1, rX - lX + 3, bY
          - tY + 3);
    }

    ImageToClip imageToClip = new ImageToClip(bufferedImage);

  }

  private RenderedImage getImage()
  {
    BufferedImage bufferedImage = new BufferedImage(maxW, maxH,
        BufferedImage.TYPE_INT_RGB);
    Graphics2D tempG = bufferedImage.createGraphics();
    drawArea1.paintComponent(tempG);

    return bufferedImage;
  }

  protected void renameTab(int tab) {

    String s = (String) JOptionPane.showInputDialog(
        this,
        "Edit Tab Name:\n",
        "Edit Tab Name",
        JOptionPane.PLAIN_MESSAGE,
        null,
        null,
        jTabbedPane1.getTitleAt(tab));

    if (s != null)
    {
      if (getPageIndex(s) == -1)
        jTabbedPane1.setTitleAt(tab, s);
      else
        JOptionPane.showMessageDialog(this,
            "Page must have unique name",
            "error",
            JOptionPane.ERROR_MESSAGE);
    }
  }

  protected void TabChanged(ChangeEvent e) {

    if (!loading)
    {
      JTabbedPane pane = (JTabbedPane) e.getSource();

      // Get current tab
      int sel = pane.getSelectedIndex();
      // fill all but current tab with empty panels
      for (int i = 1; i < jTabbedPane1.getTabCount(); i++)
      {
        if (i != sel)
          jTabbedPane1.setComponentAt(i, new JPanel());
      }

      if (sel == 0)
      {
        int index = jTabbedPane1.getTabCount();
        jTabbedPane1.addTab("Page " + String.valueOf(index), jScrollPane1);
        jTabbedPane1.setSelectedIndex(index);
        drawArea1.setCurrPage(index);
      }
      else
      {
        // set current tab
        drawArea1.setCurrPage(sel);
        jTabbedPane1.setComponentAt(sel, jScrollPane1);
      }
      drawArea1.unselectObjs();
    }
  }

  protected void FileOpenActionActionPerformed(ActionEvent evt) {

  }

  // GEN-FIRST:event_FileItemPageSetupActionPerformed
  private void FileItemPageSetupActionPerformed(java.awt.event.ActionEvent evt) {
    new PageSetup(this, true).setVisible(true);
  }// GEN-LAST:event_FileItemPageSetupActionPerformed

  // GEN-FIRST:event_formComponentResized
  private void formComponentResized(java.awt.event.ComponentEvent evt) {
    // this method makes sure that the draw area size is mostly restricted
    // to dimensions set in page setup

    Dimension coord = getScrollPaneSize();
    if (coord.height > maxH)
      coord.setSize(coord.width, maxH);
    if (coord.width > maxW)
      coord.setSize(maxW + 23, coord.height);
    drawArea1.setSize(maxW, maxH);
    jTabbedPane1.setMinimumSize(coord);
    jTabbedPane1.setSize(coord);
    jPanel3.doLayout();
    jPanel3.repaint();

  }// GEN-LAST:event_formComponentResized

  protected void formWindowClosing(WindowEvent evt) {
    formWindowClosing();

  }

  // GEN-FIRST:event_FileItemSaveActionPerformed
  private void FileItemSaveActionPerformed(java.awt.event.ActionEvent evt) {
    if (currFile == null) {
      try {
        // Default to cwd
        // FileSaveAction.setCurrentDirectory(new
        // java.io.File("").getAbsoluteFile());
        FileSaveAction.setCurrentDirectory(new java.io.File(System
            .getProperty("user.dir")).getAbsoluteFile());
        FileSaveAction.showSaveDialog(this);
      } catch (java.awt.HeadlessException e1) {
        e1.printStackTrace();
      }
      if (FileSaveAction.getSelected())
        if (tryToSave(FileSaveAction.getSelectedFile(), "fzm", true))
          setTitle("Fizzim - " + currFile.getName());

    } else {
      setTitle("Fizzim - " + currFile.getName());
      saveFile(currFile);
    }
  }// GEN-LAST:event_FileItemSaveActionPerformed

  public boolean tryToSave(File file, String type, boolean overrideCheck)
  {
    // checks file for correct pathname
    String temp = file.getName().toLowerCase();

    if (!temp.endsWith("." + type))
      file = new File(file.getAbsolutePath() + "." + type);

    // checks permission to write
    if (file.isDirectory())
    {
      JOptionPane.showMessageDialog(this,
          "Must be a file, not directory", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    else if (file.exists() && !file.canWrite())
    {
      JOptionPane.showMessageDialog(this,
          "Cannot write to file, permission denied.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    else if (overrideCheck)
    {
      if (file.exists()) {
        int choice = JOptionPane.showConfirmDialog(this,
            "Overwrite file?", "Save As",
            JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION)
          return false;
      }
    }
    else if (!file.exists())
    {
      try {
        file.createNewFile();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this,
            "Cannot write to file, permission denied.", "Error",
            JOptionPane.ERROR_MESSAGE);
        return false;
      }
    }
    if (type.equals("fzm"))
    {
      if (!saveFile(file))
        return false;
    }
    else
      exportFile(file, type);
    return true;
  }

  /**
   * Do the basic checking before saving in the 6 lines format text
   * 
   * @param file
   * @param type
   * @param overrideCheck
   * @return
   */
  public boolean tryToSave6lines(File file, String type, boolean overrideCheck)
  {
    currFile = file;
    // checks file for correct pathname
    String temp = file.getName().toLowerCase();

    if (!temp.endsWith("." + type))
      file = new File(file.getAbsolutePath() + "." + type);

    // checks permission to write
    if (file.isDirectory())
    {
      JOptionPane.showMessageDialog(this,
          "Must be a file, not directory", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    else if (file.exists() && !file.canWrite())
    {
      JOptionPane.showMessageDialog(this,
          "Cannot write to file, permission denied.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    else if (overrideCheck)
    {
      if (file.exists()) {
        int choice = JOptionPane.showConfirmDialog(this,
            "Overwrite file?", "Save As",
            JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION)
          return false;
      }
    }
    else if (!file.exists())
    {
      try {
        file.createNewFile();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this,
            "Cannot write to file, permission denied.", "Error",
            JOptionPane.ERROR_MESSAGE);
        return false;
      }
    }

    if (!saveFile6lines(file)) {
      return false;
    }
    return true;
  }

  // perform checks before window is closed
  private void formWindowClosing() {
    if (drawArea1.getFileModifed()) {
      if (currFile == null) {
        Object[] options = { "Yes", "No", "Cancel" };

        int n = JOptionPane
            .showOptionDialog(this, "Save file before closing?",
                "Fizzim", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options,
                options[0]);
        if (n == JOptionPane.YES_OPTION) {
          try {
            FileSaveAction.setCurrentDirectory(new java.io.File(System
                .getProperty("user.dir")).getAbsoluteFile());
            FileSaveAction.showSaveDialog(this);
          } catch (java.awt.HeadlessException e1) {
            e1.printStackTrace();
          }
          if (FileSaveAction.getSelected())
          {
            if (tryToSave(FileSaveAction.getSelectedFile(), "fzm", true))
              System.exit(0);
          }
        } else if (n == JOptionPane.NO_OPTION) {
          System.exit(0);
        }
      } else {
        Object[] options = { "Yes", "No", "Cancel" };

        int n = JOptionPane
            .showOptionDialog(this, "Save changes to "
                + currFile.getName() + "?", "Fizzim",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options,
                options[0]);
        if (n == JOptionPane.YES_OPTION) {
          if (saveFile(currFile))
            System.exit(0);
        } else if (n == JOptionPane.NO_OPTION) {
          System.exit(0);
        }
      }
    } else
      System.exit(0);
  }// GEN-LAST:event_formWindowClosing

  // GEN-FIRST:event_FileItemPrintActionPerformed
  private void FileItemPrintActionPerformed(java.awt.event.ActionEvent evt) {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(drawArea1);
    if (printJob.printDialog())
      try {
        printJob.print();
      } catch (PrinterException pe) {
        System.out.println("Error printing: " + pe);
      }
  }// GEN-LAST:event_FileItemPrintActionPerformed

  // GEN-FIRST:event_EditItemDeleteActionPerformed
  private void EditItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {
    drawArea1.delete();
  }// GEN-LAST:event_EditItemDeleteActionPerformed

  // GEN-FIRST:event_EditItemRedoActionPerformed
  private void EditItemRedoActionPerformed(java.awt.event.ActionEvent evt) {
    drawArea1.redo();
  }// GEN-LAST:event_EditItemRedoActionPerformed

  // GEN-FIRST:event_EditItemUndoActionPerformed
  private void EditItemUndoActionPerformed(java.awt.event.ActionEvent evt) {
    drawArea1.undo();
  }// GEN-LAST:event_EditItemUndoActionPerformed

  // GEN-FIRST:event_FileItemExitActionPerformed
  private void FileItemExitActionPerformed(java.awt.event.ActionEvent evt) {
    formWindowClosing();
  }// GEN-LAST:event_FileItemExitActionPerformed

  // GEN-FIRST:event_FileItemNewActionPerformed
  private void FileItemNewActionPerformed(java.awt.event.ActionEvent evt) {

    boolean createNew = true;
    if (drawArea1.getFileModifed()) {
      if (currFile == null) {
        Object[] options = { "Yes", "No", "Cancel" };

        int n = JOptionPane
            .showOptionDialog(this, "Save file before creating new file?",
                "Fizzim", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options,
                options[0]);

        if (n == JOptionPane.YES_OPTION) {
          try {
            FileSaveAction.setCurrentDirectory(new java.io.File(System
                .getProperty("user.dir")).getAbsoluteFile());
            FileSaveAction.showSaveDialog(this);
          } catch (java.awt.HeadlessException e1) {
            e1.printStackTrace();
          }
          if (FileSaveAction.getSelected())
            tryToSave(FileSaveAction.getSelectedFile(), "fzm", true);
        }
        else if (n == JOptionPane.CANCEL_OPTION || n == -1)
          createNew = false;
      } else {
        Object[] options = { "Yes", "No", "Cancel" };

        int n = JOptionPane
            .showOptionDialog(this, "Save changes to "
                + currFile.getName() + "?", "Fizzim",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options,
                options[0]);
        if (n == JOptionPane.YES_OPTION)
        {
          if (!saveFile(currFile))
            createNew = false;
        }
        else if (n == JOptionPane.CANCEL_OPTION || n == -1)
          createNew = false;
      }
    }
    if (createNew)
    {
      for (int i = jTabbedPane1.getTabCount() - 1; i > 1; i--)
      {
        jTabbedPane1.remove(i);
      }
      jTabbedPane1.setComponentAt(1, jScrollPane1);
      currFile = null;
      setTitle("Fizzim");
      for (int i = 0; i < globalList.size(); i++)
      {
        globalList.get(i).clear();
      }
      initGlobal();
      drawArea1.open(globalList);
    }

  }// GEN-LAST:event_FileItemNewActionPerformed

  public void resetTabs() {
    for (int i = jTabbedPane1.getTabCount() - 1; i > 0; i--)
    {
      jTabbedPane1.remove(i);
    }

  }

  public void addNewTab(String line) {
    jTabbedPane1.addTab(line, new JPanel());

  }

  private void GlobalItemMachineActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_GlobalItemMachineActionPerformed
    globalList = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, globalList, 0)
        .setVisible(true);
  }// GEN-LAST:event_GlobalItemMachineActionPerformed

  private void GlobalItemInputsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_GlobalItemsInputsActionPerformed
    globalList = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, globalList, 1)
        .setVisible(true);
  }// GEN-LAST:event_GlobalItemsInputsActionPerformed

  private void GlobalItemOutputsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_GlobalItemOutputsActionPerformed
    globalList = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, globalList, 2)
        .setVisible(true);
    /*
     * GlobalAttributesFrame.setSize(600, 300);
     * GlobalAttributesFrame.getRootPane().setDefaultButton(GACancel);
     * GlobalAttributesTabbedPane.setSelectedComponent(GAOutputsScrollPane);
     * GlobalAttributesFrame.show();
     */

  }// GEN-LAST:event_GlobalItemOutputsActionPerformed

  private void GlobalItemStatesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_GlobalItemStatesActionPerformed
    globalList = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, globalList, 3)
        .setVisible(true);
  }// GEN-LAST:event_GlobalItemStatesActionPerformed

  private void GlobalItemTransitionsActionPerformed(
      java.awt.event.ActionEvent evt) {// GEN-FIRST:event_GlobalItemTransitionsActionPerformed
    globalList = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, globalList, 4)
        .setVisible(true);
  }// GEN-LAST:event_GlobalItemTransitionsActionPerformed

  private void FileItemSaveAsActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      if (currFile == null)
      {
        // Default to cwd
        FileSaveAction.setCurrentDirectory(new java.io.File(System
            .getProperty("user.dir")).getAbsoluteFile());
      }
      else
        FileSaveAction.setSelectedFile(currFile);

      FileSaveAction.setCurrentDirectory(new java.io.File(System
          .getProperty("user.dir")).getAbsoluteFile());
      FileSaveAction.showSaveDialog(this);
    } catch (java.awt.HeadlessException e1) {
      e1.printStackTrace();
    }
    if (FileSaveAction.getSelected())
      if (tryToSave(FileSaveAction.getSelectedFile(), "fzm", true))
        setTitle("Fizzim - " + currFile.getName());

  }

  /**
   * Allows to save the file in a 6 lines format
   * 
   * @param evt
   */
  private void FileItemSaveAs6LinesActionPerformed(
      java.awt.event.ActionEvent evt) {
    try {
      if (currFile == null)
      {
        // Default to cwd
        FileSave6LinesAction.setCurrentDirectory(new java.io.File(System
            .getProperty("user.dir")).getAbsoluteFile());
      }
      else
        FileSave6LinesAction.setSelectedFile(currFile);

      FileSave6LinesAction.setCurrentDirectory(new java.io.File(System
          .getProperty("user.dir")).getAbsoluteFile());
      FileSave6LinesAction.showSaveDialog(this);
    } catch (java.awt.HeadlessException e1) {
      e1.printStackTrace();
    }
    if (FileSave6LinesAction.getSelected())
      if (tryToSave6lines(FileSave6LinesAction.getSelectedFile(), "txt", true))
        setTitle("Graphe 6 lignes - " + currFile.getName());

  }

  private void FileItemOpenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_FileItemOpenActionPerformed
    boolean open = true;
    if (drawArea1.getFileModifed()) {
      Object[] options = { "Yes", "No", "Cancel" };

      int n = JOptionPane
          .showOptionDialog(this, "Save file before opening file?",
              "Fizzim", JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, options,
              options[0]);
      if (n == JOptionPane.YES_OPTION) {
        try {
          FileSaveAction.setCurrentDirectory(currFile);
          FileSaveAction.showSaveDialog(this);
        } catch (java.awt.HeadlessException e1) {
          e1.printStackTrace();
        }
        if (FileSaveAction.getSelected())
        {
          if (!tryToSave(FileSaveAction.getSelectedFile(), "fzm", true))
            open = false;
        }
      }
      else if (n == JOptionPane.CANCEL_OPTION || n == -1)
        open = false;
    }
    if (open)
    {
      try {

        if (currFile == null)
          // Default to cwd
          // FileOpenAction.setCurrentDirectory(new
          // java.io.File("").getAbsoluteFile());
          FileOpenAction.setCurrentDirectory(new java.io.File(System
              .getProperty("user.dir")).getAbsoluteFile());
        else
          FileOpenAction.setCurrentDirectory(currFile);
        FileOpenAction.showOpenDialog(null);
      } catch (java.awt.HeadlessException e1) {
        e1.printStackTrace();
      }

      if (FileOpenAction.getSelected())
      {
        File tempFile = FileOpenAction.getSelectedFile();
        String fileName = tempFile.getName().toLowerCase();
        if (!tempFile.isDirectory() && fileName.endsWith(".fzm")) {
          openFile(tempFile);
          setTitle("Fizzim - " + currFile.getName());
        } else {
          JOptionPane.showMessageDialog(this, "File must end with .fzm",
              "error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }

  }// GEN-LAST:event_FileItemOpenActionPerformed

  private void openFile(File selectedFile) {
    loading = true;
    currFile = selectedFile;
    FileParser fileParser = new FileParser(currFile, this, drawArea1);
    jTabbedPane1.setComponentAt(1, jScrollPane1);
    jTabbedPane1.setSelectedIndex(1);
    drawArea1.setCurrPage(1);
    loading = false;

  }

  private Dimension getScrollPaneSize() {
    return new Dimension(jPanel3.getWidth() - 10, jPanel3.getHeight() - 10);
  }

  // set indentation (also exists in ObjAttribute.java and GeneralObj.java
  public String i(int indent)
  {
    String ind = "";
    for (int i = 0; i < indent; i++)
    {
      ind = ind + "   ";
    }
    return ind;
  }

  /**
   * Function that save the graph into a 6lines format text
   * 
   * @param selectedFile
   *          the targeted file to save in
   * @return
   */

  private boolean saveFile6lines(File selectedFile) {
    currFile = selectedFile;
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(currFile));
      // Put all the objects of the graph in object
      Vector<Object> object = drawArea1.getObjList();
      for (int i = 1; i < object.size(); i++) {
        GeneralObj temp = (GeneralObj) object.get(i);
        // verify that the object is a transition.
        if (temp instanceof TransitionObj) {
          TransitionObj transition = (TransitionObj) temp;
          writer.write(selectedFile.getName().substring(0, selectedFile.getName().length()-4));
          writer.newLine();
          StateObj initial_state = transition.getStartState();
          // write the number of the initial state in the file.
          writer.write(initial_state.getName());
          writer.newLine();
          StateObj final_state = transition.getEndState();
          /*
           * Verify if the transition is a loopback and
           * write the number of the final state in the file (it will be
           * the same as the initial state if it's a loop).
           */
          if (temp instanceof LoopbackTransitionObj) {
            writer.write(initial_state.getName());
            writer.newLine();
          } else {
            writer.write(final_state.getName());
            writer.newLine();
          }
          LinkedList<ObjAttribute> attributes = transition.getAttributeList();
          String event = null, condition = null, actions = null;
          for (ObjAttribute attribute : attributes) {
            String name = (String) attribute.get(0);
            String value = (String) attribute.get(1);
            if (name.equals(event_field)) {
              event = value;
            } else if (name.equals(condition_field)) {
              condition = value;
            } else if (name.equals(action_field)) {
              actions = value;
            }

          }
          if (event == null || condition == null || actions == null) {
            writer.close();
            throw new IOException(
                "One of the variable event, condition and actions are null");
          }
          writer.write(event + " " + event_field);
          writer.newLine();
          writer.write(condition + " " + condition_field );
          writer.newLine();
          writer.write(actions + " " + action_field);
          writer.newLine();
        }
      }
      writer.close();
      return true;

    } catch (IOException ex) {
      // ex.printStackTrace();
      JOptionPane.showMessageDialog(this,
          "Error saving file",
          "error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }

  }

  private boolean saveFile(File selectedFile) {
    currFile = selectedFile;
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(currFile));

      Date currDate = new Date();
      long currTime = currDate.getTime();
      DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
      DateFormat dt = DateFormat.getTimeInstance(DateFormat.MEDIUM);
      writer.write("## File last modified by Fizzim: " + dt.format(currTime)
          + " on " + df.format(currDate) + "\n");
      writer.write("<version>\n" + i(1) + currVer + "\n" + "</version>\n");

      // save global lists
      LinkedList<ObjAttribute> tempList;
      writer.write("<globals>\n");

      writer.write(i(1) + "<machine>\n");
      tempList = (LinkedList<ObjAttribute>) globalList.get(0);
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</machine>\n");

      writer.write(i(1) + "<inputs>\n");
      tempList = (LinkedList<ObjAttribute>) globalList.get(1);
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</inputs>\n");

      writer.write(i(1) + "<outputs>\n");
      tempList = (LinkedList<ObjAttribute>) globalList.get(2);
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</outputs>\n");

      writer.write(i(1) + "<state>\n");
      tempList = (LinkedList<ObjAttribute>) globalList.get(3);
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</state>\n");

      writer.write(i(1) + "<trans>\n");
      tempList = (LinkedList<ObjAttribute>) globalList.get(4);
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</trans>\n");

      writer.write("</globals>\n");

      writer.write("<tabs>\n");
      for (int i = 1; i < jTabbedPane1.getTabCount(); i++)
      {
        writer.write(i(1) + jTabbedPane1.getTitleAt(i) + "\n");
      }
      writer.write("</tabs>\n");

      // can save function on draw area, which will loop through objects
      drawArea1.save(writer);

      writer.close();
      drawArea1.setFileModifed(false);

      return true;

    } catch (IOException ex) {
      // ex.printStackTrace();
      JOptionPane.showMessageDialog(this,
          "Error saving file",
          "error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }

  }

  public int getMaxH()
  {
    return maxH;
  }

  public int getMaxW()
  {
    return maxW;
  }

  public int getPages()
  {
    return jTabbedPane1.getTabCount();
  }

  private void removePage(int i)
  {
    // TODO
  }

  /**
   * @param args
   *          the command line arguments
   */
  static String clfilename = ""; // command-line filename
  static boolean clexit = false; // command-line -exit switch
  static boolean clbatch_rewrite = false; // command-line -batch_rewrite switch

  public static void main(String args[]) {
    /*
     * For the spash screen, the simplest way is to put :
     * SplashScreen-Image: splash.png
     * in the MANIFEST.MF file in ./META-INF in the .jar
     */

    // If one of the args is ends with .fzm, assume it is the file
    // to open.
    for (String s : args) {
      if (s.endsWith(".fzm")) {
        clfilename = s;
      }
      if (s.equals("-exit")) {
        clexit = true;
      }
      if (s.equals("-batch_rewrite")) {
        clbatch_rewrite = true;
      }
    }
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {

        // sets error file to write to
        final File file = new File("fizzim_errors.log");

        // make sure output file doesnt get too large
        FileOutputStream fout = null;
        try {
          fout = new FileOutputStream(file, true) {

            public void write(byte[] b, int off, int len) throws IOException
            {
              if (file.length() < 20000)
                super.write(b, off, len);
              System.out.write(b, off, len);
            }

            public void write(byte[] b) throws IOException
            {
              if (file.length() < 20000)
                super.write(b);
              System.out.write(b);
            }

            public void write(int b) throws IOException
            {
              if (file.length() < 20000)
                super.write(b);
              System.out.write(b);
            }
          };
        } catch (FileNotFoundException e) {
        }

        // sets std err to be written to file
        System.setErr(new PrintStream(fout));

        FizzimGui fzim = new FizzimGui();
        fzim.setVisible(true);
        fzim.setSize(new java.awt.Dimension(1000, 685));
        new HelpItemAboutActionPerformed();
        // If command line filename is not null, open
        // this file.
        if (clfilename != "") {
          System.err.println("Opening file " + clfilename);
          fzim.openFile(new File(clfilename));
          if (clbatch_rewrite) {
            System.err.println("Saving file " + clfilename);
            if (fzim.tryToSave(new File(clfilename), "fzm", false)) {
              System.err.println("Exiting");
              fzim.formWindowClosing();
            }
          }
        }
        if (clexit) {
          fzim.formWindowClosing();
        }

      }
    });
  }

  public String getclfilename() {
    return clfilename;
  }

  // GEN-BEGIN:variables
  // Variables declaration - do not modify
  private javax.swing.JMenuItem EditItemDelete;
  private javax.swing.JMenuItem EditItemRedo;
  private javax.swing.JMenuItem EditItemUndo;
  private javax.swing.JMenu EditMenu;
  private javax.swing.JMenuItem FileItemExit;
  private javax.swing.JMenuItem FileItemNew;
  private javax.swing.JMenuItem FileItemOpen;
  private javax.swing.JMenuItem FilePref;
  private javax.swing.JMenuItem FileItemPageSetup;
  private javax.swing.JMenuItem FileItemPrint;
  private javax.swing.JMenuItem FileItemSave;
  private javax.swing.JMenuItem FileItemSaveAs;
  private javax.swing.JMenuItem FileItemSaveAs6Lines;
  private javax.swing.JMenu FileExport;
  private javax.swing.JMenuItem FileExportClipboard;
  private javax.swing.JMenuItem FileExportPNG;
  private javax.swing.JMenuItem FileExportJPEG;
  private javax.swing.JMenu FileMenu;
  private MyJFileChooser FileOpenAction;
  private MyJFileChooser FileSaveAction;
  private MyJFileChooser FileSave6LinesAction;
  private MyJFileChooser ExportChooser;
  private javax.swing.JMenuItem GlobalItemInputs;
  private javax.swing.JMenuItem GlobalItemMachine;
  private javax.swing.JMenuItem GlobalItemOutputs;
  private javax.swing.JMenuItem GlobalItemStates;
  private javax.swing.JMenuItem GlobalItemTransitions;
  private javax.swing.JMenu GlobalMenu;
  private javax.swing.JMenuItem HelpItemAbout;
  private javax.swing.JMenuItem HelpItemHelp;
  private javax.swing.JMenu HelpMenu;
  private javax.swing.JMenuBar MenuBar;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JSeparator jSeparator3;
  private javax.swing.JSeparator jSeparator4;
  private MyJTabbedPane jTabbedPane1;
  // End of variables declaration//GEN-END:variables

  File currFile = null;
  private DrawArea drawArea1;

  public void updateGlobal(LinkedList<LinkedList<ObjAttribute>> globalList2) {
    globalList = globalList2;

  }

  public String getPageName(int i)
  {
    return jTabbedPane1.getTitleAt(i);
  }

  public int getPageIndex(String name) {
    for (int i = 1; i < jTabbedPane1.getTabCount(); i++)
    {
      if (jTabbedPane1.getTitleAt(i).equals(name))
        return i;
    }
    return -1;
  }

  public void setDASize(int w, int h)
  {
    maxW = w;
    maxH = h;

    drawArea1.setPreferredSize(new java.awt.Dimension(maxW, maxH));
    drawArea1.setMaximumSize(new java.awt.Dimension(maxW, maxH));
    drawArea1.setMinimumSize(new java.awt.Dimension(maxW, maxH));
    drawArea1.setSize(maxW, maxH);

    // try to clean up resized page
    drawArea1.updatePageConn();
    drawArea1.moveOnResize(maxW, maxH);

    Dimension coord = getScrollPaneSize();
    if (coord.height > maxH)
      coord.setSize(coord.width, maxH);
    if (coord.width > maxW)
      coord.setSize(maxW + 23, coord.height);

    jTabbedPane1.setMinimumSize(coord);
    jTabbedPane1.setSize(coord);
    jTabbedPane1.doLayout();
    jPanel3.doLayout();
    repaint();

  }

  class MyJFileChooser extends JFileChooser {

    boolean selected;

    MyJFileChooser(String type)
    {
      if (type.equals("fzm"))
        setFileFilter(new FzmFilter());
    }

    public void approveSelection()
    {
      selected = true;
      super.approveSelection();
    }

    public void cancelSelection()
    {
      selected = false;
      super.cancelSelection();
    }

    public boolean getSelected()
    {
      return selected;
    }
  };

  // following 3 classes use code from:
  // http://forum.java.sun.com/thread.jspa?threadID=337070&messageID=1429062

  class MyJTabbedPane extends JTabbedPane implements MouseListener {

    public MyJTabbedPane() {
      super();
      this.setUI(new MyBasicTabbedPaneUI());
      addMouseListener(this);

    }

    public void addTab(String title, Component component) {
      this.addTab(title, null, component);
    }

    public void addBlankTab(String title, Component component) {
      super.addTab(title, null, component);
    }

    public void addTab(String title, Icon extraIcon, Component component) {
      super.addTab(title, new CloseTabIcon(extraIcon), component);
    }

    public void mouseClicked(MouseEvent arg0) {
      int tab = getUI().tabForCoordinate(this, arg0.getX(), arg0.getY());
      if (tab < 2)
        return;
      Rectangle rect = ((CloseTabIcon) getIconAt(tab)).getBounds();
      if (rect.contains(arg0.getX(), arg0.getY())) {
        if (JOptionPane
            .showConfirmDialog(
                this,
                "Everything on this page will be permanently deleted. The undo/redo list will be reset. Delete Tab?",
                "Close Tab Option",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
          this.setSelectedIndex(tab - 1);
          this.removeTabAt(tab);
          drawArea1.removePage(tab);
          drawArea1.resetUndo();
        }
      }

    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }

  }

  public class MyBasicTabbedPaneUI extends BasicTabbedPaneUI {

    public MyBasicTabbedPaneUI() {
    }

    protected void layoutLabel(int tabPlacement, FontMetrics metrics,
        int tabIndex, String title, Icon icon, Rectangle tabRect,
        Rectangle iconRect, Rectangle textRect, boolean isSelected) {

      textRect.x = 0;
      textRect.y = 0;
      iconRect.x = 0;
      iconRect.y = 0;
      SwingUtilities.layoutCompoundLabel((JComponent) tabPane, metrics,
          title, icon, SwingUtilities.CENTER, SwingUtilities.CENTER,
          SwingUtilities.CENTER, SwingUtilities.LEFT, tabRect, iconRect,
          textRect, textIconGap + 2);

    }
  }

  class CloseTabIcon implements Icon {
    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private Icon fileIcon;

    public CloseTabIcon(Icon fileIcon) {
      this.fileIcon = fileIcon;
      width = 16;
      height = 16;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
      this.x_pos = x;
      this.y_pos = y;

      Color col = g.getColor();

      int y_p = y + 2;
      g.setColor(Color.gray);
      // g.drawLine(x+1, y_p, x+12, y_p);
      // g.drawLine(x+1, y_p+13, x+12, y_p+13);
      // g.drawLine(x, y_p+1, x, y_p+12);
      // g.drawLine(x+13, y_p+1, x+13, y_p+12);
      g.setColor(Color.black);
      g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
      g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
      g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
      g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
      g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
      g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
      g.setColor(col);
      if (fileIcon != null) {
        fileIcon.paintIcon(c, g, x + width, y_p);
      }
    }

    public int getIconWidth() {
      return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }

    public int getIconHeight() {
      return height;
    }

    public Rectangle getBounds() {
      return new Rectangle(x_pos, y_pos, width, height);
    }

  }

  class PageSetup extends javax.swing.JDialog {

    FizzimGui fizzim;

    /** Creates new form PageSetup */
    public PageSetup(FizzimGui _fizzim, boolean modal) {
      super(_fizzim, modal);
      fizzim = _fizzim;
      initComponents();
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
      jLabel1 = new javax.swing.JLabel();
      jLabel2 = new javax.swing.JLabel();
      jTextField1 = new javax.swing.JTextField();
      jTextField2 = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();
      jLabel4 = new javax.swing.JLabel();
      jButton1 = new javax.swing.JButton();
      jButton2 = new javax.swing.JButton();
      jLabel5 = new javax.swing.JLabel();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      jLabel1.setText("Width:");

      this.setTitle("Page Setup");

      jLabel2.setText("Height:");

      jTextField1.setText(String.valueOf(fizzim.getMaxW()));
      jTextField1.setColumns(4);
      jTextField2.setText(String.valueOf(fizzim.getMaxH()));
      jTextField2.setColumns(4);

      jLabel3.setText("pixels");

      jLabel4.setText("pixels");

      jButton1.setText("Cancel");
      jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          jButton1ActionPerformed(evt);
        }
      });

      jButton2.setText("OK");
      jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          jButton2ActionPerformed(evt);
        }
      });

      jLabel5.setText("Enter new dimensions:");

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
          getContentPane());
      getContentPane().setLayout(layout);
      layout
          .setHorizontalGroup(layout
              .createParallelGroup(
                  org.jdesktop.layout.GroupLayout.LEADING)
              .add(
                  org.jdesktop.layout.GroupLayout.TRAILING,
                  layout
                      .createSequentialGroup()
                      .addContainerGap(23, Short.MAX_VALUE)
                      .add(jButton2)
                      .addPreferredGap(
                          org.jdesktop.layout.LayoutStyle.RELATED)
                      .add(jButton1).addContainerGap())
              .add(
                  layout.createSequentialGroup()
                      .addContainerGap().add(jLabel5)
                      .addContainerGap(33, Short.MAX_VALUE))
              .add(
                  layout
                      .createSequentialGroup()
                      .addContainerGap()
                      .add(
                          layout
                              .createParallelGroup(
                                  org.jdesktop.layout.GroupLayout.LEADING)
                              .add(jLabel1).add(
                                  jLabel2))
                      .add(1, 1, 1)
                      .add(
                          layout
                              .createParallelGroup(
                                  org.jdesktop.layout.GroupLayout.LEADING)
                              .add(
                                  layout
                                      .createSequentialGroup()
                                      .add(
                                          jTextField2,
                                          org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                          org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                          org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                      .addPreferredGap(
                                          org.jdesktop.layout.LayoutStyle.RELATED)
                                      .add(
                                          jLabel4))
                              .add(
                                  layout
                                      .createSequentialGroup()
                                      .add(
                                          jTextField1,
                                          org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                          org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                          org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                      .addPreferredGap(
                                          org.jdesktop.layout.LayoutStyle.RELATED)
                                      .add(
                                          jLabel3)))
                      .addContainerGap(
                          org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                          Short.MAX_VALUE)));
      layout
          .setVerticalGroup(layout
              .createParallelGroup(
                  org.jdesktop.layout.GroupLayout.LEADING)
              .add(
                  org.jdesktop.layout.GroupLayout.TRAILING,
                  layout
                      .createSequentialGroup()
                      .addContainerGap()
                      .add(jLabel5)
                      .add(17, 17, 17)
                      .add(
                          layout
                              .createParallelGroup(
                                  org.jdesktop.layout.GroupLayout.BASELINE)
                              .add(jLabel1)
                              .add(
                                  jTextField1,
                                  org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                  org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                  org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                              .add(jLabel3))
                      .addPreferredGap(
                          org.jdesktop.layout.LayoutStyle.RELATED)
                      .add(
                          layout
                              .createParallelGroup(
                                  org.jdesktop.layout.GroupLayout.BASELINE)
                              .add(jLabel2)
                              .add(
                                  jTextField2,
                                  org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                  org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                  org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                              .add(jLabel4))
                      .addPreferredGap(
                          org.jdesktop.layout.LayoutStyle.RELATED,
                          16, Short.MAX_VALUE)
                      .add(
                          layout
                              .createParallelGroup(
                                  org.jdesktop.layout.GroupLayout.BASELINE)
                              .add(jButton1).add(
                                  jButton2))
                      .addContainerGap()));
      pack();
    }// </editor-fold>//GEN-END:initComponents

    // GEN-FIRST:event_jButton1ActionPerformed
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
      dispose();
    }// GEN-LAST:event_jButton1ActionPerformed

    // GEN-FIRST:event_jButton2ActionPerformed
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
      if (JOptionPane
          .showConfirmDialog(
              this,
              "You cannot undo the page resize action.  You can however set the size back to the original, but you may need to move objects back to their original location. Continue?",
              "Resize Page",
              JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
      {
        try {
          int w = Integer.parseInt(jTextField1.getText());
          int h = Integer.parseInt(jTextField2.getText());
          fizzim.setDASize(w, h);
        } catch (NumberFormatException nfe) {
          JOptionPane.showMessageDialog(this,
              "Integers only.",
              "error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
      dispose();
    }// GEN-LAST:event_jButton2ActionPerformed

    // GEN-BEGIN:variables
    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
  }

  class ImageToClip implements ClipboardOwner
  {
    private Clipboard clip;

    public ImageToClip(BufferedImage bi)
    {
      clip = Toolkit.getDefaultToolkit().getSystemClipboard();

      BiToClip clipIm = new BiToClip(bi);
      clip.setContents(clipIm, this);

    }

    public void lostOwnership(Clipboard arg0, Transferable arg1) {

    }

  }

  class BiToClip implements Transferable
  {
    private DataFlavor[] myFlavors = new DataFlavor[] { DataFlavor.imageFlavor };
    private BufferedImage image;

    public BiToClip(BufferedImage bi)
    {
      image = bi;
    }

    public Object getTransferData(DataFlavor arg0)
        throws UnsupportedFlavorException, IOException {
      if (arg0 != DataFlavor.imageFlavor) {
        throw new UnsupportedFlavorException(arg0);
      }
      return image;
    }

    public DataFlavor[] getTransferDataFlavors() {
      return myFlavors;
    }

    public boolean isDataFlavorSupported(DataFlavor arg0) {
      return (arg0 == DataFlavor.imageFlavor);
    }
  }

  /*
   * public String getIndent(int i)
   * {
   * char[] temp = new char[(i*2)];
   * Arrays.fill(temp, ' ');
   * return temp.toString();
   * }
   */

}
