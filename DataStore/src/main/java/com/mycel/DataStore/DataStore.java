package com.mycel.DataStore;

import com.mycel.DataStoreException;
import com.mycel.Utils.TableUtils;
import com.mysql.jdbc.Connection;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.apache.log4j.receivers.db.DBHelper.closeConnection;


/**
 * Different Dao for upper functions;
 */

public class DataStore {
  static final Logger LOG = Logger.getLogger(DataStore.class);

  public void dropAllTables() throws DataStoreException, SQLException {
    Connection conn = (Connection) DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/mycel", "root", "331602");
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
  
  public void formatDataBase() throws SQLException, DataStoreException {
    dropAllTables();
    initializeDataBase();
  }

  public synchronized void initializeDataBase() throws DataStoreException, SQLException {
    java.sql.Connection conn = (Connection) DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/mycel", "root", "331602");
    try {
      TableUtils.initDBTables(conn);
    } catch (Exception e) {
      throw new DataStoreException(e);
    } finally {
      closeConnection(conn);
    }
  }

  public synchronized void checkTables() throws DataStoreException, SQLException {
    java.sql.Connection conn = (Connection) DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/mycel", "root", "331602");
    try {
      if (!TableUtils.isTableSetExist(conn)) {
        LOG.info("At least one table required by SSM is missing. "
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
