package br.edu.ifpi.projetoeventos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.edu.ifpi.projetoeventos.models.enums.EProfileType;
import br.edu.ifpi.projetoeventos.models.others.User;

public class SignUpAndValidationDAO extends SQLiteOpenHelper {

    public SignUpAndValidationDAO(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE user (username TEXT PRIMARY KEY, password TEXT," +
                " name TEXT, idEProfileType INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS user;";
        db.execSQL(sql);
        onCreate(db);
    }

    private static String encrypt(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void insert(User user){
        ContentValues cv = new ContentValues();

        cv.put("username", user.getEmail());
        cv.put("password", encrypt(user.getPassword()));
        cv.put("name", user.getName());
        cv.put("idEProfileType", user.getProfileType().getId());

        getWritableDatabase().insert("user", null, cv);
    }

    public User getUser(String username){
        String sql = "SELECT * FROM user WHERE username = '" + username + "';";
        try {
            Cursor c = getReadableDatabase().rawQuery(sql, null);
            c.moveToNext();
            String newUsername = c.getString(c.getColumnIndex("username"));
            String newPassword = c.getString(c.getColumnIndex("password"));
            String newName = c.getString(c.getColumnIndex("name"));
            EProfileType newEProfileType = EProfileType.getById(c.getInt(c.getColumnIndex("idEProfileType")));
            User user = new User(newUsername, newPassword, newName, newEProfileType);
            return user;
        }
        catch (CursorIndexOutOfBoundsException e){
            User user = new User("NONAME", "NOPASSWORD");
            return user;
        }
    }

    public boolean validate(String username, String password){
        User dbUser = getUser(username);
        if (dbUser.getEmail().equals(username) && dbUser.getPassword().equals(encrypt(password))){
            return true;
        }
        return false;
    }
}
