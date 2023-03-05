package com.lanbingo.servidorbingo;

import java.util.Random;

public class Cifrado {
    public static String cifrador(String pass){
        Random rng = new Random();
        char[] chars = pass.toCharArray();
        int cesar = rng.nextInt(1, 9);
        boolean dir = rng.nextBoolean();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if(dir)
                c += cesar;
            else
                c -= cesar;
            chars[i] = c;
        }
        String ciph;
        if(dir)
            ciph = "+" + new String(chars) + cesar + new String(chars) + cesar + new String(chars) + cesar;
        else
            ciph = "-" + new String(chars) + cesar + new String(chars) + cesar + new String(chars) + cesar;
        return ciph;

    }

    public static String descifrador(String cifrado){
        boolean dir;
        dir = cifrado.charAt(0) == '+';
        cifrado = cifrado.substring(1);
        cifrado = cifrado.substring(cifrado.length()/3, ((cifrado.length()/3)*2));
        int cesar = Integer.parseInt(cifrado.substring(cifrado.length()-1));
        cifrado = cifrado.substring(0, cifrado.length()-1);
        char[] chars = cifrado.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if(dir)
                c -= cesar;
            else
                c += cesar;
            chars[i] = c;
        }
        return new String(chars);
    }
}
