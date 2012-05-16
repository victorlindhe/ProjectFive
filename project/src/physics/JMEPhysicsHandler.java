package physics;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import math.Vector;
import model.visual.Unit;
import math.MonkeyConverter;

/**
 * Holds the physicsworld
 *
 * @author jnes
 */
public class JMEPhysicsHandler implements PhysicsCollisionListener {

    private BulletAppState bulletAppState;
    private List<PhysicsRigidBody> rigidBodies =
            new LinkedList<PhysicsRigidBody>();
    private PhysicsRigidBody ground;

    public JMEPhysicsHandler() {
        this.bulletAppState = new BulletAppState();
        this.bulletAppState.initialize(null, null);
        this.bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }

    /**
     * Creates the ground in the physics world. Because the engine sees the
     * Vector size as half extents, we divide by 2.
     *
     * @param size
     */
    public void createGround(Vector size, ICollideable model) {
        Vector position = new Vector(size);
        position.mult(0.5f);
        this.ground = new PhysicsRigidBody(new BoxCollisionShape(MonkeyConverter.convertToMonkey3D(size).mult(0.5f)), 0);
        ground.setUserObject(model);
        ground.setPhysicsLocation(MonkeyConverter.convertToMonkey3D(position));
        bulletAppState.getPhysicsSpace().addCollisionObject(ground);
    }

    public void addToWorld(IPhysicalBody object) {
        PhysicsRigidBody body = object.getBody();
        body.setGravity(new Vector3f(0, -1, 0));
        this.rigidBodies.add(body);
        this.bulletAppState.getPhysicsSpace().addCollisionObject(body);
    }

    public void removeFromWorld(IPhysicalBody object) {
        this.bulletAppState.getPhysicsSpace().removeCollisionObject(object.getBody());
        rigidBodies.remove(object);
    }

    public void update(float tpf) {
        this.bulletAppState.getPhysicsSpace().update(tpf);
        this.bulletAppState.update(tpf);

        for (PhysicsRigidBody body : rigidBodies) {
            IPhysicalModel unit = (IPhysicalModel) body.getUserObject();
            unit.getPhysicalObject().updated();
        }
    }

    public void collision(PhysicsCollisionEvent event) {
        if (event.getObjectA().getUserObject() == null || event.getObjectB().getUserObject() == null) {
            return;
        }

        ICollideable modelA = ((ICollideable) (event.getObjectA().getUserObject()));
        ICollideable modelB = ((ICollideable) (event.getObjectB().getUserObject()));

        PhysicsCollisionObject objA = event.getObjectA();
        PhysicsCollisionObject objB = event.getObjectB();

        PhysicsRigidBody bodyA = (PhysicsRigidBody) event.getObjectA();
        PhysicsRigidBody bodyB = (PhysicsRigidBody) event.getObjectB();
        
        Vector3f collision = bodyA.getLinearVelocity().cross(Vector3f.UNIT_Y).mult(event.getAppliedImpulse());

        boolean isGrinding = event.getLifeTime() > 1;
        float impulseA = isGrinding ? 0 : collision.cross(bodyA.getLinearVelocity()).length();
        float impulseB = isGrinding ? 0 : collision.cross(bodyB.getLinearVelocity()).length();

        
        modelA.collidedWith(modelB, impulseB);
        modelB.collidedWith(modelA, impulseA);
    }
}