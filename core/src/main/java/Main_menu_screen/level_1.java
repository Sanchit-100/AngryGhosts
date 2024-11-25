package Main_menu_screen;

import angry.birds.game.Angry_ghosts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;


public class level_1 implements Screen {
    private static final float BUTTON_WIDTH_PERCENT = 0.1f;
    private static final float BUTTON_HEIGHT_PERCENT = 0.1f;

    private Texture backgroundImage;
    private Texture slingshotImage;
    private Texture[] ghostSprites = new Texture[3];
    private Texture[] pigSprites = new Texture[3];
    private Texture glassBlock;
    private Texture groundBlock;
    private Texture woodenBlock;
    private Texture winButton;
    private Texture loseButton;
    private Texture pauseButton;

    private Angry_ghosts ag;
    private OrthographicCamera camera;
    private Viewport viewport;

    private float buttonWidth, buttonHeight;
    private float xWin, xLose;

    private GameWorld gameWorld; // Add GameWorld
    private Box2DDebugRenderer debugRenderer; // Debug renderer for Box2D

    private List<Bird> birds;
    private List<Pig> pigs;
    private List<Block> blocks;

    private Bird currentBird;
    private boolean isDragging;
    private Vector2 slingStart;
    private Vector2 slingEnd;

    public level_1(Angry_ghosts ag) {
        this.ag = ag;

        // Load textures
        backgroundImage = new Texture("Assets/spooky_bg_6.jpg");
        slingshotImage = new Texture("Assets/slingshot.png");
        ghostSprites[0] = new Texture("Assets/angry1.png");
        ghostSprites[1] = new Texture("Assets/angry2.png");
        ghostSprites[2] = new Texture("Assets/angry3.png");
        pigSprites[0] = new Texture("Assets/pig1.png");
        pigSprites[1] = new Texture("Assets/pig1.png");
        pigSprites[2] = new Texture("Assets/pig1.png");
        pauseButton = new Texture("Assets/pause.png");
        glassBlock = new Texture("Assets/glass_block.png");
        woodenBlock = new Texture("Assets/wooden_block.png");
        winButton = new Texture("Assets/win_icon.png");
        loseButton = new Texture("Assets/lose_icon.png");
        groundBlock = new Texture("Assets/ground.jpg");

        // Create the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        // Initialize GameWorld
        gameWorld = new GameWorld();

        gameWorld.getWorld().setContactListener(new GameContactListener());

        // Initialize Box2D debug renderer
        debugRenderer = new Box2DDebugRenderer();

        // Initialize game objects
        initializeGameObjects();

        // Initialize slingshot positions
        slingStart = new Vector2(250, 150);  // Position of the slingshot
        slingEnd = new Vector2();  // To be updated during dragging
        currentBird = birds.get(0);  // Use the first bird initially
    }

    private void initializeGameObjects() {
        birds = new ArrayList<>();
        pigs = new ArrayList<>();
        blocks = new ArrayList<>();

        // Create birds
        birds.add(new Bird(gameWorld.getWorld(), ghostSprites[0], new Vector2(250, 200), 40));
        birds.add(new Bird(gameWorld.getWorld(), ghostSprites[1], new Vector2(300, 200), 40));
        birds.add(new Bird(gameWorld.getWorld(), ghostSprites[2], new Vector2(350, 200), 40));

        // Create pigs
        pigs.add(new Pig(gameWorld.getWorld(), pigSprites[0], new Vector2(1100, 430), 40));
        pigs.add(new Pig(gameWorld.getWorld(), pigSprites[1], new Vector2(1300, 430), 40));
        pigs.add(new Pig(gameWorld.getWorld(), pigSprites[2], new Vector2(1500, 430), 40));

        for (Pig pig : pigs) {
            pig.getBody().getFixtureList().first().setUserData(pig);
        }
        // Create blocks
        blocks.add(new Block(gameWorld.getWorld(), glassBlock, new Vector2(1100, 400), 120, 50));
        blocks.add(new Block(gameWorld.getWorld(), woodenBlock, new Vector2(1300, 400), 120, 50));
        blocks.add(new Block(gameWorld.getWorld(), glassBlock, new Vector2(1500, 400), 120, 50));
        blocks.add(new Block(gameWorld.getWorld(), groundBlock, new Vector2(950, 80), 2000, 200));

        for (Block block : blocks) {
            if (block.getTexture() == groundBlock) {
                block.getBody().getFixtureList().first().setUserData("groundBlock");
            } else {
                block.getBody().getFixtureList().first().setUserData("block");
            }
        }
    }

    @Override
    public void show() {
        updateButtonPositions();
    }

    private void updateButtonPositions() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        buttonWidth = worldHeight * BUTTON_WIDTH_PERCENT;
        buttonHeight = worldHeight * BUTTON_HEIGHT_PERCENT;
        xWin = worldWidth - buttonWidth * 2;
        xLose = worldWidth - buttonWidth;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        camera.update();
        ag.batch.setProjectionMatrix(camera.combined);
        ag.batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Draw the background
        ag.batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw the slingshot
        ag.batch.draw(slingshotImage, slingStart.x, slingStart.y, 150, 250);

