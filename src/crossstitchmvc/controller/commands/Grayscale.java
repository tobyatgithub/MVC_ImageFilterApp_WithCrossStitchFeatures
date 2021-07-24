package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.GreyScaleFilter3d;

/**
 * The grayscale command.
 */
public class Grayscale implements CrossStitchCommand {
  @Override public void execute(CrossStitchModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    model.apply(new GreyScaleFilter3d());
  }
}
