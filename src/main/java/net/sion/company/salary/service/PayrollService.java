package net.sion.company.salary.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sion.company.salary.domain.SalaryItem;
import net.sion.company.salary.sessionrepository.PayrollRepository;
import net.sion.company.salary.sessionrepository.SalaryItemRepository;
import net.sion.core.filedump.util.AsposeUtil;
import net.sion.util.file.FileUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PayrollService {
	@Autowired ApplicationContext ctx;
	@Autowired AsposeUtil asposeUtil;
	@Autowired PayrollRepository payrollRepository;
	@Autowired SalaryItemRepository itemRepository;
	
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
		String templateFile = serverPath + "/sion/template/salaryTemplate.xls";
		String newFilePath = serverPath + "/temp/salary/export";
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = format.format(date);
		File fileFordel  = new File(newFilePath);
		if(!fileFordel.exists()){
			fileFordel.mkdirs();
		}
		newFilePath = newFilePath + "/工资表_"+ dateStr + ".xls";
		File eFile = new File(newFilePath);
		if(eFile.exists()){
			boolean boo = eFile.delete();
			System.out.print(boo);
		}
		
//		Map<String, Object> dataSourceMap = new HashMap<String,Object>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("年", "2016");
//		map.put("月", "12");
//		map.put("name", "11111");
//		List<Payroll> list = payrollRepository.findAll();
//		dataSourceMap.put("Map", new HashMapDataTable(map));
//		dataSourceMap.put("list", list);
//		String filePath = AsposeUtil.createExcelByTemplateMap(templateFile, newFilePath, dataSourceMap);
		
		List<SalaryItem> items = itemRepository.findAll();
		Map<String,String> itemsMap = new HashMap<String,String>();
		for (SalaryItem item : items) {
			itemsMap.put(item.getId(), item.getName());
		}
		List<Map<String, String>> dataValuesList = new ArrayList<Map<String, String>>();
		for (Map<String,Object> data : datas) {
			Map<String, String> dataValuesMap = new HashMap();
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				String key = entry.getKey();
				String itemName = itemsMap.get(key);
				if (StringUtils.isNotBlank(itemName)) {
					dataValuesMap.put(itemName, (String)entry.getValue());
				}
			}	
			dataValuesList.add(dataValuesMap);
		}
		
		
		
		Map<String, List<Map<String, String>>> map = new HashMap();
		List<Map<String, String>> excelList = new ArrayList();
		Map<String, String> excel = new HashMap();
		excel.put("年", "2015");
		excel.put("月", "12");
		excel.put("分管劳资领导", "张一");
		excel.put("备注", "aaaaaaaaaaaaaaaaaaa");
		excelList.add(excel);
		map.put("&=excel", excelList);
		
		List<Map<String, String>> gridList = new ArrayList();
		Map<String, String> grid = new HashMap();
		grid.put("工资表序号", "11");
		grid.put("档位", "1");
		grid.put("姓名", "王三");
		grid.put("基本工资", "15000");
		grid.put("绩效工资", "11000");
		grid.put("补发工资", "12222");
		gridList.add(grid);
		
		Map<String, String>  grid2 = new HashMap();
		grid2.put("工资表序号", "22");
		grid2.put("档位", "2");
		grid2.put("姓名", "赵四");
		grid2.put("基本工资", "25000");
		grid2.put("绩效工资", "21000");
		grid2.put("补发工资", "22222");
		gridList.add(grid2);
		
		Map<String, String>  grid3 = new HashMap();
		grid3.put("工资表序号", "33");
		grid3.put("档位", "3");
		grid3.put("姓名", "张一");
		grid3.put("基本工资", "35000");
		grid3.put("绩效工资", "31000");
		grid3.put("补发工资", "32222");
		gridList.add(grid3);
		
		map.put("&=list", dataValuesList);
		String filePath = AsposeUtil.replaceExcel(templateFile, newFilePath, map);
		
//		String serverPath = ctx.getResource("/").getFile().getPath();
//		List<List<Object>> list = new ArrayList<List<Object>>();
//		List<String> columnDataIndex = new ArrayList<String>();
//		List<Object> columnHeader = new ArrayList<Object>();
//		for(Map<String, Object> column : columns){
//			if(column.get("columns") != null){
//				List<Map<String, Object>> list1 = (List) column.get("columns");
//				for(Map<String, Object> column1 : list1){
//					List<Map<String, Object>> list2 = (List) column1.get("columns");
//					for(Map<String, Object> column2 : list2){
//						columnHeader.add(column.get("text").toString() + "-" + column1.get("text").toString() + "-" + column2.get("text").toString() );
//						columnDataIndex.add(column2.get("dataIndex").toString());
//					}
//				}
//			}else{
//				columnHeader.add(column.get("text").toString());
//				columnDataIndex.add(column.get("dataIndex").toString());
//			}
//		}
//		list.add(columnHeader);
//		for(Map<String, Object> data : datas){
//			List<Object> rowList = new ArrayList<Object>();
//			for(String column : columnDataIndex){
//				if(data.get(column) != null){
//					rowList.add(data.get(column).toString());
//				}else{
//					rowList.add("");
//				}
//			}
//			list.add(rowList);
//		}
//		
//		String filePath = asposeUtil.createExcelSimple(list, "Sheet1", serverPath, "xls");
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
