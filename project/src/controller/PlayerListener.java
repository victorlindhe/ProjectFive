/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import model.Player;
import model.tools.Direction;

/**
 *
 * @author victorlindhe
 * @modified johnhu
 */
public class PlayerListener implements ActionListener {

    private Player player;
    private KeyPlayable layout;

    public PlayerListener(Player player, InputManager inpManager) {
        this.player = player;
        this.layout = KeyFactory.getPlayerKeys(player);
        inpManager.addMapping(layout.getUpMap(), new KeyTrigger(layout.getForwardKey()));
        inpManager.addMapping(layout.getLeftMap(), new KeyTrigger(layout.getLeftKey()));
        inpManager.addMapping(layout.getRightMap(), new KeyTrigger(layout.getRightKey()));
        inpManager.addMapping(layout.getLeftFireMap(), new KeyTrigger(layout.getLeftFireKey()));
        inpManager.addMapping(layout.getRightFireMap(), new KeyTrigger(layout.getRightFireKey()));

        inpManager.addListener(this, layout.getLeftMap());
        inpManager.addListener(this, layout.getRightMap());
        inpManager.addListener(this, layout.getUpMap());
        inpManager.addListener(this, layout.getLeftFireMap());
        inpManager.addListener(this, layout.getRightFireMap());
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(this.layout.getUpMap())) {
            this.player.accelerateUnit(isPressed);
        }
        
        if (name.equals(this.layout.getLeftMap()) ||
                name.equals(this.layout.getRightMap())) {
            Direction dir;
            if (isPressed) {
                dir = (name.equals(this.layout.getLeftMap()) ?
                        Direction.ANTICLOCKWISE : Direction.CLOCKWISE);
            } else {
                dir = (name.equals(this.layout.getLeftMap()) ?
                        Direction.CLOCKWISE : Direction.ANTICLOCKWISE);
            }
            this.player.steerUnit(dir);
        }
    }
}