package Main_menu_screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Pig {
    private Texture texture;
    private Body body;
    private float radius;

    public Pig(World world, Texture texture, Vector2 position, float radius) {
        this.texture = texture;
        this.radius = radius;

        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        // Create the body in the world
        body = world.createBody(bodyDef);

        // Define the shape
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f; // Bounciness

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

    public float getRadius() {
        return radius;
    }
}
