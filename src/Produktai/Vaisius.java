package Produktai;

import java.time.LocalDate;

public class Vaisius extends Produktas{

    private boolean arEkologiskas;

    public Vaisius(){}

    public Vaisius(int kodas, String pavadinimas, double kaina, LocalDate galiojimoData, boolean arEkologiskas) {
        super(kodas, pavadinimas, kaina, galiojimoData);
        this.arEkologiskas = arEkologiskas;
    }

    public void setArEkologiskas(boolean arEkologiskas) {
        this.arEkologiskas = arEkologiskas;
    }

    public boolean getArEkologiskas() {
        return arEkologiskas;
    }

    @Override
    public String toString() {
        return  "Kodas: " + super.getKodas() +
                " ** Pavadinimas: " +  super.getPavadinimas() +
                " ** Kaina: " + super.getKaina() +
                " ** Galiojimo Data: " + super.getGaliojimoData().toString()+
                " ** Ar ekologiskas: " + this.arEkologiskas;

    }
    @Override
    public String toCSVString() {
        return String.format("%d,%s,%.2f,%s,%s,%b", super.getKodas(), super.getPavadinimas(), super.getKaina(), super.getGaliojimoData().toString(), "Vaisius",  this.getArEkologiskas());
    }
}
