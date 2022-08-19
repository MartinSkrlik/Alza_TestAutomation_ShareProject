package config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.DatatypeConverter;
import utility.ConfigFileReader;

public class BaseConfig {
    
    private static int i;
    private static List<Integer> maskArray;
        
    protected static String decryptValue(String key) {       
        String original     = ConfigFileReader.getValueFromProperties(key);
        String escaped      = escapeChars(original);
        String decoded1     = decodeBase64(escaped);
        String simpleDecr   = simpleDecryption(decoded1);  
        String decoded2     =  decodeBase64(simpleDecr);
        String advancedDecr = advancedDecryption(decoded2);
        
        return advancedDecr;
    }
    
    private static String escapeChars(String text) {
        return text.replaceAll("\\\\r", "\r").
                    replaceAll("\\\\n", "\n");
    }
    
    private static String decodeBase64(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString.getBytes());      
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
       
    public static String encode(String value) {
        return  DatatypeConverter.printBase64Binary
            (value.getBytes(StandardCharsets.UTF_8));
     }
    
    private static String simpleDecryption(String decoded) {        
        List<String> chars = 
            decoded.chars()
                   .mapToObj(ch -> Integer.toBinaryString((int)ch >> 1) )
                   .collect(Collectors.toList());
        
        return convertBinaryToString(chars);
    }
    
    private static String convertBinaryToString(List<String> chars) {
        StringBuilder sb = new StringBuilder();
        chars.stream().forEach(ch -> sb.append((char) Integer.parseInt(ch, 2)));
        return sb.toString();
    }
    
    private static String advancedDecryption(String decoded2) {
        i = 0;
        int[] mask = new int[]{ 
                (char)71, (char)85, (char)73, (char)84, (char)101, (char)115, (char)116, 
                (char)69, (char)110, (char)103, (char)105, (char)110, (char)101 };
        
        maskArray = new ArrayList<>();
        for (int chars: mask) {
            maskArray.add(chars);
        }
        
        List<String> chars = 
                decoded2.chars()
                        .mapToObj(ch -> shift(ch))
                        .collect(Collectors.toList());       
        return convertBinaryToString(chars);
    }
    
    private static String shift(int ch) {
        if (i >= maskArray.size()) {
            i = 0;
        }
        
        String text = Integer.toBinaryString(((int)ch ^ maskArray.get(i)) >> 1);
        i ++;
        return text;
    } 
}