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

    public TextType getTextType(){
        return this.type;
    }

    @Override
    public Component clone() {
        // Perform a shallow clone of the Text object

        // No need to deep clone 'type' since it is an enum (immutable)
        return super.clone();
    }
}
