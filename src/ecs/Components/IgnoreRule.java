package ecs.Components;

public class IgnoreRule extends Component{
    @Override
    public Component clone() {
        // Perform a shallow clone (since no deep cloning is needed for this class)
        return super.clone();
    }
}