package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    private static Logger logger = LogManager.getLogger(ExcelUtil.class);
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static String filepath;



    public static void openExcelFile(String fileName, String sheetName){

        filepath = "test_data/" + fileName + ".xlsx";

        try{
            FileInputStream fileInputStream = new FileInputStream(filepath);
            workbook = new XSSFWorkbook(fileInputStream);
            logger.info("File " + fileName + " is exist!");
            sheet = workbook.getSheet(sheetName);
            logger.info("Sheet " + sheetName + " is exist!");

        }catch (Exception e){
            logger.debug(fileName + " and " + sheetName + " cannot be found");
        }
    }

    // getting the single value from Excel file
    public static String getValue(int rowNumber, int cellNumber){

        row = sheet.getRow(rowNumber);
        cell = row.getCell(cellNumber);
        cell.setCellType(CellType.STRING);
        return cell.toString();
    }

    //getting all the values from Excel file
    public static List<List<String>> getValues(){
       // creating list of list to store all the values from Excel file
        List<List<String>> allValues = new ArrayList<>();

        // creating loops for getting the rows
        for (int r = sheet.getFirstRowNum() + 1; r <= sheet.getLastRowNum(); r++) {
            // creating a row in the sheet
            row = sheet.getRow(r);

            // creating a list to store row data
            List<String> eachRow = new ArrayList<>();

            //looping each cell in the corresponding row
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                // get each cell values and add then into eachRow list
                eachRow.add(getValue(r,c));
            }
            // adding each list of row to the list of list
            allValues.add(eachRow);
        }
        // returning list of the list
        return allValues;
    }

    public static String[][] convertListOfListToMultidimensionalArray(List<List<String>> listOfList){

        // create multidimensional array
        String[][] result = new String[listOfList.size()][];

        // loop the lists
        for(int i = 0; i < listOfList.size(); i++){

            // getting the values from each list
            List<String> currentList = listOfList.get(i);

            // converting List to array and at to multidimensional array
            result[i] = currentList.toArray(new String[currentList.size()]);
        }
        return result;
    }

    // closing the workbook
    public static void closingExcelFile(){
        try{
            workbook.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
