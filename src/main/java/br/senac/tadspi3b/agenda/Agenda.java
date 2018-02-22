package br.senac.tadspi3b.agenda;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vitoria.csilva15 20/02 - PI3
 */
public class Agenda {

    private Connection obterConexao() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agendabd", "root", "");
        return conn; 
    }

    public void listar() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultados = null;

        // método para entrar no fluxo - 1) abrir conexão com banco de dados
        // Qual drive irei usar de acordo com o banco de dados - ex:sql
        Class.forName("com.mysql.jdbc.Driver");
        try {

            //2) Abrir conexão usando a string de conexão
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agendabd", "root", "");

            // executar os comandos no bd
            // preparando o comando que quero faZER
            stmt = conn.prepareStatement("SELECT id, nome, dtnascimento FROM PESSOA");
            //ARMAZENO OS RESULTADOS NESSA VARIAVEL RESULTADO
            resultados = stmt.executeQuery();

            while (resultados.next()) {
                long id = resultados.getLong("id");
                String nome = resultados.getString("nome");
                Date dtNascimento = resultados.getDate("dtnascimento");

                System.out.println(id + "," + nome + "," + dtNascimento);
            }

        } finally {
            // 3)FECHAR CONEXÃO
            //tem que ser o inverso que abriu, assim: result, o statntemente, e o connection
            if (resultados != null) {
                resultados.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public void incluir() {

    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();

        try {
            agenda.listar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
