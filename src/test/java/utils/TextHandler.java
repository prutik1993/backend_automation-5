package utils;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class TextHandler {
    public static int getInt(String str){
        return Integer.parseInt(str.replaceAll("[^0-9]",""));
    }
    public static List<Integer> returnListOfInteger(List<WebElement> list){
        List<Integer> answer = new ArrayList<>();
        for (WebElement element : list) {
         answer.add(Integer.parseInt(element.getText()));
        }
        return answer;
    }

    public static long getLong(String str){
        return Long.parseLong(str.replaceAll("[^0-9]",""));
    }
}
