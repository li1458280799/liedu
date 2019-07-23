package com.li.honedu.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelImportUtil {
    public ExcelImportUtil() {

    }

    public static List<Map<String,Object>> getSheet(Sheet sheet) {
        //获取行内容
        Map<Integer,String> headerMap = new HashMap<>();
        Row header = sheet.getRow(0);
        for(int i = 0; i<header.getLastCellNum();i++){
            headerMap.put(i, header.getCell(i).getStringCellValue());
        }
        List<Map<String,Object>> sheetList = Lists.newArrayList();
        //第一行头部去掉，读取内容
        for(int j=1;j<sheet.getLastRowNum()+1;j++) {
            Map<String,Object> rowObject = Maps.newHashMap();
            //获取行内容
            Row row = sheet.getRow(j);
            //获取列内容
            for(int i = 0; i<row.getLastCellNum();i++){
                row.getCell(i).setCellType(CellType.STRING);
                rowObject.put(headerMap.get(i), row.getCell(i).getStringCellValue());
            }
            sheetList.add(rowObject);
        }
        return sheetList;
    }
}
