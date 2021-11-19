// Programmer: Sarah Yaw
// Client program with added encryption
// File name: TCPClientAttack.java

import java.io.*;
import java.net.*;
import java.util.*;
public class TCPClientAttack
{
    private static InetAddress host;
    private static String input="";
    public static boolean closing=false;
    public static int G, N, myKey, serverKey, a;
    public static void main(String[] args)
    {
        int port = 0;
        String user = "";
        boolean hasHost=false, hasPort=false, hasUser=false;
        int hostIndex=0, portIndex=0, userIndex=0; 
        Scanner keyb = new Scanner(System.in);
        try
        {
            if(args.length>0)
            {
                //check to see what command is input by user
                for(int i=0; i<args.length;i++)
                {
                    if(args[i]==null)
                        input=input+args[i];
                    else
                        input = input+args[i]+" ";
                    if(args[i].equals("-h"))
                    {
                        hasHost=true;
                        hostIndex = i+1;
                    }
                    if(args[i].equals("-p"))
                    {
                        hasPort=true;
                        portIndex = i+1;
                    }
                    if(args[i].equals("-u"))
                    {
                        hasUser=true;
                        userIndex = i+1;
                    }
                    //if there is an invalid command
                    if(!args[i].equals("-u")&&!args[i].equals("-p")&&!args[i].equals("-h")&&args[i].charAt(0)=='-')
                    {
                        System.out.println("Invalid command "+args[i]);
                        System.exit(0);
                    } 
                }
                    
                // Get server IP-address
                if(hasHost)
                {
                    host = InetAddress.getByName(args[hostIndex]);
                }
                else
                {
                    //host = InetAddress.getLocalHost();
                    System.out.println("Please enter a host");
                    System.exit(0);
                }
                
                //Get Port
                if(hasPort)
                {
                    port = Integer.parseInt(args[portIndex]);
                }
                else
                {
                    System.out.println("Default port "+port+" used.");
                }
                
                // Get username
                if(hasUser)
                {
                    user = args[userIndex];
                }
                else
                {
                    System.out.print("Please enter a username: ");
                    user = keyb.next();
                }
            }
            else if (args.length==0) //if the command line is left empty of arguments aside from running the client
            {
                System.out.println("Please enter the host, port, and username");
                System.exit(0);
                //host = InetAddress.getLocalHost();
                //port = 20700;
                //System.out.print("Please enter a username: ");
                //user = keyb.next();
            }
        }
        catch(UnknownHostException e)
        {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        runn(port, user);
    }

    private static void runn(int port, String user)
    {
        Socket link = null;
        try
        {
            // Establish a connection to the server
            link = new Socket(host,port);

            // Set up input and output streams for the connection
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(),true); 
            
            //send username before anything else
            out.write(user);
            out.write("\n");
            out.flush();
     
            //Set up stream for keyboard entry
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            String message, response;

            //communication to and from server
            Thread scComm=new Thread(new ServConsoleAttack(in));
            Thread usComm=new Thread(new UserServerAttack(out, userEntry));

            //starts
            scComm.start();
            usComm.start();

            //joins - only receives at this point and only gets server exit report
            try
            {
                scComm.join();
                usComm.join();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }

            //Receive the final report and close the connection
            message = in.readLine();
//decrypt
            response = ServConsoleAttack.decrypt(message, ServConsoleAttack.padd);
            System.out.println("\r"+response+" "+" "+" "+" "+" "+" "+" "+" "+" "+" "+" "+" "+" ");
            do
            {
                message = in.readLine();
                if (message==null){}
                else
                {
//decrypt
                    message = ServConsoleAttack.decrypt(message, ServConsoleAttack.padd);
                    System.out.println(message);
                }
            }while(message!=null && !message.substring(0,3).equals("Serv"));

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //actually close the connection
            try
            {
                System.out.println("Closing connection...");
                link.close();
            }

           catch(IOException e)
           {
               System.out.println("Unable to disconnect!");
               System.exit(1);
           }
        }
    }
}
class ServConsoleAttack extends Thread
{
    private BufferedReader fromServ;
    //console output is system.out
    private String response;
    public static String padd;
    public ServConsoleAttack(BufferedReader sbr)
    {
        fromServ=sbr;
    }

