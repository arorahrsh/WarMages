package main.common.util;

public class Events {

  private Events() {

  }

  public static class MainGameTick extends Event<Long> {

  }

  public static class GameCompletion extends Event<Void> {

  }
}
