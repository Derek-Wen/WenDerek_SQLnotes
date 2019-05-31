package com.example.mycontactapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editNumber;
    EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editNumber = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);

        myDb = new DatabaseHelper(this );
        Log.d("MyContactApp", "MainActivity: instantiated DatabaseHelper");
    }

    public void addData(View view){
        Cursor res = myDb.getAllData();
        boolean nameInserted = myDb.insertContact(editName.getText().toString(), editNumber.getText().toString(), editAddress.getText().toString());

        if (nameInserted){

            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "FAILED - contact not inserted", Toast.LENGTH_LONG).show();

        }
    }

    public void viewData(View view){
        Cursor res = myDb.getAllData();

        if (res.getCount() == 0){
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID: " + res.getString(0) +"\n");
            buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("Phone Number: " +res.getString(2) + "\n");
            buffer.append("Address: " + res.getString(3) + "\n");
        }
        showMessage("Data", buffer.toString());
    }

    public void searchData(View view) {
        Cursor res = myDb.getAllData();

        if (res.getCount() == 0) {
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            if (!editName.getText().toString().equals("") && editNumber.getText().toString().equals("") && editAddress.getText().toString().equals("")) {
                if (res.getString(1).equals(editName.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (editName.getText().toString().equals("") && !editNumber.getText().toString().equals("") && editAddress.getText().toString().equals("")){
                if (res.getString(2).equals(editNumber.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (editName.getText().toString().equals("") && editNumber.getText().toString().equals("") && !editAddress.getText().toString().equals("")){
                if (res.getString(3).equals(editAddress.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (!editName.getText().toString().equals("") && !editNumber.getText().toString().equals("") && editAddress.getText().toString().equals("")){
                if (res.getString(1).equals(editName.getText().toString()) && res.getString(2).equals(editNumber.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (!editName.getText().toString().equals("") && editNumber.getText().toString().equals("") && !editAddress.getText().toString().equals("")){
                if (res.getString(1).equals(editName.getText().toString()) && res.getString(3).equals(editAddress.getText().toString())){
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else if (editName.getText().toString().equals("") && !editNumber.getText().toString().equals("") && !editAddress.getText().toString().equals("")){
                if (res.getString(2).equals(editNumber.getText().toString()) && res.getString(3).equals(editAddress.getText().toString())){
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
            else {
                if (res.getString(1).equals(editName.getText().toString()) &&
                        res.getString(2).equals(editNumber.getText().toString()) &&
                        res.getString(3).equals(editAddress.getText().toString())) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Phone Number: " + res.getString(2) + "\n");
                    buffer.append("Address: " + res.getString(3) + "\n");
                }
            }
        }

        showMessage("Result", buffer.toString());
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
