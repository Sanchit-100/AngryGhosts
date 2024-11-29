package Main_menu_screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Pig_1 extends Pig {
    public Pig_1(World world, float x, float y) {
        MAX_HITS = 3;
        texture = new Texture("pig1.png");

        // Define body and fixture properties
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = getFixtureDef();
        body.createFixture(fixtureDef).setUserData(this);
        body.setGravityScale(0.5f);
    }

    @Override
    public FixtureDef getFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        CircleShape shape = new CircleShape();
        shape.setRadius(15f);
        fixtureDef.shape = shape;

        return fixtureDef;
    }
}
