# -*- coding: utf-8 -*-
from threading import Thread
import time
from socket import *
import sys
import datetime


####################
#TIMING VARIABLES TO MANIPULATE WHILE TESTING CODE
####################
BLOCK_TIME = 60
TIME_OUT = 30
for_minutes = 60 #make '60' for tesing TIME_OUT in 'minutes', '1' for testing TIME_OUT in 'seconds'

#GLOBAL VARIABLES TO MANAGE CODE.
AUTHENTICATED = False
LOGOUT = False
END = False
serverPort = int(sys.argv[1])
serverName = gethostbyname('localhost')


#Various dictionaries to keep track of who's logged in and their respective parameters.
connect_count = 0
logged_in = {}
block_list = {}
chat_list = {}
user_activity_time = {}
socket_list = {}
logs = {}
#This dictionary stores the username:password combinations.
updict = {}


#function to check if the user has been idle for longer than TIME_OUT minutes.
def check_idle(user_name,connectionSocket):
    global connect_count,user_activity_time
    while(user_name in logged_in):
        
        now = datetime.datetime.now()
        last_user_activity = user_activity_time[user_name]
        if(abs(now - last_user_activity).seconds > (TIME_OUT*for_minutes)):
            print user_name + " disconnected at " + str(now)
            print "Last user activiy at: "+str(last_user_activity)
            print "Difference = " + str(abs(now - last_user_activity).seconds )
            msend = "disconnected"
            chat_list[user_name].send(msend)
            del logged_in[user_name]
            del socket_list[user_name]
            connectionSocket.close()
            chat_list[user_name].close()
            connect_count -= 1
            del chat_list[user_name]
            del user_activity_time[user_name]
        time.sleep(1)
    print "\nCheck idle Thread closed"
            
def update_logs():
    global logs
    while(END == False):
        if(len(logged_in) > 0):
            for user_name in logged_in:
                logs[user_name] = datetime.datetime.now()
        time.sleep(1)
    print "update_logs Thread closed."
    
            
            
#function that is invoked in a seperate thread to block the user for BLOCK_TIME seconds after entering incorrect password.
def time_block(user_name):
    print "Timer started"
    time.sleep(BLOCK_TIME)
    del  block_list[user_name]
    print "Timer ended"

#function that returns whether or not a user is already logged in.
def already_logged_in(x):
    return (x in logged_in)

#function to put the words seperated by spaces in string into a list
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
    
#given a string containing the contents of a file, create a dictionary that contains username:password pairs.
def make_dict(x):
    uplist = []
    word = ''
    i = 0
    global updict
    for char in x:
        if char != ' ' and char != '\n' and char != '\r':
            word = word + char
        elif char == ' ':
            uplist.append(word)
            word = ''
            i += 1
        elif char == '\n' or char == '\r':
            uplist.append(word)
            updict[uplist[0]] = uplist[1]
            uplist = []
            word = ''
    #updict[uplist[0]] = uplist[1]
    
#function to display the username:password pairs.
def show_dict():
    for key in updict:
        print 'Password for ',key,' is :', updict[key],'\n'
        
    




