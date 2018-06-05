package main.java.services;

import main.java.LRUAlgoCacheImpl;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.memory.CacheUnit;

public class CacheUnitServices<T> {

    private CacheUnit cacheUnit;
    private  LRUAlgoCacheImpl<T,DataModel<T>> lru;
    private int capacity;
    public CacheUnitServices()
    {
        lru=new LRUAlgoCacheImpl<>(30);
        DaoFileImpl<T> daoFile = new DaoFileImpl<>("src\\main\\resources\\GetDataModeles.txt");

        this.cacheUnit=new CacheUnit(lru,daoFile);


        for (int i=0;i<4;i++){
            Integer integer=i;
            daoFile.save(new DataModel<>(Long.valueOf(i),integer));
        }
    }


    public boolean	delete(DataModel<T>[] dataModels)
    {
        DataModel<T>[] returndataModels=null;
        Long[] ids=new Long[dataModels.length];

        for(int i=0;i<dataModels.length;i++)
        {
            ids[i]=dataModels[i].getDataModelId();
        }
        returndataModels=cacheUnit.getDataModels(ids);

        for(DataModel<T> model:returndataModels)
        {
            model.setContent(null);
        }

        return true;
    }

    public  DataModel<T>[]	get(DataModel<T>[] dataModels){

        DataModel<T>[] returndataModels=null;
        Long[] ids=new Long[dataModels.length];

        for(int i=0;i<dataModels.length;i++)
        {
            ids[i]=dataModels[i].getDataModelId();
        }
        returndataModels=cacheUnit.getDataModels(ids);

        return returndataModels;
    }



    public     boolean	update(DataModel<T>[] dataModels){
        DataModel<T>[] returndataModels=null;
        Long[] ids=new Long[dataModels.length];

        for(int i=0;i<dataModels.length;i++){
            ids[i]=dataModels[i].getDataModelId();
        }

        returndataModels=cacheUnit.getDataModels(ids);


        for(int i=0;i<dataModels.length;i++)
        {
            for(int j=0;j<returndataModels.length;j++)
            {
                if(dataModels[i].getDataModelId().equals(returndataModels[i].getDataModelId()))
                {
                    returndataModels[j].setContent((T) dataModels[i].getContent());
                    j=returndataModels.length+1;
                }
            }
        }

        for(DataModel model:dataModels)
        {
            cacheUnit.updateFile(model);
        }
        return true;
    }

    public String getLru() {
        return "lru";
    }

    public int getCapacity() {
        return capacity;
    }

    public String statiticFun()
    {
        System.out.println("statistic function");
        int sizeofDM=cacheUnit.getSizeofDm();
        int getswap=cacheUnit.getswap();
        int capacity=getCapacity();
        String algoCache=getLru();
        String temp;
        temp="Capacity: "+ capacity + ".\n" +"Algorithm: " + algoCache + ".\n" + "Total number of request:" +".\n" +"Total number of DataModels:" + sizeofDM +".\n "+ "Total number of DataModels swaps: " + getswap+ ".\n" ;

        System.out.print("Check 1: "+temp);
        return temp;
    }
}
