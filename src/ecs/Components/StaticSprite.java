package ecs.Components;

import edu.usu.graphics.Texture;

public class StaticSprite extends Component{

    Texture texture;

    public StaticSprite(Texture texture){
        this.texture = texture;
    }

    public Texture getTexture(){
        return this.texture;
    }

    @Override
    public Component clone() {
        // Shallow clone the StaticSprite object
        StaticSprite cloned = (StaticSprite) super.clone();

        // If texture is not null, perform a deep clone of the texture
        if (this.texture != null) {
            cloned.texture = this.texture;
        }

        return cloned;
    }

}