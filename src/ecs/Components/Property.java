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

    public void removeProperty(Properties property) {
        properties.remove(property);
    }

    @Override
    public Component clone() {
        // Shallow clone the Property object
        Property cloned = (Property) super.clone();

        // Create a new EnumSet for the cloned object and copy the properties over
        cloned.properties = EnumSet.copyOf(this.properties);

        // Return the cloned instance
        return cloned;
    }
}