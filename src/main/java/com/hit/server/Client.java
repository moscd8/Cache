package main.java.com.hit.server;

import com.google.gson.Gson;

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
        String jsonpath="C:\\Users\\moshe\\IdeaProjects\\Cache\\src\\main\\resources\\request.JSON";

        Map<String, String> map = new HashMap<>();
        Gson gson = new Gson();


        InetAddress address = InetAddress.getLocalHost();
        Socket clinetSocket = new Socket(address.getHostAddress(), 12345);

        Scanner reader = new Scanner(new InputStreamReader(clinetSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(clinetSocket.getOutputStream()));


        Scanner scannerLocal  =new Scanner(new FileReader(jsonpath));

        String gsondm=scannerLocal.nextLine();
        System.out.println("The JSon is : "+ gsondm);
        writer.println(gsondm);
        writer.flush();

        System.out.println("after sendind server.. ");
        String inputGson;

        Boolean bool=false;
        inputGson = (reader.nextLine());
        bool= Boolean.valueOf(inputGson);
        System.out.println(bool);


        if (bool)
            System.out.println("Input model is: ok   ");
        else
            System.out.println("Input model is: NOT ok   ");

    }
}
