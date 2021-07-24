package crossstitchmvc.controller;

import crossstitchmvc.model.CrossStitchModel;

import java.io.IOException;
import java.util.Scanner;

/**
 * The controller interface for the turtle program. The functions here have been
 * designed to give control to the controller, and the primary operation for the
 * controller to function (process a turtle command)
 */

public interface IController {
  /**
   * Start the program with String input.
   */
  void startTerminalViaString(CrossStitchModel model, String commands)
      throws IOException;

  /**
   * Start this command line controller from a path of a txt file full of
   * commands.
   *
   * @param model    the cross stitch model to use.
   * @param filePath the file path of the txt file with commands.
   * @throws IOException if the file can not be found.
   */
  void startTerminalViaFile(CrossStitchModel model, String filePath)
      throws IOException;

  /**
   * Start this command line controller with an interactive model inside
   * your terminal. This method allows you to type in the command line
   * by line.
   *
   * @param model the cross stitch model to use.
   * @throws IOException if you try to load an non exist image.
   */
  void startTerminalInteractive(CrossStitchModel model) throws IOException;

  /**
   * The heavy lifting function that takes in a scanner of commands (it could
   * be a scanner of a String, or a scanner of a File, etc.) and execute
   * these method one by one.
   *
   * @param model     the cross stitch model that all commands relies on.
   * @param innerScan the scanner which will scan the commands.
   * @throws IOException if command can not find a correlated file.
   */
  void scanAndExecute(CrossStitchModel model, Scanner innerScan) throws IOException;
}
