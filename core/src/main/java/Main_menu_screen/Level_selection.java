package Main_menu_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.Color;
import angry.birds.game.Angry_ghosts;

public class Level_selection implements Screen {
    private Angry_ghosts game;
    private OrthographicCamera camera;
    private Viewport viewport;//gvgygvhvh
    private Stage stage;
    private Skin skin;
    private Texture background;
    private Texture backButtonTexture;

    public Level_selection(final Angry_ghosts game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport, game.batch);

        // Load the background image
        background = new Texture(Gdx.files.internal("spooky_bg.jpg"));

        // Load the back button image
        backButtonTexture = new Texture(Gdx.files.internal("left-arrow.png"));

        // Load the skin (you need to have a skin file in your assets)
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        // Hello This is Sanchit
        // Create custom button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        buttonStyle.font = skin.getFont("default-font");
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.up = skin.newDrawable("white", new Color(0.2f, 0.2f, 0.8f, 1));
        buttonStyle.down = skin.newDrawable("white", new Color(0.1f, 0.1f, 0.6f, 1));
        buttonStyle.over = skin.newDrawable("white", new Color(0.3f, 0.3f, 0.9f, 1));

        // Create the table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Add level buttons
        for (int i = 1; i <= 10; i++) {
            final int level = i;
            TextButton levelButton = new TextButton("Level " + i, buttonStyle);
            levelButton.getLabel().setFontScale(1.5f);

            levelButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    startLevel(level);
                }
            });

            table.add(levelButton).pad(10).width(200).height(60);
            if (i % 2 == 0) {
                table.row();
            }
        }

        // Add back button as a smaller image
        float scaleFactor = 0.5f; // Adjust this value to make the button smaller or larger
        TextureRegion backButtonRegion = new TextureRegion(backButtonTexture);
        TextureRegionDrawable backButtonDrawable = new TextureRegionDrawable(backButtonRegion);
        backButtonDrawable.setMinWidth(backButtonTexture.getWidth() * scaleFactor);
        backButtonDrawable.setMinHeight(backButtonTexture.getHeight() * scaleFactor);

        ImageButton backButton = new ImageButton(backButtonDrawable);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new main_menu(game));
            }
        });

        // Position the back button in the top left corner
        backButton.setPosition(10, Gdx.graphics.getHeight() - backButton.getHeight() - 10);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    private void startLevel(int level) {

            switch (level) {
                case 1:
                    game.setScreen(new Story_screen(game));
                    break;
                // Add cases for other levels as you implement them
                default:
                    Gdx.app.log("LevelSelectionScreen", "Starting level " + level);
                    // For example: game.setScreen(new GameScreen(game, level));
                    break;
            }

        // For example: game.setScreen(new GameScreen(game, level));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
        backButtonTexture.dispose();
    }
}
