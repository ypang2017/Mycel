package com.mycel;

public class DataStoreException extends Exception {
  
  public DataStoreException(String errorMsg) {
    super(errorMsg);
  }

  public DataStoreException(String errorMsg, Throwable throwable) {
    super(errorMsg, throwable);
  }

  public DataStoreException(Throwable throwable) {
    super(throwable);
  }
}
