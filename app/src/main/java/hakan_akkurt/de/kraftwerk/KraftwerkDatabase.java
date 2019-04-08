package hakan_akkurt.de.kraftwerk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Hakan Akkurt on 06.04.2019.
 */


public class KraftwerkDatabase extends SQLiteOpenHelper {
    public static KraftwerkDatabase INSTANCE = null;

    private static final String DB_NAME = "KRAFTWERKE";
    private static final int VERSION = 11;
    private static final String TABLE_NAME = "kraftwerke";

    public static final String ID_COLUMN = "ID";
    public static final String TYPDERERZEUGUNGSANLAGE_COLUMN = "typdererzeugungsanlage";
    public static final String BILDDERANLAGE_COLUMN = "bildderanlage";
    public static final String LEISTUNGINKW_COLUMN = "leistunginkw";
    public static final String ANSCHAFFUNGSDATUM_COLUMN = "anschaffungsdatum";
    public static final String HERSTELLERNAME_COLUMN = "herstellername";
    public static final String KAUFPREIS_COLUMN = "kaufpreis";
    public static final String EINSATZORT_COLUMN ="einsatzort";
    public static final String BETRIEBSDAUER_COLUMN ="betriebsdauer";
    public static final String VIRTUELLEKRAFTWERKID ="virtuellekraftwerkid";



    private KraftwerkDatabase(final Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static KraftwerkDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = new KraftwerkDatabase(context);
        }

