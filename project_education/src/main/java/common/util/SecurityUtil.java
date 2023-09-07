package common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author cslee
 *
 */
public class SecurityUtil {

    private SecurityUtil(){}
    
    /**
     * SHA-256 이코딩 처리
     * @param str
     * @return
     */
    public static String getSHA256Hash(String str){
        String SHA = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            System.out.println("** Error SHA-256 encoding" + e);
            SHA = null;
        }
        return SHA;
    }
    
    /**
     * cross site  script 오류를 막기위한 처리 함수
     * 참고 : http://book2s.com/java/java-method/string/removexss.html
     * @param string
     * @return
     */
    public static String removeXss(String string) {
        String rtn = string;
        if(string != null) {
            rtn = string.replaceAll("(?i)<script.*?>.*?</script.*?>", "")       // Remove all <script> tags.
                        .replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "")     // Remove tags with javascript: call.
                        .replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");         // Remove tags with on* attributes.
        }
        return rtn;
    }
}
