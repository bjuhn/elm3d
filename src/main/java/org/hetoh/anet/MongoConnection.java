package org.hetoh.anet;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import javax.swing.text.Document;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        DBCursor find = collection.find(dbObj);
        if(find.size() > 0) {
            collection.update(dbObj, dbObj);
        }else{
            collection.insert(dbObj);
        }

    }

    public JsonObject getDocumentById(String id, Gson gson) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        DBCursor cursor = collection.find(whereQuery);
        if(cursor.hasNext()) {
            return (gson.toJsonTree(cursor.next())).getAsJsonObject();
        }
        return null;
    }

    public List<JsonObject> getDocumentsById(List<String> ids, Gson gson) {
        List<JsonObject> docs = new ArrayList<JsonObject>();
        for(String id : ids) {
            JsonObject doc = getDocumentById(id, gson);
            if(doc != null) {
                docs.add(doc);
            }
        }
        return docs;
    }
}