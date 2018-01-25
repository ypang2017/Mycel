package com.mycel.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MycelInfoAdd extends JDialog implements ActionListener {
  JLabel label1, label2, label3, label4, label5, label6;
  JLabel falseJLabel;
  JTextField wbk1, wbk2, wbk3, wbk4, wbk5;
  JButton an1, an2;
  JPanel mb1, mb2, mb3, mb4, mb5;

  public MycelInfoAdd(Frame fck, String ckm, Boolean msck) {
    super(fck, ckm, msck);
    //lable
    label1 = new JLabel("   Id（int）     ");
    label2 = new JLabel("   Name（String）     ");
    label3 = new JLabel("   Position（String）    ");
    label4 = new JLabel("   Join time （eg:2017-07-24）   ");
    label5 = new JLabel("   Salary  （float）   ");

    //textField
    wbk1 = new JTextField(5);
    wbk2 = new JTextField(5);
    wbk3 = new JTextField(5);
    wbk4 = new JTextField(5);
    wbk5 = new JTextField(5);
    //button
    an1 = new JButton("add");
    an1.addActionListener(this);
    an1.setActionCommand("add1");
    an2 = new JButton("cancel");
    an2.addActionListener(this);
    an2.setActionCommand("cancel");
    //5 panel
    mb1 = new JPanel();
    mb2 = new JPanel();
    mb3 = new JPanel();
    mb4 = new JPanel();
    mb5 = new JPanel();//Prompt format panel

    //panel 1、2 gridlayout
    mb1.setLayout(new GridLayout(5, 1));//label panel
    mb2.setLayout(new GridLayout(5, 1));//textFiel panel

    mb1.add(label1);
    mb1.add(label2);
    mb1.add(label3);
    mb1.add(label4);
    mb1.add(label5);
    mb2.add(wbk1);
    mb2.add(wbk2);
    mb2.add(wbk3);
    mb2.add(wbk4);
    mb2.add(wbk5);
    mb3.add(an1);
    mb3.add(an2);

    this.add(mb1, BorderLayout.WEST);
    this.add(mb2);
    this.add(mb3, BorderLayout.SOUTH);
    this.add(mb4, BorderLayout.EAST);

    this.setSize(370, 250);
    this.setLocation(400, 280);
    this.setResizable(false);
    //		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("add1")) {
      java.sql.PreparedStatement ps = null;
      java.sql.Connection ct = null;
      ResultSet rs = null;
      Statement sm = null;
      try {
        Class.forName("com.mysql.jdbc.Driver");
        ct = DriverManager.getConnection("jdbc:mysql://localhost:3306/mycel", "root", "331602");
        String ss = ("insert into member values(?,?,?,?,?)");
        ps = ct.prepareStatement(ss);
        ps.setString(1, wbk1.getText());
        ps.setString(2, wbk2.getText());
        ps.setString(3, wbk3.getText());
        ps.setString(4, wbk4.getText());
        ps.setString(5, wbk5.getText());
        ps.executeUpdate();

        this.dispose();
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
      }
    } else if (e.getActionCommand().equals("cancel")) {
      this.dispose();
    }
  }
}