/*
 * Class: Render Particle System Class
 * Date Started: 04-02-2025
 * Date Finished: TBH
 * Description:
 *   - Renders the state of the, well, uh, particle system.
 *      Particle system is an unfortunate name, as it collides with the ECS System name.
 *      While your code will have a particle system, it will not be derived from the ECS System class.
 *      Instead, it will have an interface that the other game code (perhaps the Movement system above)
 *      will call to create effects.
 *      This particle system will have an update method just like other systems, but it doesn't track any entities.
 *
 */

package ecs.Systems;

import ecs.Entities.Entity;
import edu.usu.utils.Tuple2;

import java.util.ArrayList;

public class RenderParticleSystem extends System{
    @Override
    public ArrayList<Tuple2<Entity, Boolean>> update(double elapsedTime) {
        return new ArrayList<>();
    }
}
