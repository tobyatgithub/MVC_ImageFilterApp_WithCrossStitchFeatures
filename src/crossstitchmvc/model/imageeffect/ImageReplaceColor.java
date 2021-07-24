package crossstitchmvc.model.imageeffect;

import java.awt.Color;

/**
 * The class for replace one color by another for the selected image.
 */
public class ImageReplaceColor implements Filter3d {

  private final int   oldRed;
  private final int   oldGreen;
  private final int   oldBlue;
  private final int   newRed;
  private final int   newGreen;
  private final int   newBlue;

  /**
   * Default constructor.
   * @param oldColor  the rgb color chosen to be replaced.
   * @param newColor  the new rgb color that will replace the old one.
   */
  public ImageReplaceColor(Color oldColor, Color newColor) {
    oldRed = oldColor.getRed();
    oldGreen = oldColor.getGreen();
    oldBlue = oldColor.getBlue();

    newRed = newColor.getRed();
    newGreen = newColor.getGreen();
    newBlue = newColor.getBlue();
  }

  @Override public int[][][] filter(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null.");
    }
    int height = image.getHeight(); //~row
    int width = image.getWidth();  //~column
    int[][][] processed = image.getMatrix();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if ((processed[i][j][0] == oldRed) && (processed[i][j][1] == oldGreen) &&
            (processed[i][j][2] == oldBlue)) {
          processed[i][j][0] = newRed;
          processed[i][j][1] = newGreen;
          processed[i][j][2] = newBlue;
        }
      }
    }
    return processed;
  }

  @Override public String toString() {
    return "Replace Color Filter";
  }
}
