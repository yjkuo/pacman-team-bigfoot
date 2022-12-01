package edu.rice.comp504.model.strategy.ghost;

import edu.rice.comp504.model.object.ACharacter;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.Pacman;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;
import java.util.ArrayList;

public class ChaseStrategy implements IUpdateGhostStrategy{
    private static IUpdateStrategy singleton;
    private int[][] layout;

    private ChaseStrategy(int[][] layout) {
        this.layout = layout;
    }


    /**
     * Make a walk strategy.  There is only one (singleton).
     * @return A walk strategy
     */
    public static IUpdateStrategy makeStrategy(int[][] layout) {
        if (singleton == null) {
            singleton = new ChaseStrategy(layout);
        }

        return singleton;
    }

    @Override
    public void updateState(ACharacter pacman, ACharacter context) {
        if (context != null) {
            if (pacman.getName().equals("pacman")) {
                Pacman pacmanTemp = (Pacman) pacman;
                if (context.getName().equals("ghost")) {
//                    Point ghostLoc = context.getLoc();
//                    Point pacmanLoc = pacman.getLoc();
//
//                    Point ghostCoords = new Point(ghostLoc.x / 20, ghostLoc.y / 20);
//                    Point pacmanCoords = new Point(pacmanLoc.x / 20, pacmanLoc.y / 20);
//
//                    DFS dfs = new DFS(pacmanCoords, layout);
//                    dfs.dfs(ghostCoords);
//                    context.setDirection(dfs.bestDirection);
//                    if (!context.detectCollisionWithWalls(dfs.bestDirection, layout)) {
//                        Point locAfterMoveInDirection = context.locationAfterMoveInDirection(dfs.bestDirection);
//
//                        context.setLoc(locAfterMoveInDirection);
//                    }
//                    return;



                    Point currentLoc = context.getLoc();
                    Point newLoc = currentLoc;
                    Point distance = new Point(pacman.getLoc().x - currentLoc.x, pacman.getLoc().y - currentLoc.y);

                    ArrayList<Integer> directions = new ArrayList<>();
                    int currentDir = context.getDirection() + 1;
                    int newDirection = 0;
                    int backupDirection = 0;
                    Point xDir = distToDirection(distance.x, true);
                    Point yDir = distToDirection(distance.y, false);

                    // Checks whether to approach Pacman from x or y-axis first. Then the other directions are prioritized according to which are more likely to lead to Pacman.
                    if (Math.abs(distance.x) >= Math.abs(distance.y)) {
                        directions.add(xDir.x);
                        directions.add(yDir.x);
                        directions.add(yDir.y);
                        directions.add(xDir.y);
                    } else {
                        directions.add(yDir.x);
                        directions.add(xDir.x);
                        directions.add(xDir.y);
                        directions.add(yDir.y);
                    }

                    boolean foundFirstDir = false;
                    for (int i = 0; i < directions.size(); i++) {
                        int dir = directions.get(i);
                        if (!context.detectCollisionWithWalls(dir - 1, layout)) {
                            if (foundFirstDir) {
                                backupDirection = dir;
                                break;
                            } else {
                                newDirection = dir;
                                foundFirstDir = true;
                            }
                        }
                    }

                    /**
                     * Apply new direction to update the state. Invalid direction of 0 if cannot move (should not be possible).
                     * If the new direction is opposite of the last direction, a backup direction exists, the choose
                     * his backupdirection instead of going backwards. This avoids Ghosts looping indefinitely or getting
                     * stuck in dead ends, since they cannot double back unless they are forced to or need to chase pacman
                     * in a different direction.
                     **/
                    if ((newDirection + 1) % 4 + 1 == currentDir && backupDirection != 0) {
                        newDirection = backupDirection;
                    }
                    context.setDirection(newDirection - 1);
                    switch (newDirection) {
                        case 1:
                            newLoc = new Point(currentLoc.x - context.getVel(), currentLoc.y);
                            break;
                        case 2:
                            newLoc = new Point(currentLoc.x, currentLoc.y - context.getVel());
                            break;
                        case 3:
                            newLoc = new Point(currentLoc.x + context.getVel(), currentLoc.y);
                            break;
                        case 4:
                            newLoc = new Point(currentLoc.x, currentLoc.y + context.getVel());
                            break;
                        default:
                            break;
                    }

                    // Update ghost state.
                    context.setLoc(newLoc);
                }
            }
        }
    }

    /**
     * Converts distance to direction. 1 - Left, 2 - Up, 3 - Right, 4 - Down.
     * @param distance Input distance.
     * @param isX If in X or Y direction.
     * @return Appropriate direction.
     */
    public Point distToDirection(int distance, boolean isX) {
        if (isX) {
            if (distance > 0) {
                return new Point(3, 1);
            } else {
                return new Point(1, 3);
            }
        } else {
            if (distance > 0) {
                return new Point(4, 2);
            } else {
                return new Point(2, 4);
            }
        }
    }

    @Override
    public String getName() {
        return "chase";
    }
}
