package com.energiza.EnergizaWeb.servlets.meuNegocio;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.meuNegocio.*;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;

import java.io.BufferedReader;

@WebServlet("/servlet/meuNegocio/*")
public class meuNegocioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private meuNegocioDAO negocioDAO;
	
	public meuNegocioServlet() {
        super();
		negocioDAO = new meuNegocioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Usuario usuarioLogado = (Usuario) request.getSession(false).getAttribute("CURRENT_USER");
        
        meuNegocio negocio = negocioDAO.getNegocioByUsuario(usuarioLogado.getId());

        if (negocio == null) {
            out.print("{\"success\": false, \"message\": \"Negócio não encontrado\"}");
            return;
        }

        String json = String.format(
            "{" +
            "\"success\": true," +
            "\"negocio\": {" +
            "\"id_negocio\": %d," +
            "\"id_operador\": %d," +
            "\"nome\": \"%s\"," +
            "\"apelido\": \"%s\"," +
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
            "}" +
            "}",
            negocio.getId(),
            negocio.getIdOperador(),
            escapeJson(negocio.getNome()),
            escapeJson(negocio.getApelido()),
            escapeJson(negocio.getEmail()),
            escapeJson(negocio.getTelefone()),
            escapeJson(negocio.getNomeDocumento()),
            escapeJson(negocio.getNumeroDocumento()),
            escapeJson(negocio.getCep()),
            escapeJson(negocio.getEndereco()),
            escapeJson(negocio.getNumero()),
            escapeJson(negocio.getComplemento()),
            escapeJson(negocio.getBairro()),
            escapeJson(negocio.getCidade()),
            escapeJson(negocio.getEstado()),
            escapeJson(negocio.getPais())
        );
        out.print(json);
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
            meuNegocio negocio = parsemeuNegocioFromJson(jsonRequest);
            
            if (negocioDAO.criarNegocio(negocio, usuario.getId())) {
                out.print("{\"success\": true, \"message\": \"Negócio salvo com sucesso!\"}");
            } else {
                enviarRespostaErro(response, "Erro ao salvar negócio.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            enviarRespostaErro(response, "Erro interno do servidor ao salvar negócio: " + e.getMessage());
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
            meuNegocio negocio = parsemeuNegocioFromJson(jsonRequest);

            if (negocio.getId() == 0) { // O ID deve estar presente para atualização
                enviarRespostaErro(response, "ID do negócio é obrigatório para atualização.");
                return;
            }
            
            if (negocioDAO.atualizarNegocio(negocio, usuario.getId())) {
                out.print("{\"success\": true, \"message\": \"Negócio atualizado com sucesso!\"}");
            } else {
                enviarRespostaErro(response, "Erro ao atualizar negócio. Verifique o ID e o usuário.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            enviarRespostaErro(response, "Erro interno do servidor ao atualizar negócio: " + e.getMessage());
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

    private meuNegocio parsemeuNegocioFromJson(String jsonString) {
        meuNegocio negocio = new meuNegocio();
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
                        case "id_negocio":
                            if (!value.isEmpty() && !value.equals("null")) {
                                negocio.setId(Integer.parseInt(value));
                            }
                            break;
                        case "id_operador":
                        	if (!value.isEmpty() && !value.equals("null")) {
                                negocio.setIdOperador(Integer.parseInt(value));
                            }
                            break;
                        case "nome":
                            negocio.setNome(value);
                                break;
                        case "apelido":
                            negocio.setApelido(value);
                            break;
                        case "nome_documento":
                            negocio.setNomeDocumento(value);
                            break;
                        case "numero_documento":
                            negocio.setNumeroDocumento(value);
                            break;
                        case "email":
                            negocio.setEmail(value);
                            break;
                        case "telefone":
                            negocio.setTelefone(value);
                            break;
                        case "cep":
                            negocio.setCep(value);
                            break;
                        case "endereco":
                            negocio.setEndereco(value);
                            break;
                        case "numero":
                            negocio.setNumero(value);
                            break;
                        case "complemento":
                            negocio.setComplemento(value);
                            break;
                        case "bairro":
                            negocio.setBairro(value);
                            break;
                        case "cidade":
                            negocio.setCidade(value);
                            break;
                        case "estado":
                            negocio.setEstado(value);
                            break;
                        case "pais":
                            negocio.setPais(value);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao parsear JSON para PessoaFJ: " + e.getMessage(), e);
        }
        return negocio;
    }

}
