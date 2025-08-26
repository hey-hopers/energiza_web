package com.energiza.EnergizaWeb.servlets.minhaFatura;

/**
 * ========================================
 * SERVLET DE SALVAMENTO DE FATURAS
 * ========================================
 * 
 * Este servlet é responsável por processar e salvar dados de faturas
 * de energia elétrica no banco de dados.
 * 
 * Funcionalidades:
 * - Validação de dados de entrada
 * - Verificação de unidade de consumo
 * - Conversão de tipos de dados
 * - Persistência no banco de dados
 * - Criação de registros relacionados (leitura, itens)
 * 
 * Endpoint: /servlet/salvarFatura/*
 * Método: POST
 * 
 * @author Energiza Web
 * @version 1.0
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;
import com.energiza.EnergizaWeb.modelos.minhaFatura.Fatura;
import com.energiza.EnergizaWeb.modelos.minhaFatura.FaturaDAO;
import com.energiza.EnergizaWeb.modelos.minhaFatura.FtItensFatura;
import com.energiza.EnergizaWeb.modelos.minhaFatura.FtItensFaturaDAO;
import com.energiza.EnergizaWeb.modelos.minhaFatura.FtLeitura;
import com.energiza.EnergizaWeb.modelos.minhaFatura.FtLeituraDAO;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.UnidadeConsumo;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.UnidadeConsumoDAO;

/**
 * Servlet para salvar faturas de energia elétrica
 * Processa dados do formulário e persiste no banco
 */
@WebServlet("/servlet/salvarFatura/*")
public class SalvarFaturaServlet extends HttpServlet {
    /** Serial version UID para serialização */
    private static final long serialVersionUID = 1L;
    
    // ========================================
    // DAOs PARA ACESSO AO BANCO DE DADOS
    // ========================================
    /** DAO para operações com faturas */
    private FaturaDAO faturaDAO;
    /** DAO para operações com itens de fatura */
    private FtItensFaturaDAO itensFaturaDAO;
    /** DAO para operações com leituras de medidor */
    private FtLeituraDAO leituraDAO;
    /** DAO para operações com unidades de consumo */
    private UnidadeConsumoDAO unidadeConsumoDAO;
    
    /**
     * Construtor do servlet
     * Inicializa todos os DAOs necessários para operações no banco
     */
    public SalvarFaturaServlet() {
        super();
        faturaDAO = new FaturaDAO();
        itensFaturaDAO = new FtItensFaturaDAO();
        leituraDAO = new FtLeituraDAO();
        unidadeConsumoDAO = new UnidadeConsumoDAO();
    }
    
