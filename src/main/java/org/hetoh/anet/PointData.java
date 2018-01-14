package org.hetoh.anet;

public class PointData {

    private final char blue;
    private final char red;
    private final int y;
    private final int x;
    private final int z;
    private final char green;


    public PointData(int x, int y, int z, char red, char blue, char green) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public String getHash() {
        int result = 373; // Constant can vary, but should be prime
        result = 37 * result + this.x;
        result = 37 * result + this.y;
        return Integer.toString(result);
    }
}
