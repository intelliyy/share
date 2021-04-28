package com.yyshare.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**
     * 获取单元格数据
     * @param cell
     * @param dateFormat
     * @return
     */
    public static String getCellData(Cell cell, String dateFormat) {
        if (cell == null) {
            return null;
        }
        String value = null;
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                value = DateUtil.format(cell.getDateCellValue(), dateFormat);
            } else {
                value = String.valueOf((long)cell.getNumericCellValue());
            }
        } else {
            value = cell.getStringCellValue();
        }
        return value.trim();
    }

    public static void addData(Row row, List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(data.get(i));
        }
    }

    public static List<String>[] getData(Sheet sheet, String dateFormat, String... keys) {
        List<String>[] datas = new List[keys.length];
        int[] indexs = new int[keys.length];
        for (int i = 0; i < datas.length; i++) {
            datas[i] = new ArrayList<>();
            indexs[i] = getIndex(keys[i], sheet);
        }
        int c = sheet.getLastRowNum();
        for (int i = 1; i <= c; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < indexs.length; j++) {
                if (indexs[j] == -1) {
                    datas[j].add(null);
                } else {
                    datas[j].add(ExcelUtil.getCellData(row.getCell(indexs[j]), dateFormat));
                }
            }
        }
        return datas;
    }

    public static List<String>[] getData(String url, String dateFormat, String... keys) throws IOException, IOException {
        Workbook book;
        File file = new File(url);
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            if (file.getName().endsWith("x")) {
                book = new XSSFWorkbook(input);
            } else {
                book = new HSSFWorkbook(input);
            }
            Sheet sheet = book.getSheetAt(0);
            return getData(sheet, dateFormat, keys);
        } catch (Exception e) {
            throw e;
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * 获取excel工作簿
     * @param input
     * @param name
     * @return
     * @throws IOException
     */
    public static Workbook createWorkBook(FileInputStream input, String name) throws IOException {
        if (name.endsWith("x")) {
            return new XSSFWorkbook(input);
        } else {
            return new HSSFWorkbook(input);
        }
    }

    /**
     * 创建新的excel工作簿
     * @return
     * @throws IOException
     */
    public static Workbook createNewWorkBook() {
        return new XSSFWorkbook();
    }

    private static int getIndex(String key, Sheet sheet) {
        Row row = sheet.getRow(0);
        for (int i = 0;; i++) {
            Cell cell = row.getCell(i);
            if (cell == null || StrUtil.isBlank(cell.getStringCellValue())) {
                return -1;
            }
            if (key.equals(cell.getStringCellValue())) {
                return i;
            }
        }
    }
}
