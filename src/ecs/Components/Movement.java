package ecs.Components;

import utils.Direction;

public class Movement extends Component{

    public Direction moving;

    public Movement(Direction moving){
        this.moving = moving;
    }
    @Override
    public Component clone() {
        // Shallow clone the Movement object
        Movement cloned = (Movement) super.clone();

        // No deep clone needed for the enum, just copy the reference of the 'moving' enum
        cloned.moving = this.moving;

        // Return the cloned instance
        return cloned;
    }
}