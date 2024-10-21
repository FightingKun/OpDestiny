package com.op.des.web;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

public class Main {


    public static void main(String[] args) throws IOException, InvalidFormatException {
        saveChengGu();

    }

    private static void saveBaZiTiYao() throws IOException, InvalidFormatException {
        // 连接数据库
        String url = "jdbc:mysql://127.0.0.1:3306/ops?useSSL=false&autoReconnect=true&failOverReadOnly=false&characterEncoding=utf-8";
        String user = "root";
        String password = "kun123";

        String sql = "insert into bazitiyao (zhi_month, zhi_day, zhi_time, content)" + "values (?,?,?,?)";
        String select = "SELECT * from bazitiyao where zhi_month=? and zhi_day=? and zhi_time=?";
        Workbook workbook = WorkbookFactory.create(new FileInputStream("/Users/kun/Desktop/八字提要整理.xlsx"));
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            for (Row row : sheet) {
                // 获取单元格数据
                String column1 = row.getCell(0).getStringCellValue();
                String column2 = row.getCell(1).getStringCellValue();
                String column3 = row.getCell(2).getStringCellValue();
                String column4 = row.getCell(3).getStringCellValue();
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     // 加载Excel文件

                ) {
                    try (PreparedStatement pstmt1 = conn.prepareStatement(select)) {
                        pstmt1.setString(1, column1);
                        pstmt1.setString(2, column2);
                        pstmt1.setString(3, column3);
                        ResultSet resultSet = pstmt1.executeQuery();
                        boolean next = resultSet.next();
                        if (next) {
                            continue;
                        }

                    }
                    try {
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setString(1, column1);
                            pstmt.setString(2, column2);
                            pstmt.setString(3, column3);
                            pstmt.setString(4, column4);
                            // ... 设置更多的参数
                            pstmt.executeUpdate();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("column1:" + column1 + "column2:" + column2 + "column3:" + column3 + "column4:" + column4);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void saveChengGu() throws IOException, InvalidFormatException {
        // 连接数据库
        String url = "jdbc:mysql://127.0.0.1:3306/ops?useSSL=false&autoReconnect=true&failOverReadOnly=false&characterEncoding=utf-8";
        String user = "root";
        String password = "kun123";

        String sql = "insert into chenggusuanming (sex, gu_weight, ming_detail,comment)" + "values (?,?,?,?)";
        String select = "SELECT * from bazitiyao where zhi_month=? and zhi_day=? and zhi_time=?";
        Workbook workbook = WorkbookFactory.create(new FileInputStream("/Users/kun/Desktop/称骨论命整理.xlsx"));
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            for (Row row : sheet) {
                // 获取单元格数据
                Double numericCellValue = row.getCell(0).getNumericCellValue();
                Integer column1 = numericCellValue.intValue();
                String column2 = row.getCell(1).getStringCellValue();
                String column3 = row.getCell(2).getStringCellValue();
                String column4 = row.getCell(3) == null ? null : row.getCell(3).getStringCellValue();
                try (Connection conn = DriverManager.getConnection(url, user, password)) {
//                    try (PreparedStatement pstmt1 = conn.prepareStatement(select)) {
//                        pstmt1.setString(1, column1);
//                        pstmt1.setString(2, column2);
//                        pstmt1.setString(3, column3);
//                        pstmt1.setString(4, column4);
//                        ResultSet resultSet = pstmt1.executeQuery();
//                        boolean next = resultSet.next();
//                        if(next){
//                            continue;
//                        }
//
//                    }
                    try {
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setInt(1, column1);
                            pstmt.setString(2, column2);
                            pstmt.setString(3, column3);
                            pstmt.setString(4, column4);
                            // ... 设置更多的参数
                            pstmt.executeUpdate();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("column1:" + column1 + "column2:" + column2 + "column3:" + column3 + "column4:" + column4);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
