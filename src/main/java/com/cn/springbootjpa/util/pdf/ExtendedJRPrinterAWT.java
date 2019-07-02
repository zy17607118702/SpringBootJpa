package com.cn.springbootjpa.util.pdf;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.print.JRPrinterAWT;

/**
 */
public class ExtendedJRPrinterAWT extends JRPrinterAWT {

/**
* @param jasperPrint2
* @param firstPageIndex
* @param lastPageIndex
* @param printerName
* @param copies
* @return
* @throws JRException
*/
public static void printPages(JasperPrint jrPrint, int firstPageIndex, int lastPageIndex, String printerName,
int copies) throws JRException {
ExtendedJRPrinterAWT printer = new ExtendedJRPrinterAWT(jrPrint);
printer.printPages(firstPageIndex, lastPageIndex, printerName, copies);
}

/**
* @param arg0
* @throws JRException
*/
/**
* 
*/
private JasperPrint jasperPrint = null;

public ExtendedJRPrinterAWT(JasperPrint jrPrint) throws JRException {
super(jrPrint);
jasperPrint = jrPrint;
}

/**
* @param printerName
* @param attributes
* @return
*/
private PrintService getNamedPrinter(String printerName, PrintRequestAttributeSet attributes) {
PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attributes);
if (services.length > 0) {
if (printerName == null)
return services[0];
else {
// System.out.println("Available Printer Names:");
for (int i = 0; i < services.length; i++) {
// System.out.println("\t"+services[i].getName());
if (services[i].getName().equals(printerName)) {
return services[i];
}
}
}
}
return null;
}

/**
* @param firstPageIndex
* @param lastPageIndex
* @param printerName
* @param copies
* @return
* @throws JRException
*/
private void printPages(int firstPageIndex, int lastPageIndex, String printerName, int copies) throws JRException {
if (printerName == null || "".equals(printerName)) {
return;
}
if (firstPageIndex < 0 || firstPageIndex > lastPageIndex || lastPageIndex >= jasperPrint.getPages().size()) {
throw new JRException("Invalid page index range : " + firstPageIndex + " - " + lastPageIndex + " of "
+ jasperPrint.getPages().size());
}

PrinterJob printJob = PrinterJob.getPrinterJob();

initPrinterJobFields(printJob);

PageFormat pageFormat = printJob.defaultPage();

Paper paper = pageFormat.getPaper();

printJob.setJobName(jasperPrint.getName());

switch (jasperPrint.getOrientationValue()) {
case LANDSCAPE: {
pageFormat.setOrientation(PageFormat.LANDSCAPE);
paper.setSize(jasperPrint.getPageHeight(), jasperPrint.getPageWidth());
paper.setImageableArea(0, 0, jasperPrint.getPageHeight(), jasperPrint.getPageWidth());
break;
}
case PORTRAIT:
default: {
pageFormat.setOrientation(PageFormat.PORTRAIT);
paper.setSize(jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
paper.setImageableArea(0, 0, jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
}
}

pageFormat.setPaper(paper);

try {
PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
PrintService service = getNamedPrinter(printerName, null);
if (copies > 1) {
for (int i = copies; i > 0; i--) {
Book book = new Book();
book.append(this, pageFormat, lastPageIndex - firstPageIndex + 1);
printJob.setPrintService(service);
printJob.setPageable(book);
printJob.print(attributes);
}
} else {
Book book = new Book();
book.append(this, pageFormat, lastPageIndex - firstPageIndex + 1);
printJob.setPrintService(service);
printJob.setPageable(book);
printJob.print(attributes);
}
} catch (Exception ex) {
ex.printStackTrace();
}
}
}