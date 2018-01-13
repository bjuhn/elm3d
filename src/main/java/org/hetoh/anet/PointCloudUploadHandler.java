package org.hetoh.anet;

import com.google.gson.JsonObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ResourceHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
        List<JsonObject> docs = createDocs(data);

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

    private List<JsonObject> createDocs(PointCloudUploadData data) {
        List<JsonObject> docs = new ArrayList<JsonObject>();
        return docs;
    }

    private PointCloudUploadData createPointCloudUploadData(HttpServletRequest request) {
        PointCloudUploadData pointCloudUploadData = new PointCloudUploadData();
        return pointCloudUploadData;
    }

    private List<SaveResult> saveDocs(List<JsonObject> docs) {
        List<SaveResult> saveResults = new ArrayList<SaveResult>();
        MongoConnection mongoConnection = new MongoConnection();
        for (JsonObject doc : docs) {
            mongoConnection.setDocument(doc.toString());
        }
        return saveResults;
    }
}