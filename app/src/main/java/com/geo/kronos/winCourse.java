package com.geo.kronos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class winCourse extends AppCompatActivity {
    GridView gv;
    ArrayList<File> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_course);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        toolbar.setTitle(intent.getStringExtra("name"));
        setSupportActionBar(toolbar);


        //File file = ;

        list = imageReader(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()));

        //Toast toast = Toast.makeText(getApplicationContext(),Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(),Toast.LENGTH_SHORT);

        //toast.show();
        gv = (GridView) findViewById(R.id.grid_gallery);
        gv.setAdapter(new GridAdapter());
    }

    class GridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.item_gallery, null);
            ImageView iv = convertView.findViewById(R.id.image_view_grid);
            iv.setImageURI(Uri.parse(getItem(position).toString()));

            return convertView;
        }
    }
    ArrayList<File> imageReader(File root){
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();

        for (int i = 0;i<files.length;i++){
            if(files[i].isDirectory()){
                a.addAll(imageReader(files[i]));
            }
            else{
                if(files[i].getName().endsWith(".jpg")){
                    a.add(files[i]);
                }
            }

        }
        return a;
    }
}