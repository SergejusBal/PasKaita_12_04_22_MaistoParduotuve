import Manager.IOmanager;
import Produktai.Produktas;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String path = "C:/Users/Sergejus/IdeaProjects/PasKaita_12_04_22_MaistoParduotuve/src/Resources/Produktai.csv";
        IOmanager iOmanager = new IOmanager();
        Scanner scanner = new Scanner(System.in);
        List<Produktas> krepselis = new ArrayList<>();
        Produktas produktas;

        boolean dirbti = true;
        while(dirbti){
            System.out.println("************************Pasirinkite paslauga: ************************");
            System.out.println("Prideti prekes i parduotuve (1)");
            System.out.println("Isimti prekes is partuotuves pagal ID (2)");
            System.out.println("Isimti prekes is partuotuves pagal Produkto pilna obj (3)");
            System.out.println("Prideti preke i krepseli pagal ID (4)");
            System.out.println("Prideto preke i krepseli pagal Produkto pilna obj (5)");
            System.out.println("Atspausdinti visas krepselio prekes (6)");
            System.out.println("Paskaiciuoti krepselio kaina (7)");
            System.out.println("Atspausdinti visus produktus i konsole (8)");
            System.out.println("Pabaigti darba. (0)");
            int atsakymas;
            atsakymas = nuskanuotiIntVerte();
            switch (atsakymas){
                case 1:
                    iOmanager.pridetiProduktaICSV(path);
                    break;
                case 2:
                    System.out.println("Iveskite prekes ID");
                    iOmanager.pašalintiProduktaIsCSV(nuskanuotiIntVerte(),path);
                    break;
                case 3:
                    iOmanager.pašalintiProduktaIsCSV(iOmanager.nuskanuotiProdukta(),path);
                    break;
                case 4:
                    System.out.println("Iveskite prekes ID");
                    produktas = iOmanager.pašalintiProduktaIsCSV(nuskanuotiIntVerte(),path);
                    if (produktas != null) krepselis.add(produktas);
                    break;
                case 5:
                     produktas = iOmanager.pašalintiProduktaIsCSV(iOmanager.nuskanuotiProdukta(),path);
                    if (produktas != null) krepselis.add(produktas);
                    break;
                case 6:
                    for(Produktas p : krepselis) System.out.println(p);
                    break;
                case 7:
                    iOmanager.skaiciotiKrepsialioKaina(krepselis);
                    break;
                case 8:
                    for(Produktas p : iOmanager.importuotiIsCSV(path)) System.out.println(p);
                    break;
                case 0:
                    System.out.println("UI uzsidaro");
                    dirbti = false;
                    break;
                default:
                    System.out.println("Tokios operacijos nera");
                    break;
            }
        }
    }

    private static int nuskanuotiIntVerte(){
        int i;
        try {
            i = Integer.parseInt(nuskaityti());
        } catch (NumberFormatException e) {
            System.out.println("Ivyko klaida, bandikite dar karta");
            return nuskanuotiIntVerte();
        }
        return i;
    }

    private static String nuskaityti(){
        Scanner scanner = new Scanner(System.in);
        String verte = null;
        try {
            verte = scanner.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return verte;
    }

}