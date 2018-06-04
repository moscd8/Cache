package main.java.com.hit.dao;

import main.java.com.hit.dm.DataModel;

import java.io.*;

public class DaoFileImpl<T>  implements IDao<Long,DataModel<T>> {
    private String filename;

    public  DaoFileImpl(String filename){
        this.filename=filename;
}

    @Override
    public void delete(DataModel<T> entity) {
        try {
            BufferedReader fr=new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            String temp_filename="C:\\Users\\moshe\\IdeaProjects\\Cache\\src\\main\\resources\\GetDataModeles1.txt";
//            temp_filename=filename.substring(filename.indexOf(".txt"+(-4)));
            PrintWriter pw= new PrintWriter(new BufferedWriter(new FileWriter(temp_filename)));
            String line=fr.readLine();

            while (line != null ){
                if(line.toString().contains(entity.getDataModelId().toString()))
                    line=fr.readLine();
                else
                {
                    pw.println(line.toString());
                    line=fr.readLine();
                }
            }
            fr.close();
            pw.close();
            File file=new File(filename);
            file.delete();

            file=new File(temp_filename);
            file.renameTo(new File(filename));
        }   catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public DataModel<T> find(Long aLong) {
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            DataModel<T> page = null;
            String line = fr.readLine();
           while ( line != null){
           {
              System.out.println(line.toString());
              if(line.toString().contains(aLong.toString()))
                {
                   // System.out.println("yes");
                    //System.out.println(line.toString());
                    fr.close();
                    String co=line.substring(line.indexOf("content")+8);
//                    System.out.println("Co="+co);
                    return new DataModel<T>(aLong,co);
                }
               line=fr.readLine();
            }
           }

  } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void save(DataModel<T> entity) {
        PrintWriter pw = null;
        BufferedReader fr = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            fr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            String line = fr.readLine();
            while (line != null) {
                if ((line.contains(entity.getDataModelId().toString()))) {
                //    System.out.println("is already exist..");
                    break;
                } else
                    line = fr.readLine();
            }
            pw.println(entity.toString());
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                fr.close();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}


