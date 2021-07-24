package crossstitchmvc.view;

import crossstitchmvc.controller.ImageFeatures;
import crossstitchmvc.utility.SystemColorChooserPanel;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Image View is where we define elements of the main GUI.
 */
public class ImageView extends JFrame implements IView {

  //<editor-fold desc="field setup">
  //TODO: generate serial id for others?
  private static final long         serialVersionUID = -8490332900879683732L;
  private final        JScrollPane  scrollerPanel;
  private final        JLabel       imageDisplay;
  private final        JButton      openImageButton;
  private final        JButton      reloadImageButton;
  private final        JButton      saveImageButton;
  private final        JMenuItem    openImageMenu;
  private final        JMenuItem    saveImageMenu;
  private final        JMenuItem    exitMenu;
  private final        JMenuItem    blurEffectMenu;
  private final        JMenuItem    sharpenEffectMenu;
  private final        JMenuItem    greyscaleEffectMenu;
  private final        JMenuItem    sepiaEffectMenu;
  private final        JMenuItem    ditheringEffectMenu;
  private final        JMenuItem    mosaicEffectMenu;
  private final        JMenuItem    pixelateEffectMenu;
  private final        JMenuItem    generatePatternMenu;
  private final        JMenuItem    saveTextPatternMenu;
  private final        JMenuItem    createBatchMenu;
  private final        JMenuItem    addTextOverLayMenu;
  private final        JFileChooser imageFileChooser;
  private final        JMenuItem    chooseDmcColorRange;
  private final        JMenuItem    overlaySymbols;
  //</editor-fold>

  /**
   * Default constructor. Here we define all the layouts,
   * fields, buttons, and menus.
   *
   * @param title must provide a string title.
   */
  public ImageView(String title) {
    super(title);

    this.setSize(800, 600);
    this.setLocation(200, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Create panel for buttons on the bottom right.
    JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    openImageButton = new JButton("Open Image");
    bottom.add(openImageButton);

    reloadImageButton = new JButton("Reload Image");
    bottom.add(reloadImageButton);

    saveImageButton = new JButton("Save Image");
    bottom.add(saveImageButton);

    this.add(bottom, BorderLayout.SOUTH);

    imageDisplay = new JLabel();
    scrollerPanel = new JScrollPane(imageDisplay);
    scrollerPanel.createHorizontalScrollBar();
    scrollerPanel.createVerticalScrollBar();
    scrollerPanel.setPreferredSize(new Dimension(800, 600));
    this.add(scrollerPanel, BorderLayout.CENTER);

    //<editor-fold desc="construct file menu">
    // Add a menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    openImageMenu = new JMenuItem("Open Image");
    openImageMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
    fileMenu.add(openImageMenu);

    saveImageMenu = new JMenuItem("Save Image");
    saveImageMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
    fileMenu.add(saveImageMenu);

    exitMenu = new JMenuItem("Exit");
    exitMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
    fileMenu.add(exitMenu);

    //</editor-fold>

    //<editor-fold desc="construct image effect menu">
    // adding image effect menu
    JMenu imageEffectMenu = new JMenu("Effects");
    blurEffectMenu = new JMenuItem("Blur");
    blurEffectMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
    imageEffectMenu.add(blurEffectMenu);

    sharpenEffectMenu = new JMenuItem("Sharpen");
    sharpenEffectMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
    imageEffectMenu.add(sharpenEffectMenu);

    greyscaleEffectMenu = new JMenuItem("Greyscale");
    greyscaleEffectMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
    imageEffectMenu.add(greyscaleEffectMenu);

    sepiaEffectMenu = new JMenuItem("Sepia");
    sepiaEffectMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));
    imageEffectMenu.add(sepiaEffectMenu);

    ditheringEffectMenu = new JMenuItem("Dithering");
    ditheringEffectMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
    imageEffectMenu.add(ditheringEffectMenu);

    mosaicEffectMenu = new JMenuItem("Mosaics");
    mosaicEffectMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
    imageEffectMenu.add(mosaicEffectMenu);

    pixelateEffectMenu = new JMenuItem("Pixelation");
    pixelateEffectMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
    imageEffectMenu.add(pixelateEffectMenu);
    //</editor-fold>

    //<editor-fold desc="construct cross stitch menu">
    // adding cross stitch menu
    JMenu crossStitchMenu = new JMenu("Cross Stitch");
    generatePatternMenu = new JMenuItem("Generate Pattern");
    generatePatternMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
    crossStitchMenu.add(generatePatternMenu);

    saveTextPatternMenu = new JMenuItem("Save Text Pattern");
    saveTextPatternMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
    crossStitchMenu.add(saveTextPatternMenu);
    //</editor-fold>

    //<editor-fold desc="Construct batch related menu">
    // adding batch related menu
    JMenu batchMenu = new JMenu("Batch Input");
    createBatchMenu = new JMenuItem("Open Batch Controller");
    createBatchMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
    batchMenu.add(createBatchMenu);
    //</editor-fold>

