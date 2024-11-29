package Main_menu_screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Pig {
    protected int num_of_hits = 0;
    protected Body body;
    protected Texture texture;
    protected int MAX_HITS;
    protected float radius = 45f;

    public void incrementHits() {
        num_of_hits++;
    }
    public int getHits(){
        return this.num_of_hits;
    }

    public boolean isDestroyed() {
        return num_of_hits >= MAX_HITS;
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }

    public abstract FixtureDef getFixtureDef();

    public float getRadius(){
        return radius;
    }
}
