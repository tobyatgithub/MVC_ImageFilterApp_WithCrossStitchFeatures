package crossstitchmvc;

import crossstitchmvc.controller.ImageController;
import crossstitchmvc.controller.CommandController;
import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.CrossStitchModelImpl;
import crossstitchmvc.view.ImageView;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A simple enter point of this program, where we hand a model to the controller.
 */
public class NewDriver {
  /**
   * The enter point for this cross stitch app.
   * @param args terminal line input.
   * @throws IOException  if the path for -script mode is invalid.
   */
  public static void main(String[] args) throws IOException {
    if (args.length > 0) {
      if (args[0].equalsIgnoreCase("-interactive")) {
        CrossStitchModel model = new CrossStitchModelImpl();
        ImageView view = new ImageView("Cross Stitch App");
        new ImageController(model, view);
      } else if (args[0].equalsIgnoreCase("-script")) {
        new CommandController(new InputStreamReader(System.in), System.out)
            .startTerminalViaFile(new CrossStitchModelImpl(), args[1]);
      } else if (args[0].equalsIgnoreCase("-command")) {
        new CommandController(new InputStreamReader(System.in), System.out)
            .startTerminalInteractive(new CrossStitchModelImpl());
      }
    } else {
      System.out.println("ERROR: Command can not be recognized.");
      System.exit(0);
    }
  }
}
