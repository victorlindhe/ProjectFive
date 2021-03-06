package model.visual;

import physics.IPhysicalModel;
import math.Vector;
import observable.IObservable;

/**
 * Interface with methods to control a moveable unit.
 */
public interface IMoveable extends IPhysicalModel, IObservable {
    public void setPosition(Vector pos);
    public void setDirection(Vector dir);
    public Vector getPosition();
    public Vector getDirection();
    public void update(float tpf);
    public void announceShow();
    public void announceHide();
    public void announceRemoval();
}
