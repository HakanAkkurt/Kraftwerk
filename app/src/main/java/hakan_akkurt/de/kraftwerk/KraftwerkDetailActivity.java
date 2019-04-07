package hakan_akkurt.de.kraftwerk;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;


public class KraftwerkDetailActivity extends AppCompatActivity implements TextWatcher, DatePickerDialog.OnDateSetListener{


    public static final String KRAFTWEK_ID_KEY = "KRAFTWERK";

    private EditText typDerErzeugungsanlage;
    private EditText leistungInKw;
    private TextView anschaffungsdatum;
    private EditText herstellerName;
    private EditText kaufpreis;
    private EditText einsatzort;
    private EditText betriebsdauer;
    private EditText virtuelleKraftwerkId;
    private Button update_button;
    private ImageView bildDerAnlage;
    public static final int PICK_IMAGE = 1;
    private static Context context;


    private Kraftwerk kraftwerk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kraftwerk_detail);
        KraftwerkDetailActivity.context = getApplicationContext();

        long id = getIntent().getLongExtra(KRAFTWEK_ID_KEY, 0);
        this.kraftwerk = KraftwerkDatabase.getInstance(this).readKraftwerk(id);

        bildDerAnlage = findViewById(R.id.bildDerAnlage);
        typDerErzeugungsanlage = findViewById(R.id.typDerErzeugungsanlage);
        leistungInKw = findViewById(R.id.leistungInKw);
        anschaffungsdatum = findViewById(R.id.anschaffungsdatum);
        herstellerName = findViewById(R.id.herstellerName);
        kaufpreis = findViewById(R.id.kaufpreis);
        einsatzort =  findViewById(R.id.einsatzort);
        betriebsdauer = findViewById(R.id.betriebsdauer);
        virtuelleKraftwerkId = findViewById(R.id.virtuelleKraftwerkID);

        update_button = findViewById(R.id.update_button);

        if(kraftwerk.getBildDerAnlage() != null){
            File imgFile = new File(kraftwerk.getBildDerAnlage());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                bildDerAnlage.setImageBitmap(myBitmap);
            }
        }

        typDerErzeugungsanlage.setText(kraftwerk.getTypDerErzeugungsanlage());
        leistungInKw.setText(kraftwerk.getLeistungInKw() == null ? " -" : kraftwerk.getLeistungInKw());
        anschaffungsdatum.setText(kraftwerk.getAnschaffungsdatum() == null ? " -" : getDateInString(kraftwerk.getAnschaffungsdatum()));
        leistungInKw.setText(kraftwerk.getLeistungInKw() == null ? " -" : kraftwerk.getLeistungInKw());
        herstellerName.setText(kraftwerk.getHerstellerName() == null ? " -" : kraftwerk.getHerstellerName());
        kaufpreis.setText(kraftwerk.getKaufpreis() == null ? " -" : kraftwerk.getKaufpreis());
        einsatzort.setText(kraftwerk.getEinsatzort() == null ? "-" : kraftwerk.getEinsatzort());
        betriebsdauer.setText(kraftwerk.getBetriebsdauer() == null ? " -" : kraftwerk.getBetriebsdauer());
        virtuelleKraftwerkId.setText(kraftwerk.getVirtuelleKraftwerkId() == null ? " -" : kraftwerk.getVirtuelleKraftwerkId());


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

        this.virtuelleKraftwerkId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                kraftwerk.setVirtuelleKraftwerkId(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (kraftwerk.getTypDerErzeugungsanlage() == null) {
                    Toast.makeText(KraftwerkDetailActivity.this, "Fehler beim Speichern, bitte den Typ der Erzeugungsanlage eingeben.", Toast.LENGTH_SHORT).show();
                    return;
                }

                KraftwerkDatabase.getInstance(KraftwerkDetailActivity.this).updateKraftwerk(kraftwerk);
                Intent intent = new Intent(KraftwerkDetailActivity.this, MainView.class);
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

                KraftwerkCreate kraftwerkCreate = new KraftwerkCreate();
                String imagePath = kraftwerkCreate.SaveImage(bitmap);
                Toast.makeText(KraftwerkDetailActivity.this, imagePath, Toast.LENGTH_LONG).show();
                kraftwerk.setBildDerAnlage(imagePath);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDateSet(final DatePicker datePicker, final int i, final int i1, final int i2) {
        this.anschaffungsdatum.setText(String.format(Locale.GERMANY, "%02d.%02d.%d", i2, i1 + 1, i)); //Datumsformat festlegen

        Calendar c = Calendar.getInstance();
        c.set(i, i1, i2);

        kraftwerk.setAnschaffungsdatum(c);
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


    //Hier wird das Datum in String umgewandelt.
    private String getDateInString(Calendar calendar) {
        return String.format(Locale.GERMANY, "%02d.%02d.%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }


}
