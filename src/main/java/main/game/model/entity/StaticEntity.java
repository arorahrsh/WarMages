package main.game.model.entity;

import java.util.List;
import main.game.model.world.World;
import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

public class StaticEntity extends DefaultEntity {

  private int layer = -1;
  private final double animationSpeed;

  private final List<GameImage> gameImageList;
  private final boolean loop;
  private double currentImage = 0;

  public StaticEntity(MapPoint topLeft,
                      MapSize size,
                      List<GameImage> gameImageList,
                      boolean loop) {
    this(topLeft, size, gameImageList, loop, 1);
  }

  public StaticEntity(MapPoint topLeft,
                      MapSize size,
                      List<GameImage> gameImageList,
                      boolean loop,
                      double animationSpeed) {
    super(topLeft, size);
    this.animationSpeed = animationSpeed;
    this.gameImageList = gameImageList;
    this.loop = loop;
  }

  /**
   * Should be set immediately if you want to set it.
   * If you dont the entity view will cache it and it wont have any effect
   */
  public void setLayer(int layer) {
    this.layer = layer;
  }

  @Override
  public GameImage getImage() {
    return this.gameImageList.get((int)currentImage);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    this.currentImage += this.animationSpeed;
    if (this.currentImage >= this.gameImageList.size()) {
      if (this.loop) {
        this.currentImage = 0;
      } else {
        world.removeStaticEntity(this);
      }
    }
  }

  @Override
  public boolean contains(MapPoint point) {
    return false;
  }

  @Override
  public int getLayer() {
    return this.layer;
  }
}
