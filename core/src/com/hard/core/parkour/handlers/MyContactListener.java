package com.hard.core.parkour.handlers;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by leonlevy on 11/6/16.
 */
public class MyContactListener implements ContactListener {

    // called when two fixtures collide
    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        System.out.println("Begin Contact: " + fa.getUserData() + ", " + fb.getUserData());



    }

    // called when two fixtures no longer collide
    public void endContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        System.out.println("End Contact: " + fa.getUserData() + ", " + fb.getUserData());

    }

    // collision detection
    // presolve
    // collision handling
    // postsolve
    public void preSolve(Contact c, Manifold m) {}
    public void postSolve(Contact c, ContactImpulse m) {}


}
