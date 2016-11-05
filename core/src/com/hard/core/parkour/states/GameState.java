package com.hard.core.parkour.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hard.core.parkour.handlers.GameStateManager;
import com.hard.core.parkour.MyGdxGame;

/**
 * Created by leonlevy on 11/4/16.
 */
public abstract class GameState {

    protected GameStateManager gsm;
    protected MyGdxGame game;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected OrthographicCamera hudCam;

    protected GameState(GameStateManager gsm){
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = game.getCamera();
        hudCam = game.getHUDCamera();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();

}
