< Debashish Hemant Sanyal >
< UNI dhs2143 >
CSEE 4119: Computer Networks, Fall 2015
Columbia University
Programming Assignment 1: Socket Programming
< 10/7/2015 >

Submission date: < 10/8/2015 >

***SIMPLE CHATROOM***

################################
0. CHATROOM COMMANDS
################################
*Command*			*Functionality*
whoelse 			Displays name of other connected users
wholast <number> 		Displays name of those users connected 
				within the last <number> minutes. Let 0 < number < 60
broadcast message <message> 	Broadcasts <message> to all connected usersbroadcast user <user> <user> 	Broadcasts <message> to the list of users
... <user> message <message>message <user> <message>	Private <message> to a <user>logout				Log out this user.

################################
1. BRIEF DESCRIPTION OF THE CODE.
################################


#####
Server code
#####

Server listens on serverSocket
After accepting and authorising a client, a thread is created along
with two new sockets per client on the server side:
connectionSocket: to receive commands from the client
chatSenderSocket: used to specifically send messages to that client.


#####
Client code
#####

Client connects to the server using the clientSocket. After a successful
login, a another socket, called the ‘clientChatSocket’ is created and handled by 
a separate thread.
The clientChatSocket only listens for incoming chat messages and prints them.



Any time the server receives a “message” to be sent(from ONE client, to be sent to
ONE or multiple clients), the server looks up the list containing various chatSenderSockets of ‘logged-in’ users.
The server then uses these sockets to send the message to the designated clients.
THUS, there are 2 pairs of socket for each client, allowing full duplex communication
between the client and server.


################################
2. DEVELOPING ENVIRONMENT.
################################

The assignment was coded in ‘python’
IDE used: Canopy 1.5.4
sys.version 2.7.9 
Terminal on Mac was used for all testing purpose.

################################
3. INSTRUCTIONS ON HOW TO RUN THE CODE
################################

1. Install python on Mac

2. Open Terminal on Mac. Make sure the the computer is connected to the Internet.

3. change directory to the folder containing “user_pass.txt”,”Server.py” and “Client.py”

4. to run the server, type in Terminal:python Server.py <Server port number>

5. For multiple clients, open multiple Terminals (command + N) on the same computer or 
terminals on other computers connected to the Internet. 

6. To run the client, type in Terminal:python Client.py <Server IP address> <Server port no>

7. When prompted, please type ‘login’ to connect to server, you will then be prompted for your username.

################################
4. SAMPLE COMMANDS TO INVOKE THE CODE
################################

Eg. The server should be invoked as:

	python Server.py 4119

Eg. The client should be invoked as:

	python Client.py 10.6.31.102 4119




 





