package ecs.Components;

import utils.Direction;
import java.util.HashMap;
import java.util.Map;

public class KeyboardControlled extends Component {
    public Map<Integer, Direction> keys;
    public Map<Direction, Integer> lookup;
    public final HashMap<Direction, Boolean> keysPressed = new HashMap<>();

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

//    @Override
//    public KeyboardControlled clone() throws CloneNotSupportedException {
//        KeyboardControlled clone = (KeyboardControlled) super.clone();
//        // Create deep copies of all maps
//        clone.keys = new HashMap<>(this.keys);
//        clone.lookup = new HashMap<>(this.lookup);
//        // Clear and rebuild keysPressed since it's final
//        clone.keysPressed.clear();
//        clone.keysPressed.putAll(this.keysPressed);
//
//        clone.enabled = this.enabled;
//        return clone;
//    }
}