package main.java.com.hit.server;

import com.google.gson.Gson;
import main.java.com.hit.dm.DataModel;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Client {
    String IPAddress;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        DataModel[] dataModel = new DataModel[1];

        dataModel[0] = new DataModel(Long.valueOf(100), Integer.valueOf(30));

        Map<String, String> map = new HashMap<>();
        String action = "action";
        String header = "UPDATE";

        header = scanner.nextLine();
        scanner.reset();

        map.put(action, header);
        Request request = new Request(map, dataModel);
        Gson gson = new Gson();


        InetAddress address = InetAddress.getLocalHost();
        Socket clinetSocket = new Socket(address.getHostAddress(), 12345);

//        ObjectOutputStream writer=new ObjectOutputStream(clinetSocket.getOutputStream());
//        ObjectInputStream input=new ObjectInputStream (clinetSocket.getInputStream());
//
        Scanner reader = new Scanner(new InputStreamReader(clinetSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(clinetSocket.getOutputStream()));

        String gsonModel = gson.toJson(request);
//        writer.writeObject (gsonModel);

        System.out.println(gsonModel);

        writer.write(gsonModel.toString());
        DataModel model;
        String inputGson;
        boolean flag=true;
        while (flag)
        {
//            inputGson = (String) input.readObject();
            if (reader.hasNextLine()) {
                inputGson = (String) reader.nextLine();
                model = gson.fromJson(inputGson, DataModel.class);
                System.out.println(model.toString());
                System.out.println("enter another command ");
                inputGson = (String) scanner.nextLine();
  //*
            } else {
                System.out.println("enter another command ");
                inputGson = (String) scanner.nextLine();
                model = gson.fromJson(inputGson, DataModel.class);
                System.out.println(model.toString());
   //          */
            }
        }
    }
}

