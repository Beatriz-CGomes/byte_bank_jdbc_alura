package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;
import br.com.alura.bytebank.domain.conta.Conta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDao {

    private Connection conn;

    ContaDao(Connection connection) {
        this.conn = connection;
    }

    public void salvar(DadosAberturaConta dadosDaConta) {
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), cliente);
        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email)"
                + "VALUES (?, ?, ?, ?, ? )";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, dadosDaConta.dadosCliente().nome());
            preparedStatement.setString(4, dadosDaConta.dadosCliente().cpf());
            preparedStatement.setString(5, dadosDaConta.dadosCliente().email());

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Set<Conta> listar() {
        Set<Conta> contas = new HashSet<>();
        String sql = "SELECT * FROM conta";

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = conn.prepareStatement(sql);
            //para obter algum retorno
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Integer numeroConta = resultSet.getInt(1);
                BigDecimal saldp = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(nome, cpf, email);

                Cliente cliente = new Cliente(dadosCadastroCliente);
                contas.add(new Conta(numeroConta, cliente));
            }

            resultSet.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contas;
    }

}
