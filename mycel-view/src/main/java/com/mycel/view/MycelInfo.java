package com.mycel.view;

import javax.swing.table.AbstractTableModel;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MycelInfo extends AbstractTableModel {
  Vector field, record;
  java.sql.PreparedStatement ps = null;
  java.sql.Connection ct = null;
  ResultSet rs = null;

  public MycelInfo(String ss) {
    this.sqlname(ss);
  }

  public MycelInfo() {
    this.sqlname("select * from member");
  }

  @Override
  public int getRowCount() {
    return this.record.size();
  }

  @Override
  public int getColumnCount() {
    return this.field.size();
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return ((Vector) this.record.get(rowIndex)).get(columnIndex);
  }

  public String getColumnName(int e) {
    return (String) this.field.get(e);
  }

  private void sqlname(String sql) {
    field = new Vector();
    field.add("Id");
    field.add("Name");
    field.add("Position");
    field.add("Join_time");
    field.add("Salary");

    record = new Vector();

    try {
      Class.forName("com.mysql.jdbc.Driver");
      try {
        ct = DriverManager.getConnection("jdbc:mysql://localhost:3306/mycel", "root", "331602");
        ps = ct.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
          Vector row = new Vector();
          row.add(rs.getInt(1));
          row.add(rs.getString(2));
          row.add(rs.getString(3));
          row.add(rs.getDate(4));
          row.add(rs.getFloat(5));
          record.add(row);
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (rs != null) {
        try {
          rs.close();
          if (ps != null) {
            ps.close();
          }
          if (ct != null) {
            ct.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
  }
}
