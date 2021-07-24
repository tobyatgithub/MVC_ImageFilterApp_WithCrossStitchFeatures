package crossstitchmvc.model.imageeffect;

import crossstitchmvc.utility.ImageUtilities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Concrete class to implement image interface.
 */
public class ImageImpl implements Image {
  private       int[][][] matrix;
  private final String    filename;
  private final int       height;
  private final int       width;
  private       int       superPixelSize;
  private       String    textPattern;

  /**
   * Constructor of an image object.
   *
   * @param filename A string indication the location of the image file to read
   * @throws IOException If such image does not exist
   */
  public ImageImpl(String filename) throws IOException {
    if (filename == null) {
      throw new IllegalArgumentException("ImageImp: filename can not be null.");
    }
    this.filename = filename;
    this.matrix = ImageUtilities.readImage(filename);
    this.height = ImageUtilities.getHeight(filename);
    this.width = ImageUtilities.getWidth(filename);
    this.superPixelSize = -1;
    this.textPattern = "";
  }

  /**
   * Test constructor for extra credit #3.
   *
   * @param bufferedImage a bufferedImage object.
   * @param filename      a string file name.
   */
  public ImageImpl(BufferedImage bufferedImage, String filename) {
    int[][][] result = new int[bufferedImage.getHeight()][bufferedImage
        .getWidth()][ImageUtilities.Channel.values().length];

    for (int i = 0; i < bufferedImage.getHeight(); i++) {
      for (int j = 0; j < bufferedImage.getWidth(); j++) {
        int color = bufferedImage.getRGB(j, i);
        Color c = new Color(color);
        result[i][j][ImageUtilities.Channel.RED.ordinal()] = c.getRed();
        result[i][j][ImageUtilities.Channel.GREEN.ordinal()] = c.getGreen();
        result[i][j][ImageUtilities.Channel.BLUE.ordinal()] = c.getBlue();
      }
    }
    this.matrix = result;
    this.filename = filename;
    this.height = bufferedImage.getHeight();
    this.width = bufferedImage.getWidth();
    this.superPixelSize = -1;
    this.textPattern = "";
  }

  /**
   * Helper method for the r,g,b channel getters.
   *
   * @param matrix  the 3d integer matrix of an image.
   * @param channel indicating which channel to get, 0=red, 1=green, 2=blue
   * @return a 2d integer matrix of the specified channel.
   */
  private int[][] getChannelFromMatrix(int[][][] matrix, int channel) {
    int[][] result = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        result[i][j] = matrix[i][j][channel];
      }
    }
    return result;
  }

  @Override public int[][][] getMatrix() {
    return matrix.clone();
  }

  @Override public int[][] getRedChannel() {
    return getChannelFromMatrix(matrix, 0);
  }

  @Override public int[][] getGreenChannel() {
    return getChannelFromMatrix(matrix, 1);
  }

  @Override public int[][] getBlueChannel() {
    return getChannelFromMatrix(matrix, 2);
  }

  @Override public int getHeight() {
    return height;
  }

  @Override public int getWidth() {
    return width;
  }

  @Override public int getSuperPixelSize() {
    return this.superPixelSize;
  }

  @Override public void setSuperPixelSize(int size) {
    this.superPixelSize = size;
  }

  @Override public void setTextPattern(String textPattern) {
    this.textPattern = textPattern;
  }

  @Override public String getTextPattern() {
    return this.textPattern;
  }

  @Override public void save(String filename) throws IOException {
    ImageUtilities.writeImage(matrix, width, height, filename);
  }

  @Override public void apply(Filter3d filter3d) {
    if (filter3d == null) {
      throw new IllegalArgumentException("ImageImp: Filter can not be null.");
    }
    this.matrix = filter3d.filter(this);
  }

  @Override public String toString() {
    return "ImageImpl{" + filename + '}' + "\nheight = " + height + "\nwidth = " +
        width;
  }
}
