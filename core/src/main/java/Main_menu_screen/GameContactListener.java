package Main_menu_screen;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;

public class GameContactListener implements ContactListener {
    private level_1 level;

    public GameContactListener(level_1 level) {
        this.level = level;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Check if the collision involves a pig and the groundBlock
        if (isGroundBlockCollision(fixtureA, fixtureB)) {
            Fixture pigFixture = (fixtureA.getUserData() instanceof Pig) ? fixtureA : fixtureB;
            Pig pig = (Pig) pigFixture.getUserData();
            pig.getBody().setUserData("remove");  // Mark the pig for removal
            level.incrementPigsDestroyed();  // Increment pigs destroyed count
        }

        // Check if the collision involves a bird and the groundBlock
        if (isBirdGroundCollision(fixtureA, fixtureB)) {
            Fixture birdFixture = (fixtureA.getUserData() instanceof Bird) ? fixtureA : fixtureB;
            Bird bird = (Bird) birdFixture.getUserData();
            bird.getBody().setUserData("remove");  // Mark the bird for removal
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    private boolean isGroundBlockCollision(Fixture fixtureA, Fixture fixtureB) {
        Object userDataA = fixtureA.getUserData();
        Object userDataB = fixtureB.getUserData();

        if (userDataA == null || userDataB == null) {
            return false;
        }

        return (userDataA instanceof Pig && "groundBlock".equals(userDataB))
            || (userDataB instanceof Pig && "groundBlock".equals(userDataA));
    }

    private boolean isBirdGroundCollision(Fixture fixtureA, Fixture fixtureB) {
        Object userDataA = fixtureA.getUserData();
        Object userDataB = fixtureB.getUserData();

        if (userDataA == null || userDataB == null) {
            return false;
        }

        return (userDataA instanceof Bird && "groundBlock".equals(userDataB))
            || (userDataB instanceof Bird && "groundBlock".equals(userDataA));
    }
}
