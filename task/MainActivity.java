package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import static java.util.Calendar.getInstance;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private mySQLiteDBHandler dbHandler;
    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        calendarView=findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                selectedDate=Integer.toString(year)+Integer.toString(month)+Integer.toString(dayOfMonth);
                readDatabase(view);
            }
        });
        try{
            dbHandler=new mySQLiteDBHandler(this,"Calendar Database",null,1);
        sqLiteDatabase=dbHandler.getWritableDatabase();
        sqLiteDatabase.execSQL("CREATE TABLE eventCalendar(Date TEXT,Event TEXT)");

        }
        catch(Exception e){
            e.printStackTrace();

        }




    } public void insertDatabase(View view){
        ContentValues contentValues=new ContentValues();
        contentValues.put("Date",selectedDate);
        contentValues.put("Event",editText.getText().toString());
        sqLiteDatabase.insert("eventCalendar",null,contentValues);

    }

    public void readDatabase(View view){
        String query="Select Event from eventCalendar Date="+selectedDate;
        try{
            Cursor cursor=sqLiteDatabase.rawQuery(query,null);
            cursor.moveToFirst();
            editText.setText(cursor.getString(0));
        }catch(Exception e){
            e.printStackTrace();
            editText.setText(" ");

        }
    }

}