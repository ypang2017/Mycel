package com.mycel.Excel.util;

import com.mycel.common.Configuration;

import java.sql.*;

/**
 * Database utils
 */
public class DBUtil {

  //Get the database connection
  public static Connection getConnection() {
    Configuration conf = new Configuration();
    Connection con = null;
    try {
      Class.forName(conf.getDatabaseConf("driverClass"));
      con = DriverManager.getConnection(conf.getDatabaseConf("url"),
        conf.getDatabaseConf("username"), "password");
    } catch (ClassNotFoundException e) {
      System.err.println("Load JDBC/ODBC driver failed");
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("Cann't connect the database");
      e.printStackTrace();
    }
    return con;
  }

  //Close all sources
  public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
    try {
      if (rs != null)
        rs.close();
      if (stmt != null)
        stmt.close();
      if (conn != null)
        conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
