package Util;

import model.ProductColor;
import model.ProductSize;
import service.ProductColorService;
import service.ProductDetailsService;
import service.ProductSizeService;

import java.util.ArrayList;
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
//        for (int i = 0; i < 10; i++) {
//            System.out.println(getSaltString());
//        }
        ArrayList<Integer> sizes = new ArrayList<Integer>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int a = 0; a < 10; a++) {
            sizes.add(a);
        }
        for (int j = 10; j < 20; j++) {
            colors.add(j);
        }
        for (int s : sizes) {
            for (int c : colors) {
                System.out.println(s+" "+c);
            }
        }
    }
}
