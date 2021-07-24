import crossstitchmvc.controller.ImageController;
import crossstitchmvc.model.imageeffect.ImageImpl;
import crossstitchmvc.view.BatchTextView;
import crossstitchmvc.view.IView;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the ImageController.
 */
public class TestImageController {
  private StringBuilder   log;
  private MockModel       model;
  private BatchTextView   textView;
  private ImageController controller;
  private String          testFilePath;

  @Before public void setUp() throws Exception {
    log = new StringBuilder();
    testFilePath = new File("").getAbsolutePath() + "/res/uni.jpeg";
    model = new MockModel(log);
    model.setImage(new ImageImpl(testFilePath));
    IView view = new MockImageView("test view", log);
    textView = new MockTextView("Mock Text View", log);
    controller = new ImageController(model, view);
  }

  @Test public void testSetUp() {
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockView: MockView init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockView: getScrollerPanel called.\n")
                                    .append("MockView: getImageDisplay called.\n")
                                    .append("MockView: setFeatures called.\n")
                                    .toString(), log.toString());
    assertTrue(model.modelHasImage());
  }

  @Test public void testOpenImage() {
    try {
      controller.openImage(new File(testFilePath), testFilePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockView: MockView init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockView: getScrollerPanel called.\n")
                                    .append("MockView: getImageDisplay called.\n")
                                    .append("MockView: setFeatures called.\n")
                                    .append("MockModel: loadImage\n").toString(),
                 log.toString());

  }

  @Test public void testReloadImage() throws IOException {
    controller.openImage(new File(testFilePath), testFilePath);
    controller.reloadImage();
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockView: MockView init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockView: getScrollerPanel called.\n")
                                    .append("MockView: getImageDisplay called.\n")
                                    .append("MockView: setFeatures called.\n")
                                    .append("MockModel: loadImage\n")
                                    .append("MockModel: loadImage\n").toString(),
                 log.toString());
  }

  @Test public void testSaveImage() throws IOException {
    controller.saveImage(testFilePath);
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockView: MockView init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockView: getScrollerPanel called.\n")
                                    .append("MockView: getImageDisplay called.\n")
                                    .append("MockView: setFeatures called.\n")
                                    .append("MockModel: getImage\n").toString(),
                 log.toString());
  }

  @Test public void testReplaceColor() throws IOException {
    controller.openImage(new File(testFilePath), testFilePath);
    controller.replaceColor(10, 10);
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockView: MockView init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockView: getScrollerPanel called.\n")
                                    .append("MockView: getImageDisplay called.\n")
                                    .append("MockView: setFeatures called.\n")
                                    .append("MockModel: loadImage\n")
                                    .append("MockModel: getImage\n")
                                    .append("MockView: selectNewColor called.\n")
                                    .toString(), log.toString());
  }

  @Test public void testBlur() {
    controller.blurImage();
    assertTrue(log.toString().contains("Filter: Blur Filter was called."));
  }

  @Test public void testSharpen() {
    controller.sharpenImage();
    assertTrue(log.toString().contains("Filter: Sharpen Filter was called."));
  }

  @Test public void testGreyScale() {
    controller.greyscaleImage();
    assertTrue(log.toString().contains("Filter: GreyScale Filter was called."));
  }

  @Test public void testSepia() {
    controller.sepiaImage();
    assertTrue(log.toString().contains("Filter: Sepia Tone Filter was called."));
  }

  @Test public void testDithering() {
    controller.ditherImage();
    assertTrue(log.toString().contains("Filter: Reduce Density Filter was called."));
  }

  @Test public void testMosaic() {
    controller.mosaicImage("200");
    assertTrue(log.toString().contains("Filter: Mosaic Filter was called."));
  }

  @Test public void testPixelation() {
    controller.pixelateImage("200");
    assertTrue(log.toString().contains("Filter: Pixelation Filter was called."));
  }

  @Test public void testPatternGeneration() {
    controller.generatePattern();
    assertTrue(
        log.toString().contains("Filter: Pattern Generation Filter was called."));
  }

  @Test public void testSaveTextPattern() throws IOException {
    controller.openImage(new File(testFilePath), testFilePath);
    String textPattern = controller.saveTextPattern(false);
    assertTrue(textPattern.contains("TEST\n" + "LEGEND:"));
  }

  @Test public void testCreateAndShowTextGUI() {
    controller.createAndShowTextGUI(textView);
    assertEquals(log.toString(),
                 new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockView: MockView init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockView: getScrollerPanel called.\n")
                                    .append("MockView: getImageDisplay called.\n")
                                    .append("MockView: setFeatures called.\n")
                                    .append("MockTextView: setFeatures called.\n")
                                    .toString());
  }
}