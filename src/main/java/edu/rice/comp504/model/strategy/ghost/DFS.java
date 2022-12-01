package edu.rice.comp504.model.strategy.ghost;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DFS {
    ArrayList<Integer> path;
    Map<Point, Integer> visited;
    private int[][] layout;
    Point target;
    int bestDirection;

    /**
     * Constructor call.
     */
    public DFS(Point target, int[][] layout) {
        path = new ArrayList<>();
        visited = new HashMap<Point, Integer>();
        this.layout = layout;
        this.target = target;
        bestDirection = -1;
    }

    private boolean isValidLocation(Point p) {
        int x = p.x;
        int y = p.y;
        return x > 0 && y > 0 && x < layout.length && y < layout.length && layout[y][x] != 1 && layout[y][x] != 2;
    }

    /**
     * Perform DFS.
     */
    public int dfs(Point currentCoords) {
        for (Map.Entry<Point, Integer> entry : visited.entrySet()) {
            Point key = entry.getKey();
            int value = entry.getValue();
            if (key.equals(currentCoords)) {
                return value;
            }
        }

        if (currentCoords.equals(target)) {
            visited.put(currentCoords, 0);
            return 0;
        }

        int x = currentCoords.x;
        int y = currentCoords.y;

        Point[] neighbors = new Point[]{
            new Point(x - 1, y),
            new Point(x, y - 1),
            new Point(x + 1, y),
            new Point(x, y + 1)
        };

        int dist = -1;
        visited.put(currentCoords, dist);

        for (int i = 0; i < neighbors.length; i++) {
            if (isValidLocation(neighbors[i])) {
                int ret = dfs(new Point(neighbors[i]));
                if (ret != -1) {
                    if (dist == -1 || ret < dist) {
                        dist = ret;
                        this.bestDirection = i;
                    }
                }
            }
        }

        if (dist > -1) {
            dist++;
        }

        visited.put(currentCoords, dist);
        return dist;
    }
}
