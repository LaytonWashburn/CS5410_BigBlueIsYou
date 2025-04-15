package utils;

import ecs.Components.Position;
import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Texture;
import level.Level;
import org.joml.Vector2f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ParticleSystem {
    private final HashMap<Long, Particle> particles = new HashMap<>();
    private final MyRandom random = new MyRandom();

    private Vector2f center;

    Graphics2D graphics;
    Texture texParticles;

    public ParticleSystem(Texture texParticle, Graphics2D graphics2D) {


        this.graphics = graphics2D;
        this.texParticles = texParticle;
    }

    public void update(double gameTime) {

        //System.out.println("In the particle system update");

        // Update existing particles
        List<Long> removeMe = new ArrayList<>();

        // Iterate through particle
        for (Particle p : particles.values()) {
            if (!p.update(gameTime)) { // Put the dead particles in the remove list
                removeMe.add(p.name);
            }
        }

        // Remove dead particles
        for (Long key : removeMe) {
            particles.remove(key);
        }

        for(Particle particle : particles.values()){
            graphics.draw(texParticles, particle.area, particle.rotation, particle.center, particle.color);
        }


    }

    public Collection<Particle> getParticles() {
        return this.particles.values();
    }

    private Particle createSparkle(float x, float y) {
        float sizeMean = 0.025f;
        float sizeStdDev = 0.0f;
        float speedMean = 0.0f;
        float speedStdDev = 0.0f;
        float lifetimeMean = 0.7f;
        float lifetimeStdDev = 0.1f;


        float size = (float) this.random.nextGaussian(sizeMean, sizeStdDev);
        var p = new Particle(
                new Vector2f(x, y),
                this.random.nextCircleVector(),
                0.0f,// (float) this.random.nextGaussian(this.speedMean, this.speedStdDev),
                new Vector2f(size, size),
                this.random.nextGaussian(lifetimeMean, lifetimeStdDev));

        return p;
    }

    private Particle createDeathParticle(float x, float y, Color color) {
        float sizeMean = 0.015f;
        float sizeStdDev = 0.005f;
        float speedMean = 0.1f;
        float speedStdDev = 0.05f;
        float lifetimeMean = 0.5f;
        float lifetimeStdDev = 0.1f;


        float size = (float) this.random.nextGaussian(sizeMean, sizeStdDev);
        var p = new Particle(
                new Vector2f(x, y),
                this.random.nextCircleVector(),
                (float) this.random.nextGaussian(speedMean, speedStdDev),
                new Vector2f(size, size),
                this.random.nextGaussian(lifetimeMean, lifetimeStdDev),
                color);

        return p;
    }

    private Particle createWinParticle(float x, float y) {
        float sizeMean = 0.025f;
        float sizeStdDev = 0.015f;
        float speedMean = 0.5f;
        float speedStdDev = 0.03f;
        float lifetimeMean = 0.7f;
        float lifetimeStdDev = 0.2f;


        float size = (float) this.random.nextGaussian(sizeMean, sizeStdDev);
        var p = new Particle(
                new Vector2f(x, y),
                this.random.nextCircleVector(),
                (float) this.random.nextGaussian(speedMean, speedStdDev),
                new Vector2f(size, size),
                this.random.nextGaussian(lifetimeMean, lifetimeStdDev));

        return p;
    }

    /**
     * Method: Player Is You
     * Description: Particles around the edges of the affected entities
     */
    public void playerIsYou(Position position, Level level){

        float topLeftWorldX = -EntityConstants.rectSize * (level.getWidth() / 2f);
        float topLeftWorldY = -EntityConstants.rectSize * (level.getHeight() / 2f);

        float entityWorldX = topLeftWorldX + position.j * EntityConstants.rectSize;
        float entityWorldY = topLeftWorldY + position.i * EntityConstants.rectSize;

        Particle p;
        // Create Top
        for(int i = 0; i < 5; i++){
            p = createSparkle(entityWorldX + (i * EntityConstants.rectSize / 5), entityWorldY);
            particles.put(p.name, p);
        }

        // Create Bottom
        for(int i = 0; i < 6; i++){ // I just made this 6 because the bottom row was missing a particle for some reason?
            p = createSparkle(entityWorldX + (i * EntityConstants.rectSize / 5), entityWorldY + EntityConstants.rectSize);
            particles.put(p.name, p);
        }

        // Create Left
        for(int i = 0; i < 5; i++){
            p = createSparkle(entityWorldX, entityWorldY  + (i * EntityConstants.rectSize / 5));
            particles.put(p.name, p);
        }

        // Create Right
        for(int i = 0; i < 5; i++){
            p = createSparkle(entityWorldX + EntityConstants.rectSize, entityWorldY  + (i * EntityConstants.rectSize / 5));
            particles.put(p.name, p);
        }

    }

    public void objectIsWin(Position position, Level level) {
        float topLeftWorldX = -EntityConstants.rectSize * (level.getWidth() / 2f);
        float topLeftWorldY = -EntityConstants.rectSize * (level.getHeight() / 2f);

        float entityWorldX = topLeftWorldX + position.j * EntityConstants.rectSize;
        float entityWorldY = topLeftWorldY + position.i * EntityConstants.rectSize;

        Particle p;
        // Create Top
        for(int i = 0; i < 5; i++){
            p = createSparkle(entityWorldX + (i * EntityConstants.rectSize / 5), entityWorldY);
            particles.put(p.name, p);
        }

        // Create Bottom
        for(int i = 0; i < 6; i++){
            p = createSparkle(entityWorldX + (i * EntityConstants.rectSize / 5), entityWorldY + EntityConstants.rectSize);
            particles.put(p.name, p);
        }

        // Create Left
        for(int i = 0; i < 5; i++){
            p = createSparkle(entityWorldX, entityWorldY  + (i * EntityConstants.rectSize / 5));
            particles.put(p.name, p);
        }

        // Create Right
        for(int i = 0; i < 5; i++){
            p = createSparkle(entityWorldX + EntityConstants.rectSize, entityWorldY  + (i * EntityConstants.rectSize / 5));
            particles.put(p.name, p);
        }
    }

    public void playerDeath(Position position, Level level){

        float topLeftWorldX = -EntityConstants.rectSize * (level.getWidth() / 2f);
        float topLeftWorldY = -EntityConstants.rectSize * (level.getHeight() / 2f);

        float halfWidth = EntityConstants.rectSize / 2f;

        Particle p;
        for(int i = 0; i < 200; i++){
            p = createDeathParticle(topLeftWorldX + (position.j*2+1) * halfWidth,
                                   topLeftWorldY + (position.i*2+1) * halfWidth,
                                    Color.RED);
            particles.put(p.name, p);
        }
    }


    public void objectDeath(Position position, Level level){

        float topLeftWorldX = -EntityConstants.rectSize * (level.getWidth() / 2f);
        float topLeftWorldY = -EntityConstants.rectSize * (level.getHeight() / 2f);

        float halfWidth = EntityConstants.rectSize / 2f;

        Particle p;
        for(int i = 0; i < 200; i++){
            p = createDeathParticle(topLeftWorldX + (position.j*2+1) * halfWidth,
                                 topLeftWorldY + (position.i*2+1) * halfWidth,
                                    Color.WHITE);
            particles.put(p.name, p);
        }
    }


    public void levelWin(Position position, Level level){

        float topLeftWorldX = -EntityConstants.rectSize * (level.getWidth() / 2f);
        float topLeftWorldY = -EntityConstants.rectSize * (level.getHeight() / 2f);

        float halfWidth = EntityConstants.rectSize / 2f;

        Particle p;
        for(int i = 0; i < 200; i++){
            p = createWinParticle(topLeftWorldX + (position.j*2+1) * halfWidth,
                    topLeftWorldY + (position.i*2+1) * halfWidth);
            particles.put(p.name, p);
        }
    }

}

