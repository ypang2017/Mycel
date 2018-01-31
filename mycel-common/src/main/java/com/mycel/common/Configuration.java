package com.mycel.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
  static final String rootPath = "./conf";
  List<String> confPaths = new ArrayList<String>();

  /**
   * get the configuration paths
   *
   * @return
   */
  public List<String> getConfPaths() {
    File file = new File(rootPath);
    File[] tempList = file.listFiles();
    for (int i = 0; i < tempList.length; i++) {
      confPaths.add(tempList[i].toString());
    }
    return confPaths;
  }

  /**
   * get the configuration path
   *
   * @param confName
   * @return
   */
  public String getConfPath(String confName) {
    List<String> pathsList = getConfPaths();
    if (confName.equals("database")) {
      if (pathsList.contains("./conf/database.xml")) {
        return "./conf/database.xml";
      } else {
        return "./conf/database-default.xml";
      }
    } else if (confName.equals("core")) {
      if (pathsList.contains("./conf/core.xml")) {
        return "./conf/core.xml";
      } else {
        return "./conf/core-default.xml";
      }
    }
    return null;
  }
}
