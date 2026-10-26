package com.example.exam01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "campus.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE students (" +
                "student_id TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "enrolled INTEGER NOT NULL DEFAULT 0)");   // 1 = enrolled, 0 = not
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }

    private ContentValues toValues(Student s) {
        ContentValues cv = new ContentValues();
        cv.put("student_id", s.getId());
        cv.put("name", s.getName());
        cv.put("enrolled", s.isEnrolled() ? 1 : 0);
        return cv;
    }

    public boolean insert(Student s) {
        return getWritableDatabase().insert("students", null, toValues(s)) != -1;
    }

    public boolean update(Student s) {
        return getWritableDatabase().update("students", toValues(s),
                "student_id=?", new String[]{s.getId()}) > 0;
    }

    public boolean delete(String id) {
        return getWritableDatabase().delete("students",
                "student_id=?", new String[]{id}) > 0;
    }

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT student_id, name, enrolled FROM students ORDER BY name", null);
        try {
            while (c.moveToNext()) {
                list.add(new Student(c.getString(0), c.getString(1), c.getInt(2) == 1));
            }
        } finally {
            c.close();
        }
        return list;
    }
}