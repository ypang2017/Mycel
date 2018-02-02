package com.mycel.Excel.impl;

import com.mycel.Excel.MemEntity;

import java.util.List;

public interface IService {
  public List<MemEntity> findAll();

  public IEntity findById(String id);

  public void update(IEntity entity);

  public void add(IEntity entity);

  public void delete(String id);

  public IEntity findByIdAndPassword(String id, String password);

  public void updatePassword(String id, String password);

  IEntity findByTablename(String tablename);
}
