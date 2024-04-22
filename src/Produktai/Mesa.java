package Produktai;

import java.time.LocalDate;

public class Mesa extends Produktas{
    private String rusis;
    public Mesa(){}

    public Mesa(int kodas, String pavadinimas, double kaina, LocalDate galiojimoData, String rusis) {
        super(kodas, pavadinimas, kaina, galiojimoData);
        this.rusis = rusis;
    }

    public void setRusis(String rusis) {
        this.rusis = rusis;
    }
    public String getRusis() {
        return rusis;
    }

    @Override
    public String toString() {
        return  "Kodas: " + super.getKodas() +
                " ** Pavadinimas: " +  super.getPavadinimas() +
                " ** Kaina: " + super.getKaina() +
                " ** Galiojimo Data: " + super.getGaliojimoData().toString()+
                " ** Rusis: " + this.rusis;
    }

    @Override
    public String toCSVString() {
        return String.format("%d,%s,%f,%s,%s,%s", super.getKodas(), super.getPavadinimas(), super.getKaina() ,super.getGaliojimoData().toString(),"Mesa", this.getRusis());
    }



}
