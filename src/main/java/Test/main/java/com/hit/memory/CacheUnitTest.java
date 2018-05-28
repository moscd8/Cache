package main.java.com.hit.memory;

import main.java.LRUAlgoCacheImpl;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dm.DataModel;
import org.junit.Test;

public class CacheUnitTest extends java.lang.Object{

    private String filename="test_GetDataModeles.txt";

    public CacheUnitTest( ){ }

    @Test
        public void getDataModelsTest() {
        DaoFileImpl<String> daofile=new DaoFileImpl<String>(filename);
        LRUAlgoCacheImpl<Long,DataModel<String>> lru=new LRUAlgoCacheImpl<>(3);
        CacheUnit<String> cacheUnit =new CacheUnit<>(lru,daofile);

        String[] str= new String[]{"a", "b", "c"};

        int j=0;
        for(int i=1;i<=3;i++) {
            DataModel<String> dm = new DataModel<String>((long) i,str[j++]);
                lru.putElement((long) i, dm);
                daofile.save(dm);
        }

        Long[] ids={Long.valueOf(1), Long.valueOf(2), Long.valueOf(3)};
        DataModel<String>[] dataModels=null;
        dataModels=cacheUnit.getDataModels(ids);
        System.out.println("lenght is : "+dataModels.length);
        for(DataModel dm: dataModels)
        {
            System.out.println(dm);
        }

    }
}
