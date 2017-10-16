package main.game.model.entity.unit.state;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import main.common.entity.Direction;
import main.common.entity.Unit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitImagesComponent;
import main.common.World;
import main.common.images.GameImage;
import main.common.images.UnitSpriteSheet.Sequence;

/**
 * An interface for the states of a unit.
 * @author paladogabr
 */
public abstract class UnitState implements Serializable {

  private static final long serialVersionUID = 1L;

  protected final DefaultUnit unit;
  protected final UnitImagesComponent imagesComponent;

  protected UnitState requestedNextState;

  public UnitState(Sequence sequence, DefaultUnit unit) {
    this.unit = unit;
    this.imagesComponent = new UnitImagesComponent(sequence, unit);
  }

  /**
   * Updates the state.
   *
   * @param timeSinceLastTick time passed since last tick call.
   */
  public void tick(Long timeSinceLastTick, World world) {
    imagesComponent.tick(timeSinceLastTick);
  }

  /**
   * Returns the current image of the AbstractUnitState.
   *
   * @return GameImage image of current state.
   */
  public GameImage getImage() {
    return imagesComponent.getImage();
  }

  /**
   * Sets the "next" state to be the requested state, if there isn't already a requested state.
   *
   * @param nextState the requested state.
   */
  public void requestState(UnitState nextState) {
    this.requestedNextState = requireNonNull(nextState);
  }

  /**
   * Gets the direction the unit should face, defaults to face away from the last position.
   */
  public Direction getCurrentDirection() {
    return Direction.between(unit.getPreviousTopLeft(), unit.getTopLeft());
  }

  /**
   * Gets the next state or this if no change was requested.
   */
  public abstract UnitState updateState();

  public void onTakeDamage(double amount, World world, Unit attacker) {
    // do nothing
  }
}
