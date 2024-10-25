package Main_menu_screen;

import angry.birds.game.Angry_ghosts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Story_screen implements Screen {
    private final Angry_ghosts game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private BitmapFont font;
    private String introText;
    private float textY;
    private float scrollSpeed = 30f; // Adjust for faster/slower scrolling
    private Texture backButtonTexture;
    private Texture skipButtonTexture;
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;

    public Story_screen(final Angry_ghosts game) {
        this.game = game;
        camera = new OrthographicCamera();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        viewport = new FitViewport(screenWidth, screenHeight, camera);
        stage = new Stage(viewport, game.batch);

        font = new BitmapFont(); // Use default libGDX font
        font.getData().setScale(3.5f); // Adjust scale as needed

        introText = "It was a time of great unrest in the vibrant world of the Angry Birds. The peace that had reigned over Bird Island was shattered" +"\n"+"once more by the nefarious Pig Empire, who had once again stolen the precious eggs of the birds, bringing devastation to their peaceful lives.\n" +
            "\n" +
            "The birds, led by the fierce Red and his brave companions, fought valiantly against the Pig Empire, but they suffered a devastating loss—their" +"\n"+"young ones, the innocent hatchlings, were cruelly taken by the pigs, leaving their parents heartbroken and filled with rage.\n" +
            "The Resurrection\n" +
            "\n" +
            "In a dark corner of the island, a forgotten graveyard lay hidden beneath the ancient trees. Here, the spirits of the fallen hatchlings lingered,"+"\n"+" trapped between worlds, their cries echoing through the night. But on the eve of the Harvest Moon, an ancient magic awoke, \n"+"and the spirits were drawn together. With the combined power of their love for their parents and the desire for vengeance, the hatchlings were resurrected.\n" +
            "\n" +
            "As the dawn broke, the once-innocent hatchlings emerged from the earth, transformed by the energies of the grave. No longer mere chicks, they were now"+"\n"+" fierce avengers, clad in spectral armor and armed with the wisdom of their fallen kin. Their mission was clear: reclaim their stolen eggs and restore peace to Bird Island.";

        textY = -200; // Start the text below the screen

        // Load button textures
        backButtonTexture = new Texture(Gdx.files.internal("left-arrow.png"));
        skipButtonTexture = new Texture(Gdx.files.internal("next.png"));
        //backgroundTexture = new Texture(Gdx.files.internal("dark_img.png"));
        backgroundTexture = new Texture(Gdx.files.internal("dark_img.png"));
        backgroundRegion = new TextureRegion(backgroundTexture);

        // Create and position buttons
        createButtons();

        Gdx.input.setInputProcessor(stage);
    }

    private void createButtons() {
        float buttonScale = 0.2f; // Reduced from 0.5f to make buttons smaller


        TextureRegion backRegion = new TextureRegion(backButtonTexture);
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(backRegion);
        backDrawable.setMinWidth(backButtonTexture.getWidth() * buttonScale);
        backDrawable.setMinHeight(backButtonTexture.getHeight() * buttonScale);

        ImageButton backButton = new ImageButton(backDrawable);
        backButton.setPosition(10, viewport.getWorldHeight() - backButton.getHeight() - 10);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Level_selection(game));
            }
        });


        TextureRegion skipRegion = new TextureRegion(skipButtonTexture);
        TextureRegionDrawable skipDrawable = new TextureRegionDrawable(skipRegion);
        skipDrawable.setMinWidth(skipButtonTexture.getWidth() * buttonScale);
        skipDrawable.setMinHeight(skipButtonTexture.getHeight() * buttonScale);

        ImageButton skipButton = new ImageButton(skipDrawable);
        skipButton.setPosition(viewport.getWorldWidth() - skipButton.getWidth() - 10,
            viewport.getWorldHeight() - skipButton.getHeight() - 10);
        skipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               // game.setScreen(//something here); // Replace with your actual game screen
            }
        });

        stage.addActor(backButton);
        stage.addActor(skipButton);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        textY += scrollSpeed * delta;

        game.batch.begin();

        // Debug output
        Gdx.app.log("Story_screen", "Drawing background: " + viewport.getWorldWidth() + "x" + viewport.getWorldHeight());
        game.batch.draw(backgroundRegion, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        float textWidth = viewport.getWorldWidth() * 0.8f;
        float textX = (viewport.getWorldWidth() - textWidth) / 2;

        GlyphLayout layout = new GlyphLayout(font, introText,
            font.getColor(), textWidth,
            Align.center, true);

        // Debug output
        Gdx.app.log("Story_screen", "Drawing text at: " + textX + ", " + (textY + viewport.getWorldHeight()));
        font.draw(game.batch, layout, textX, textY + viewport.getWorldHeight());

        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (textY > viewport.getWorldHeight() + layout.height) {
            textY = -layout.height;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        backButtonTexture.dispose();
        skipButtonTexture.dispose();
        backgroundTexture.dispose();
    }
}
