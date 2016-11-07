package com.hard.core.parkour.handlers;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by leonlevy on 11/6/16.
 */
public class MyContactListener implements ContactListener {

    private  boolean playerOnGround;

    // called when two fixtures collide
    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        System.out.println("Begin Contact: " + fa.getUserData() + ", " + fb.getUserData());

        // just trying to figure out which fixture is which
        if (fa.getUserData() != null && fa.getUserData().equals("foot")){
            System.out.println("fa is foot");
            playerOnGround = true;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")){
            System.out.println("fb is foot");
            playerOnGround = true;
        }


    }

    // called when two fixtures no longer collide
    public void endContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        System.out.println("End Contact: " + fa.getUserData() + ", " + fb.getUserData());
        if (fa.getUserData() != null && fa.getUserData().equals("foot")){
            System.out.println("fa is foot");
            playerOnGround = false;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")){
            System.out.println("fb is foot");
            playerOnGround = false;
        }

    }

    public boolean isPlayerOnGround() { return playerOnGround;}

    // collision detection
    // presolve
    // collision handling
    // postsolve
    public void preSolve(Contact c, Manifold m) {}
    public void postSolve(Contact c, ContactImpulse m) {}


}
