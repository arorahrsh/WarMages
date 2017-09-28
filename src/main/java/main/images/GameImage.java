package main.images;

import java.awt.image.BufferedImage;
import java.io.IOException;
import main.game.model.world.World;

/**
 * Reference all of the image files in the app here by adding a new enum value.
 *
 * @see GameImage#load(ImageProvider) To get a {@link BufferedImage}.
 */
public class GameImage {

  static final int FILL_SPACE = Integer.MAX_VALUE;

  /**
   * File path relative to the resources directory.
   */
  private final String filePath;

  /**
   * Fields for selecting a sub-image.
   */
  private final int startX;
  private final int startY;
  private final int width;
  private final int height;

  /**
   * Overflow space variables. E.g. Say this {@link GameImage} represents a tree, and this image is
   * 25x45 pixels (width x height). Also, this tree should only take up one grid cell in the
   * {@link World}. We don't want to vertically 'squish' the tree image into this cell, we want it
   * to draw extra space above the cell. If one cell is 25x25 pixels on the screen, then we can
   * set overflowTop = 20 to say that the top 20 pixels of this image should be overflowed outside
   * the designated drawing area.
   * <p>
   * The overflows refer to the image after sub-image processing.
   * </p>
   */
  private final int overflowTop;
  private final int overflowRight;
  private final int overflowBottom;
  private final int overflowLeft;

  /**
   * Don't use this directly. Use {@link GameImageBuilder}.
   */
  GameImage(
      String filePath,
      int startX,
      int startY,
      int width,
      int height,
      int overflowTop,
      int overflowRight,
      int overflowBottom,
      int overflowLeft
  ) {
    if (filePath.startsWith("/")) {
      throw new IllegalArgumentException();
    }

    this.filePath = filePath;

    this.startX = startX;
    this.startY = startY;
    this.width = width;
    this.height = height;

    this.overflowTop = overflowTop;
    this.overflowRight = overflowRight;
    this.overflowBottom = overflowBottom;
    this.overflowLeft = overflowLeft;
  }

  public String getFilePath() {
    return filePath;
  }

  public int getStartX() {
    return startX;
  }

  public int getStartY() {
    return startY;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  /**
   * Use this method to get an image.
   *
   * @throws IOException For file IO errors
   */
  public BufferedImage load(ImageProvider imageProvider) throws IOException {
    BufferedImage fromCache = imageProvider.getFromCache(this);
    if (fromCache != null) {
      return fromCache;
    }

    BufferedImage image = imageProvider.load(filePath);
    image = clipToBounds(image);

    imageProvider.storeInCache(this, image);
    return image;
  }

  private BufferedImage clipToBounds(BufferedImage image) {
    int w = width;
    int h = height;

    if (width == FILL_SPACE && height == FILL_SPACE) {
      return image;
    }

    if (w == FILL_SPACE) {
      w = image.getWidth() - startX;
    }
    if (h == FILL_SPACE) {
      h = image.getHeight() - startY;
    }

    return image.getSubimage(startX, startY, w, h);
  }

}
