package Util;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Encode {
    public static String encodeToSHA1(String str){
        String res=null;
        String salt="321ew21fwe21Fsd45";

        str=str+salt;
        try {
            byte[] data=str.getBytes(StandardCharsets.UTF_8);
            MessageDigest messageDigest= MessageDigest.getInstance("SHA-1");
            res= new String(Base64.encodeBase64(messageDigest.digest(data)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

        public static String decodeSHA1(String hashedPasswordStored, String saltString) {
            String res = null;
            byte[] salt = Base64.decodeBase64(saltString.getBytes());
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(salt);
                md.update(hashedPasswordStored.getBytes(StandardCharsets.UTF_8));
                byte[] hashed = md.digest();
                res = new String(hashed, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }



    public static void main(String[] args) {
        String hashedPasswordStored = "gIShZmhio2CGLPBxG40sxrYW5S0=";
        String saltString = "321ew21fwe21Fsd45";
        String decodedPassword = decodeSHA1(hashedPasswordStored, saltString);
        System.out.println(decodedPassword);

    }
}
