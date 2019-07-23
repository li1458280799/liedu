package com.li.honedu.util;

import org.apache.poi.hssf.usermodel.*;

import java.util.List;
import java.util.Map;

public class ExcelExportUtil {
    public ExcelExportUtil() {
    }

    public static HSSFWorkbook exportToExcel(List<Map<String, Object>> sheetsList) throws Exception {
        HSSFWorkbook workBook = new HSSFWorkbook();
        int sheetnum = sheetsList.size();

        for(int m = 0; m < sheetnum; ++m) {
            Map<String, Object> sheetMap = (Map)sheetsList.get(m);
            HSSFSheet sheet = workBook.createSheet();
            workBook.setSheetName(m, (String)sheetMap.get("sheetName"));
            HSSFHeader header = sheet.getHeader();
            header.setCenter("sheet");
            HSSFRow headerRow = sheet.createRow(0);
            List<String> heads = (List)sheetMap.get("heads");
            if (heads != null) {
                HSSFCellStyle headstyle = workBook.createCellStyle();
                HSSFFont headfont = workBook.createFont();
                headfont.setColor((short)8);
                headfont.setFontHeight((short)700);
                headstyle.setFont(headfont);

                for(int i = 0; i < heads.size(); ++i) {
                    HSSFCell headerCell = headerRow.createCell(i);
                    headerCell.setCellStyle(headstyle);
                    headerCell.setCellValue((String)heads.get(i));
                    headerCell.setCellStyle(headstyle);
                }
            }

            List<List<Object>> datas = (List)sheetMap.get("datas");
            if (datas != null) {
                for(int i = 0; i < datas.size(); ++i) {
                    List<Object> list2 = (List)datas.get(i);
                    HSSFRow row = sheet.createRow(i + 1);

                    for(int q = 0; q < list2.size(); ++q) {
                        HSSFCell cell = row.createCell(q);
                        Object val = list2.get(q);
                        if (val != null) {
                            if (val instanceof String) {
                                cell.setCellValue((String)val);
                            } else {
                                cell.setCellValue(String.valueOf(val));
                            }
                        }
                    }
                }
            }
        }

        return workBook;
    }
}
