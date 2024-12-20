package angry.birds.game.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import angry.birds.game.Angry_ghosts;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }


    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Angry_ghosts(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Angry Ghosts");

        // Ensure windowed mode with specific dimensions (e.g., 800x600)
        configuration.setWindowedMode(2000, 1200);

        // Ensure fullscreen mode is disabled
        configuration.setFullscreenMode(null);

        // Optional: Enable VSync
        configuration.useVsync(true);

        // Optional: Set foreground FPS (e.g., 60 FPS)
        configuration.setForegroundFPS(60);

        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        return configuration;
    }
}
