package test.game.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static test.game.model.world.WorldTestUtils.createEmptyLevel;
import static test.game.model.world.WorldTestUtils.createHeroUnit;
import static test.game.model.world.WorldTestUtils.createLevels;
import static test.game.model.world.WorldTestUtils.createWorld;

import java.util.Arrays;
import java.util.List;
import main.game.model.GameModel;
import main.game.model.entity.MovableEntity;
import main.game.model.world.World;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Test;

public class MovableEntityTest {

  private MovableEntity getMovableEntity() {
    return new MockMovable(new MapPoint(1, 1), new MapSize(1, 1));
  }

  private class MockMovable extends MovableEntity {

    /**
     * Constructor takes the position of the entity and the size.
     *
     * @param position = position of Entity
     * @param size = size of Entity
     */
    public MockMovable(MapPoint position, MapSize size) {
      super(position, size, 0.1);
    }
  }

  private List<MapPoint> getPathDown() {
    return Arrays.asList(
        new MapPoint(1, 2),
        new MapPoint(1, 3),
        new MapPoint(1, 4),
        new MapPoint(1, 5),
        new MapPoint(1, 6),
        new MapPoint(1, 7),
        new MapPoint(1, 8),
        new MapPoint(1, 9)
    );
  }

  private List<MapPoint> getPathAcross() {
    return Arrays.asList(
        new MapPoint(2, 1),
        new MapPoint(3, 1),
        new MapPoint(4, 1),
        new MapPoint(5, 1),
        new MapPoint(6, 1),
        new MapPoint(7, 1),
        new MapPoint(8, 1),
        new MapPoint(9, 1
        )
    );
  }

  private List<MapPoint> getPathDiagonal() {
    return Arrays.asList(
        new MapPoint(2, 2),
        new MapPoint(3, 3),
        new MapPoint(4, 4),
        new MapPoint(5, 5),
        new MapPoint(6, 6),
        new MapPoint(7, 7),
        new MapPoint(8, 8),
        new MapPoint(9, 9)
    );
  }

  @Test
  public void testMovingEntity_oneSpaceHorizontally() {
    MovableEntity me = getMovableEntity();
    List<MapPoint> path = getPathAcross();
    me.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createHeroUnit());
    // with the given speed of 0.01, with each tick (time since last being 100)
    // the movable entity should move 100*0.01 = 1 map distance
    for (int i = 0; i < 10; i++) {
      assertEquals(new MapPoint(1D + 0.1 * i, 1).x, me.getTopLeft().x, 0.001);
      assertEquals(new MapPoint(1D + 0.1 * i, 1).y, me.getTopLeft().y, 0.001);
      me.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_twoSpacesHorizontally() {
    MovableEntity me = getMovableEntity();
    List<MapPoint> path = getPathAcross();
    me.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createHeroUnit());
    // with the given speed of 0.01, with each tick (time since last being 200)
    // the movable entity should move 200*0.01 = 2 map distances
    for (int i = 0; i < 20; i ++) {
      assertEquals(new MapPoint(1D + 0.1 * i, 1).x, me.getTopLeft().x, 0.001);
      assertEquals(new MapPoint(1D + 0.1 * i, 1).y, me.getTopLeft().y, 0.001);
      me.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_oneSpaceVertically() {
    MovableEntity me = getMovableEntity();
    List<MapPoint> path = getPathDown();
    me.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createHeroUnit());
    // with the given speed of 0.01, with each tick (time since last being 100)
    // the movable entity should move 100*0.01 = 1 map distance
    for (int i = 0; i < 10; i++) {
      assertEquals(new MapPoint(1, 1D + 0.1 * i).x, me.getTopLeft().x, 0.001);
      assertEquals(new MapPoint(1, 1D + 0.1 * i).y, me.getTopLeft().y, 0.001);
      me.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_twoSpacesVertically() {
    MovableEntity me = getMovableEntity();
    List<MapPoint> path = getPathDown();
    me.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createHeroUnit());
    // with the given speed of 0.01, with each tick (time since last being 200)
    // the movable entity should move 200*0.01 = 2 map distances
    for (int i = 0; i < 20; i ++) {
      assertEquals(new MapPoint(1, 1D + 0.1 * i).x, me.getTopLeft().x, 0.001);
      assertEquals(new MapPoint(1, 1D + 0.1 * i).y, me.getTopLeft().y, 0.001);
      me.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_oneSpaceDiagonally() {
    MovableEntity me = getMovableEntity();
    List<MapPoint> path = getPathDiagonal();
    me.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createHeroUnit());
    // with the given speed of 0.01, with each tick (time since last being 200)
    // the movable entity should move 100*0.01 = 1 map distance which is approx 1 diagonal
    for (int i = 0; i < 10; i++) {
      assertEquals(1D + 0.1 / Math.sqrt(2) * i, me.getTopLeft().x, 0.001);
      assertEquals(1D + 0.1 / Math.sqrt(2) * i, me.getTopLeft().y, 0.001);
      me.tick(GameModel.DELAY, world);
    }
  }
}
