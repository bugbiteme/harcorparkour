package com.hard.core.parkour.states;

import static com.hard.core.parkour.handlers.B2DVars.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.glutils.VertexBufferObjectWithVAO;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.hard.core.parkour.Game;
import com.hard.core.parkour.handlers.B2DVars;
import com.hard.core.parkour.handlers.GameStateManager;
import com.hard.core.parkour.handlers.MyContactListener;
import com.hard.core.parkour.handlers.MyInput;



/**
 * Created by leonlevy on 11/4/16.
 */
public class Play extends GameState {

   //private BitmapFont font = new BitmapFont();
    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;

    private Body playerBody;
    private MyContactListener cl;

    private TiledMap tileMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    public Play(GameStateManager gsm){

        super(gsm);

        world = new World(new Vector2(0, -9.81f), true);

        cl = new MyContactListener();
        world.setContactListener(cl);

        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //create platform
        /*
        BodyDef bdef = new BodyDef();
        bdef.position.set(160/PPM, 120/PPM);
        bdef.type = BodyType.StaticBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50/PPM, 5/PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_GROUND;
        fdef.filter.maskBits = B2DVars.BIT_PLAYER;
        body.createFixture(fdef).setUserData("ground");
        */

        // create player
        bdef.position.set(160/PPM, 200/PPM);
        bdef.type = BodyType.DynamicBody;
        playerBody = world.createBody(bdef);

        shape.setAsBox(5/PPM, 5/PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_RED;
        playerBody.createFixture(fdef).setUserData("player");

        // create foot sensor
        shape.setAsBox(2/PPM, 2/PPM, new Vector2(0, -5/PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_RED;
        fdef.isSensor = true;
        playerBody.createFixture(fdef).setUserData("foot");

        // set up box2d cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH/PPM, Game.V_HEIGHT/PPM);

        //static body - don't move, unaffected by forces

        //kinematic body - don't get affected forces

        //dynamic body - always get affected by forces


        //////////////////////////////////////////////////////////////////////////////

        // load tile map
        tileMap = new TmxMapLoader().load("res/maps/test.tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);

        TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get("red");

        tileSize = layer.getTileWidth();

        // go through all cells in the layer
        for (int row = 0; row < layer.getHeight(); row++){
            for (int col = 0; col < layer.getWidth(); col++){

                // get cell
                TiledMapTileLayer.Cell cell = layer.getCell(col, row );

                //check if cell exists
                if (cell == null)
                    continue;
                if (cell.getTile() == null)
                    continue;

                //create a body + fixture from the cell
                bdef.type = BodyType.StaticBody;
                bdef.position.set(
                        (col + 0.5f) * tileSize/PPM,
                        (row + 0.5f) * tileSize/PPM
                );

                ChainShape cs = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(-tileSize/2/PPM, -tileSize/2/PPM);
                v[1] = new Vector2(-tileSize/2/PPM,  tileSize/2/PPM);
                v[2] = new Vector2( tileSize/2/PPM,  tileSize/2/PPM);
                cs.createChain(v);
                fdef.friction = 0;
                fdef.shape = cs;
                fdef.filter.categoryBits = B2DVars.BIT_RED;
                fdef.filter.maskBits = -1;
                fdef.isSensor = false;
                world.createBody(bdef).createFixture(fdef);













            }
        }



    }

    public  void handleInput(){

        if (MyInput.isPressed(MyInput.BUTTON1)){
            System.out.println("pressed z");
        }
        if (MyInput.isDown(MyInput.BUTTON2)){
            System.out.println("hold x");
        }

        // player jump
        if (MyInput.isPressed(MyInput.BUTTON1)){
            if (cl.isPlayerOnGround()){
                playerBody.applyForceToCenter(0, 200, true);
            }
        }


    }

    public  void update(float dt){

        handleInput();

        world.step(dt, 6, 2);
    }

    public  void render(){

        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw tile map
        tmr.setView(cam);
        tmr.render();

        // draw box2d world
        b2dr.render(world, b2dCam.combined);
    }

    public  void dispose(){}
}
