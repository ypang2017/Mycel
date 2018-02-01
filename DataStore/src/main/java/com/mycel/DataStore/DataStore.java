package com.mycel.DataStore;

import com.mycel.DataStore.Utils.TableUtils;
import com.mycel.common.Configuration;
import com.mysql.jdbc.Connection;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.apache.log4j.receivers.db.DBHelper.closeConnection;


/**
 * Different Dao for upper functions;
 */

public class DataStore {
  static final Logger LOG = Logger.getLogger(DataStore.class);
  private Configuration conf;
  private String url;
  private String username;
  private String password;
  private String driverClass;

  public DataStore() {
    this.conf = new Configuration();
    this.url = conf.getDatabaseConf("url");
    this.username = conf.getDatabaseConf("username");
    this.password = conf.getDatabaseConf("password");
    this.driverClass = conf.getDatabaseConf("driverClass");
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDriverClass() {
    return driverClass;
  }

  public void setDriverClass(String driverClass) {
    this.driverClass = driverClass;
  }

  /**
   * Drop all tables
   * @throws DataStoreException
   * @throws SQLException
   */
  public void dropAllTables() throws DataStoreException, SQLException {
    Connection conn = (Connection) DriverManager.getConnection(url, username, password);
    try {
      String url = conn.getMetaData().getURL();
      if (url.startsWith(TableUtils.MYSQL_URL_PREFIX)) {
        TableUtils.dropAllTables(conn, url);
      } else {
        throw new DataStoreException("Unsupported database");
      }
    } catch (Exception e) {
      throw new DataStoreException(e);
    } finally {
      closeConnection(conn);
    }
  }

  /**
   * Format database by drop all tables and init database
   * @throws SQLException
   * @throws DataStoreException
   */
  public void formatDataBase() throws SQLException, DataStoreException {
    dropAllTables();
    initializeDataBase();
  }

  public synchronized void initializeDataBase() throws DataStoreException, SQLException {
    java.sql.Connection conn = (Connection) DriverManager.getConnection(url, username, password);
    try {
      TableUtils.initDBTables(conn);
    } catch (Exception e) {
      throw new DataStoreException(e);
    } finally {
      closeConnection(conn);
    }
  }

  /**
   * Check tables
   * @throws DataStoreException
   * @throws SQLException
   */
  public synchronized void checkTables() throws DataStoreException, SQLException {
    java.sql.Connection conn = (Connection) DriverManager.getConnection(url, username, password);
    try {
      if (!TableUtils.isTableSetExist(conn)) {
        LOG.info("At least one table required by mycel is missing. "
          + "The configured database will be formatted.");
        formatDataBase();
      }
    } catch (Exception e) {
      throw new DataStoreException(e);
    } finally {
      closeConnection(conn);
    }
  }
}
