package crossstitchmvc.utility;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Map;

/**
 * The class for the color choose panel that will be used to pick the dmc color
 * to replace.
 */
public class SystemColorChooserPanel extends AbstractColorChooserPanel
    implements ItemListener {
  private static final int NOT_FOUND = -1;

  JComboBox comboBox;
  String[]  labels;
  Color[]   colors;

  /**
   * Default Constructor.
   */
  public SystemColorChooserPanel() {
    Map<Color, String> dictionaryDMC = ImageUtilities
        .readDmcColors(new File("").getAbsolutePath() + "/newDmcColorWithUtf.csv");
    this.labels = dictionaryDMC.values().toArray(new String[0]);
    this.colors = dictionaryDMC.keySet().toArray(new Color[0]);
  }

  @Override public void itemStateChanged(ItemEvent itemEvent) {
    int state = itemEvent.getStateChange();
    if (state == ItemEvent.SELECTED) {
      int position = findColorLabel(itemEvent.getItem());
      if ((position != NOT_FOUND) && (position != labels.length - 1)) {
        ColorSelectionModel selectionModel = getColorSelectionModel();
        selectionModel.setSelectedColor(colors[position]);
      }
    }
  }

  private void setColor(Color newColor) {
    int position = findColorPosition(newColor);
    comboBox.setSelectedIndex(position);
  }

  private int findColorLabel(Object label) {
    String stringLabel = label.toString();
    int position = NOT_FOUND;
    for (int i = 0, n = labels.length; i < n; i++) {
      if (stringLabel.equals(labels[i])) {
        position = i;
        break;
      }
    }
    return position;
  }

  private int findColorPosition(Color color) {
    int position = colors.length - 1;

    int colorRGB = color.getRGB();
    for (int i = 0, n = colors.length; i < n; i++) {
      if ((colors[i] != null) && (colorRGB == colors[i].getRGB())) {
        position = i;
        break;
      }
    }
    return position;
  }

  @Override public void updateChooser() {
    Color color = getColorFromModel();
    setColor(color);
  }

  @Override protected void buildChooser() {
    comboBox = new JComboBox(labels);
    comboBox.addItemListener(this);
    add(comboBox);
  }

  @Override public String getDisplayName() {
    return "DMC Color";
  }

  @Override public Icon getSmallDisplayIcon() {
    return null;
  }

  @Override public Icon getLargeDisplayIcon() {
    return null;
  }
}
