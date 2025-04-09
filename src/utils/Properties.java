package utils;

public enum Properties {
    PUSHABLE(0b00000001),
    MOVE(0b00000010),
    WIN(0b00000100),
    KILL(0b00001000),
    STOP(0b00010000),
    YOU(0b00100000),
    SINK(0b01000000);

    final int value;

    Properties(int i) {
        this.value = i;
    }
}
