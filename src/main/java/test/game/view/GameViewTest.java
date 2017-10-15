package test.game.view;

import static junit.framework.TestCase.assertEquals;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import main.common.GameController;
import main.game.view.GameView;
import main.common.GameModel;
import main.common.entity.HeroUnit;
import main.common.entity.Unit;
import main.common.entity.Entity;
import main.common.World;
import main.common.images.GameImage;
import main.common.images.GameImageResource;
import main.common.images.ImageProvider;
import main.common.util.Config;
import main.common.util.Event;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.common.util.MapRect;
import main.game.view.events.AbilityIconClick;
import main.game.view.events.ItemIconClick;
import main.game.view.events.KeyEvent;
import main.game.view.events.MouseClick;
import main.game.view.events.MouseDrag;
import main.game.view.events.UnitIconClick;
import org.junit.Before;
import org.junit.Test;


/**
 * tests for the GameView class.
 *
 * <p>checks position, initial load and image size.</p>
 *
 * @author Andrew McGhie
 */
public class GameViewTest {

  GameView gameView;
  GameModelMock gameModelMock;
  List<Entity> entityList;
  Config config;

  /**
   * sets up the class variables that are used in every test.
   */
  @Before
  public void setUp() {
    final ImageProvider imageProvider = new ImageProvider() {
      @Override
      protected BufferedImage load(String filePath) throws IOException {
        return new BufferedImage(1,1, BufferedImage.TYPE_4BYTE_ABGR);
      }

      @Override
      protected void storeInCache(GameImage gameImage, BufferedImage image) {

      }

      @Override
      protected BufferedImage getFromCache(GameImage gameImage) {
        return new BufferedImage(1,1, BufferedImage.TYPE_4BYTE_ABGR);
      }
    };
    final GameControllerMock gameController = new GameControllerMock();
    this.gameModelMock = new GameModelMock();
    this.config = new Config();
    this.config.setScreenDim(1000, 1000);
    this.config.setEntityViewTilePixelsX(50);
    this.config.setEntityViewTilePixelsY(50);
    this.gameView = new GameView(config,
        gameController, gameModelMock, imageProvider, new Event<>());

    EntityMock entity = new EntityMock(new MapPoint(0, 0), new MapSize(1, 1));
    entityList = new ArrayList<>();
    entityList.add(entity);
    this.gameModelMock.setEntities(entityList);
  }

  @Test
  public void testMovingViewBox() {
    MapRect originalView = this.gameView.getViewBox();
    this.gameView.updateMousePosition(0, 0);
    assertEquals(originalView.topLeft.x - config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().topLeft.x - config.getGameViewScrollSpeed());
    assertEquals(originalView.topLeft.y - config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().topLeft.y - config.getGameViewScrollSpeed());
    assertEquals(originalView.bottomRight.x - config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().bottomRight.x - config.getGameViewScrollSpeed());
    assertEquals(originalView.bottomRight.y - config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().bottomRight.y - config.getGameViewScrollSpeed());
  }

  @Test
  public void testPixToTile() {
    assertEquals(new MapPoint(1, 1), this.gameView.pixToTile(new MapPoint(0, 50)));
    assertEquals(new MapPoint(1, 0), this.gameView.pixToTile(new MapPoint(25, 25)));
    assertEquals(new MapPoint(0, 1), this.gameView.pixToTile(new MapPoint(-25, 25)));
    assertEquals(new MapPoint(7, 5), this.gameView.pixToTile(new MapPoint(50, 300)));
  }

  class GameModelMock implements GameModel {

    private List<Entity> entities = new ArrayList<>();

    @Override
    public Collection<Entity> getAllEntities() {
      return this.entities;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void setUnitSelection(Collection<Unit> unitSelection) {

    }

    @Override
    public Collection<Unit> getUnitSelection() {
      return Arrays.asList();
    }

    @Override
    public void addToUnitSelection(Unit unit) {

    }

    @Override
    public Collection<Unit> getAllUnits() {
      return null;
    }

    @Override
    public World getWorld() {
      return null;
    }

    @Override
    public HeroUnit getHeroUnit() {
      return null;
    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void resumeGame() {

    }

    @Override
    public void stopGame() {

    }

    void setEntities(List<Entity> entities) {
      this.entities = entities;
    }

  }

  class EntityMock implements Entity {


    private MapPoint position;
    private final MapSize size;

    EntityMock(MapPoint position, MapSize size) {
      this.position = position;
      this.size = size;
    }

    @Override
    public GameImage getImage() {
      return GameImageResource.TEST_IMAGE_1_1.getGameImage();
    }

    @Override
    public void tick(long timeSinceLastTick, World world) {

    }

    @Override
    public MapPoint getPreviousTopLeft() {
      return null;
    }

    @Override
    public boolean contains(MapPoint point) {
      return false;
    }

    @Override
    public MapPoint getTopLeft() {
      throw new AssertionError("This method is not used here");
    }

    @Override
    public MapPoint getCentre() {
      return position;
    }

    @Override
    public MapSize getSize() {
      return this.size;
    }

    @Override
    public MapRect getRect() {
      return null;
    }

    @Override
    public void translatePosition(double dx, double dy) {
      position = new MapPoint(position.x + dx, position.y + dy);
    }

    @Override
    public void slidePosition(double dx, double dy) {

    }
  }

  class GameControllerMock implements GameController {

    GameControllerMock() {

    }

    @Override
    public void onKeyPress(KeyEvent keyevent) {

    }

    @Override
    public void onMouseEvent(MouseClick mouseEvent) {

    }

    @Override
    public void onMouseDrag(MouseDrag mouseEvent) {

    }

    @Override
    public void onDbClick(MouseClick mouseClick) {

    }

    @Override
    public void onUnitIconClick(UnitIconClick clickEvent) {

    }

    @Override
    public void onAbilityIconClick(AbilityIconClick clickEvent) {

    }

    @Override
    public void onItemIconClick(ItemIconClick clickEvent) {

    }
  }
}
