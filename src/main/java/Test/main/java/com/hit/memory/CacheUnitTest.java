package main.java.com.hit.memory;

import com.hit.algorithm.LRUAlgoCacheImpl;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dm.DataModel;
import org.junit.Assert;
import org.junit.Test;

public class CacheUnitTest extends java.lang.Object {

    private String filename = "C:\\Users\\moshe\\IdeaProjects\\FinalProject\\CacheUnit-Project1\\CacheUnit-Project\\src\\main\\resources\\test_GetDataModeles.txt";

    @Test
    public void getDataModelsTest() {

        DaoFileImpl<Integer> daofile = new DaoFileImpl<Integer>(filename);
        LRUAlgoCacheImpl<Long, DataModel<Integer>> lru = new LRUAlgoCacheImpl<>(3);
        CacheUnit<Integer> cacheUnit = new CacheUnit<>(lru, daofile);
        Long[] ids = {Long.valueOf(0), Long.valueOf(1), Long.valueOf(2)};

        DataModel<String> dm00 = new DataModel<>((long) 0, "0");
        DataModel<String> dm01 = new DataModel<>((long) 1, "1");
        DataModel<String> dm02 = new DataModel<>((long) 2, "2");

        int j = 0;
        for (int i = 0; i < 3; i++) {
            DataModel<Integer> dm = new DataModel<>((long) i, ids[j++]);
            lru.putElement((long) i, dm);
            daofile.save(dm);
        }
        Assert.assertEquals(dm00.getDataModelId(), daofile.find((long) 0).getDataModelId());
        Assert.assertEquals(dm01.getDataModelId(), daofile.find((long) 1).getDataModelId());
        Assert.assertEquals(dm02.getDataModelId(), daofile.find((long) 2).getDataModelId());
    }
}
