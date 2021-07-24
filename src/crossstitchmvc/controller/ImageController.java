package crossstitchmvc.controller;

import crossstitchmvc.controller.commands.Blur;
import crossstitchmvc.controller.commands.CrossStitchCommand;
import crossstitchmvc.controller.commands.Dithering;
import crossstitchmvc.controller.commands.Grayscale;
import crossstitchmvc.controller.commands.Mosaic;
import crossstitchmvc.controller.commands.Pattern;
import crossstitchmvc.controller.commands.Pixelation;
import crossstitchmvc.controller.commands.ReplaceColor;
import crossstitchmvc.controller.commands.Sepia;
import crossstitchmvc.controller.commands.Sharpen;
import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.Image;
import crossstitchmvc.model.imageeffect.ImageImpl;
import crossstitchmvc.utility.ImageUtilities;
import crossstitchmvc.view.BatchTextView;
import crossstitchmvc.view.IView;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static crossstitchmvc.utility.ImageUtilities.getBufferedImage;

/**
 * The controller for the Image view GUI.
 * In here we handles all the logic after the user opens
 * the Image GUI.
 */
public class ImageController implements ImageFeatures {
  private final CrossStitchModel   model;
  private final IView              view;
  private final JScrollPane        scrollerPanel;
  private final JLabel             imageDisplay;
  private       String             filename;
  private       File               file;
  private       Map<Color, String> dmcColorDictionary;
  private       Map<Color, String> dmcSymbolDictionary;
  private       BufferedImage      currentImageCache;

  /**
   * Default constructor. We assign model, view, scrollerPanel, and
   * init Listener and have view display.
   *
   * @param model the model to use.
   */
  public ImageController(CrossStitchModel model, IView view) {
    this.model = model;
    this.view = view;
    this.file = null;
    this.filename = null;
    this.scrollerPanel = view.getScrollerPanel();
    this.imageDisplay = view.getImageDisplay();

    this.view.setFeatures(this);

    this.dmcColorDictionary = ImageUtilities
        .readDmcColors(new File("").getAbsolutePath() + "/newDmcColorWithUtf.csv");
    this.dmcSymbolDictionary = ImageUtilities
        .readDmcSymbols(new File("").getAbsolutePath() + "/newDmcColorWithUtf.csv");

    this.currentImageCache = null; // cache to store the current displayed image.
  }

  @Override public void openImage(File file, String filename) throws IOException {
    if (file == null) {
      throw new IllegalArgumentException("file can not be null");
    }
    if (filename == null) {
      filename = file.getAbsolutePath();
    }
    this.file = file;

    currentImageCache = ImageIO.read(file);
    // Add this to the GUI
    imageDisplay.setIcon(new ImageIcon(currentImageCache));
    scrollerPanel.repaint();
    // Add image to model object.
    model.loadImage(filename);
  }

  @Override public void reloadImage() {
    if (file == null) {
      JOptionPane.showMessageDialog(scrollerPanel, "ERROR: No Image Loaded.");
      return;
    }
    try {
      openImage(file, filename);
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(scrollerPanel, "error occurred loading image");
    }
  }

  @Override public void saveImage(String savePath) throws IOException {
    //    if (model.getImage() != null) {
    if (model.modelHasImage()) {
      Image modelImage = model.getImage();
      BufferedImage image =
          getBufferedImage(modelImage.getMatrix(), modelImage.getWidth(),
                           modelImage.getHeight());
      ImageIO.write(image, "png", new File(savePath));
    }
  }

  @Override public void replaceColor(int x, int y) {
    Image modelImage = model.getImage();
    if ((modelImage == null) || (modelImage.getTextPattern().equals(""))) {
      JOptionPane.showMessageDialog(null,
                                    "ERROR: Please generate the pattern before you " +
                                        "replace for a new DMC color");
    } else {
      JOptionPane.showMessageDialog(null, "Here are the locations: " + x + " " + y);
      Color newColor = view.selectNewColor();
      if (newColor != null) {
        System.out.println("new color" + newColor.toString());
        String newSymbol = dmcSymbolDictionary.get(newColor);
        Color oldColor = model.getColor(x, y);
        String oldSymbol = dmcSymbolDictionary.get(oldColor);
        // System.out.println("old color:" + oldColor.toString());
        processImage("Replace Color", new ReplaceColor(oldColor, newColor));

        //Replace the old symbols in the text pattern by the new symbol
        String textPatterns = model.getImage().getTextPattern();
        model.getImage().setTextPattern(textPatterns.replace(oldSymbol, newSymbol));
      }
    }
  }

