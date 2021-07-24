package crossstitchmvc.model.imageeffect;

/**
 * Filter class of grey scale effect. Notice it is just a special case of
 * color transform.
 */
public class GreyScaleFilter3d extends ColorTransform3d {
  /**
   * Constructor for grey scale filter. Since it is just a special case of color
   * transform. We only need to specify the parameters.
   */
  public GreyScaleFilter3d() {
    super(0.2126d, 0.7152d, 0.0722d,
        0.2126d, 0.7152d, 0.0722d,
        0.2126d, 0.7152d, 0.0722d);
  }

  @Override public String toString() {
    return "GreyScale Filter";
  }
}
