package main.java.services;

import main.java.com.hit.dm.DataModel;

public class CacheUnitController<T> {

    private CacheUnitServices<T> cacheUnitServices=null;

    public CacheUnitController(){
       cacheUnitServices=new CacheUnitServices<T>();
    }

    public boolean	delete(DataModel<T>[] dataModels){
        return cacheUnitServices.delete(dataModels);
    }

    public  DataModel<T>[]	get(DataModel<T>[] dataModels){
         return  cacheUnitServices.get(dataModels);
    }

    public     boolean	update(DataModel<T>[] dataModels){
         return cacheUnitServices.update(dataModels);
    }
}
