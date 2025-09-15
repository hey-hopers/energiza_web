package com.energiza.EnergizaWeb.modelos.pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.energiza.EnergizaWeb.utils.Conexao;

public class PessoaFJDAO {
	
	// 🔹 Construtor Padrão
    public PessoaFJDAO() {
    }
    
	public List<PessoaFJ> getPessoaFJByUsuario(int idUsuario) {
    	
        List<PessoaFJ> lista = new ArrayList<>();
        
        try {
        		
			String sql = "SELECT "
					+ "PESSOAS_FJ.ID_PESSOAS_FJ, "
					+ "PESSOAS_FJ.APELIDO, "
					+ "PESSOAS_FJ.TIPO, "
					+ "IDENTIFICACAO.NOME, "
					+ "IDENTIFICACAO.EMAIL, "
					+ "IDENTIFICACAO.TELEFONE, "
					+ "DOCUMENTOS.NOME_DOCUMENTO, "
					+ "DOCUMENTOS.NUMERO_DOCUMENTO, "
					+ "ENDERECOS.CEP, "
					+ "ENDERECOS.ENDERECO, "
					+ "ENDERECOS.NUMERO, "
					+ "ENDERECOS.COMPLEMENTO, "
					+ "ENDERECOS.BAIRRO, "
					+ "ENDERECOS.CIDADE, "
					+ "ENDERECOS.ESTADO, "
					+ "ENDERECOS.PAIS "
					+ "FROM PESSOAS_FJ "
					+ "JOIN DOCUMENTOS ON PESSOAS_FJ.ID_DOCUMENTO = DOCUMENTOS.ID_DOCUMENTO "
					+ "JOIN ENDERECOS ON PESSOAS_FJ.ID_ENDERECO = ENDERECOS.ID_ENDERECO "
					+ "JOIN IDENTIFICACAO ON PESSOAS_FJ.ID_IDENTIFICACAO = IDENTIFICACAO.ID_IDENTIFICACAO "
					+ "WHERE PESSOAS_FJ.ID_USUARIO = ?"; 

			Connection con = Conexao.conectar();
			
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, idUsuario);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				PessoaFJ p = new PessoaFJ();
				p.setId(rs.getInt("ID_PESSOAS_FJ"));
				p.setApelido(rs.getString("APELIDO"));
				p.setTipo(rs.getString("TIPO"));
				p.setNome(rs.getString("NOME"));
				p.setEmail(rs.getString("EMAIL"));
				p.setTelefone(rs.getString("TELEFONE"));
				p.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
				p.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
				p.setCep(rs.getString("CEP"));
				p.setEndereco(rs.getString("ENDERECO"));
				p.setNumero(rs.getString("NUMERO"));
				p.setComplemento(rs.getString("COMPLEMENTO"));
				p.setBairro(rs.getString("BAIRRO"));
				p.setCidade(rs.getString("CIDADE"));
				p.setEstado(rs.getString("ESTADO"));
				p.setPais(rs.getString("PAIS"));
				lista.add(p);
			}

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

