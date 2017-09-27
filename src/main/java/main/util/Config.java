package main.util;

/**
 * Holds the config and context of the game.
 * @author Andrew McGhie
 */
public class Config {

  private static final int CONTEXT_SCREEN_SIZE_NOT_SET = -1;

  private int gameModelDelay = 50;

  private double gameViewScrollSpeed = 1;

  private int entityRenderableTilePixelsX = 50;
  private int entityRenderableTilePixelsY = 50;

  private int contextScreenWidth = CONTEXT_SCREEN_SIZE_NOT_SET;
  private int contextScreenHeight = CONTEXT_SCREEN_SIZE_NOT_SET;

  public int getGameModelDelay() {
    return gameModelDelay;
  }

  public double getGameViewScrollSpeed() {
    return gameViewScrollSpeed;
  }

  public int getEntityRenderableTilePixelsX() {
    return entityRenderableTilePixelsX;
  }

  public int getEntityRenderableTilePixelsY() {
    return entityRenderableTilePixelsY;
  }

  /**
   * Gets the width of the screen.
   * @return screenWidth
   * @throws Error if screenWidth not set
   */
  public int getContextScreenWidth() {
    if (contextScreenWidth == CONTEXT_SCREEN_SIZE_NOT_SET) {
      throw new IllegalStateException("You have not set the screen height yet");
    }
    return contextScreenWidth;
  }

  /**
   * Gets the height of the screen.
   * @return screenHeight
   * @throws Error if screenHeight not set
   */
  public int getContextScreenHeight() {
    if (contextScreenHeight == CONTEXT_SCREEN_SIZE_NOT_SET) {
      throw new IllegalStateException("You have not set the screen height yet");
    }
    return contextScreenHeight;
  }

  public void setScreenDim(int width, int height) {
    this.contextScreenWidth = width;
    this.contextScreenHeight = height;
  }
}
