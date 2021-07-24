package crossstitchmvc.model.imageeffect;

import java.io.IOException;

/**
 * Interface for image.
 */
public interface Image {
  /**
   * Save an image to the specified filename/path.
   * @param filename  A string indicating the path location to save
   */
  void save(String filename) throws IOException;

  /**
   * Apply a filter onto an image.
   * @param filter3d  a filter object that manipulate the image
   */
  void apply(Filter3d filter3d);

  /**
   * Getter for Image.matrix, which stores all digit information.
   * @return  a 3d integer matrix as in a 3d array.
   */
  int[][][] getMatrix();

  /**
   * Getter for the red channel digit information of the image.
   * @return  a 2d integer matrix as in a 2d array.
   */
  int[][] getRedChannel();

  /**
   * Getter for the green channel digit information of the image.
   * @return  a 2d integer matrix as in a 2d array.
   */
  int[][] getGreenChannel();

  /**
   * Getter for the blue channel digit information of the image.
   * @return  a 2d integer matrix as in a 2d array.
   */
  int[][] getBlueChannel();

  /**
   * Return the height of the image.
   * @return the height of the image.
   */
  int getHeight();

  /**
   * Return the width of the image.
   * @return the width of the image.
   */
  int getWidth();

  /**
   * Return the size of the super pixel.
   * By default it will be -1 to indicate its initial state.
   * This is desgined to be used by Mosaic and Pixelation methods.
   * @return the size of the super pixel.
   */
  int getSuperPixelSize();

  /**
   * Set the size of the super pixel.
   * @param size the size of the super pixel (which shall be calculated during
   *             the process of Mosaic and Pixelation method.)
   */
  void setSuperPixelSize(int size);

  /**
   * Set the text pattern without Legend to save. We will generate Legend later
   * on the fly due to possible color changes.
   * @param textPattern  the String text pattern with legend info.
   */
  void setTextPattern(String textPattern);

  /**
   * Get the text pattern to save.
   * @return a String with text pattern in it.
   */
  String getTextPattern();
}
