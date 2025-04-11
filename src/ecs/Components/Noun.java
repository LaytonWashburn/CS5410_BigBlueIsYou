package ecs.Components;

import utils.NounType;

public class Noun extends Component{

    NounType type;

    public Noun(NounType type){
        this.type = type;
    }

    public void setNounType(NounType type){
        this.type = type;
    }

    public NounType getNounType(){
        return this.type;
    }

    @Override
    public Noun clone() {
        // Shallow clone the Noun object
        Noun cloned = (Noun) super.clone();

        // No deep clone needed for the enum, just copy the reference of the 'type' enum
        cloned.type = this.type;

        // Return the cloned instance
        return cloned;
    }
}
