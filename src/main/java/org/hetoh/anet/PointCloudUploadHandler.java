package org.hetoh.anet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.log.StdErrLog;

class PointCloudUploadHandler extends AbstractHandler {

    private static final Gson gson = new Gson();
    private static final StdErrLog logger = new StdErrLog();
//    private static final Slf4jLog logger = new Slf4jLog(PointCloudUploadHandler.class.getName());

    PointCloudUploadHandler() throws Exception {
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        Map<String, List<PointData>> docs = uploadPointCloud(request);
        response.getWriter().println("<h1>Created the following blocks </h1>");
        printChainResponses(response.getWriter(), docs);
    }

    private void printChainResponses(PrintWriter writer, Map<String, List<PointData>> docs) {
        for (String key : docs.keySet()) {
            writer.println("Hash: " + key.toString() + "    size: " + Integer.toString(docs.get(key).size()) + "</br>");
        }
    }

    public Map<String, List<PointData>> uploadPointCloud(HttpServletRequest request) {
        PointCloudUploadData data = createPointCloudUploadData(request);
        Map<String, List<PointData>> docs = createBlocks(data);
        logger.info("Total blocks: " + docs.keySet().size());
        // need to make these a transaction:
        List<SaveResult> saveResult = saveDocs(docs);
        List<UpdateChainResponse> updateChainResponses = updateChain(saveResult);
        return docs;
    }

    private List<UpdateChainResponse> updateChain(List<SaveResult> saveResults) {
        List<UpdateChainResponse> updateChainResponses = new ArrayList<UpdateChainResponse>();
        ExonumClient exonumClient = new ExonumClient();
        for (SaveResult saveResult : saveResults) {
            String from = "03e657ae71e51be60a45b4bd20bcf79ff52f0c037ae6da0540a0e0066132b472";
            String to = "d1e877472a4585d515b13f52ae7bfded1ccea511816d7772cb17e1ab20830819";
            int amount = 3030;
            exonumClient.transferFunds(from, to, amount);
            BlockChainUpdated.register(saveResult);
        }
        return updateChainResponses;
    }

    public static Map<String, List<PointData>> createBlocks(PointCloudUploadData data) {
        Map<String, List<PointData>> docs = new HashMap<String, List<PointData>>();
        for (PointData point : data.getPoints()) {
            String hash = point.getHash();
            if (docs.containsKey(hash)) {
                docs.get(hash).add(point);
            } else {
                List<PointData> points = new ArrayList<PointData>();
                points.add(point);
                docs.put(hash, points);
            }
        }
        logger.info("Number of hashes created: " + docs.keySet().size());
        return docs;
    }

    public static PointCloudUploadData createPointCloudUploadData(HttpServletRequest request) {
//        PointCloudUploadData pointCloudUploadData = PointCloudUploadData.fromLas("/Users/bjuhn/Downloads/MastinLake9_001_colorized0.laz");
        PointCloudUploadData pointCloudUploadData = PointCloudUploadData.fromLas("/Users/bjuhn/Downloads/ScanLook_Vehicle06.laz");
        return pointCloudUploadData;
    }

    private List<SaveResult> saveDocs(Map<String, List<PointData>> docs) {
        List<SaveResult> saveResults = new ArrayList<SaveResult>();
        MongoConnection mongoConnection = new MongoConnection();
        for (String key : docs.keySet()) {
            logger.info("Hash: " + key.toString() + "    size: " + Integer.toString(docs.get(key).size()));
            JsonObject block = createBlock(key, docs.get(key));
            mongoConnection.setDocument(block.toString());
            saveResults.add(new SaveResult());
        }
        return saveResults;
    }

    private JsonObject createBlock(String hash, List<PointData> pointData) {
        JsonObject json = new JsonObject();
        json.add("points", gson.toJsonTree(pointData));
        json.addProperty("_id", hash);
        return json;
    }
}