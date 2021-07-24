package crossstitchmvc.utility;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Image utility class that has methods to read an image from file and write to
 * a file.
 */
public class ImageUtilities {
  /**
   * An enumeration of the different channels in our images.
   */
  public enum Channel {
    RED, GREEN, BLUE
  }

  /**
   * Read an image from a file and return it as a 2D array of RGB colors.
   *
   * @param filename The name of the file containing the image to read
   * @return a 2D array of RGB colors
   * @throws IOException if there is an error reading the file
   */
  public static int[][][] readImage(String filename) throws IOException {
    BufferedImage input;

    input = ImageIO.read(new FileInputStream(filename));
    if (input == null) {
      throw new IOException("Can not read image at : " + filename);
    }
    int[][][] result =
        new int[input.getHeight()][input.getWidth()][Channel.values().length];

    for (int i = 0; i < input.getHeight(); i++) {
      for (int j = 0; j < input.getWidth(); j++) {
        int color = input.getRGB(j, i);
        Color c = new Color(color);
        result[i][j][Channel.RED.ordinal()] = c.getRed();
        result[i][j][Channel.GREEN.ordinal()] = c.getGreen();
        result[i][j][Channel.BLUE.ordinal()] = c.getBlue();
      }
    }
    return result;
  }

  /**
   * The width of the image in a file.
   *
   * @param filename The name of the file containing an image.
   * @return The width of the image contained in the file.
   * @throws IOException if there is an error reading the file
   */
  public static int getWidth(String filename) throws IOException {
    BufferedImage input;

    input = ImageIO.read(new FileInputStream(filename));

    return input.getWidth();
  }

  /**
   * The height of the image in a file.
   *
   * @param filename The name of the file containing an image.
   * @return The height of the image contained in the file.
   * @throws IOException if there is an error reading the file
   */
  public static int getHeight(String filename) throws IOException {
    BufferedImage input;

    input = ImageIO.read(new FileInputStream(filename));

    return input.getHeight();
  }

  /**
   * Write the content of a 2D array of RGB colors to a file.
   *
   * @param rgb      The 2D array of RGB values that will be written to the file
   * @param width    The width of the image to be written
   * @param height   The height of the image to be written
   * @param filename The name of the file containing the image to read
   * @throws IOException if there is an error reading the file
   */
  public static void writeImage(int[][][] rgb, int width, int height, String filename)
      throws IOException {

    BufferedImage output =
        new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = rgb[i][j][Channel.RED.ordinal()];
        int g = rgb[i][j][Channel.GREEN.ordinal()];
        int b = rgb[i][j][Channel.BLUE.ordinal()];

        // color is stored in 1 integer, with the 4 bytes storing ARGB in that
        // order. Each of r,g,b are stored in 8 bits (hence between 0 and 255).
        // So we put them all in one integer by using bit-shifting << as below
        int color = (r << 16) + (g << 8) + b;
        output.setRGB(j, i, color);
      }
    }
    String extension = filename.substring(filename.indexOf(".") + 1);
    ImageIO.write(output, extension, new FileOutputStream(filename));
  }

  /**
   * Writes an RGB color array to a file as an image.
   *
   * @param rgb    the color array to write to a file.
   * @param width  the width of the image file.
   * @param height the height of the image file.
   * @throws IOException if the file cannot be written to.
   */
  public static BufferedImage getBufferedImage(int[][][] rgb, int width, int height) {
    if (rgb == null) {
      throw new IllegalArgumentException("rgb array cannot be null");
    }
    BufferedImage output =
        new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = rgb[i][j][Channel.RED.ordinal()];
        int g = rgb[i][j][Channel.GREEN.ordinal()];
        int b = rgb[i][j][Channel.BLUE.ordinal()];

        // color is stored in 1 integer, with the 4 bytes storing ARGB in that
        // order. Each of r,g,b are stored in 8 bits (hence between 0 and 255).
        // So we put them all in one integer by using bit-shifting << as below
        int color = (r << 16) + (g << 8) + b;
        output.setRGB(j, i, color);
      }
    }
    return output;
  }

  /**
   * Helper function that validates the filename.
   *
   * @param filename the filename that needs to be validated
   * @throws IllegalArgumentException if the filename is null
   */
  private static void checkFilename(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("filename is cannot be null");
    }
  }

  /**
   * Helper method to read csv file to get information about DMC-to-RGB.
   *
   * @param fileName filename of the csv file to read.
   * @return a map between DMC color name and its RGB value.
   * @throws IOException if the filename is not valid.
   */
  public static Map<Color, String> readDmcColors(String fileName) {
    Map<Color, String> result = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        Color color =
            new Color(Integer.parseInt(values[2]), Integer.parseInt(values[3]),
                      Integer.parseInt(values[4]));
        result.put(color, values[0]);
      }
    } catch (IOException e) {
      // do nothing
      System.out.println("Read DMC csv failed.");
    }
    return result;
  }

  /**
   * Helper method to read csv file to get information about DMC and its symbol.
   *
   * @param fileName filename of the csv file to read.
   * @return a map between DMC color name and its utf8 symbol.
   * @throws IOException if the filename is not valid.
   */
  public static Map<Color, String> readDmcSymbols(String fileName) {
    Map<Color, String> result = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        Color color =
            new Color(Integer.parseInt(values[2]), Integer.parseInt(values[3]),
                      Integer.parseInt(values[4]));
        result.put(color, values[6]);
      }
    } catch (IOException e) {
      // do nothing
      System.out.println("Read DMC csv failed.");
    }
    return result;
  }

  /**
   * Calculate the red mean distance between two rgb colors.
   * @param thisColor the rgb color1.
   * @param thatColor the rgb color2.
   * @return the red mean distance between color1 and color2.
   */
  public static double redMeanDistance(Color thisColor, Color thatColor) {
    if ((thisColor == null) || (thatColor == null)) {
      throw new IllegalArgumentException("Input colors can not be null.");
    }
    double redMean = (thisColor.getRed() + thatColor.getRed()) / 2.0;
    double deltaC = Math.sqrt(
        (2 + redMean / 256.0) * Math.pow(thisColor.getRed() - thatColor.getRed(), 2) +
            4 * Math.pow(thisColor.getGreen() - thatColor.getGreen(), 2) +
            (2 + (255 - redMean) / 256.0) *
                Math.pow(thisColor.getBlue() - thatColor.getBlue(), 2));
    return deltaC;
  }
}