        return INSTANCE;
    }

    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + " (" + ID_COLUMN + " INTEGER PRIMARY KEY, "
                + TYPDERERZEUGUNGSANLAGE_COLUMN + " TEXT NOT NULL, "
                + BILDDERANLAGE_COLUMN + " TEXT DEFAULT NULL, " + LEISTUNGINKW_COLUMN + " INTEGER DEFAULT NULL, "
                + ANSCHAFFUNGSDATUM_COLUMN + " INTEGER DEFAULT NULL, " + HERSTELLERNAME_COLUMN + " TEXT DEFAULT NULL, "
                + KAUFPREIS_COLUMN + " INTEGER DEFAULT NULL, " + EINSATZORT_COLUMN + " TEXT DEFAULT NULL, "
                + BETRIEBSDAUER_COLUMN + " INTEGER DEFAULT NULL, " + VIRTUELLEKRAFTWERKID + " INTEGER)";

        sqLiteDatabase.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int i, final int i1) {
        String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(dropTable);

        onCreate(sqLiteDatabase);
    }

    public Kraftwerk createKraftwerk(final Kraftwerk kraftwerk) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TYPDERERZEUGUNGSANLAGE_COLUMN, kraftwerk.getTypDerErzeugungsanlage());
        values.put(BILDDERANLAGE_COLUMN, kraftwerk.getBildDerAnlage());
        values.put(LEISTUNGINKW_COLUMN, kraftwerk.getLeistungInKw());
        values.put(ANSCHAFFUNGSDATUM_COLUMN, kraftwerk.getAnschaffungsdatum() == null ? null : kraftwerk.getAnschaffungsdatum().getTimeInMillis() / 1000);
        values.put(HERSTELLERNAME_COLUMN, kraftwerk.getHerstellerName());
        values.put(KAUFPREIS_COLUMN, kraftwerk.getKaufpreis());
        values.put(EINSATZORT_COLUMN, kraftwerk.getEinsatzort());
        values.put(BETRIEBSDAUER_COLUMN, kraftwerk.getBetriebsdauer());
        values.put(VIRTUELLEKRAFTWERKID, Globals.getvKraftwerkId());

        long newID = database.insert(TABLE_NAME, null, values);

        database.close();

        return readKraftwerk(newID);

    }


    public Kraftwerk readKraftwerk(final long id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[]{ID_COLUMN, TYPDERERZEUGUNGSANLAGE_COLUMN, BILDDERANLAGE_COLUMN, LEISTUNGINKW_COLUMN,
                        ANSCHAFFUNGSDATUM_COLUMN, HERSTELLERNAME_COLUMN, KAUFPREIS_COLUMN, EINSATZORT_COLUMN, BETRIEBSDAUER_COLUMN,
                        VIRTUELLEKRAFTWERKID},
                ID_COLUMN + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        Kraftwerk kraftwerk = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            kraftwerk = new Kraftwerk(cursor.getString(cursor.getColumnIndex(TYPDERERZEUGUNGSANLAGE_COLUMN)));
            kraftwerk.setId(cursor.getLong(cursor.getColumnIndex(ID_COLUMN)));

            Calendar calendar = null;

            if (!cursor.isNull(cursor.getColumnIndex(ANSCHAFFUNGSDATUM_COLUMN))) {
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(ANSCHAFFUNGSDATUM_COLUMN)) * 1000);
            }

            kraftwerk.setBildDerAnlage((cursor.getString(cursor.getColumnIndex(BILDDERANLAGE_COLUMN))));
            kraftwerk.setLeistungInKw(cursor.getString(cursor.getColumnIndex(LEISTUNGINKW_COLUMN)));
            kraftwerk.setAnschaffungsdatum(calendar);
            kraftwerk.setHerstellerName(cursor.getString(cursor.getColumnIndex(HERSTELLERNAME_COLUMN)));
            kraftwerk.setKaufpreis(cursor.getString(cursor.getColumnIndex(KAUFPREIS_COLUMN)));
            kraftwerk.setEinsatzort(cursor.getString(cursor.getColumnIndex(EINSATZORT_COLUMN)));
            kraftwerk.setBetriebsdauer(cursor.getString(cursor.getColumnIndex(BETRIEBSDAUER_COLUMN)));
            kraftwerk.setVirtuelleKraftwerkId(cursor.getString(cursor.getColumnIndex(VIRTUELLEKRAFTWERKID)));

        }

        database.close();

        return kraftwerk;
    }

    public List<Kraftwerk> readAllKraftwerke(final String Parameter) {
        List<Kraftwerk> kraftwerke = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        String query ="SELECT * FROM " + TABLE_NAME + " WHERE virtuellekraftwerkid LIKE " + Globals.getvKraftwerkId();

        //Extra Parameter für das SQL Query hinzufügen
        switch (Parameter) {
            case "DATE":
                query += " order by anschaffungsdatum DESC";
                break;
            case "LEISTUNG":
                query += " order by leistunginkw DESC";
                break;
            case "NONE":
                break;
            default:
                break;
        }
        Cursor cursor = database.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                Kraftwerk kraftwerk = readKraftwerk(cursor.getLong(cursor.getColumnIndex(ID_COLUMN)));
                if (kraftwerk != null) {
                    kraftwerke.add(kraftwerk);
                }
            } while (cursor.moveToNext());
        }

        database.close();

        return kraftwerke;
    }


    public Kraftwerk updateKraftwerk(final Kraftwerk kraftwerk) {
        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(TYPDERERZEUGUNGSANLAGE_COLUMN, kraftwerk.getTypDerErzeugungsanlage());
        values.put(BILDDERANLAGE_COLUMN, kraftwerk.getBildDerAnlage());
        values.put(LEISTUNGINKW_COLUMN, kraftwerk.getLeistungInKw());
        values.put(ANSCHAFFUNGSDATUM_COLUMN, kraftwerk.getAnschaffungsdatum() == null ? null : kraftwerk.getAnschaffungsdatum().getTimeInMillis() / 1000);
        values.put(HERSTELLERNAME_COLUMN, kraftwerk.getHerstellerName());
        values.put(KAUFPREIS_COLUMN, kraftwerk.getKaufpreis());
        values.put(EINSATZORT_COLUMN, kraftwerk.getEinsatzort());
        values.put(BETRIEBSDAUER_COLUMN, kraftwerk.getBetriebsdauer());
        //values.put(VIRTUELLEKRAFTWERKID, kraftwerk.getVirtuelleKraftwerkId());


        database.update(TABLE_NAME, values, ID_COLUMN + " = ?", new String[]{String.valueOf(kraftwerk.getId())});

        database.close();

        return this.readKraftwerk(kraftwerk.getId());
    }


    public Cursor berechneSumme(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("SELECT SUM(leistunginkw) FROM " + TABLE_NAME  + " WHERE virtuellekraftwerkid LIKE " + Globals.getvKraftwerkId(), null);
        return res;
    }


    public void deleteKraftwerk(final Kraftwerk kraftwerk) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, ID_COLUMN + " = ?", new String[]{String.valueOf(kraftwerk.getId())});
        database.close();
    }


    public void deleteAllKraftwerke() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME);
        database.close();
    }

}

