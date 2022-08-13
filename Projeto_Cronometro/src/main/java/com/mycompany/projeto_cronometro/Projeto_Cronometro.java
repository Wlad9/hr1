/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.projeto_cronometro;

import cidade_jardim.CidadeJardim;
import com.mycompany.projeto_cronometro.montador.Apagador;
import com.mycompany.projeto_cronometro.montador.Atualizador;
import com.mycompany.projeto_cronometro.montador.Montador;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import view.AtualizaTabelaCronometro;

/**
 *
 * @author wladi
 */
public class Projeto_Cronometro {

    public static void main(String[] args) throws SQLException, ParseException, FileNotFoundException, IOException {

//        CidadeJardim cj = new CidadeJardim();
//        cj.inicia();
        
//        
//        AtualizaTabelaCronometro atc = new AtualizaTabelaCronometro();
//        atc.setVisible(true);
        
//        Atualizador atualiza = new Atualizador();
//        atualiza.inicia();
        
        
        Montador monta = new Montador();
        monta.pesquisaDB1();

        //monta.insereColocacao();
//        System.out.println("APAGADOR\n\n\n\n\n");
//        Apagador apaga = new Apagador();
//        apaga.apagaCorridas();
    }

}
