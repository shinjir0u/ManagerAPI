package com.repository.manager.service.jasper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class JasperServiceImpl implements JasperService {
	@Override
	public byte[] exportFile(List<?> data, String format, String jrxmlFileName) throws Exception {
		InputStream inputStream = getClass().getResourceAsStream(jrxmlFileName);
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("title", "Report");

		JRDataSource dataSource = new JRBeanCollectionDataSource(data);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		byte[] jasperExport = getFileByFormat(format, jasperPrint);

		return jasperExport;
	}

	private byte[] getFileByFormat(String format, JasperPrint jasperPrint) throws JRException {
		byte[] jasperExport = null;
		switch (format.toLowerCase()) {
		case "pdf" -> jasperExport = JasperExportManager.exportReportToPdf(jasperPrint);
		case "xml" -> jasperExport = JasperExportManager.exportReportToXml(jasperPrint).getBytes();
		case "html" -> {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			HtmlExporter exporter = new HtmlExporter();

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));

			SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
			exporter.setConfiguration(configuration);

			exporter.exportReport();
			jasperExport = outputStream.toByteArray();
		}
		case "xlsx" -> {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			configuration.setCollapseRowSpan(false);
			configuration.setDetectCellType(true);

			exporter.setConfiguration(configuration);

			exporter.exportReport();
			jasperExport = outputStream.toByteArray();
		}
		case "docx" -> {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			JRDocxExporter exporter = new JRDocxExporter();

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

			SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
			exporter.setConfiguration(configuration);

			exporter.exportReport();
			jasperExport = outputStream.toByteArray();
		}
		default -> throw new IllegalArgumentException("Unexpected value: " + format);
		}
		return jasperExport;
	}
}
