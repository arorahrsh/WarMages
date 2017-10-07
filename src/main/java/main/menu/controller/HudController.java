package main.menu.controller;

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import main.Main;
import main.game.model.entity.Unit;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.Entity;
import main.game.model.entity.usable.Item;
import main.game.view.GameView;
import main.game.view.events.MouseClick;
import main.menu.MainMenu;
import main.renderer.Renderer;
import main.util.Config;
import main.util.MapPoint;

/**
 * Controls the hud when in game. Receives the click events when in game.
 *
 * @author Andrew McGhie
 */
public class HudController extends MenuController {

  final Main main;
  final MainMenu mainMenu;
  final GameView gameView;
  final Renderer renderer;

  public HudController(Main main, MainMenu mainMenu, GameView gameView, Renderer renderer) {
    this.main = main;
    this.mainMenu = mainMenu;
    this.gameView = gameView;
    this.renderer = renderer;
  }

  /**
   * Triggers event for when the icon of an entity is clicked.
   */
  public void unitIconBtn(Unit unit) {
    try {
      System.out.println(unit.getHealth());
      // TODO event trigger here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when the icon of an ability is clicked.
   */
  public void abilityIconBtn(Ability ability) {
    try {
      // TODO event trigger here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when the icon of an item is clicked.
   */
  public void itemIconBtn(Item item) {
    try {
      // TODO event trigger here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when Game View is clicked.
   */
  public void onLeftClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown) {
    try {
      gameView.onLeftClick(x, y, wasShiftDown, wasCtrlDown);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when Game View is clicked.
   */
  public void onRightClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown) {
    try {
      gameView.onRightClick(x, y, wasShiftDown, wasCtrlDown);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onMouseMove(MouseEvent event) {
    gameView.updateMousePosition((int)event.getX(), (int)event.getY());
  }

  @Override
  public void onKeyDown(KeyEvent event) {
    gameView.onKeyDown(event.getCharacter().charAt(0),
        event.isShiftDown(),
        event.isControlDown());
  }

  /**
   * Handles when the ui enters a state that the game should pause.
   */
  public void pause() {
    try {
      renderer.pause();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles when the ui enters a state that the game should unpause.
   */
  public void resume() {
    try {
      renderer.resume();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles when the quit button was pressed.
   */
  public void quitBtn() {
    try {
      // TODO handle exiting the game
      this.main.loadMenu(this.mainMenu);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * handles when the save button was pressed.
   */
  public void saveBtn() {
    try {
      // TODO handle going to the save menu and saving the game
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
