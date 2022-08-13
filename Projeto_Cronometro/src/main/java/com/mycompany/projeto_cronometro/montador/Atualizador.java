/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_cronometro.montador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author wladi Atualiza todo banco de dados 11/06/22
 */
public class Atualizador {

    private static PreparedStatement prep1 = null;
    private static ResultSet rst1 = null;
    private static Connection conexao1 = null;
    private static final String URL1 = "jdbc:mysql://localhost:3306/db_cavalaria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";

    private static PreparedStatement prep2 = null;
    private static ResultSet rst2 = null;
    private static Connection conexao2 = null;
    private static final String URL2 = "jdbc:mysql://localhost:3306/db_cronometro?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";

    private static final String USER = "root";
    private static final String PASSWORD = "@ustralopitecos89";
    private static int total = 0;
    private static int cont = 0;

    public Atualizador() throws SQLException {
        conexao2 = DriverManager.getConnection(URL2, USER, PASSWORD);
        System.out.println("conex2");
        conexao1 = DriverManager.getConnection(URL1, USER, PASSWORD);
        System.out.println("conex1");
    }
    public void inicia() throws SQLException{
        Date lastDate = atualiza();
        atualizaTabela(lastDate);
    }

    private Date atualiza() throws SQLException {
        Date lastDate = null;
        String sql2 = "SELECT * FROM cronometro ORDER BY data DESC LIMIT 1;";
        prep2 = conexao2.prepareStatement(sql2);
        rst2 = prep2.executeQuery();
        if(rst2.next())
            lastDate = rst2.getDate("data");
        System.out.println("-->" + lastDate);
        return lastDate;
    }

    private void atualizaTabela(Date lastDate) {
        
    }
    

}
