/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projeto_cronometro.montador;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author wladi
 */
public class ConverteCronometroParaFloat {

    public static float converteCronometro(String tc) {
        tc = tc.toUpperCase();
        tc = tc.trim();
        //System.out.println("tc:" + tc);
        float numero = 0;
        if (!(tc.equals("NULL") | tc.equals("NOINFO"))) {
            if (tc.contains("M")) {
                String minuto = tc.substring(0, tc.indexOf("M"));
                String segundo = tc.substring(tc.indexOf("M") + 1, tc.indexOf("S"));
                String centesimo = tc.substring(tc.indexOf("S") + 1);
                centesimo = "0." + centesimo;
                int m1 = Integer.parseInt(minuto);
                float s1 = Float.parseFloat(segundo);
                float c1 = Float.parseFloat(centesimo);
                numero = m1 * 60 + s1 + c1;
            } else {
                String segundo = tc.substring(0, tc.indexOf("S"));
                String centesimo = tc.substring(tc.indexOf("S") + 1);
                String nr = segundo + "." + centesimo;
                numero = Float.parseFloat(nr);
            }
        }
        return numero;
    }

    public static Set<Float> converteCronometro(Set<String> tmp) {
        Set<Float> tempos = new HashSet<>();
        for(String t: tmp){
            tempos.add(converteCronometro(t));
        }
        return tempos;
    }
}
