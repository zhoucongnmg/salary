package net.sion.company.salary.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sion.core.filedump.util.AsposeUtil;
import net.sion.util.file.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PayrollService {
	@Autowired ApplicationContext ctx;
	@Autowired AsposeUtil asposeUtil;
	
	public  String exportExcel(String fileName, List<Map<String, Object>> columns, List<Map<String, Object>> datas, HttpServletResponse response) throws IOException{
		String serverPath = ctx.getResource("/").getFile().getPath();
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<String> columnDataIndex = new ArrayList<String>();
		List<Object> columnHeader = new ArrayList<Object>();
		for(Map<String, Object> column : columns){
			columnHeader.add(column.get("text").toString());
			columnDataIndex.add(column.get("dataIndex").toString());
		}
		list.add(columnHeader);
		for(Map<String, Object> data : datas){
			List<Object> rowList = new ArrayList<Object>();
			for(String column : columnDataIndex){
				if(data.get(column) != null){
					rowList.add(data.get(column).toString());
				}else{
					rowList.add("");
				}
			}
			list.add(rowList);
		}
		String filePath = asposeUtil.createExcelSimple(list, "Sheet1", serverPath, "xls");
		File file = new File(filePath);
		FileUtil.download(fileName + ".xls", new FileInputStream(file), response);
		return filePath;
	}
	public  String createExcel(String fileName,List<Map<String, Object>> columns, List<Map<String, Object>> datas, HttpServletResponse response) throws IOException{
		String serverPath = ctx.getResource("/").getFile().getPath();
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<String> columnDataIndex = new ArrayList<String>();
		List<Object> columnHeader = new ArrayList<Object>();
		for(Map<String, Object> column : columns){
			columnHeader.add(column.get("text").toString());
			columnDataIndex.add(column.get("dataIndex").toString());
		}
		
		for(Map<String, Object> data : datas){
			List<Object> rowList = new ArrayList<Object>();
			for(String column : columnDataIndex){
				if(data.get(column) != null){
					rowList.add(data.get(column).toString());
				}else{
					rowList.add("");
				}
			}
			list.add(columnHeader);
			list.add(rowList);
			list.add(null);
		}
		String filePath = asposeUtil.createExcelSimple(list, "Sheet1", serverPath, "xls");
		File file = new File(filePath);
		
		FileUtil.download(fileName + ".xls", new FileInputStream(file), response);
		return filePath;
	}
}
