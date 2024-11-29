package Main_menu_screen;

import angry.birds.game.Angry_ghosts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class level_1 implements GameLevel,Screen {
    private int pigsDestroyed = 0;
    private static final float BUTTON_WIDTH_PERCENT = 0.1f;
    private static final float BUTTON_HEIGHT_PERCENT = 0.1f;
    int tries = 0;
    private static final int MAX_TRIES = 3;
    private float timeSinceLastLaunch = 0;
    private boolean birdLaunched = false;
    private ShapeRenderer shapeRenderer;
  //  private Vector2 lastVelocity = new Vector2(0, 0);
    private float stationaryTime = 0f;
   // private static final float MAX_STATIONARY_TIME = 2f; // 2 seconds of minimal movement
    //private static final float MOVEMENT_THRESHOLD = 0.5f; // Threshold for considering movement
    private boolean checkBirdStop = false;

    private static final float LAUNCH_FORCE_MULTIPLIER = 20000000000000f;
    private static final float MAX_LAUNCH_STRETCH = 250f;
    private static final float LAUNCH_DAMPENING = 0.05f;



    private Texture backgroundImage;
    private Texture slingshotImage;
    Texture[] ghostSprites = new Texture[3];
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

    GameWorld gameWorld; // Add GameWorld
    private Box2DDebugRenderer debugRenderer; // Debug renderer for Box2D

    List<Bird> birds;
    List<Pig> pigs;
    List<Block> blocks;
    int score;

    Bird currentBird;
    private boolean isDragging;
    private Vector2 slingStart;
    private Vector2 slingEnd;

    public level_1(Angry_ghosts ag) {
        this.ag = ag;
        //score =0;


        // Load textures
        backgroundImage = new Texture("spooky_bg_6.jpg");
        slingshotImage = new Texture("slingshot.png");
        ghostSprites[0] = new Texture("angry1.png");
        ghostSprites[1] = new Texture("angry2.png");
        ghostSprites[2] = new Texture("angry3.png");
        pigSprites[0] = new Texture("pig1.png");
        pigSprites[1] = new Texture("pig1.png");
        pigSprites[2] = new Texture("pig1.png");
        pauseButton = new Texture("pause.png");
        glassBlock = new Texture("glass_block.png");
        woodenBlock = new Texture("wooden_block.png");
        winButton = new Texture("win_icon.png");
        loseButton = new Texture("lose_icon.png");
        groundBlock = new Texture("ground.jpg");
        shapeRenderer = new ShapeRenderer();



            camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        // Initialize GameWorld
        gameWorld = new GameWorld();
        gameWorld.getWorld().setContactListener(new GameContactListener(this));

        // Initialize Box2D debug renderer
        debugRenderer = new Box2DDebugRenderer();

        // Initialize game objects
        initializeGameObjects();

        // Initialize slingshot positions
        slingStart = new Vector2(250, 150);  // Position of the slingshot
        slingEnd = new Vector2();  // To be updated during dragging
        currentBird = birds.get(0);  // Use the first bird initially
    }

    public void incrementPigsDestroyed() {
        pigsDestroyed++;
    }

    public GameWorld getWorld(){
        return gameWorld;
    }

    int getScore() {
        score = pigsDestroyed*500;
        return score;
    }

    public void loadSavedGame() {
        String savePath = System.getProperty("user.dir") + "/level1_save.ser";
        File saveFile = new File("level1_save.ser");

        if(saveFile.exists()){
            try{
                LevelState savedState = LevelState.loadFromFile(savePath);

                if(savedState != null){
                    // Clear existing game objects
                    birds.clear();
                    pigs.clear();
                    blocks.clear();

                    // Restore saved state
                    savedState.restoreState(this);

                    // Restore additional game state
                    this.tries = savedState.tries;
                    //this.score = savedState;

                    System.out.println("Game state loaded successfully!");
                }
            }
            catch(Exception e){
                System.err.println("Error loading game state:");
                e.printStackTrace();
            }
        }
        else{
            System.out.println("No save file found.");
        }
    }


    private void initializeGameObjects(){
        birds = new ArrayList<>();
        pigs = new ArrayList<>();
        blocks = new ArrayList<>();

        // Adjust bird spawn heights
        float groundHeight = 200f; // Adjust based on your ground block positioning
        birds.add(new Bird(gameWorld.getWorld(), ghostSprites[0], new Vector2(250, groundHeight + 100), 40));
        birds.add(new Bird(gameWorld.getWorld(), ghostSprites[1], new Vector2(300, groundHeight + 100), 40));
        birds.add(new Bird(gameWorld.getWorld(), ghostSprites[2], new Vector2(350, groundHeight + 100), 40));
        // Create pigs
        pigs.add(new Pig_1(gameWorld.getWorld(),  1100, 450));
        pigs.add(new Pig_2(gameWorld.getWorld(),  1400, 450));
        pigs.add(new Pig_3(gameWorld.getWorld(),  1700, 450));

        for(Pig pig : pigs){
            pig.getBody().getFixtureList().first().setUserData(pig);
        }

        blocks.add(new Block(gameWorld.getWorld(), glassBlock, new Vector2(1100, 400), 120, 100));
        blocks.add(new Block(gameWorld.getWorld(), woodenBlock, new Vector2(1400, 400), 120, 70));
        blocks.add(new Block(gameWorld.getWorld(), glassBlock, new Vector2(1700, 400), 120, 100));
        blocks.add(new Block(gameWorld.getWorld(), groundBlock, new Vector2(950, 80), 2000, 200));

        for(Block block : blocks){
            if(block.getTexture() == groundBlock){
                block.getBody().getFixtureList().first().setUserData("groundBlock");
            }
            else{
                block.getBody().getFixtureList().first().setUserData("block");
            }
        }
    }

    @Override
    public void show(){
        updateButtonPositions();
    }

    private void updateButtonPositions(){
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        buttonWidth = worldHeight * BUTTON_WIDTH_PERCENT;
        buttonHeight = worldHeight * BUTTON_HEIGHT_PERCENT;
        xWin = worldWidth - buttonWidth * 2;
        xLose = worldWidth - buttonWidth;
    }

    void checkScoreAndProceed() {
        this.score = getScore();

        // Check if all pigs are destroyed
        boolean allPigsDestroyed = pigs.isEmpty();

        // Check if score meets the threshold and all pigs are destroyed
        if (this.score >= 1500 && allPigsDestroyed) {
            ag.setScreen(new win_screen(ag, score));
        } else if (tries >= MAX_TRIES || birds.isEmpty()) {
            ag.setScreen(new lose_screen(ag, score));
        }
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
        if(birdLaunched){
            checkBirdVelocity();
        }
        for (Bird bird : birds) {
            Vector2 position = bird.getBody().getPosition();

            // Only draw birds that aren't the current launching bird
            if (bird != currentBird || !isDragging) {
                ag.batch.draw(bird.getTexture(),
                    position.x - bird.getRadius(),
                    position.y - bird.getRadius(),
                    bird.getRadius() * 2,
                    bird.getRadius() * 2
                );
            }
        }

        float fixedTimeStep = Math.min(delta, 1 / 90f); // Cap the timestep to prevent jumps
        gameWorld.getWorld().step(fixedTimeStep * 2, 8, 1);

        // Draw the current bird if it has been launched or is being dragged
        if (currentBird != null && birdLaunched) {
            Vector2 position = currentBird.getBody().getPosition();
            ag.batch.draw(currentBird.getTexture(),
                position.x - currentBird.getRadius(),
                position.y - currentBird.getRadius(),
                currentBird.getRadius() * 2,
                currentBird.getRadius() * 2
            );
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

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);
        String scoreText = "Score: " + score;
        font.draw(ag.batch, scoreText, worldWidth - 200, worldHeight - 50);
        if (isDragging && currentBird != null) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            Vector2 birdPos = currentBird.getBody().getPosition();
            shapeRenderer.line(250, 150, birdPos.x, birdPos.y);
            shapeRenderer.end();
        }

        ag.batch.end();

        // Comment out or remove this line to avoid rendering the debug visuals
        // debugRenderer.render(gameWorld.getWorld(), camera.combined);


        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);
            if (isButtonClicked(touch, xWin, 50)) {
    //                this.dispose();
                ag.setScreen(new win_screen(ag, 1000));
            }
            else if (isButtonClicked(touch, xLose, 50)) {
    //                this.dispose();
                ag.setScreen(new lose_screen(ag, 10000));
            }
            else if (isButtonClicked(touch, worldWidth * 0.05f, worldHeight * 0.9f)) {
    //                this.dispose();
                ag.setScreen(new PauseScreen(ag));
            }
        }
        handleInput();
        pigs.removeIf(pig -> {
            if ("remove".equals(pig.getBody().getUserData())) {
                gameWorld.getWorld().destroyBody(pig.getBody());
                return true;
            }
            return false;
        });


        if (currentBird != null) {
            Vector2 velocity = currentBird.getBody().getLinearVelocity();
            System.out.println("Bird Velocity: " + velocity);
        }


        if (birdLaunched) {
            timeSinceLastLaunch += delta;
        }
        if (currentBird != null && birdLaunched) {
            Vector2 currentVelocity = currentBird.getBody().getLinearVelocity();
            System.out.println("Detailed Velocity Tracking:");
            System.out.println("Current Velocity X: " + currentVelocity.x);
            System.out.println("Current Velocity Y: " + currentVelocity.y);
            System.out.println("Stationary Time: " + stationaryTime);
        }
        // Update game world
        gameWorld.getWorld().step(delta, 8, 3);
    }
    private static final float MAX_STRETCH_DISTANCE = 250f;
   // private static final float LAUNCH_FORCE_MULTIPLIER = 15f;
   public void setScore(int newScore) {
       this.score = newScore;
   }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            // Bird selection and dragging logic
            if (!birdLaunched && !isDragging) {
                for (Bird bird : birds) {
                    Vector2 birdPosition = bird.getBody().getPosition();
                    float touchRadius = bird.getRadius() * 3;
                    float distanceToBird = Vector2.dst(touch.x, touch.y, birdPosition.x, birdPosition.y);

                    if (bird.isSelectable() && distanceToBird < touchRadius) {
                        currentBird = bird;
                        isDragging = true;
                        System.out.println("Bird Selected: " + currentBird);
                        break;
                    }
                }
            }

            if (currentBird != null && isDragging) {
                // Fixed slingshot center
                Vector2 slingshotCenter = new Vector2(250, 150);

                // Calculate stretch vector
                Vector2 stretchVector = new Vector2(
                    slingshotCenter.x - touch.x,
                    slingshotCenter.y - touch.y
                );

                // Limit stretch distance
                float maxStretchDistance = MAX_STRETCH_DISTANCE;
                if (stretchVector.len() > maxStretchDistance) {
                    stretchVector.setLength(maxStretchDistance);
                }

                // Calculate bird position within slingshot
                Vector2 birdPosition = new Vector2(
                    slingshotCenter.x - stretchVector.x,
                    slingshotCenter.y - stretchVector.y
                );

                // Modify bird position during drag without completely stopping physics
                Body birdBody = currentBird.getBody();
                birdBody.setTransform(birdPosition, birdBody.getAngle());
            }
        }
        else if (isDragging) {
            // Bird Launch Logic
            if (currentBird != null) {
                Body birdBody = currentBird.getBody();

                // Slingshot center
                Vector2 slingshotCenter = new Vector2(250, 150);

                // Calculate launch vector
                Vector2 launchVector = new Vector2(
                    -10000000000f*(slingshotCenter.x - (birdBody.getPosition().x)),
                    -10000000000f*(slingshotCenter.y - (birdBody.getPosition().y))
                );

                // Enhanced launch force calculation
                float stretchDistance = launchVector.len();
                float launchForce = Math.min(stretchDistance * LAUNCH_FORCE_MULTIPLIER*10000000000000000000000000000000000000f, 20000000000000f);

                // Normalize and scale the vector with angle preservation
                launchVector.nor().scl(launchForce);

                System.out.println("Launch Vector: " + launchVector);
                System.out.println("Launch Force: " + launchForce);

                // Apply impulse with world center
                birdBody.applyLinearImpulse(launchVector, birdBody.getWorldCenter(), true);

                // Enhanced flight characteristics
                birdBody.setBullet(true);
               // birdBody.setLinearDamping(0.5f);

                // Reset launch state
                isDragging = false;
                birdLaunched = true;



                // Check game progression



                if (birdLaunched) {
                    checkBirdStop = true;
                    stationaryTime = 0f;
                }
            }
        }
    }

    private void checkBirdVelocity() {
        if (currentBird != null && birdLaunched) {

            System.out.println("Bird velocity low, attempting to save state...");

            // Save the game state before switching birds

            Vector2 velocity = currentBird.getBody().getLinearVelocity();


            // Check if velocity is very close to zero
            if (Math.abs(velocity.x) < 1f && Math.abs(velocity.y) < 1f) {
                // Save the game state before switching birds

                //LevelState currentState = new LevelState(this);
                //currentState.saveToFile("java/Main_menu_screen/level1_save.ser");
                currentBird.getBody().setUserData("remove");

                // Remove current bird
                birds.remove(currentBird);

                // Reset launch states
                birdLaunched = false;
                //LevelState currentState = new LevelState(this);
                //currentState.saveToFile("java/Main_menu_screen/level1_save.ser");
                System.out.println("Bird velocity low, attempting to save state...");
                // Save the game state before switching birds


                // Increment tries
                tries++;
                checkScoreAndProceed();


                // Select next bird if available
                if (!birds.isEmpty()) {
                    currentBird = birds.get(0);
                } else {
                    currentBird = null;
                    // Check game progression if no birds left

                }
                LevelState currentState = new LevelState(this);
                currentState.saveToFile("level1_save.ser");

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
