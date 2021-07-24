package crossstitchmvc.controller;

import crossstitchmvc.controller.commands.Blur;
import crossstitchmvc.controller.commands.Dithering;
import crossstitchmvc.controller.commands.Grayscale;
import crossstitchmvc.controller.commands.Mosaic;
import crossstitchmvc.controller.commands.Pattern;
import crossstitchmvc.controller.commands.Pixelation;
import crossstitchmvc.controller.commands.Sepia;
import crossstitchmvc.controller.commands.Sharpen;
import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.utility.ImageUtilities;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * A controller for the command line related functions.
 * This controller works with a Readable and Appendable object.
 * It has been designed to accept a sequence of multiple inputs from
 * a Readable object (for example a txt file). See README for How to use.
 */
public class CommandController implements IController {
  private final Readable           in;
  private final Appendable         out;
  private       Map<Color, String> dmcColorDictionary;
  private       Map<Color, String> dmcSymbolDictionary;

  /**
   * Basic constructor for the controller.
   *
   * @param in  the input stream
   * @param out the output stream
   */
  public CommandController(Readable in, Appendable out) {
    if (in == null) {
      throw new IllegalArgumentException("Readable input can not be null.");
    }
    if (out == null) {
      throw new IllegalArgumentException("Appendable output can not be null.");
    }

    this.in = in;
    this.out = out;
    this.dmcColorDictionary = ImageUtilities
        .readDmcColors(new File("").getAbsolutePath() + "/newDmcColorWithUtf.csv");
    this.dmcSymbolDictionary = ImageUtilities
        .readDmcSymbols(new File("").getAbsolutePath() + "/newDmcColorWithUtf.csv");
  }

  @Override public void startTerminalViaString(CrossStitchModel model, String commands)
      throws IOException {
    Objects.requireNonNull(model);
    Scanner innerScan = new Scanner(commands);
    scanAndExecute(model, innerScan);
  }

  @Override public void startTerminalViaFile(CrossStitchModel model, String filePath)
      throws IOException {
    Objects.requireNonNull(model);
    Objects.requireNonNull(filePath);
    File file = getFileFromFileName(filePath);
    assert file != null;
    Scanner innerScan = new Scanner(file);
    scanAndExecute(model, innerScan);
  }

  @Override public void startTerminalInteractive(CrossStitchModel model)
      throws IOException {
    Objects.requireNonNull(model);
    out.append("Hello. Welcome to the image processor.\n");
    out.append("Here are some commands you can use to process images:\n");
    out.append("Please start by providing a batch file with allowed commands,\n");

    Scanner scan = new Scanner(this.in);
    scanAndExecute(model, scan);
  }

