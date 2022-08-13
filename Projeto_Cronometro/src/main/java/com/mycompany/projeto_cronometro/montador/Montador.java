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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wladi
 */
public class Montador {

    private static PreparedStatement prep1 = null;
    private static ResultSet rst1 = null;
    private static Connection conexao1 = null;
    private static final String URL1 = "jdbc:mysql://localhost:3306/db_cavalaria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";

    private static PreparedStatement prep2 = null;
    private static Connection conexao2 = null;
    private static final String URL2 = "jdbc:mysql://localhost:3306/db_cronometro?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";

    private static final String USER = "root";
    private static final String PASSWORD = "@ustralopitecos89";
    private static int total = 0;
    private static int cont = 0;

    public Montador() throws SQLException {
        conexao2 = DriverManager.getConnection(URL2, USER, PASSWORD);
        System.out.println("conex2");
        conexao1 = DriverManager.getConnection(URL1, USER, PASSWORD);
        System.out.println("conex1");
    }

    public void pesquisaDB1() throws SQLException {
        int zero = 0;
        Set<StringBuilder> dados = new LinkedHashSet<>();
        String sql1 = "select a.data_pareo as dataPareo, a.cronometro as crono, a.idCavalo as id, b.tipo_pista as pista"
                + ", b.distancia as distancia, b.pareo as pareo, b.tipo_pareo as tipoPareo, a.corpo_chegada as corpoChegada, a.colocacao as colocacao from listao a inner join pareo b using(pareo) where a.cronometro is not null "
                + "and b.tipo_pista is not null and b.distancia is not null  order by a.data_pareo desc;";
        prep1 = conexao1.prepareStatement(sql1);
        rst1 = prep1.executeQuery();
        while (rst1.next()) {
            StringBuilder stb = new StringBuilder();
            cont++;
            zero++;
            //System.out.println(cont);
            Date data = rst1.getDate("dataPareo");
            String cronometro = rst1.getString("crono");
            int idCavalo = rst1.getInt("id");
            String pista = rst1.getString("pista");
            int distancia = rst1.getInt("distancia");
            String pareo = rst1.getString("pareo");
            String filtro = montaFiltro(distancia, pista);
            String tipoPareo = rst1.getString("tipoPareo");
            String corpoChegada = rst1.getString("corpoChegada");
            int colocacao = rst1.getInt("colocacao");
            float tempo = ConverteCronometroParaFloat.converteCronometro(cronometro);
            stb.append(idCavalo);
            stb.append("#");
            stb.append(cronometro);
            stb.append("#");
            stb.append(filtro);
            stb.append("#");
            stb.append(data);
            stb.append("#");
            stb.append(pareo);
            stb.append("#");
            stb.append(tempo);
            stb.append("#");
            stb.append(tipoPareo);
            stb.append("#");
            stb.append(corpoChegada);
            stb.append("#");
            stb.append(colocacao);
            dados.add(stb);
            if (zero == 100) {
                System.out.println("==> " + cont);
                zero = 0;
            }
        }
        System.out.println("cont: " + cont);
        try {
            insereDados(dados);
        } catch (ParseException ex) {
            Logger.getLogger(Montador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String montaFiltro(int distancia, String pista) {
        pista = pista.trim();
        return distancia + pista;

    }

    private void insereDados(Set<StringBuilder> dados) throws SQLException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql2 = "INSERT INTO cronometro(idCavalo, cronometro, filtro, data, pareo, tempo, tipo_pareo, corpo_chegada, colocacao) values(?,?,?,?,?,?,?,?,?)";
        prep2 = conexao2.prepareStatement(sql2);
        for (StringBuilder stb : dados) {
            System.out.println(total++);
            String l = stb.toString();
            String d[] = l.split("#");
            prep2.setInt(1, Integer.parseInt(d[0]));
            prep2.setString(2, d[1]);
            prep2.setString(3, d[2]);
            //System.out.println("s4="+d[3]);
            java.sql.Date data = new java.sql.Date(sdf.parse(d[3]).getTime());
            prep2.setDate(4, data);
            prep2.setString(5, d[4]);
            float tempo = Float.parseFloat(d[5]);
            prep2.setFloat(6, tempo);
            prep2.setString(7, d[6]);
            prep2.setString(8, d[7]);
            prep2.setInt(9, Integer.parseInt(d[8]));
            int ok = prep2.executeUpdate();
            if(ok<0){
                System.out.println("ERRO:" + d[4]);
            }
        }
        // total += dados.size();
        System.out.println("Total inserido:" + total);
    }

    public void insereColocacao() throws SQLException, ParseException {
        List<String> linhas = new ArrayList<>();
        int colocacao;
        String pareo;
        int idCavalo;
        String sql1 = "SELECT colocacao as colocacao, pareo as pareo, idCavalo as idCavalo FROM listao";
        prep1 = conexao1.prepareStatement(sql1);
        rst1 = prep1.executeQuery();
        while (rst1.next()) {
            colocacao = rst1.getInt("colocacao");
            System.out.println("Data: " + rst1.getDate("data_pareo"));
            pareo = rst1.getString("pareo");
            idCavalo = rst1.getInt("idCavalo");
            String linha = colocacao + "#" + pareo.trim() + "#" + idCavalo;
            linhas.add(linha);
            System.out.println(++cont);
        }
        cont = 0;
        sql1 = "UPDATE cronometro SET colocacao=? WHERE pareo=? and idCavalo=?";
        prep2 = conexao2.prepareStatement(sql1);
        for (String linha : linhas) {
            String d[] = linha.split("#");
            colocacao = Integer.parseInt(d[0]);
            pareo = d[1];
            idCavalo = Integer.parseInt(d[2]);

            prep2.setInt(1, colocacao);
            prep2.setString(2, pareo);
            prep2.setInt(3, idCavalo);
            int ok = prep2.executeUpdate();
            System.out.println(++cont);
        }
    }

    public void insereColocacao_v2() throws SQLException, ParseException {
        String dta = "2022-05-09";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date data = new java.sql.Date(sdf.parse(dta).getTime());
        List<String> linhas = new ArrayList<>();
        int colocacao;
        String pareo;
        int idCavalo;
        String sql1 = "SELECT colocacao as colocacao, pareo as pareo, idCavalo as idCavalo FROM listao WHERE colocacao<4; and data_pareo>?";
        prep1 = conexao1.prepareStatement(sql1);
        prep1.setDate(1, data);
        rst1 = prep1.executeQuery();
        while (rst1.next()) {
            colocacao = rst1.getInt("colocacao");
            System.out.println("Data: " + rst1.getDate("data_pareo"));
            pareo = rst1.getString("pareo");
            idCavalo = rst1.getInt("idCavalo");
            String linha = colocacao + "#" + pareo.trim() + "#" + idCavalo;
            linhas.add(linha);
            System.out.println(++cont);
        }
        cont = 0;
        sql1 = "UPDATE cronometro SET colocacao=? WHERE pareo=? and idCavalo=?";
        prep2 = conexao2.prepareStatement(sql1);
        for (String linha : linhas) {
            String d[] = linha.split("#");
            colocacao = Integer.parseInt(d[0]);
            pareo = d[1];
            idCavalo = Integer.parseInt(d[2]);

            prep2.setInt(1, colocacao);
            prep2.setString(2, pareo);
            prep2.setInt(3, idCavalo);
            int ok = prep2.executeUpdate();
            System.out.println(++cont);
        }
    }

}
