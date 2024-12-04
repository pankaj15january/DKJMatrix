package com.serverlessapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.Timer;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class StreamLambdaHandler implements RequestStreamHandler {

    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    public StreamLambdaHandler() {
        // we enable the timer for debugging. This SHOULD NOT be enabled in production.
        Timer.enable();
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }
}
//
////private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
////
////static {
////  try {
////      handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
////  } catch (ContainerInitializationException e) {
////      // if we fail here. We re-throw the exception to force another cold start
////      e.printStackTrace();
////      throw new RuntimeException("Could not initialize Spring Boot application", e);
////  }
////}
////
////public StreamLambdaHandler() {
////  // we enable the timer for debugging. This SHOULD NOT be enabled in production.
////  Timer.enable();
////}
////
////@Override
////public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
////      throws IOException {
////  handler.proxyStream(inputStream, outputStream, context);
////}
//
//public class StreamLambdaHandler1 implements RequestHandler<Map<String, String>, List<Document>> {
//	
//	private final MongoClient mongoClient;
//	
//	public StreamLambdaHandler1() {
//		mongoClient = MongoClients.create(System.getenv("MONGODB_ATLAS_URI"));
//	}
//	
////	 @Override
////	   public List<Document> handleRequest(Map<String, String> event, Context context){
////		
////		MongoDatabase database = mongoClient.getDatabase("sample_mflix");
////		MongoCollection collection = database.getCollection("movies");
////		Bson filter = new BsonDocument();
////		List<Document> results = new ArrayList<Document>();
////		collection.find(filter).limit(5).into(results);
////		
////		return results;
////		 
////	 }
//	
//	 @Override
//	   public List<Document> handleRequest(Map<String, String> event, Context context){
//		
//		MongoDatabase database = mongoClient.getDatabase("test");
//		MongoCollection<Document> collection = database.getCollection("person");
//		Bson filter = new BsonDocument();
//		List<Document> results = new ArrayList<Document>();
//		collection.find(filter).limit(2).into(results);	
//		return results;
//		 
//	 }
//	
//	/*
//	@Override
//	public List<Document> handleRequest(Map<String,String> event, Context context) {
//	    MongoDatabase database = mongoClient.getDatabase("sample_mflix");
//	    MongoCollection<Document> collection = database.getCollection("movies");
//
////	    Bson filter = new BsonDocument();
////
////	    if(event.containsKey("title") && !event.get("title").isEmpty()) {
////	        filter = Filters.eq("title", event.get("title"));
////	    }
////
////	    List<Document> results = new ArrayList<>();
////	    collection.find(filter).limit(5).into(results);
////	    return results;
//	    
//	    
//	 // Convert the MongoCollection into a List<Document>
//        List<Document> documentList = new ArrayList<>();
//        try (MongoCursor<Document> cursor = collection.find().iterator()) {
//            while (cursor.hasNext()) {
//                documentList.add(cursor.next());
//            }
//        }
//
//        
//        // Print the list or use it further
//        for (Document doc : documentList) {
//            System.out.println(doc.toJson());
//        }
//    
//
//	    return documentList;
//	}
//	    	
//*/
//}



