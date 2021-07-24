package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;

/**
 * Interface that implement the command pattern.
 */
public interface CrossStitchCommand {
  /**
   * The double dispatch design for this command pattern.
   * Here we will invoke the given model and ask it to execute the command.
   * @param model  a cross stitch model.
   */
  void execute(CrossStitchModel model);
}
