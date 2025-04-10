package ecs.Components;

public class Component implements Cloneable {
    @Override
    public Component clone() {
        try {
            return (Component) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed unexpectedly", e); // Should never happen
        }
    }
}

//package ecs.Components;
//
//public class Component {
//}
