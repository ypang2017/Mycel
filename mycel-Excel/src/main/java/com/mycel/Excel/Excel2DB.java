package com.mycel.Excel;

import com.mysql.jdbc.StringUtils;

import java.util.List;

public class Excel2DB {
  public static void main(String[] args) {
    MemExcelService memExcelService = new MemExcelService();
    
    //Get all data in Excel 
    List<MemEntity> listExcel = memExcelService.findAll();
    MemDao memDao = new MemDao();

    for (MemEntity memEntity : listExcel) {
      int id = memEntity.getId();
      if (!memExcelService.isExist(id)) {
        //If not exist,add
        String sql="insert into member (name,position,join_time,salary) values(?,?,?,?)";
        String[] str=new String[]{memEntity.getName(), memEntity.getPosition(),
                                  memEntity.getJoinTime() + "", memEntity.getSalary() + ""};
        memDao.addU(sql, str);
      }else {
        //If exist,update
        String sql="update member set name=?,position=?,join_time=?,salary=? where id=?";
        String[] str=new String[]{memEntity.getName(), memEntity.getPosition(),
                                  memEntity.getJoinTime() + "", memEntity.getSalary() + "", String.valueOf(id)};
        memDao.addU(sql, str);
      }
    }
  }
}
