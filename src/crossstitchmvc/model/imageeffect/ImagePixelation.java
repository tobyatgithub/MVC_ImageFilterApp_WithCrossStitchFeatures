package crossstitchmvc.model.imageeffect;

/**
 * Filter class of image pixelation effect.
 */
public class ImagePixelation implements Filter3d {
  int numberOfSuperPixelInWidth; // in width

  /**
   * Default constructor.
   * @param numberOfSuperPixelInWidth the parameter for pixelation.
   */
  public ImagePixelation(int numberOfSuperPixelInWidth) {
    this.numberOfSuperPixelInWidth = numberOfSuperPixelInWidth;
  }

  @Override public int[][][] filter(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null.");
    }
    int height = image.getHeight(); //~row
    int width = image.getWidth();  //~column
    int[][][] original = image.getMatrix();
    int[][][] processed = new int[height][width][3];

    int squareSize = width / this.numberOfSuperPixelInWidth;
    int remainderRow = height % squareSize;
    int remainderColumn = width % squareSize;
    int numberOfSuperPixelInHeight = height / squareSize;
    image.setSuperPixelSize(squareSize);

    // Prevent the case where you won't have enough super pixels to spare your
    // remainders.
    if ((remainderColumn > numberOfSuperPixelInWidth) ||
        (remainderRow > numberOfSuperPixelInHeight)) {
      throw new IllegalArgumentException(
          "The parameter for Image Pixelation is too small.");
    }

    int[][] superPixel =
        new int[numberOfSuperPixelInHeight][numberOfSuperPixelInWidth];

    // while loop
    int centerX;
    int centerY;
    int endRow;
    int endColumn;
    int row = 0;
    int column = 0;
    while (row < height) {
      endRow = remainderRow >= 1 ? row + squareSize + 1 : row + squareSize;

      while (column < width) {
        // change the color within the range row ~ row+squareSize+(remainderWidth > 1)
        // to col ~ col+squareSize+(remainderColumn > 1)
        endColumn =
            remainderColumn >= 1 ? column + squareSize + 1 : column + squareSize;
        centerX = (row + endRow) / 2;
        centerY = (column + endColumn) / 2;
        for (int i = row; i < endRow; i++) {
          for (int j = column; j < endColumn; j++) {
            processed[i][j][0] = original[centerX][centerY][0];
            processed[i][j][1] = original[centerX][centerY][1];
            processed[i][j][2] = original[centerX][centerY][2];
          }
        }
        column = endColumn;
        remainderColumn = Math.max(remainderColumn - 1, 0);
      }

      row = endRow;
      column = 0;
      remainderRow = Math.max(remainderRow - 1, 0);
      remainderColumn =
          width % squareSize; // re-init remainderColumn after finish one row
    }

    return processed;
  }

  @Override public String toString() {
    return "Pixelation Filter";
  }
}
