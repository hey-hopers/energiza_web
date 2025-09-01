package com.energiza.EnergizaWeb.servlets.minhasPessoas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.UsuarioDAO;
import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJ;
import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJDAO;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.UnidadeConsumo;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;

import java.io.BufferedReader;

@WebServlet("/servlet/minhasPessoas/*")
public class minhasPessoas extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    private PessoaFJDAO pessoaFJDAO;
	
	public minhasPessoas() {
        super();
        usuarioDAO = new UsuarioDAO();
        pessoaFJDAO = new PessoaFJDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Usuario usuario = SessionManager.getInstance().getCurrentUser(request);    
        
        if (usuario == null) {
            out.print("{\"success\": false, \"message\": \"Usuário não logado!\"}");
            return;
        }

        List<PessoaFJ> pessoas = pessoaFJDAO.getPessoaFJByUsuario(usuario.getId());
        StringBuilder json = new StringBuilder();
        json.append("{\"success\": true, \"pessoas\": [");
        
        for (int i = 0; i < pessoas.size(); i++) {
        	PessoaFJ p = pessoas.get(i);
        	json.append(String.format(
        	        "{" +
        	        "\"id_pessoa_fj\": %d," +
        	        "\"apelido\": \"%s\"," +
        	        "\"tipo\": \"%s\"," +
        	        "\"nome\": \"%s\"," +
        	        "\"email\": \"%s\"," +
        	        "\"telefone\": \"%s\"," +
        	        "\"nome_documento\": \"%s\"," +
        	        "\"numero_documento\": \"%s\"," +
        	        "\"cep\": \"%s\"," +
        	        "\"endereco\": \"%s\"," +
        	        "\"numero\": \"%s\"," +
        	        "\"complemento\": \"%s\"," +
        	        "\"bairro\": \"%s\"," +
        	        "\"cidade\": \"%s\"," +
        	        "\"estado\": \"%s\"," +
        	        "\"pais\": \"%s\"" +
        	        "}",
        	        p.getId(),
        	        escapeJson(p.getApelido()),
        	        escapeJson(p.getTipo()),
        	        escapeJson(p.getNome()),
        	        escapeJson(p.getEmail()),
        	        escapeJson(p.getTelefone()),
        	        escapeJson(p.getNomeDocumento()),
        	        escapeJson(p.getNumeroDocumento()),
        	        escapeJson(p.getCep()),
        	        escapeJson(p.getEndereco()),
        	        escapeJson(p.getNumero()),
        	        escapeJson(p.getComplemento()),
        	        escapeJson(p.getBairro()),
        	        escapeJson(p.getCidade()),
        	        escapeJson(p.getEstado()),
        	        escapeJson(p.getPais())
        	    ));
            if (i < pessoas.size() - 1) json.append(",");
        }

        json.append("]}");
        out.print(json.toString());
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Usuario usuario = SessionManager.getInstance().getCurrentUser(request);
        if (usuario == null) {
            enviarRespostaErro(response, "Usuário não logado.");
            return;
        }

        try {
            String jsonRequest = readJsonBody(request);
            PessoaFJ pessoa = parsePessoaFJFromJson(jsonRequest);
            
            if (pessoaFJDAO.salvarPessoa(pessoa, usuario.getId())) {
                out.print("{\"success\": true, \"message\": \"Pessoa salva com sucesso!\"}");
            } else {
                enviarRespostaErro(response, "Erro ao salvar pessoa.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            enviarRespostaErro(response, "Erro interno do servidor ao salvar pessoa: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Usuario usuario = SessionManager.getInstance().getCurrentUser(request);
        if (usuario == null) {
            enviarRespostaErro(response, "Usuário não logado.");
            return;
        }

        try {
            String jsonRequest = readJsonBody(request);
            PessoaFJ pessoa = parsePessoaFJFromJson(jsonRequest);

            if (pessoa.getId() == 0) { // O ID deve estar presente para atualização
                enviarRespostaErro(response, "ID da pessoa é obrigatório para atualização.");
                return;
            }
            
            if (pessoaFJDAO.atualizarPessoa(pessoa, usuario.getId())) {
                out.print("{\"success\": true, \"message\": \"Pessoa atualizada com sucesso!\"}");
            } else {
                enviarRespostaErro(response, "Erro ao atualizar pessoa. Verifique o ID e o usuário.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            enviarRespostaErro(response, "Erro interno do servidor ao atualizar pessoa: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            enviarRespostaErro(response, "ID da pessoa é obrigatório para exclusão.");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            boolean sucesso = pessoaFJDAO.excluirPessoa(id);

            if (sucesso) {
                out.print("{\"success\": true, \"message\": \"Pessoa excluída com sucesso!\"}");
            } else {
                enviarRespostaErro(response, "Erro ao excluir pessoa. Verifique se o ID existe.");
            }
        } catch (NumberFormatException e) {
            enviarRespostaErro(response, "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            enviarRespostaErro(response, "Erro interno do servidor ao excluir pessoa.");
        }
    }

	private void enviarRespostaErro(HttpServletResponse response, String mensagem)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String json = String.format("{\"success\": false, \"message\": \"%s\"}", escapeJson(mensagem));
        out.print(json);
    }

    // Funções para utilização referente a JSON
    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }

    private String readJsonBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private PessoaFJ parsePessoaFJFromJson(String jsonString) {
        PessoaFJ pessoa = new PessoaFJ();
        // Exemplo simplificado de parsing manual de JSON para PessoaFJ
        // Em um cenário real, você precisaria de uma lógica mais robusta para lidar com todos os campos e erros
        try {
            // Remover as chaves de abertura e fechamento do JSON e dividir por vírgula
            String cleanJson = jsonString.substring(jsonString.indexOf("{") + 1, jsonString.lastIndexOf("}"));
            String[] pairs = cleanJson.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Divide por vírgula, ignorando vírgulas dentro de strings

            for (String pair : pairs) {
                String[] keyValue = pair.split(":", 2); // Divide em chave e valor, considerando ':' dentro do valor
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replace("\"", "");
                    String value = keyValue[1].trim().replace("\"", "");

                    switch (key) {
                        case "id_pessoa_fj":
                            if (!value.isEmpty() && !value.equals("null")) {
                                pessoa.setId(Integer.parseInt(value));
                            }
                            break;
                        case "nome":
                            pessoa.setNome(value);
                            break;
                        case "apelido":
                            pessoa.setApelido(value);
                            break;
                        case "tipoPessoa":
                            pessoa.setTipo(value);
                            break;
                        case "nome_documento":
                            pessoa.setNomeDocumento(value);
                            break;
                        case "numero_documento":
                            pessoa.setNumeroDocumento(value);
                            break;
                        case "email":
                            pessoa.setEmail(value);
                            break;
                        case "telefone":
                            pessoa.setTelefone(value);
                            break;
                        case "cep":
                            pessoa.setCep(value);
                            break;
                        case "endereco":
                            pessoa.setEndereco(value);
                            break;
                        case "numero":
                            pessoa.setNumero(value);
                            break;
                        case "complemento":
                            pessoa.setComplemento(value);
                            break;
                        case "bairro":
                            pessoa.setBairro(value);
                            break;
                        case "cidade":
                            pessoa.setCidade(value);
                            break;
                        case "estado":
                            pessoa.setEstado(value);
                            break;
                        case "pais":
                            pessoa.setPais(value);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao parsear JSON para PessoaFJ: " + e.getMessage(), e);
        }
        return pessoa;
    }

}
