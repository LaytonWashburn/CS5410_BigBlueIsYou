package ecs.Components;

public class AnimatedSprite extends Component{

    public edu.usu.graphics.AnimatedSprite animatedSprite;

    public AnimatedSprite(edu.usu.graphics.AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
    }

    @Override
    public Component clone() {
        // Shallow clone this component (i.e., copy the reference to animatedSprite)
        AnimatedSprite clone = (AnimatedSprite) super.clone();

        // Perform deep copy of animatedSprite if it is mutable and doesn't support cloning
        // (Assuming that the AnimatedSprite class provides a copy constructor or a clone method)
        if (this.animatedSprite != null) {
            clone.animatedSprite =this.animatedSprite; // Assuming a copy constructor
        }

        return clone;
    }

}
