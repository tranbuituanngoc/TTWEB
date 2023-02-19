package Util;

import java.util.Random;

public class SaltString {
    public static String getSaltString() {
        String SALTCHARS = "abcdefghiklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getSaltString());
        }
    }
}
