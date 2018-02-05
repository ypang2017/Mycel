package com.mycel.Excel;

import com.mycel.Excel.impl.IEntity;
import com.mycel.Excel.impl.IService;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel to DB Service
 */
public class MemExcelService implements IService{

  /**
   * Find all data in Excel file
   * @return
   */
  public List<MemEntity> findAll() {
    String file = "/home/hadoop/work/mycel/Mycel/mysql.xls";
    List<MemEntity> list=new ArrayList<MemEntity>();
    try {
      Workbook rwb=Workbook.getWorkbook(new File(file));
      Sheet rs = rwb.getSheet(0);
      int clos=rs.getColumns();//get all columns
      int rows=rs.getRows();//get all rows

      System.out.println("cloums:" + clos+" rows:"+rows);
      for (int i = 1; i < rows; i++) {
        for (int j = 0; j < clos; j++) {
          //First is column number,second is row number
          int id = Integer.parseInt(rs.getCell(j++, i).getContents());//The left most left number is also a column, so here is the j++
          String name = rs.getCell(j++, i).getContents();
          String position = rs.getCell(j++, i).getContents();
          Date date = Date.valueOf(rs.getCell(j++, i).getContents());
          int salary = Integer.parseInt(rs.getCell(j++, i).getContents());
          list.add(new MemEntity(id, name, position, date, salary));
        }
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }
  
  public boolean isExist(int id) {
    String sql = "select * from member where id=?";
    try {
      MemDao memDao = new MemDao();
      ResultSet rs= memDao.search(sql, new String[]{String.valueOf(id)});
      if (rs.next()) {
        return true;
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }
}
