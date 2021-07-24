import crossstitchmvc.controller.CommandController;
import crossstitchmvc.model.imageeffect.ImageImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

/**
 * Test class for the Command Controller.
 */
public class TestCommandController {
  private StringBuilder log;
  private MockModel     model;

  @Before public void setUp() throws Exception {
    log = new StringBuilder();
    String testFilePath = new File("").getAbsolutePath() + "/res/uni.jpeg";
    model = new MockModel(log);
    model.setImage(new ImageImpl(testFilePath));
  }

  @Test public void testGoWithNonExistingBatchFileToRead() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model, "read xxx");
    assertTrue(
        log.toString().contains("ERROR: this command is not recognizable: read"));
  }

  @Test public void testNoSuchMethod() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model, " 4352$#@ops");
    assertTrue(log.toString()
                  .contains("ERROR: this command is not recognizable: 4352$#@ops"));
  }

  @Test public void testBlur() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model,
                                      new StringBuilder().append("load uni.jpeg\n")
                                                         .append("blur\n").append(
                                          "save blur-uni.jpeg\n").toString());
    assertTrue(log.toString().contains("Loading image from: uni.jpeg success."));
    assertTrue(log.toString().contains("[command received]: blur"));
    assertTrue(log.toString().contains("Image [blur-uni.jpeg] save success!"));
  }

  @Test public void testSharpen() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model,
                                      new StringBuilder().append("load uni.jpeg\n")
                                                         .append("sharpen\n").append(
                                          "save sharpen-uni.jpeg\n").toString());
    assertTrue(log.toString().contains("[command received]: sharpen"));
    assertTrue(log.toString().contains("Loading image from: uni.jpeg success."));
    assertTrue(log.toString().contains("Image [sharpen-uni.jpeg] save success!"));
  }

  @Test public void testGrey() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model,
                                      new StringBuilder().append("load uni.jpeg\n")
                                                         .append("grayscale\n").append(
                                          "save grayscale-uni.jpeg\n").toString());
    assertTrue(log.toString().contains("[command received]: grayscale"));
    assertTrue(log.toString().contains("Image [grayscale-uni.jpeg] save success!"));
  }

  @Test public void testSepia() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model, new StringBuilder()
        .append("load seabass-small.png\n").append("sepia\n")
        .append("save old-seabass.png").toString());
    assertTrue(log.toString().contains("[command received]: sepia"));
    assertTrue(
        log.toString().contains("Loading image from: seabass-small.png success."));
    assertTrue(log.toString().contains("Image [old-seabass.png] save success!"));
  }

  @Test public void testDithering() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model, new StringBuilder()
        .append("load seabass-small.png\n").append("dithering\n")
        .append("save dithering-seabass.png").toString());
    assertTrue(log.toString().contains("[command received]: dithering"));
    assertTrue(
        log.toString().contains("Loading image from: seabass-small.png success."));
  }

  @Test public void testMosaic() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model, new StringBuilder()
        .append("load seabass-small.png\n").append("mosaic 30\n")
        .append("save mosaic-seabass.png").toString());
    assertTrue(log.toString().contains("[command received]: mosaic"));
    assertTrue(
        log.toString().contains("Loading image from: seabass-small.png success."));
  }

  @Test public void testMosaicWrongParameter() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model, new StringBuilder()
        .append("load seabass-small.png\n").append("mosaic aaa\n")
        .append("save mosaic-seabass.png").toString());
    assertTrue(log.toString().contains(
        "ERROR: Mosaic shall take an integer input. Please modify and try again."));
  }

  @Test public void testPixelation() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model,
                                      new StringBuilder().append("load goat.png\n")
                                                         .append("pixel 50\n")
                                                         .append("save pixel-goat.png")
                                                         .toString());
    System.out.println(log);
    assertTrue(log.toString().contains("[command received]: pixel"));
    assertTrue(log.toString().contains("Image [pixel-goat.png] save success!"));
  }

  @Test public void testPixelationWrongParameter() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model,
                                      new StringBuilder().append("load goat.png\n")
                                                         .append("pixel abc\n").append(
                                          "save pixel-goat.png\n").toString());
    assertTrue(log.toString().contains(
        "ERROR: Pixelation shall take an integer input. Please modify and try again" +
            "."));
  }

  @Test public void testPattern() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model,
                                      new StringBuilder().append("load goat.png\n")
                                                         .append("pixel 50\n").append(
                                          "save pixel-goat.png\n").append(
                                          "pattern pattern-goat.png").toString());
    assertTrue(log.toString().contains("[command received]: pixel"));
    assertTrue(log.toString().contains("Image [pixel-goat.png] save success!"));
    assertTrue(log.toString().contains("[command received]: pattern"));
  }

  @Test public void testPatternWrongPath() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model,
                                      new StringBuilder().append("load goat.png\n")
                                                         .append("pixel 50\n").append(
                                          "save pixel-goat.png\n").append("pattern\n")
                                                         .toString());
    assertTrue(log.toString().contains(
        "ERROR: Pattern shall take a String input. Please modify and try again."));
  }

  @Test public void testEmptyLineBlur() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model,
                                      new StringBuilder().append("load uni.jpeg\n")
                                                         .append("\n").append("blur\n")
                                                         .append("\n").append(
                                          "save spaceblur-uni.jpeg\n").toString());
    assertTrue(log.toString().contains("[command received]: blur"));
    assertTrue(log.toString().contains("Image [spaceblur-uni.jpeg] save success!"));
  }

  @Test public void testWrongCommand() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.startTerminalViaString(model, "learn more faster");
    assertTrue(
        log.toString().contains("ERROR: this command is not recognizable: learn"));
  }

  @Test public void testScanAndExecute() throws IOException {
    CommandController controller = new CommandController(new StringReader(""), log);
    controller.scanAndExecute(model, new Scanner(
        new StringBuilder().append("load uni.jpeg\n").append("blur\n")
                           .append("save blur-uni.jpeg\n").toString()));
    assertTrue(log.toString().contains("Loading image from: uni.jpeg success."));
    assertTrue(log.toString().contains("[command received]: blur"));
    assertTrue(log.toString().contains("Image [blur-uni.jpeg] save success!"));
  }
}