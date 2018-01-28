package com.apps.vados.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.vados.notes.R;

import com.apps.vados.notes.controller.DatabaseAdapter;
import com.apps.vados.notes.model.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button createNewNote;
    private ListView noteList;
    private ArrayAdapter<Note> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNewNote = findViewById(R.id.newNote);
        createNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("click", "add");
                startActivity(intent);
            }
        });

        noteList = findViewById(R.id.list);
        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = arrayAdapter.getItem(i);
                long id = note.getId();

                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("click", "edit");
                intent.putExtra("id", Long.toString(id));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        databaseAdapter.open();

        List<Note> notes = databaseAdapter.getNotes();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item_of_list_notes, notes);
        noteList.setAdapter(arrayAdapter);
        databaseAdapter.close();
    }
}
