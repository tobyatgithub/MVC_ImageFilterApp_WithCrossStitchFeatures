package crossstitchmvc.model.imageeffect;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static crossstitchmvc.utility.ImageUtilities.redMeanDistance;

/**
 * Implementation of pattern generation effect.
 * Here we get an image with positive super-pixel info -> which means
 * it has already been preprocessed by mosaics or pixelation.
 * Next,
 * 1. calculate out how many rows and cols there shall be
 * 2. load dmc color dictionary
 * 3. read superpixel one by one (via calculating its correct location based
 * on super-pixel size info), and find the closest dmc color to use.
 * 4. update the image with new dmc color for display.
 * 5. create the text pattern with legend.
 */
public class PatternGeneration implements Filter3d {
  private final Map<Color, String> dmcSymbolDictionary;
  private final Map<Color, String> dmcColorDictionary;

  /**
   * Default constructor that takes in two dictionaries for
   * less IO back and forth.
   *
   * @param fileName            filename to save the pattern txt file.
   * @param dmcColorDictionary  a dictionary with key = color, and value = dmc name
   * @param dmcSymbolDictionary a dinctionary with key = color, and value = string
   *                            symbol
   */
  public PatternGeneration(
      String fileName, Map<Color, String> dmcColorDictionary,
      Map<Color, String> dmcSymbolDictionary) {
    // and we allow the fileName to be null.
    this.dmcColorDictionary = dmcColorDictionary;
    this.dmcSymbolDictionary = dmcSymbolDictionary;
  }

  @Override public int[][][] filter(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null.");
    }
    // 1. calculate out how many rows and cols there shall be
    int squareSize = image.getSuperPixelSize();
    if (squareSize <= 0) {
      throw new IllegalStateException("The squareSize can not be found.");
    }

    int[][][] original = image.getMatrix();
    int imageHeight = image.getHeight();
    int numberInRow = imageHeight / squareSize;
    int remainderRow = imageHeight % squareSize;

    int imageWidth = image.getWidth();
    int numberInColumn = imageWidth / squareSize;
    int remainderColumn = imageWidth % squareSize;
    // this will be the pattern image to display.
    int[][][] processedImage = new int[imageHeight][imageWidth][3];

    // this is for generate the text pattern. The value at each location will
    // be the colorIndex in the colors Array.
    Color[][] patternMatrix = new Color[numberInRow][numberInColumn];
    // store all the color used.
    List<Color> colors = new ArrayList<>();

    // 2. load dmc color dictionary
    Map<Color, Color> rgbToClosestDmc = new HashMap<>();
    Map<String, String> colorNameSymbolMap = new HashMap<>();

    // 3. read superpixel one by one and find the closest dmc color to use.
    int row = 0;
    int column = 0;
    int i = 0;
    int j = 0;
    int endRow;
    int endColumn;
    String closestDmcName;
    String closestDmcSymbol;
    Color closestDmcColor;
    while (row < imageHeight) {
      endRow = remainderRow >= 1 ? row + squareSize + 1 : row + squareSize;
      while (column < imageWidth) {
        endColumn =
            remainderColumn >= 1 ? column + squareSize + 1 : column + squareSize;
        Color color = new Color(original[row][column][0], original[row][column][1],
                                original[row][column][2]);

        if (!rgbToClosestDmc.containsKey(color)) {
          closestDmcColor = findClosestDmcColor(dmcColorDictionary, color);
          rgbToClosestDmc.put(color, closestDmcColor);

          closestDmcName = dmcColorDictionary.get(closestDmcColor);
          closestDmcSymbol = dmcSymbolDictionary.get(closestDmcColor);
          colorNameSymbolMap.put(closestDmcName, closestDmcSymbol);
        } else {
          closestDmcColor = rgbToClosestDmc.get(color);
        }
        patternMatrix[i][j] = closestDmcColor;
        column = endColumn;
        remainderColumn = Math.max(remainderColumn - 1, 0);
        j++;
      }
      i++;
      j = 0;
      row = endRow;
      column = 0;
      remainderRow = Math.max(remainderRow - 1, 0);
      remainderColumn = imageWidth % squareSize;
    }

    // 4. update the image with new dmc color for display
    for (int k = 0; k < imageHeight; k++) {
      for (int l = 0; l < imageWidth; l++) {
        Color color =
            new Color(original[k][l][0], original[k][l][1], original[k][l][2]);
        closestDmcColor = rgbToClosestDmc.get(color);
        processedImage[k][l][0] = closestDmcColor.getRed();
        processedImage[k][l][1] = closestDmcColor.getGreen();
        processedImage[k][l][2] = closestDmcColor.getBlue();
      }
    }

    // 5. create the text pattern with legend.
    System.out.println("Start writing");
    //File fileToWrite = new File(this.fileName + ".txt");
    StringBuilder sb = new StringBuilder();

    for (int r = 0; r < numberInRow; r++) {
      for (int c = 0; c < numberInColumn; c++) {
        String symbol = dmcSymbolDictionary.get(patternMatrix[r][c]);
        sb.append(symbol + "  ");
      }
      sb.append('\n');
    }

    System.out.println("Finished writing.");
    image.setTextPattern(sb.toString());
    System.out.println(sb.toString());
    return processedImage;
  }

  /**
   * Helper method of finding the closest DMC color of any given rpg color.
   *
   * @param dictionaryDMC the dictionary containing dmc-to-rgb info.
   * @param target        a target rgb color.
   * @return the closest dmc color.
   */
  private Color findClosestDmcColor(
      Map<Color, String> dictionaryDMC, Color target) {
    double minDistance = Double.MAX_VALUE;
    Color result = null;

    //remove and reserve the blank dmc color
    dictionaryDMC.remove(new Color(254, 254, 254));

    for (Map.Entry<Color, String> entry : dictionaryDMC.entrySet()) {
      Color dmcColor = entry.getKey();
      double deltaC = redMeanDistance(target, dmcColor);
      if (deltaC < minDistance) {
        minDistance = deltaC;
        result = dmcColor;
      }
    }
    if (result == null) {
      System.out.println("Something went wrong in finding the closest DMC color.");
    }
    return result;
  }

  @Override public String toString() {
    return "Pattern Generation Filter";
  }
}
