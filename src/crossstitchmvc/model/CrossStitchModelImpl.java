package crossstitchmvc.model;

import crossstitchmvc.model.imageeffect.Filter3d;
import crossstitchmvc.model.imageeffect.Image;
import crossstitchmvc.model.imageeffect.ImageImpl;

import java.awt.Color;
import java.io.IOException;

/**
 * Concrete model class implements the interface.
 */
public class CrossStitchModelImpl implements CrossStitchModel {
  private Image image;

  /**
   * Default Constructor.
   */
  public CrossStitchModelImpl() {
    this.image = null;
  }

  @Override public void apply(Filter3d filter) {
    image.apply(filter);
  }

  @Override public void loadImage(String fileName) throws IOException {
    //    fileName = new File("").getAbsolutePath() + "/res/" + fileName;
    image = new ImageImpl(fileName);
  }

  @Override public void saveImage(String fileName) throws IOException {
    //    fileName = new File("").getAbsolutePath() + "/res/" + fileName;
    image.save(fileName);
  }

  @Override public boolean modelHasImage() {
    return (image != null);
  }

  @Override public Image getImage() {
    return image;
  }

  @Override public void setImage(Image image) {
    this.image = image;
  }

  @Override public Color getColor(int y, int x) {
    int[][][] matrix = image.getMatrix();
    return new Color(matrix[x][y][0], matrix[x][y][1], matrix[x][y][2]);
  }
}
