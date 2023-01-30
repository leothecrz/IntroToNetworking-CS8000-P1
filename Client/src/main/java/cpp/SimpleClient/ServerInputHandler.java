package cpp.SimpleClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerInputHandler implements Runnable{

    SimpleClient parenClient;

    public ServerInputHandler(SimpleClient parentRef)
    {
        parenClient = parentRef;
    }


    @Override
    public void run() {
        
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            while(parenClient.clientSocket.isConnected()){
                try 
                {
                    String consoleMessage = consoleIn.readLine();
                    parenClient.outWriter.println(consoleMessage);
                } 
                catch (IOException e) 
                {
                    System.err.print(e.getMessage());
                }

            }
        
    }
    
}
