package Main_menu_screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Block {
    private Texture texture;
    private Body body;
    private float width;
    private float height;

    public Block(World world, Texture texture, Vector2 position, float width, float height) {
        this.texture = texture;
        this.width = width;
        this.height = height;

        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position);

        // Create the body in the world
        body = world.createBody(bodyDef);

        // Define the shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.2f; // Less bounciness

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose the shape after using it
        shape.dispose();
    }

    public Texture getTexture() {
        return texture;
    }

    public Body getBody() {
        return body;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
