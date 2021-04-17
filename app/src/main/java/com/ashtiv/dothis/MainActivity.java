package com.ashtiv.dothis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.*;
import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {
    ListView simplelist;
    Button add;
    DBhelper DB;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add=findViewById(R.id.ov);

//        update=findViewById(R.id.btnupd);
//        delete=findViewById(R.id.task_delete);
//        view=findViewById(R.id.btnview);
        DB = new DBhelper(this);
        updui();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText taskEditText = new EditText(MainActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                String task = String.valueOf(taskEditText.getText());
                                String nametxt = String.valueOf(taskEditText.getText());
                                Boolean checkinsertdata = DB.insertdata(nametxt);
                                if (checkinsertdata == true) {
                                    Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Task already exist", Toast.LENGTH_SHORT).show();
                                }
                                updui();


                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                        dialog.show();

            }
        });
    }
    public void deletetask(View view) {
        View parent = (View) view.getParent();
        TextView nn = (TextView) parent.findViewById(R.id.task_title);
//                TextView nn=findViewById(R.id.task_title);
        String nametxt = nn.getText().toString();
//                String nametxt=name.getText().toString();
        long checkdeldata = DB.deldata(nametxt);
        Toast.makeText(MainActivity.this, "Task Removed", Toast.LENGTH_SHORT).show();
        updui();
    }

    public void updui() {

        Cursor res = DB.getdata();
        if (res.getCount() == 0) {
            simplelist = (ListView) findViewById(R.id.list);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.clear();
            Toast.makeText(MainActivity.this, "No Task exists", Toast.LENGTH_SHORT).show();
            arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.item_todo, R.id.task_title, arrayList);
            simplelist.setAdapter(arrayAdapter);
            return;
        }
//                StringBuffer buffer=new StringBuffer();
        else {
            simplelist = (ListView) findViewById(R.id.list);
            ArrayList<String> arrayList = new ArrayList<>();
            while (res.moveToNext()) {
//                    buffer.append("Name :"+res.getString(0)+"\n");

                arrayList.add(res.getString(0));
            }
            arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.item_todo, R.id.task_title, arrayList);
            simplelist.setAdapter(arrayAdapter);
        }

    }
}

