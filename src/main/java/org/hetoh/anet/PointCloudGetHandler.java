package org.hetoh.anet;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PointCloudGetHandler extends AbstractHandler {

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        MongoConnection mongoConnection = new MongoConnection();

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        List<String> ids = new ArrayList<String>();
        // if ids is not in request
        for (int x = -10; x < 10; x++) {
            for (int y = -10; y < 10; y++) {
                for (int z = -10; z < 10; z++) {
                    ids.add(x + ":" + y + ":" + z);
                }
            }
        }

        List<JsonObject> documents = mongoConnection.getDocumentsById(ids, new GsonBuilder().setPrettyPrinting().create());
        for (JsonObject document : documents) {
            JsonArray points = document.get("points").getAsJsonArray();
            for (JsonElement element : points) {
                Number x = element.getAsJsonObject().get("x").getAsNumber();
                Number y = element.getAsJsonObject().get("y").getAsNumber();
                Number z = element.getAsJsonObject().get("z").getAsNumber();
                Number r = element.getAsJsonObject().get("r").getAsNumber();
                Number g = element.getAsJsonObject().get("g").getAsNumber();
                Number b = element.getAsJsonObject().get("b").getAsNumber();
                response.getWriter().println(x + " " + y + " " + z + " " + r + " " + g + " " + b);
            }
        }
        baseRequest.setHandled(true);
    }
}
