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
import net.sion.core.admin.domain.User;
import net.sion.core.filedump.util.AsposeUtil;
import net.sion.util.file.FileUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SalaryExportService {
	@Autowired ApplicationContext ctx;
	@Autowired AsposeUtil asposeUtil;
	@Autowired PayrollRepository payrollRepository;
	@Autowired SalaryItemRepository itemRepository;
	
	public  String exportExcel(String fileName, List<Map<String, Object>> columns, List<Map<String, Object>> datas, HttpServletResponse response, User user, String note) throws IOException{
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
		}

		List<SalaryItem> items = itemRepository.findAll();
		Map<String,String> itemsMap = new HashMap<String,String>();
		for (SalaryItem item : items) {
			itemsMap.put(item.getId(), item.getName());
		}
		List<Map<String, Object>> dataValuesList = new ArrayList<Map<String, Object>>();
		int i = 0;
		for (Map<String,Object> data : datas) {
			Map<String, Object> dataValuesMap = new HashMap();
			dataValuesMap.put("工资表序号", ++i+"");
			dataValuesMap.put("姓名", data.get("name").toString());
			dataValuesMap.put("卡号", data.get("bankAccount").toString());
			if (data.get("level")!=null) {
				dataValuesMap.put("档位", data.get("level").toString());
			}
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				String key = entry.getKey();
				String itemName = itemsMap.get(key);
				if (StringUtils.isNotBlank(itemName)) {
					dataValuesMap.put(itemName, entry.getValue());
				}
			}	
			dataValuesList.add(dataValuesMap);
		}
		Map<String, List<Map<String, Object>>> map = new HashMap();
		List<Map<String, Object>> excelList = new ArrayList();
		Map<String, Object> excel = new HashMap();
		excel.put("title", fileName);
		excel.put("制表", user.getName());
		excel.put("备注", note);
		excelList.add(excel);
		map.put("&=excel", excelList);
	
		
		map.put("&=list", dataValuesList);
		String filePath = AsposeUtil.replaceExcelObject(templateFile, newFilePath, map);
		
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
