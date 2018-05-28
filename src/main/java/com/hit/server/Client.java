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
        String jsonpath = "C:\\Users\\moshe\\IdeaProjects\\Cache\\src\\main\\resources\\request.JSON";

//*        DataModel[] dataModel = new DataModel[2];

    //*    dataModel[0] = new DataModel(Long.valueOf(100), Integer.valueOf(30));
    //*    dataModel[1] = new DataModel(Long.valueOf(101), Integer.valueOf(31));

        Map<String, String> map = new HashMap<>();
//        String action = "action";
//        String header = "UPDATE";

//        header = scanner.nextLine();
//        scanner.reset();

//        map.put(action, header);
//*        Request request = new Request(map, dataModel);
        Gson gson = new Gson();


        InetAddress address = InetAddress.getLocalHost();
        Socket clinetSocket = new Socket(address.getHostAddress(), 12345);

//        ObjectOutputStream writer=new ObjectOutputStream(clinetSocket.getOutputStream());
//        ObjectInputStream input=new ObjectInputStream (clinetSocket.getInputStream());
//
        Scanner reader = new Scanner(new InputStreamReader(clinetSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(clinetSocket.getOutputStream()));

//*        String gsonModel = gson.toJson(request);
//        writer.writeObject (gsonModel);

        //*     System.out.println(gsonModel);

        //*       writer.println(gsonModel.toString());
        //*writer.flush();


        Scanner scanner2  =new Scanner(new FileReader(jsonpath));

        String gsondm=scanner2.nextLine();
        System.out.println(gsondm);
        writer.println(gsondm);
        writer.flush();

        System.out.println("after sendind server.. ");
        String inputGson;

        DataModel[] model;
        boolean flag = true;
          //while (flag)
          //  {


//            inputGson = (String) input.readObject();
        //    if (reader.hasNextLine()) {


//        inputGson = (String) reader.nextLine();
//        System.out.println("Input Jason is :  "+inputGson);
        Boolean bool=false;
//        inputGson = reader.nextLine();
        bool = Boolean.valueOf(reader.nextLine());

        if (bool)
            System.out.println("Input model is: ok   ");
        else
            System.out.println("Input model is: NOT ok   ");

//        model = gson.fromJson(inputGson, DataModel[].class);
//        System.out.println("DMS  is :"+model.toString());
       //       }
    }
}
