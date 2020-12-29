package com.geo.kronos.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.geo.kronos.MainActivity;
import com.geo.kronos.R;
import com.geo.kronos.create_courses;
import com.geo.kronos.winCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import BaseDatos.DatosOpenHelper;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private GridView dualDatos;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> cursos;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        dualDatos = (GridView) root.findViewById(R.id.gridView);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                actualizar();
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity().getApplication(), create_courses.class);
                //startActivity(it);
                startActivityForResult(it,0);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        dualDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String list_row = cursos.get(position);
                Intent it = new Intent(getActivity().getApplication(), winCourse.class);
                it.putExtra("name",cursos.get(position));
                startActivity(it);
            }
        });

        actualizar();
        return root;
    }

    public void actualizar(){
        //lstDatos =(ListView) findViewById(R.id.lst_courses);


        cursos = new ArrayList<String>();

        try {
            datosOpenHelper = new DatosOpenHelper(getContext());
            conexion = datosOpenHelper.getWritableDatabase();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CURSOS");
            String sNombre;
            Cursor resultado = conexion.rawQuery(sql.toString(),null);
            if(resultado.getCount() > 0){
                resultado.moveToFirst();
                do{
                    sNombre = resultado.getString(resultado.getColumnIndex("NOMBRE"));
                    cursos.add(sNombre);
                }
                while (resultado.moveToNext());
            }
            adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,cursos);
            CustomAdapter customAdapter = new CustomAdapter();

            //lstDatos.setAdapter(adaptador);
            //dualDatos.setAdapter(adaptador);
            dualDatos.setAdapter(customAdapter);
        }
        catch (Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setTitle("Aviso");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }
    private class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return cursos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);

            TextView name = view1.findViewById(R.id.coursse);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(cursos.get(position));
            image.setImageResource(R.mipmap.icon_book_background);
            return view1;
        }
    }
}