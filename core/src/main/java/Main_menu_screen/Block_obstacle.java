package Main_menu_screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Block_obstacle {
    protected Texture texture;
    protected Body body;
    protected float width;
    protected float height;

    public Block_obstacle(World world, Vector2 position, float width, float height) {
        this.width = width;
        this.height = height;

        // Create the body in the world using the dynamic body method
        body = createBody(world, position);

        // Define the fixture
        FixtureDef fixtureDef = getFixtureDef();
        body.createFixture(fixtureDef);

        // Dispose the shape after using it
        fixtureDef.shape.dispose();
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

    public abstract FixtureDef getFixtureDef();

    public abstract Body createBody(World world, Vector2 position);
}