        // Draw birds
        for (Bird bird : birds) {
            if (bird != currentBird) {
                Vector2 position = bird.getBody().getPosition();
                ag.batch.draw(bird.getTexture(), position.x - bird.getRadius(), position.y - bird.getRadius(), bird.getRadius() * 2, bird.getRadius() * 2);
            }
        }

        // Draw the current bird if it has been launched or is being dragged
        if (currentBird != null) {
            Vector2 position = currentBird.getBody().getPosition();
            float yOffset = isDragging ? 200 : 0;  // Raise the current bird by 30 units while dragging
            ag.batch.draw(currentBird.getTexture(), position.x - currentBird.getRadius(), position.y - currentBird.getRadius() + yOffset, currentBird.getRadius() * 2, currentBird.getRadius() * 2);
        }

        // Draw pigs
        for (Pig pig : pigs) {
            Vector2 position = pig.getBody().getPosition();
            ag.batch.draw(pig.getTexture(), position.x - pig.getRadius(), position.y - pig.getRadius(), pig.getRadius() * 2, pig.getRadius() * 2);
        }

        // Draw blocks
        for (Block block : blocks) {
            Vector2 position = block.getBody().getPosition();
            ag.batch.draw(block.getTexture(), position.x - block.getWidth() / 2, position.y - block.getHeight() / 2, block.getWidth(), block.getHeight());
        }

        // Draw the buttons at the bottom right
        ag.batch.draw(winButton, xWin, 50, buttonWidth, buttonHeight);
        ag.batch.draw(loseButton, xLose, 50, buttonWidth, buttonHeight);
        ag.batch.draw(pauseButton, worldWidth * 0.05f, worldHeight * 0.9f, buttonWidth, buttonHeight);

        ag.batch.end();

        // Comment out or remove this line to avoid rendering the debug visuals
        // debugRenderer.render(gameWorld.getWorld(), camera.combined);

        // Handle input
        handleInput();

        // Remove pigs marked for removal
        pigs.removeIf(pig -> "remove".equals(pig.getBody().getUserData()));

        // Update game world
        gameWorld.getWorld().step(delta, 6, 2);
    }



    private void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (!isDragging) {
                isDragging = true;
            }

            // Restrict the bird's position to within a certain distance from the slingshot
            Vector2 stretchVector = slingStart.cpy().sub(touch.x, touch.y);
            float maxStretch = 200f; // Maximum stretch length
            if (stretchVector.len() > maxStretch) {
                stretchVector.setLength(maxStretch);
            }

            // Ensure the bird's position doesn't go below ground level
            float groundLevelY = 100f; // Example ground level y-coordinate
            float newY = slingStart.y - stretchVector.y;
            if (newY < groundLevelY) {
                newY = groundLevelY;
            }

            // Update slingEnd to the current touch position, constrained by ground level
            slingEnd.set(slingStart.x - stretchVector.x, newY);

            // Update the bird's position to follow the drag within the restricted area
            currentBird.getBody().setTransform(slingEnd, 0);
        } else if (isDragging) {
            // When the user releases the touch, launch the bird
            isDragging = false;

            // Ensure bird launches from its current position on the slingshot
            Vector2 launchPosition = currentBird.getBody().getPosition();
            launchPosition.y+=200;

            // Calculate the launch force based on the stretch vector
            Vector2 launchVector = slingStart.cpy().sub(launchPosition);
            float launchForce = 1e12f * launchVector.len(); // Adjust scaling factor for balanced launch

            // Normalize the launch vector to maintain direction but scale the magnitude
//            launchVector.nor().scl(launchForce);

            currentBird.getBody().setLinearDamping(0.5f); // To reduce bird's speed over time
            currentBird.getBody().applyLinearImpulse(launchVector, currentBird.getBody().getWorldCenter(), true);
            System.out.println(currentBird.getBody().getPosition());

            // Keep track of the launched bird without removing it
            currentBird = null;

            // Set the next bird as the current bird, if available
            if (!birds.isEmpty()) {
                currentBird = birds.get(0);
            }
        }
    }







    private boolean isButtonClicked(Vector3 touch, float x, float y) {
        return touch.x >= x && touch.x <= x + buttonWidth && touch.y >= y && touch.y <= y + buttonHeight;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        updateButtonPositions();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        slingshotImage.dispose();
        for (Texture ghost : ghostSprites) {
            ghost.dispose();
        }
        for (Texture pig : pigSprites) {
            pig.dispose();
        }
        glassBlock.dispose();
        woodenBlock.dispose();
        winButton.dispose();
        loseButton.dispose();
        gameWorld.dispose(); // Dispose the game world
        debugRenderer.dispose(); // Dispose the debug renderer
    }
}
