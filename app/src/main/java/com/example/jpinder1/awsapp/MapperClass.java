package com.example.jpinder1.awsapp;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;


/**
 * Created by jpinder1 on 3/3/2018.
 */
@DynamoDBTable(tableName = "StudentsForTA")
public class MapperClass {
    String Name;
    int PlaceInLine;
    AmazonDynamoDBClient ddb = UserPreferenceDemoActivity.clientManager
            .ddb();


    public String getName() {
        return Name;
    }

    public void setPlaceInLine(int PlaceInLine) {
        this.PlaceInLine = PlaceInLine;
    }

}