  @Override public void blurImage() {
    processImage("Blur", new Blur());
  }

  @Override public void sharpenImage() {
    processImage("Sharpen", new Sharpen());
  }

  @Override public void greyscaleImage() {
    processImage("GreyScale", new Grayscale());
  }

  @Override public void sepiaImage() {
    processImage("Sepia", new Sepia());
  }

  @Override public void ditherImage() {
    processImage("Dither", new Dithering());
  }

  @Override public void mosaicImage(String textInput) {
    if (textInput == null) {
      textInput = JOptionPane.showInputDialog("How many?");
    }
    try {
      int parameter = Integer.parseInt(textInput);
      processImage("Mosaics", new Mosaic(parameter));
    } catch (NumberFormatException e) {
      JOptionPane
          .showMessageDialog(scrollerPanel, "Please type in a positive integer");
    }
  }

  @Override public void pixelateImage(String textInput) {
    if (textInput == null) {
      textInput = JOptionPane.showInputDialog("How many?");
    }
    try {
      int parameter = Integer.parseInt(textInput);
      processImage("Pixelation", new Pixelation(parameter));
    } catch (NumberFormatException e) {
      JOptionPane
          .showMessageDialog(scrollerPanel, "Please type in a positive integer");
    }
  }

  @Override public void generatePattern() {
    if (model.getImage().getSuperPixelSize() <= 0) {
      JOptionPane.showMessageDialog(scrollerPanel,
                                    "ERROR: Please use Mosaics or Pixelation before " +
                                        "trying to generate a pattern");
    } else {
      processImage("Pattern",
                   new Pattern(null, dmcColorDictionary, dmcSymbolDictionary));
      //saveTextPattern(false);
    }
  }