#This function is used a thread to handle ONE client.
def clientThread(connectionSocket, AUTHENTICATED, LOGOUT,addr):
    global logged_in,connect_count,block_list,chat_list,socket_list,user_activity_time
    DROPPED = False
    while (LOGOUT == False):
        if not AUTHENTICATED:
            #receive the message
            print "Client thread. Waiting for response."
            
            while(not AUTHENTICATED and not DROPPED):
                mrecv = connectionSocket.recv(1024) 
                
                if mrecv in block_list:
                    if(block_list[mrecv] == addr[0]):
                        print "Client is in the blocked list. Ending connection."
                        msend = "block"
                        connectionSocket.send(msend)
                        connectionSocket.close()
                        connect_count -= 1
                        DROPPED = True
                        LOGOUT = True
                        break
                if mrecv in logged_in:
                    print "Duplicate client exists"
                    msend = "dupl"
                    connectionSocket.send(msend)
                elif(mrecv in updict):
                    print "Client has entered valid username. Prompting for password."
                    user_name = mrecv
                    attempt_count = 1
                    while(attempt_count <= 3 and (not AUTHENTICATED)):
                        #ask for password
                        print "Prompting for password: Time" +str(attempt_count)
                        msend = "pw"
                        connectionSocket.send(msend)
                        #get password
                        mrecv = connectionSocket.recv(1024)
                        #if password is correct, authenticate
                        if (mrecv == updict[user_name]):
                            print "Correct password entered"
                            AUTHENTICATED = True
                        #if password is wrong, increment attempt count, ask again
                        else:
                            attempt_count += 1
                            DROPPED = True
                            
                else:
                    print "Client has entered a wrong username"
                    msend = "wrong name"
                    connectionSocket.send(msend)
            if AUTHENTICATED:
                print user_name + "Authenticated"
                chatListenerSocket = socket(AF_INET,SOCK_STREAM) 
                #chatListenerSocket = socket(AF_INET6,SOCK_STREAM) 
                chatListenerSocket.bind(('',0))
                chatListenerSocket.listen(1)
                msend = "Auth "+str(chatListenerSocket.getsockname()[1])
                connectionSocket.send(msend)
                chatSenderSocket, addr = chatListenerSocket.accept()
                chat_list[user_name] = chatSenderSocket
                logged_in[user_name] = datetime.datetime.now()
                logs[user_name] = logged_in[user_name]
                socket_list[user_name] = connectionSocket
                user_activity_time[user_name] = datetime.datetime.now()
                idle_check_thread = Thread(target = check_idle, args = (user_name,connectionSocket))
                idle_check_thread.start()
                
                
                
                
            if not AUTHENTICATED and (not user_name in block_list):
                print "User failed to give UP for 3rd time. Dropping connection and adding to blocklist."
                msend = "Drop"
                block_list[user_name] = addr[0]
                connectionSocket.send(msend)
                connectionSocket.close()
                connect_count -= 1
                LOGOUT = True
                t1 = Thread(target = time_block, args = (user_name,))
                t1.start()
        if user_name in logged_in:
            print "Waiting for "+user_name+"'s input."
            while(mrecv != 'logout' and (user_name in logged_in)):
                mrecv = connectionSocket.recv(1024)
                user_activity_time[user_name] = datetime.datetime.now()
                print user_name + " activity at " +str(user_activity_time[user_name])
                if mrecv == 'logout':
                    msend = "logging out"
                    connectionSocket.send(msend)
                    connectionSocket.close()
                    chat_list[user_name].close()
                    connect_count -= 1
                    LOGOUT = True
                    AUTHENTICATED = False
                    del logged_in[user_name]
                    del chat_list[user_name]
                    del user_activity_time[user_name]
                    del socket_list[user_name]
                    print user_name+ " logged out"
                elif mrecv == 'whoelse':
                    msend = "whoelse"
                    for word in logged_in:
                        if not (word == user_name): 
                            msend = msend +" "+word
                    connectionSocket.send(msend)
                elif words(mrecv)[0] == 'wholast':
                    now = datetime.datetime.now()
                    msend = "wholast"  
                    for user in logs:
                        print user + ": " + str(abs(now - logs[user]).seconds)
                        print "limit : " + str(for_minutes*(int(words(mrecv)[1])))
                        if(abs(now - logs[user]).seconds < for_minutes*(int(words(mrecv)[1]))):
                            if user != user_name:
                                msend = msend +" "+user
                    connectionSocket.send(msend)
                    
                elif words(mrecv)[0] == 'message':
                    user = words(mrecv)[1]
                    if user in chat_list:
                        message = user_name+":"
                        for word in words(mrecv)[2:]:
                            message = message+" "+word 
                        chat_list[user].send(message)
                        msend = "Done sending private message."
                        connectionSocket.send(msend)
                    elif user in updict:
                        msend = user +" is not logged in."
                        connectionSocket.send(msend)
                    else:
                        msend = "Inv"
                        connectionSocket.send(msend)
                        
                elif mrecv == '' and user_name in chat_list :
                        print "\n"+user_name + " was disconnected."
                        AUTHENTICATED = False
                        LOGOUT = True
                        chat_list[user_name].close()
                        connect_count -= 1
                        del logged_in[user_name]
                        del chat_list[user_name]
                        del socket_list[user_name]
                elif mrecv == '':
                        LOGOUT = True
                elif words(mrecv)[0] == 'broadcast':
                    if words(mrecv)[1] == 'message':
                        message = user_name+":"
                        for word in words(mrecv)[2:]:
                            message = message+" "+word 
                        for user in chat_list:
                            if user != user_name:
                                chat_list[user].send(message)
                        msend = "Done Broadcasting."
                        connectionSocket.send(msend)
                    elif words(mrecv)[1] == 'user':
                        if "message" in words(mrecv)[2:]:
                            message = user_name+":"
                            send_list = []
                            index = 2
                            for word in words(mrecv)[2:]:
                                if word != "message":
                                    send_list.append(word)
                                    index += 1
                                else:
                                    break
                            for word in words(mrecv)[index+1:]:
                                message = message+" "+word 
                            for user in send_list:
                                chat_list[user].send(message)
                            msend = "Done Broadcasting."
                            connectionSocket.send(msend)
                        else:
                            msend = "Inv"
                            connectionSocket.send(msend)
                    else:
                        msend = "Inv"
                        connectionSocket.send(msend)
                        
                elif user_name in logged_in:
                    msend = "Inv"
                    print "Message received in invalid format."
                    connectionSocket.send(msend)
    print "\n "+user_name + " Client Thread closed"



