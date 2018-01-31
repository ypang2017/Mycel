package com.mycel.DataStore.Utils;

import com.mycel.DataStore.DataStore;
import com.mycel.DataStore.DataStoreException;
import com.mycel.common.Configuration;
import com.mysql.jdbc.NonRegisteringDriver;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

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
      "user_set"
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

  /**
   * init database,drop existed tables and create new tables,
   *
   * @param conn
   * @throws DataStoreException
   */
  public static void initDBTables(Connection conn)
    throws DataStoreException {
    String[] deleExistedTables = new String[]{
      "DROP TABLE IF EXISTS table_set;",
      "DROP TABLE IF EXISTS user_set;",
    };
    String[] creatEmptyTables = new String[]{
      "CREATE TABLE table_set (\n"
        + "  table_id bigint(20) PRIMARY KEY,\n"
        + "  table_name varchar(50) NOT NULL,\n"
        + "  create_time INTEGER NOT NULL,\n"
        + "  modify_time INTEGER NOT NULL,\n"
        + "  create_user varchar(20) NOT NULL,\n"
        + "  modify_user varchar(20) NOT NULL,\n"
        + ");",
      "CREATE TABLE user_set (\n"
        + "  user_id bigint(20) PRIMARY KEY,\n"
        + "  user_name varchar(10) NOT NULL,\n"
        + "  user_permission INTEGER NOT NULL,\n"
        + "  modify_time INTEGER NOT NULL,\n"
        + "  create_user varchar(20) NOT NULL,\n"
        + "  modify_user varchar(20) NOT NULL,\n"
        + ");",
    };
  }

  public static void executeSql(Connection conn, String sql)
    throws DataStoreException {
    try {
      Statement s = conn.createStatement();
      s.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
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

  public static void formatDatabase(Configuration conf) throws DataStoreException, SQLException {
    getDBAdapter(conf).formatDataBase();
  }

  public static void checkTables(Configuration conf) throws DataStoreException, SQLException {
    getDBAdapter(conf).checkTables();
  }

  /**
   * Adapter
   *
   * @param conf configuration from the core-site.xml
   * @return
   * @throws DataStoreException
   */
  public static DataStore getDBAdapter(
    Configuration conf) throws DataStoreException {
    String confPath = conf.getConfPath("database");
    File confFile = new File(confPath);
    if (confFile.exists()) {
      Properties p = new Properties();
      try {
        p.loadFromXML(new FileInputStream(confFile));
        String url = String.format("jdbc:mysql://127.0.0.1:%s", 3306);
        String purl = p.getProperty("url");
        if (purl.startsWith(TableUtils.MYSQL_URL_PREFIX)) {
          //
          String dbName = getMysqlDBName(purl);
          for (String name : DB_NAME_NOT_ALLOWED) {
            if (dbName.equals(name)) {
              throw new DataStoreException(
                String.format(
                  "The database %s in mysql is for DB system use, "
                    + "please appoint other database in " + confPath));
            }
          }
        }
        return new DataStore();
      } catch (Exception e) {
        if (e instanceof InvalidPropertiesFormatException) {
          throw new DataStoreException(
            "Malformat " + confPath + ", please check the file.", e);
        } else {
          throw new DataStoreException(e);
        }
      }
    } else {
      LOG.error("DB connection config file " + confPath
        + " NOT found.");
    }
    return null;
  }

  public static Integer getKey(Map<Integer, String> map, String value) {
    for (Integer key : map.keySet()) {
      if (map.get(key).equals(value)) {
        return key;
      }
    }
    return null;
  }

  public static void dropAllTables(Connection conn, String url) 
    throws DataStoreException {
    try {
      Statement stat = conn.createStatement();
      String dbName = getMysqlDBName(url);
      LOG.info("Drop All tables of Current DBname: " + dbName);
      ResultSet rs = stat.executeQuery("SELECT TABLE_NAME FROM "
        + "INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + dbName + "';");
      List<String> tbList = new ArrayList<String>();
      while (rs.next()) {
        tbList.add(rs.getString(1));
      }
      for (String tb : tbList) {
        LOG.info(tb);
        stat.execute("DROP TABLE IF EXISTS " + tb + ";");
      }
    } catch (Exception e) {
      throw new DataStoreException(e);
    }
  }
}
