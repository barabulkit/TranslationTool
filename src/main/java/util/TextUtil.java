package util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    static public String removeTriggers(String text) {
        String regex  = "\\{\\{(.*?)\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }

    static public Map<Integer, String> getTriggers(String text) {
        Map<Integer, String> result = new HashMap<Integer, String>();
        String regex  = "\\{\\{(.*?)\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()) {
            result.put(matcher.start(), matcher.group());
        }
        return result;
    }

    static public String removeLinks(String text) {
        String regex  = "\\[\\[(.*?)\\]\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }
}
