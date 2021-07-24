import crossstitchmvc.controller.BatchTextFeatures;
import crossstitchmvc.view.BView;
import crossstitchmvc.view.BatchTextView;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Mock Text View used in the test.
 */
public class MockTextView extends BatchTextView implements BView {
  private final StringBuilder log;

  /**
   * Constructor of this mock text view.
   * @param title a string title.
   * @param log the stringbuilder for logging the info.
   */
  public MockTextView(String title, StringBuilder log) {
    super(title);
    if ((title == null) || (log == null)) {
      throw new IllegalArgumentException(
          "MockTextView: title and log can not be null.");
    }
    this.log = log;
    log.append("MockTextView init.\n");
  }

  @Override public void setFeatures(BatchTextFeatures f) {
    if (f == null) {
      throw new IllegalArgumentException(
          "MockTextView: Features and log can not be null.");
    }
    log.append("MockTextView: setFeatures called.\n");
  }

  @Override public JTextField getInputTextArea() {
    log.append("MockTextView: getInputTextArea called.\n");
    return new JTextField();
  }

  @Override public JTextArea getTextArea() {
    log.append("MockTextView: getTextArea called.\n");
    return new JTextArea();
  }

  @Override public String openBatchFile() {
    log.append("MockTextView: openBatchFile called.\n");
    return null;
  }

  @Override public String saveBatchFile() {
    log.append("MockTextView: saveBatchFile called.\n");
    return null;
  }
}
