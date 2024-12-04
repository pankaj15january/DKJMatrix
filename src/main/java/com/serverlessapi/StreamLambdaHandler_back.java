package com.serverlessapi;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.serverlessapi.person.models.PersonEntity;

@Configuration
public class StreamLambdaHandler_back implements RequestHandler<Map<String, String>, List<PersonEntity>> {

//private final MongoClient mongoClient;
	
//	public StreamLambdaHandler() {
//		mongoClient = MongoClients.create(System.getenv("MONGODB_ATLAS_URI"));
//	}
	
	private final MongoClientSettings clientSettings;
	
	public StreamLambdaHandler_back() {
//		ConnectionString connectionString = new ConnectionString(System.getProperty("MONGODB_ATLAS_URI"));
		ConnectionString connectionString = new ConnectionString(System.getenv("MONGODB_ATLAS_URI"));
	    CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
	    CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
	    this.clientSettings = MongoClientSettings.builder()
	                                                            .applyConnectionString(connectionString)
	                                                            .codecRegistry(codecRegistry)
	                                                            .build();
	}
    @Override
	   public List<PersonEntity> handleRequest(Map<String, String> event, Context context){
    	
    	
    	try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("test");
            MongoCollection<PersonEntity> persons = db.getCollection("persons", PersonEntity.class);
            // create a new grade.
            /*Grade newGrade = new Grade().setStudentId(10003d)
                                        .setClassId(10d)
                                        .setScores(List.of(new Score().setType("homework").setScore(50d)));
            grades.insertOne(newGrade);
            System.out.println("Grade inserted.");*/
            
            // find this grade.
            
//          PersonEntity personEntity = persons.find(eq("student_id", 10003d)).first();
            /*
            PersonEntity personEntity = persons.find().first();
            System.out.println("Grade found:\t" + personEntity);
            List<PersonEntity> personList = new ArrayList<>();
            personList.add(personEntity);*/
            List<PersonEntity> personList = new ArrayList<>();
            persons.find().limit(2).into(personList);	
//		MongoDatabase database = mongoClient.getDatabase("test");
//		MongoCollection<Document> collection = database.getCollection("person");
//		Bson filter = new BsonDocument();
//		List<Document> results = new ArrayList<Document>();
//		collection.find(filter).limit(2).into(results);	
		return personList;//results;
		 
	 }
    
    }
    
    
    
}
