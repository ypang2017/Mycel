package com.mycel.Excel;

import com.mycel.Excel.impl.ITableDao;
import com.mycel.Excel.util.DBUtil;


import java.sql.*;

public class MemDao implements ITableDao{
  Connection con=null;
  PreparedStatement pstmt=null;
  ResultSet res=null;
  
  public ResultSet search(String sql, String[] str) {
    con = DBUtil.getConnection();
    try {
      pstmt = con.prepareStatement(sql);
      if (str != null) {
        for (int i =0; i < str.length; i++) {
          pstmt.setString(i+1, str[i]);
        }
      }
      res = pstmt.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
//    }finally{
//      DBUtil.closeAll(res, pstmt, con);
    }
    return res;
  }

  public int addU(String sql, String[] str) {
    con = DBUtil.getConnection();
    int a = 0;
    try {
      PreparedStatement pst = con.prepareStatement(sql);
      if (str != null) {
        for (int i = 0; i < str.length; i++) {
          pst.setString(i + 1, str[i]);
        }
      }
      a = pst.executeUpdate();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return a;
  }
}
