package ecs.Components;

import edu.usu.graphics.Texture;

public class Appearance extends Component{

    Texture texture;

    public Appearance(Texture texture){
        this.texture = texture;
    }

    public Texture getTexture(){
        return this.texture;
    }

}
