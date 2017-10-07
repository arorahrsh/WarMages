package main.game.model.entity;

import java.util.List;
import main.game.model.world.World;
import main.util.MapPoint;
import main.util.MapSize;

public abstract class MovableEntity extends Entity {

  private static final long serialVersionUID = 1L;

  protected List<MapPoint> path;
  protected int speed;

  /**
   * Constructor takes the position of the entity and the size.
   *
   * @param position = position of Entity
   * @param size = size of Entity
   */
  public MovableEntity(MapPoint position, MapSize size) {
    super(position, size);
  }

  /**
   * Sets the path to be followed by the unit to the given path.
   */
  public void setPath(List<MapPoint> path) {
    this.path = path;
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    long distToBeTravelled = speed * timeSinceLastTick; //todo finalize
    int leeway = 5; //todo finalize
    //update position
    if (path != null && !path.isEmpty()) {
      System.out.println("Moved");
      for (MapPoint mp : path) {
        double distFromCurrent = Math
            .sqrt((Math.pow(mp.x - position.x, 2) + Math.pow(mp.y - position.y, 2)));
        if (distFromCurrent < distToBeTravelled + leeway
            && distFromCurrent > distToBeTravelled - leeway) {
          position = mp;
          return;
        }
      }
    }
  }
}
