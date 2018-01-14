package org.hetoh.anet;

import org.junit.Test;

import java.util.List;

public class PointCloudUploadHandlerTest {

    @Test
    public void testCreatePointCloudUploadData() {
        PointCloudUploadData pointCloudUploadData = PointCloudUploadHandler.createPointCloudUploadData(null);
        List<PointData> points = pointCloudUploadData.getPoints();
        assert(points.size() == 10001543);
    }

}
