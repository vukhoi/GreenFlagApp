package project1.example.com.greenflagapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Locale;


public class DatabaseManager extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "database";
    private static int DATABASE_VERSION = 1;

    // account table
    private final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    private final String NAME = "NAME";
    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";
    private final String PHOTO = "PHOTO";
    private final String EMAIL = "EMAIL";
    private final String AGE = "AGE";
    private final String BIRTHDATE = "BIRTHDATE";
    private final String GENDER = "GENDER";
    private final String POSTALADDRESS = "POSTALADDRESS";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    // create tables
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateQuery = String.format("create table %s(%s integer primary key autoincrement, %s text, %s text, %s text, %s text, %s text, %s integer, %s text, %s text, %s text)"
                , ACCOUNT_TABLE, "ID", NAME, USERNAME, PASSWORD, PHOTO, EMAIL, AGE, BIRTHDATE, GENDER, POSTALADDRESS);


        sqLiteDatabase.execSQL(CreateQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop old table if it exists , otherwise, the table will consistently accessible during the life time of app
        sqLiteDatabase.execSQL("drop table if exists " + ACCOUNT_TABLE);
        // Re-create tables
        onCreate(sqLiteDatabase);
    }

    // insert depending on the type of table
    public void insert(Account acc) {
        SQLiteDatabase db = this.getWritableDatabase();

        String insertQuery = String.format(Locale.getDefault(),"insert into %s(%s, %s, %s, %s, %s, %s, %s, %s, %s) values (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", %d, \"%s\", \"%s\", \"%s\")",
                ACCOUNT_TABLE, NAME, USERNAME, PASSWORD, PHOTO, EMAIL, AGE, BIRTHDATE, GENDER, POSTALADDRESS, acc.getName(), acc.getUsername(), acc.getPassword(), acc.getPhoto()
                ,acc.getEmail(), acc.getAge(), acc.getBirthDate(), acc.getGender(), acc.getPostalAddress());
        db.execSQL(insertQuery);

        db.close();
    }


    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // each table has field "name"
        db.execSQL(String.format("delete from %s where id = %s", ACCOUNT_TABLE, id));
        db.close();
    }


    public void updateById(Account acc, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String updateQuery = "update " + ACCOUNT_TABLE;

        updateQuery += String.format(Locale.getDefault()," set %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %d, %s = %s, %s = %s, %s = %s"
            , NAME, acc.getName(), USERNAME, acc.getUsername(), PASSWORD, acc.getPassword(), PHOTO, acc.getPhoto()
            , EMAIL, acc.getEmail(), AGE, acc.getAge(), BIRTHDATE, acc.getBirthDate(), GENDER, acc.getGender()
            , POSTALADDRESS, acc.getPostalAddress());


        updateQuery += "where name ID = " + id;
        db.execSQL(updateQuery);
        db.close();
    }

    public void updateByEmail(Account acc, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        String updateQuery = "update " + ACCOUNT_TABLE;

        updateQuery += String.format(Locale.getDefault()," set %s = \"%s\", %s = \"%s\", %s = \"%s\", %s = \"%s\", %s = %d, %s = \"%s\", %s = \"%s\", %s = \"%s\""
                , NAME, acc.getName(), USERNAME, acc.getUsername(), PASSWORD, acc.getPassword(), PHOTO, acc.getPhoto()
                , AGE, acc.getAge(), BIRTHDATE, acc.getBirthDate(), GENDER, acc.getGender() , POSTALADDRESS, acc.getPostalAddress());


        updateQuery += "where " + EMAIL + " = \"" + email + "\"";
        db.execSQL(updateQuery);
        db.close();
    }

    public Account selectById(String id) {
        String sqlQuery = "select * from " + ACCOUNT_TABLE;
        sqlQuery += " where id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        Account acc = createAccFromCursor(cursor);

        cursor.close();

        return acc;
    }

    public Account selectByEmail(String email) {
        String sqlQuery = "select * from " + ACCOUNT_TABLE;
        sqlQuery += " where " + EMAIL + " = \"" + email + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        Account acc = createAccFromCursor(cursor);

        cursor.close();

        return acc;
    }

    private Account createAccFromCursor(Cursor cursor) {
        Account acc = null;
        if (cursor.getCount() == 0) {
            Log.d("selectById", " no such acc found");
        }
        else{
            int id = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            String username = cursor.getString(2);
            String password = cursor.getString(3);
            String photo = cursor.getString(4);
            String email = cursor.getString(5);
            int age = Integer.parseInt(cursor.getString(6));
            String birthDate = cursor.getString(7);
            String gender = cursor.getString(8);
            String postalAddress = cursor.getString(9);


            acc = new Account(name, username, photo, password, email, age, birthDate,gender, postalAddress);
        }
        return acc;
    }

    public ArrayList<Account> selectAll(String table){
        String sqlQuery = "select * from " + ACCOUNT_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        ArrayList<Account> accs = new ArrayList<Account>();
        if (cursor.getCount() > 0) {
            do {
               createAccFromCursor(cursor);
            } while (cursor.moveToNext());
        }

        return accs;
    }

}

