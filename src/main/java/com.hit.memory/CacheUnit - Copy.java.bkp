package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

import java.util.ArrayList;

public class CacheUnit <T> 
{

	private IDao dao;
	private IAlgoCache algoCache;

	public CacheUnit(com.hit.algorithm.IAlgoCache<java.lang.Long,DataModel<T>> algo,
            IDao<java.io.Serializable,DataModel<T>> dao)
	{
		this.algoCache = algo;
		this.dao = dao;
	}

	public DataModel<T>[] getDataModels(java.lang.Long[] ids) throws java.lang.ClassNotFoundException, java.io.IOException
	{

		ArrayList<DataModel<T>> listOfEntitys = new ArrayList<>();
		DataModel<T> entity;

		ArrayList<Long> arrayIds = new ArrayList<>();

		ArrayList<Long> removeId = new ArrayList<>();

		for(Long l: ids)
		{
			arrayIds.add(l);
		}


		for(int i = 0; i < arrayIds.size(); i++)
		{
			entity = (DataModel<T>) algoCache.getElement(arrayIds.get(i));

			if(entity != null)
			{
				removeId.add(arrayIds.get(i));
				listOfEntitys.add(entity);
			}
		}

		for(Long id: removeId)
		{
			arrayIds.remove(id);
		}


		for (int i = 0; i < arrayIds.size(); i++)
		{
			Long id = arrayIds.get(i);
			DataModel<T> tempData;
			tempData = (DataModel<T>) dao.find(id);

			if (tempData != null) {
				//dao.delete(tempData);
				listOfEntitys.add(tempData);
				removeId.add(id);
			}

			DataModel resultObject = null;

			if(tempData != null)
			{
				resultObject = (DataModel) algoCache.putElement(tempData.getId(), tempData);
			}
			if (resultObject != null) {
				dao.save(resultObject);
			}

		}


		DataModel<T>[] arrOfDataModels = new DataModel[listOfEntitys.size()];

		for (int i = 0; i < listOfEntitys.size(); i++)
		{
			arrOfDataModels[i] = listOfEntitys.get(i);
		}

		return arrOfDataModels;

	}

	public void updateFile(DataModel model)
	{
		dao.save (model);
	}

}
