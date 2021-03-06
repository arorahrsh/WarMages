package main.game.controller;

import main.game.view.GameView;
import main.menu.controller.events.AbilityIconClick;
import main.menu.controller.events.ItemIconClick;
import main.menu.controller.events.KeyEvent;
import main.menu.controller.events.MouseClick;
import main.menu.controller.events.MouseDrag;
import main.menu.controller.events.UnitIconClick;

/**
 * Allows the user to control the game. Listens to user actions on the view {@link GameView}, e.g.
 * mouse and keyboard input, and calls methods on the model to respond to the user input.
 *
 * @author Hrshikesh Arora
 */
public interface GameController {

  /**
   * Calls the appropriate method in the model depending on the user's key press.
   *
   * @param keyevent -- the KeyEvent object for the current key press
   */
  public void onKeyPress(KeyEvent keyevent);

  /**
   * Responds to user's mouse click actions by calling the appropriate method on the model. The
   * following scenarios are taken care of:
   *
   * <ul>
   * <li>LEFT click => Deselect all previously selected units and select only the clicked unit</li>
   * <li>LEFT click + SHIFT => Add the clicked unit to the previously selected units</li>
   * <li>LEFT click + CTRL => If the clicked unit is already selected, deselect it. Otherwise,
   *   select that unit.</li>
   * <li>RIGHT click => All selected units move to the clicked location</li>
   * <li>RIGHT click on an enemy => All selected units will attack the enemy unit</li>
   * </ul>
   *
   * @param mouseEvent -- the MouseClick object for the current mouse click
   */
  public void onMouseEvent(MouseClick mouseEvent);

  /**
   * Responds to user's mouse drag actions by calling the appropriate method on the model. The
   * following scenarios are taken care of:
   *
   * <ul>
   * <li>LEFT drag => Deselect all previously selected units and select only the units under the
   *   drag rectangle</li>
   * <li>LEFT drag + SHIFT => Add all units in the drag rectangle to the selected units</li>
   * <li>LEFT drag + CTRL => Toggle all units in the drag rectangle. If the unit was selected,
   *   deselect it. Otherwise, select it.</li>
   * </ul>
   * @param mouseEvent -- the MouseClick object for the current mouse click
   */
  public void onMouseDrag(MouseDrag mouseEvent);

  void onDbClick(MouseClick mouseClick);

  void onUnitIconClick(UnitIconClick clickEvent);

  void onAbilityIconClick(AbilityIconClick clickEvent);

  void onItemIconClick(ItemIconClick clickEvent);
}
