package ecs.Components;

import ecs.Entities.Entity;

public class Component implements Cloneable{

    @Override
    public Component clone() {
        try {
            // Perform a shallow clone (since no deep cloning is needed for this class)
            return (Component) super.clone();  // Ensures the correct type is returned
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed unexpectedly", e);
        }
    }

}