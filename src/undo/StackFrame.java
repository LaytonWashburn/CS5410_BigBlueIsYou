package undo;

import com.sun.tools.jconsole.JConsoleContext;
import ecs.Components.BigBlue;
import ecs.Components.KeyboardControlled;
import ecs.Components.Position;
import ecs.Components.Property;
import ecs.Entities.Entity;
import edu.usu.utils.Tuple2;

import java.util.ArrayList;

public class StackFrame {

    // The boolean represents whether the entity was deleted.
    // True = The entity was deleted in this stack frame.
    // False = The entity was only changed.
    private ArrayList<Tuple2<Entity, Boolean>> entities;

    public StackFrame() throws CloneNotSupportedException {
        this.entities = new ArrayList<>();
    }

    public ArrayList<Tuple2<Entity, Boolean>> getEntities() {
        return this.entities;
    }

    public void printEntities() {
        for (Tuple2<Entity, Boolean> entity : this.entities) {
            System.out.println(entity.item1());
        }
    }

    public void addEntityTuple(Tuple2<Entity, Boolean> entityTuple) throws CloneNotSupportedException {
        this.entities.add(new Tuple2<>(entityTuple.item1().clone(), entityTuple.item2()));
    }

    public Tuple2<Entity, Boolean> getCorrespondingTuple(Tuple2<Entity, Boolean> givenTuple) {
        Tuple2<Entity, Boolean> correspondingTuple = new Tuple2<>(null, false);

        for (Tuple2<Entity, Boolean> entityTuple : entities) {
            if (entityTuple.item1().getId() == givenTuple.item1().getId()) {
                correspondingTuple = entityTuple;

//                System.out.println("Popped entity: " + entityTuple.item1().get(Position.class).i);
//                System.out.println("Corresponding entity: " + correspondingTuple.item1().get(Position.class).i);
            }
        }

        return correspondingTuple;
    }

    public Entity getBigBlue() {
        for (Tuple2<Entity, Boolean> entityTuple : entities) {
            if (entityTuple.item1().contains(BigBlue.class)) {
                return entityTuple.item1();
            }
        }
        return null;
    }

    public void print() {
        System.out.println(entities.size());
    }
}
