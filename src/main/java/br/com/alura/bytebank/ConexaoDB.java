package br.com.alura.bytebank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    public static void main(String... x) {

        try {
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/byteBank?user=root&password=root");

            System.out.println("Conexão iniciada!");
            connection.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
