package org.hetoh.anet;


import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import org.hetoh.anet.MongoConnection;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MongoConnectionTest {

    @Test
    public void testSet() throws Exception {
        MongoConnection connection = new MongoConnection();
        connection.setDocument(("{\"test\":\"b\"}"));

    }
}