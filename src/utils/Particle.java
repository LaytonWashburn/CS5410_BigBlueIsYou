package utils;

import edu.usu.graphics.Color;
import org.joml.Vector2f;
import edu.usu.graphics.Rectangle;

public class Particle {
    public Particle(Vector2f center, Vector2f direction, float speed, Vector2f size, double lifetime) {
        this.name = nextName++;
        this.center = center;
        this.direction = direction;
        this.speed = speed;
        this.size = size;
        this.area = new Rectangle(center.x - size.x / 2, center.y - size.y / 2, size.x, size.y, 1f);
        this.lifetime = lifetime;

        this.rotation = 0;
    }

    public Particle(Vector2f center, Vector2f direction, float speed, Vector2f size, double lifetime, Color color) {
        this.name = nextName++;
        this.center = center;
        this.direction = direction;
        this.speed = speed;
        this.size = size;
        this.area = new Rectangle(center.x - size.x / 2, center.y - size.y / 2, size.x, size.y, 1f);
        this.lifetime = lifetime;
        this.color = color;

        this.rotation = 0;
    }

    public boolean update(double elapsedTime) {
        // Update how long it has been alive
        alive += elapsedTime;

        // Update its center
        center.x += (float) (elapsedTime * speed * direction.x);
        center.y += (float) (elapsedTime * speed * direction.y);
        area.left += (float) (elapsedTime * speed * direction.x);
        area.top += (float) (elapsedTime * speed * direction.y);

        // Rotate proportional to its speed
        rotation += (speed / 0.5f);

        // Return true if this particle is still alive
        return alive < lifetime;
    }

    public long name;
    public Vector2f size;
    public Vector2f center;
    public Rectangle area;
    public float rotation;
    private final Vector2f direction;
    private final float speed;
    private final double lifetime;
    private double alive = 0;
    private static long nextName = 0;

    public Color color = Color.WHITE;
}
