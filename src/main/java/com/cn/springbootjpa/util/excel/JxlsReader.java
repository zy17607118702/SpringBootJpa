package com.cn.springbootjpa.util.excel;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadMessage;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.springframework.core.io.ClassPathResource;

/**
 * <p>
 * Jxls file reader.
 * </p>
 * <b>2018-04-18</b>
 * 
 * @author danne<lshefan@163.com>
 * 
 */
public class JxlsReader {

public static final List<String> readXls(ClassPathResource resourceXML, File fileXLS, Map<String, Object> beans) {
try {
InputStream inputXML = resourceXML.getInputStream();
InputStream inputXLS = new FileInputStream(fileXLS);
return JxlsReader.readXls(inputXML, inputXLS, beans);
} catch (Exception e) {
e.printStackTrace();
return null;
}
}

public static final List<String> readXls(File fileXML, File fileXLS, Map<String, Object> beans) {
try {
InputStream inputXML = new FileInputStream(fileXML);
InputStream inputXLS = new FileInputStream(fileXLS);
return JxlsReader.readXls(inputXML, inputXLS, beans);
} catch (Exception e) {
e.printStackTrace();
return null;
}
}

public static final List<String> readXls(InputStream inputXML, InputStream inputXLS, Map<String, Object> beans) {
List<String> errors = new ArrayList<>();
try {
// InputStream inputXML = new BufferedInputStream(
// new ClassPathResource("/templates/master/warehouse.xml").getInputStream());
XLSReader mainReader = ReaderBuilder.buildFromXML(new BufferedInputStream(inputXML));
// InputStream inputXLS = new BufferedInputStream(
// new ClassPathResource("/templates/master/warehouse.xls").getInputStream());
XLSReadStatus status = mainReader.read(new BufferedInputStream(inputXLS), beans);
if (status.isStatusOK()) {
return errors;
} else {
List<?> list = status.getReadMessages();
if (list != null && !list.isEmpty()) {
for (Object o : list) {
XLSReadMessage msg = (XLSReadMessage) o;
errors.add(msg.getMessage());
}
}
}
} catch (Exception e) {
e.printStackTrace();
errors.add(e.getLocalizedMessage());
}
return errors;
}
}