package main.java.com.hit.server;

import main.java.services.CacheUnitController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server extends Object implements Observer {

    private ServerSocket serversocket;
    private Socket socket=null;
    private boolean serverIsRunning=true;
    private CacheUnitController unitController;
    private ExecutorService executor;

    public Server() throws IOException {
        unitController = new CacheUnitController();
        executor = Executors.newFixedThreadPool(2);
        serversocket = new ServerSocket(12345);
    }

    void start() throws IOException {
        System.out.println("start");
        while (serverIsRunning) {
            socket = serversocket.accept();
            System.out.println("Waiting for the client ");
//            System.out.println("accept...");
            Thread thread = new Thread(new HandleRequest(socket, unitController));
//            executor.execute(thread);
            System.out.println("thread...");
        }
        ((ExecutorService) executor).shutdown();

    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        System.out.println("after notify " + arg.toString());
        String command = (String) arg;
        if (command.equals("start")) {
            System.out.println("Starting Server...");
            try {
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command.equals("stop")) {
            System.out.println("Shutdown In Here");
            serverIsRunning = false;
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Shutdown Server...");
        }else
        {
            System.out.println("please enter valid command ");
        }
    }
}