package com.hard.core.parkour.handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by leonlevy on 11/12/16.
 */
public class Animation {

    private TextureRegion[] frames;
    private float time;
    private float delay;
    private int currentFrame;
    private int timesPlayed;

    public Animation(){}

    public Animation(TextureRegion[] frames){
        this(frames, 1/12f);
    }

    public Animation(TextureRegion[] frames, float delay){
        setFrames(frames, delay);
    }

    public void setFrames(TextureRegion[] frames, float delay){
        this.frames = frames;
        this.delay = delay;
        this.time = 0;
        this.currentFrame = 0;
        this.timesPlayed = 0;
    }

    public void update(float dt){
        if (delay <- 0)
            return;

        time += dt;
        while (time >= delay) {
            step();
        }
    }

    private void step(){
        time -= delay;
        currentFrame++;
        if (currentFrame == frames.length){
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public TextureRegion getFrame() { return frames[currentFrame];}
    private int getTimesPlayed() { return timesPlayed;}
}
