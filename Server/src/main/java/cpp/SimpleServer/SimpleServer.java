package cpp.SimpleServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

class SimpleServer {

    private static final int PORT = 4102;

    public SimpleServer()  {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {

            Socket onlyUser = serverSocket.accept();

            BufferedReader inReader = new BufferedReader(new InputStreamReader(onlyUser.getInputStream()));
            PrintWriter outWriter = new PrintWriter(onlyUser.getOutputStream(), true);
            outWriter.println("Connection");

            while(onlyUser.isConnected())
            {
                String currentMessage = inReader.readLine();
                if(currentMessage.startsWith("/quit"))
                {
                    outWriter.println("GOOD BYE");
                    System.out.print("GOOD BYE");
                    inReader.close();
                    outWriter.close();
                    onlyUser.close();
                }

                outWriter.println("USER: " + currentMessage);
                System.out.println("USER: " + currentMessage);
            }

        }
        catch (IOException IOX)
        {
            System.err.println(IOX.getMessage());
        }

    }

    public static void main(String[] args)
    {
        System.out.println("Hello World");

        new SimpleServer();

    }



}