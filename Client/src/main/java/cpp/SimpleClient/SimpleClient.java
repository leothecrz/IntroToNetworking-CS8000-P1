package cpp.SimpleClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class SimpleClient{

    private static int PORT = 4102;

    private BufferedReader inReader;
    private PrintWriter outWriter;
    Socket clientSocket;


    public SimpleClient(){

        try
        {
            clientSocket = new Socket("127.0.0.1", PORT);

            inReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            Inputs inputs = new Inputs();
            Thread inThread = new Thread(inputs);
            inThread.start();


            String currentMessage;
            while( (currentMessage = inReader.readLine()) != null){
                System.out.println(currentMessage);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args)
    {

        new SimpleClient();
    }

    class Inputs implements Runnable{

        @Override
        public void run() {
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            while(clientSocket.isConnected()){
                try {
                    String consoleMessage = consoleIn.readLine();
                    outWriter.println(consoleMessage);

                } catch (IOException e) {
                    //TODO: Handle Input I/O EXCEPTIONS
                }

            }
        }
    }

}