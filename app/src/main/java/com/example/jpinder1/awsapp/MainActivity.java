package com.example.jpinder1.awsapp;


import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import static com.example.jpinder1.awsapp.DynamoDBManager.insertUsers;


// extends AppCompatActivity

public class MainActivity extends AppCompatActivity {

    // Declare a DynamoDBMapper object
    DynamoDBMapper dynamoDBMapper;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AWSMobileClient.getInstance().initialize(this).execute();

        // Instantiate a AmazonDynamoDBMapperClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
        tv=(TextView)findViewById(R.id.tv);

    }

    @Override
    protected void onPause() {
        super.onPause();
        tv.setText("Paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv.setText("Welcome to the TA Lab!");
    }
    public void ding(View view){
        insertUsers();
    }

    public void list_users(View v){
        int rand = (int)(Math.random() *  100  + 100);
        //Toast.makeText(getApplicationContext(),"Yoooooo",Toast.LENGTH_SHORT).show();
        //tv.setText(String.valueOf(rand));
        tv.getText();
        Snackbar.make(v, "Enter your First and Last Name", Snackbar.LENGTH_INDEFINITE).show();
    }
    public void show_popup (View s){
        Toast.makeText(this,"Requested a Computer Science TA",Toast.LENGTH_SHORT).show();
        tv.setText("Thank you for joining the queue! We will be with you shortly");
    }

}


