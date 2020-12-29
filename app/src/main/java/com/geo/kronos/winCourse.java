package com.geo.kronos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.provider.MediaStore;
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
    FloatingActionButton btnCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_course);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        toolbar.setTitle(intent.getStringExtra("name"));
        setSupportActionBar(toolbar);
        btnCamera  = (FloatingActionButton)findViewById(R.id.camera);
        if (ContextCompat.checkSelfPermission(winCourse.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(winCourse.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }



        //File file = ;

        list = imageReader(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()));

        //Toast toast = Toast.makeText(getApplicationContext(),Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(),Toast.LENGTH_SHORT);

        //toast.show();
        gv = (GridView) findViewById(R.id.grid_gallery);
        gv.setAdapter(new GridAdapter());


        btnCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                try {
                    //Intent intent = new Intent(MainActivity.this, camera.class);
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    // intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    //intent.putExtra("android.intent.extra.USE_BACK_CAMERA", true);
                    startActivityForResult(intent,100);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
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