  @Override public void scanAndExecute(CrossStitchModel model, Scanner innerScan)
      throws IOException {
    String innerCommand;

    // start processing the commands inside the batch file.
    while (innerScan.hasNext()) {
      innerCommand = innerScan.next();
      out.append("innerCommand = ").append(innerCommand);
      if (innerCommand != null) {
        out.append("[command received]: ").append(innerCommand).append("\n");
        if (innerCommand.equalsIgnoreCase("q") ||
            innerCommand.equalsIgnoreCase("quit")) {
          return;
        } else if (innerCommand.equalsIgnoreCase("load")) {
          String imageName = innerScan.next();
          out.append(String.format("Loading image [%s]...\n", imageName));
          try {
            model.loadImage(imageName);
          } catch (FileNotFoundException e) {
            e.printStackTrace();
            out.append("Can not find the image.\n")
               .append("Please check image address and try again: ").append(imageName)
               .append("\n");
            break;
          }
          out.append("Loading image from: ").append(imageName).append(" success.\n");
        } else if (innerCommand.equalsIgnoreCase("blur")) {
          if (!model.modelHasImage()) {
            out.append("Please load image first.\n");
            break;
          }
          new Blur().execute(model);
        } else if (innerCommand.equalsIgnoreCase("sharpen")) {
          if (!model.modelHasImage()) {
            out.append("Please load image first.\n");
            break;
          }
          new Sharpen().execute(model);
        } else if (innerCommand.equalsIgnoreCase("grayscale") ||
            innerCommand.equalsIgnoreCase("greyscale")) {
          if (!model.modelHasImage()) {
            out.append("Please load image first.\n");
            break;
          }
          new Grayscale().execute(model);
        } else if (innerCommand.equalsIgnoreCase("sepia")) {
          if (!model.modelHasImage()) {
            out.append("Please load image first.\n");
            break;
          }
          new Sepia().execute(model);
        } else if (innerCommand.equalsIgnoreCase("dithering")) {
          if (!model.modelHasImage()) {
            out.append("Please load image first.\n");
            break;
          }
          new Dithering().execute(model);
        } else if (innerCommand.equalsIgnoreCase("mosaic")) {
          if (!model.modelHasImage()) {
            out.append("Please load image first.\n");
            break;
          }
          try {
            int parameter = innerScan.nextInt();
            new Mosaic(parameter).execute(model);
          } catch (InputMismatchException e) {
            out.append("ERROR: Mosaic shall take an integer input. " +
                           "Please modify and try again.\n");
            break;
          }
        } else if (innerCommand.equalsIgnoreCase("pixel")) {
          if (!model.modelHasImage()) {
            out.append("Please load image first.\n");
            break;
          }
          try {
            int parameter = innerScan.nextInt();
            new Pixelation(parameter).execute(model);
          } catch (InputMismatchException e) {
            out.append("ERROR: Pixelation shall take an integer input. " +
                           "Please modify and try again.\n");
            break;
          }
        } else if (innerCommand.equalsIgnoreCase("pattern")) {
          if (!model.modelHasImage()) {
            out.append("Please load image first.\n");
            break;
          }
          try {
            String savePath = innerScan.next();
            new Pattern(savePath, dmcColorDictionary, dmcSymbolDictionary)
                .execute(model);
          } catch (NoSuchElementException e) {
            out.append("ERROR: Pattern shall take a String input. " +
                           "Please modify and try again.\n");
            break;
          }
        } else if (innerCommand.equalsIgnoreCase("save")) {
          if (!model.modelHasImage()) {
            out.append("Please load image first.\n");
            break;
          }
          try {
            String savePath = innerScan.next();
            // System.out.println("Save path = " + savePath);
            model.saveImage(savePath);
            out.append("Image [").append(savePath).append("] save success!\n\n");
          } catch (NoSuchElementException e) {
            out.append("ERROR: Save shall take a String input. " +
                           "Please modify and try again.\n");
            break;
          }
        } else if (innerCommand.equalsIgnoreCase("q") ||
            innerCommand.equalsIgnoreCase("quit")) {
          out.append("Good Bye.\n");
          return;
        } else {
          out.append(String.format("ERROR: this command is not recognizable: %s\n",
                                   innerCommand));
          break;
        }
      }
    }
    out.append("All commands finished!\n");
    out.append("Now you can read another batch file if you want.\n");
  }

  /**
   * Helper function to get the file Object from a path.
   *
   * @param filepath string path of the file to grab.
   * @return a File object.
   * @throws IOException if the filepath is invalid.
   */
  private File getFileFromFileName(String filepath) throws IOException {
    if (filepath == null) {
      out.append("Can not find the batch file you provided.\n");
      out.append("Please check file address and try again.\n");
      return null;
    }

    if (!(filepath.contains("/res/"))) {
      filepath = new File("").getAbsolutePath() + "/res/" + filepath;
    }
    File file = new File(filepath);
    if (!(file.exists() && file.canRead())) {
      out.append("Can not find file. Non-exist or read access restricted.\n");
      out.append("Please check file address and try again.\n");
      return null;
    }
    return file;
  }

}
