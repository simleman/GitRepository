package demo.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/*
 * 导出word文件的类
 */
public class JavaExportDoc {
	private Configuration configuration = null;

	public JavaExportDoc() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}
//导出word文件的方法
	public void createDoc(String modelName,String filepath ,Map<String, Object> dataMap) {

		Template t = null;
		configuration.setClassForTemplateLoading(this.getClass(), "/template");

		try {
			t = configuration.getTemplate("paper.xml");//选择要使用的模版
		} catch (IOException e) {
			e.printStackTrace();
		}
		String exportfilepath = filepath+ modelName;//配置导出路径
		File outFile = new File(exportfilepath);
		if (!outFile.exists()) {//判断文件是否存在
			try {
				outFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			t.process(dataMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void main(String[] args) { JavaExportDoc dh = new
	 * JavaExportDoc(); String filePath = "D:/outFile.doc";//导出doc文件的路径 String
	 * modelName = "test.xml";//模板名称 dh.createDoc(modelName, filePath);
	 * System.out.println("	导出成功"); }
	 */

}
