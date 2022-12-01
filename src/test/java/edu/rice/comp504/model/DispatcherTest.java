package edu.rice.comp504.model;
import edu.rice.comp504.model.object.Ghost;
import edu.rice.comp504.model.object.Pacman;
import edu.rice.comp504.model.cmd.SwitchStrategyCmd;
import edu.rice.comp504.model.object.Aitem;

import edu.rice.comp504.model.strategy.ghost.ChaseStrategy;
import edu.rice.comp504.model.strategy.ghost.WalkStrategy;
import edu.rice.comp504.model.strategy.ghost.RetreatStrategy;
import junit.framework.TestCase;
import java.util.List;
import java.awt.*;

/**
 * Tested the DispatchAdapter communications with the controller and the model.
 */
public class DispatchAdapterTest extends TestCase{
    DispatcherAdapter dis = new DispatcherAdapter();
    GameStore context = dis.initializeGame();

    /**
     * Test game initialization.
     */
    public void testInit() {
        assertTrue(context.getPacman() != null);
        assertFalse(context.getGhosts().isEmpty());
        assertEquals(context.getCurrentScore(),0);
        assertEquals(context.getLives(),3);
    }

    /**
     * Test game parameters setting.
     */
    public void testSetGameParameter() {
        dis.setGameParameters(2,3,2,false);
        context = dis.getContext();
        assertFalse(context.init());
    }

    /**
     * Test object update.
     */
    public void testObjectUpdate() {
        Pacman man= context.getPacman();
        Point vel = man.getVel();
        Point loc = man.getLoc();
        Point newLoc = new Point(loc.x + vel.x,loc.y );
        String keyCode = "3";
        dis.updateCanvas(keyCode);
        assertEquals("Test pacman direction change",3,man.getDirection());
        assertEquals("Test pacman next direction change",3,man.getNextDirection());
        assertEquals("Test pacman location",context.getPacman().getLoc(),newLoc);
        keyCode = "2";
        dis.updateCanvas(keyCode);
        assertEquals("Test pacman direction should not change",3,man.getDirection());
        assertEquals("Test pacman next direction change",2,man.getNextDirection());
        dis.updateCanvas("3");

    }

    /**
     * Test walk ghost strategy.
     */
    public void testWalkStrategy() {
        List<Ghost> ghosts = context.getGhosts();
        for (Ghost ghost: ghosts) {
            if (ghost.getColor().equals("orange")) {
                Pacman pacman = context.getPacman();
                WalkStrategy ws = new WalkStrategy();
                assertEquals(ws.getName(), "walk");
                for (int i = 0; i < 300; i++) {
                    Point curLoc = ghost.getLoc();
                    ws.updateState(pacman, ghost);
                    Point nextLoc = ghost.getLoc();
                    assertNotSame(curLoc, nextLoc);
                }
            }
        }
    }

    /**
     * Test chase ghost strategy
     */

    public void testChaseStrategy() {
        List<Ghost> ghosts = context.getGhosts();
        for (Ghost ghost: ghosts) {
            System.out.println(ghost.getColor());
            if (ghost.getColor().equals("orange")) {
                Pacman pacman = context.getPacman();
                PatrolChaseStrategy pc = new PatrolChaseStrategy();
                assertEquals(pc.getName(), "chase");
                for (int i = 0; i < 300; i++) {
                    Point curLoc = ghost.getLoc();
                    pc.updateState(pacman, ghost);
                    Point nextLoc = ghost.getLoc();
                    assertNotSame(curLoc, nextLoc);
                }
            }
        }
    }

    /**
     * Test run away ghost strategy
     */

    public void testRetreatStrategy() {
        List<Ghost> ghosts = context.getGhosts();
        for (Ghost ghost: ghosts) {
            if (ghost.getColor().equals("orange")) {
                Pacman pacman = context.getPacman();
                RetreatStrategy rs = new RetreatStrategy();
                RetreatStrategy.makeStrategy();
                assertEquals(rs.getName(), "Retreat");
                for (int i = 0; i < 500; i++) {
                    Point curLoc = ghost.getLoc();
                    rs.updateState(pacman, ghost);
                    Point nextLoc = ghost.getLoc();
                    assertNotSame(curLoc, nextLoc);
                }
            }
        }
    }

    /**
     * Test eating dots
     */
    public void testEatDots() {
        Pacman man= context.getPacman();
        man.setLoc(new Point(56,84));
        int score =dis.getContext().getCurrentScore();
        int highestScore = dis.getContext().getHighestScore();
        dis.updateCanvas("2");
        assertEquals("Test eaten dots", score + 10, dis.getContext().getCurrentScore());
        man.setLoc(new Point(140,280));
        score =dis.getContext().getCurrentScore();
        dis.updateCanvas("2");
        assertEquals("Test eaten big dots", score + 50, dis.getContext().getCurrentScore());
        assertEquals("Test highest score", highestScore + 60, dis.getContext().getHighestScore());
    }

    /**
     * Test collide with ghosts
     */
    public void testCollideWithGhost() {
        DispatcherAdapter dis1 = new DispatcherAdapter();
        GameContext context1 = dis1.init();
        Pacman man = context1.getPacman();
        man.setLoc(new Point(700,336));
        dis1.updateCanvas("4");
        assertEquals("Test pacman is dead.",2,dis1.getContext().getPacman().getLeftLives());

    }

    /**
     * Test collide with flashing ghosts
     */
    public void testCollideWithFlashingGhost() {
        DispatcherAdapter dis1 = new DispatcherAdapter();
        GameContext context1 = dis1.init();
        Pacman man = context1.getPacman();
        man.setLoc(new Point(700,336));
        dis1.updateCanvas("2");
        Ghost g1 = context1.getGhosts().get(0);
        Ghost g2 = context1.getGhosts().get(1);
        assertTrue(g1.isFlashing());
        man.setLoc(new Point(700,336)));
        int score = dis1.getContext().getCurrentScore();
        dis1.updateCanvas("4");
        assertTrue(g1.isDead());
        assertEquals(score + 200, dis1.getContext().getCurrentScore());
        man.setLoc(new Point(700,336));
        for(int i = 0 ;i<10; i++) {
            dis1.updateCanvas("2");
        }
        assertFalse(g2.isFlashing());
    }



    /**
     * Test Fruit.
     */
    public void testFruit() {
        DispatcherAdapter dis1 = new DispatcherAdapter();
        GameContext context1 = dis1.init();
        GameLevel level1 = context1.getLevelInstance();
        int numFruit = context1.getNumberOfFruits();
        assertEquals("Test fruits loaded", numFruit, level1.getFruits().size());
        int numActivated = context1.getFruitsActivated();
        context1.activateNextFruit();
        assertEquals("Test fruit activate", numActivated + 1, context1.getFruitsActivated());
        Fruit f1 = level1.getNextFruit(0);
        assertTrue("Test correct fruit activates", f1.getActive());
        for(int i = 0 ;i<50; i++) {
            dis1.updateCanvas("2");
        }
        assertFalse("Test correct fruit deactivates", f1.getActive());
    }



}