#This function represents a thread used for accepting incoming client connections.
def listen_for_incoming():
    
    global connect_count,block_list,END
    c = []
    loop = True
    max_users_connected = 100
    no_of_registered_users = 100
    while(not END):
        #continue waiting for a client to connect in a queue
        #start a thread for that client
        print "Waiting for incoming connection:",str(len(logged_in)+1)
        
        connectionSocket, addr = serverSocket.accept()
        
        if(not END):
            if(connect_count < max_users_connected and len(logged_in) < no_of_registered_users):
                connect_count += 1
                msend =  "un"
                print "Client has connected."
                print "Number of times client connected:",str(connect_count)
                
                connectionSocket.send(msend)
                c1 = Thread(target = clientThread, args = (connectionSocket, AUTHENTICATED, LOGOUT,addr))
                c1.start()
                c.append(c1)
                print "Current number of users: " + str(len(logged_in))
            else:
                
                if len(logged_in) >= 9:
                    msend = "full_l"
                elif(connect_count >= max_users_connected):
                    msend = "full_c"
                connectionSocket.send(msend)
                connectionSocket.close()
                print logged_in
    print "\nListener Thread closed"
                
                
#read text file username and pass.
user_pass = open("user_pass.txt",'r').read()

#store username:password pairs inside a dictionary.
make_dict(user_pass)

#make a socket and bind it to the given port.
#serverSocket = socket(AF_INET6,SOCK_STREAM)
serverSocket = socket(AF_INET,SOCK_STREAM)  
serverSocket.bind(('',serverPort))
serverSocket.listen(1)
print 'The server is ready to receive' 
mrecv = 'start'       
    
#accept calls on a seperate thread.
server_listen = Thread(target = listen_for_incoming)
server_listen.start()
update_log = Thread(target = update_logs)
update_log.start()


#lookout for keyboard interrupt. (Ctrl+C)
try:
    while(not END):
        a = 1
except KeyboardInterrupt:
    #close all client sockets
    END = True
    temp = {}
    t = 0
    
    for key in logged_in:
        temp[key] = logged_in[key]
    for user_name in temp:
        msend = "serversd"
        chat_list[user_name].send(msend)
    tempSocket = socket(AF_INET, SOCK_STREAM)
    tempSocket.connect((serverName,serverPort))
    tempSocket.close()
    serverSocket.listen(0)
    serverSocket.close()
    END = True
                    