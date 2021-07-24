package crossstitchmvc.view;

import crossstitchmvc.controller.BatchTextFeatures;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

/**
 * The view for text batch window where user can play with different
 * command line items and batch files with command line items.
 */
public class BatchTextView extends JFrame implements BView {
  private final JButton      clickMeButton;
  private final JMenuItem    openBatchFile;
  private final JTextField   inputTextArea;
  private final JTextArea    textArea;
  private final JMenuItem    saveBatchFile;
  private final JMenuItem    runBatchFile;
  private final JMenuItem    runFromBatchFile;
  private final JButton      clearButton;
  private       JFileChooser fileChooser;

  /**
   * Constructor for this batch text view.
   *
   * @param title title of the window.
   * @throws HeadlessException must provide a title.
   */
  public BatchTextView(String title) throws HeadlessException {
    super(title);
    this.setSize(600, 400);
    this.setLocation(200, 200);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLayout(new BorderLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1.0;
    constraints.weighty = 1.0;

    // set up the grid panel
    JPanel textPanel = new JPanel(new GridBagLayout());
    inputTextArea = new JTextField(20);

    textArea = new JTextArea(30, 30);
    textArea.setEditable(false);
    textArea.setLineWrap(true);

    JScrollPane scrollPane = new JScrollPane(textArea);
    textPanel.add(inputTextArea, constraints);
    textPanel.add(scrollPane, constraints);

    this.add(textPanel, BorderLayout.CENTER);

    JPanel bottom = new JPanel(new FlowLayout());
    clearButton = new JButton("Clear Script!");
    clickMeButton = new JButton("Run Script!");
    bottom.add(clearButton);
    bottom.add(clickMeButton);
    this.add(bottom, BorderLayout.SOUTH);

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    openBatchFile = new JMenuItem("Load File");
    openBatchFile.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
    fileMenu.add(openBatchFile);

    saveBatchFile = new JMenuItem("Save..");
    saveBatchFile.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
    fileMenu.add(saveBatchFile);

    runBatchFile = new JMenuItem("Run..");
    runBatchFile.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
    fileMenu.add(runBatchFile);

    runFromBatchFile = new JMenuItem("Run From..");
    runFromBatchFile.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
    fileMenu.add(runFromBatchFile);
    menuBar.add(fileMenu);

    this.setJMenuBar(menuBar);
    setVisible(true);

    this.fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("text", "txt"));
  }

  @Override public void setFeatures(BatchTextFeatures f) {
    clearButton.addActionListener(l -> f.clearButton());
    clickMeButton.addActionListener(l -> f.runBatchFile());

    openBatchFile.addActionListener(l -> f.openBatchFile());
    inputTextArea.addActionListener(l -> f.enterTypedText());
    saveBatchFile.addActionListener(l -> f.saveBatchFile());
    runBatchFile.addActionListener(l -> f.runBatchFile());
    runFromBatchFile.addActionListener(l -> f.runFromBatchFile());
  }

  @Override public JTextField getInputTextArea() {
    return inputTextArea;
  }

  @Override public JTextArea getTextArea() {
    return textArea;
  }

  @Override public String openBatchFile() {
    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().toString();
    } else {
      return null;
    }
  }

  @Override public String saveBatchFile() {
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().toString();
    }
    return null;
  }
}
