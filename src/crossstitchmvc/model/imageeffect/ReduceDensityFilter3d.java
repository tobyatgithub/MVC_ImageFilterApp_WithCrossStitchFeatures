package crossstitchmvc.model.imageeffect;

/**
 * Filter class of reduce color density effect.
 */
public class ReduceDensityFilter3d extends AbstractFilter3d {
  private int redMaxNumColor;
  private int greenMaxNumColor;
  private int blueMaxNumColor;

  /**
   * Constructor of reduce color density filter. We make this to make
   * this class more general and can handle more possibilities.
   * @param redMaxNumColor  integer indicating the number of red color to reduce to.
   * @param greenMaxNumColor  integer indicating the number of green color to reduce to.
   * @param blueMaxNumColor  integer indicating the number of blue color to reduce to.
   */
  public ReduceDensityFilter3d(int redMaxNumColor, int greenMaxNumColor, int blueMaxNumColor) {
    this.redMaxNumColor = redMaxNumColor;
    this.greenMaxNumColor = greenMaxNumColor;
    this.blueMaxNumColor = blueMaxNumColor;
  }

  @Override
  public int[][][] filter(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null.");
    }
    int[][] newRed = ditherIt(image.getRedChannel(), redMaxNumColor);
    int[][] newGreen = ditherIt(image.getGreenChannel(), greenMaxNumColor);
    int[][] newBlue = ditherIt(image.getBlueChannel(), blueMaxNumColor);
    return rgbToHWC(newRed, newGreen, newBlue);
  }

  /**
   * Helper method for reduce color density. Here we do it channel
   * by channel.
   * For example, given the 2d matrix of red channel, we reduce the
   * color density of this red channel and return a new 2d matrix
   * containing reduced digit info.
   * The reduce color density is done by using Floyd-Steinberg algorithm.
   * @param matrix  a 2d matrix with integer values indicating a
   *                channel of an image.
   * @param maxNumColor  the number of color to reduce to.
   * @return a new 2d matrix with integer values indicating a
   *         reduced color density channel of an image.
   */
  private int[][] ditherIt(int[][] matrix, int maxNumColor) {
    int row = matrix.length;
    int column = matrix[0].length;
    int[][] dithered = new int[row][column];
    int error;

    for (int i = 0; i < row - 1; i++) {
      for (int j = 1; j < column - 1; j++) {
        // step 1. find new color
        int oldColor = matrix[i][j];
        int newColor = findClosestPaletteColor(oldColor, maxNumColor);
        dithered[i][j] = newColor;

        // step 2. update error
        error = oldColor - newColor;
        matrix[i][j + 1] += error * 7 / 16;
        matrix[i + 1][j - 1] += error * 3 / 16;
        matrix[i + 1][j] += error * 5 / 16;
        matrix[i + 1][j + 1] += error * 1 / 16;
        dithered[i][j] = clampDigit(dithered[i][j], 0, 255);
      }
    }
    return dithered;
  }

  /**
   * Helper method of Floyd-Steinberg algorithm. Here we reduce a RGB color
   * (from 0-255) to the specified max num of color as maxNumColor.
   * Effectively, this function is returning N equal point between 0 and 255.
   * @param oldColor  integer indicating a color in one rgb channel (int between 0 and 255)
   * @param maxNumColor  integer indicating the number of color to reduce to.
   * @return  integer indicating the closest color given the amount of maxNumColor.
   */
  private int findClosestPaletteColor(int oldColor, int maxNumColor) {
    if (maxNumColor <= 1) {
      throw new IllegalArgumentException("Max number of color can not be fewer than 1.");
    }
    int factor = maxNumColor - 1;
    return (int) Math.round(factor * oldColor / 255) * (255 / factor);
  }

  @Override public String toString() {
    return "Reduce Density Filter";
  }
}
