package com.mycel.Excel;

import com.mycel.Excel.impl.IEntity;
import com.mycel.Excel.impl.ITableDao;
import com.mycel.Excel.util.DBUtil;
import com.mycel.common.Configuration;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemDao implements ITableDao{
  Connection con=null;
  PreparedStatement pstmt=null;
  ResultSet res=null;
  
  public List<MemEntity> findAll() {
    List<MemEntity> members = new ArrayList<MemEntity>();
    String sql = "select * from member";
    con= DBUtil.getConnection();
    try {
      pstmt = con.prepareStatement(sql);
      res = pstmt.executeQuery();
      while(res.next()){
        MemEntity member = new MemEntity(res.getInt(1), res.getString(2),
                                         res.getString(3), res.getDate(4),
                                         res.getInt(5));
        members.add(member);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }finally{
      DBUtil.closeAll(res, pstmt, con);
    }
    return members;
  }

  public IEntity findById(String id) {
    return null;
  }

  public void update(IEntity entity) {

  }

  public void add(IEntity entity) {

  }

  public void delete(String id) {

  }

  public IEntity findByIdAndPassword(String id, String password) {
    return null;
  }

  public void updatePassword(String id, String password) {

  }

  public IEntity findByTablename(String tablename) {
    return null;
  }
}
