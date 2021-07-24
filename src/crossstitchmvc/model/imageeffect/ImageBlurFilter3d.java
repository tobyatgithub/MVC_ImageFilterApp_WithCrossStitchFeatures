package crossstitchmvc.model.imageeffect;

/**
 * Filter class of image blur effect.
 */
public class ImageBlurFilter3d extends AbstractFilter3d {
  @Override public int[][][] filter(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null.");
    }
    int[][] newRed = blurIt(image.getRedChannel());
    int[][] newGreen = blurIt(image.getGreenChannel());
    int[][] newBlue = blurIt(image.getBlueChannel());
    return rgbToHWC(newRed, newGreen, newBlue);
  }

  /**
   * Helper method for blurring. Here we do it channel by channel.
   * For example, given the 2d matrix of red channel, we blur this
   * red channel and return a new 2d matrix containing blurred
   * digit info.
   * @param matrix  a 2d matrix with integer values indicating a
   *                channel of an image.
   * @return  a new 2d matrix with integer values indicating a
   *          blurred channel of an image.
   */
  private int[][] blurIt(int[][] matrix) {
    int row = matrix.length;
    int column = matrix[0].length;
    int[][] blurred = new int[row][column];

    for (int i = 1; i < row - 1; i++) {
      for (int j = 1; j < column - 1; j++) {
        blurred[i][j] = (int) (
                    matrix[i - 1][j - 1] * (1 / 16d) +
                    matrix[i - 1][j] * (1 / 8d) +
                    matrix[i - 1][j + 1] * (1 / 16d) +
                    matrix[i][j - 1] * (1 / 8d) +
                    matrix[i][j] * (1 / 4d) +
                    matrix[i][j + 1] * (1 / 8d) +
                    matrix[i + 1][j - 1] * (1 / 16d) +
                    matrix[i + 1][j] * (1 / 8d) +
                    matrix[i + 1][j + 1] * (1 / 16d));
        blurred[i][j] = clampDigit(blurred[i][j], 0, 255);
      }
    }
    return blurred;
  }

  @Override public String toString() {
    return "Blur Filter";
  }
}
