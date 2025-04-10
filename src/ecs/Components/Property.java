package ecs.Components;

import utils.Properties;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class Property extends Component{

    private Set<Properties> properties;

    public Property(Properties... properties) {
        this.properties = EnumSet.noneOf(Properties.class);
        Collections.addAll(this.properties, properties);
    }

    public Set<Properties> getProperties() {
        return properties;
    }

    @Override
    public Property clone() throws CloneNotSupportedException {
        Property clone = (Property) super.clone();
        // Create a new EnumSet with the same properties
        clone.properties = EnumSet.copyOf(this.properties);
        return clone;
    }
}
