package com.energiza.EnergizaWeb.servlets.unidadeConsumo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;
import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJ;
import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJDAO;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.UnidadeConsumo;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.UnidadeConsumoDAO;

@WebServlet("/servlet/unidadeConsumo/*")
public class UnidadeConsumoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UnidadeConsumoDAO unidadeConsumoDAO;
    private PessoaFJDAO pessoaFJDAO;

    public UnidadeConsumoServlet() {
        super();
        unidadeConsumoDAO = new UnidadeConsumoDAO();
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

        List<UnidadeConsumo> unidadesConsumo = unidadeConsumoDAO.getUnidadesConsumoByUsuario(usuario.getId());
        StringBuilder json = new StringBuilder();
        json.append("{\"success\": true, \"unidades\": [");
        
        for (int i = 0; i < unidadesConsumo.size(); i++) {
        	UnidadeConsumo uc = unidadesConsumo.get(i);
        	json.append(String.format(
        		    "{" +
        		    "\"id_unidade_consumo\": %d," +
        		    "\"uc_codigo\": %s," +
        		    "\"apelido\": \"%s\"" +  
        		    "}",                  
        		    uc.getId(),
        		    uc.getUcCodigo(),
        		    escapeJson(uc.getNomePessoa())
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
            
            if(!pessoaFJDAO.getPessoaCadastrada(usuario.getId())) {
            	out.print("{\"success\": false, \"message\": \"Meu negócio não encontrado!\"}");
                return;
            }
            
            String uc_codigo = request.getParameter("uc_codigo");
            boolean eh_geradora = Boolean.parseBoolean(request.getParameter("eh_geradora"));
            String etapa = request.getParameter("etapa");
            String cep = request.getParameter("cep");
            String endereco = request.getParameter("endereco");
            String numero = request.getParameter("numero");
            String complemento = request.getParameter("complemento");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String pais = request.getParameter("pais");
            
            UnidadeConsumo uc = unidadeConsumoDAO.getUnidadeConsumo(usuario.getId()); 
            
            PessoaFJ pessoa = pessoaFJDAO.getPessoaFJByIdUser(usuario.getId()); 
            
            if (uc == null) {
                
            	uc = new UnidadeConsumo();
                
            	uc.setUcCodigo(uc_codigo);
            	uc.setEhGeradora(eh_geradora);
            	uc.setEtapa(etapa);
            	uc.setCep(cep);
            	uc.setEndereco(endereco);
            	uc.setNumero(numero);
            	uc.setComplemento(complemento);
            	uc.setBairro(bairro);
            	uc.setCidade(cidade);
            	uc.setEstado(estado);
            	uc.setPais(pais);

            	if (unidadeConsumoDAO.inserirUnidadeConsumo(uc, pessoa)) {                  
                    out.print("{\"success\": true, \"message\": \"Cadastro de negócio criado com sucesso!\"}");
                    
                } else {
                    enviarRespostaErro(response, "Erro ao criar unidade de consumo");
                }
            	
            } else {      
                
            	uc.setUcCodigo(uc_codigo);
            	uc.setEhGeradora(eh_geradora);
            	uc.setEtapa(etapa);
                uc.setCep(cep);
                uc.setEndereco(endereco);
                uc.setNumero(numero);
                uc.setComplemento(complemento);
                uc.setBairro(bairro);
                uc.setCidade(cidade);
                uc.setEstado(estado);
                uc.setPais(pais);

            	if (unidadeConsumoDAO.atualizarUnidadeConsumo(uc)) {
                    out.print("{\"success\": true, \"message\": \"Cadastro de negócio editado com sucesso!\"}");
                } else {
                    enviarRespostaErro(response, "Erro ao editar negócio");
                }
            	
            }         

        } catch (Exception e) {
            e.printStackTrace();
            enviarRespostaErro(response, "Erro interno: " + e.getMessage());
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
    
    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}
