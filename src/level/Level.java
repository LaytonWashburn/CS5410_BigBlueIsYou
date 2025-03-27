package level;

public class Level {
    String name;
    int width, height;
    char[][] group1;
    char[][] group2;

    public Level(String name, int width, int height, char[][] group1, char[][] group2) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.group1 = group1;
        this.group2 = group2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public char[][] getGroup1() {
        return group1;
    }

    public char[][] getGroup2() {
        return group2;
    }

    @Override
    public String toString() {
        return "Level: " + name + " (" + width + "x" + height + ")";
    }
}