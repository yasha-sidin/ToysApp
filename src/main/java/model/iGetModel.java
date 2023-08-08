package model;

import java.util.List;

public interface iGetModel<T> {

    public void addData(T data);

    public List<T> getAllData();

    public T getDataById(int id);

    public void setData(T data);
}
