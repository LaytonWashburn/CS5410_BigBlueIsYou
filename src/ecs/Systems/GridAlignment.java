package ecs.Systems;

import ecs.Components.Position;
import ecs.Entities.Entity;

import java.util.ArrayList;

public class GridAlignment extends System{

    public Entity[][] grid;

    public GridAlignment(Entity[][] grid) {
        super(ecs.Components.Text.class);
        this.grid = grid;
    }

    @Override
    public ArrayList<Entity> update(double elapsedTime) {

        // Reset to avoid duplicates
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[0].length; col++) {
                grid[row][col] = null;
            }
        }

        Position position;
        for(Entity entity : entities.values()) {
            position = entity.get(ecs.Components.Position.class);
            this.grid[position.i][position.j] = entity;
        }
        return new ArrayList<>();
    }
}
