package com.hard.core.parkour.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.hard.core.parkour.Game;

/**
 * Created by leonlevy on 11/13/16.
 */
public class Crystal extends B2DSprite {

    public Crystal(Body body) {

        super(body);

        Texture tex = Game.res.getTexture("crystal");
        TextureRegion[] sprites = TextureRegion.split(tex, 16, 16)[0];

        this.setAnimation(sprites, 1/12f);
    }
}