  @Override public String saveTextPattern(boolean toSave) {
    Image image = model.getImage();
    String textPatterns = image.getTextPattern();
    String result = "";
    int[][][] imageMatrix = image.getMatrix();
    if (textPatterns.equals("")) {
      JOptionPane.showMessageDialog(scrollerPanel,
                                    "ERROR: Please use Patten Generation before " +
                                        "trying to save a text pattern");
    } else {
      // 1. grab the text pattern saved in image
      // 2. generate legend based on result of step2
      // we do this because we may want to replace color in the middle.
      Map<String, String> colorNameSymbolMap = new HashMap<>();
      for (int i = 0; i < image.getHeight(); i++) {
        for (int j = 0; j < image.getWidth(); j++) {
          Color color = new Color(imageMatrix[i][j][0], imageMatrix[i][j][1],
                                  imageMatrix[i][j][2]);
          colorNameSymbolMap
              .put(dmcColorDictionary.get(color), dmcSymbolDictionary.get(color));
        }
      }

      StringBuilder sb = new StringBuilder();
      sb.append(textPatterns);
      sb.append("\n");
      sb.append("LEGEND:\n");
      java.util.List<Integer> intDMC = new ArrayList<>();
      List<String> strDMC = new ArrayList<>();
      for (String dmcName : colorNameSymbolMap.keySet()) {
        try {
          int value = Integer.parseInt(dmcName);
          intDMC.add(value);
        } catch (NumberFormatException e) {
          if (dmcName != null) {
            strDMC.add(dmcName);
          }
        }
      }

      Collections.sort(intDMC);
      Collections.sort(strDMC);
      for (int dmcName : intDMC) {
        sb.append(colorNameSymbolMap.get(Integer.toString(dmcName))).append("  ")
          .append("DMC-").append(dmcName).append("\n");
      }

      for (String dmcName : strDMC) {
        sb.append(colorNameSymbolMap.get(dmcName)).append("  ").append("DMC-")
          .append(dmcName).append("\n");
      }

      // 3. open a file chooser to save
      if (toSave) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("text", "txt"));
        int fcResult = fileChooser.showSaveDialog(scrollerPanel);
        if (fcResult == JFileChooser.APPROVE_OPTION) {
          String savePath = fileChooser.getSelectedFile().toString();
          try {
            FileWriter writer = new FileWriter(savePath, false);
            writer.write(sb.toString());
            writer.close();
            JOptionPane
                .showMessageDialog(scrollerPanel, "Text Pattern Saved Successfully!");
          } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(scrollerPanel,
                                          "ERROR: can not access to the save " +
                                              "location specified for saving this " +
                                              "text pattern.");
          }
        }
      } else {
        // debug mode.
        // System.out.println(sb);
      }
      result += sb.toString();
    }
    return result;
  }

  @Override public void createAndShowTextGUI(BatchTextView textView) {
    new BatchTextController(textView);
  }

  @Override public void addTextOverLay() {
    // System.out.println("Overlay!");
    if (file == null) {
      JOptionPane.showMessageDialog(null, "ERROR: Must load an image first.");
      return;
    }

    Graphics2D g2d = currentImageCache.createGraphics();
    g2d.drawImage(currentImageCache, 0, 0, currentImageCache.getWidth(),
                  currentImageCache.getHeight(), null);
    g2d.setPaint(view.selectNewColor());
    //g2d.setFont(new Font("Amsterdam", Font.BOLD, 40));
    String inputString = view.getUserInputString();
    int superPixelSize = model.getImage().getSuperPixelSize();
    int fontSize = 9 * superPixelSize;
    if (fontSize <= 0) {
      fontSize = view.getInputFontSize();
    }
    g2d.setFont(new Font("Amsterdam", Font.BOLD, fontSize));
    FontMetrics fm = g2d.getFontMetrics();
    //    int x = currentImageCache.getWidth() - fm.stringWidth(inputString) - 5;
    // int x = 0;
    // int y = fm.getHeight();
    int x = view.getInputX();
    int y = view.getInputY();
    g2d.drawString(inputString, x, y);
    g2d.dispose();

    imageDisplay.setIcon(new ImageIcon(currentImageCache));
    scrollerPanel.repaint();

    // update the model with the new image with words on it.
    Image newImage = new ImageImpl(currentImageCache, filename);
    if (superPixelSize > 0) {
      newImage.setSuperPixelSize(superPixelSize);
    }
    model.setImage(newImage);
  }

  @Override public void overlaySymbols() {
    if (file == null) {
      JOptionPane.showMessageDialog(null, "ERROR: Must load an image first.");
      return;
    }
    String textPattern = model.getImage().getTextPattern();
    if (textPattern.equals("")) {
      JOptionPane.showMessageDialog(null, "ERROR: Must call generate pattern first.");
      return;
    }
    String[] textPatternsByLine = textPattern.split("\n");
    Graphics2D g2d = currentImageCache.createGraphics();
    g2d.drawImage(currentImageCache, 0, 0, currentImageCache.getWidth(),
                  currentImageCache.getHeight(), null);
    g2d.setPaint(Color.BLACK);
    int superPixelSize = model.getImage().getSuperPixelSize();
    int fontSize = (int) Math.round(superPixelSize * 0.75);
    g2d.setFont(new Font("Amsterdam", Font.BOLD, fontSize));
    FontMetrics fm = g2d.getFontMetrics();

    int x = 0;
    int y = fm.getHeight();
    for (String linePattern: textPatternsByLine) {
      g2d.drawString(linePattern, x, y);
      y += superPixelSize;
    }

    g2d.dispose();

    imageDisplay.setIcon(new ImageIcon(currentImageCache));
    scrollerPanel.repaint();
  }

  @Override public void chooseDmcColors() {
    view.chooseDmcColors();
  }

  @Override public void exitProgram() {
    System.exit(0);
  }

  /**
   * Helper function for using commands from the Command pattern.
   *
   * @param commandName string name of the command to execute.
   * @param command     the cross stitch command object for execution.
   */
  private void processImage(String commandName, CrossStitchCommand command) {
    JOptionPane.showMessageDialog(scrollerPanel, commandName + " Command Received!");
    if (!model.modelHasImage()) {
      JOptionPane.showMessageDialog(scrollerPanel, "Error: Can not find the image!");
      return;
    }
    command.execute(model);
    Image modelImage = model.getImage();
    currentImageCache = getBufferedImage(modelImage.getMatrix(), modelImage.getWidth(),
                                         modelImage.getHeight());
    imageDisplay.setIcon(new ImageIcon(currentImageCache));
    scrollerPanel.repaint();
    JOptionPane.showMessageDialog(scrollerPanel, commandName + " Command Finished!");
  }
}
