package com.mycel.Excel;

import java.sql.Date;

public class MemEntity {
  private int id;
  private String name;
  private String position;
  private Date joinTime;
  private int salary;

  public MemEntity() {
  }

  public MemEntity(int id, String name, String position, Date joinTime, int salary) {
    this.id = id;
    this.name = name;
    this.position = position;
    this.joinTime = joinTime;
    this.salary = salary;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public Date getJoinTime() {
    return joinTime;
  }

  public void setJoinTime(Date joinTime) {
    this.joinTime = joinTime;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }
}
