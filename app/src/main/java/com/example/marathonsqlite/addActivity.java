package com.example.marathonsqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class addActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    private Button addBtn;
    private EditText firstName, lastName, email, phone;
    private ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Candidate");
        setContentView(R.layout.activity_add);

        firstName =  findViewById(R.id.firstName);
        lastName = findViewById(R.id.LastName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        addBtn = findViewById(R.id.button);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Candidate candidate;
                try {
                     candidate = new Candidate(null,firstName.getText().toString(),
                            lastName.getText().toString(), email.getText().toString(),
                            Long.parseLong(phone.getText().toString()));
                    //Toast.makeText(addActivity.this, candidate.toString(), Toast.LENGTH_LONG).show();
                }catch (Exception e) {
                    //Toast.makeText(addActivity.this, "Error creating candidate. Make sure to enter all info", Toast.LENGTH_SHORT).show();
                    candidate = new Candidate(null,"error","error","noEmail",1L);
                }
                DatabaseHelper databaseHelper = new DatabaseHelper(addActivity.this);
                String addedCandidate = databaseHelper.addOne(candidate);
                Toast.makeText(addActivity.this, "Candidate " + addedCandidate + " successfully", Toast.LENGTH_LONG).show();

                databaseHelper = new DatabaseHelper(addActivity.this);
                arrayAdapter = new ArrayAdapter<Candidate>(addActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getAll());
                listView.setAdapter(arrayAdapter);
            }
        });



    }
}