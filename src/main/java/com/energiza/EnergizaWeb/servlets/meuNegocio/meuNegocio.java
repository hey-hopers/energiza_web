package com.energiza.EnergizaWeb.servlets.meuNegocio;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.UsuarioDAO;
import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJ;
//import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJDAO;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;


@WebServlet("/servlet/meuNegocio/*")
public class meuNegocio extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
//    private PessoaFJDAO pessoaFJDAO;
	
	public meuNegocio() {
        super();
        usuarioDAO = new UsuarioDAO();
		/* pessoaFJDAO = new PessoaFJDAO(); */
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

		/*
		 * Usuario usuarioLogado = (Usuario)
		 * request.getSession(false).getAttribute("CURRENT_USER");
		 * 
		 * Usuario usuario = usuarioDAO.getUsuarioById(usuarioLogado.getId());
		 * 
		 * if (usuario == null) {
		 * out.print("{\"success\": false, \"message\": \"Usuário não logado!\"}");
		 * return; }
		 * 
		 * if(!pessoaFJDAO.getPessoaCadastrada(usuarioLogado.getId())) { out.
		 * print("{\"success\": false, \"message\": \"Meu negócio não encontrado!\"}");
		 * return; }
		 * 
		 * PessoaFJ pessoa = pessoaFJDAO.getPessoa(usuarioLogado.getId());
		 * 
		 * String json = String.format( "{" + "\"success\": true," + "\"pessoa\": {" +
		 * "\"nome\": \"%s\"," + "\"documento\": \"%s\"," + "\"cep\": \"%s\"," +
		 * "\"endereco\": \"%s\"," + "\"numero\": \"%s\"," + "\"complemento\": \"%s\","
		 * + "\"bairro\": \"%s\"," + "\"cidade\": \"%s\"," + "\"estado\": \"%s\"," +
		 * "\"pais\": \"%s\"" + "}" + "}", escapeJson(pessoa.getNome()),
		 * escapeJson(pessoa.getDocumento()), escapeJson(pessoa.getCep()),
		 * escapeJson(pessoa.getEndereco()), escapeJson(pessoa.getNumero()),
		 * escapeJson(pessoa.getComplemento()), escapeJson(pessoa.getBairro()),
		 * escapeJson(pessoa.getCidade()), escapeJson(pessoa.getEstado()),
		 * escapeJson(pessoa.getPais()) ); out.print(json);
		 */
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
            
            String nome = request.getParameter("nome");
            String documento = request.getParameter("documento");
            String cep = request.getParameter("cep");
            String endereco = request.getParameter("endereco");
            String numero = request.getParameter("numero");
            String complemento = request.getParameter("complemento");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String pais = request.getParameter("pais");
            
			/*
			 * PessoaFJ pessoa = pessoaFJDAO.getPessoaFJByIdUser(usuario.getId());
			 * 
			 * if (pessoa == null) {
			 * 
			 * pessoa = new PessoaFJ();
			 * 
			 * pessoa.setNome(nome); pessoa.setDocumento(documento); pessoa.setCep(cep);
			 * pessoa.setEndereco(endereco); pessoa.setNumero(numero);
			 * pessoa.setComplemento(complemento); pessoa.setBairro(bairro);
			 * pessoa.setCidade(cidade); pessoa.setEstado(estado); pessoa.setPais(pais);
			 * 
			 * if (pessoaFJDAO.inserirPessoaFJ(pessoa, usuario)) {
			 * System.out.println(pessoa.getId()); usuario.setIdPessoa(pessoa.getId());
			 * SessionManager.getInstance().setCurrentUser(request, usuario);
			 * 
			 * out.
			 * print("{\"success\": true, \"message\": \"Cadastro de negócio criado com sucesso!\"}"
			 * ); } else { enviarRespostaErro(response, "Erro ao criar negócio"); }
			 * 
			 * } else {
			 * 
			 * pessoa.setNome(nome); pessoa.setDocumento(documento); pessoa.setCep(cep);
			 * pessoa.setEndereco(endereco); pessoa.setNumero(numero);
			 * pessoa.setComplemento(complemento); pessoa.setBairro(bairro);
			 * pessoa.setCidade(cidade); pessoa.setEstado(estado); pessoa.setPais(pais);
			 * 
			 * if (pessoaFJDAO.atualizarPessoaFJ(pessoa)) { out.
			 * print("{\"success\": true, \"message\": \"Cadastro de negócio editado com sucesso!\"}"
			 * ); } else { enviarRespostaErro(response, "Erro ao editar negócio"); }
			 * 
			 * }
			 */ 
            
            

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
