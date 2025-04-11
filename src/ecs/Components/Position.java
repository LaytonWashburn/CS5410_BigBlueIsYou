package ecs.Components;

public class Position extends Component{

    public int i;
    public int j;

    public Position(int i, int j){
        this.i = i;
        this.j = j;
    }

    @Override
    public Component clone() {
        //Component component = super.clone();
        // Perform a deep clone by creating a new Position object
        return new Position(this.i, this.j);
    }
}