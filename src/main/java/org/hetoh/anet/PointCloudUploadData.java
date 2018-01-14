package org.hetoh.anet;


import com.github.mreutegg.laszip4j.LASPoint;
import com.github.mreutegg.laszip4j.LASReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class PointCloudUploadData {

    private List<PointData> points = new ArrayList<PointData>();

    public static PointCloudUploadData fromLas(String filename) {

        PointCloudUploadData pointCloudUploadData = new PointCloudUploadData();

        LASReader reader = new LASReader(new File(filename));
        for (LASPoint p : reader.getPoints()) {
            PointData pointData = new PointData(p.getX(), p.getY(), p.getZ(), p.getRed(), p.getBlue(), p.getGreen());
            pointCloudUploadData.addPoint(pointData);
        }
        return pointCloudUploadData;
    }

    public void addPoint(PointData pointData) {
        this.points.add(pointData);
    }

    public List<PointData> getPoints() {
        return points;
    }
}