	public boolean excluirPessoa(int id) {
		String sql = "DELETE FROM PESSOAS_FJ WHERE ID_PESSOAS_FJ = ?";
		try (Connection con = Conexao.conectar();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean salvarPessoa(PessoaFJ pessoa, int idUsuario) {
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
			stmt.setString(1, pessoa.getCep());
			stmt.setString(2, pessoa.getEndereco());
			stmt.setString(3, pessoa.getNumero());
			stmt.setString(4, pessoa.getComplemento());
			stmt.setString(5, pessoa.getBairro());
			stmt.setString(6, pessoa.getCidade());
			stmt.setString(7, pessoa.getEstado());
			stmt.setString(8, pessoa.getPais());
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
			stmt.setString(1, pessoa.getNumeroDocumento());
			stmt.setString(2, pessoa.getNomeDocumento());
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
			stmt.setString(1, pessoa.getNome());
			stmt.setString(2, pessoa.getApelido());
			stmt.setString(3, pessoa.getEmail());
			stmt.setString(4, pessoa.getTelefone());
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
			String sqlPessoaFJ = "INSERT INTO PESSOAS_FJ (APELIDO, TIPO, ID_ENDERECO, ID_DOCUMENTO, ID_IDENTIFICACAO, ID_USUARIO) VALUES (?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sqlPessoaFJ, java.sql.Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, pessoa.getApelido());
			stmt.setString(2, pessoa.getTipo());
			stmt.setInt(3, idEndereco);
			stmt.setInt(4, idDocumento);
			stmt.setInt(5, idIdentificacao);
			stmt.setInt(6, idUsuario);
			stmt.executeUpdate();
			generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				pessoa.setId(generatedKeys.getInt(1)); // Define o ID da PessoaFJ
			} else {
				throw new SQLException("Falha ao obter ID_PESSOAS_FJ gerado.");
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

	public boolean atualizarPessoa(PessoaFJ pessoa, int idUsuario) {
        
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
    
            String getIdsSql = "SELECT ID_ENDERECO, ID_DOCUMENTO, ID_IDENTIFICACAO FROM PESSOAS_FJ WHERE ID_PESSOAS_FJ = ?";
            stmt = con.prepareStatement(getIdsSql);
            stmt.setInt(1, pessoa.getId());
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idEnderecoExistente = rs.getInt("ID_ENDERECO");
                    idDocumentoExistente = rs.getInt("ID_DOCUMENTO");
                    idIdentificacaoExistente = rs.getInt("ID_IDENTIFICACAO");
                } else {
                    throw new SQLException("PessoaFJ não encontrada para atualização.");
                }
            }
            stmt.close();
    
            // 1. Atualizar ENDERECOS
            String updateEnderecoSql = "UPDATE ENDERECOS SET CEP = ?, ENDERECO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, ESTADO = ?, PAIS = ? WHERE ID_ENDERECO = ?";
            stmt = con.prepareStatement(updateEnderecoSql);
            stmt.setString(1, pessoa.getCep());
            stmt.setString(2, pessoa.getEndereco());
            stmt.setString(3, pessoa.getNumero());
            stmt.setString(4, pessoa.getComplemento());
            stmt.setString(5, pessoa.getBairro());
            stmt.setString(6, pessoa.getCidade());
            stmt.setString(7, pessoa.getEstado());
            stmt.setString(8, pessoa.getPais());
            stmt.setInt(9, idEnderecoExistente);
            stmt.executeUpdate();
            stmt.close();
    
            // 2. Atualizar DOCUMENTOS
            String updateDocumentoSql = "UPDATE DOCUMENTOS SET NUMERO_DOCUMENTO = ?, NOME_DOCUMENTO = ? WHERE ID_DOCUMENTO = ?";
            stmt = con.prepareStatement(updateDocumentoSql);
            stmt.setString(1, pessoa.getNumeroDocumento());
            stmt.setString(2, pessoa.getNomeDocumento());
            stmt.setInt(3, idDocumentoExistente);
            stmt.executeUpdate();
            stmt.close();
    
            // 3. Atualizar IDENTIFICACAO
            String updateIdentificacaoSql = "UPDATE IDENTIFICACAO SET NOME = ?, APELIDO = ?, EMAIL = ?, TELEFONE = ? WHERE ID_IDENTIFICACAO = ?";
            stmt = con.prepareStatement(updateIdentificacaoSql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getApelido());
            stmt.setString(3, pessoa.getEmail());
            stmt.setString(4, pessoa.getTelefone());
            stmt.setInt(5, idIdentificacaoExistente);
            stmt.executeUpdate();
            stmt.close();
    
            // 4. Atualizar PESSOAS_FJ (apenas os campos diretos)
            String updatePessoaFjSql = "UPDATE PESSOAS_FJ SET APELIDO = ?, TIPO = ? WHERE ID_PESSOAS_FJ = ? AND ID_USUARIO = ?";
            stmt = con.prepareStatement(updatePessoaFjSql);
            stmt.setString(1, pessoa.getApelido());
            stmt.setString(2, pessoa.getTipo());
            stmt.setInt(3, pessoa.getId());
            stmt.setInt(4, idUsuario);
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected == 0) {
                throw new SQLException("Falha ao atualizar PessoaFJ. Nenhuma linha afetada ou usuário não autorizado.");
            }
    
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
	
	public PessoaFJ getProprietario(int idProprietario) {
		
		PessoaFJ pj = new PessoaFJ();
		
        try {
        	
	        Connection con = Conexao.conectar();
	
	        PreparedStatement stm = con.prepareStatement("SELECT * FROM PESSOAS_FJ WHERE ID_PESSOAS_FJ = ?");	
        	
        	stm.setInt(1, idProprietario);           
        	ResultSet rs = stm.executeQuery(); 
        	
            while (rs.next()) {
            	pj.setId(rs.getInt("ID_PESSOAS_FJ"));
            	pj.setApelido(rs.getString("APELIDO"));
            }
            
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pj;
	}
    
}

