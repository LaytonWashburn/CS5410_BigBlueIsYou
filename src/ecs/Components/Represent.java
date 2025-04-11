package ecs.Components;

import utils.NounType;

public class Represent extends Component{

    NounType noun;

    public Represent(NounType noun) {
        this.noun = noun;
    }

    public NounType getNounType() {
        return this.noun;
    }

    public void setNounType(NounType noun) {
        this.noun = noun;
    }

    @Override
    public Component clone() {
        // Shallow clone the Represent object

        // Return the cloned instance
        return (Represent) super.clone();
    }
}
