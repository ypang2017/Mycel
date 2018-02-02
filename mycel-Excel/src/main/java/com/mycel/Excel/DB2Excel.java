package com.mycel.Excel;

import com.mycel.Excel.impl.IEntity;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.List;

import static jxl.Workbook.createWorkbook;

public class DB2Excel {
  public static void main(String[] args) {
    MemService memService = new MemService();
    try {
      WritableWorkbook wwb = null;

      // Create a Excel workbook
      String fileName = "/home/hadoop/work/mycel/Mycel/mysql.xls";
      File file = new File(fileName);
      if (!file.exists()) {
        file.createNewFile();
      }
      // Create Workbook with fileName
      wwb = Workbook.createWorkbook(file);
      
      // Create a work sheet
      WritableSheet ws = wwb.createSheet("Test Shee 1", 0);

      // Query all data from the database
      List<MemEntity> list = (List<MemEntity>) memService.findAll();
      // Use the defaut number "0" as the first column in Excel
      Label labelId = new Label(0, 0, "编号(id)");//
      Label labelName = new Label(1, 0, "姓名(name)");
      Label labelPostion = new Label(2, 0, "位置(position)");
      Label labelJoinTime = new Label(3, 0, "时间(date)");
      Label labelSalary = new Label(4, 0, "薪水(num)");

      ws.addCell(labelId);
      ws.addCell(labelName);
      ws.addCell(labelPostion);
      ws.addCell(labelJoinTime);
      ws.addCell(labelSalary);
      
      for (int i = 0; i < list.size(); i++) {

        Label labelId_i = new Label(0, i + 1, list.get(i).getId() + "");
        Label labelName_i = new Label(1, i + 1, list.get(i).getName());
        Label labelPostion_i = new Label(2, i + 1, list.get(i).getPosition());
        Label labelJoinTime_i = new Label(3, i + 1, list.get(i).getJoinTime() + "");
        Label labelSalary_i = new Label(4, i + 1, list.get(i).getSalary() + "");
        ws.addCell(labelId_i);
        ws.addCell(labelName_i);
        ws.addCell(labelPostion_i);
        ws.addCell(labelJoinTime_i);
        ws.addCell(labelSalary_i);
      }

      // Write to Excel 
      wwb.write();
      // Close Excel object
      wwb.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
