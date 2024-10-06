package com.op.des.web;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main {


    public static void main(String[] args) throws IOException, InvalidFormatException {
        // 连接数据库
        String url = "jdbc:mysql://127.0.0.1:3306/ops?useSSL=false&autoReconnect=true&failOverReadOnly=false&characterEncoding=utf-8";
        String user = "root";
        String password = "kun123";

        String sql = "insert into bazitiyao (zhi_month, zhi_day, zhi_time, content)" + "values (?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             // 加载Excel文件
             Workbook workbook = WorkbookFactory.create(new FileInputStream("/Users/kun/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/19bfc2eef4fec8e515f81a3f5826eec4/Message/MessageTemp/90025071edad761b8fc411cf8d22df0d/File/八字提要整理.xlsx"));) {
            Sheet sheet = workbook.getSheetAt(0);
            // 遍历行
            for (Row row : sheet) {
                // 获取单元格数据
                String column1 = row.getCell(0).getStringCellValue();
                String column2 = row.getCell(1).getStringCellValue();
                String column3 = row.getCell(2).getStringCellValue();
                String column4 = row.getCell(3).getStringCellValue();
                // ... 获取更多列的数据

                // 插入数据库
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
