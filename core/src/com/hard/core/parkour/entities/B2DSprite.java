package com.hard.core.parkour.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.hard.core.parkour.handlers.Animation;
import com.hard.core.parkour.handlers.B2DVars;

/**
 * Created by leonlevy on 11/12/16.
 */
public class B2DSprite {

    protected Body body;
    protected Animation animation;
    protected float width;
    protected float height;

    public B2DSprite(Body body){
        this.body = body;
        this.animation = new Animation();
    }

    public void setAnimation(TextureRegion[] reg, float delay){
        animation.setFrames(reg, delay);
        this.width = reg[0].getRegionWidth();
        this.height  = reg[0].getRegionHeight();
    }

    public void update(float dt){
        animation.update(dt);
    }

    public void render(SpriteBatch sb){
        sb.begin();
        sb.draw(
                this.animation.getFrame(),
                this.body.getPosition().x * B2DVars.PPM - width / 2,
                this.body.getPosition().y * B2DVars.PPM - width / 2
                );
        sb.end();
    }

    public Body getBody() { return this.body; }
    public Vector2 getPosition(){ return body.getPosition(); }
    public float getWidth(){ return this.width; }
    public float getHeight(){ return this.height; }

}
