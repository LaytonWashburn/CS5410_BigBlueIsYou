package ecs.Components;

import utils.Direction;
import java.util.HashMap;
import java.util.Map;

public class KeyboardControlled extends Component {
    public Map<Integer, Direction> keys;
    public Map<Direction, Integer> lookup;
    public HashMap<Direction, Boolean> keysPressed = new HashMap<>();

    public boolean enabled;

    public KeyboardControlled(Map<Integer, Direction> keys) {
        this.keys = keys;

        // Build the action to key lookup based on the key to action inf
        lookup = new HashMap<>();
        for (var mapping : keys.entrySet()) {
            lookup.put(mapping.getValue(), mapping.getKey());
            keysPressed.put(mapping.getValue(), false);
        }

        enabled = true;
    }

    @Override
    public Component clone() {
        // Shallow clone the KeyboardControlled object
        KeyboardControlled cloned = (KeyboardControlled) super.clone();

        // Deep clone the keys, lookup, and keysPressed maps
        cloned.keys = new HashMap<>(this.keys); // Create a new HashMap with the same entries
        cloned.lookup = new HashMap<>(this.lookup); // Create a new HashMap for lookup
        cloned.keysPressed = new HashMap<>(this.keysPressed); // Deep clone the keysPressed map

        // Return the cloned instance
        return cloned;
    }
}