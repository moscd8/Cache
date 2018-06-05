package main.java.com.hit.server;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;
import main.java.services.CacheUnitController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;


public class HandleRequest<T> extends java.lang.Object implements java.lang.Runnable {
    private CacheUnitController<T> controller;
    private Socket socket;
    //     private ObjectInputStream inputStream;
//    private ObjectOutputStream outputStream;
    private Scanner reader;
    private PrintWriter writer;



    //Scanner reader = new Scanner(new InputStreamReader(clinetSocket.getInputStream()));
    //PrintWriter writer = new PrintWriter(new OutputStreamWriter(clinetSocket.getOutputStream()));

//    Gson gson;

    public HandleRequest(Socket s, CacheUnitController<T> controller) {
        this.controller = controller;
        this.socket = s;


        // System.out.println("RUN");
        // gson = new GsonBuilder().create();

        /*
        try {

//           inputStream=new ObjectInputStream(socket.getInputStream());
//           outputStream=new ObjectOutputStream(socket.getOutputStream());
            reader = new Scanner(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    @Override
    public void run() {
        //  try {
//        String command="test";
        DataModel[] datamodel = null;
        DataModel<T>[] body;
        Request<DataModel<T>[]> request;
        String[] outputToClient = {"Failed", "Succeeded"};
        Boolean outputgson;
        String result="";
        String temp=null;


        try {

//           inputStream=new ObjectInputStream(socket.getInputStream());
//           outputStream=new ObjectOutputStream(socket.getOutputStream());
            reader = new Scanner(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        //        System.out.println("1");
        String req = (String) reader.nextLine();

        if (req.equals(null))
            System.out.println("null");
        else if(req.equals("Show Statistics"))
        {   temp="";
            temp = getStatistics() + " status: " + result + " .....\n" + "#";
            System.out.println("Temp is : *&&&&&&&&&&&"+ temp);
            writer.write(temp.toCharArray());

            //  writer.print(temp);
//            writer.write(temp.toCharArray());

        }else {
                            System.out.println("Json Request:");
                            System.out.println("req is :" + req.toString());

                            Type ref = new TypeToken<Request<DataModel<T>[]>>() {}.getType();
                            request = new Gson().fromJson(req, ref);

                            String command = request.getHeaders().get("action").toUpperCase();
                            System.out.println("Command is : " + command);

                            //(String) inputStream.readObject();
                            //Map headers = socketrequest.getHeaders();

                            body = request.getBody();           // get the dm
                            System.out.println("body is: " + body.toString());
                            //System.out.println("after command ");

                            DataModel[] dataModels;

                            if(command != null)
                            {
                                switch (command){
                                    case "GET":{
                                        System.out.println("get...");
                                        dataModels = controller.get(body);
                                        if (dataModels != null) {
                                            result = outputToClient[1];
                                        } else {
                                            result = outputToClient[0];
                                        }
                                        System.out.println("result is :"+ result);
                                        break;
                                    }
                                    case "UPDATE":{
                                        System.out.println("update..");
                                        boolean update = controller.update(body);
                                        writer.println(update);
                                        break;
                                    }
                                    case "DELETE":{
                                        System.out.println("delete..");
                                        boolean delete = controller.delete(body);
                                        writer.println(delete);
                                        break;
                                    }
                                }
                            }
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        writer.flush();


//        reader.close();
//        writer.close();
//        temp = getStatistics() + " status: " + result + ".\n" + "#";
//        writer.write(temp.toCharArray());

        System.out.println("exit");
        writer.println("Exit");
        writer.flush();
        writer.close();
        reader.close();
    }

    private void writeToOutputStream(String s) {
        writer.write(s);
    }
    public  String getStatistics()
    {
       return controller.getstats();

    }
}

