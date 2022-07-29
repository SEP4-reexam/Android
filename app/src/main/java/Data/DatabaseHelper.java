package Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String LINECHART_TABLE = "CREATE TABLE " +
                Constants.TABLE_NAME + " (" +
                Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.yAXIS + " STRING);";
        sqLiteDatabase.execSQL(LINECHART_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void saveData( String valY)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.yAXIS, valY);

        db.insert(Constants.TABLE_NAME, null, values);
    }

    public ArrayList<String> queryYData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> yData = new ArrayList<String>();

        String query = "SELECT SUM (" + Constants.yAXIS + ") FROM " + Constants.TABLE_NAME + " WHERE " +
                Constants.yAXIS + " IS NOT NULL" +
                " GROUP BY " + Constants.DATE;

        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            yData.add(cursor.getString(0));
        }
        cursor.close();
        System.out.println("Size of my peepee: " + yData.size());
        return yData;
    }

}
