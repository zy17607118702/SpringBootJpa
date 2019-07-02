package com.cn.springbootjpa.util.pdf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.cn.springbootjpa.util.DateUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 打印工具类 使用jasperReport打印pdf
 * @author zhangyang
 *
 */
public class JasperPrinter {

public static void exportPDF(JasperPrint jsperprint, String outputFileName) throws JRException {
JasperExportManager.exportReportToPdfFile(jsperprint, outputFileName);
}

public static void exportPDF(String fileName, String outputFileName, Map<String, Object> params,
Collection<?> detailList) throws JRException {
File jasperFile = new File(fileName);
JasperPrint jasperPrint = fillReport(fileName, params, detailList);
if (StringUtils.isBlank(outputFileName)) {
outputFileName = jasperFile.getParent() + "/"
+ DateUtils.format(new Date(), DateUtils.FORMAT_DATE_YYYY_MM_DD_HHMMSS) + ".pdf";
}
JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);
}

public static void exportPDFToWeb(InputStream inputStream, String outputFileName, Map<String, Object> params,
Collection<?> detailList, HttpServletResponse response) throws JRException, IOException {
exportPDFToWeb(inputStream, outputFileName, params, new JRBeanCollectionDataSource(detailList), response);
}

public static void exportPDFToWeb(InputStream inputStream, String outputFileName, Map<String, Object> params,
JRDataSource dataSource, HttpServletResponse response) throws JRException, IOException {
JasperPrint jasperPrint = fillReport(inputStream, params, dataSource);
if (StringUtils.isBlank(outputFileName)) {
outputFileName = DateUtils.format(new Date(), DateUtils.FORMAT_DATE_YYYY_MM_DD_HHMMSS) + ".pdf";
}
response.setContentType("application/pdf");
JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
}

public static void exportPDFToWeb(JasperPrint jasperPrint, String outputFileName, HttpServletResponse response)
throws JRException, IOException {
response.setContentType("application/pdf");
JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
}

public static void exportPDFToWeb(String fileName, String outputFileName, Map<String, Object> params,
Collection<?> detailList, HttpServletResponse response) throws JRException, IOException {
JasperPrint jasperPrint = fillReport(fileName, params, detailList);
if (StringUtils.isBlank(outputFileName)) {
outputFileName = DateUtils.format(new Date(), DateUtils.FORMAT_DATE_YYYY_MM_DD_HHMMSS) + ".pdf";
}
response.setContentType("application/pdf");
JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
}

public static JasperPrint fillReport(InputStream inputStream, Map<String, Object> params, Collection<?> detailList)
throws JRException {
return fillReport(inputStream, params, detailList == null ? null : new JRBeanCollectionDataSource(detailList));
}

public static JasperPrint fillReport(InputStream inputStream, Map<String, Object> params, JRDataSource datasource)
throws JRException {
JasperPrint jasperPrint = null;

if (datasource != null) {
jasperPrint = JasperFillManager.fillReport(inputStream, params, datasource);
} else {
jasperPrint = JasperFillManager.fillReport(inputStream, params, new JREmptyDataSource());
}
return jasperPrint;
}

public static JasperPrint fillReport(String fileName, Map<String, Object> params, Collection<?> detailList)
throws JRException {
JasperPrint jasperPrint = null;
JRBeanCollectionDataSource datasource = null;
if (detailList != null && !detailList.isEmpty()) {
datasource = new JRBeanCollectionDataSource(detailList);
jasperPrint = JasperFillManager.fillReport(fileName, params, datasource);
} else {
jasperPrint = JasperFillManager.fillReport(fileName, params, new JREmptyDataSource());
}
return jasperPrint;
}

public static void printFile(InputStream inputStream, Map<String, Object> fields, Collection<?> detailList,
String printerName, int copies) throws Exception {
JasperPrint jasperPrint;
jasperPrint = fillReport(inputStream, fields, detailList);
try {
int pages = jasperPrint.getPages().size() - 1;
if (StringUtils.isBlank(printerName)) {
if (copies > 1) {
for (int i = copies; i > 0; i--) {
JasperPrintManager.printPages(jasperPrint, 0, pages, false);
}
} else {
JasperPrintManager.printPages(jasperPrint, 0, pages, false);
}
} else {
ExtendedJRPrinterAWT.printPages(jasperPrint, 0, pages, printerName, copies);
}
} catch (Exception e) {
throw new Exception("Export report to pdf failed.", e);
}
}

public static void printFile(String fileName, Map<String, Object> params, Collection<?> detailList)
throws JRException {
JasperPrint jasperPrint;
jasperPrint = fillReport(fileName, params, detailList);
int size = jasperPrint.getPages().size();
if (size > 0) {
JasperPrintManager.printPages(jasperPrint, 0, size - 1, false);
} else {
JasperPrintManager.printReport(jasperPrint, false);
}

}

public static void printFile(String fileName, Map<String, Object> fields, Collection<?> detailList,
String printerName, int copies) throws Exception {
JasperPrint jasperPrint;
jasperPrint = fillReport(fileName, fields, detailList);
try {
int pages = jasperPrint.getPages().size() - 1;
if (StringUtils.isBlank(printerName)) {
if (copies > 1) {
for (int i = copies; i > 0; i--) {
JasperPrintManager.printPages(jasperPrint, 0, pages, false);
}
} else {
JasperPrintManager.printPages(jasperPrint, 0, pages, false);
}
} else {
ExtendedJRPrinterAWT.printPages(jasperPrint, 0, pages, printerName, copies);
}
} catch (Exception e) {
throw new Exception("Export report to pdf failed.", e);
}
}

private JasperPrinter() {

}

// public void print(InputStream file, String printerName) throws JRException {
// print(new SimpleExporterInput(file), printerName);
//
// }

// /**
// * @param input
// * @param printerName
// *
// */
// public void print(SimpleExporterInput input, String printerName) throws JRException {
// long start = System.currentTimeMillis();
// PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
// // printRequestAttributeSet.add(MediaSizeName.ISO_A4);
//
// PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
// if (!StringUtils.isBlank(printerName)) {
//
// printServiceAttributeSet.add(new PrinterName(printerName, null));
// }
// // printServiceAttributeSet.add(new PrinterName("hp LaserJet 1320 PCL 6",
// // null));
// // printServiceAttributeSet.add(new PrinterName("PDFCreator", null));
//
// JRPrintServiceExporter exporter = new JRPrintServiceExporter();
//
// exporter.setExporterInput(input);
// SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
// configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
// configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
// configuration.setDisplayPageDialog(false);
// configuration.setDisplayPrintDialog(false);
// exporter.setConfiguration(configuration);
// exporter.exportReport();
//
// System.err.println("Printing time : " + (System.currentTimeMillis() - start));
// }

// public void print(String file, String printerName) throws JRException {
// print(new SimpleExporterInput(file), printerName);
//
// }
}