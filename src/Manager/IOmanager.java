package Manager;

import Produktai.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOmanager implements CSVImportExport{
    private Scanner scanner;

    private static String ERRORZINUTE = "Ivyko klaida, bandikite dar karta!";

    public IOmanager() {
        scanner = new Scanner(System.in);

    }

    @Override
    public List<Produktas> importuotiIsCSV(String path) {
        Produktas produktas;
        List<Produktas> prodouktuListas = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] lineValues = line.split(",");

                if(lineValues[5].equalsIgnoreCase("true") || lineValues[5].equalsIgnoreCase("false")){
                    produktas = new Vaisius();
                    ((Vaisius) produktas).setArEkologiskas(Boolean.parseBoolean(lineValues[5]));
                }
                else {
                    produktas = new Mesa();
                    ((Mesa) produktas).setRusis(lineValues[5]);
                }
                produktas.setKodas(Integer.parseInt(lineValues[0]));
                produktas.setPavadinimas(lineValues[1]);
                produktas.setKaina(Double.parseDouble(lineValues[2]));
                produktas.setGaliojimoData(LocalDate.parse(lineValues[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                prodouktuListas.add(produktas);

            }
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println("Nepavyko skaityti failo: " + e.getMessage());
        }
        return prodouktuListas;
    }

    @Override
    public void eksportuotiICSV(List<Produktas> produktai, String path) {
        for(Produktas p : produktai) irasytiProduktaAppend(p, path);
    }

    @Override
    public void pridetiProduktaICSV(String path) {
        Produktas produktas = nuskanuotiProdukta();
        this.irasytiProduktaAppend(produktas,path);
    }

    @Override
    public Produktas pašalintiProduktaIsCSV(Produktas produktas, String path) {
        List<Produktas> produktuList = importuotiIsCSV(path);
        int index=-1;
        for(int i = 0; i < produktuList.size(); i++){
            if (
                            produktas instanceof Vaisius &&
                            produktas.getPavadinimas().equals(produktuList.get(i).getPavadinimas()) &&
                            produktas.getKaina() == produktuList.get(i).getKaina() &&
                            produktas.getGaliojimoData().equals(produktuList.get(i).getGaliojimoData()) &&
                            produktas.getKodas() == produktuList.get(i).getKodas() &&
                            ((Vaisius)produktas).getArEkologiskas() == ((Vaisius) produktuList.get(i)).getArEkologiskas()
            ) {
                index = i;
                break;
            }
            else if (
                            produktas instanceof Mesa &&
                            produktas.getPavadinimas().equals(produktuList.get(i).getPavadinimas()) &&
                            produktas.getKaina() == produktuList.get(i).getKaina() &&
                            produktas.getGaliojimoData().equals(produktuList.get(i).getGaliojimoData()) &&
                            produktas.getKodas() == produktuList.get(i).getKodas() &&
                            ((Mesa)produktas).getRusis().equals(((Mesa) produktuList.get(i)).getRusis())
            ) {
                index = i;
                break;
            }
        }
        Produktas produktasIstrintas;
        if (index != -1) produktasIstrintas = produktuList.remove(index);
        else {
            System.out.println("Tokio produkto nera");
            return null;
        }

        File file = new File(path);
        istrinti(file);
        eksportuotiICSV(produktuList,path);
        return produktasIstrintas;
    }

    @Override
    public Produktas pašalintiProduktaIsCSV(int produktoKodas, String path) {
        List<Produktas> produktuList = importuotiIsCSV(path);
        int index=-1;
        for(int i = 0; i < produktuList.size(); i++) {
            if (produktoKodas == produktuList.get(i).getKodas()){
                index = i;
                break;
            }
        }
        Produktas produktasIstrintas;
        if (index != -1) produktasIstrintas = produktuList.remove(index);
        else {
            System.out.println("Tokio kodo nera");
            return null;
        }

        File file = new File(path);
        istrinti(file);
        eksportuotiICSV(produktuList,path);
        return produktasIstrintas;
    }

    public void skaiciotiKrepsialioKaina(List<Produktas> produktai){
        Double suma = 0.0;
        for(Produktas p: produktai){
            suma += p.getKaina();
        }
        System.out.println("Krepsialio kaina yra: " + suma);

    }

    public Produktas nuskanuotiProdukta(){
        System.out.println("Koki Produkta norite prideti: vaisiai (1), mesa (2)");
        int atsakymas = nuskanuotiIntVerte();
        Produktas produkas = null;
        switch (atsakymas){
            case 1:
                produkas = new Vaisius();
                System.out.println("Iveskite tipa: Ekologiskas (1), Neekologiskas (2)");
                ((Vaisius) produkas).setArEkologiskas(nuskanuotiBoleanVerte());
                break;
            case 2:
                produkas = new Mesa();
                System.out.println("Iveskite mesos rusi:");
                ((Mesa) produkas).setRusis(nuskanuotiStringVerte());
                break;
            default:
                System.out.println(ERRORZINUTE);
                nuskanuotiProdukta();
                break;
        }
        System.out.println("Iveskite produkto pavadinima:");
        produkas.setPavadinimas(nuskanuotiStringVerte());
        System.out.println("Iveskite produkto koda:");
        produkas.setKodas(nuskanuotiIntVerte());
        System.out.println("Iveskite produkto kaina:");
        produkas.setKaina(nuskanuotiDoubleVerte());
        System.out.println("Iveskite produkto galiojimo laika");
        produkas.setGaliojimoData(nuslanuotiLocalDate());


        return produkas;
    }

    private void irasytiProduktaAppend(Produktas produkas, String path){
        try {
            FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(produkas.toCSVString());
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Nepavyko rasyti failo: " + e.getMessage());
        }
    }
    private void istrinti(File file){
        try {
            Files.deleteIfExists(Paths.get(file.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDate nuslanuotiLocalDate(){

        System.out.println("Iveskite metus:");
        int metai;
        while(true) {
            metai = nuskanuotiIntVerte();
            if (2030 < metai || metai < 2000) {
                System.out.println(ERRORZINUTE);
                continue;
            }
            break;
        }

        System.out.println("Iveskite mienesi:");
        int mieno;
        while(true) {
            mieno = nuskanuotiIntVerte();
            if (1 > mieno || mieno > 12){
                System.out.println(ERRORZINUTE);
                continue;
            }
            break;
        }

        System.out.println("Iveskite diena:");
        int diena;
        while(true) {
            diena = nuskanuotiIntVerte();
            if (1 > diena || diena > 32){
                System.out.println(ERRORZINUTE);
                continue;
            }
            break;
        }

        LocalDate localDate;
        localDate = LocalDate.parse((metai + "-" + String.format("%02d", mieno) + "-"+ String.format("%02d", diena)), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return localDate;
    }

    private boolean nuskanuotiBoleanVerte(){
        int verte;
        while(true) {
            verte = nuskanuotiIntVerte();
            if (1 > verte || verte > 2) {
                System.out.println(ERRORZINUTE);
                continue;
            }
            break;
        }
        if (verte == 1) return true;
        else return false;

    }

    private String nuskanuotiStringVerte(){
        return nuskaityti();
    }

    private int nuskanuotiIntVerte(){
        int i;
        try {
            i = Integer.parseInt(nuskaityti());
        } catch (NumberFormatException e) {
            System.out.println(ERRORZINUTE);
            return nuskanuotiIntVerte();
        }
        return i;
    }

    private double nuskanuotiDoubleVerte(){
        double i;
        try {
            i = Double.parseDouble(nuskaityti());
        } catch (NumberFormatException e) {
            System.out.println(ERRORZINUTE);
            return nuskanuotiDoubleVerte();
        }
        return i;
    }

    private String nuskaityti(){
        String verte = null;
        try {
            verte = scanner.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return verte;
    }

}
