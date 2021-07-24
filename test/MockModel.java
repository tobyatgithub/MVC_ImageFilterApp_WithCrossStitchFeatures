import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.Filter3d;
import crossstitchmvc.model.imageeffect.Image;

import java.awt.Color;
import java.io.IOException;

/**
 * A mock cross stitch model for testing purpose.
 */
public class MockModel implements CrossStitchModel {
  private StringBuilder log;
  private Image         image;

  /**
   * Constructor.
   *
   * @param log logger for showing debug info.
   */
  public MockModel(StringBuilder log) {
    if (log == null) {
      throw new IllegalArgumentException("MockModel: log can not be null.");
    }
    this.log = log;
    this.image = null;
    log.append("MockModel: MockModel init.\n");
  }

  @Override public void setImage(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("MockModel: setImage image can not be null.");
    }
    this.image = image;
    this.image.setSuperPixelSize(1);
    this.image.setTextPattern("TEST");
  }

  @Override public void apply(Filter3d filter) {
    if (filter == null) {
      throw new IllegalArgumentException("MockModel.apply: Filter can not be null.");
    }
    //TODO: add toString methods for filters.
    log.append(String.format("Filter: %s was called.\n", filter.toString()));
  }

  @Override public void loadImage(String filePath) throws IOException {
    log.append("MockModel: loadImage\n");
    return;
  }

  @Override public void saveImage(String filePath) throws IOException {
    log.append("MockModel: saveImage\n");
    return;
  }

  @Override public boolean modelHasImage() {
    return (image != null);
  }

  @Override public Image getImage() {
    log.append("MockModel: getImage\n");
    return image;
  }

  @Override public Color getColor(int x, int y) {
    return null;
  }
}
