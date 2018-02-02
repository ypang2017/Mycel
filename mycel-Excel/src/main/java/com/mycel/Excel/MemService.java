package com.mycel.Excel;

import com.mycel.Excel.impl.IEntity;
import com.mycel.Excel.impl.IService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemService implements IService {

  MemDao memDao = new MemDao();
  
  /**
   * query all data in member table
   *
   * @return
   */
  public List<MemEntity> findAll() {
    List<MemEntity> list = new ArrayList<MemEntity>();
    List memList = memDao.findAll();
    Iterator iterable = memList.iterator();

    while (iterable.hasNext()) {
      list.add((MemEntity) iterable.next());
    }
    return list;
  }

  public IEntity findById(String id) {
    return null;
  }

  public void update(IEntity entity) {

  }

  public void add(IEntity entity) {

  }

  public void delete(String id) {

  }

  public IEntity findByIdAndPassword(String id, String password) {
    return null;
  }

  public void updatePassword(String id, String password) {

  }

  public IEntity findByTablename(String tablename) {
    return null;
  }
}
