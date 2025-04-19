package undo;

import ecs.Entities.Entity;
import edu.usu.utils.Tuple2;

import java.util.ArrayList;

public class StackFrame {

    // The boolean represents whether the entity was deleted.
    // True = The entity was deleted in this stack frame.
    // False = The entity was only changed.
    private final ArrayList<Tuple2<Entity, Boolean>> entities;

    public StackFrame() {
        this.entities = new ArrayList<>();
    }

    public ArrayList<Tuple2<Entity, Boolean>> getEntities() {
        return this.entities;
    }


    public void addEntityTuple(Tuple2<Entity, Boolean> entityTuple) throws CloneNotSupportedException {
        this.entities.add(new Tuple2<>(entityTuple.item1().clone(), entityTuple.item2()));
    }
}