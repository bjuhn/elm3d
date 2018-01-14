package org.hetoh.anet;

public class PointData {

    private final int b;
    private final int r;
    private final int y;
    private final int x;
    private final int z;
    private final int g;

    public PointData(int x, int y, int z, char r, char g, char b) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = (int)r & 0xFF;
        this.g = (int)g & 0xFF;
        this.b = (int)b & 0xFF;
    }

    public String getHash() {
        String a = Integer.toString(this.x / 10000);
        String b = Integer.toString(this.y / 10000);
        String c = Integer.toString(this.z / 10000);
        String hash = a + ":" + b + ":" + c;
        return hash;
    }

}
