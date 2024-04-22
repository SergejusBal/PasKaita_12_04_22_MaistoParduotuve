package Manager;

import Produktai.*;
import java.util.List;

public interface CSVImportExport {

    List<Produktas> importuotiIsCSV(String path);

    void eksportuotiICSV(List<Produktas> produktai, String path);

    void pridetiProduktaICSV(String path);

    Produktas pašalintiProduktaIsCSV(Produktas produktas, String path);
    Produktas pašalintiProduktaIsCSV(int produktoKodas, String path);

}
