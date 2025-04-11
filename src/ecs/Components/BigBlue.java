package ecs.Components;

public class BigBlue extends Component {
    @Override
    public Component clone() {
        // Perform a shallow clone, ensuring the correct type is returned
        return  super.clone(); // Cast to BigBlue since we're in BigBlue class
    }
}
