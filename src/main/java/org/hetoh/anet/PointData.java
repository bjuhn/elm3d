package org.hetoh.anet;

public class PointData {

    private final int blue;
    private final int red;
    private final int y;
    private final int x;
    private final int z;
    private final int green;

    public PointData(int x, int y, int z, char red, char blue, char green) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.red = Character.getNumericValue(red);
        this.blue = Character.getNumericValue(blue);
        this.green = Character.getNumericValue(green);
    }

    public String getHash() {
        String a = Integer.toString(this.x / 10000);
        String b = Integer.toString(this.y / 10000);
        String c = Integer.toString(this.z / 10000);
        String hash = a + ":" + b + ":" + c;
        return hash;
    }

}
