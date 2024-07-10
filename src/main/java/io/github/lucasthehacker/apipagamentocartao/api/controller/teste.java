package io.github.lucasthehacker.apipagamentocartao.api.controller;

public class teste {

//    public static boolean isNumber(char c) {
//        if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' )
//            return true;
//        else
//            return false;
//    }

    public static void main(String[] args) {



        Long cpnj = 97441842000110L;

        String cpnj_stringado = cpnj.toString();

        for (int i = 0; 1 < cpnj_stringado.length(); i++) {

            if (i > 2 & i < 7) {
                cpnj_stringado.replace(cpnj_stringado.charAt(i), '*');
            }

            if (i > 7 & i < cpnj_stringado.length()) {
                cpnj_stringado.replace(cpnj_stringado.charAt(i), '*');
            }

        }

        System.out.println(cpnj_stringado);


    }
}
