package ecs.Components;

import utils.Direction;

public class Movement extends Component{

    public Direction moving;

    public Movement(Direction moving){
        this.moving = moving;
    }
}
