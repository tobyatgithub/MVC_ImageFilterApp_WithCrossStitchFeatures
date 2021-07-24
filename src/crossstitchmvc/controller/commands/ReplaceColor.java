package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.ImageReplaceColor;

import java.awt.Color;

/**
 * The replace color command.
 */
public class ReplaceColor implements CrossStitchCommand {
  private final Color newColor;
  private final Color oldColor;

  /**
   * Constructor.
   *
   * @param oldColor the old rgb color to be replaced.
   * @param newColor the new rgb color to replace the old one.
   */
  public ReplaceColor(Color oldColor, Color newColor) {
    this.oldColor = oldColor;
    this.newColor = newColor;
  }

  @Override public void execute(CrossStitchModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    model.apply(new ImageReplaceColor(oldColor, newColor));
  }
}
