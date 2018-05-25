package main.java.com.hit.server;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;
import main.java.services.CacheUnitController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;


public class HandleRequest<T> extends java.lang.Object implements java.lang.Runnable {
    private CacheUnitController<T> controller;
    private Socket socket;
     private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    Gson gson;

    public HandleRequest(Socket s, CacheUnitController<T> controller){
        this.controller = controller;
        this.socket=s;

        try {

           inputStream=new ObjectInputStream(socket.getInputStream());
           outputStream=new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("RUN");
        gson=new GsonBuilder().create();
    }

    @Override
    public void run()
    {
        try {
            String command;
            DataModel[] datamodel=null;
            DataModel<T>[] body;

            Type ref = new TypeToken<Request<DataModel<T>[]>>() {}.getType();
            Request<DataModel<T>[]> socketrequest;
            command= (String) inputStream.readObject();
            System.out.println("Command is : " + command);
            socketrequest = new Gson().fromJson(command, ref);
            Map headers = socketrequest.getHeaders();
            command = (String) headers.get("action");
            body = socketrequest.getBody();


            if (command.toUpperCase().equals("GET")) {
                System.out.println("get...");
                DataModel[] dataModels = controller.get(body);
                for (DataModel<T> dm : dataModels) {
                    String outputgson = gson.toJson(dm);
                    outputStream.writeObject(outputgson);
                }
            } else if (command.toUpperCase().equals("UPDATE")) {
                System.out.println("update..");
                boolean update = controller.update(body);
                outputStream.writeObject(update);
            } else if (command.equals("DELETE")) {
                System.out.println("delete..");
                boolean delete = controller.delete(body);
                outputStream.writeObject(delete);
            } else {
                System.out.println("no apporiate header found...");
                outputStream.writeObject("Unkowon Action");
              }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
  }

    private void writeToOutputStrean(String s)
    {
        try {
            outputStream.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
