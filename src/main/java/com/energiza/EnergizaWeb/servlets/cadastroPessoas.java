package com.energiza.EnergizaWeb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.energiza.EnergizaWeb.modelos.pessoa.*;

@WebServlet("/servlet/cadastroPessoas")
public class cadastroPessoas extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
//            PessoaFJ pessoa = new PessoaFJ();
//
//            // Tipo de pessoa (1 = Física, 2 = Jurídica)
//            int tipoPessoa = Integer.parseInt(request.getParameter("tipoPessoa"));
//            pessoa.setTipoPessoa(tipoPessoa);
//
//            // Apelido (opcional)
//            pessoa.setApelido(request.getParameter("apelido"));
//
//            // Identificação
//            Identificacao identificacao = new Identificacao();
//            identificacao.setNome(request.getParameter("nome"));
//            identificacao.setApelido(request.getParameter("apelido"));
//            // Caso tenha upload de imagem futuramente:
//            // identificacao.setImagem(...);
//            pessoa.setIdentificacao(identificacao);
//
//            // Localização
//            Localizacao localizacao = new Localizacao();
//            localizacao.setCep(request.getParameter("cep"));
//            localizacao.setEndereco(request.getParameter("endereco"));
//            localizacao.setNumero(request.getParameter("numero"));
//            localizacao.setComplemento(request.getParameter("complemento"));
//            localizacao.setBairro(request.getParameter("bairro"));
//            localizacao.setCidade(request.getParameter("cidade"));
//            localizacao.setEstado(request.getParameter("estado"));
//            localizacao.setPais(request.getParameter("pais"));
//            pessoa.setLocalizacao(localizacao);
//
//            // Documentos
//            List<Documento> documentos = new ArrayList<>();
//            if (tipoPessoa == 1) { // Física (CPF)
//                documentos.add(new Documento("CPF", request.getParameter("cpf")));
//            } else if (tipoPessoa == 2) { // Jurídica (CNPJ)
//                documentos.add(new Documento("CNPJ", request.getParameter("cnpj")));
//            }
//            pessoa.setDocumentos(documentos);
//
//            // Redes Sociais
//            String[] plataformas = request.getParameterValues("plataforma[]");
//            String[] telefones = request.getParameterValues("telefone[]");
//            String[] urls = request.getParameterValues("url[]");
//
//            List<RedeSocial> redesSociais = new ArrayList<>();
//            if (plataformas != null && telefones != null && urls != null) {
//                for (int i = 0; i < plataformas.length; i++) {
//                    redesSociais.add(new RedeSocial(plataformas[i], telefones[i], urls[i]));
//                }
//            }
//            pessoa.setRedesSociais(redesSociais);
//
//            // Inserir no banco via DAO
//            PessoaFJDAO pessoaFJDAO = new PessoaFJDAO();
//            pessoaFJDAO.inserirPessoaFJ(pessoa);

            // Redireciona para página de sucesso
            response.sendRedirect("menuPrincipal.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("erroCadastro.jsp");
        }
    }
}
