package org.hetoh.anet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class PointCloudUploadHandlerTest {

    @Test
    public void testCreatePointCloudUploadData() throws Exception {
        PointCloudUploadHandler pointCloudUploadHandler = new PointCloudUploadHandler();
        Map<String, List<PointData>> updateChainResponses = pointCloudUploadHandler.uploadPointCloud(null);


        MongoConnection connection = new MongoConnection();
        JsonObject document = connection.getDocumentById("0:0:0", new Gson());
        assert(document.get("_id").getAsString() == "0:0:0");
//        PointCloudUploadData pointCloudUploadData = PointCloudUploadHandler.createPointCloudUploadData(null);
//        List<PointData> points = pointCloudUploadData.getPoints();
//        assert(points.size() == 10001543);
//        Map<String, List<PointData>> blocks = PointCloudUploadHandler.createBlocks(pointCloudUploadData);
//        assert(blocks.keySet().size() == 511);
    }

}
