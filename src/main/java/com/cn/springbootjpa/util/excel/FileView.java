package com.cn.springbootjpa.util.excel;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jxls.common.Context;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.AbstractView;

public class FileView extends AbstractView {
private static final String CONTENT_TYPE = "application/vnd.ms-excel";

private String exportFileName;
private String file;

/**
* @param file
*            模版相对于当前classpath路径
* @param exportFileName
*            导出文件名
*/
public FileView(String file, String exportFileName) {
this.file = file;
if (exportFileName != null) {
try {
exportFileName = URLEncoder.encode(exportFileName, "UTF-8");
} catch (UnsupportedEncodingException e) {
e.printStackTrace();
}
}
this.exportFileName = exportFileName;
setContentType(CONTENT_TYPE);
}

@Override
protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
HttpServletResponse response) throws Exception {
Context context = new Context(model);
response.setContentType(getContentType());
response.setHeader("content-disposition", "attachment;filename=" + exportFileName + ".xlsx");
ServletOutputStream os = response.getOutputStream();
InputStream is = new ClassPathResource(file).getInputStream();
IOUtils.copy(is, os);
is.close();
}
}