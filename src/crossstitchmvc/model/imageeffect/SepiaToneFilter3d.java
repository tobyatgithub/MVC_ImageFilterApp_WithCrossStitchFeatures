package crossstitchmvc.model.imageeffect;

/**
 * Filter class of sepia tone effect. Notice it is just a special case of
 * color transform.
 */
public class SepiaToneFilter3d extends ColorTransform3d {
  /**
   * Constructor for sepia tone filter. Since it is just a special case of color
   * transform. We only need to specify the parameters.
   */
  public SepiaToneFilter3d() {
    super(0.393d, 0.769d, 0.189d,
        0.349d, 0.686d, 0.168d,
        0.272d, 0.534d, 0.131d);
  }

  @Override public String toString() {
    return "Sepia Tone Filter";
  }
}

