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

    public NounType getNounType(NounType type){
        return this.type;
    }
}
