package ecs.Components;

import utils.TextType;

public class Text extends Component{

    TextType type;

    public Text(TextType type){
        this.type = type;
    }

    public void setTextType(TextType type){
        this.type = type;
    }

    public TextType getTextType(TextType type){
        return this.type;
    }
}
