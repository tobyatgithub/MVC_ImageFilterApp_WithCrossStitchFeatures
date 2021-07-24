package crossstitchmvc.model.imageeffect;

/**
 * Filter class of color transform effect.
 */
public class ColorTransform3d extends AbstractFilter3d {
  private final double a11;
  private final double a12;
  private final double a13;
  private final double a21;
  private final double a22;
  private final double a23;
  private final double a31;
  private final double a32;
  private final double a33;

  /**
   * Constructor of colorTransform3d. Here we specify how we will do the color transform.
   * i.e. we specify all the parameters here.
   * This allows us to extend the usability of this filter class.
   * color transform will do:
   * r' = a11 * r + a12 * g + a13 * b;
   * g' = a21 * r + a22 * g + a23 * b;
   * b' = a31 * r + a32 * g + a33 * b;
   * @param a11  parameter in the formula above.
   * @param a12  parameter in the formula above.
   * @param a13  parameter in the formula above.
   * @param a21  parameter in the formula above.
   * @param a22  parameter in the formula above.
   * @param a23  parameter in the formula above.
   * @param a31  parameter in the formula above.
   * @param a32  parameter in the formula above.
   * @param a33  parameter in the formula above.
   */
  public ColorTransform3d(double a11, double a12, double a13, double a21, double a22, double a23,
      double a31, double a32, double a33) {
    this.a11 = a11;
    this.a12 = a12;
    this.a13 = a13;
    this.a21 = a21;
    this.a22 = a22;
    this.a23 = a23;
    this.a31 = a31;
    this.a32 = a32;
    this.a33 = a33;
  }

  @Override public int[][][] filter(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null.");
    }
    int[][][] matrix = image.getMatrix();
    int row = matrix.length;
    int column = matrix[0].length;
    int[][][] result = new int[row][column][DEPTH];

    for (int i = 1; i < row - 1; i++) {
      for (int j = 1; j < column - 1; j++) {
        int oldRed = matrix[i][j][0];
        int oldGreen = matrix[i][j][1];
        int oldBlue = matrix[i][j][2];
        int newRed = (int) (a11 * oldRed + a12 * oldGreen + a13 * oldBlue);
        int newGreen = (int) (a21 * oldRed + a22 * oldGreen + a23 * oldBlue);
        int newBlue = (int) (a31 * oldRed + a32 * oldGreen + a33 * oldBlue);
        result[i][j] = new int[]{
            clampDigit(newRed, 0, 255),
            clampDigit(newGreen, 0, 255),
            clampDigit(newBlue, 0, 255)
        };
      }
    }
    return result;
  }

  @Override public String toString() {
    return "ColorTransform Filter";
  }
}
