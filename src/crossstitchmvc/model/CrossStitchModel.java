package crossstitchmvc.model;

import crossstitchmvc.model.imageeffect.Filter3d;
import crossstitchmvc.model.imageeffect.Image;

import java.awt.Color;
import java.io.IOException;

/**
 * Interface for the cross stitch model.
 */
public interface CrossStitchModel {
  /**
   * Apply a filter to the image of this model.
   *
   * @param filter a filter3d object.
   */
  void apply(Filter3d filter);

  /**
   * Load image from the given fileName.
   *
   * @param fileName address of the target image to load.
   * @throws IOException if address is not valid.
   */
  void loadImage(String fileName) throws IOException;

  /**
   * Save image to the given fileName.
   *
   * @param fileName address of the target image to save.
   * @throws IOException if address is not valid.
   */
  void saveImage(String fileName) throws IOException;

  /**
   * Returns whether the model has an image loaded with.
   *
   * @return whether the model has an image loaded with.
   */
  boolean modelHasImage();

  /**
   * Get the image object from the model.
   *
   * @return the image object.
   */
  Image getImage();

  /**
   * Set the Image to the model.
   */
  void setImage(Image image);

  /**
   * Get the RGB color at the location of the current image.
   * PS. I believe the x and y is reversed on the image (which
   * is height x width). x on canvas shall indicate how far on
   * width and y on canvas shall indicate how far on height.
   *
   * @param x int position x.
   * @param y int position y.
   * @return an RGB color.
   */
  Color getColor(int y, int x);
}
