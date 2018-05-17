package main.java.com.hit.memory;

import main.java.IAlgoCache;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.dm.DataModel;

import java.util.ArrayList;
import java.util.List;

public class CacheUnit<T> extends Object {

    private IAlgoCache<Long, DataModel<T>> cache;                        //used as ram
    private IDao<Long, DataModel<T>> dao;

    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
        this.cache = algo;
        this.dao = dao;
    }
    IAlgoCache<Long, DataModel<T>> getCache(){ 
        return cache;
    }
    
    public DataModel<T>[] getDataModels(Long[] ids) 
    {
        List<DataModel<T>> listOfEntitys = new ArrayList<>();
        DataModel<T> entity;

        ArrayList<Long> arrayIds =new ArrayList<>();
        ArrayList<Long> removeIds =new ArrayList<>();

        for(Long l:ids)
        {
            arrayIds.add(l);
        }

        for(int i=0;i<arrayIds.size();i++)
        {
            entity= (DataModel<T>) cache.getElement(arrayIds.get(i));

            if(entity != null)
            {
                removeIds.add(arrayIds.get(i));
                listOfEntitys.add(entity);
            }
        }

        for(Long id:removeIds)
        {
            arrayIds.remove(id);
        }

        for(int i=0;i<arrayIds.size();i++)
        {
            Long id = arrayIds.get(i);
            DataModel<T> tempData;
            tempData = (DataModel<T>) dao.find(id);

            if (tempData != null) {
                listOfEntitys.add(tempData);
                removeIds.add(id);
            }

            DataModel resultObject = null;

            if (tempData != null) {
                resultObject = cache.putElement(tempData.getDataModelId(), tempData);
            }
            if (resultObject != null) {
                dao.save(resultObject);
            }

        }

        DataModel<T>[] arrOfDataModels = new DataModel[listOfEntitys.size()];
        for(int i=0;i<listOfEntitys.size();i++)
        {
            arrOfDataModels[i]= listOfEntitys.get(i);
        }
        return arrOfDataModels;
}

    public void updateFile(DataModel model)
    {
        dao.save (model);
    }


}
