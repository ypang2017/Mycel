package com.mycel.view;

public class TableFactory {
  private static String tableName;
  private static MycelTable mycelTable;
  private static TableFactory tableFactory;

  public TableFactory(String tableName) {
    this.tableName = tableName;
    mycelTable = new MycelTable(tableName);
  }

  public static TableFactory singleton() {
    if (tableFactory == null) {
      return new TableFactory(tableName);
    }
    return tableFactory;
  }

  public static void main(String[] args) {
    String tableName = "member";
    TableFactory factory = new TableFactory(tableName);
  }
}
