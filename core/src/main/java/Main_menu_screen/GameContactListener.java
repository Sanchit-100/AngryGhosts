package Main_menu_screen;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (isGroundBlockCollision(fixtureA, fixtureB)) {
            Fixture pigFixture = (fixtureA.getUserData() instanceof Pig) ? fixtureA : fixtureB;
            Pig pig = (Pig) pigFixture.getUserData();
            pig.getBody().setUserData("remove");
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private boolean isGroundBlockCollision(Fixture fixtureA, Fixture fixtureB) {
        Object userDataA = fixtureA.getUserData();
        Object userDataB = fixtureB.getUserData();
        if (userDataA == null || userDataB == null) {
            return false;
        }
        return (userDataA instanceof Pig && "groundBlock".equals(userDataB)) || (userDataB instanceof Pig && "groundBlock".equals(userDataA));
    }
}
