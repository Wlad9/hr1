/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_cronometro.montador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author wladi
 */
public class Apagador {

    private static PreparedStatement prep = null;
    private static ResultSet rst = null;
    private static Connection conexao = null;
    private static Connection conexao2 = null;
    private final static String URL = "jdbc:mysql://localhost:3306/db_cronometro?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";
    private final static String USER = "root";
    private final static String PASSWORD = "@ustralopitecos89";

    public Apagador() throws SQLException {
        conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        conexao2 = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void apagaCorridas() throws SQLException {
        conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        String sql = "SELECT * FROM cronometro;";
        prep = conexao.prepareStatement(sql);
        rst = prep.executeQuery();
        while (rst.next()) {
            int idCrono = rst.getInt("idCrono");
            int idCavalo = rst.getInt("idCavalo");
            String pareo = rst.getString("pareo");
            verifica(idCrono, idCavalo, pareo);

        }

    }

    private static void verifica(int id, int idCavalo, String pareo) throws SQLException {
        PreparedStatement prep1 = null;
        ResultSet rst1 = null;
        //String sql = "DELETE FROM cronometro WHERE idCavalo=? and pareo=? and idCrono !=?;";
        String sql = " select * from cronometro where idCavalo = ? and pareo = ? and idCrono !=?";
        prep1 = conexao2.prepareStatement(sql);
        prep1.setInt(1, idCavalo);
        prep1.setString(2, pareo);
        prep1.setInt(3, id);
        rst1 = prep1.executeQuery();
        while (rst1.next()) {
            System.out.println("idCronoRcB:" + id + "\tidCrono:" + rst1.getString("idCrono"));
        }

    }
}
