package Main_menu_screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Block_3 extends Block_obstacle {
    public Block_3(World world, Vector2 position, float width, float height) {
        super(world, position, width, height);
        texture = new Texture("wood_block_horizontal.png");
    }

    @Override
    public FixtureDef getFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        fixtureDef.shape = shape;

        return fixtureDef;
    }

    @Override
    public Body createBody(World world, Vector2 position) {
        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Change to DynamicBody
        bodyDef.position.set(position);
        bodyDef.angularDamping=2.0f;
        bodyDef.linearDamping=0.5f;

        // Create the body in the world
        return world.createBody(bodyDef);
    }
}