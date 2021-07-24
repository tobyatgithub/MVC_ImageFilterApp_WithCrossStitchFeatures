import crossstitchmvc.controller.ImageFeatures;
import crossstitchmvc.view.IView;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Color;

/**
 * Mock view class for testing.
 */
public class MockImageView extends JFrame implements IView {
  private final StringBuilder log;

  /**
   * Constructor of this mock image view.
   * @param title  a string title.
   * @param log  a stringBuilder for logging info.
   */
  public MockImageView(String title, StringBuilder log) {
    super(title);
    if ((title == null) || (log == null)) {
      throw new IllegalArgumentException("MockView: title and log can not be null.");
    }
    this.log = log;
    log.append("MockView: MockView init.\n");
  }

  @Override public void setFeatures(ImageFeatures f) {
    if (f == null) {
      throw new IllegalArgumentException(
          "MockView: Features and log can not be null.");
    }
    log.append("MockView: setFeatures called.\n");
  }

  @Override public void openImage(ImageFeatures f) {
    log.append("MockView: openImage");
  }

  @Override public void saveImage(ImageFeatures f) {
    log.append("MockView: saveImage");
  }

  @Override public JScrollPane getScrollerPanel() {
    log.append("MockView: getScrollerPanel called.\n");
    return new JScrollPane();
  }

  @Override public JLabel getImageDisplay() {
    log.append("MockView: getImageDisplay called.\n");
    return new JLabel();
  }

  @Override public Color selectNewColor() {
    log.append("MockView: selectNewColor called.\n");
    return null;
  }

  @Override public String getUserInputString() {
    log.append("MockView: getUserInputString called.\n");
    return null;
  }

  @Override public int getInputFontSize() {
    log.append("MockView: getInputFontSize called.\n");
    return 0;
  }

  @Override public int getInputX() {
    log.append("MockView: getInputX called.\n");
    return 0;
  }

  @Override public int getInputY() {
    log.append("MockView: getInputY called.\n");
    return 0;
  }

  @Override public void chooseDmcColors() {
    log.append("MockView: chooseDmcColors called.\n");
  }
}
