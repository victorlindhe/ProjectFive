package controller;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import model.Direction;

/**
 *
 * @author victorlindhe
 */
public class KeyBoardListener implements ActionListener, AnalogListener {
    private PlayerAdapter player;
    private KeyPlayable layout;
    
    public KeyBoardListener(PlayerAdapter player, InputManager inpManager) {
        this.player = player;
        this.layout = KeyBoardFactory.getPlayerKeys(player);
        inpManager.addMapping(layout.getUpMap(), new KeyTrigger(layout.getForwardKey()));
        inpManager.addMapping(layout.getLeftMap(), new KeyTrigger(layout.getLeftKey()));
        inpManager.addMapping(layout.getRightMap(), new KeyTrigger(layout.getRightKey()));
        
        inpManager.addListener(this, layout.getLeftMap());
        inpManager.addListener(this, layout.getRightMap());
        inpManager.addListener(this, layout.getUpMap());
    }
            

    
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals(this.layout.getUpMap())) {
                this.player.accelerateUnit(isPressed);
        }
    }

    public void onAnalog(String name, float value, float tpf) {   
        if (name.equals(this.layout.getLeftMap())) {
            this.player.steerUnit(Direction.ANTICLOCKWISE, tpf);
        }
        if (name.equals(this.layout.getRightMap())) {
            this.player.steerUnit(Direction.CLOCKWISE, tpf);
        }
    }
}
