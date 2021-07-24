package crossstitchmvc.view;

import crossstitchmvc.controller.ImageFeatures;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Color;

/**
 * The interface for our view class.
 */
public interface IView {
  /**
   * Init the feature callback functions for each button and menu items.
   *
   * @param f the imageFeatures object which has the callback functions needed.
   */
  void setFeatures(ImageFeatures f);

  /**
   * Select an image to open and display on GUI.
   * @param f  the imageFeatures object which has the callback functions needed.
   */
  void openImage(ImageFeatures f);

  /**
   * Save the current image to a location.
   * @param f  the imageFeatures object which has the callback functions needed.
   */
  void saveImage(ImageFeatures f);

  /**
   * Get the scroller panel where the image panel will be at.
   *
   * @return the scroller panel.
   */
  JScrollPane getScrollerPanel();

  /**
   * Get the image display JLabel where image will be displayed.
   *
   * @return the JLabel.
   */
  JLabel getImageDisplay();

  /**
   * Open a new Color choose panel with all DMC colors and pick out one.
   *
   * @return the RGB Color chosen.
   */
  Color selectNewColor();

  /**
   * Open a dialog and ask user for input string.
   * @return
   */
  String getUserInputString();

  /**
   * Pop out a window and ask user about the font size to use.
   * @return  an integer font size.
   */
  int getInputFontSize();

  /**
   * Pop out a window and ask user about the x location for the text.
   * @return  an integer x location.
   */
  int getInputX();

  /**
   * Pop out a window and ask user about the y location for the text.
   * @return  an integer y location.
   */
  int getInputY();

  /**
   * Extra credit #2, ask uses to multi select allowed dmc colors.
   */
  void chooseDmcColors();
}
