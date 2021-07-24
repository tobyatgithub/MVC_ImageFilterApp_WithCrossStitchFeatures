package crossstitchmvc.controller;

import crossstitchmvc.view.BatchTextView;

import java.io.File;
import java.io.IOException;

/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the ImageView, to pass control to the controller. How
 * the view uses them as callbacks is completely up to how the view is designed
 * (e.g. it could use them as a callback for a button, or a callback for a
 * dialog box, or a set of text inputs, etc.)
 *
 * <p>Each function is designed to take in the necessary data to complete that
 * functionality.
 */
public interface ImageFeatures {
  /**
   * Exit the program.
   */
  void exitProgram();

  /**
   * Restore the image.
   */
  void reloadImage();

  /**
   * Call out a file chooser and ask user to pick an image to load.
   */
  void openImage(File file, String filename) throws IOException;

  /**
   * Call out a file chooser and allow user to save the current image
   * to a specified location.
   */
  void saveImage(String savePath) throws IOException;

  /**
   * Replace the color on the GUI based on the mouse click choice made by user.
   *
   * @param x x location of the mouse click.
   * @param y y location of the mouse click.
   */
  void replaceColor(int x, int y);

  /**
   * Blur the current image and present the result in GUI.
   */
  void blurImage();

  /**
   * Sharpen the current image and present the result in GUI.
   */
  void sharpenImage();

  /**
   * Greyscale the current image and present the result in GUI.
   */
  void greyscaleImage();

  /**
   * Apply Sepia tone to the current image and present in GUI.
   */
  void sepiaImage();

  /**
   * Dither the current image and present the result in GUI.
   */
  void ditherImage();

  /**
   * Mosaics the current image and present the result in GUI.
   */
  void mosaicImage(String parameter);

  /**
   * Pixelate the current image and present the result in GUI.
   */
  void pixelateImage(String parameter);

  /**
   * Generate a cross stitch pattern and present the result in GUI.
   */
  void generatePattern();

  /**
   * Save the text pattern generated from patternGeneration and save it.
   */
  String saveTextPattern(boolean toSave);

  /**
   * Create an empty text field for typing in batch commands.
   */
  void createAndShowTextGUI(BatchTextView textView);

  /**
   * Add a text overlay on top of the GUI.
   */
  void addTextOverLay();

  /**
   * ER Credit2: allow user to specify the range of dmc color they want to use.
   */
  void chooseDmcColors();

  /**
   * Extra credit 1: overlay symbols when display the cross stitch pattern.
   */
  void overlaySymbols();
}
