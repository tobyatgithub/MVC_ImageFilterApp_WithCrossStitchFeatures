package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.ImageBlurFilter3d;

/**
 * The blur command.
 */
public class Blur implements CrossStitchCommand {
  @Override public void execute(CrossStitchModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    for (int i = 0; i < 10; i++) {
      model.apply(new ImageBlurFilter3d());
    }
  }
}
