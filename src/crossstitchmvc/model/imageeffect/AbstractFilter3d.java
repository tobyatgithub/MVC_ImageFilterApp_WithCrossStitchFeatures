package crossstitchmvc.model.imageeffect;

/**
 * Abstract class that implements Filter3d interface.
 * It contains the core method - filter for all filter objects,
 * and also two helper methods that almost every filter will use.
 */
public abstract class AbstractFilter3d implements Filter3d {
  // we assume images are all in 3 dimensional format.
  protected final int DEPTH = 3;

  @Override public int[][][] filter(Image image) {
    throw new UnsupportedOperationException();
  }

  /**
   * Clamp a value to its boundary. That is, if value is smaller
   * than the minimum, change the value to minimum. Same for maximum.
   * @param value  a given int value to clamp.
   * @param min  an integer minimum value that the value will clamp to.
   * @param max  an integer maximum value that the value will clamp to.
   * @return  a clamped integer value.
   */
  protected int clampDigit(int value, int min, int max) {
    value = Math.max(min, value);
    value = Math.min(max, value);
    return value;
  }

  /**
   * Given 2d matrix of R,G,B color channels, convert them into a 3d matrix in the form of
   * height x width x depth.
   * @param redChannel  a 2d matrix with integer values indicating red channel of an image.
   * @param greenChannel  a 2d matrix with integer values indicating green channel of an image.
   * @param blueChannel  a 2d matrix with integer values indicating blue channel of an image.
   * @return  a 3d matrix of an image in the form of height x width x depth.
   */
  protected int[][][] rgbToHWC(int[][] redChannel, int[][] greenChannel, int[][] blueChannel) {
    int height = redChannel.length;
    int width = redChannel[0].length;
    int depth = 3;
    int[][][] result = new int[height][width][depth];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        result[i][j][0] = redChannel[i][j];
        result[i][j][1] = greenChannel[i][j];
        result[i][j][2] = blueChannel[i][j];
      }
    }
    return result;
  }
}
