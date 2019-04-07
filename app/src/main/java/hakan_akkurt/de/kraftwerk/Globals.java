package hakan_akkurt.de.kraftwerk;

public class Globals {
    private static Globals ourInstance = new Globals();

    public static Globals getInstance() {
        return ourInstance;
    }

    private Globals() {
    }

    private static String vKraftwerkId;

    public static String getvKraftwerkId() {
        return vKraftwerkId;
    }

    public static void setvKraftwerkId(String vKraftwerkId) {
        Globals.vKraftwerkId = vKraftwerkId;
    }
}
