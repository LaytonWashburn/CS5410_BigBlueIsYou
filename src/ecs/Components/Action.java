package ecs.Components;

public class Action extends Component{

    utils.Action action;

    public Action(utils.Action action) {
        this.action = action;
    }

    public utils.Action getAction() {
        return action;
    }
}
