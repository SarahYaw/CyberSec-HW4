# Creator: Sarah Yaw

## Project Description:

Multithreaded client and server programs with encryption. The server is able to support multiple clients coming and going. As long as there is at least one client, the server will update any incoming clients to the chats that happened before that specific arrival. The server will broadcast arrivals and departures of clients as well as their messages. If the TCPClientAttack is run, it will attack the server with a simulated DOS attack.
	
## Run Instructions:

1. Open up the terminal window.
	
2. Type "cd "and then the file path to where the TCPServer.java, TCPClient.java, and TCPClientAttack.java files are located and hit enter.
	>Example: cd Desktop/school/s3/sec/CyberSec-HW4/src
			
3. Type "javac TCPServer.java" in the terminal and hit enter. This compiles the program and adds a file called TCPServer.class to your folder.
	
4. Type "java TCPServer" and type in the credentials preferred then hit enter. These include the port and the Diffe-Hellman G and N.
	>Example: java TCPServer -p 20700

### To connect as a regular client:
	
5. Open a second terminal window. It is imperative you do not close the one you have started the server on.
	
6. Type "cd "and then the file path to where the TCPServer.java, TCPClient.java, and TCPClientAttack.java files are located and hit enter.
	>Example: cd Desktop/school/s3/sec/CyberSec-HW4/src
	
7. Type "javac TCPClient.java" in the terminal and hit enter. This compiles the program and adds a file called TCPClient.class to your folder.
	
8. Type "java TCPClient" and type in the credentials required. These include the host, port number, and username. Each of these three are optional, but you will be prompted to type in the username if you do not include it here. Each of these values needs a key to be recognized as well. -h for host, -p for port, and -u for user. If an incorrect key is used, the program will complain and terminate.
	>Example: java TCPClient -h hostName -u userName -p portName
	
9. You are now connected to the server and should be able to interact with it. Send whatever messages you want by typing them in and hitting enter to send.
	
10. To disconnect form the server type in "DONE" and hit enter. This must be its own message.'
        
### To Attack:
11. Open a third terminal window.
	
6. Type "cd "and then the file path to where the TCPServer.java, TCPClient.java, and TCPClientAttack.java files are located and hit enter.
	>Example: cd Desktop/school/s3/sec/CyberSec-HW4/src
	
7. Type "javac TCPClientAttack.java" in the terminal and hit enter. This compiles the program and adds a file called TCPClientAttack.class to your folder.
	
8. Type "java TCPClientAttack" and type in the credentials required. These include the host, port number, and username. Each of these three are optional, but you will be prompted to type in the username if you do not include it here. Each of these values needs a key to be recognized as well. -h for host, -p for port, and -u for user. If an incorrect key is used, the program will complain and terminate.
	>Example: java TCPClientAttack -h hostName -u userName -p portName

## Attack Functionality and Observations
![Screenshot of four terminal windows showing the DOS attack as described below](https://github.com/SarahYaw/CyberSec-HW4/blob/main/Screen%20Shot%202021-11-19%20at%2011.05.35%20AM.png)

Once the TCPClientAttack program is run and the message to spam is entered, all the users see is spam, but the attacker can see the conversation between the users toitally fine so the victim machines would have to filter out the spam, including the server, but the attacker machine is totally free to see what's going on. This could be a way to mask traffic while the attacker does nefarious searching or just a decent DOS attack because any users won't be able to see any messages for more than a millisecond because of the two loops.       

Users can exit gracefully, but upon re-entry thire is still spam and you lose the ability to broadcast. So if the users leave the attacker can no longer surveil the messages. The old user gains the ability to broadcast to the new user but the new user cannot respond. If the old user leaves the new user is still notified but still cannot broadcast.

The newest (formerly old) user can type normally upon reentry, but there is no broadcast. The server still only receives spam. If the attacker leaves function resumes normally.
