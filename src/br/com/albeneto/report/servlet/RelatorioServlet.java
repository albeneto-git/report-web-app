package br.com.albeneto.report.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.albeneto.report.gerador.GeradorRelatorio;
import br.com.albeneto.report.infra.ConnectionFactory;

@WebServlet("/gastos")
public class RelatorioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/gasto_por_mes.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			Connection conexao = new ConnectionFactory().getConnection();
			
			String data_ini = request.getParameter("data_ini");
			String data_fim = request.getParameter("data_fim");
			
			Date dataInicial = new SimpleDateFormat("dd/MM/yyyy").parse(data_ini);
			Date dataFinal = new SimpleDateFormat("dd/MM/yyyy").parse(data_fim);
						
			parametros.put("DATA_INI", dataInicial);
			parametros.put("DATA_FIM", dataFinal);
			
			GeradorRelatorio gerador = new GeradorRelatorio(nome, parametros, conexao);
			gerador.geraPDFParaOutputStream(response.getOutputStream());
			
			conexao.close();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
