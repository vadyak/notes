package com.apps.vados.notes.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.vados.notes.R;
import com.apps.vados.notes.controller.DatabaseAdapter;
import com.apps.vados.notes.model.Note;

public class EditActivity extends AppCompatActivity {
    private DatabaseAdapter adapter;
    private EditText description;
    private Button saveButton;
    private Button deleteButton;
    private long noteId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        description = findViewById(R.id.description);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(onClickSaveButton);

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(onClickDeleteButton);

        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();

        String click = extras.getString("click");

        if (click.equals("edit")) {
            noteId = Long.parseLong(extras.getString("id"));
            adapter.open();
            Note note = adapter.getNote(noteId);
            description.setText(note.getDescription());
            adapter.close();
        } else if (click.equals("add")){
            deleteButton.setText("отмена");
            deleteButton.setBackgroundColor(Color.parseColor("#ff8800"));
        }
    }

    View.OnClickListener onClickSaveButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String noteDescription = description.getText().toString();
            if (!noteDescription.equals("")) {
                Note note = new Note(noteId, noteDescription);

                adapter.open();
                if (noteId > 0) {
                    adapter.update(note);
                } else {
                    adapter.insert(note);
                }
                adapter.close();
            }
            goHome();
        }
    };

    View.OnClickListener onClickDeleteButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            adapter.open();
            adapter.delete(noteId);
            adapter.close();
            goHome();
        }
    };

    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
