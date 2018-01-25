package com.mycel.view;

import org.junit.Assert;
import org.junit.Test;

public class TestTableFactory {
  String tableName = "member";
  
  @Test
  public void createTable(){
    TableFactory factory = new TableFactory(tableName);
  }
}
