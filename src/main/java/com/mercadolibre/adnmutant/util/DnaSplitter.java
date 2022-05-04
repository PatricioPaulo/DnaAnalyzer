package com.mercadolibre.adnmutant.util;

public class DnaSplitter {

    public static String[] dnaSplitter(String sequence){
        return sequence.split("-");
    }

    public static String composeDna(String[] sequence){
        return String.join("-", sequence);
    }
}
