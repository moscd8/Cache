package main.java.com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.dm.DataModel;

public class CacheUnit<T> extends Object {
    private IAlgoCache<Long, DataModel<T>> cache;
    private IDao<Long, DataModel<T>> dao;
    private static int numOfSwap = 0;
    private static int dmsize = 0;

    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
        this.cache = algo;
        this.dao = dao;
    }

    IAlgoCache<Long, DataModel<T>> getCache() {
        return cache;
    }

    public DataModel<T>[] getDataModels(Long[] ids) {
        DataModel<T>[] dataModels = new DataModel[ids.length];
        int i = 0;

        for (Long id : ids) {
            DataModel<T> dataModelID = cache.getElement(id);
            if (dataModelID == null)
                dataModelID = dao.find(id);
            if (dataModelID != null) {
                numOfSwap++;
            }
            cache.putElement(id, dataModelID);
            dataModels[i++] = dataModelID;
        }
        dmsize += dataModels.length;
        return dataModels;
    }

    public static int getNumOfSwap() {
        return numOfSwap;
    }

    public void updateFile(DataModel model) {
        dao.save(model);
    }

    public int getswap() {
        return numOfSwap;
    }

    public static int getSizeofDm() {
        return dmsize;
    }

}
