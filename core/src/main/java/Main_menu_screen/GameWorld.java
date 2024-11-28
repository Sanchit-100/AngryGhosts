package Main_menu_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
    private World world;

    public GameWorld() {
        // Initialize Box2D
        Box2D.init();


        Vector2 gravity = new Vector2(0, -11f);
        world = new World(gravity, true);

        createScreenBoundaries();
    }

    public World getWorld() {
        return world;
    }

    private void createScreenBoundaries() {
        float worldWidth = Gdx.graphics.getWidth();
        float worldHeight = Gdx.graphics.getHeight();
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1.0f;
        EdgeShape edgeShape = new EdgeShape();
        groundBodyDef.position.set(0, 0);
        edgeShape.set(0, 0, worldWidth, 0);
        fixtureDef.shape = edgeShape;
        world.createBody(groundBodyDef).createFixture(fixtureDef);
        groundBodyDef.position.set(0, worldHeight);
        edgeShape.set(0, 0, worldWidth, 0);
        fixtureDef.shape = edgeShape;
        world.createBody(groundBodyDef).createFixture(fixtureDef);
        groundBodyDef.position.set(0, 0);
        edgeShape.set(0, 0, 0, worldHeight);
        fixtureDef.shape = edgeShape;
        world.createBody(groundBodyDef).createFixture(fixtureDef);
        groundBodyDef.position.set(worldWidth, 0);
        edgeShape.set(0, 0, 0, worldHeight);
        fixtureDef.shape = edgeShape;
        world.createBody(groundBodyDef).createFixture(fixtureDef);
        edgeShape.dispose();
    }

    // Dispose of the world when it's no longer needed
    public void dispose() {
        world.dispose();
    }
}
