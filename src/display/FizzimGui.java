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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import entities.GeneralObj;
import entities.LoopbackTransitionObj;
import entities.StateObj;
import entities.TransitionObj;
import attributes.EnumVisibility;
import attributes.GlobalAttributes;
import attributes.ObjAttribute;

/* This file was originally created with matisse GUI Builder for MyEclipse.
 * Due to bugs and limitations, in is now being manually edited
 */
@SuppressWarnings("serial")
public class FizzimGui extends JFrame {

  static String currVer = "14.03.1";

  public static final String action_field = "Action";
  public static final String condition_field = "Garde";
  public static final String event_field = "Evènements";

  /*
   * Global attributes for the machine, the inputs, outputs, states and
   * transitions
   */
  private GlobalAttributes global_attributes;

  int maxH = 1296;
  int maxW = 936;
  boolean loading = false;

  private File currFile = null;
  /* The DrawArea is the central editing window */
  private DrawArea drawArea1;

  /** Creates new form FizzimGui */
  public FizzimGui() {

    ImageIcon icon = new ImageIcon("icon.png");
    this.setIconImage(icon.getImage());

    /* Create an empty set of global attributes */
    global_attributes = new GlobalAttributes();
    /* Initialize the global attributes */
    initGlobal();

    drawArea1 = new DrawArea(global_attributes);
    drawArea1.setPreferredSize(new Dimension(maxW, maxH));

    // custom initComponents
    initComponents();
    setTitle("Fizzim: State Machine GUI editor");
    /* Set the window at the middle of the screen */
    setLocationRelativeTo(null);

    drawArea1.setJFrame(this);

    drawArea1.updateStates();
    drawArea1.updateTrans();

  }

  /**
   * Configure the default global attributes.
   * 
   * @details Every state and transition have a name. Transitions have also an
   *          "equation" field.
   */
  public void initGlobal() {
    int[] editable = { ObjAttribute.ABS, ObjAttribute.GLOBAL_VAR,
        ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR,
        ObjAttribute.GLOBAL_VAR, ObjAttribute.GLOBAL_VAR,
        ObjAttribute.GLOBAL_VAR };

    global_attributes.addMachineAttribute(
        new ObjAttribute("name", "def_name", EnumVisibility.NO, "", "",
            Color.black, "", "", editable));

    global_attributes.addMachineAttribute(
        new ObjAttribute("clock", "clk", EnumVisibility.NO,
            "posedge", "", Color.black, "", "", editable));

    global_attributes.addStateAttribute(
        new ObjAttribute("name", "def_name", EnumVisibility.YES,
            "def_type", "", Color.black, "", "", editable));

    global_attributes.addTransitionAttribute(
        new ObjAttribute("name", "def_name", EnumVisibility.YES,
            "def_type", "", Color.black, "", "", editable));

    /* User defined properties */
    // globalList.get(EnumGlobalList.STATES).add(
    // new ObjAttribute("Ind. vrais", "", EnumVisibility.YES,
    // "def_type", "", Color.black, "", "", editable));

    global_attributes.addTransitionAttribute(
        new ObjAttribute(event_field, "", EnumVisibility.NO,
            "def_type", "", Color.black, "", "", editable));
    global_attributes.addTransitionAttribute(
        new ObjAttribute(condition_field, "", EnumVisibility.NO,
            "def_type", "", Color.black, "", "", editable));
    global_attributes.addTransitionAttribute(
        new ObjAttribute(action_field, "", EnumVisibility.NO,
            "def_type", "", Color.black, "", "", editable));
  }

  // GEN-BEGIN:variables
  // Variables declaration - do not modify
  private JMenuItem EditItemDelete;
  private JMenuItem EditItemRedo;
  private JMenuItem EditItemUndo;
  private JMenu EditMenu;
  private JMenuItem FileItemExit;
  private JMenuItem FileItemNew;
  private JMenuItem FileItemOpen;
  private JMenuItem FilePref;
  private JMenuItem FileItemPageSetup;
  private JMenuItem FileItemPrint;
  private JMenuItem FileItemSave;
  private JMenuItem FileItemSaveAs;
  private JMenuItem FileItemSaveAs6Lines;
  private JMenuItem FileItemOpen6Lines;
  private JMenu FileExport;
  private JMenuItem FileExportClipboard;
  private JMenuItem FileExportPNG;
  private JMenuItem FileExportJPEG;
  private JMenu FileMenu;
  private JFileChooser FileOpenAction;
  private JFileChooser FileOpen6LinesAction;
  private JFileChooser FileSaveAction;
  private JFileChooser ExportChooser;
  private JFileChooser FileSave6LinesAction;
  private JMenuItem GlobalItemInputs;
  private JMenuItem GlobalItemMachine;
  private JMenuItem GlobalItemOutputs;
  private JMenuItem GlobalItemStates;
  private JMenuItem GlobalItemTransitions;
  private JMenu GlobalMenu;
  private JMenuItem HelpItemAbout;
  private JMenuItem HelpItemHelp;
  private JMenu HelpMenu;
  private JMenuBar MenuBar;
  private JPanel jPanel1;
  private JPanel jPanel3;
  private JScrollPane jScrollPane1;
  private JSeparator jSeparator1;
  private JSeparator jSeparator2;
  private JSeparator jSeparator3;
  private JSeparator jSeparator4;
  private MyJTabbedPane pages_tabbedPane;

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
    GridBagConstraints gridBagConstraints;

