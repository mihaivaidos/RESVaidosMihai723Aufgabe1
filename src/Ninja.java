public class Ninja {

    private int ID;
    private String characterName;
    private String stufe;
    private String beschreibung;
    private String datum;
    private double kraftpunkte;

    @Override
    public String toString() {
        return "Ninja{" +
                "ID=" + ID +
                ", characterName='" + characterName + '\'' +
                ", stufe='" + stufe + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", datum='" + datum + '\'' +
                ", kraftpunkte=" + kraftpunkte +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getStufe() {
        return stufe;
    }

    public void setStufe(String stufe) {
        this.stufe = stufe;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public double getKraftpunkte() {
        return kraftpunkte;
    }

    public void setKraftpunkte(double kraftpunkte) {
        this.kraftpunkte = kraftpunkte;
    }

    public Ninja(int ID, String characterName, String stufe, String beschreibung, String datum, double kraftpunkte) {
        this.ID = ID;
        this.characterName = characterName;
        this.stufe = stufe;
        this.beschreibung = beschreibung;
        this.datum = datum;
        this.kraftpunkte = kraftpunkte;
    }
}
