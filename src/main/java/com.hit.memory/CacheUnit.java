package main.java.com.hit.memory;

import main.java.IAlgoCache;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.dm.DataModel;

public class CacheUnit<T> extends Object {

    private IAlgoCache<Long, DataModel<T>> cache;                        //used as ram
    private IDao<Long, DataModel<T>> dao;
    private static  int numOfSwap=0;
    private int dmsize;

    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
        this.cache = algo;
        this.dao = dao;
    }
    IAlgoCache<Long, DataModel<T>> getCache(){ 
        return cache;
    }
    
    public DataModel<T>[] getDataModels(Long[] ids)
    {
        DataModel<T>[] dataModels = new DataModel[ids.length];
        int i = 0;

        for (Long id : ids) {
            DataModel<T> dataModelID = cache.getElement(id);
            if (dataModelID == null)
                dataModelID = dao.find(id);
            if (dataModelID != null) {
                //if(cache=="full")
                numOfSwap++;
            }
            cache.putElement(id, dataModelID);
            dataModels[i++] = dataModelID;
        }
    dmsize=dataModels.length;
        return dataModels;
    }

    public static int getNumOfSwap() {
        return numOfSwap;
    }
    /*
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
        */

    public void updateFile(DataModel model)
    {
        dao.save (model);
    }

    public int getswap(){
    return  numOfSwap;
        //    int dmsize=this.dmsize;
    }
    public int getSizeofDm(){
        return  dmsize;
    }



    }
