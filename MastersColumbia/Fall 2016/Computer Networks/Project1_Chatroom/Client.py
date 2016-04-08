# -*- coding: utf-8 -*-
from socket import *
from threading import Thread
import sys

AUTHENTICATED = False
CONNECTED = False
END = False
#serverName = '2604:2000:c524:8600:c02e:36e0:2e6f:d101'
serverName = str(sys.argv[1])
serverPort = int(sys.argv[2])
print gethostbyname('localhost')

#This function is used to convert the given string into a list of words
def words(x):
    word = ''
    up_pair = []
    for char in x:
        if char != ' ':
            word = word + char
        elif char == ' ':
            up_pair.append(word)
            word = ''
    up_pair.append(word)
    return up_pair


#This funciton is used as a thread to listen for incoming chat messages from the server and print them
#It is also used to print the server messages in cases such as when the server is shutting down.
def chat_print(clientChatSocket,clientSocket):
    global AUTHENTICATED,CONNECTED, END
    while(AUTHENTICATED and CONNECTED):
        mrecv = clientChatSocket.recv(1024)
        if(mrecv == "disconnected" or mrecv == "serversd"):
            if(mrecv == "disconnected"):
                print "You have been disconnected for being idle for too long. Press 'enter' to terminate program."
            elif(mrecv == "serversd"):
                print "Server has shut down. Press 'enter' to terminate program."
                
            clientSocket.close()
            clientChatSocket.close()
            CONNECTED = False
            AUTHENTICATED = False
            END = True
        
        else:
            print '\n' +mrecv

#The main program starts here.
#The client is prompted for username and password.
#If authenticated,the client may enter chat commands, othewise the client is blocked for a specific period of time.
print "**************************\nSimple CHATROOM.\n**************************"
while not END:
 try:
    
    while(not CONNECTED and not END):
        #input = raw_input("\n\nType:\n1. 'login' to connect to chat server.\n2. 'exit' to exit application\n\n");
        input = "login"
        if(input == 'login'):
            
            clientSocket = socket(AF_INET, SOCK_STREAM)
            #clientSocket = socket(AF_INET6, SOCK_STREAM)
            clientSocket.connect((serverName,serverPort))
            
            mrecv = clientSocket.recv(1024)
            if(mrecv == "full_c"):
                print "Server is currently at full capacity. Please try again."
                clientSocket.close()
            elif mrecv == "full_l":
                print "All registered users are currently connected."
                clientSocket.close()
            elif mrecv == "un":
                CONNECTED = True
                print "\n****LOG IN****"
        elif input == 'exit':
            print "Thank you, come again!"
            END = True
        else:
            print "Invalid input."
            
    #The user is prompted for username and password.
    while(not AUTHENTICATED and CONNECTED):
        print "\nPlease enter your login details."
        un = raw_input("Username: ")
        clientSocket.send(un)
        mrecv = clientSocket.recv(1024)
        if mrecv == "wrong name":
            print "\nNo such user, please try again"
        elif mrecv == "block":
            print "\nYou're currently blocked. Please try again later"
            clientSocket.close()
            CONNECTED = False
        elif mrecv == "dupl":
            print "\nThis user is already logged in. Try again if you're a different user."
        elif mrecv == "pw":
            while(CONNECTED and (not AUTHENTICATED)):
                pw = raw_input("Password: ")
                msend = pw
                clientSocket.send(msend)
                mrecv = clientSocket.recv(1024)
                if words(mrecv)[0] == 'Auth':
                    AUTHENTICATED = True
                    print "\nYou have been authenticated. Welcome to simple chat server!"
                    clientChatSocket = socket(AF_INET, SOCK_STREAM)
                    #clientChatSocket = socket(AF_INET6, SOCK_STREAM)
                    clientChatSocket.connect((serverName,int(words(mrecv)[1])))
                    c1 = Thread(target = chat_print,args = (clientChatSocket,clientSocket))
                    c1.start()
                    #listen = Thread(target = receive_broadcast, args = (clientSocket,))
                    #socket.setblocking(0)
                    #clientSocket.settimeout(0.02)
                    #listen.start()
                elif mrecv == 'Drop':
                    print "\nWrong details entered 3 times. Your connection has been dropped."
                    clientSocket.close()
                    CONNECTED = False
                elif mrecv == "pw":
                    print "\nWrong password. Try again."
        
        
            
    #This part can only excecute after authentication
    #The user has now logged in to the chat server and may now enter chat commands.
    while AUTHENTICATED and CONNECTED:
        #tell client he can either type end or enter a string for its capitalised.
        msend = raw_input("\n\nCHATROOM:\nType:\n1. whoelse \n2. wholast <number>\n3. broadcast message <message>\n4. broadcast user <user> <user> ... <user> message <message>\n5. message <user> <message>\n6. logout\n\n")
        
        if AUTHENTICATED and (msend != ""):
            clientSocket.send(msend)
            mrecv = clientSocket.recv(1024)
        else:
            mrecv = "empty"
        if not AUTHENTICATED:
            print "Connection dropped."
        elif words(mrecv)[0] == 'logging':
            print 'Disconnected from the server.'
            clientSocket.close()
            clientChatSocket.close()
            CONNECTED = False
            AUTHENTICATED = False
            END = True
        elif words(mrecv)[0] == "whoelse":
            if len(words(mrecv)[1:]) == 0:
                print "No one"
            else:
                print "Others in the room are: "
                for word in words(mrecv)[1:]:
                    print word
        elif mrecv == "Inv":
            print "\nInvalid message format."
        elif words(mrecv)[0] == "wholast":
            if len(words(mrecv)[1:]) == 0:
                print "No one"
            else:
                print "Others who were logged in within given time are: "
                for word in words(mrecv)[1:]:
                    print word
        elif mrecv == "empty":
            print "Empty string entered. Please try again."
        else:
            print "\nServer replied: ",mrecv
            
 #If the user use ctrl+c to exit, this exception block will excecute.
 except KeyboardInterrupt:
     msend = 'logout'
     clientSocket.send(msend)
     mrecv = clientSocket.recv(1024)
     print '\n\nClient disconnected from the server.'
     clientSocket.close()
     clientChatSocket.close()
     CONNECTED = False
     AUTHENTICATED = False
     END = True

print "Session ended."
