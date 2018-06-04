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

    Gson gson;

    public HandleRequest(Socket s, CacheUnitController<T> controller) {
        this.controller = controller;
        this.socket = s;

        try {

//           inputStream=new ObjectInputStream(socket.getInputStream());
//           outputStream=new ObjectOutputStream(socket.getOutputStream());
            reader = new Scanner(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("RUN");
        // gson = new GsonBuilder().create();
    }

    @Override
    public void run() {
        //  try {
//        String command="test";
        DataModel[] datamodel = null;
        DataModel<T>[] body;
        Request<DataModel<T>[]> request;
        String[] outputToClient = {"Failed", "Succeeded"};
        System.out.println(outputToClient[0]);
        System.out.println(outputToClient[1]);

        //        System.out.println("1");
        String req = (String) reader.nextLine();

        if (req.equals(null))
            System.out.println("null");
        System.out.println("ok");

//        System.out.println("2");
        System.out.println("req is :" + req.toString());

        Type ref = new TypeToken<Request<DataModel<T>[]>>() {
        }.getType();
        request = new Gson().fromJson(req, ref);

        String command = request.getHeaders().get("action");
        System.out.println("Command is : " + command);

        //(String) inputStream.readObject();
        //Map headers = socketrequest.getHeaders();

        body = request.getBody();           // get the dm
        System.out.println("body is: " + body.toString());
        //System.out.println("after command ");
        Boolean outputgson;
        String result;
        DataModel[] dataModels;
        if (command.toUpperCase().equals("GET")) {
            System.out.println("get...");
            dataModels = controller.get(body);
            if (dataModels != null) {
                    result=outputToClient[1];
                    //outputgson = true;
            } else {
//                outputgson = false;
                result=outputToClient[0];

            }
            writer.println(result.toString());
            System.out.println(result);
        } else if (command.toUpperCase().equals("UPDATE")) {
            System.out.println("update..");
            boolean update = controller.update(body);

            //                outputStream.writeObject(update);
            writer.println(update);
        } else if (command.equals("DELETE")) {
            System.out.println("delete..");
            boolean delete = controller.delete(body);
            writer.println(delete);
            //              outputStream.writeObject(delete);
        } else {
            System.out.println("no apporiate header found...");
            writer.println("Unkowon Action");
            //            outputStream.writeObject("Unkowon Action");
        }
        writer.flush();
        writer.write("Exit");
    }

    private void writeToOutputStream(String s) {
        writer.write(s);
    /*}
        try {
            outputStream.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    }
}

