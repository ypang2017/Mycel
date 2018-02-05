package com.mycel.Excel;

import com.mycel.Excel.impl.IEntity;
import com.mycel.Excel.impl.IService;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

/**
 * DB to Excel Service
 */
public class MemDBService implements IService {
  MemDao memDao = new MemDao();

  /**
   * query all data in member table
   *
   * @return
   */
  public List<MemEntity> searchAll() {
    String sql = "select * from member";
    List<MemEntity> list = new ArrayList<MemEntity>();
    
    ResultSet res = memDao.search(sql, null);
    try {
      while (res.next()) {
        int id = res.getInt("id");
        String name = res.getString("name");
        String position = res.getString("position");
        Date date = res.getDate("join_time");
        int salary = res.getInt("salary");
        list.add(new MemEntity(id, name, position, date, salary));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}
