package BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class DatosOpenHelper extends SQLiteOpenHelper{
    public DatosOpenHelper(Context context){
        super(context,"DATOS",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS CURSOS (");
        sql.append("NOMBRE VARCHAR(250))");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
