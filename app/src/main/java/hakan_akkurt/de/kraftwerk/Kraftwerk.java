package hakan_akkurt.de.kraftwerk;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Hakan Akkurt on 06.04.2019
 */

public class Kraftwerk implements Serializable {
    private long id;
    private String bildDerAnlage;
    private String typDerErzeugungsanlage;
    private String leistungInKw;
    private Calendar anschaffungsdatum;
    private String herstellerName;
    private String kaufpreis;
    private String einsatzort;
    private String betriebsdauer;
    private String virtuelleKraftwerkId;

    //Die Defeaultwerte von Kraftwerke auf null setzen
    public Kraftwerk() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public Kraftwerk(final String typDerErzeugungsanlage) {
        this(typDerErzeugungsanlage, null, null, null, null, null, null, null, null);
    }

    public Kraftwerk(final String typDerErzeugungsanlage, final String bildDerAnlage, final String leistungInKw, final Calendar anschaffungsdatum, final String herstellerName,
                     final String kaufpreis, final String einsatzort, final String betriebsdauer, final String virtuelleKraftwerkId) {
        this.typDerErzeugungsanlage = typDerErzeugungsanlage;
        this.bildDerAnlage = bildDerAnlage;
        this.leistungInKw = leistungInKw;
        this.anschaffungsdatum = anschaffungsdatum;
        this.herstellerName = herstellerName;
        this.kaufpreis = kaufpreis;
        this.einsatzort = einsatzort;
        this.betriebsdauer = betriebsdauer;
        this.virtuelleKraftwerkId = virtuelleKraftwerkId;
    }

    //Getter und Setter Methoden
    public long getId() {
        return id;
    }

    public String getBildDerAnlage() {
        return bildDerAnlage;
    }

    public void setBildDerAnlage(String bildDerAnlage) {
        this.bildDerAnlage = bildDerAnlage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypDerErzeugungsanlage() {
        return typDerErzeugungsanlage;
    }

    public void setTypDerErzeugungsanlage(String typDerErzeugungsanlage) {
        this.typDerErzeugungsanlage = typDerErzeugungsanlage;
    }

    public String getLeistungInKw() {
        return leistungInKw;
    }

    public void setLeistungInKw(String leistungInKw) {
        this.leistungInKw = leistungInKw;
    }

    public Calendar getAnschaffungsdatum() {
        return anschaffungsdatum;
    }

    public void setAnschaffungsdatum(Calendar anschaffungsdatum) {
        this.anschaffungsdatum = anschaffungsdatum;
    }

    public String getHerstellerName() {
        return herstellerName;
    }

    public void setHerstellerName(String herstellerName) {
        this.herstellerName = herstellerName;
    }

    public String getKaufpreis() {
        return kaufpreis;
    }

    public void setKaufpreis(String kaufpreis) {
        this.kaufpreis = kaufpreis;
    }

    public String getEinsatzort() {
        return einsatzort;
    }

    public void setEinsatzort(String einsatzort) {
        this.einsatzort = einsatzort;
    }

    public String getBetriebsdauer() {
        return betriebsdauer;
    }

    public void setBetriebsdauer(String betriebsdauer) {
        this.betriebsdauer = betriebsdauer;
    }

    public String getVirtuelleKraftwerkId() {
        return virtuelleKraftwerkId;
    }

    public void setVirtuelleKraftwerkId(String virtuelleKraftwerkId) {
        this.virtuelleKraftwerkId = virtuelleKraftwerkId;
    }

}



