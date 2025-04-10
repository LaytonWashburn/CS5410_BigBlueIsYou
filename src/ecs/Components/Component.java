package ecs.Components;

public abstract class Component implements Cloneable {
    @Override
    public Component clone() throws CloneNotSupportedException {
        return (Component) super.clone();
    }
}
