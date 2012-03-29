/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.asset.DesktopAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import view.GraphicalUnit;
import java.beans.PropertyChangeListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johnhu
 */
public class GameTest {
    
    public GameTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of acceleratePlayerUnit method, of class Game.
     */
    @Test
    public void testAcceleratePlayerUnit() {
        System.out.println("acceleratePlayerUnit");
        boolean accel = true;
        Game instance = new Game(new Battlefield(), 1, 2);
        instance.acceleratePlayerUnit(1, accel);
        assertTrue(instance.getPlayerPosition(1) != instance.getPlayerPosition(2));
    }

    /**
     * Test of getBattlefieldSize method, of class Game.
     */
    @Test
    public void testGetBattlefieldSize() {
        System.out.println("getBattlefieldSize");
        Game instance = new Game();
        Game instanceTwo = new Game(new Battlefield(200, 200), 1, 2);
        assertTrue(instance.getBattlefieldSize().getX() == 100
                && instanceTwo.getBattlefieldSize().getX() == 200);
        assertFalse(instanceTwo.getBattlefieldSize().getY() == 300);
    }

//    /**
//     * Test of startRound method, of class Game.
//     */
//    @Test
//    public void testStartRound() {
//        Game instance = new Game();
//        instance.startRound();
//        
//    }

    /**
     * Test of placeUnit method, of class Game.
     */
    @Test
    public void testPlaceUnit() {
        System.out.println("placeUnit");
        Game instance = new Game();
        Player player = new Player(1);
        instance.placeUnit(1, new Vector(0, 0));
        assertTrue(instance.getPlayerPosition(1).equals(new Vector(0, 0)));
    }

    /**
     * Test of endRound method, of class Game.
     */
//    @Test
//    public void testEndRound() {
//        System.out.println("endRound");
//        Game instance = new Game();
//        
//    }

    /**
     * Test of getNbrOfPlayers method, of class Game.
     */
    @Test
    public void testGetNbrOfPlayers() {
        System.out.println("getNbrOfPlayers");
        Game instance = new Game(new Battlefield(), 1, 2);
        assertTrue(instance.getNbrOfPlayers() == 2);
    }

    /**
     * Test of getPlayerPosition method, of class Game.
     */
    @Test
    public void testGetPlayerPosition() {
        System.out.println("getPlayerPosition");
        Game instance = new Game();
        assertTrue(instance.getPlayerPosition(1).equals(new Vector(1, 1)));
    }
}