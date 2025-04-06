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

}
