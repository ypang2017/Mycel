package com.mycel.Utils;

import com.mycel.DataStoreException;
import com.mysql.jdbc.NonRegisteringDriver;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

public class TableUtils {
  public static final String MYSQL_URL_PREFIX = "jdbc:mysql:";
  public static final String[] DB_NAME_NOT_ALLOWED =
    new String[]{
      "mysql",
      "sys",
      "information_schema",
      "INFORMATION_SCHEMA",
      "performance_schema",
      "PERFORMANCE_SCHEMA"
    };
  public static final String DB_NAME = "mycel";
  static final Logger LOG = Logger.getLogger(TableUtils.class);

  public static Connection createConnection(String url,
                                            String userName,
                                            String password) throws ClassNotFoundException, SQLException {
    if (url.startsWith(MYSQL_URL_PREFIX)) {
      Class.forName("com.mysql.jdbc.Driver");
    }
    Connection conn = DriverManager.getConnection(url, userName, password);
    return conn;
  }

  public static Connection createConnection(String driver, String url,
                                            String userName,
                                            String password) throws ClassNotFoundException, SQLException {
    Class.forName(driver);
    Connection conn = DriverManager.getConnection(url, userName, password);
    return conn;
  }

  public static boolean isTableSetExist(Connection conn) throws DataStoreException {
    String tableSet[] = new String[]{
      "table_set",
      "user_group"
    };
    try {
      String url = conn.getMetaData().getURL();
      if (url.startsWith(TableUtils.MYSQL_URL_PREFIX)) {
        String dbName = getMysqlDBName(url);
        for (String table : tableSet) {
          String query = String.format("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES "
            + "WHERE TABLE_SCHEMA='%s' and TABLE_NAME='%s'", dbName, table);
          if (isEmptyResultSet(conn, query)) {
            return false;
          }
        }
        return true;
      } else {
        throw new DataStoreException("The jdbc url is not valid for mycel use.");
      }
    } catch (Exception e) {
      throw new DataStoreException(e);
    }
  }

  public static String getMysqlDBName(String url) throws SQLException {
    NonRegisteringDriver nonRegisteringDriver = new NonRegisteringDriver();
    Properties properties = nonRegisteringDriver.parseURL(url, null);
    return properties.getProperty(nonRegisteringDriver.DBNAME_PROPERTY_KEY);
  }

  public static boolean isEmptyResultSet(Connection conn, String sql)
    throws DataStoreException {
    try {
      Statement s = conn.createStatement();
      ResultSet rs = s.executeQuery(sql);
      return !rs.next();
    } catch (Exception e) {
      throw new DataStoreException(e);
    }
  }
}
