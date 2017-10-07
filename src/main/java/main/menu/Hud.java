package main.menu;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import main.Main;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Unit;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Item;
import main.game.view.GameView;
import main.images.DefaultImageProvider;
import main.menu.controller.HudController;
import main.menu.generators.ScriptFileGenerator;
import main.renderer.Renderer;

/**
 * The definitions of the file paths to the html file for the Heads Up Display.
 *
 * @author Andrew McGhie
 */
public class Hud extends Menu {

  private final GameModel gameModel;
  private final Collection<Unit> selectedUnits = new ArrayList<>();
  private HeroUnit hero;

  public Hud(Main main, MainMenu mainMenu, GameView gameView, Renderer renderer, GameModel gameModel) {
    super(main);
    this.gameModel = gameModel;
    this.menuController = new HudController(main, mainMenu, gameView, renderer);
  }

  @Override
  public String getHtml() {
    return this.fileToString(MenuFileResources.HUD_HTML.getPath());
  }

  @Override
  public String getStyleSheetLocation() {
    return new File(MenuFileResources.HUD_CSS.getPath()).toURI().toString();
  }

  @Override
  public String[] getScripts() {
    return new String[]{
      new ScriptFileGenerator()
          .setFile(MenuFileResources.JQUERY_JS.getPath())
          .getScript(),
      new ScriptFileGenerator()
          .setFile(MenuFileResources.HUD_JS.getPath())
          .getScript()
    };
  }

  public void updateIcons() {
    final Collection<Unit> removeUnits = new HashSet<>();
    this.selectedUnits.stream()
        .filter(unit -> !this.gameModel.getUnitSelection().contains(unit))
        .forEach(removeUnits::add);
    this.selectedUnits.removeAll(removeUnits);
    if (removeUnits.size() > 0) {
      this.main.callJsFunction("clearUnits");
      this.main.callJsFunction("clearAbilties");
      this.main.callJsFunction("clearItems");
      this.selectedUnits.forEach(this::addUnitIcon);
      if (hero != null) {
        hero.getAbilities().forEach(this::addAbilityIcon);
        hero.getItemInventory().forEach(this::addItemIcon);
      }
    }

    this.gameModel.getUnitSelection().stream()
        .filter(unit -> !this.selectedUnits.contains(unit))
        .forEach(unit -> {
          if (unit instanceof HeroUnit) {
            hero = ((HeroUnit) unit);
            hero.getAbilities().forEach(this::addAbilityIcon);
            hero.getItemInventory().forEach(this::addItemIcon);
          }
          addUnitIcon(unit);
          this.selectedUnits.add(unit);
        });
  }

  private void addUnitIcon(Unit unit){
    try {
      this.addIcon("addUnitIcon", unit.getImage().load(new DefaultImageProvider()), unit);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addItemIcon(Item item) {
    try {
      this.addIcon("addItemIcon", item.getIconImage().load(new DefaultImageProvider()), item);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addAbilityIcon(Ability ability) {
    try {
      this.addIcon("addAbilityIcon", ability.getIconImage().load(new DefaultImageProvider()), ability);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addIcon(String method, BufferedImage image, Object entity) {
    String entityIcon = this.formatImageForHtml(image);
    this.main.callJsFunction(method, entityIcon, entity);
  }

  /**
   * Assumes that the image is png formatted.
   */
  private String formatImageForHtml(RenderedImage image) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "png", baos);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    byte[] bytes = baos.toByteArray();
    String imageMimeType = "image/png";
    String dataUri =
        "data:" + imageMimeType + ";base64," + DatatypeConverter.printBase64Binary(bytes);
    return dataUri;
  }


}
