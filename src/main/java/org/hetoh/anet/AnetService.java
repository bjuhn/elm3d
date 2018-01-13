package org.hetoh.anet;

import org.eclipse.jetty.server.Server;

public class AnetService {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new PointCloudUploadHandler());

        server.start();
        server.join();
    }
}