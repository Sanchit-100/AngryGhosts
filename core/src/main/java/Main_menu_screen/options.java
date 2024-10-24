package Main_menu_screen;

import angry.birds.game.Angry_ghosts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class options implements Screen {

    private final Angry_ghosts ag;
    private OrthographicCamera camera;
    private Viewport viewport;

    // Textures for the images
    private Texture leftImage;
    private Texture rightImage;
    private Texture starsImage;
    private Texture bestScoreImage;

    // Font for the text
    private BitmapFont font;

    // Rectangle renderer
    private ShapeRenderer shapeRenderer;

    // Layouts
    private GlyphLayout textLayout;

    // Constants for layout
    private static final float RECTANGLE_PADDING = 10f;

    public options(Angry_ghosts ag) {
        this.ag = ag;

        // Initialize the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        // Load images
        leftImage = new Texture("left_image.png"); // Your left image file
        rightImage = new Texture("right_image.png"); // Your right text image file
        starsImage = new Texture("stars_image.png"); // Image with three stars
        bestScoreImage = new Texture("best_score_image.png"); // Side image for Best Score

        // Initialize font and layout
        font = new BitmapFont(); // You can load a custom .fnt if needed
        textLayout = new GlyphLayout();

        // Initialize shape renderer for the rectangle
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();
        ag.batch.setProjectionMatrix(camera.combined);

        ag.batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Calculate the positions and sizes
        float boxWidth = worldWidth * 0.25f;
        float boxHeight = boxWidth; // Square boxes for images
        float padding = RECTANGLE_PADDING;
        float xLeft = padding;
        float yTop = worldHeight - boxHeight - padding;
        float xRight = worldWidth - boxWidth - padding;
        float yStars = yTop - boxHeight - padding;
        float yBestScore = yStars - boxHeight - padding;

        // Draw the images and text
        ag.batch.draw(leftImage, xLeft, yTop, boxWidth, boxHeight); // Left image
        ag.batch.draw(rightImage, xRight, yTop, boxWidth, boxHeight); // Right image
        ag.batch.draw(starsImage, (worldWidth - boxWidth) / 2f, yStars, boxWidth, boxHeight); // Stars image

        // Draw the text "Best Score" inside a rectangle
        String bestScoreText = "Best Score";
        textLayout.setText(font, bestScoreText);
        float textWidth = textLayout.width;
        float textHeight = textLayout.height;

        // Center the text rectangle
        float xText = (worldWidth - boxWidth) / 2f;
        float yText = yBestScore;

        // Draw the rectangle behind the text
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1f); // Gray rectangle
        shapeRenderer.rect(xText - padding, yText - padding, boxWidth, textHeight + 2 * padding);
        shapeRenderer.end();

        // Draw the "Best Score" text
        font.draw(ag.batch, textLayout, xText, yText + textHeight);

        // Draw the image next to the "Best Score" text
        ag.batch.draw(bestScoreImage, xText + boxWidth + padding, yBestScore, boxWidth / 2f, boxHeight / 2f); // Adjust size as needed

        ag.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        leftImage.dispose();
        rightImage.dispose();
        starsImage.dispose();
        bestScoreImage.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
