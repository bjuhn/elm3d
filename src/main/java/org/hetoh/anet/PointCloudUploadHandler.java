package org.hetoh.anet;

import ch.hsr.geohash.WGS84Point;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ResourceHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PointCloudUploadHandler extends ResourceHandler {

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        List<UpdateChainResponse> updateChainResponses = uploadPointCloud(request);
        response.getWriter().println("<h1>Hello World</h1>");
        printChainResponses(response.getWriter(), updateChainResponses);
    }

    private void printChainResponses(PrintWriter writer, List<UpdateChainResponse> updateChainResponses) {
        for (UpdateChainResponse updateChainResponse : updateChainResponses) {
            writer.println(updateChainResponse.toString());
        }
    }

    private List<UpdateChainResponse> uploadPointCloud(HttpServletRequest request) {
        PointCloudUploadData data = createPointCloudUploadData(request);
        Map<String, JsonObject> docs = createBlocks(data);

        // need to make these a transaction:
        List<SaveResult> saveResult = saveDocs(docs);
        return updateChain(saveResult);
    }

    private List<UpdateChainResponse> updateChain(List<SaveResult> saveResults) {
        List<UpdateChainResponse> updateChainResponses = new ArrayList<UpdateChainResponse>();
        for (SaveResult saveResult : saveResults) {
            BlockChainUpdated.register(saveResult);
        }
        return updateChainResponses;
    }

    private Map<String, JsonObject> createBlocks(PointCloudUploadData data) {
        Map<String, JsonObject> docs = new HashMap<String, JsonObject>();

        new WGS84Point(47.2342, 15.7465465);
        Gson gson = new Gson();
        for(PointData point : data.getPoints()) {
            docs.put(point.getHash(), gson.toJsonTree(point).getAsJsonObject());
        }
        return docs;
    }

    public static PointCloudUploadData createPointCloudUploadData(HttpServletRequest request) {
        PointCloudUploadData pointCloudUploadData = PointCloudUploadData.fromLas("/Users/bjuhn/Downloads/MastinLake9_001_colorized0.laz");
        return pointCloudUploadData;
    }

    private List<SaveResult> saveDocs(Map<String, JsonObject> docs) {
        List<SaveResult> saveResults = new ArrayList<SaveResult>();
        MongoConnection mongoConnection = new MongoConnection();
        for (JsonObject doc : docs) {
            mongoConnection.setDocument(doc.toString());
        }
        return saveResults;
    }
}