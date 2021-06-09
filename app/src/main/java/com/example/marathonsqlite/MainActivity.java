package com.example.marathonsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private FloatingActionButton actionBtn;
    private ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBtn = findViewById(R.id.fab);
        listView = findViewById(R.id.simpleListView);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        showCandidateList(databaseHelper);

        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), addActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Candidate candidate = (Candidate) adapterView.getItemAtPosition(position);
                databaseHelper.delete(candidate);
                showCandidateList(databaseHelper);
                Toast.makeText(MainActivity.this, "Deleted " + candidate.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCandidateList(DatabaseHelper databaseHelper) {
        arrayAdapter = new ArrayAdapter<Candidate>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getAll());
        listView.setAdapter(arrayAdapter);
    }


}
