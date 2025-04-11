package ecs.Components;

public class Action extends Component implements Cloneable{

    utils.Action action;

    public Action(utils.Action action) {
        this.action = action;
    }

    public utils.Action getAction() {
        return action;
    }

    @Override
    public Component clone() {
        // TODO: copy mutable state here, so the clone can't change the internals of the original
        return super.clone();
    }
}
