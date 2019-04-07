package hakan_akkurt.de.kraftwerk;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    SQLiteDatabase myDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            myDB = this.openOrCreateDatabase("KraftwerkApp", MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS VKRAFTWERKIDS (id INTEGER PRIMARY KEY, name VARCHAR)");
            myDB.execSQL("INSERT INTO VKRAFTWERKIDS (id, name) VALUES (100, 'KW1')");
            myDB.execSQL("INSERT INTO VKRAFTWERKIDS (id, name) VALUES (200, 'KW2')");
            myDB.execSQL("INSERT INTO VKRAFTWERKIDS (id, name) VALUES (300, 'KW3')");

        } catch (Exception e) {
            Log.e("Error", "Error", e);
        }

        final Button button = findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText editTextVKW = findViewById(R.id.txt_vKwId);

                String vkwId = editTextVKW.getText().toString();

                Cursor cursor = myDB.rawQuery("SELECT id FROM VKRAFTWERKIDS where name='" + vkwId.trim() + "'", null);

                try {
                    if (cursor.getCount() < 1)
                    {
                        cursor.close();
                        Toast.makeText(getApplicationContext(), "Unbekannte virtuelle Kraftwerk ID", Toast.LENGTH_LONG).show();
                    }

                    cursor.moveToFirst();
                    String vKWid = cursor.getString(cursor.getColumnIndex("id"));
                    cursor.close();

                        Toast.makeText(getApplicationContext(), "Login Erfolgreich!", Toast.LENGTH_LONG).show();

                        Globals.getInstance();
                        Globals.setvKraftwerkId(vKWid);

                        Intent myIntent = new Intent(MainActivity.this, MainView.class);
                        myIntent.putExtra("VkwId", vkwId);
                        MainActivity.this.startActivity(myIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Es ist ein Fehler aufgetreten: " + e.getMessage());
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

            }
        });

    }
}
