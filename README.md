Creator: Sarah Yaw
Project Description:
	Multithreaded client and server programs with encryption. The server is able to support multiple clients coming and going. As long as there is at least one client, the server will update any incoming clients to the chats that happened before that specific arrival. The server will broadcast arrivals and departures of clients as well as their messages. if the TCPClientAttack is run, it will attack the server.
Run Instructions:
	1)  Open up the terminal window.
	2)  Type "cd "and then the file path to where the TCPServer.java and TCPClient.java files are located and hit enter.
			Example: cd Desktop/school/s3/sec/CyberSec-HW4
	3)  Type "javac TCPServer.java" in the terminal and hit enter. This compiles the program and adds a file called TCPServer.class to your folder.
	4)  Type "java TCPServer" and type in the credentials require then hit enter. These include the port (without which, your server will not run).
			Example: java TCPServer -p 20700
	5)  Open a second terminal window. It is imperative you do not close the one you have started the server on.
	6)  Type "cd "and then the file path to where the TCPServer.java and TCPClient.java files are located and hit enter.
			Example: cd Desktop/school/s3/sec/CyberSec-HW4
	7)  Type "javac TCPClient.java" in the terminal and hit enter. This compiles the program and adds a file called TCPClient.class to your folder.
	8)  Type "java TCPClient" and type in the credentials required. These include the host, port number, and username. Each of these three are optional, but you will be prompted to type in the username if you do not include it here. If the other two values are missing, the program will alert the user and terminate. Each of these values needs a key to be recognized as well. -h for host, -p for port, and -u for user. If an incorrect key is used, the program will complain and terminate.
			Example: java TCPClient -h hostName -u userName -p portName
	9)  You are now connected to the server and should be able to interact with it. Send whatever messages you want by typing them in and hitting enter to send.
	10) To disconnect form the server type in "DONE" and hit enter. This must be its own message.'
        To Attack:
        1) Start TCPClientAttack in the same way as TCPClient.