package crossstitchmvc.model.imageeffect;

/**
 * Filter class of image sharpen effect.
 */
public class ImageSharpenFilter3d extends AbstractFilter3d {
  @Override public int[][][] filter(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null.");
    }
    int[][] newRed = sharpIt(image.getRedChannel());
    int[][] newGreen = sharpIt(image.getGreenChannel());
    int[][] newBlue = sharpIt(image.getBlueChannel());
    return rgbToHWC(newRed, newGreen, newBlue);
  }

  /**
   * Helper method for sharpening. Here we do it channel by channel.
   * For example, given the 2d matrix of red channel, we sharpen this
   * red channel and return a new 2d matrix containing sharpened
   * digit info.
   * @param matrix  a 2d matrix with integer values indicating a
   *                channel of an image.
   * @return  a new 2d matrix with integer values indicating a
   *          sharpened channel of an image.
   */
  private int[][] sharpIt(int[][] matrix) {
    int row = matrix.length;
    int column = matrix[0].length;
    int[][] sharpened = new int[row][column];

    for (int i = 2; i < row - 2; i++) {
      for (int j = 2; j < column - 2; j++) {
        sharpened[i][j] = (int) (
            matrix[i - 2][j - 2] * (- 1 / 8d) +
            matrix[i - 2][j - 1] * (- 1 / 8d) +
            matrix[i - 2][j] * (- 1 / 8d) +
            matrix[i - 2][j + 1] * (- 1 / 8d) +
            matrix[i - 2][j + 2] * (- 1 / 8d) +
            matrix[i - 1][j - 2] * (- 1 / 8d) +
            matrix[i - 1][j - 1] * (1 / 4d) +
            matrix[i - 1][j] * (1 / 4d) +
            matrix[i - 1][j + 1] * (1 / 4d) +
            matrix[i - 1][j + 2] * (- 1 / 8d) +
            matrix[i][j - 2] * (- 1 / 8d) +
            matrix[i][j - 1] * (1 / 4d) +
            matrix[i][j] * (1.0d) +
            matrix[i][j + 1] * (1 / 4d) +
            matrix[i][j + 2] * (- 1 / 8d) +
            matrix[i + 1][j - 2] * (- 1 / 8d) +
            matrix[i + 1][j - 1] * (1 / 4d) +
            matrix[i + 1][j] * (1 / 4d) +
            matrix[i + 1][j + 1] * (1 / 4d) +
            matrix[i + 1][j + 2] * (- 1 / 8d) +
            matrix[i + 2][j - 2] * (- 1 / 8d) +
            matrix[i + 2][j - 1] * (- 1 / 8d) +
            matrix[i + 2][j] * (- 1 / 8d) +
            matrix[i + 2][j + 1] * (- 1 / 8d) +
            matrix[i + 2][j + 2] * (- 1 / 8d));
        sharpened[i][j] = clampDigit(sharpened[i][j], 0, 255);
      }
    }
    return sharpened;
  }

  @Override public String toString() {
    return "Sharpen Filter";
  }
}
