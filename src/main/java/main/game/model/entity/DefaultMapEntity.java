package main.game.model.entity;

import main.common.entity.MapEntity;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.world.World;

public class DefaultMapEntity extends DefaultEntity implements MapEntity {

  private static final long serialVersionUID = 1L;
  private GameImage image;

  public DefaultMapEntity(MapPoint coord, GameImage image) {
    this(coord, new MapSize(1, 1), image);
  }

  public DefaultMapEntity(MapPoint coord, MapSize size, GameImage image) {
    super(coord, size);
    this.image = image;
  }

  @Override
  public void translatePosition(double dx, double dy) {
    throw new UnsupportedOperationException();
  }

  @Override
  public GameImage getImage() {
    return image;
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //do nothing
  }

  @Override
  public boolean contains(MapPoint point) {
    return getRect().contains(point);
  }

  @Override
  public boolean isPassable() {
    return false;
  }
}