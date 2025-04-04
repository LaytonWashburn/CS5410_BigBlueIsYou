package ecs.Components;

import edu.usu.graphics.AnimatedSprite;

public class Sprite extends Component{

    public AnimatedSprite animatedSprite;

    public Sprite(){}
    public Sprite(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
    }

}
