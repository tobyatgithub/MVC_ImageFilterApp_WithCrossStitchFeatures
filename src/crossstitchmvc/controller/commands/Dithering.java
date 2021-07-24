package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.ReduceDensityFilter3d;

/**
 * The dithering command.
 */
public class Dithering implements CrossStitchCommand {
  @Override public void execute(CrossStitchModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    model.apply(new ReduceDensityFilter3d(2, 2, 2));
  }
}
