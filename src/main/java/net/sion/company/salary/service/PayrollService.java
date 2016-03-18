package net.sion.company.salary.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sion.core.filedump.util.AsposeCellsMergeBean;
import net.sion.core.filedump.util.AsposeUtil;
import net.sion.util.file.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

@Service
public class PayrollService {
	@Autowired ApplicationContext ctx;
	@Autowired AsposeUtil asposeUtil;
	
	public  String exportSheets(String fileName, List<Map<String, Object>> columns, List<Map<String, Object>> datas, HttpServletResponse response) throws IOException{
		String serverPath = ctx.getResource("/").getFile().getPath();
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<String> columnDataIndex = new ArrayList<String>();
		List<Object> columnHeader = new ArrayList<Object>();
		for(Map<String, Object> column : columns){
			if(column.get("columns") != null){
				List<Map<String, Object>> list1 = (List) column.get("columns");
				for(Map<String, Object> column1 : list1){
					List<Map<String, Object>> list2 = (List) column1.get("columns");
					for(Map<String, Object> column2 : list2){
						columnHeader.add(column.get("text").toString() + "-" + column1.get("text").toString() + "-" + column2.get("text").toString() );
						columnDataIndex.add(column2.get("dataIndex").toString());
					}
				}
			}else{
				columnHeader.add(column.get("text").toString());
				columnDataIndex.add(column.get("dataIndex").toString());
			}
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
		
//		String newFilePath = serverPath;
//		Workbook workbook = new Workbook();
//		List<AsposeCellsMergeBean> asposeCellsMergeList = new ArrayList();
//		AsposeCellsMergeBean bean1 = new AsposeCellsMergeBean();
//		bean1.setFirstColumn(1);
//		bean1.setFirstRow(0);
//		bean1.setTotalColumns(2);
//		bean1.setTotalRows(1);
//		asposeCellsMergeList.add(bean1);
//		AsposeCellsMergeBean bean2 = new AsposeCellsMergeBean();
//		bean2.setFirstColumn(3);
//		bean2.setFirstRow(0);
//		bean2.setTotalColumns(1);
//		bean2.setTotalRows(2);
//		asposeCellsMergeList.add(bean2);
//		AsposeCellsMergeBean bean3 = new AsposeCellsMergeBean();
//		bean3.setFirstColumn(4);
//		bean3.setFirstRow(1);
//		bean3.setTotalColumns(1);
//		bean3.setTotalRows(2);
//		asposeCellsMergeList.add(bean3);
//		Worksheet sheet = AsposeUtil.addSheet(workbook, "Sheet1", list, null, null, asposeCellsMergeList);
//		String filePath = AsposeUtil.saveExcel(newFilePath, "xls",workbook);
		
		File file = new File(filePath);
		FileUtil.download(fileName + ".xls", new FileInputStream(file), response);
		return filePath;
	}
	public  String exportExcel(String fileName, List<Map<String, Object>> columns, List<Map<String, Object>> datas, HttpServletResponse response) throws IOException{
		String serverPath = ctx.getResource("/").getFile().getPath();
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<String> columnDataIndex = new ArrayList<String>();
		List<Object> columnHeader = new ArrayList<Object>();
		for(Map<String, Object> column : columns){
			if(column.get("columns") != null){
				List<Map<String, Object>> list1 = (List) column.get("columns");
				for(Map<String, Object> column1 : list1){
					List<Map<String, Object>> list2 = (List) column1.get("columns");
					for(Map<String, Object> column2 : list2){
						columnHeader.add(column.get("text").toString() + "-" + column1.get("text").toString() + "-" + column2.get("text").toString() );
						columnDataIndex.add(column2.get("dataIndex").toString());
					}
				}
			}else{
				columnHeader.add(column.get("text").toString());
				columnDataIndex.add(column.get("dataIndex").toString());
			}
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
//			columnHeader.add(column.get("text").toString());
//			columnDataIndex.add(column.get("dataIndex").toString());
			
			if(column.get("columns") != null){
				List<Map<String, Object>> list1 = (List) column.get("columns");
				for(Map<String, Object> column1 : list1){
					List<Map<String, Object>> list2 = (List) column1.get("columns");
					for(Map<String, Object> column2 : list2){
						columnHeader.add(column.get("text").toString() + "-" + column1.get("text").toString() + "-" + column2.get("text").toString() );
						columnDataIndex.add(column2.get("dataIndex").toString());
					}
				}
			}else{
				columnHeader.add(column.get("text").toString());
				columnDataIndex.add(column.get("dataIndex").toString());
			}
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
