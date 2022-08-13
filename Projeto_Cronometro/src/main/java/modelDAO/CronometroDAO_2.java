/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelDAO;

import com.mycompany.projeto_cronometro.montador.ConverteCronometroParaFloat;
import java.sql.Connection;
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
public class CronometroDAO_2 {

    private static PreparedStatement prep1 = null;
    private static ResultSet rst1 = null;
    private static PreparedStatement prep2 = null;
    private static ResultSet rst2 = null;
    private static Connection conexao = null;
    private static final String URL1 = "jdbc:mysql://localhost:3306/db_cavalaria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Sao_Paulo";
    private static final String USER1 = "root";
    private static final String PASSWORD = "@ustralopitecos89";
    private static Cronometro cronometro;

    public static String inicia(List<Integer> ids) throws SQLException {
        conexao = DriverManager.getConnection(URL1, USER1, PASSWORD);
        String sql = "SELECT * FROM listao where idListao = ?;";
        prep1 = conexao.prepareStatement(sql);
        String sql2 = "SELECT * FROM pareo WHERE pareo=?;";
        prep2 = conexao.prepareStatement(sql2);
        List<Cronometro> lista = new ArrayList<>();
        for (Integer id : ids) {
            cronometro = new Cronometro();
            dadosTabelaListao(id);
            dadosTabelaPareo(cronometro.getPareo());
            lista.add(cronometro);
            System.out.println(cronometro);
        }
        prep1.close();
        rst1.close();
        prep2.close();
        rst2.close();
        conexao.close();
        Cronometro_DAO.atualizaTabelaCronometro(lista);
        System.exit(0);//////////////////////////////////////////////////////
        return null;
    }

    private static void dadosTabelaListao(int idListao) throws SQLException {
        prep1.setInt(1, idListao);
        rst1 = prep1.executeQuery();
        if (rst1.next()) {
            cronometro.setIdCavalo(rst1.getInt("idCavalo"));
            cronometro.setColocacao(rst1.getInt("colocacao"));
            cronometro.setCorpo_chegada(rst1.getString("corpo_chegada"));
            cronometro.setCronometro(rst1.getString("cronometro"));
            cronometro.setData(rst1.getDate("data_pareo"));
            cronometro.setPareo(rst1.getString("pareo"));
        }
    }

    private static void dadosTabelaPareo(String pareo) throws SQLException {
        prep2.setString(1, pareo);
        rst2 = prep2.executeQuery();
        if (rst2.next()) {
            cronometro.setTipo_pareo(rst2.getString("tipo_pareo"));
            String pista = rst2.getString("tipo_pista");
            int distancia = rst2.getInt("distancia");
            String raia = String.valueOf(distancia) + pista;
            cronometro.setRaia(raia);
            cronometro.setTipo_pareo(rst2.getString("tipo_pareo"));

            float tempo = ConverteCronometroParaFloat.converteCronometro(cronometro.getCronometro());
            if (tempo > 0) {
                cronometro.setTempo(tempo);
            } else {
                System.out.println("ERRO:=> pareo:" + pareo);
                System.exit(0);
            }
        }
    }
}
