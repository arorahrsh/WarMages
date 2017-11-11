package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.common.entity.HeroUnit;
import main.common.entity.Unit;
import main.common.entity.usable.Item;
import main.common.images.UnitSpriteSheet.Sequence;
import main.common.util.MapPoint;

/**
 * @author Andrew McGhie
 */
public class TargetItem extends Target implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final double PICK_UP_MAX_DISTANCE = 1;

  private final MapPoint location;
  private final Item item;

  public TargetItem(Unit unit, Item item) {
    super(unit);
    this.item = item;
    this.location = item.getCentre();
  }

  public TargetItem(HeroUnit heroUnit, Item item) {
    super(heroUnit);
    this.item = item;
    this.location = item.getCentre();
    this.setNextState(new PickingUp(heroUnit, Sequence.DEAD, this));
  }

  public Item getItem() {
    return this.item;
  }

  @Override
  MapPoint getDestination() {
    return location;
  }

  @Override
  boolean isStillValid() {
    return true;
  }

  @Override
  public boolean hasArrived() {
    return unit.getCentre().distanceTo(location) < PICK_UP_MAX_DISTANCE;
  }

  @Override
  public double acceptableDistanceFromEnd() {
    return PICK_UP_MAX_DISTANCE;
  }
}
