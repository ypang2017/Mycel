package com.mycel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MycelSystem extends JFrame implements ActionListener {
  JPanel mb1, mb2;
  JLabel label;
  JTextField textField;
  JButton an1, an2, an3, an4;
  JTable table;
  JScrollPane rollPane;
  MycelInfo mycelInfo;

  public MycelSystem() {
    //add first panel
    mb1 = new JPanel();
    label = new JLabel("Please enter the name");
    textField = new JTextField(12);
    an1 = new JButton("Search");
    an1.addActionListener(this);
    an1.setActionCommand("search");
    mb1.add(label);
    mb1.add(textField);
    mb1.add(an1);
    //add second panel
    mb2 = new JPanel();
    an2 = new JButton("Add");
    an2.addActionListener(this);
    an2.setActionCommand("add");
    an3 = new JButton("Update");
    an3.addActionListener(this);
    an3.setActionCommand("update");
    an4 = new JButton("Delete");
    an4.addActionListener(this);
    an4.setActionCommand("delete");
    mb2.add(an2);
    mb2.add(an3);
    mb2.add(an4);
    //the user info table
    mycelInfo = new MycelInfo();
    table = new JTable(mycelInfo);
    rollPane = new JScrollPane(table);

    this.add(rollPane);
    this.add(mb1, "North");
    this.add(mb2, "South");

    this.setTitle("Mycel management system");
    this.setSize(500, 400);
    this.setLocation(400, 200);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  public static void main(String[] args) {
    MycelSystem system = new MycelSystem();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //search action listening
    if (e.getActionCommand().equals("search")) {
      String name = this.textField.getText().trim();
      String sql = null;
      if (name.equals("")) {
        sql = "select * from member";
      } else {
        sql = "select * from member where Name ='" + name + "'";
      }
      mycelInfo = new MycelInfo(sql);
      table.setModel(mycelInfo);
    }
    //add preform listening
    else if (e.getActionCommand().equals("add")) {
      MycelInfoAdd add = new MycelInfoAdd(this, "Add user information", true);
      mycelInfo = new MycelInfo();
      table.setModel(mycelInfo);
    }
    //Update action listening
    else if (e.getActionCommand().equals("update")) {
      int i = this.table.getSelectedRow();
      if (i == -1) {
        JOptionPane.showMessageDialog(this, "Choose the line to update ");
        return;
      }
      new MycelInfoUpdte(this, "update user info", true, mycelInfo, i);
      mycelInfo = new MycelInfo();
      table.setModel(mycelInfo);
    }
    //Delete action listening
    else if (e.getActionCommand().equals("delete")) {
      int i = this.table.getSelectedRow();
      if (i == -1) {
        JOptionPane.showMessageDialog(this, "Choose the line to delete");
        return;
      }
      String string = (String) mycelInfo.getValueAt(i, 0).toString();
      PreparedStatement ps = null;
      Connection ct = null;
      ResultSet rs = null;
      Statement sm = null;
      try {
        Class.forName("com.mysql.jdbc.Driver");
        ct = DriverManager.getConnection("jdbc:mysql://localhost:3306/mycel", "root", "331602");
        String ss = ("delete from mycel where Id=?");
        ps = ct.prepareStatement(ss);
        ps.setString(1, string);
//				ps.setInt(1, Integer.parseInt(string));
        ps.executeUpdate();
      } catch (ClassNotFoundException e1) {
        e1.printStackTrace();
      } catch (SQLException e1) {
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
          } catch (SQLException e1) {
            e1.printStackTrace();
          }
        }
        mycelInfo = new MycelInfo();
        table.setModel(mycelInfo);
      }
    }
  }
}