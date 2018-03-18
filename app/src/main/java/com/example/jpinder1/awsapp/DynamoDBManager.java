package com.example.jpinder1.awsapp;

/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

//package com.amazonaws.demo.userpreferencesom;

        import android.util.Log;

        import com.amazonaws.AmazonServiceException;
        import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
        import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
        import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
        import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
        import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
        import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
        import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;


        import java.util.ArrayList;

public class DynamoDBManager {

    private static final String TAG = "DynamoDBManager";

    public static String getTestTableStatus(){
        return "the test table is doing fine :)";
    }

    /*
     * Inserts ten users with userNo from 1 to 10 and random names.
     */
    public static void insertUsers() {
        AmazonDynamoDBClient ddb = UserPreferenceDemoActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {

                UserPreference userPreference = new UserPreference();
                userPreference.setPlaceInLine(3);
                userPreference.setName(Constants.getRandomName());


                Log.d(TAG, "Inserting users");
                mapper.save(userPreference);
                Log.d(TAG, "Users inserted");

        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting users");
            UserPreferenceDemoActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    /*
     * Scans the table and returns the list of users.
     */
    public static ArrayList<UserPreference> getUserList() {

        AmazonDynamoDBClient ddb = UserPreferenceDemoActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        try {
            PaginatedScanList<UserPreference> result = mapper.scan(
                    UserPreference.class, scanExpression);

            ArrayList<UserPreference> resultList = new ArrayList<UserPreference>();
            for (UserPreference up : result) {
                resultList.add(up);
            }
            return resultList;

        } catch (AmazonServiceException ex) {
            UserPreferenceDemoActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }

        return null;
    }

    /*
     * Retrieves all of the attribute/value pairs for the specified user.
     */
    public static UserPreference getUserPreference(int userNo) {

        AmazonDynamoDBClient ddb = UserPreferenceDemoActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            UserPreference userPreference = mapper.load(UserPreference.class,
                    userNo);

            return userPreference;

        } catch (AmazonServiceException ex) {
            UserPreferenceDemoActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }

        return null;
    }

    /*
     * Updates one attribute/value pair for the specified user.
     */
    public static void updateUserPreference(UserPreference updateUserPreference) {

        AmazonDynamoDBClient ddb = UserPreferenceDemoActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            mapper.save(updateUserPreference);

        } catch (AmazonServiceException ex) {
            UserPreferenceDemoActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    /*
     * Deletes the specified user and all of its attribute/value pairs.
     */
    public static void deleteUser(UserPreference deleteUserPreference) {

        AmazonDynamoDBClient ddb = UserPreferenceDemoActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            mapper.delete(deleteUserPreference);

        } catch (AmazonServiceException ex) {
            UserPreferenceDemoActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }


    @DynamoDBTable(tableName = "StudentsForTA")
    public static class UserPreference {
        private int PlaceInLine;
        private String Name;


        @DynamoDBHashKey(attributeName = "PlaceInLine")
        public int getPlaceInLine() {
            return PlaceInLine;
        }

        public void setPlaceInLine(int PlaceInLine) {
            this.PlaceInLine = PlaceInLine;
        }

        @DynamoDBAttribute(attributeName = "Name")
        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;


        }
    }
}