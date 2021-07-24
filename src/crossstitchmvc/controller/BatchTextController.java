package crossstitchmvc.controller;

import crossstitchmvc.model.CrossStitchModelImpl;
import crossstitchmvc.view.BatchTextView;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The controller for the text batch interface.
 * In here we handles all the logic after the user opens
 * the TextBatchPanel.
 */
public class BatchTextController implements BatchTextFeatures {
  private BatchTextView view;

  /**
   * Constructor of the textBatchController.
   *
   * @param view a TextBatchView.
   */
  public BatchTextController(BatchTextView view) {
    this.view = view;
    this.view.setFeatures(this);
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("text", "txt"));
  }

  @Override public void openBatchFile() {
    String filePath = view.openBatchFile();
    if (filePath != null) {
      try {
        String content = Files.readString(Path.of(filePath));
        view.getTextArea().setText(content);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override public void saveBatchFile() {
    String filePath = view.saveBatchFile();
    if (filePath != null) {
      String content = view.getTextArea().getText();
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content);
        writer.close();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "ERROR: SaveBatchFile Can not save.");
        e.printStackTrace();
      }
    }
  }

  @Override public void runBatchFile() {
    String commands = view.getTextArea().getText();
    if (commands.length() > 0) {
      StringBuilder feedback = new StringBuilder();
      try {
        new CommandController(new InputStreamReader(System.in), feedback)
            .startTerminalViaString(new CrossStitchModelImpl(), commands);
        JOptionPane.showMessageDialog(null, feedback.toString());
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "ERROR: runBatchFile failed.");
        e.printStackTrace();
      }
    }
  }

  @Override public void runFromBatchFile() {
    openBatchFile();
    runBatchFile();
  }

  @Override public void clearButton() {
    view.getTextArea().setText("");
    view.getInputTextArea().setText("");
    JOptionPane.showMessageDialog(null, "Script Cleared!");
  }

  @Override public void enterTypedText() {
    JTextField inputTextField = view.getInputTextArea();
    String text = inputTextField.getText();
    JTextArea textArea = view.getTextArea();
    textArea.append(text + "\n");
    inputTextField.selectAll();

    //    System.out.println("Enter: " + text);
    //    System.out.println(textArea.getDocument().getLength());
    //Make sure the new text is visible, even if there
    //was a selection in the text area.
    textArea.setCaretPosition(textArea.getDocument().getLength());
  }

}
