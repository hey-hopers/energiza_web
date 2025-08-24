package com.energiza.EnergizaWeb.servlets.minhaFatura;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.utils.ExtrairDadosFatura;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;

@WebServlet("/servlet/uploadFatura/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
                 maxFileSize = 1024 * 1024 * 10,   // 10MB
                 maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadFaturaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ExtrairDadosFatura extrairDados;

    public UploadFaturaServlet() {
        super();
        extrairDados = new ExtrairDadosFatura();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

            Part filePart = request.getPart("files");
            if (filePart == null || filePart.getSize() == 0) {
                out.print("{\"success\": false, \"message\": \"Nenhum arquivo enviado\"}");
                return;
            }

            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            File uploadDir = new File("C:/EnergizaWeb/files/faturas");
            if (!uploadDir.exists()) uploadDir.mkdirs();

            File destino = new File(uploadDir, fileName);
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            
            String dados = extrairDados.extrairFatura();
            
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.delete(destino.toPath());
            }
            
            Map<String, String> dadosExtraidos = new HashMap<>();
            
            dadosExtraidos.put("unidadeConsumo", extractValue(dados, "unidadeConsumo"));
            dadosExtraidos.put("referencia", extractValue(dados, "referencia"));
            dadosExtraidos.put("vencimento", extractValue(dados, "vencimento"));
            dadosExtraidos.put("data_leitura_anterior", extractValue(dados, "data_leitura_anterior"));
            dadosExtraidos.put("data_leitura_atual", extractValue(dados, "data_leitura_atual"));
            dadosExtraidos.put("data_leitura_proxima", extractValue(dados, "data_leitura_proxima"));
            dadosExtraidos.put("diasLidos", extractValue(dados, "diasLidos"));
            dadosExtraidos.put("medidor", extractValue(dados, "medidor"));
            dadosExtraidos.put("leituraAnterior", extractValue(dados, "leituraAnterior"));
            dadosExtraidos.put("leituraAtual", extractValue(dados, "leituraAtual"));
            dadosExtraidos.put("totalApurado", extractValue(dados, "totalApurado"));
            dadosExtraidos.put("valorTotal", extractValue(dados, "valorTotal"));

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");
            jsonBuilder.append("\"success\": true,");
            jsonBuilder.append("\"fatura\": {");
            
            int count = 0;
            for (Map.Entry<String, String> entry : dadosExtraidos.entrySet()) {
                jsonBuilder.append("\"")
                           .append(escapeJson(entry.getKey()))
                           .append("\": \"")
                           .append(escapeJson(entry.getValue()))
                           .append("\"");

                count++;
                if (count < dadosExtraidos.size()) {
                    jsonBuilder.append(", ");
                }
            }

            jsonBuilder.append("}");
            jsonBuilder.append("}");

            String json = jsonBuilder.toString();

            out.print(json);

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Erro interno: " + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "")
                    .replace("\r", "");
    }
    
    public static String extractValue(String json, String key) {
        if (json == null || key == null) return "";

        String regex = "\"" + Pattern.quote(key) + "\"\\s*:\\s*(\"([^\"]*)\"|[\\d.+-]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            String value;
            if (matcher.group(2) != null) {
                value = matcher.group(2);
            } else {
                value = matcher.group(1);
            }

            if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }

            return value;
        }

        return "";
    }
}
