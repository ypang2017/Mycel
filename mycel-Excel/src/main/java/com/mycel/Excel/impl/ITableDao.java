package com.mycel.Excel.impl;

import java.sql.ResultSet;

/**
 * The dao interface 
 */
public interface ITableDao {
  /**
   * Query
   * @param sql
   * @param str
   * @return
   */
  public ResultSet search(String sql, String str[]);
  
  /**
   * Add,delete,update
   * @param sql
   * @param str
   * @return
   */
  public int addU(String sql, String[] str);
}
