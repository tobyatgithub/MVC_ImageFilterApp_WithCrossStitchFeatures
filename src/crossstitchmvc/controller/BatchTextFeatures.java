package crossstitchmvc.controller;

/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the TextBatchView, to pass control to the controller.
 * How the view uses them as callbacks is completely up to how the view is designed
 * (e.g. it could use them as a callback for a button, or a callback for a
 * dialog box, or a set of text inputs, etc.)
 *
 * <p>Each function is designed to take in the necessary data to complete that
 * functionality.
 */
public interface BatchTextFeatures {
  /**
   * Open the txt batch file.
   */
  void openBatchFile();

  /**
   * Save the string in the textArea to a file.
   */
  void saveBatchFile();

  /**
   * Pass the text input in the text field to the textArea for display.
   */
  void enterTypedText();

  /**
   * Clear all the text int the textArea.
   */
  void clearButton();

  /**
   * Run the current commands in the textArea.
   */
  void runBatchFile();

  /**
   * Run a script from opening a batch file directly.
   * It is basically open batch file + run batch file.
   */
  void runFromBatchFile();
}
