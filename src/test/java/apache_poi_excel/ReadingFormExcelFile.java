package apache_poi_excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadingFormExcelFile {

    static Logger logger = LogManager.getLogger(ReadingFormExcelFile.class);

    public static void main(String[] args) throws IOException {

        // assigning the file path
        String excelFilePath = "test_data/ReadData.xlsx";

        // Reaching out the file
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);

        // opening the file where we specify the path
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

        // going into specific sheet in the workbook
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        // getting the first name from the file
        String firstName = sheet.getRow(1).getCell(0).getStringCellValue();
        logger.info("First name from the first sell is " + firstName);

        // getting the second id from the file
        double secondId = sheet.getRow(2).getCell(1).getNumericCellValue();
        logger.info("The second id value is " + secondId );

        // getting the last row number from the file
        int lastRow = sheet.getLastRowNum();
        logger.info("The last roe number is " + lastRow);

        //getting the last cell number
        int lastCell = sheet.getRow(1).getLastCellNum();
        logger.info("The last cell number is " + lastCell);

        /**
         * Rows are by index, cell are by number of the cells
         */

        // looping each data (getting all the data)
        for (int r = 0; r <= lastRow; r++) {

            // visiting each row
            XSSFRow row = sheet.getRow(r);

            // looping each cell with corresponding row
            for(int c = 0; c < lastCell; c++){

                // getting each cell value from that row
                XSSFCell cell = row.getCell(c);

                System.out.print(cell + " | ");
            }
            System.out.println();
        }
    }
}
