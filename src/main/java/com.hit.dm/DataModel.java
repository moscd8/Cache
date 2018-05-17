package main.java.com.hit.dm;

public class DataModel <T> extends Object implements  java.io.Serializable {
    private Long  id;
    private T content;
    public  DataModel(Long id, Object content){
      this.id=id;
      this.content= (T) content;
    }

    public Long	getDataModelId(){  return this.id;}


 public    T getContent(){return this.content;}

  public   void setContent(T content){
       this.content=content;
    }

public     void setDataModelid(java.lang.Long id ){
        this.id=id;
    }

    @Override
    public String toString() {

        return "id="+id+" "+"content="+ content;//super.toString();
    }

/*
   public int hashCode(){return  (int)id; }//null;}

    void setDataModelid(java.lang.Long id ){
        this.id=id;
    }

//    java.lang.String toString(){ }
*/
}

