package Main_menu_screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Bird {
    private Texture texture;
    private Body body;
    private float radius;
    private boolean launched;
    private boolean destroyed;
    private Vector2 originalPosition; // Store the original spawn position

    public Bird(World world, Texture texture, Vector2 position, float radius) {
        this.texture = texture;
        this.radius = radius;
        this.launched = false;
        this.destroyed = false;
        this.originalPosition = new Vector2(position); // Store original position

        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, Math.max(position.y, radius)); // Ensure y is at least radius height


        // Create the body in the world
        body = world.createBody(bodyDef);

        // Define the shape
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 5f; // Lighter weight
        fixtureDef.friction = 0.01f; // Reduced friction
        fixtureDef.restitution = 0.4f; // Moderate bounciness

        // Collision filtering
        fixtureDef.filter.categoryBits = 0x0002;
        fixtureDef.filter.maskBits = 0x0004 | 0x0001; // Can collide with ground and other objects

        body.createFixture(fixtureDef);

        // Additional body configuration
        body.setFixedRotation(true);
        body.setGravityScale(0.3f);
        body.setLinearDamping(0.02f);

        shape.dispose();
    }

    // Getters and basic methods
    public Texture getTexture() {
        return texture;
    }

    public Body getBody() {
        return body;
    }

    public float getRadius() {
        return radius;
    }

    // Enhanced launch mechanics
    public void launch(Vector2 launchVector, float launchForce) {
        // Reset any previous velocity
        body.setLinearVelocity(0, 0);

        // Apply launch impulse
        Vector2 scaledLaunchVector = launchVector.nor().scl(launchForce);
        body.applyLinearImpulse(scaledLaunchVector, body.getWorldCenter(), true);

        // Set launch state
        launched = true;
    }

    // Improved launch state management
    public void setLaunched(boolean launched) {
        this.launched = launched;
        // Optional: Additional logic when launch state changes
        if (launched) {
            body.setLinearDamping(0.1f); // Reduce air resistance
        }
    }

    public boolean isLaunched() {
        return launched;
    }

    // Enhanced bounds checking
    public boolean isOutOfBounds(float worldWidth, float worldHeight) {
        Vector2 position = body.getPosition();
        float buffer = 200f; // Increased buffer zone
        return position.x < -buffer ||
            position.x > worldWidth + buffer ||
            position.y < -buffer ||
            position.y > worldHeight + buffer;
    }

    // Velocity management
    public void limitVelocity(float maxVelocity) {
        Vector2 velocity = body.getLinearVelocity();
        if (velocity.len() > maxVelocity) {
            velocity.setLength(maxVelocity);
            body.setLinearVelocity(velocity);
        }
    }

    // Advanced reset method
    public void reset() {
        // Reset to original position
        body.setTransform(originalPosition, 0);

        // Clear velocity
        body.setLinearVelocity(0, 0);

        // Reset states
        launched = false;
        destroyed = false;

        // Reset physics properties
        body.setLinearDamping(0);
    }

    // Collision and interaction methods
    public void setCollisionTag(String tag) {
        body.getFixtureList().first().setUserData(tag);
    }

    // Destruction management
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        if (destroyed) {
            // Optional: Additional destruction logic
            body.setActive(false);
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    // Check if bird can be selected
    public boolean isSelectable() {
        return !launched && !destroyed;
    }

    // Precise positioning method
    public void setPosition(Vector2 position) {
        body.setTransform(position, body.getAngle());
    }

    // Resource management
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    // Additional utility methods
    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }
}
