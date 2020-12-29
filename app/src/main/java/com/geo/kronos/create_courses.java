package com.geo.kronos;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.geo.kronos.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import BaseDatos.DatosOpenHelper;

public class create_courses extends AppCompatActivity {
    private EditText edt_name_course;
    private SQLiteDatabase conexion;
    private SQLiteOpenHelper datosOpenHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt_name_course = (EditText)findViewById(R.id.name_course);

        Button cerrar = findViewById(R.id.button_cancel);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                onBackPressed();
            }
        });
    }
    public void insert_button(View view){
        if(!edt_name_course.getText().toString().trim().isEmpty()){
            try {
                datosOpenHelper = new DatosOpenHelper(this);
                conexion = datosOpenHelper.getWritableDatabase();
                StringBuilder sql = new StringBuilder();
                sql.append("INSERT INTO CURSOS(NOMBRE) VALUES ('");
                sql.append(edt_name_course.getText().toString().trim()+"')");

                conexion.execSQL(sql.toString());
                conexion.close();
                finish();

            }
            catch (Exception ex){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Aviso");
                dlg.setMessage(ex.getMessage());
                dlg.setNeutralButton("OK",null);
                dlg.show();
            }
        }
        else{
            edt_name_course.requestFocus();
        }
    }

}