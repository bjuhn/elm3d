package org.hetoh.anet;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;

public class PointCloudGetHandlerTest {

    @Test
    public void testCreatePointCloudUploadData() throws Exception {
        PointCloudGetHandler pointCloudGetHandler = new PointCloudGetHandler();
        MongoConnection connection = new MongoConnection();
        JsonObject obj = connection.getDocumentById("0:0:0", new GsonBuilder().setPrettyPrinting().create());
        JsonElement points = obj.get("points");
        System.out.println(points);

    }
}
