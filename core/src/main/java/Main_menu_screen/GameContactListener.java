package Main_menu_screen;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;

public class GameContactListener implements ContactListener {
    private GameLevel level;
    private java.util.Set<Pig> scoredPigs = new java.util.HashSet<>();
    private long levelReloadTime;
    private static final long PIG_IMMUNITY_DURATION = 3000; // 3 seconds of immunity

    public GameContactListener(GameLevel level) {
        this.level = level;
        levelReloadTime = TimeUtils.millis();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (isPigCollision(fixtureA, fixtureB)){
            Fixture pigFixture = (fixtureA.getUserData() instanceof Pig) ? fixtureA : fixtureB;
            Pig pig = (Pig) pigFixture.getUserData();

            // Check if we're within the immunity period
            long currentTime = TimeUtils.millis();
            if (currentTime - levelReloadTime > PIG_IMMUNITY_DURATION) {
                pig.incrementHits();

                if (pig.isDestroyed() && !scoredPigs.contains(pig)) {
                    pig.getBody().setUserData("remove"); // Mark the pig for removal
                    scoredPigs.add(pig); // Prevent multiple scoring
                    level.incrementPigsDestroyed(); // Increment pigs destroyed count
                }
            }
        }


        if (isBirdGroundCollision(fixtureA, fixtureB)) {
            Fixture birdFixture = (fixtureA.getUserData() instanceof Bird) ? fixtureA : fixtureB;
            Bird bird = (Bird) birdFixture.getUserData();
            bird.getBody().setUserData("remove");  // Mark the bird for removal
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

    private boolean isPigCollision(Fixture fixtureA, Fixture fixtureB) {
        Object userDataA = fixtureA.getUserData();
        Object userDataB = fixtureB.getUserData();
        return (userDataA instanceof Pig || userDataB instanceof Pig);
    }

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
