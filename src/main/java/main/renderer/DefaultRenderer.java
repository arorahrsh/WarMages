package main.renderer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import main.game.model.entity.Renderable;
import main.game.view.BackGroundView;
import main.game.view.EntityView;
import main.game.view.GameView;
import main.images.DefaultImageProvider;
import main.images.ImageProvider;
import main.util.Config;
import main.util.Looper;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Implementation of Renderer API.
 *
 * @author Eric Diputado
 */
public class DefaultRenderer implements Renderer {

  private final GameView gameView;
  private final ImageView imageView;
  private final Config config;
  private final Looper looper;
  private final ImageProvider imageProvider = new DefaultImageProvider();

  /**
   * Creates a Renderer and the rendering loop.
   *
   * @param gameView the object the contains the GUI.
   * @param imageView the javaFX object that actually draws the GUI.
   */
  public DefaultRenderer(
      GameView gameView, ImageView imageView, Config config, Looper looper
  ) {
    this.gameView = gameView;
    this.imageView = imageView;
    this.config = config;
    this.looper = looper;
  }

  @Override
  public void drawAll(long currentTime, GameView gameView, ImageView imageView) {
    Objects.requireNonNull(gameView);
    Objects.requireNonNull(imageView);

    BufferedImage image = new BufferedImage(config.getContextScreenWidth(),
        config.getContextScreenHeight(),
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();
    RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
    );
    g.setRenderingHints(rh);
    BackGroundView background = gameView.getBackGroundView();
    g.drawImage(background.getImage(),
        (int)background.getImagePosition(0).x,
        (int)background.getImagePosition(0).y,
        (int)background.getImageSize().width,
        (int)background.getImageSize().height,
        null);
    for (Renderable r : gameView.getRenderables(currentTime)) {
      MapPoint position = r.getImagePosition(currentTime);
      MapSize size = r.getImageSize();
      try {
        r.getImage().drawOnto(g,
            this.imageProvider,
            (int)(position.x - gameView.getViewBox().topLeft.x),
            (int)(position.y - gameView.getViewBox().topLeft.y),
            (int)size.width,
            (int)size.height);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    for (Renderable renderable : gameView.getRenderables(currentTime)) {
      MapPoint position = renderable.getImagePosition(currentTime);
      MapSize size = renderable.getImageSize();
      renderable.drawDecorations(g,
          (int)(position.x - gameView.getViewBox().topLeft.x),
          (int)(position.y - gameView.getViewBox().topLeft.y),
          (int)size.width,
          (int)size.height);
    }
    g.drawImage(gameView.getFogOfWarView().getImage(), 0, 0, null);

    if (config.isDebugMode()) {
      for (int i = 0; i < 100; i++) {
        MapPoint x1 = EntityView.tileToPix(new MapPoint(i, 0), config);
        MapPoint x2 = EntityView.tileToPix(new MapPoint(i, 100), config);
        MapPoint y1 = EntityView.tileToPix(new MapPoint(0, i), config);
        MapPoint y2 = EntityView.tileToPix(new MapPoint(100, i), config);
        g.drawLine(
            (int) (x1.x - gameView.getViewBox().topLeft.x),
            (int) (x1.y - gameView.getViewBox().topLeft.y),
            (int) (x2.x - gameView.getViewBox().topLeft.x),
            (int) (x2.y - gameView.getViewBox().topLeft.y)
        );
        g.drawLine(
            (int) (y1.x - gameView.getViewBox().topLeft.x),
            (int) (y1.y - gameView.getViewBox().topLeft.y),
            (int) (y2.x - gameView.getViewBox().topLeft.x),
            (int) (y2.y - gameView.getViewBox().topLeft.y)
        );
        for (int j = 0; j < 100; j++) {
          MapPoint mp = EntityView.tileToPix(new MapPoint(i, j), config);
          g.drawString("{" + i + ", " + j + "}",
              ((int) (mp.x + gameView.getViewBox().x())),
              ((int) (mp.y + gameView.getViewBox().y())));
        }
      }

    }
    imageView.setImage(SwingFXUtils.toFXImage(image, null));

  }

  @Override
  public void pause() throws InterruptedException {
    looper.setPaused(true);
  }

  @Override
  public void resume() {
    looper.setPaused(false);
  }

  @Override
  public void start() {
    looper.start(
        () -> drawAll(System.currentTimeMillis(), gameView, imageView)
    );
  }

  @Override
  public void stop() {
    looper.stop();
  }
}
