package apache_poi_excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class WritingDataToExcelFile {


    public static void main(String[] args) throws IOException {

        // getting the file path
        String filePath = "test_data/WriteData.xlsx";

        // created a workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // created sheet on the workbook
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        // go to the specific row on that sheet
        XSSFRow row = sheet.createRow(0);

        // go to specific cell pon that row
        XSSFCell cell = row.createCell(0);

        // write the value on that cell
        cell.setCellValue("Tech Global");

        // store the file path into the system
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);

        // compete writing process on the file
        workbook.write(fileOutputStream);

        // closing the file after writing completion
        fileOutputStream.close();


    }
}
