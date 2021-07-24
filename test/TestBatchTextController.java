import crossstitchmvc.controller.BatchTextController;
import crossstitchmvc.model.imageeffect.ImageImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the batch text controller.
 */
public class TestBatchTextController {
  private StringBuilder       log;
  private BatchTextController controller;

  @Before public void setUp() throws Exception {
    log = new StringBuilder();
    String testFilePath = new File("").getAbsolutePath() + "/res/uni.jpeg";
    MockModel model = new MockModel(log);
    model.setImage(new ImageImpl(testFilePath));
    MockTextView view = new MockTextView("Mock Text view", log);
    controller = new BatchTextController(view);
  }

  @Test public void testSetUp() {
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockTextView: setFeatures called.\n")
                                    .toString(), log.toString());
  }

  @Test public void testOpenBatchFile() {
    controller.openBatchFile();
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockTextView: setFeatures called.\n")
                                    .append("MockTextView: openBatchFile called.\n")
                                    .toString(), log.toString());

  }

  @Test public void testSaveBatchFile() {
    controller.saveBatchFile();
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockTextView: setFeatures called.\n")
                                    .append("MockTextView: saveBatchFile called.\n")
                                    .toString(), log.toString());
  }

  @Test public void runBatchFile() {
    controller.runBatchFile();
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockTextView: setFeatures called.\n")
                                    .append("MockTextView: getTextArea called.\n")
                                    .toString(), log.toString());
  }

  @Test public void runFromBatchFile() {
    controller.runFromBatchFile();
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockTextView: setFeatures called.\n")
                                    .append("MockTextView: openBatchFile called.\n")
                                    .append("MockTextView: getTextArea called.\n")
                                    .toString(), log.toString());
  }

  @Test public void clearButton() {
    controller.clearButton();
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockTextView: setFeatures called.\n")
                                    .append("MockTextView: getTextArea called.\n")
                                    .append("MockTextView: getInputTextArea called.\n")
                                    .toString(), log.toString());
  }

  @Test public void enterTypedText() {
    controller.enterTypedText();
    assertEquals(new StringBuilder().append("MockModel: MockModel init.\n")
                                    .append("MockTextView init.\n")
                                    .append("MockTextView: setFeatures called.\n")
                                    .append("MockTextView: getInputTextArea called.\n")
                                    .append("MockTextView: getTextArea called.\n")
                                    .toString(), log.toString());
  }
}