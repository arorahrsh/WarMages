package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

/**
 * Item extends {@link MapEntity}. An item is something that can be picked up and used by HeroUnit.
 * For now, all items have an {@link Ability} (through decoration).
 *
 * <p>
 * Functionality should be delegated to the {@link Ability}.
 * </p>
 */
public class Item extends MapEntity implements Usable {

  private static final long serialVersionUID = 1L;
  private final Ability ability;

  /**
   * Constructor takes the coordinates of the item.
   * @param onMapImage What this image looks like when it's on the map.
   */
  public Item(MapPoint coord, Ability ability, GameImage onMapImage) {
    super(coord);
    this.ability = ability;
    this.image = onMapImage;
  }

  @Override
  public void setImage(GameImage image) {
    throw new UnsupportedOperationException("Cannot change the image of an item");
  }

  @Override
  public void tick(long timeSinceLastTick) {
    ability.tick(timeSinceLastTick);
  }

  @Override
  public GameImage getIconImage() {
    return ability.getIconImage();
  }

  @Override
  public String getDescription() {
    return ability.getDescription();
  }

  @Override
  public double getCoolDownProgress() {
    return ability.getCoolDownProgress();
  }

  @Override
  public void _startCoolDown() {
    ability._startCoolDown();
  }

  @Override
  public Effect _createEffectForUnit(Unit unit) {
    return ability._createEffectForUnit(unit);
  }
}