    FileNameExtensionFilter filter =
        new FileNameExtensionFilter("Fizzim Files (*.fzm)", "fzm");
    FileNameExtensionFilter PNGfilter =
        new FileNameExtensionFilter("PNG", "png");
    FileNameExtensionFilter TXTfilter =
        new FileNameExtensionFilter("TXT", "txt");

    FileOpenAction = new JFileChooser();
    FileOpenAction.setFileFilter(filter);
    FileOpen6LinesAction = new JFileChooser();
    FileOpen6LinesAction.setFileFilter(TXTfilter);
    FileSaveAction = new JFileChooser();
    FileSaveAction.setFileFilter(filter);
    ExportChooser = new JFileChooser();
    ExportChooser.setFileFilter(PNGfilter);
    FileSave6LinesAction = new JFileChooser();
    FileSave6LinesAction.setFileFilter(TXTfilter);

    jPanel3 = new JPanel();
    pages_tabbedPane = new MyJTabbedPane();
    jScrollPane1 = new JScrollPane();
    jPanel1 = new JPanel();
    MenuBar = new JMenuBar();
    FileMenu = new JMenu();
    FileItemNew = new JMenuItem();
    FileItemOpen = new JMenuItem();
    FileItemSave = new JMenuItem();
    FileItemSaveAs = new JMenuItem();
    FileItemOpen6Lines = new JMenuItem();
    FileItemSaveAs6Lines = new JMenuItem();
    FileExport = new JMenu("Export to...");
    FileExportClipboard = new JMenuItem();
    FileExportPNG = new JMenuItem();
    FileExportJPEG = new JMenuItem();
    jSeparator1 = new JSeparator();
    FilePref = new JMenuItem();
    FileItemPageSetup = new JMenuItem();
    FileItemPrint = new JMenuItem();
    jSeparator2 = new JSeparator();
    FileItemExit = new JMenuItem();
    EditMenu = new JMenu();
    EditItemUndo = new JMenuItem();
    EditItemRedo = new JMenuItem();
    EditItemDelete = new JMenuItem();
    GlobalMenu = new JMenu();
    GlobalItemMachine = new JMenuItem();
    GlobalItemStates = new JMenuItem();
    GlobalItemTransitions = new JMenuItem();
    jSeparator3 = new JSeparator();
    GlobalItemInputs = new JMenuItem();
    GlobalItemOutputs = new JMenuItem();
    HelpMenu = new JMenu();
    HelpItemHelp = new JMenuItem();
    jSeparator4 = new JSeparator();
    HelpItemAbout = new JMenuItem();

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("Fizzim");
    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent evt) {
        formComponentResized(evt);
      }
    });
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        formWindowClosing();
      }
    });

    FileOpenAction.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileOpenActionActionPerformed(evt);
      }
    });

    jPanel3.setLayout(new GridBagLayout());

    jPanel3.setMinimumSize(new Dimension(100, 100));
    pages_tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

    jPanel1 = drawArea1;
    GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
        Alignment.LEADING).addGap(0, 1294, Short.MAX_VALUE));
    jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
        Alignment.LEADING).addGap(0, 1277, Short.MAX_VALUE));
    jScrollPane1.setViewportView(jPanel1);

    // pages
    pages_tabbedPane.addBlankTab("Create New Page", new JPanel());
    pages_tabbedPane.addTab("Page 1", jScrollPane1);
    pages_tabbedPane.setSelectedIndex(1);
    pages_tabbedPane.setBackgroundAt(0, new Color(200, 200, 200));
    pages_tabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        TabChanged(e);
      }
    });

    pages_tabbedPane.addMouseListener(new MouseListener() {

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

    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    jPanel3.add(pages_tabbedPane, gridBagConstraints);

    getContentPane().add(jPanel3, BorderLayout.CENTER);

    Dimension coord = getScrollPaneSize();
    if (coord.height > maxH)
      coord.setSize(coord.width, maxH);
    if (coord.width > maxW)
      coord.setSize(maxW + 23, coord.height);

    pages_tabbedPane.setMinimumSize(coord);
    pages_tabbedPane.setSize(coord);
    jPanel3.doLayout();
    jPanel3.repaint();

    FileMenu.setText("File");
    FileMenu.setMnemonic(KeyEvent.VK_F);
    FileItemNew.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_N,
        InputEvent.CTRL_MASK));
    FileItemNew.setMnemonic(KeyEvent.VK_N);
    FileItemNew.setText("New");
    FileItemNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileItemNewActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemNew);

    FileItemOpen.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_O,
        InputEvent.CTRL_MASK));
    FileItemOpen.setMnemonic(KeyEvent.VK_O);
    FileItemOpen.setText("Open");
    FileItemOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileItemOpenActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemOpen);

    FileItemSave.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S,
        InputEvent.CTRL_MASK));
    FileItemSave.setMnemonic(KeyEvent.VK_S);
    FileItemSave.setText("Save");
    FileItemSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileItemSaveActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemSave);

    FileItemSaveAs.setText("Save As");
    FileItemSaveAs.setMnemonic(KeyEvent.VK_A);
    FileItemSaveAs.setDisplayedMnemonicIndex(5);
    FileItemSaveAs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileItemSaveAsActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemSaveAs);

    /* Open as 6 lines file */
    FileItemOpen6Lines.setText("Open As 6 lines");
    FileItemOpen6Lines.setMnemonic(java.awt.event.KeyEvent.VK_L);
    FileItemOpen6Lines.setDisplayedMnemonicIndex(11);
    FileItemOpen6Lines.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        FileItemOpen6linesActionPerformed(evt);
      }
    });
    FileMenu.add(FileItemOpen6Lines);

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
    FileExportClipboard.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_F2, 0));
    FileExportClipboard.setMnemonic(KeyEvent.VK_C);
    FileExportClipboard.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileExportClipboardActionPerformed(evt);
      }
    });
    FileExport.add(FileExportClipboard);

    FileExportPNG.setText("PNG");
    FileExportPNG.setMnemonic(KeyEvent.VK_P);
    FileExportPNG.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileExportPNGActionPerformed(evt);
      }
    });
    FileExport.add(FileExportPNG);

    FileExportJPEG.setText("JPEG");
    FileExportJPEG.setMnemonic(KeyEvent.VK_J);
    FileExportJPEG.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileExportJPEGActionPerformed(evt);
      }
    });
    FileExport.add(FileExportJPEG);
    FileExport.setMnemonic(KeyEvent.VK_E);

    FileMenu.add(FileExport);

    FileMenu.add(jSeparator1);

    FilePref.setText("Preferences");
    FilePref.setMnemonic(KeyEvent.VK_R);
    FilePref.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FilePrefActionPerformed(evt);
      }
    });

    FileMenu.add(FilePref);

    FileItemPageSetup.setText("Page Setup");
    FileItemPageSetup.setMnemonic(KeyEvent.VK_U);
    FileItemPageSetup
        .addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            FileItemPageSetupActionPerformed(evt);
          }
        });

    FileMenu.add(FileItemPageSetup);

    FileItemPrint.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_P,
        InputEvent.CTRL_MASK));
    FileItemPrint.setMnemonic(KeyEvent.VK_P);
    FileItemPrint.setText("Print");
    FileItemPrint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileItemPrintActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemPrint);

    FileMenu.add(jSeparator2);

    FileItemExit.setText("Exit");
    FileItemExit.setMnemonic(KeyEvent.VK_X);
    FileItemExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FileItemExitActionPerformed(evt);
      }
    });

    FileMenu.add(FileItemExit);

    MenuBar.add(FileMenu);

    EditMenu.setText("Edit");
    EditMenu.setMnemonic(KeyEvent.VK_E);
    EditItemUndo.setMnemonic(KeyEvent.VK_U);
    EditItemUndo.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_Z,
        InputEvent.CTRL_MASK));
    EditItemUndo.setText("Undo");
    EditItemUndo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        EditItemUndoActionPerformed(evt);
      }
    });

    EditMenu.add(EditItemUndo);
    EditItemRedo.setMnemonic(KeyEvent.VK_R);
    EditItemRedo.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_Y,
        InputEvent.CTRL_MASK));
    EditItemRedo.setText("Redo");
    EditItemRedo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        EditItemRedoActionPerformed(evt);
      }
    });

    EditMenu.add(EditItemRedo);
    EditItemDelete.setMnemonic(KeyEvent.VK_D);
    EditItemDelete.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_DELETE, 0));
    EditItemDelete.setText("Delete");
    // EditItemDelete.setVisible(false);
    EditItemDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        EditItemDeleteActionPerformed(evt);
      }
    });

    EditMenu.add(EditItemDelete);

    MenuBar.add(EditMenu);

    GlobalMenu.setText("Global Attributes");
    GlobalMenu.setMnemonic(KeyEvent.VK_G);

    GlobalItemMachine.setText("State Machine");
    GlobalItemMachine.setMnemonic(KeyEvent.VK_M);
    GlobalItemMachine
        .addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GlobalItemMachineActionPerformed(evt);
          }
        });

    GlobalMenu.add(GlobalItemMachine);

    GlobalItemInputs.setMnemonic(KeyEvent.VK_I);
    GlobalItemInputs.setText("Inputs");
    GlobalItemInputs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        GlobalItemInputsActionPerformed(evt);
      }
    });

    GlobalMenu.add(GlobalItemInputs);

    GlobalItemOutputs.setMnemonic(KeyEvent.VK_O);
    GlobalItemOutputs.setText("Outputs");
    GlobalItemOutputs
        .addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GlobalItemOutputsActionPerformed(evt);
          }
        });

    GlobalMenu.add(GlobalItemOutputs);

    GlobalMenu.add(jSeparator3);

    GlobalItemStates.setText("States");
    GlobalItemStates.setMnemonic(KeyEvent.VK_S);
    GlobalItemStates.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        GlobalItemStatesActionPerformed(evt);
      }
    });

    GlobalMenu.add(GlobalItemStates);

    GlobalItemTransitions.setText("Transitions");
    GlobalItemTransitions.setMnemonic(KeyEvent.VK_T);
    GlobalItemTransitions
        .addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GlobalItemTransitionsActionPerformed(evt);
          }
        });

    GlobalMenu.add(GlobalItemTransitions);

    MenuBar.add(GlobalMenu);

    HelpMenu.setMnemonic(KeyEvent.VK_H);
    HelpMenu.setText("Help");
    HelpItemHelp.setMnemonic(KeyEvent.VK_H);
    HelpItemHelp.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_F1, 0));
    HelpItemHelp.setText("Help");
    HelpItemHelp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        HelpItemHelpActionPerformed(evt);
      }
    });

    HelpMenu.add(HelpItemHelp);

    HelpMenu.add(jSeparator4);

    HelpItemAbout.setMnemonic(KeyEvent.VK_A);
    HelpItemAbout.setText("About");

    HelpItemAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
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

    ExportChooser.setCurrentDirectory(currFile);
    int returnVal = ExportChooser.showSaveDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      tryToSave(ExportChooser.getSelectedFile(), "png", true);
    }

  }

  private void exportFile(File file, String type)
  {
    try {
      ImageIO.write(getImage(), type, file);
    } catch (IOException e) {
    }
  }

  protected void FileExportJPEGActionPerformed(ActionEvent evt) {

    ExportChooser.setCurrentDirectory(currFile);
    int returnVal = ExportChooser.showSaveDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      tryToSave(ExportChooser.getSelectedFile(), "jpg", true);
    }
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
        pages_tabbedPane.getTitleAt(tab));

    if (s != null)
    {
      if (getPageIndex(s) == -1)
        pages_tabbedPane.setTitleAt(tab, s);
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
      for (int i = 1; i < pages_tabbedPane.getTabCount(); i++)
      {
        if (i != sel)
          pages_tabbedPane.setComponentAt(i, new JPanel());
      }

      if (sel == 0)
      {
        int index = pages_tabbedPane.getTabCount();
        pages_tabbedPane.addTab("Page " + String.valueOf(index), jScrollPane1);
        pages_tabbedPane.setSelectedIndex(index);
        drawArea1.setCurrPage(index);
        drawArea1.setSCounter(index, 0);
      } else {
        // set current tab
        drawArea1.setCurrPage(sel);
        pages_tabbedPane.setComponentAt(sel, jScrollPane1);
      }
      drawArea1.unselectObjs();
    }
  }

  protected void FileOpenActionActionPerformed(ActionEvent evt) {

  }

  // GEN-FIRST:event_FileItemPageSetupActionPerformed
  private void FileItemPageSetupActionPerformed(ActionEvent evt) {
    new PageSetup(this, true).setVisible(true);
  }// GEN-LAST:event_FileItemPageSetupActionPerformed

  // GEN-FIRST:event_formComponentResized
  private void formComponentResized(ComponentEvent evt) {
    // this method makes sure that the draw area size is mostly restricted
    // to dimensions set in page setup

    Dimension coord = getScrollPaneSize();
    if (coord.height > maxH)
      coord.setSize(coord.width, maxH);
    if (coord.width > maxW)
      coord.setSize(maxW + 23, coord.height);
    drawArea1.setSize(maxW, maxH);
    pages_tabbedPane.setMinimumSize(coord);
    pages_tabbedPane.setSize(coord);
    jPanel3.doLayout();
    jPanel3.repaint();

  }// GEN-LAST:event_formComponentResized

  // GEN-FIRST:event_FileItemSaveActionPerformed
  private void FileItemSaveActionPerformed(ActionEvent evt) {
    if (currFile == null) {

      // Default to cwd
      // FileSaveAction.setCurrentDirectory(new
      // java.io.File("").getAbsoluteFile());
      FileSaveAction.setCurrentDirectory(new File(System
          .getProperty("user.dir")).getAbsoluteFile());
      int returnVal = FileSaveAction.showSaveDialog(this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        if (tryToSave(FileSaveAction.getSelectedFile(), "fzm", true)) {
          setTitle("Fizzim - " + currFile.getName());
        }
      }

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

          FileSaveAction.setCurrentDirectory(new File(System
              .getProperty("user.dir")).getAbsoluteFile());
          int returnVal = FileSaveAction.showSaveDialog(this);

          if (returnVal == JFileChooser.APPROVE_OPTION) {
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
  private void FileItemPrintActionPerformed(ActionEvent evt) {
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
  private void EditItemDeleteActionPerformed(ActionEvent evt) {
    drawArea1.delete();
  }// GEN-LAST:event_EditItemDeleteActionPerformed

  // GEN-FIRST:event_EditItemRedoActionPerformed
  private void EditItemRedoActionPerformed(ActionEvent evt) {
    drawArea1.redo();
  }// GEN-LAST:event_EditItemRedoActionPerformed

  // GEN-FIRST:event_EditItemUndoActionPerformed
  private void EditItemUndoActionPerformed(ActionEvent evt) {
    drawArea1.undo();
  }// GEN-LAST:event_EditItemUndoActionPerformed

  // GEN-FIRST:event_FileItemExitActionPerformed
  private void FileItemExitActionPerformed(ActionEvent evt) {
    formWindowClosing();
  }// GEN-LAST:event_FileItemExitActionPerformed

  // GEN-FIRST:event_FileItemNewActionPerformed
  private void FileItemNewActionPerformed(ActionEvent evt) {

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

          FileSaveAction.setCurrentDirectory(new File(System
              .getProperty("user.dir")).getAbsoluteFile());
          int returnVal = FileSaveAction.showSaveDialog(this);

          if (returnVal == JFileChooser.APPROVE_OPTION) {
            tryToSave(FileSaveAction.getSelectedFile(), "fzm", true);
          }
        } else if (n == JOptionPane.CANCEL_OPTION || n == -1) {
          createNew = false;
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
          if (!saveFile(currFile)) {
            createNew = false;
          }
        } else if (n == JOptionPane.CANCEL_OPTION || n == -1) {
          createNew = false;
        }
      }
    }
    if (createNew) {
      for (int i = pages_tabbedPane.getTabCount() - 1; i > 1; i--) {
        pages_tabbedPane.remove(i);
      }
      pages_tabbedPane.setComponentAt(1, jScrollPane1);
      currFile = null;
      setTitle("Fizzim");
      for (int i = 0; i < global_attributes.size(); i++)
      {
        global_attributes.getSpecificGlobalAttributes(i).clear();
      }
      initGlobal();
      drawArea1.open(global_attributes);
    }

  }// GEN-LAST:event_FileItemNewActionPerformed

  public void resetTabs() {
    for (int i = pages_tabbedPane.getTabCount() - 1; i > 0; i--)
    {
      pages_tabbedPane.remove(i);
    }

  }

  public void addNewTab(String line) {
    pages_tabbedPane.addTab(line, new JPanel());
  }

  private void GlobalItemMachineActionPerformed(ActionEvent evt) {// GEN-FIRST:event_GlobalItemMachineActionPerformed
    global_attributes = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, global_attributes,
        GlobalAttributes.MACHINE)
        .setVisible(true);
  }// GEN-LAST:event_GlobalItemMachineActionPerformed

  private void GlobalItemInputsActionPerformed(ActionEvent evt) {// GEN-FIRST:event_GlobalItemsInputsActionPerformed
    global_attributes = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, global_attributes,
        GlobalAttributes.INPUTS)
        .setVisible(true);
  }// GEN-LAST:event_GlobalItemsInputsActionPerformed

  private void GlobalItemOutputsActionPerformed(ActionEvent evt) {// GEN-FIRST:event_GlobalItemOutputsActionPerformed
    global_attributes = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, global_attributes,
        GlobalAttributes.OUTPUTS)
        .setVisible(true);
    /*
     * GlobalAttributesFrame.setSize(600, 300);
     * GlobalAttributesFrame.getRootPane().setDefaultButton(GACancel);
     * GlobalAttributesTabbedPane.setSelectedComponent(GAOutputsScrollPane);
     * GlobalAttributesFrame.show();
     */

  }// GEN-LAST:event_GlobalItemOutputsActionPerformed

  private void GlobalItemStatesActionPerformed(ActionEvent evt) {// GEN-FIRST:event_GlobalItemStatesActionPerformed
    global_attributes = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, global_attributes,
        GlobalAttributes.STATES)
        .setVisible(true);
  }// GEN-LAST:event_GlobalItemStatesActionPerformed

  private void GlobalItemTransitionsActionPerformed(
      ActionEvent evt) {// GEN-FIRST:event_GlobalItemTransitionsActionPerformed
    global_attributes = drawArea1.setUndoPoint();
    new GlobalProperties(drawArea1, this, true, global_attributes,
        GlobalAttributes.TRANSITIONS)
        .setVisible(true);
  }// GEN-LAST:event_GlobalItemTransitionsActionPerformed

  private void FileItemSaveAsActionPerformed(ActionEvent evt) {

    if (currFile == null) {
      // Default to cwd
      FileSaveAction.setCurrentDirectory(new File(System
          .getProperty("user.dir")).getAbsoluteFile());
    } else {
      FileSaveAction.setSelectedFile(currFile);
    }

    FileSaveAction.setCurrentDirectory(new File(System
        .getProperty("user.dir")).getAbsoluteFile());
    int returnVal = FileSaveAction.showSaveDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      if (tryToSave(FileSaveAction.getSelectedFile(), "fzm", true)) {
        setTitle("Fizzim - " + currFile.getName());
      }
    }

  }

  /**
   * Allows to save the file in a 6 lines format
   * 
   * @param evt
   */
  private void FileItemSaveAs6LinesActionPerformed(
      java.awt.event.ActionEvent evt) {
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
    int returnVal = FileSave6LinesAction.showSaveDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION)
      if (tryToSave6lines(FileSave6LinesAction.getSelectedFile(), "txt", true))
        setTitle("Graphe 6 lignes - " + currFile.getName());

  }

  private void FileItemOpenActionPerformed(ActionEvent evt) {// GEN-FIRST:event_FileItemOpenActionPerformed

    boolean open = true;
    if (drawArea1.getFileModifed()) {
      Object[] options = { "Yes", "No", "Cancel" };

      int n = JOptionPane
          .showOptionDialog(this, "Save file before opening file?",
              "Fizzim", JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, options,
              options[0]);
      if (n == JOptionPane.YES_OPTION) {
        FileSaveAction.setCurrentDirectory(currFile);
        int returnVal = FileSaveAction.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          if (!tryToSave(FileSaveAction.getSelectedFile(), "fzm", true))
            open = false;
        }
      }
      else if (n == JOptionPane.CANCEL_OPTION || n == -1)
        open = false;
    }
    if (open) {

      if (currFile == null) {
        // Default to cwd
        // FileOpenAction.setCurrentDirectory(new
        // java.io.File("").getAbsoluteFile());
        FileOpenAction.setCurrentDirectory(new File(System
            .getProperty("user.dir")).getAbsoluteFile());
      } else {
        FileOpenAction.setCurrentDirectory(currFile);
      }
      int returnVal = FileOpenAction.showOpenDialog(null);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
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
    pages_tabbedPane.setComponentAt(1, jScrollPane1);
    pages_tabbedPane.setSelectedIndex(1);
    drawArea1.setCurrPage(1);
    loading = false;

  }

  private void FileItemOpen6linesActionPerformed(ActionEvent evt) {// GEN-FIRST:event_FileItemOpenActionPerformed

    boolean open = true;
    if (drawArea1.getFileModifed()) {
      Object[] options = { "Yes", "No", "Cancel" };

      int n = JOptionPane
          .showOptionDialog(this, "Save file before opening file?",
              "Fizzim", JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, options,
              options[0]);
      if (n == JOptionPane.YES_OPTION) {
        FileSave6LinesAction.setCurrentDirectory(currFile);
        int returnVal = FileSave6LinesAction.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          if (!tryToSave(FileSave6LinesAction.getSelectedFile(), "txt", true))
            open = false;
        }
      }
      else if (n == JOptionPane.CANCEL_OPTION || n == -1)
        open = false;
    }
    if (open) {

      if (currFile == null) {
        // Default to cwd
        // FileOpenAction.setCurrentDirectory(new
        // java.io.File("").getAbsoluteFile());
        FileOpen6LinesAction.setCurrentDirectory(new File(System
            .getProperty("user.dir")).getAbsoluteFile());
      } else {
        FileOpen6LinesAction.setCurrentDirectory(currFile);
      }
      int returnVal = FileOpen6LinesAction.showOpenDialog(null);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File tempFile = FileOpen6LinesAction.getSelectedFile();
        String fileName = tempFile.getName().toLowerCase();
        if (!tempFile.isDirectory() && fileName.endsWith(".txt")) {
          try {
            openFile6lines(tempFile);
          } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error when loading: "
                + e.getMessage(),
                "error", JOptionPane.ERROR_MESSAGE);
          }
          setTitle("Fizzim - " + currFile.getName());
        } else {
          JOptionPane.showMessageDialog(this, "File must end with .txt",
              "error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }

  }// GEN-LAST:event_FileItemOpenActionPerformed

  /**
   * Open a 6 lines file and charge it into the graph editor
   * 
   * @param selectedFile
   *          The file to open
   * @throws IOException
   */
  private void openFile6lines(File selectedFile) throws IOException {
    loading = true;
    currFile = selectedFile;
    FileParser6lines fileParser = new FileParser6lines(currFile, this,
        drawArea1);
    pages_tabbedPane.setComponentAt(1, jScrollPane1);
    pages_tabbedPane.setSelectedIndex(1);
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
   * each page contains an independant graph and the name of the page will be
   * the name of the graph in the file.
   * 
   * @param selectedFile
   *          the targeted file to save in
   * @return
   */

  private boolean saveFile6lines(File selectedFile) {
    currFile = selectedFile;
    try {

      int j = 1;
      BufferedWriter writer = new BufferedWriter(new FileWriter(currFile));
      // Put all the objects of the graph in object
      Vector<Object> object = drawArea1.getObjList();
      for (int i = 1; i < object.size(); i++) {
        GeneralObj temp = (GeneralObj) object.get(i);
        // verify that the object is a transition.
        if (temp instanceof TransitionObj) {
          if (j != 1) {
            writer.write("\r\n");
          }
          j++;
          TransitionObj transition = (TransitionObj) temp;
          // writer.write(selectedFile.getName().substring(0,
          // selectedFile.getName().length() - 4));
          // writer.write("\r\n");

          StateObj initial_state = transition.getStartState();
          writer.write(pages_tabbedPane.getTitleAt(initial_state.getPage()));
          writer.write("\r\n");
          int page_init_state = initial_state.getPage();
          // write the number of the initial state in the file.
          writer.write(initial_state.getName());
          writer.write("\r\n");
          StateObj final_state = transition.getEndState();
          int page_final_state = final_state.getPage();
          if (page_final_state != page_init_state) {
            String final_page = String.valueOf(page_final_state);
            String init_page = Integer.toString(page_init_state);
            JOptionPane
                .showMessageDialog(
                    this,
                    "Two states are not of the same page in a transition.\n The transition between the state "
                        + initial_state.getName()
                        + " page " + init_page + " and the state "
                        + final_state.getName() + " page " + final_page,
                    "Transition overflow",
                    JOptionPane.ERROR_MESSAGE);
            writer.close();
            return false;
          }
          /*
           * Verify if the transition is a loopback and
           * write the number of the final state in the file (it will be
           * the same as the initial state if it's a loop).
           */
          if (temp instanceof LoopbackTransitionObj) {
            writer.write(initial_state.getName());
            writer.write("\r\n");
          } else {
            writer.write(final_state.getName());
            writer.write("\r\n");
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
          String text;
          text = event + " " + "Evenement";
          writer.write(text.trim());
          writer.write("\r\n");
          text = condition + " " + "Condition";
          writer.write(text.trim());
          writer.write("\r\n");
          text = actions + " " + "Action";
          writer.write(text.trim());

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
      tempList = global_attributes.getMachineAttributes();
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</machine>\n");

      writer.write(i(1) + "<inputs>\n");
      tempList = global_attributes.getInputsAttributes();
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</inputs>\n");

      writer.write(i(1) + "<outputs>\n");
      tempList = global_attributes.getOutputsAttributes();
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</outputs>\n");

      writer.write(i(1) + "<state>\n");
      tempList = global_attributes.getStateAttributes();
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</state>\n");

      writer.write(i(1) + "<trans>\n");
      tempList = global_attributes.getTransAttributes();
      for (int i = 0; i < tempList.size(); i++) {
        ObjAttribute obj = tempList.get(i);
        obj.save(writer, 1);
      }
      writer.write(i(1) + "</trans>\n");

      writer.write("</globals>\n");

      writer.write("<tabs>\n");
      for (int i = 1; i < pages_tabbedPane.getTabCount(); i++)
      {
        writer.write(i(1) + pages_tabbedPane.getTitleAt(i) + "\n");
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
    return pages_tabbedPane.getTabCount();
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
    EventQueue.invokeLater(new Runnable() {
      public void run() {

        // sets error file to write to
        final File file = new File("fizzim_errors.log");

        // make sure output file doesnt get too large
        FileOutputStream fout = null;
        try {
          fout = new FileOutputStream(file, true) {

            public void write(byte[] b, int off, int len) throws IOException {
              if (file.length() < 20000)
                super.write(b, off, len);
              System.out.write(b, off, len);
            }

            public void write(byte[] b) throws IOException {
              if (file.length() < 20000)
                super.write(b);
              System.out.write(b);
            }

            public void write(int b) throws IOException {
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
        fzim.setSize(new Dimension(1000, 685));
        // new HelpItemAboutActionPerformed();
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

  public void updateGlobal(GlobalAttributes new_global_attributes) {
    global_attributes = new_global_attributes;
  }

  public String getPageName(int i)
  {
    return pages_tabbedPane.getTitleAt(i);
  }

  public int getPageIndex(String name) {
    for (int i = 1; i < pages_tabbedPane.getTabCount(); i++)
    {
      if (pages_tabbedPane.getTitleAt(i).equals(name))
        return i;
    }
    return -1;
  }

  public void setDASize(int w, int h) {
    maxW = w;
    maxH = h;

    drawArea1.setPreferredSize(new Dimension(maxW, maxH));
    drawArea1.setMaximumSize(new Dimension(maxW, maxH));
    drawArea1.setMinimumSize(new Dimension(maxW, maxH));
    drawArea1.setSize(maxW, maxH);

    // try to clean up resized page
    drawArea1.updatePageConn();
    drawArea1.moveOnResize(maxW, maxH);

    Dimension coord = getScrollPaneSize();
    if (coord.height > maxH)
      coord.setSize(coord.width, maxH);
    if (coord.width > maxW)
      coord.setSize(maxW + 23, coord.height);

    pages_tabbedPane.setMinimumSize(coord);
    pages_tabbedPane.setSize(coord);
    pages_tabbedPane.doLayout();
    jPanel3.doLayout();
    repaint();

  }

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

  class PageSetup extends JDialog {

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
      jLabel1 = new JLabel();
      jLabel2 = new JLabel();
      jTextField1 = new JTextField();
      jTextField2 = new JTextField();
      jLabel3 = new JLabel();
      jLabel4 = new JLabel();
      jButton1 = new JButton();
      jButton2 = new JButton();
      jLabel5 = new JLabel();

      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
      jButton1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          jButton1ActionPerformed(evt);
        }
      });

      jButton2.setText("OK");
      jButton2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          jButton2ActionPerformed(evt);
        }
      });

      jLabel5.setText("Enter new dimensions:");

      GroupLayout layout = new GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
          .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
              .addContainerGap(23, Short.MAX_VALUE)
              .addComponent(jButton2)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(jButton1).addContainerGap())
          .addGroup(layout.createSequentialGroup()
              .addContainerGap().addComponent(jLabel5)
              .addContainerGap(33, Short.MAX_VALUE))
          .addGroup(layout
              .createSequentialGroup()
              .addContainerGap()
              .addGroup(layout.createParallelGroup(Alignment.LEADING)
                  .addComponent(jLabel1).addComponent(jLabel2))
              .addGap(1, 1, 1)
              .addGroup(layout.createParallelGroup(Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                      .addComponent(jTextField2,
                          GroupLayout.PREFERRED_SIZE,
                          GroupLayout.DEFAULT_SIZE,
                          GroupLayout.PREFERRED_SIZE)
                      .addPreferredGap(
                          ComponentPlacement.RELATED)
                      .addComponent(jLabel4))
                  .addGroup(layout
                      .createSequentialGroup()
                      .addComponent(jTextField1,
                          GroupLayout.PREFERRED_SIZE,
                          GroupLayout.DEFAULT_SIZE,
                          GroupLayout.PREFERRED_SIZE)
                      .addPreferredGap(ComponentPlacement.RELATED)
                      .addComponent(jLabel3)))
              .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

      layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
          .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
              .addContainerGap()
              .addComponent(jLabel5)
              .addGap(17, 17, 17)
              .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                  .addComponent(jLabel1)
                  .addComponent(jTextField1,
                      GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE,
                      GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel3))
              .addPreferredGap(ComponentPlacement.RELATED)
              .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                  .addComponent(jLabel2)
                  .addComponent(jTextField2,
                      GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE,
                      GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel4))
              .addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
              .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                  .addComponent(jButton1).addComponent(jButton2))
              .addContainerGap()));
      pack();
    }// </editor-fold>//GEN-END:initComponents

    // GEN-FIRST:event_jButton1ActionPerformed
    private void jButton1ActionPerformed(ActionEvent evt) {
      dispose();
    }// GEN-LAST:event_jButton1ActionPerformed

    // GEN-FIRST:event_jButton2ActionPerformed
    private void jButton2ActionPerformed(ActionEvent evt) {
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
    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField jTextField1;
    private JTextField jTextField2;
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

  /**
   * Add a new page with the name pageName
   * 
   * @param pageName
   *          the name of the new page.
   */
  public void addNewPage(String pageName) {

    int index = pages_tabbedPane.getTabCount();
    this.addNewTab(pageName);
    pages_tabbedPane.setSelectedIndex(index);
    drawArea1.setCurrPage(index);
    drawArea1.setSCounter(index, 0);
  }

  /**
   * @return the number of pages
   */
  public int getPageCount() {
    return pages_tabbedPane.getTabCount();
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
