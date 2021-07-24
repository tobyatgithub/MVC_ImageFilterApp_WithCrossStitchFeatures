package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.ImageSharpenFilter3d;

/**
 * The image sharpener command.
 */
public class Sharpen implements CrossStitchCommand {
  @Override public void execute(CrossStitchModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    model.apply(new ImageSharpenFilter3d());
  }
}
