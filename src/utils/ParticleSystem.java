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
    private final float sizeMean;
    private final float sizeStdDev;
    private final float speedMean;
    private final float speedStdDev;
    private final float lifetimeMean;
    private final float lifetimeStdDev;

    Graphics2D graphics;
    Texture texParticles;

    public ParticleSystem(float sizeMean,
                          float sizeStdDev,
                          float speedMean,
                          float speedStdDev,
                          float lifetimeMean,
                          float lifetimeStdDev,
                          Texture texParticle,
                          Graphics2D graphics2D) {
        this.sizeMean = sizeMean;
        this.sizeStdDev = sizeStdDev;
        this.speedMean = speedMean;
        this.speedStdDev = speedStdDev;
        this.lifetimeMean = lifetimeMean;
        this.lifetimeStdDev = lifetimeStdDev;
        this.graphics = graphics2D;
        this.texParticles = texParticle;
    }

    public void update(double gameTime) {

        System.out.println("In the particle system update");

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
            graphics.draw(texParticles, particle.area, particle.rotation, particle.center, Color.WHITE);
        }


    }

//    public void createExplosion(Vector2f center){
//        this.center = center;
//        // Generate some new particles
//        for (int i = 0; i < 1000; i++) {
//            var particle = create();
//            particles.put(particle.name, particle);
//        }
//    }

    public Collection<Particle> getParticles() {
        return this.particles.values();
    }

    private Particle createSparkle(float x, float y) {
        float size = (float) this.random.nextGaussian(this.sizeMean, this.sizeStdDev);
        var p = new Particle(
                new Vector2f(x, y),
                this.random.nextCircleVector(),
                0.0f,// (float) this.random.nextGaussian(this.speedMean, this.speedStdDev),
                new Vector2f(size, size),
                this.random.nextGaussian(this.lifetimeMean, this.lifetimeStdDev));

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

        System.out.println("Sparkles");
        Particle p;
        // Create Top
        for(int i = 0; i < 5; i++){
            p = createSparkle(entityWorldX + (i * EntityConstants.rectSize / 5), entityWorldY);
            particles.put(p.name, p);
        }

        // Create Bottom
        for(int i = 0; i < 5; i++){
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

    public void playerDeath() {

    }

    public void objectDeath() {

    }

    public void objectIsWin(Position position, Level level) {
        float topLeftWorldX = -EntityConstants.rectSize * (level.getWidth() / 2f);
        float topLeftWorldY = -EntityConstants.rectSize * (level.getHeight() / 2f);

        float entityWorldX = topLeftWorldX + position.j * EntityConstants.rectSize;
        float entityWorldY = topLeftWorldY + position.i * EntityConstants.rectSize;

        System.out.println("Sparkles");
        Particle p;
        // Create Top
        for(int i = 0; i < 5; i++){
            p = createSparkle(entityWorldX + (i * EntityConstants.rectSize / 5), entityWorldY);
            particles.put(p.name, p);
        }

        // Create Bottom
        for(int i = 0; i < 5; i++){
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


}



//package utils;
//
//import edu.usu.graphics.Color;
//import edu.usu.graphics.Graphics2D;
//import edu.usu.graphics.Texture;
//import org.joml.Vector2f;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//
//public class ParticleSystem {
//    private final HashMap<Long, Particle> particles = new HashMap<>();
//    private final MyRandom random = new MyRandom();
//
//    private Vector2f center;
//    private final float sizeMean;
//    private final float sizeStdDev;
//    private final float speedMean;
//    private final float speedStdDev;
//    private final float lifetimeMean;
//    private final float lifetimeStdDev;
//
//    Graphics2D graphics;
//    Texture texParticles;
//
//    public ParticleSystem(Vector2f center,
//                          float sizeMean,
//                          float sizeStdDev,
//                          float speedMean,
//                          float speedStdDev,
//                          float lifetimeMean,
//                          float lifetimeStdDev,
//                          Texture texParticle,
//                          Graphics2D graphics2D) {
//        this.center = center;
//        this.sizeMean = sizeMean;
//        this.sizeStdDev = sizeStdDev;
//        this.speedMean = speedMean;
//        this.speedStdDev = speedStdDev;
//        this.lifetimeMean = lifetimeMean;
//        this.lifetimeStdDev = lifetimeStdDev;
//        this.graphics = graphics2D;
//        this.texParticles = texParticle;
//    }
//
//    public void update(double gameTime) {
//        // Update existing particles
//        List<Long> removeMe = new ArrayList<>();
//
//        // Iterate through particle
//        for (Particle p : particles.values()) {
//            if (!p.update(gameTime)) { // Put the dead particles in the remove list
//                removeMe.add(p.name);
//            }
//        }
//
//        // Remove dead particles
//        for (Long key : removeMe) {
//            particles.remove(key);
//        }
//
//        for(Particle particle : particles.values()){
//            graphics.draw(texParticles, particle.area, particle.rotation, particle.center, Color.WHITE);
//        }
//
//
//    }
//
//    public void createExplosion(Vector2f center){
//        this.center = center;
//        // Generate some new particles
//        for (int i = 0; i < 1000; i++) {
//            var particle = create();
//            particles.put(particle.name, particle);
//        }
//    }
//
//    public Collection<Particle> getParticles() {
//        return this.particles.values();
//    }
//
//    private Particle create() {
//        float size = (float) this.random.nextGaussian(this.sizeMean, this.sizeStdDev);
//        var p = new Particle(
//                new Vector2f(this.center.x, this.center.y),
//                this.random.nextCircleVector(),
//                (float) this.random.nextGaussian(this.speedMean, this.speedStdDev),
//                new Vector2f(size, size),
//                this.random.nextGaussian(this.lifetimeMean, this.lifetimeStdDev));
//
//        return p;
//    }
//

//}
