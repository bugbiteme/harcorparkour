package com.hard.core.parkour.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * Created by leonlevy on 11/6/16.
 *
 *  THis is where all the collision handling between game object goes
 */
public class MyContactListener implements ContactListener {

    private boolean playerOnGround; //is this wrong?
    private int numFootContacts = 0;
    private Array<Body> bodiesToRemove;

    public MyContactListener(){
        super();
        bodiesToRemove = new Array<Body>();
    }

    // called when two fixtures collide
    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (B2DVars.DEBUG) System.out.println("Begin Contact: " + fa.getUserData() + ", " + fb.getUserData());

        // just trying to figure out which fixture is which
        if (fa.getUserData() != null && fa.getUserData().equals("foot")){
            if (B2DVars.DEBUG) System.out.println("fa is foot");
            numFootContacts++;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")){
            if (B2DVars.DEBUG) System.out.println("fb is foot");
            numFootContacts++;
        }

        if (fa.getUserData() != null && fa.getUserData().equals("crystal")){
            if (B2DVars.DEBUG) System.out.println("fa is crystal");
            // remove crystals
            bodiesToRemove.add(fa.getBody());

        }
        if (fb.getUserData() != null && fb.getUserData().equals("crystal")){
            if (B2DVars.DEBUG) System.out.println("fb is crystal");
            // remove crystals
            bodiesToRemove.add(fb.getBody());

        }



    }

    // called when two fixtures no longer collide
    public void endContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (B2DVars.DEBUG) System.out.println("End Contact: " + fa.getUserData() + ", " + fb.getUserData());
        if (fa.getUserData() != null && fa.getUserData().equals("foot")){
            if (B2DVars.DEBUG) System.out.println("fa is foot");
            numFootContacts--;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")){
            if (B2DVars.DEBUG) System.out.println("fb is foot");
            numFootContacts--;
        }

    }

    public boolean isPlayerOnGround() { return numFootContacts > 0;}
    public Array<Body> getBodiesToRemove(){ return bodiesToRemove; }

    // collision detection
    // presolve
    // collision handling
    // postsolve
    public void preSolve(Contact c, Manifold m) {}
    public void postSolve(Contact c, ContactImpulse m) {}


}
