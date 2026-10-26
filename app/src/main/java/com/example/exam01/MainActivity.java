package com.example.exam01;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DBHelper db;
    private EditText etName, etId;
    private CheckBox cbEnrolled;
    private ListView lvStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        etName = findViewById(R.id.etName);
        etId = findViewById(R.id.etId);
        cbEnrolled = findViewById(R.id.cbEnrolled);
        lvStudents = findViewById(R.id.lvStudents);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnView = findViewById(R.id.btnView);

        btnAdd.setOnClickListener(v -> {
            Student s = readForm();
            if (s == null) return;
            if (db.insert(s)) toast("Student added");
            else toast("Student ID already exists");
            refresh();
        });

        btnEdit.setOnClickListener(v -> {
            Student s = readForm();
            if (s == null) return;
            if (db.update(s)) toast("Student updated");
            else toast("Student ID not found");
            refresh();
        });

        btnDelete.setOnClickListener(v -> {
            String id = etId.getText().toString().trim();
            if (id.isEmpty()) {
                toast("Enter / select a Student ID");
                return;
            }
            if (db.delete(id)) toast("Student deleted");
            else toast("Student ID not found");
            clearForm();
            refresh();
        });

        btnView.setOnClickListener(v -> refresh());

        lvStudents.setOnItemClickListener((parent, view, position, id) -> {
            Student s = (Student) parent.getItemAtPosition(position);
            etName.setText(s.getName());
            etId.setText(s.getId());
            cbEnrolled.setChecked(s.isEnrolled());
        });

        refresh();
    }

    private Student readForm() {
        String name = etName.getText().toString().trim();
        String id = etId.getText().toString().trim();
        if (name.isEmpty() || id.isEmpty()) {
            toast("Name and Student ID are required");
            return null;
        }
        return new Student(id, name, cbEnrolled.isChecked());
    }

    private void refresh() {
        lvStudents.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, db.getAll()));
    }

    private void clearForm() {
        etName.setText("");
        etId.setText("");
        cbEnrolled.setChecked(false);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}