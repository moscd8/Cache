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


        for(int i=1;i<=3;i++) {
            DataModel<String> dm = new DataModel<String>((long) i, (("a")+i ).toString());
            lru.putElement((long) i,dm);
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
