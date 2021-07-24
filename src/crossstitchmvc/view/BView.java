package crossstitchmvc.view;

import crossstitchmvc.controller.BatchTextFeatures;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The interface for the BatchTextView. Here we will define all
 * the methods the a batch text view must have.
 */
public interface BView {
  /**
   * Connecting the view with the feature class that will provide
   * all the callback functions for different actionListeners.
   *
   * @param f a TextBatchFeature object.
   */
  void setFeatures(BatchTextFeatures f);

  /**
   * Getter for the input text area.
   *
   * @return a input text area.
   */
  JTextField getInputTextArea();

  /**
   * Getter for the text display area.
   *
   * @return a text display area.
   */
  JTextArea getTextArea();

  /**
   * Pop out a showOpenDialog and ask user to specify which batch text file to open.
   *
   * @return the string file path of the chosen file.
   */
  String openBatchFile();

  /**
   * Pop out a showOpenDialog and ask user to specify which batch text file to save.
   *
   * @return the string file path of the chosen file.
   */
  String saveBatchFile();
}