    public static String decrypt(String message, String pad)
    {
        String inp="";
        int parseInt;
        String temo[] = message.split(" ");
        char c;
        for (int i = 0; i<temo.length;i++)
        {
            if(!temo[i].equals(""))
            {
                parseInt = Integer.parseInt(temo[i]) ^ Integer.parseInt(pad,2);
                c = (char)parseInt;
                inp+=c;
            }
        }
        return inp;
    }

    @Override
    public void run()
    {
        // Receive data from the server
        while(!TCPClientAttack.closing)
        {
            try
            {
                response = fromServ.readLine();
                if (response!=null)
                {         //the client isnt listening for some reason here
                    if (!response.equals("initializing..."))
                    {
//decrypt               
                        response = decrypt(response, padd);
                        System.out.println("\r"+response+" "+" "+" "+" "+" "+" "+" "+" "+" "+" "+" "+" "+" "+" "+" "+" ");
                        System.out.print("Enter message: ");
                    }
                    else //this runs as soon as it connects to the server
                    {
                        response = fromServ.readLine();
                        String temp[] = response.split(" "); 
                        TCPClientAttack.G = Integer.parseInt(temp[1]); //g
                        TCPClientAttack.N = Integer.parseInt(temp[3]); //n

                        TCPClientAttack.a = (int)(Math.random()*100)+100; //a
                        TCPClientAttack.myKey = 1;
                        for (int i = 0; i<TCPClientAttack.a; i++)
                        {
                            TCPClientAttack.myKey = (TCPClientAttack.G * TCPClientAttack.myKey)%TCPClientAttack.N;
                        }
                        UserServerAttack.toServ.println(TCPClientAttack.myKey);//Ak
                        UserServerAttack.toServ.flush();

                        response = fromServ.readLine();
                        TCPClientAttack.serverKey = Integer.parseInt(response); //Bk

                        response = fromServ.readLine();//SkB

                        TCPClientAttack.myKey = 1;
                        for (int i = 0; i<TCPClientAttack.a; i++)
                        {
                            TCPClientAttack.myKey = (TCPClientAttack.serverKey * TCPClientAttack.myKey)%TCPClientAttack.N; //SkA
                        }

                        padd = String.format("%8s",Integer.toBinaryString(TCPClientAttack.myKey & 255)).replace(' ', '0');    

                        System.out.print("Enter message: ");
                    }
                }
            }
            catch(Exception e){System.out.println(e);}
        }
    }
}
class UserServerAttack extends Thread
{
    private BufferedReader fromUser;
    public static PrintWriter toServ;
    private String message, padd=ServConsoleAttack.padd, bin, word;
    UserServerAttack(PrintWriter pw, BufferedReader cbr)
    {
        fromUser=cbr;
        toServ=pw;
    }
    
    public String encrypt(String message, String padd)
    {
        bin="";
        String output="";
        for (int i = 0; i<message.length(); i++)
            output+= (Integer.valueOf(message.charAt(i)) ^ Integer.parseInt(padd,2))+" ";
        return output;
    }

    public String attack (String msg) 
    {
        msg+="\n"+msg;
        return msg;
    }
    @Override
    public void run()
    {
        // Get data from the user and send it to the server
        do
        {
            try
            {
                message = fromUser.readLine();
//encrypt   
                while(true){//infinite spam but gets cut off when another user sends a chat.
                toServ.println(encrypt(attack(message),ServConsoleAttack.padd));
                }
            }
            catch(Exception e){System.out.println(e);}
        }while (!message.equals("DONE"));
        TCPClientAttack.closing=true;
    }
}
