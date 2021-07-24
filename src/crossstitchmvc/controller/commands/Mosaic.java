package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.ImageMosaic3d;

/**
 * The mosaic command.
 */
public class Mosaic implements CrossStitchCommand {
  private int parameter;

  /**
   * Default constructor for mosaic command.
   *
   * @param parameter an integer parameter specifying how many piece.
   */
  public Mosaic(int parameter) {
    this.parameter = parameter;
  }

  @Override public void execute(CrossStitchModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    model.apply(new ImageMosaic3d(parameter));
  }
}
