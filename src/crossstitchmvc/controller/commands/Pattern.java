package crossstitchmvc.controller.commands;

import crossstitchmvc.model.CrossStitchModel;
import crossstitchmvc.model.imageeffect.PatternGeneration;

import java.awt.Color;
import java.util.Map;

/**
 * The pattern generation command.
 */
public class Pattern implements CrossStitchCommand {
  private final Map<Color, String> dmcSymbolDictionary;
  private final Map<Color, String> dmcColorDictionary;
  private       String             fileSavePath;

  /**
   * Default Constructor.
   *
   * @param fileSavePath        the string path to which results will be saved.
   * @param dmcColorDictionary  a dictionary between dmc color to its name.
   * @param dmcSymbolDictionary a dictionary between dmc color to its symbol.
   */
  public Pattern(
      String fileSavePath, Map<Color, String> dmcColorDictionary,
      Map<Color, String> dmcSymbolDictionary) {
    this.fileSavePath = fileSavePath;
    this.dmcColorDictionary = dmcColorDictionary;
    this.dmcSymbolDictionary = dmcSymbolDictionary;
  }

  @Override public void execute(CrossStitchModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    if ((dmcColorDictionary == null) || (dmcSymbolDictionary == null)) {
      throw new IllegalArgumentException("The two dmc dictionarys can not be empty.");
    }
    model.apply(
        new PatternGeneration(fileSavePath, dmcColorDictionary, dmcSymbolDictionary));
  }
}
