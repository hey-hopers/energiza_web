package com.energiza.EnergizaWeb.modelos.meuNegocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.energiza.EnergizaWeb.utils.Conexao;

public class meuNegocioDAO {
    
    // 🔹 Construtor Padrão
    public meuNegocioDAO() {
    }

    public meuNegocio getNegocioByUsuario(int idUsuario) {   	
        
        meuNegocio negocio = null;

        try {
        		
			String sql = "SELECT "
					+ "OE.ID_OPERADOR_ENERGETICO, "
					+ "FJ.ID_PESSOAS_FJ, "
					+ "FJ.APELIDO, "
					+ "ID.NOME, "
					+ "ID.EMAIL, "
					+ "ID.TELEFONE, "
					+ "DO.NOME_DOCUMENTO, "
					+ "DO.NUMERO_DOCUMENTO, "
					+ "ED.CEP, "
					+ "ED.ENDERECO, "
					+ "ED.NUMERO, "
					+ "ED.COMPLEMENTO, "
					+ "ED.BAIRRO, "
					+ "ED.CIDADE, "
					+ "ED.ESTADO, "
					+ "ED.PAIS "
					+ "FROM OPERADOR_ENERGETICO OE "
					+ "JOIN IDENTIFICACAO ID ON ID.ID_IDENTIFICACAO = OE.ID_IDENTIFICACAO "
					+ "JOIN DOCUMENTOS DO ON DO.ID_DOCUMENTO = OE.ID_DOCUMENTO "
					+ "JOIN ENDERECOS ED ON ED.ID_ENDERECO = OE.ID_ENDERECO "
					+ "JOIN PESSOAS_FJ FJ ON FJ.ID_PESSOAS_FJ = OE.ID_PESSOAS_FJ "
					+ "WHERE OE.ID_USUARIO = ?"; 

			Connection con = Conexao.conectar();
			
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, idUsuario);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				negocio = new meuNegocio();
				negocio.setId(rs.getInt("ID_OPERADOR_ENERGETICO"));
				negocio.setIdOperador(rs.getInt("ID_PESSOAS_FJ"));
				negocio.setApelido(rs.getString("APELIDO"));
				negocio.setNome(rs.getString("NOME"));
				negocio.setEmail(rs.getString("EMAIL"));
				negocio.setTelefone(rs.getString("TELEFONE"));
				negocio.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
				negocio.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
				negocio.setCep(rs.getString("CEP"));
				negocio.setEndereco(rs.getString("ENDERECO"));
				negocio.setNumero(rs.getString("NUMERO"));
				negocio.setComplemento(rs.getString("COMPLEMENTO"));
				negocio.setBairro(rs.getString("BAIRRO"));
				negocio.setCidade(rs.getString("CIDADE"));
				negocio.setEstado(rs.getString("ESTADO"));
				negocio.setPais(rs.getString("PAIS"));
			}

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return negocio;
    }

    public boolean criarNegocio(meuNegocio negocio, int idUsuario) {
		Connection con = null;
		PreparedStatement stmt = null;
		java.sql.ResultSet generatedKeys = null;
		boolean sucesso = false;

		try {
			con = Conexao.conectar();
			con.setAutoCommit(false); // Iniciar transação

			int idEndereco = 0;
			int idDocumento = 0;    
			int idIdentificacao = 0;
			
			// 1. Inserir na tabela ENDERECOS
			String sqlEndereco = "INSERT INTO ENDERECOS (CEP, ENDERECO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, PAIS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sqlEndereco, java.sql.Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, negocio.getCep());
			stmt.setString(2, negocio.getEndereco());
			stmt.setString(3, negocio.getNumero());
			stmt.setString(4, negocio.getComplemento());
			stmt.setString(5, negocio.getBairro());
			stmt.setString(6, negocio.getCidade());
			stmt.setString(7, negocio.getEstado());
			stmt.setString(8, negocio.getPais());
			stmt.executeUpdate();
			generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				idEndereco = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Falha ao obter ID_ENDERECO gerado.");
			}
			stmt.close();
			generatedKeys.close();

			// 2. Inserir na tabela DOCUMENTOS
			String sqlDocumento = "INSERT INTO DOCUMENTOS (NUMERO_DOCUMENTO, NOME_DOCUMENTO) VALUES (?, ?)";
			stmt = con.prepareStatement(sqlDocumento, java.sql.Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, negocio.getNumeroDocumento());
			stmt.setString(2, negocio.getNomeDocumento());
			stmt.executeUpdate();
			generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				idDocumento = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Falha ao obter ID_DOCUMENTO gerado.");
			}
			stmt.close();
			generatedKeys.close();

			// 3. Inserir na tabela IDENTIFICACAO
			String sqlIdentificacao = "INSERT INTO IDENTIFICACAO (NOME, APELIDO, EMAIL, TELEFONE) VALUES (?, ?, ?, ?)";
			stmt = con.prepareStatement(sqlIdentificacao, java.sql.Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, negocio.getNome());
			stmt.setString(2, negocio.getApelido());
			stmt.setString(3, negocio.getEmail());
			stmt.setString(4, negocio.getTelefone());
			stmt.executeUpdate();
			generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				idIdentificacao = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Falha ao obter ID_IDENTIFICACAO gerado.");
			}
			stmt.close();
			generatedKeys.close();

			// 4. Inserir na tabela PESSOAS_FJ
			String sqlPessoaFJ = "INSERT INTO OPERADOR_ENERGETICO (ID_IDENTIFICACAO, ID_DOCUMENTO, ID_PESSOAS_FJ, ID_ENDERECO, ID_USUARIO) VALUES (?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sqlPessoaFJ, java.sql.Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, idIdentificacao);
			stmt.setInt(2, idDocumento);
			stmt.setInt(3, negocio.getIdOperador());
			stmt.setInt(4, idEndereco);
			stmt.setInt(5, idUsuario);
			stmt.executeUpdate();
			generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				negocio.setId(generatedKeys.getInt(1)); // Define o ID da PessoaFJ
			} else {
				throw new SQLException("Falha ao obter ID_OPERADOR_ENERGETICO gerado.");
			}

			con.commit(); // Confirmar transação se tudo deu certo
			sucesso = true;

		} catch (SQLException e) {
			e.printStackTrace();
			if (con != null) {
				try {
					con.rollback(); // Reverter transação em caso de erro
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			
		} finally {
			try {
				if (generatedKeys != null) generatedKeys.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return sucesso;
	}

	public boolean atualizarNegocio(meuNegocio negocio, int idUsuario) {
        
		Connection con = null;
        PreparedStatement stmt = null;
        boolean sucesso = false;
    
        try {
            con = Conexao.conectar();
            con.setAutoCommit(false); // Iniciar transação
    
            // Obter IDs de relacionamento existentes da PessoaFJ
            // (Isso é necessário para atualizar as tabelas relacionadas corretamente)
            int idEnderecoExistente = 0;
            int idDocumentoExistente = 0;
            int idIdentificacaoExistente = 0;
    
            String getIdsSql = "SELECT ID_ENDERECO, ID_DOCUMENTO, ID_IDENTIFICACAO FROM OPERADOR_ENERGETICO WHERE ID_OPERADOR_ENERGETICO = ?";
            stmt = con.prepareStatement(getIdsSql);
            stmt.setInt(1, negocio.getId());
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idEnderecoExistente = rs.getInt("ID_ENDERECO");
                    idDocumentoExistente = rs.getInt("ID_DOCUMENTO");
                    idIdentificacaoExistente = rs.getInt("ID_IDENTIFICACAO");
                } else {
                    throw new SQLException("Negocio não encontrado para atualização.");
                }
            }
            stmt.close();
    
            // 1. Atualizar ENDERECOS
            String updateEnderecoSql = "UPDATE ENDERECOS SET CEP = ?, ENDERECO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, ESTADO = ?, PAIS = ? WHERE ID_ENDERECO = ?";
            stmt = con.prepareStatement(updateEnderecoSql);
            stmt.setString(1, negocio.getCep());
            stmt.setString(2, negocio.getEndereco());
            stmt.setString(3, negocio.getNumero());
            stmt.setString(4, negocio.getComplemento());
            stmt.setString(5, negocio.getBairro());
            stmt.setString(6, negocio.getCidade());
            stmt.setString(7, negocio.getEstado());
            stmt.setString(8, negocio.getPais());
            stmt.setInt(9, idEnderecoExistente);
            stmt.executeUpdate();
            stmt.close();
    
            // 2. Atualizar DOCUMENTOS
            String updateDocumentoSql = "UPDATE DOCUMENTOS SET NUMERO_DOCUMENTO = ?, NOME_DOCUMENTO = ? WHERE ID_DOCUMENTO = ?";
            stmt = con.prepareStatement(updateDocumentoSql);
            stmt.setString(1, negocio.getNumeroDocumento());
            stmt.setString(2, negocio.getNomeDocumento());
            stmt.setInt(3, idDocumentoExistente);
            stmt.executeUpdate();
            stmt.close();
    
            // 3. Atualizar IDENTIFICACAO
            String updateIdentificacaoSql = "UPDATE IDENTIFICACAO SET NOME = ?, APELIDO = ?, EMAIL = ?, TELEFONE = ? WHERE ID_IDENTIFICACAO = ?";
            stmt = con.prepareStatement(updateIdentificacaoSql);
            stmt.setString(1, negocio.getNome());
            stmt.setString(2, negocio.getApelido());
            stmt.setString(3, negocio.getEmail());
            stmt.setString(4, negocio.getTelefone());
            stmt.setInt(5, idIdentificacaoExistente);
            stmt.executeUpdate();
            stmt.close();
    
            con.commit(); // Confirmar transação
            sucesso = true;
    
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback(); // Reverter transação em caso de erro
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return sucesso;
    }

}
