package apache_poi_excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HomeworkExcel {

    static Logger logger = LogManager.getLogger(HomeworkExcel.class);

    public static void main(String[] args) throws IOException {

        // assigning the file path
        String exelFilePath = "test_data/TestDataHomework.xlsx";

        // Reaching out the file
        FileInputStream fileInputStream = new FileInputStream(exelFilePath);

        // opening the file where we specify the path
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

        // going into specific sheet in the workbook
        XSSFSheet sheet = xssfWorkbook.getSheet("Sheet1");

        // getting the Chemistry from the file
        double chemistry = sheet.getRow(1).getCell(1).getNumericCellValue();
        logger.info(chemistry);

        // getting the last row number from the file
        int lastRow = sheet.getLastRowNum();
        logger.info(lastRow);

        //getting the last cell number
        int lastCell = sheet.getRow(1).getLastCellNum();
        logger.info(lastCell);
        for (int r = 0; r <= lastRow; r++) {
            // visiting each row
            XSSFRow row = sheet.getRow(r);

            // looping each cell with corresponding row
            for (int c = 0; c < lastCell; c++) {
                XSSFCell cell = row.getCell(c);
                //Printing each cell
                System.out.print(cell + " | ");
            }
            System.out.println();
        }

    }
}
