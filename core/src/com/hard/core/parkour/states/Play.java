package com.hard.core.parkour.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.hard.core.parkour.handlers.GameStateManager;

/**
 * Created by leonlevy on 11/4/16.
 */
public class Play extends GameState {

    private BitmapFont font = new BitmapFont();

    public Play(GameStateManager gsm){
        super(gsm);

    }

    public  void handleInput(){}

    public  void update(float dt){}

    public  void render(){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        font.draw(sb, "play state", 100, 100);
        sb.end();
    }

    public  void dispose(){}
}
