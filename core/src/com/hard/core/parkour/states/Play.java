package com.hard.core.parkour.states;

import static com.hard.core.parkour.handlers.B2DVars.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.hard.core.parkour.Game;
import com.hard.core.parkour.handlers.B2DVars;
import com.hard.core.parkour.handlers.GameStateManager;
import com.hard.core.parkour.handlers.MyContactListener;
import com.sun.org.apache.xpath.internal.operations.Or;


/**
 * Created by leonlevy on 11/4/16.
 */
public class Play extends GameState {

   //private BitmapFont font = new BitmapFont();
    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;


    public Play(GameStateManager gsm){

        super(gsm);

        world = new World(new Vector2(0, -1f), true);
        world.setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();

        //create platform
        BodyDef bdef = new BodyDef();
        bdef.position.set(160/PPM, 120/PPM);
        bdef.type = BodyType.StaticBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50/PPM, 5/PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_GROUND;
        fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
        body.createFixture(fdef).setUserData("ground");

        // create falling box
        bdef.position.set(160/PPM, 200/PPM);
        bdef.type = BodyType.DynamicBody;
        body = world.createBody(bdef);

        shape.setAsBox(5/PPM, 5/PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_BOX;
        //fdef.restitution = 1f;
        fdef.filter.maskBits = B2DVars.BIT_GROUND;
        body.createFixture(fdef).setUserData("box");;

        // create ball
        bdef.position.set(153/PPM, 220/PPM);
        bdef.type = BodyType.DynamicBody;
        body = world.createBody(bdef);

        CircleShape cshape = new CircleShape();
        cshape.setRadius(5/PPM);
        fdef.shape = cshape;
        //fdef.restitution = 0.2f;
        fdef.filter.categoryBits = B2DVars.BIT_BALL;
        fdef.filter.maskBits = B2DVars.BIT_GROUND;
        body.createFixture(fdef).setUserData("ball");;


        // set up box2d cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH/PPM, Game.V_HEIGHT/PPM);


        //static body - don't move, unaffected by forces

        //kinematic body - don't get affected forces

        //dynamic body - always get affected by forces


    }

    public  void handleInput(){}

    public  void update(float dt){

        world.step(dt, 6, 2);
    }

    public  void render(){

        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw box2d world
        b2dr.render(world, b2dCam.combined);
    }

    public  void dispose(){}
}
