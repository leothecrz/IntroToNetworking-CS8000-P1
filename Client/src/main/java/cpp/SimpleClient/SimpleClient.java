package cpp.SimpleClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

class SimpleClient{

    private static int PORT = 4102;

    private BufferedReader inReader;
    protected PrintWriter outWriter;
    protected Socket clientSocket;

    public SimpleClient(){

        try
        {
            clientSocket = new Socket("127.0.0.1", PORT);

            inReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            ServerInputHandler serverIn = new ServerInputHandler(this);
            Thread inThread = new Thread(serverIn);
            inThread.start();

            while(clientSocket.isConnected())
            {
                String currentMessage;  
                if((currentMessage = inReader.readLine()) != null)
                    System.out.println(currentMessage);

                if(currentMessage.startsWith("/quit"))
                {
                    inReader.close();
                    outWriter.close();
                    clientSocket.close();
                }
                
            }
            
        }
        catch ( UnknownHostException e)
        {
            System.err.println("Unknow Host Exception - :");
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args)
    {
        new SimpleClient();
    }
}