    //<editor-fold desc="Construct extra credit menu">
    JMenu advancedMenu = new JMenu("Advanced");
    addTextOverLayMenu = new JMenuItem("Overlay Text");
    addTextOverLayMenu.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
    advancedMenu.add(addTextOverLayMenu);

    overlaySymbols = new JMenuItem("Overlay Symbols");
    advancedMenu.add(overlaySymbols);

    chooseDmcColorRange = new JMenuItem("Select DMC Colors");
    advancedMenu.add(chooseDmcColorRange);
    //</editor-fold>

    menuBar.add(fileMenu);
    menuBar.add(imageEffectMenu);
    menuBar.add(crossStitchMenu);
    menuBar.add(batchMenu);
    menuBar.add(advancedMenu);

    this.setJMenuBar(menuBar);
    setVisible(true);

    this.imageFileChooser = new JFileChooser();
    FileNameExtensionFilter filter =
        new FileNameExtensionFilter("Image", "jpg", "png", "jpeg", "gif", "bmp");
    imageFileChooser.setFileFilter(filter);
  }

  @Override public void setFeatures(ImageFeatures f) {
    // bottom buttons for easy access
    openImageButton.addActionListener(l -> openImage(f));
    reloadImageButton.addActionListener(l -> f.reloadImage());
    saveImageButton.addActionListener(l -> saveImage(f));

    // link file menus
    openImageMenu.addActionListener(l -> openImage(f));
    saveImageMenu.addActionListener(l -> saveImage(f));
    exitMenu.addActionListener(l -> f.exitProgram());

    // link imageEffect menus
    blurEffectMenu.addActionListener(l -> f.blurImage());
    sharpenEffectMenu.addActionListener(l -> f.sharpenImage());
    greyscaleEffectMenu.addActionListener(l -> f.greyscaleImage());
    sepiaEffectMenu.addActionListener(l -> f.sepiaImage());
    ditheringEffectMenu.addActionListener(l -> f.ditherImage());
    mosaicEffectMenu.addActionListener(l -> f.mosaicImage(null));
    pixelateEffectMenu.addActionListener(l -> f.pixelateImage(null));

    // link generate pattern
    generatePatternMenu.addActionListener(l -> f.generatePattern());
    saveTextPatternMenu.addActionListener(l -> f.saveTextPattern(true));

    // link batch items
    createBatchMenu.addActionListener(
        l -> f.createAndShowTextGUI(new BatchTextView("Batch Text")));

    // link extra credit menu items
    addTextOverLayMenu.addActionListener(l -> f.addTextOverLay());
    overlaySymbols.addActionListener(l -> f.overlaySymbols());
    chooseDmcColorRange.addActionListener(l -> f.chooseDmcColors());
    // click to replace color
    scrollerPanel.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        f.replaceColor(e.getX(), e.getY());
      }
    });
  }

  @Override public void openImage(ImageFeatures f) {
    int result = imageFileChooser.showOpenDialog(scrollerPanel);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = imageFileChooser.getSelectedFile();
      String filename = file.getAbsolutePath();
      try {
        f.openImage(file, filename);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(scrollerPanel, "error occurred loading image");
      }
    }
  }

  @Override public void saveImage(ImageFeatures f) {
    int result = imageFileChooser.showSaveDialog(scrollerPanel);
    if (result == JFileChooser.APPROVE_OPTION) {
      String savePath = imageFileChooser.getSelectedFile().toString();
      try {
        f.saveImage(savePath);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(scrollerPanel,
                                      "ERROR: There is no image to save or save path" +
                                          " invalid.");
        e.printStackTrace();
      }
    }
  }

  @Override public JScrollPane getScrollerPanel() {
    return scrollerPanel;
  }

  @Override public JLabel getImageDisplay() {
    return imageDisplay;
  }

  @Override public Color selectNewColor() {
    JColorChooser colorChooser = new JColorChooser();
    SystemColorChooserPanel newChooser = new SystemColorChooserPanel();
    ActionListener okActionListener =
        actionEvent -> System.out.println(colorChooser.getColor());
    ActionListener cancelActionListener = actionEvent -> colorChooser.setColor(null);

    AbstractColorChooserPanel[] panels = { newChooser };
    colorChooser.setChooserPanels(panels);
    JDialog dialog = JColorChooser
        .createDialog(null, "Replace the Selected Color", true, colorChooser,
                      okActionListener, cancelActionListener);
    dialog.setVisible(true);

    return colorChooser.getColor();
  }

  @Override public String getUserInputString() {
    return JOptionPane
        .showInputDialog("Enter the string you want to display on image:");
  }

  @Override public int getInputFontSize() {
    String fontSize =
        JOptionPane.showInputDialog("Enter the font size for the String:");
    return Integer.parseInt(fontSize);
  }

  @Override public int getInputX() {
    String xLocation = JOptionPane.showInputDialog("Enter the X location for the text:");
    return Integer.parseInt(xLocation);
  }

  @Override public int getInputY() {
    String yLocation = JOptionPane.showInputDialog("Enter the Y location for the text:");
    return Integer.parseInt(yLocation);
  }

  @Override public void chooseDmcColors() {
    //TODO: hummmm havent figure out yet.
  }
}
