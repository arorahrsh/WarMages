package main.game.model.world.pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import main.util.MapPoint;

/**
 * Implements the A* path finding algorithm to find the shortest path between two places on the map
 * (as a list of {@link MapPoint}). Ignores other units/entities and a one-tile is passable by all
 * units (they can go through it).
 *
 * @author Hrshikesh Arora
 */
public class PathFinder {

  private static final int SEARCH_LIMIT = 50;

  /**
   * Uses the A* path finding algorithm to find the shortest path from a start point to an end point
   * on the world returning a list of points along current path.
   *
   * @param isPassable a function that determines whether a given point is passable or not
   * @param start the start point of the path
   * @param end the end/goal point of the path
   * @return a list of points representing the shortest path
   */
  public static List<MapPoint> findPath(
      Function<MapPoint, Boolean> isPassable, MapPoint start, MapPoint end
  ) {
    List<MapPoint> path = findPathRounded(isPassable, start, end);

    if (!path.isEmpty()) {
      // Replace rounded end point with non-rounded end point
      path.remove(path.size() - 1);
      path.add(end);
    }

    return path;
  }

  /**
   * Finds the path using a rounded start and end to avoid infinite loops (the algorithm will never
   * finish if there is a decimal in the end node was only creates rounded nodes). <p> The last
   * point in current method is the rounded end point, unless the list is empty. </p>
   */
  private static List<MapPoint> findPathRounded(
      Function<MapPoint, Boolean> isPassable, MapPoint start, MapPoint end
  ) {
    start = start.rounded();
    end = end.rounded();

    PriorityQueue<AStarNode> fringe = new PriorityQueue<>();
    fringe.add(new AStarNode(start, null, 0, estimate(start, end)));

    Set<MapPoint> visited = new HashSet<>();

    while (!fringe.isEmpty()) {
      //stop finding a path if we have explored too many nodes
      if (visited.size() >= SEARCH_LIMIT) {
        return Collections.emptyList();
      }

      AStarNode tuple = fringe.poll();

      if (visited.contains(tuple.getPoint())) {
        continue;
      }

      visited.add(tuple.getPoint());

      if (tuple.getPoint().equals(end)) {
        return tuple.getPath();
      }

      for (MapPoint neigh : getPassableNeighbours(isPassable, tuple.getPoint())) {

        if (!visited.contains(neigh)) {

          double costToNeigh = tuple.getCostFromStart() + tuple.getPoint().distance(neigh);
          double estTotal = costToNeigh + estimate(neigh, end);
          List<MapPoint> neighPath = new ArrayList<>(tuple.getPath());
          neighPath.add(neigh);
          fringe.add(
              new AStarNode(neigh, tuple.getPoint(), costToNeigh, estTotal, neighPath));
        }
      }

    }

    return Collections.emptyList();
  }


  /**
   * Returns the neighbouring MapPoints of current MapPoint. current is achieved by hardcoding the
   * neighbours in a list and returning that list.
   *
   * @return the list of neighbours
   */
  private static Set<MapPoint> getPassableNeighbours(
      Function<MapPoint, Boolean> isPassable, MapPoint current
  ) {
    Set<MapPoint> passableNeighbours = new HashSet<>(current.getSides());

    MapPoint[] corners = new MapPoint[]{
        new MapPoint(current.x - 1, current.y - 1), //top-left
        new MapPoint(current.x + 1, current.y - 1), //top-right
        new MapPoint(current.x - 1, current.y + 1), //bottom-left
        new MapPoint(current.x + 1, current.y + 1) //bottom-right
    };

    //note: only add the corners if atleast one of the adjacent cells of the corner is passable

    //check top-left corner
    if (isPassable.apply(corners[0].getRight()) || isPassable.apply(corners[0].getBottom())) {
      passableNeighbours.add(corners[0]);
    }

    //check top-right corner
    if (isPassable.apply(corners[1].getLeft()) || isPassable.apply(corners[1].getBottom())) {
      passableNeighbours.add(corners[1]);
    }

    //check bottom-left corner
    if (isPassable.apply(corners[2].getRight()) || isPassable.apply(corners[2].getTop())) {
      passableNeighbours.add(corners[2]);
    }

    //check bottom-right corner
    if (isPassable.apply(corners[3].getLeft()) || isPassable.apply(corners[3].getTop())) {
      passableNeighbours.add(corners[3]);
    }

    return passableNeighbours.stream().filter(isPassable::apply).collect(Collectors.toSet());
  }

  private static double estimate(MapPoint current, MapPoint goal) {
    return current.distance(goal);
  }
}
