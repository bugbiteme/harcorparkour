package com.hard.core.parkour.states;

import static com.hard.core.parkour.handlers.B2DVars.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.glutils.VertexBufferObjectWithVAO;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.hard.core.parkour.Game;
import com.hard.core.parkour.entities.Crystal;
import com.hard.core.parkour.entities.Player;
import com.hard.core.parkour.handlers.B2DVars;
import com.hard.core.parkour.handlers.GameStateManager;
import com.hard.core.parkour.handlers.MyContactListener;
import com.hard.core.parkour.handlers.MyInput;



/**
 * Created by leonlevy on 11/4/16.
 */
public class Play extends GameState {

    private boolean debug = false;

   //private BitmapFont font = new BitmapFont();
    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;

    //private Body playerBody;
    private MyContactListener cl;

    private TiledMap tileMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    private Player player;
    private Array<Crystal> crystals;

    public Play(GameStateManager gsm){

        super(gsm);

        // set up box2d stuff
        world = new World(new Vector2(0, -9.81f), true);
        cl = new MyContactListener();
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();

        // create player
        createPlayer();

        // create tiles
        createTiles();

        // create crystals
        createCrystals();

        // set up box2d cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH/PPM, Game.V_HEIGHT/PPM);

        //static body - don't move, unaffected by forces

        //kinematic body - don't get affected forces

        //dynamic body - always get affected by forces
    }

    public  void handleInput(){

        if (B2DVars.DEBUG) {
            if (MyInput.isPressed(MyInput.BUTTON1)) {
                System.out.println("pressed z");
            }
            if (MyInput.isDown(MyInput.BUTTON2)) {
                System.out.println("hold x");
            }
        }

        // player jump
        if (MyInput.isPressed(MyInput.BUTTON1)){
            if (cl.isPlayerOnGround()){
                player.getBody().applyForceToCenter(0, 250, true);
            }
        }


    }

    public  void update(float dt){

        handleInput();

        world.step(dt, 6, 2);

        // remove crystals
        Array<Body> bodies = cl.getBodiesToRemove();

        for (int i =0; i < bodies.size; i++) {
            Body body = bodies.get(i);
            crystals.removeValue((Crystal) body.getUserData(), true);
            world.destroyBody(body);
            player.collectCrystal();
        }
        bodies.clear();


        player.update(dt);

        for (int i = 0; i < crystals.size; i++){
            crystals.get(i).update(dt);
        }
    }

    public  void render(){

        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw tile map
        tmr.setView(cam);
        tmr.render();

        // draw player
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);

        // draw crystals
        for (int i = 0; i < crystals.size; i++){
            crystals.get(i).render(sb);
        }

        // draw box2d world (green outlines of drawings
        if (B2DVars.DEBUG)
            b2dr.render(world, b2dCam.combined);
    }

    public  void dispose(){}

    private void createPlayer(){

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // create player
        bdef.position.set(100/PPM, 200/PPM);
        bdef.type = BodyType.DynamicBody;
        bdef.linearVelocity.set(0.1f, 0);
        Body body = world.createBody(bdef);

        shape.setAsBox(13/PPM, 13/PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_RED;
        body.createFixture(fdef).setUserData("player");

        // create foot sensor
        shape.setAsBox(13/PPM, 2/PPM, new Vector2(0, -13/PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_RED | B2DVars.BIT_CRYSTAL;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

        // create player
        player = new Player(body);

        body.setUserData(player);

    }

    private void createTiles(){

        // load tile map
        tileMap = new TmxMapLoader().load("res/maps/test2.tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);

        //tileSize = layer.getTileWidth();
        if (B2DVars.DEBUG)
            System.out.println("Tile size: " + tileMap.getProperties().get("tilewidth").toString());

        tileSize = Float.parseFloat(tileMap.getProperties().get("tilewidth").toString());

        TiledMapTileLayer layer;
        layer = (TiledMapTileLayer) tileMap.getLayers().get("red");
        createLayer(layer,B2DVars.BIT_RED);

        layer = (TiledMapTileLayer) tileMap.getLayers().get("green");
        createLayer(layer,B2DVars.BIT_GREEN );

        layer = (TiledMapTileLayer) tileMap.getLayers().get("blue");
        createLayer(layer,B2DVars.BIT_BLUE);
    }

    private void createLayer(TiledMapTileLayer layer, short bits ){

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

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
                fdef.filter.categoryBits = bits;
                fdef.filter.maskBits = -1;
                fdef.isSensor = false;
                world.createBody(bdef).createFixture(fdef);
            }
        }

    }

    private void createCrystals() {

        crystals = new Array<Crystal>();

        MapLayer layer = tileMap.getLayers().get("crystals");

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        for (MapObject mo : layer.getObjects()){

            bdef.type = BodyType.StaticBody;

            float x = Float.parseFloat(mo.getProperties().get("x").toString())/PPM;
            float y = Float.parseFloat(mo.getProperties().get("y").toString())/PPM;

            bdef.position.set(x,y);

            CircleShape cshape = new CircleShape();
            cshape.setRadius(8/PPM);

            fdef.shape = cshape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = B2DVars.BIT_CRYSTAL;
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;

            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("crystal");

            Crystal c = new Crystal(body);
            crystals.add(c);

            body.setUserData(c);
        }

    }
}
