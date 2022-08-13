/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cidade_jardim;

import static auxiliar1.Constantes.PATH_PFCJ;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author wladi
 */
public class CidadeJardim {

    private static List<Integer> listaDeIdsListao;

    public static void inicia() throws FileNotFoundException, IOException {
        File file = new File(PATH_PFCJ);
        File arquivos[] = file.listFiles();
        for (File arquivo : arquivos) {
            String nomeArq = arquivo.getName().toLowerCase();
            if(FilenameUtils.getExtension(nomeArq).equals("xlsx")){
                System.out.println("Igual xlsx:" + arquivo);
                nomeArq = PATH_PFCJ+"\\"+nomeArq;
                lePlanilha(nomeArq);
            }else if(FilenameUtils.getExtension(nomeArq).equals("xls")){
                System.out.println("Igual xls:" + arquivo);
            }
            
            System.out.println("Arq:" + arquivo+ "\t=>"+nomeArq);
        }
    }

    private static void lePlanilha(String arquivo) throws IOException {
        DataFormatter df = new DataFormatter();
        FileInputStream fis = new FileInputStream(arquivo);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> rowIt = sheet.iterator();
        while (rowIt.hasNext()) {
            Row row = rowIt.next();
            Iterator<Cell> celIt = row.iterator();
            while (celIt.hasNext()) {
                Cell cel = celIt.next();
                String x = df.formatCellValue(cel);
                System.out.println("x:" + x);
            }
        }
    }

}
