package crossstitchmvc.model.imageeffect;

import crossstitchmvc.utility.Position2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter class of image mosaic effect.
 */
public class ImageMosaic3d implements Filter3d {
  private int seed;

  /**
   * Default constructor with no input.
   */
  public ImageMosaic3d() {
    this.seed = 0; // default seed
  }

  /**
   * Constructor.
   * @param seed  how many mosaic piece you want.
   */
  public ImageMosaic3d(int seed) {
    this.seed = seed;
  }

  @Override public int[][][] filter(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null.");
    }
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] original = image.getMatrix();
    int[][][] processed = new int[height][width][3];
    List<Position2D> centroids = new ArrayList<>();

    // generate centroids randomly
    for (int i = 0; i < seed; i++) {
      centroids.add(new Position2D(
          generateRandomIntWithinRange(0, height),
          generateRandomIntWithinRange(0, width)));
    }

    // for each pixel, we find the closest centroid
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Position2D currentPosition = new Position2D(i, j);
        int index = findClosestCentroid(currentPosition, centroids);

        // replace RGB color: new color set from centroid old color
        Position2D centroid = centroids.get(index);
        processed[i][j][0] = original[(int) centroid.getX()][(int) centroid.getY()][0];
        processed[i][j][1] = original[(int) centroid.getX()][(int) centroid.getY()][1];
        processed[i][j][2] = original[(int) centroid.getX()][(int) centroid.getY()][2];
      }
    }
    return processed;
  }

  /**
   * Given the current 2d position, and a list of centroid locations. Find the index of the
   * centroid that is the closest to the given current 2d position.
   * @param currentPosition  current 2d position
   * @param centroids  a list of centroid positions
   * @return  index of the closest centroid
   */
  private int findClosestCentroid(Position2D currentPosition, List<Position2D> centroids) {
    int indexOfCentroidWithMinDistance = 0;
    double minDistance = Double.MAX_VALUE;

    for (int k = 0; k < centroids.size(); k++) {
      double tmpDistance = centroids.get(k).getDistance(currentPosition);
      if (tmpDistance < minDistance) {
        minDistance = tmpDistance;
        indexOfCentroidWithMinDistance = k;
      }
    }

    return indexOfCentroidWithMinDistance;
  }

  /**
   * Uniform-randomly generate an integer within the given range in the form of [min, max)
   * i.e. including the min but excluding the max.
   * @param min  minimum of the range.
   * @param max  maximum of the range.
   * @return  a randomly generated integer.
   */
  private int generateRandomIntWithinRange(int min, int max) {
    return min + (int)(Math.random() * ((max - min)));
  }

  @Override public String toString() {
    return "Mosaic Filter";
  }
}
