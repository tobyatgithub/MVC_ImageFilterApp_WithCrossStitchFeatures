package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.SepiaToneFilter3d;

/**
 * The sepia tone command.
 */
public class Sepia implements CrossStitchCommand {
  @Override public void execute(CrossStitchModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    model.apply(new SepiaToneFilter3d());
  }
}
