package org.hetoh.anet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class AnetService {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ContextHandler uploadHandler = new ContextHandler("/upload");
        uploadHandler.setHandler(new PointCloudUploadHandler());

        ContextHandler fetchHandler = new ContextHandler("/pointcloud/");
        fetchHandler.setHandler(new PointCloudGetHandler());

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{uploadHandler, fetchHandler});

        server.setHandler(contexts);

        server.start();
        server.join();
    }
}