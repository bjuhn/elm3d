package org.hetoh.anet;


import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.File;
import java.io.IOException;

class MongoConnection {

    //
    // Connect to MongoDB (without authentification for the time being)
    // And get a handle on the collection used to store the metadata
    //
    private static Mongo mongo;
    private static DB db;
    private static DBCollection collection;


    public MongoConnection() {
        mongo = new Mongo("localhost", 27017);
        db = mongo.getDB("test");
        collection = db.getCollection("blocks");
    }

    public void addFile(String bucket, String filename, File file) throws IOException {
        GridFS gridfs = new GridFS(db, bucket);
        GridFSInputFile gfsFile = gridfs.createFile(file);
        gfsFile.setFilename(filename);
        gfsFile.save();
    }

    public void setDocument(String json) {
        Object o = com.mongodb.util.JSON.parse(json);
        DBObject dbObj = (DBObject) o;
        collection.insert(dbObj, WriteConcern.SAFE);
    }

}