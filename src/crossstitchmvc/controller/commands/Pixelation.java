package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.ImagePixelation;

/**
 * The pixelation command.
 */
public class Pixelation implements CrossStitchCommand {
  private int parameter;

  /**
   * Constructor.
   *
   * @param parameter integer specify how many super pixels one row shall contain.
   */
  public Pixelation(int parameter) {
    this.parameter = parameter;
  }

  @Override public void execute(CrossStitchModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    model.apply(new ImagePixelation(parameter));
  }
}
