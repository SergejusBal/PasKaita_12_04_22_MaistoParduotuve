package Produktai;

import java.time.LocalDate;

public abstract class Produktas {

    private int kodas;
    private String pavadinimas;
    private double kaina;
    private LocalDate galiojimoData;

    public Produktas(){}

    public Produktas(int kodas, String pavadinimas, double kaina, LocalDate galiojimoData) {
        this.kodas = kodas;
        this.pavadinimas = pavadinimas;
        this.kaina = kaina;
        this.galiojimoData = galiojimoData;
    }

    public void setKodas(int kodas) {
        this.kodas = kodas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public void setKaina(double kaina) {
        this.kaina = kaina;
    }

    public void setGaliojimoData(LocalDate galiojimoData) {
        this.galiojimoData = galiojimoData;
    }

    public int getKodas() {
        return kodas;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public double getKaina() {
        return kaina;
    }

    public LocalDate getGaliojimoData() {
        return galiojimoData;
    }


    public abstract String toCSVString();

    @Override
    public String toString() {
        return  "Kodas: " + kodas +
                " Pavadinimas: " +  pavadinimas +
                " Kaina: " + kaina +
                " Galiojimo Data: " + galiojimoData;
    }
}
