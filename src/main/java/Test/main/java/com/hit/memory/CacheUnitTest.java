package main.java.com.hit.memory;

import main.java.LRUAlgoCacheImpl;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dm.DataModel;
import org.junit.Test;

public class CacheUnitTest extends java.lang.Object{

    private String filename="C:\\Users\\moshe\\IdeaProjects\\Cache\\testfile_getdatamodel.obj";

    public CacheUnitTest( ){ }

    @Test
        public void getDataModelsTest() {
        DaoFileImpl<String> daofile=new DaoFileImpl<String>("test_GetDataModeles.txt");
        LRUAlgoCacheImpl<Long,DataModel<String>> lru=new LRUAlgoCacheImpl<>(3);
        CacheUnit<String> cacheUnit =new CacheUnit<>(lru,daofile);

        String[] str= new String[]{"a", "b", "c"};

        for(int i=0;i<3;i++)
        {
            lru.putElement(Long.valueOf(i),new DataModel<>(Long.valueOf(i),str[i]));
        }

        for(int i=0;i<3;i++)
        {
            daofile.save(new DataModel<>(Long.valueOf(i),str[i]));
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
