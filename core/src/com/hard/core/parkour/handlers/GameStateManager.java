package com.hard.core.parkour.handlers;

//import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.hard.core.parkour.MyGdxGame;
import com.hard.core.parkour.states.GameState;
import com.hard.core.parkour.states.Play;
//import com.sun.tools.javac.jvm.Code;
import java.util.Stack;

/**
 * Created by leonlevy on 11/4/16.
 */
public class GameStateManager {

    private MyGdxGame game;

    private Stack<GameState> gameStates;

    public static final int PLAY = 95817;

    public GameStateManager(MyGdxGame game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(PLAY);
    }

    public MyGdxGame game(){ return game; }

    public void update(float dt){
        gameStates.peek().update(dt);
    }

    public void render(){
        gameStates.peek().render();
    }

    private GameState getState(int state){
        if (state == PLAY) return new Play(this);
        return null;
    }

    public void setState(int state){
        popState();
        pushState(state);
    }

    public void pushState(int state){
        gameStates.push(getState(state));
    }

    public void popState(){
        GameState g = gameStates.pop();
        g.dispose();
    }
}
