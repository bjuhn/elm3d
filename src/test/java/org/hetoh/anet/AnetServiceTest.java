package org.hetoh.anet;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertThat;
import static sun.nio.cs.Surrogate.is;

public class AnetServiceTest {

    @Test
    public void testGet() throws Exception
    {
        // Create Server
        Server server = new Server(8080);
//        ServletContextHandler context = new ServletContextHandler();
//        context.addServlet(defaultServ,"/");

        PointCloudUploadHandler pointCloudUploadHandler = new PointCloudUploadHandler();

        server.setHandler(pointCloudUploadHandler);

        // Start Server
        server.start();

        // Test GET
        HttpURLConnection http = (HttpURLConnection)new URL("http://localhost:8080/").openConnection();
        http.connect();
        assert(http.getResponseCode() == HttpStatus.OK_200);
//
        // Stop Server
        server.stop();
    }
}
