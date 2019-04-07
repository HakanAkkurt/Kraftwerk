package hakan_akkurt.de.kraftwerk;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;


public class KraftwerkCreate extends AppCompatActivity implements TextWatcher, DatePickerDialog.OnDateSetListener{

    private Kraftwerk kraftwerk;
    private EditText typDerErzeugungsanlage;
    private EditText leistungInKw;
    private TextView anschaffungsdatum;
    private EditText herstellerName;
    private EditText kaufpreis;
    private EditText einsatzort;
    private EditText betriebsdauer;
    private TextView virtuelleKraftwerkId;
    private ImageView bildDerAnlage;
    private Button submit;
    public static final int PICK_IMAGE = 1;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kraftwerk_create);
        KraftwerkCreate.context = getApplicationContext();

        this.kraftwerk = new Kraftwerk();

        this.bildDerAnlage = findViewById(R.id.bildDerAnlage);
        this.typDerErzeugungsanlage =  findViewById(R.id.typDerErzeugungsanlage);
        this.leistungInKw = findViewById(R.id.leistungInKw);
        this.herstellerName = findViewById(R.id.herstellerName);
        this.kaufpreis = findViewById(R.id.kaufpreis);
        this.einsatzort = findViewById(R.id.einsatzort);
        this.betriebsdauer = findViewById(R.id.betriebsdauer);
        this.virtuelleKraftwerkId = findViewById(R.id.virtuelleKraftwerkID);

        virtuelleKraftwerkId.setText(Globals.getvKraftwerkId());

        this.submit = findViewById(R.id.submit);

        this.anschaffungsdatum =  findViewById(R.id.anschaffungsdatum);

        this.anschaffungsdatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        //Listener für Texteingabe
        this.typDerErzeugungsanlage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                kraftwerk.setTypDerErzeugungsanlage(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.leistungInKw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                kraftwerk.setLeistungInKw(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.herstellerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                kraftwerk.setHerstellerName(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.kaufpreis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                kraftwerk.setKaufpreis(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.einsatzort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                kraftwerk.setEinsatzort(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.betriebsdauer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                kraftwerk.setBetriebsdauer(editable.toString().length() == 0 ? null : editable.toString());
            }
        });


        this.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (kraftwerk.getTypDerErzeugungsanlage() == null) {
                    Toast.makeText(KraftwerkCreate.this, "Fehler beim Speichern, bitte den Typ der Erzeugungsanlage eingeben.", Toast.LENGTH_SHORT).show();
                    return;
                }

                KraftwerkDatabase.getInstance(KraftwerkCreate.this).createKraftwerk(kraftwerk);
                Intent intent = new Intent(KraftwerkCreate.this, MainView.class);
                startActivity(intent);
            }
        });


        this.bildDerAnlage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bildDerAnlage.setImageBitmap(bitmap);

                String imagePath = SaveImage(bitmap);
                Toast.makeText(KraftwerkCreate.this, imagePath, Toast.LENGTH_LONG).show();
                kraftwerk.setBildDerAnlage(imagePath);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "/Image-"+ n +".png";
        File file = new File(root + fname );

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    @Override
    public void onDateSet(final DatePicker datePicker, final int i, final int i1, final int i2) {
        this.anschaffungsdatum.setText(String.format(Locale.GERMANY, "%02d.%02d.%d", i2, i1 + 1, i));  //Datumsformat festlegen

        Calendar c = Calendar.getInstance();
        c.set(i, i1, i2);

        kraftwerk.setAnschaffungsdatum(c);//Das Systemdatum wird als Defaultwert genommen
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
