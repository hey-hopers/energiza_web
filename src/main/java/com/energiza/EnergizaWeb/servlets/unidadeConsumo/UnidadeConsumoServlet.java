package com.energiza.EnergizaWeb.servlets.unidadeConsumo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Endereco;
import com.energiza.EnergizaWeb.modelos.EnderecoDAO;
import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;
import com.energiza.EnergizaWeb.modelos.meuNegocio.meuNegocio;
import com.energiza.EnergizaWeb.modelos.meuNegocio.meuNegocioDAO;
import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJ;
import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJDAO;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.Distribuidora;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.DistribuidoraDAO;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.UnidadeConsumo;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.UnidadeConsumoDAO;

@WebServlet("/servlet/unidadeConsumo/*")
public class UnidadeConsumoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UnidadeConsumoDAO unidadeConsumoDAO;
    private PessoaFJDAO pessoaFJDAO;
    private EnderecoDAO enderecoDAO;
    private DistribuidoraDAO distribuidoraDAO;
    private meuNegocioDAO negocioDAO;

    public UnidadeConsumoServlet() {
        super();
        unidadeConsumoDAO = new UnidadeConsumoDAO();
        pessoaFJDAO = new PessoaFJDAO();
        enderecoDAO = new EnderecoDAO();
        distribuidoraDAO = new DistribuidoraDAO();
        negocioDAO = new meuNegocioDAO();
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
        
        List<UnidadeConsumo> unidadesConsumo = unidadeConsumoDAO.getUnidadesConsumoByUsuario(usuario.getId());
        StringBuilder json = new StringBuilder();
        json.append("{\"success\": true, \"unidades\": [");
        
        for (int i = 0; i < unidadesConsumo.size(); i++) {
        	
        	UnidadeConsumo uc = unidadesConsumo.get(i);
        	
        	Endereco endereco = enderecoDAO.getEndereco(uc.getIdEndereco());
        	
        	PessoaFJ pj = pessoaFJDAO.getProprietario(uc.getIdProprietario());
        	
        	Distribuidora dist = distribuidoraDAO.getDistribuidora(uc.getIdDistribuidora());
        	
        	meuNegocio operador = negocioDAO.getOperador(uc.getIdOperador());
        	
        	json.append(String.format(
        		    "{" +
        		    "\"id_unidade_consumo\": %d," +
        		    "\"uc_codigo\": \"%s\"," +
        		    "\"eh_geradora\": %b," +
        		    "\"medidor\": \"%s\"," + 
        		    "\"id_operador\": %d," +
        		    "\"nome_operador\": \"%s\"," +
        		    "\"id_distribuidora\": %d," +
        		    "\"nome_distribuidora\": \"%s\"," +
        		    "\"id_proprietario\": %d," +
        		    "\"apelido_proprietario\": \"%s\"," +
        		    "\"id_endereco\": %d," +
        		    "\"cep\": \"%s\"," +
        		    "\"endereco\": \"%s\"," +
        		    "\"numero\": \"%s\"," +
        		    "\"complemento\": \"%s\"," +
        		    "\"bairro\": \"%s\"," +
        		    "\"cidade\": \"%s\"," +
        		    "\"estado\": \"%s\"," +
        		    "\"pais\": \"%s\"" +
        		    "}",                  
        		    uc.getId(),
        		    escapeJson(uc.getUcCodigo()),
        		    uc.getEhGeradora(),
        		    escapeJson(uc.getMedidor() != null ? uc.getMedidor() : ""),
        		    operador.getId(),
        		    escapeJson(operador.getNome()),
        		    dist.getId(),
        		    escapeJson(dist.getNome()),
        		    pj.getId(),
        		    escapeJson(pj.getApelido()),
        		    endereco.getId(),
        		    escapeJson(endereco.getCep()),
        		    escapeJson(endereco.getEndereco()),
        		    escapeJson(endereco.getNumero()),
        		    escapeJson(endereco.getComplemento()),
        		    escapeJson(endereco.getBairro()),
        		    escapeJson(endereco.getCidade()),
        		    escapeJson(endereco.getEstado()),
        		    escapeJson(endereco.getPais())
        		    
        		));
            if (i < unidadesConsumo.size() - 1) json.append(",");
        }

        json.append("]}");
        out.print(json.toString());
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            salvarUsuario(request, response);
        } else {
            enviarRespostaErro(response, "Operação não suportada");
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        salvarUsuario(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Usuario usuario = SessionManager.getInstance().getCurrentUser(request);
        
        if (usuario == null) {
            out.print("{\"success\": false, \"message\": \"Usuário não logado\"}");
            return;
        }

        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                out.print("{\"success\": false, \"message\": \"ID da unidade não informado\"}");
                return;
            }
            
            int idUnidade = Integer.parseInt(idStr);
            
            if (unidadeConsumoDAO.excluirUnidadeConsumo(idUnidade)) {
                out.print("{\"success\": true, \"message\": \"Unidade de consumo excluída com sucesso!\"}");
            } else {
                out.print("{\"success\": false, \"message\": \"Erro ao excluir unidade de consumo\"}");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Erro interno: " + e.getMessage() + "\"}");
        }
    }

    private void salvarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();

        try {
        	
        	Usuario usuario = SessionManager.getInstance().getCurrentUser(request);   
                   	
            if (usuario == null) {
                out.print("{\"success\": false, \"message\": \"Usuário não logado\"}");
                return;   
            }
            
            // Ler JSON do corpo da requisição
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (java.io.BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            
            // Parse JSON (simples, sem biblioteca externa)
            String json = jsonBuffer.toString();
            String uc_codigo = extractJsonValue(json, "uc_codigo");
            String eh_geradora_str = extractJsonValue(json, "eh_geradora");
            String medidor = extractJsonValue(json, "medidor");
            String etapa = extractJsonValue(json, "etapa");
            String cep = extractJsonValue(json, "cep");
            String endereco = extractJsonValue(json, "endereco");
            String numero = extractJsonValue(json, "numero");
            String complemento = extractJsonValue(json, "complemento");
            String bairro = extractJsonValue(json, "bairro");
            String cidade = extractJsonValue(json, "cidade");
            String estado = extractJsonValue(json, "estado");
            String pais = extractJsonValue(json, "pais");
            String id_unidade_consumo_str = extractJsonValue(json, "id_unidade_consumo");
            
            boolean eh_geradora = "1".equals(eh_geradora_str) || "true".equalsIgnoreCase(eh_geradora_str);
            
            UnidadeConsumo uc = new UnidadeConsumo();
            uc.setUcCodigo(uc_codigo);
            uc.setEhGeradora(eh_geradora);
            uc.setMedidor(medidor);
            uc.setEtapa(etapa);
            uc.setCep(cep);
            uc.setEndereco(endereco);
            uc.setNumero(numero);
            uc.setComplemento(complemento);
            uc.setBairro(bairro);
            uc.setCidade(cidade);
            uc.setEstado(estado);
            uc.setPais(pais);
            
            boolean sucesso;
            String mensagem;
            
            if (id_unidade_consumo_str != null && !id_unidade_consumo_str.isEmpty() && !"null".equals(id_unidade_consumo_str)) {
                // Editar unidade existente
                uc.setId(Integer.parseInt(id_unidade_consumo_str));
                sucesso = unidadeConsumoDAO.atualizarUnidadeConsumo(uc);
                mensagem = sucesso ? "Unidade de consumo atualizada com sucesso!" : "Erro ao atualizar unidade de consumo";
            } else {
                // Criar nova unidade
                // Buscar pessoa do usuário logado
                List<PessoaFJ> pessoa = pessoaFJDAO.getPessoaFJByUsuario(usuario.getId());
                if (pessoa == null) {
                    out.print("{\"success\": false, \"message\": \"Pessoa não encontrada para o usuário\"}");
                    return;
                }
                
                // sucesso = unidadeConsumoDAO.inserirUnidadeConsumo(uc, pessoa);
                // mensagem = sucesso ? "Unidade de consumo criada com sucesso!" : "Erro ao criar unidade de consumo";
            }
            
            // out.print(String.format("{\"success\": %s, \"message\": \"%s\"}", sucesso, escapeJson(mensagem)));
            
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Erro interno: " + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return null;
        
        startIndex += searchKey.length();
        
        // Pular espaços em branco
        while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
            startIndex++;
        }
        
        if (startIndex >= json.length()) return null;
        
        char firstChar = json.charAt(startIndex);
        
        if (firstChar == '"') {
            // String value
            int endIndex = json.indexOf('"', startIndex + 1);
            if (endIndex == -1) return null;
            return json.substring(startIndex + 1, endIndex);
        } else if (firstChar == '{' || firstChar == '[' || Character.isDigit(firstChar) || firstChar == '-' || firstChar == 't' || firstChar == 'f' || firstChar == 'n') {
            // Object, array, number, boolean, or null
            int endIndex = startIndex;
            
            if (firstChar == '{') {
                int braceCount = 1;
                endIndex++;
                while (endIndex < json.length() && braceCount > 0) {
                    if (json.charAt(endIndex) == '{') braceCount++;
                    else if (json.charAt(endIndex) == '}') braceCount--;
                    endIndex++;
                }
            } else if (firstChar == '[') {
                int bracketCount = 1;
                endIndex++;
                while (endIndex < json.length() && bracketCount > 0) {
                    if (json.charAt(endIndex) == '[') bracketCount++;
                    else if (json.charAt(endIndex) == ']') bracketCount--;
                    endIndex++;
                }
            } else {
                // Number, boolean, or null - find next comma or closing brace/bracket
                while (endIndex < json.length() && json.charAt(endIndex) != ',' && json.charAt(endIndex) != '}' && json.charAt(endIndex) != ']') {
                    endIndex++;
                }
            }
            
            return json.substring(startIndex, endIndex).trim();
        }
        
        return null;
    }

    private void enviarRespostaErro(HttpServletResponse response, String mensagem)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String json = String.format("{\"success\": false, \"message\": \"%s\"}", escapeJson(mensagem));
        out.print(json);
    }
    
    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}
