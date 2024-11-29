package Main_menu_screen;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class LevelState3 implements Serializable {
    private static final long serialVersionUID = 1L;

    public static class SerializableBird implements Serializable {
        public Vector2 position;
        public boolean isDestroyed;
        public int spriteIndex;

        public SerializableBird(Bird bird, int spriteIndex) {
            this.position = bird.getBody().getPosition();
            this.isDestroyed = "remove".equals(bird.getBody().getUserData());
            this.spriteIndex = spriteIndex;
        }
    }

    public static class SerializablePig implements Serializable {
        public Vector2 position;
        public boolean isDestroyed;
        public int type;

        public SerializablePig(Pig pig) {
            this.position = pig.getBody().getPosition();
            this.isDestroyed = "remove".equals(pig.getBody().getUserData());
            this.type = (pig instanceof Pig_1) ? 1 :
                (pig instanceof Pig_2) ? 2 :
                    (pig instanceof Pig_3) ? 3:0;
        }
    }

    public static class SerializableBlock implements Serializable {
        public Vector2 position;
        public boolean isDestroyed;
        public String blockType;

        public SerializableBlock(Block block) {
            this.position = block.getBody().getPosition();
            this.isDestroyed = block.getBody().getUserData() == null;
            this.blockType = block.getTexture().toString();
        }
    }

    // Game state variables
    public List<SerializableBird> birds;
    public List<SerializablePig> pigs;
    public List<SerializableBlock> blocks;
    //public int score;
    public int tries;
    public int score;

    public LevelState3(level_3 level) {
        // Capture only non-destroyed birds
        birds = new ArrayList<>();
        for (int i = 0; i < level.birds.size(); i++) {
            Bird bird = level.birds.get(i);
            if (!"remove".equals(bird.getBody().getUserData())) {
                // Find the correct sprite index by comparing the bird's texture
                int spriteIndex = -1;
                for (int j = 0; j < level.ghostSprites.length; j++) {
                    if (bird.getTexture() == level.ghostSprites[j]) {
                        spriteIndex = j;
                        break;
                    }
                }

                if (spriteIndex != -1) {
                    birds.add(new SerializableBird(bird, spriteIndex));
                }
            }
        }

        // Capture only non-destroyed pigs
        pigs = new ArrayList<>();
        for (Pig pig : level.pigs) {

            if (!"remove".equals(pig.getBody().getUserData())) {
                pigs.add(new SerializablePig(pig));
            }
        }

        blocks = new ArrayList<>();
        for (Block block : level.blocks) {
            blocks.add(new SerializableBlock(block));
        }

        score = level.getScore();
        //System.out.println("GGGET LEVEL SCORE GIVES"+score);
        tries = level.tries;
    }

    // Save method
    public void saveToFile(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
            System.out.println("Game state saved successfully to " + filename);
        }
        catch (IOException e) {
            System.err.println("Error saving game state: " + e.getMessage());
        }
    }

    // Load method
    public static LevelState3 loadFromFile(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (LevelState3) in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game state: " + e.getMessage());
            return null;
        }
    }

    //Method to restore game state
    public void restoreState(level_3 level) {
        // Clear existing game objects
        level.birds.clear();
        level.pigs.clear();
        level.blocks.clear();

        // Restore birds
        for(SerializableBird serializableBird : birds){
            // Only restore birds that were not destroyed when saved
            if (!serializableBird.isDestroyed) {
                Bird bird = new Bird(level.gameWorld.getWorld(),
                    level.ghostSprites[serializableBird.spriteIndex],
                    serializableBird.position,
                    40);
                level.birds.add(bird);
            }
        }

        // Restore pigs
        for (SerializablePig serializablePig : pigs) {
            // Only restore pigs that were not destroyed when saved
            if (!serializablePig.isDestroyed) {
                Pig pig;
                switch (serializablePig.type) {
                    case 1:
                        pig = new Pig_1(level.gameWorld.getWorld(),
                            serializablePig.position.x,
                            serializablePig.position.y);
                        break;
                    case 2:
                        pig = new Pig_2(level.gameWorld.getWorld(),
                            serializablePig.position.x,
                            serializablePig.position.y);
                        break;
                    case 3:
                        pig = new Pig_3(level.gameWorld.getWorld(),
                            serializablePig.position.x,
                            serializablePig.position.y);
                        break;
                    default:
                        continue;
                }

                level.pigs.add(pig);
            }
        }

        // Restore blocks
        for (SerializableBlock serializableBlock : blocks) {
            Texture blockTexture = new Texture(serializableBlock.blockType);
            Block block;
            if (serializableBlock.blockType.contains("ground.jpg")) {
                block = new Block(level.gameWorld.getWorld(),
                    blockTexture,
                    serializableBlock.position,
                    2000,  //specific ground block width
                    200);  //specific ground block height
            }
            else{
                block = new Block(level.gameWorld.getWorld(),
                    blockTexture,
                    serializableBlock.position,
                    120,
                    100);
            }
            level.blocks.add(block);
        }

        // Restore additional game state
        level.tries = tries;
        level.setScore(score);

    }

}
