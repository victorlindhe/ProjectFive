/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import java.util.HashMap;
import java.util.Map;
import model.IGame;
import model.Player;

/**
 *
 * @author atamon
 */
public class JoinPlayerListener implements ActionListener {

    private final Map<Integer, KeyTrigger> joinKeys = new HashMap<Integer, KeyTrigger>();
    private final IGame game;
    private final InputManager inpManager;

    public JoinPlayerListener(IGame game, InputManager inpManager) {
        this.game = game;
        this.inpManager = inpManager;

        // Set up join keys for all supported players and add them to manager
        joinKeys.put(0, new KeyTrigger(PlayerZeroKeys.KEY_JOIN));
        joinKeys.put(1, new KeyTrigger(PlayerOneKeys.KEY_JOIN));
        joinKeys.put(2, new KeyTrigger(PlayerTwoKeys.KEY_JOIN));
        joinKeys.put(3, new KeyTrigger(PlayerThreeKeys.KEY_JOIN));

        this.setKeys(inpManager);

    }

    private void setKeys(InputManager inpManager) {
        for (Integer id : joinKeys.keySet()) {
            KeyTrigger mapTrigger = joinKeys.get(id);
            inpManager.addMapping("" + id, mapTrigger);
            inpManager.addListener(this, "" + id);
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed && !game.roundStarted()) {
            int id = -1;
            try {
                id = Integer.parseInt(name);
            } catch (NumberFormatException e) {
                System.out.println("Illegal playerID registered in GlobalKeyListener."
                        + " Not a Number!");
            }

            this.game.createPlayer(id);
            Player player = this.game.getPlayer(id);
            new PlayerListener(player, inpManager);

            // Use name since we know it was correct id
            inpManager.deleteTrigger(name, joinKeys.get(id));
        }
    }
}
