package com.hard.core.parkour.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.hard.core.parkour.Game;
import com.hard.core.parkour.states.Play;

/**
 * Created by leonlevy on 11/12/16.
 */
public class Player extends B2DSprite{

    private int numCrystals;
    private int totalCrystals;

    public Player(Body body){

        super(body);

        Texture tex = Game.res.getTexture("bunny");
        TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];

        setAnimation(sprites, 1/12f);
    }

    public void collectCrystal() { this.numCrystals++; }
    public int getNumCrystals() { return numCrystals; }
    public void setTotalCrystals(int i){ this.totalCrystals = i; }
    public int getTotalCrystals(){ return this.numCrystals; }

}
