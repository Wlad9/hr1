/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelDAO;

import com.mycompany.projeto_cronometro.montador.ConverteCronometroParaFloat;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Cronometro;

/**
 *
 * @author wladi
 */
public class Cronometro_DAO {
    
    private static PreparedStatement prep2 = null;
    private static ResultSet rst2 = null;
    private static Connection conexao2 = null;
    private static String URL2 = "jdbc:mysql://localhost:3306/db_cronometro?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";
    
    private static PreparedStatement prep1 = null;
    private static ResultSet rst1 = null;
    private static Connection conexao1 = null;
    private static final String URL1 = "jdbc:mysql://localhost:3306/db_cavalaria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";
    private static final String USER1 = "root";
    private static final String PASSWORD = "@ustralopitecos89";

    public static List<Cronometro> pesquisa(Date inicio, Date fim) throws SQLException {
        List<Cronometro> crono = new ArrayList<>();
        String sql = "SELECT * FROM listao where data_registro>=? and data_registro<=? and cronometro is not null;";
        conexao1 = DriverManager.getConnection(URL1, USER1, PASSWORD);
        prep1 = conexao1.prepareStatement(sql);
        prep1.setDate(1, inicio);
        prep1.setDate(2, fim);
        rst1 = prep1.executeQuery();
        while (rst1.next()) {
            Cronometro c = new Cronometro();
            c.setIdCavalo(rst1.getInt("idCavalo"));
            c.setColocacao(rst1.getInt("colocacao"));
            c.setCorpo_chegada(rst1.getString("corpo_chegada"));
            c.setCronometro(rst1.getString("cronometro"));
            c.setData(rst1.getDate("data_pareo"));
            c.setPareo(rst1.getString("pareo"));
            crono.add(c);
        }
        return crono;
    }

    public static void montaCronometro(List<Cronometro> listaCronometro) throws SQLException {
        String sql = "SELECT * FROM pareo WHERE pareo=?;";
        prep1 = conexao1.prepareStatement(sql);
        for (Cronometro c : listaCronometro) {
            String pareo = c.getPareo();
            prep1.setString(1, pareo);
            rst1 = prep1.executeQuery();
            while (rst1.next()) {
                c.setTipo_pareo(rst1.getString("tipo_pareo"));
                String pista = rst1.getString("tipo_pista");
                int distancia = rst1.getInt("distancia");
                String raia = String.valueOf(distancia) + pista;
                c.setRaia(raia);
                c.setTipo_pareo(rst1.getString("tipo_pareo"));

                float tempo = ConverteCronometroParaFloat.converteCronometro(c.getCronometro());
                //System.out.println("crono:" +c.getCronometro()+"\ttempo: " + tempo);
                if (tempo > 0) {
                    c.setTempo(tempo);
                    //System.out.println(c);
                }
            }
        }
        prep1.close();
        conexao1.close();
    }

    public static void atualizaTabelaCronometro(List<Cronometro> listaCronometro) throws SQLException {
        int cont = 0;
        conexao2 = DriverManager.getConnection(URL2, USER1, PASSWORD);
        String sql = "INSERT INTO cronometro(idCavalo,cronometro,filtro,data,pareo,tempo,tipo_pareo,corpo_chegada,colocacao) values(?,?,?,?,?,?,?,?,?);";
        prep2 = conexao2.prepareStatement(sql);
        for (Cronometro c : listaCronometro) {
            if (!atualizado(c.getPareo(),c.getIdCavalo(), c.getData())) {
                prep2.setInt(1, c.getIdCavalo());
                prep2.setString(2, c.getCronometro());
                prep2.setString(3, c.getRaia());
                prep2.setDate(4, c.getData());
                prep2.setString(5, c.getPareo());
                prep2.setFloat(6, c.getTempo());
                prep2.setString(7, c.getTipo_pareo());
                prep2.setString(8, c.getCorpo_chegada());
                prep2.setInt(9, c.getColocacao());
                int ok = prep2.executeUpdate();
                if (ok < 1) {
                    System.out.println("Erro " + c.getPareo() + "\tidCavalo:" + c.getIdCavalo());
                } else {
                    cont++;
                }
            }
            System.out.println("Já inserido: "+ c);
        }
        System.out.println("Adicionadas " + cont + "  linhas.");
        prep2.close();
        conexao2.close();
        System.exit(0);
    }

    public static void atualizaTupla(List<Integer> lista) throws SQLException {
        PreparedStatement prep2 = null;
        Connection conexao2 = null;
        final String URL2 = "jdbc:mysql://localhost:3306/db_cronometro?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";
        conexao2 = DriverManager.getConnection(URL2, USER1, PASSWORD);
        String sql = "INSERT INTO cronometro(cronometro) value(?);";
        prep2 = conexao1.prepareStatement(sql);
        for (Integer id : lista) {

        }
    }

    private static boolean atualizado(String pareo, int idCavalo, Date data) throws SQLException {
        boolean existe = false;
        String sql = "SELECT * FROM cronometro WHERE pareo=? AND idCavalo=? AND data=?;";
        PreparedStatement prep =conexao2.prepareStatement(sql);
        prep.setString(1, pareo);
        prep.setInt(2, idCavalo);
        prep.setDate(3, data);
        ResultSet rst = prep.executeQuery();
        if(rst.next()){
            existe = true;
        }
        return existe;
    }
}
