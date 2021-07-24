package crossstitchmvc.model.imageeffect;

/**
 * Interface for the filtering method, here we assume we are filtering
 * on a 3 dimensional image (i.e. RGB image when we first design this.)
 * @author toby
 */
public interface Filter3d {
  /**
   * Filter the given image object and return the filtered image matrix.
   * @param image  an Image object to be filtered.
   * @return  a 3d array containing altered digit information.
   */
  int[][][] filter(Image image);

}