    /**
     * Processa requisições POST para salvar faturas
     * Recebe dados do formulário, valida e persiste no banco
     * 
     * @param request Requisição HTTP com dados da fatura
     * @param response Resposta HTTP com resultado da operação
     * @throws ServletException Erro no processamento do servlet
     * @throws IOException Erro de entrada/saída
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            // ========================================
            // VERIFICAÇÃO DE AUTENTICAÇÃO
            // ========================================
            // Verificar se o usuário está logado
            Usuario usuario = SessionManager.getInstance().getCurrentUser(request);
            if (usuario == null) {
                out.print("{\"success\": false, \"message\": \"Usuário não logado\"}");
                return;
            }
            
            // ========================================
            // EXTRAÇÃO DE PARÂMETROS DO FORMULÁRIO
            // ========================================
            // Obter parâmetros do formulário
            String referencia = request.getParameter("referencia");
            String vencimentoStr = request.getParameter("vencimento");
            String unidadeConsumoStr = request.getParameter("unidadeConsumo");
            String valorTotalStr = request.getParameter("valor_total");
            
            // Debug: Log dos parâmetros recebidos
            System.out.println("DEBUG - Parâmetros recebidos:");
            System.out.println("referencia: '" + referencia + "'");
            System.out.println("vencimentoStr: '" + vencimentoStr + "'");
            System.out.println("unidadeConsumoStr: '" + unidadeConsumoStr + "'");
            System.out.println("valorTotalStr: '" + valorTotalStr + "'");
            
            // ========================================
            // PARÂMETROS DE LEITURA DO MEDIDOR
            // ========================================
            // Parâmetros de leitura
            String diasLidosStr = request.getParameter("dias_lidos");
            String medidor = request.getParameter("medidor");
            String dataLeituraAnteriorStr = request.getParameter("data_leitura_anterior");
            String dataLeituraAtualStr = request.getParameter("data_leitura_atual");
            String dataLeituraProximaStr = request.getParameter("data_leitura_proxima");
            String leituraAnteriorStr = request.getParameter("leitura_anterior");
            String leituraAtualStr = request.getParameter("leitura_atual");
            String totalApuradoStr = request.getParameter("total_apurado");
            
            // ========================================
            // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
            // ========================================
            // Validar campos obrigatórios
            System.out.println("DEBUG - Validando referencia: '" + referencia + "'");
            if (referencia == null || referencia.trim().isEmpty()) {
                System.out.println("DEBUG - Referencia é nula ou vazia");
                out.print("{\"success\": false, \"message\": \"Referência é obrigatória\"}");
                return;
            }
            System.out.println("DEBUG - Referencia validada com sucesso");
            
            if (vencimentoStr == null || vencimentoStr.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"Vencimento é obrigatório\"}");
                return;
            }
            
            if (unidadeConsumoStr == null || unidadeConsumoStr.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"Unidade de consumo é obrigatória\"}");
                return;
            }
            
            if (valorTotalStr == null || valorTotalStr.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"Valor total é obrigatório\"}");
                return;
            }
            
            
            
            // ========================================
            // CONVERSÃO DE TIPOS DE DADOS
            // ========================================
            // Converter tipos de dados
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date vencimento = null;
            Date dataLeituraAnterior = null;
            Date dataLeituraAtual = null;
            Date dataLeituraProxima = null;
            
            try {
                // Tentar diferentes formatos de data
                if (vencimentoStr != null && !vencimentoStr.trim().isEmpty()) {
                    try {
                        vencimento = dateFormat.parse(vencimentoStr);
                    } catch (ParseException e) {
                        // Tentar formato yyyy-MM-dd
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                        vencimento = dateFormat2.parse(vencimentoStr);
                    }
                }
                
                if (dataLeituraAnteriorStr != null && !dataLeituraAnteriorStr.trim().isEmpty()) {
                    try {
                        dataLeituraAnterior = dateFormat.parse(dataLeituraAnteriorStr);
                    } catch (ParseException e) {
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                        dataLeituraAnterior = dateFormat2.parse(dataLeituraAnteriorStr);
                    }
                }
                
                if (dataLeituraAtualStr != null && !dataLeituraAtualStr.trim().isEmpty()) {
                    try {
                        dataLeituraAtual = dateFormat.parse(dataLeituraAtualStr);
                    } catch (ParseException e) {
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                        dataLeituraAtual = dateFormat2.parse(dataLeituraAtualStr);
                    }
                }
                
                if (dataLeituraProximaStr != null && !dataLeituraProximaStr.trim().isEmpty()) {
                    try {
                        dataLeituraProxima = dateFormat.parse(dataLeituraProximaStr);
                    } catch (ParseException e) {
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                        dataLeituraProxima = dateFormat2.parse(dataLeituraProximaStr);
                    }
                }
            } catch (ParseException e) {
                out.print("{\"success\": false, \"message\": \"Formato de data inválido: " + e.getMessage() + "\"}");
                return;
            }
            
            // ========================================
            // CONVERSÃO DE CAMPOS NUMÉRICOS
            // ========================================
            // Converter campos numéricos
            int idUnidadeConsumo = 0;
            BigDecimal valorTotal = BigDecimal.ZERO;
            int diasLidos = 0;
            BigDecimal leituraAnterior = BigDecimal.ZERO;
            BigDecimal leituraAtual = BigDecimal.ZERO;
            BigDecimal totalApurado = BigDecimal.ZERO;
            
            try {
                if (unidadeConsumoStr != null && !unidadeConsumoStr.trim().isEmpty()) {
                    idUnidadeConsumo = Integer.parseInt(unidadeConsumoStr.trim());
                }
                
                if (valorTotalStr != null && !valorTotalStr.trim().isEmpty()) {
                    valorTotal = new BigDecimal(valorTotalStr.trim().replace(",", "."));
                }
                
                if (diasLidosStr != null && !diasLidosStr.trim().isEmpty()) {
                    diasLidos = Integer.parseInt(diasLidosStr.trim());
                }
                
                if (leituraAnteriorStr != null && !leituraAnteriorStr.trim().isEmpty()) {
                    leituraAnterior = new BigDecimal(leituraAnteriorStr.trim().replace(",", "."));
                }
                
                if (leituraAtualStr != null && !leituraAtualStr.trim().isEmpty()) {
                    leituraAtual = new BigDecimal(leituraAtualStr.trim().replace(",", "."));
                }
                
                if (totalApuradoStr != null && !totalApuradoStr.trim().isEmpty()) {
                    totalApurado = new BigDecimal(totalApuradoStr.trim().replace(",", "."));
                }
            } catch (NumberFormatException e) {
                out.print("{\"success\": false, \"message\": \"Valor numérico inválido: " + e.getMessage() + "\"}");
                return;
            }
            
         // ========================================
            // VALIDAÇÃO DE UNIDADE DE CONSUMO
            // ========================================
            // Validar se a unidade de consumo existe e pertence ao usuário
            try {
                List<UnidadeConsumo> unidadesUsuario = unidadeConsumoDAO.getUnidadesConsumoByUsuario(usuario.getId());
                boolean unidadeValida = false;
                
                for (UnidadeConsumo uc : unidadesUsuario) {
                    if (uc.getId() == idUnidadeConsumo) {
                        unidadeValida = true;
                        break;
                    }
                }
                
                if (!unidadeValida) {
                    out.print("{\"success\": false, \"message\": \"Unidade de consumo inválida ou não pertence ao usuário logado\"}");
                    return;
                }
                
                System.out.println("DEBUG - Unidade de consumo validada: " + idUnidadeConsumo);
            } catch (Exception e) {
                System.out.println("DEBUG - Erro ao validar unidade de consumo: " + e.getMessage());
                out.print("{\"success\": false, \"message\": \"Erro ao validar unidade de consumo\"}");
                return;
            }
            
            // ========================================
            // PERSISTÊNCIA NO BANCO DE DADOS
            // ========================================
            // Criar e salvar a fatura
            Fatura fatura = new Fatura(referencia, vencimento, idUnidadeConsumo, valorTotal);
            int idFatura = faturaDAO.incluirFatura(fatura);
            
            if (idFatura > 0) {
                // ========================================
                // SALVAMENTO DE DADOS RELACIONADOS
                // ========================================
                // Salvar leitura se houver dados
                if (medidor != null && !medidor.trim().isEmpty()) {
                    FtLeitura leitura = new FtLeitura(
                        dataLeituraAnterior,
                        dataLeituraAtual,
                        dataLeituraProxima,
                        diasLidos,
                        medidor,
                        leituraAnterior,
                        leituraAtual,
                        totalApurado,
                        idFatura,
                        "ENERGIA" // Serviço padrão
                    );
                    
                    leituraDAO.incluirLeitura(leitura);
                }
                
                // ========================================
                // CRIAÇÃO DE ITEM PADRÃO DA FATURA
                // ========================================
                // Criar item padrão da fatura
                FtItensFatura itemFatura = new FtItensFatura(
                    "Consumo de Energia",
                    "kWh",
                    totalApurado,
                    valorTotal,
                    idFatura
                );
                
                itensFaturaDAO.incluirItemFatura(itemFatura);
                
                out.print("{\"success\": true, \"message\": \"Fatura salva com sucesso!\", \"idFatura\": " + idFatura + "}");
            } else {
                out.print("{\"success\": false, \"message\": \"Erro ao salvar fatura\"}");
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao salvar fatura: " + e.getMessage());
            out.print("{\"success\": false, \"message\": \"Erro interno do servidor\"}");
        }
    }
    
    /**
     * Método GET não é suportado neste servlet
     * Retorna erro HTTP 405 (Method Not Allowed)
     * 
     * @param request Requisição HTTP
     * @param response Resposta HTTP
     * @throws ServletException Erro no processamento
     * @throws IOException Erro de entrada/saída
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método GET não é suportado");
    }
}
