package models;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;

import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

/*
    MongoDB docs
    
    http://api.mongodb.org/java/2.13/
    http://www.tutorialspoint.com/mongodb/mongodb_query_document.htm
    //http://mongodb.github.io/mongo-java-driver/2.13/getting-started/quick-tour/
    http://docs.mongodb.org/manual/

*/

public class UserManager {

    private List<User> users;
    
    private MongoClient mongoClient;
    private DB database;

    
    public UserManager() {
        users = new ArrayList<User>();
    }
    
    public void test() {
        openConnection();
        
        System.out.println("toString: " + mongoClient.getMongoClientOptions().toString());
        System.out.println("maxConn: " + mongoClient.getMongoClientOptions().getConnectionsPerHost());
        
        closeConnection();
    }
    
    public void openConnection() {
        if (mongoClient == null) {
            
            try {
                List<MongoCredential> credentialList = new ArrayList<MongoCredential>();
                ServerAddress serverAddress = new ServerAddress("ds045608.mongolab.com", 45608);
                
                MongoCredential credential = MongoCredential.createCredential("largedebts", "largedebts", "pocosi12".toCharArray());
                credentialList.add(credential);
                
                mongoClient = new MongoClient( serverAddress, credentialList );
                
                database = mongoClient.getDB( "largedebts" );
            } catch (Exception e) {
                System.out.println("ERROR: Could not reach Database");
            }
        }
    }
    
    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
    
    public List<User> getUsers() {
        loadUsers();
        return users;
    }
    
    public void insert(User user) {
        
        openConnection();
        
        DBCollection usersCol = database.getCollection("users");
        
        if (usersCol == null) {
            System.out.println("ERROR: Collection not created.");
            
            
        } else {
            BasicDBObject userDB = new BasicDBObject( "name", user.name );
            usersCol.insert(userDB);
        }
        
        closeConnection();
    }
    
    public void delete(User user) {
        
        openConnection();
        
        DBCollection usersCol = database.getCollection("users");
        
        if (usersCol == null) {
            System.out.println("ERROR: Collection not created.");
            
            
        } else {
            BasicDBObject userDB = new BasicDBObject( "name", user.name );
            usersCol.remove(userDB);
        }
        
        closeConnection();
    }
    
    public void loadUsers() {
        
        openConnection();
        
        DBCollection usersCol = database.getCollection("users");
        users = new ArrayList<User>();
        
        if (usersCol == null) {
            System.out.println("Creating users collection...");
            usersCol = database.createCollection( "users" , new BasicDBObject( "autoIndexID", "true" ) );
            System.out.println("success.");
        }
        
        DBCursor usersCursor = usersCol.find();
        while ( usersCursor.hasNext() == true ) {
            BasicDBObject userDB = (BasicDBObject)usersCursor.next();
            
            String id = userDB.getString("_id");
            String name = userDB.getString("name");
            
            users.add(new User(id, name));
        }
        
        closeConnection();
    }
    
}
