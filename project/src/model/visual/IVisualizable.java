/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.tools.Vector;

/**
 *  An interface that will make sure an object can be visualized.
 *  Something that can be visualized must have a size and a position
 * @author johnnes wikner
 */
interface IVisualizable {
    
    public Vector getPosition();
    
    public float getSize();
    
}