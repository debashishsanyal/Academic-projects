# -*- coding: utf-8 -*-
#CSEE 4119: Computer Networks, Fall 2015, Columbia University
#Distributed Bellman­Ford
#< 12/05/2015 >

#######################
#A brief description of how this program handles various events:
#######################

#1. Client Initialised:
    #All neighbours get a NODE_ID
    #NODES and NEIGHBOURS Dictionaries get updated
    #Start timer for ALL neighbours.
    #DV gets updated with ALL nodes in NODES

#2. DV is received from a neighbour:
                    #Check if status of this neighbour = DOWN. I
                        #If YES
                            #Start timeout thread for this link
                            #Set neighbour status to up
                        #If no,
                            #just restart timer

                    #First check if DV contains any new <ipaddress,port> pair
                            #If yes
                                #Give it a new node ID
                                #update NEIGHBOUR with the new node

                    #Update NEIGHBOURS distance vector
                    #RUN BELLMANFORD TO UPDATE NODES
                    #Broadcast if DV has changed

#3. Broadcast timeout
    #UDP broadcast DV to all neighbours

    #Broadcast
        #while broadcasting to any neighbour,modify the DV being sent to the neighbour
        #such that distance to ALL nodes that have this neighbour as "next_hop"
        #is converted to infinity in the distance vector being sent.

#4. TIMEOUT FOR A NEIGHBOUR:
    #Set the distance to that neighbour as infinity
    #Perform BellmanFord algorithm.
    #Broadcast

#5. User enters a command
    #Handle command as described in the assignment.


from socket import *
from threading import Thread
import sys
import time
import datetime
from select import select

localport = int(sys.argv[1])
TIMEOUT = int(sys.argv[2])

#################################
#GLOBAL VARIABLES
#################################
CLOSE = False
DV = {}#Dictionary containing ip address,port,distance,seperated by commas for every node known to this node
NEIGHBOURS = {} #Object containing neighbour ip,port, cost(link_cost, fixed), distance(current
                #distance to neighbour, variable), status(“UP”,”DOWN”), DV(DV of neighbour),
	        #‘downed’(Boolean to indicate that this link has been “LINKDOWN”-ed).
NODES = {} #Object containing node ip, port, distance, tupl (IP,port identifier tuple), next_hop.

NEIGHBOUR_UPDATE_TOGGLE = {} #Toggled when update is received from neighbour

NODE_ID = 0 #This variable is used to keep track of different NODES in the network.

timer = "STOP" #Broadcast timer control variable.

Inf = float('inf'); #This variable stores the value 'infinity'

my_ip = "" #This string will store this client's public ip address. This is extracted from a neighbour's ROUTE_UPDATE message.

#################################
#INITIALISING SOCKETS
#################################
#listenSocket = socket(AF_INET6, SOCK_DGRAM)
listenSocket = socket(AF_INET, SOCK_DGRAM)
listenSocket.bind(('', localport))


#broadcastSocket = socket(AF_INET6, SOCK_DGRAM)
broadcastSocket = socket(AF_INET, SOCK_DGRAM)
broadcastSocket.bind(('', 0))

#################################
#OBJECTS: NEIGHBOURS AND NODES
#################################
class neighbour:
    def __init__(self,ip,port,distance):
        self.ip = ip
        self.port = port
        self.distance = distance
        self.cost = distance
        self.status = "UP"
        self.DV = {}
        self.downed = False
    def __str__(self):
        return "IP address: " + self.ip +", Port = " + str(self.port) + ", Link weight = " + str(self.distance) + ", Status = " + self.status


class node:
    def __init__(self,ip,port,distance):
        self.ip = ip
        self.port = port
        self.distance = float(distance)
        #self.status = "UP"
        self.tupl = ip + str(port)
        self.next_hop = "No next hop yet."
    def __str__(self):
        return "IP address: " + self.ip +", Port = " + str(self.port) + ", Distance = " + str(self.distance) + ", Status = " + self.status

    def dist(self):
        dist = 0 + self.distance
        return self.dist

#################################
#HELPER FUNCTIONS
#################################

def get_node_id(ip,port):
    #here port is also a string
    for node_id in NODES:
        if NODES[node_id].ip == ip:
            if str(NODES[node_id].port) == port:
                return node_id
    return -1

def get_neig_node_id(ip,port):
    for node_id in NEIGHBOURS:
        if NEIGHBOURS[node_id].ip == ip:
            if str(NEIGHBOURS[node_id].port) == port:
                return node_id
    return -1


def bellman():
    change = False
    for nod in NODES:#For all nodes in the network
        hopp = NODES[nod].next_hop
        min2 = NODES[nod].distance #store current distance to this node to check if anything has changed
        mins = Inf #initialise mins to Inf to check if mins changed, storing mins' value
        tupl = NODES[nod].tupl #tupl represents the node
        for neig in NEIGHBOURS:#See which neighbour is the best next hop for this
            if NEIGHBOURS[neig].status == "UP": #if the neighbour link is active
                d2 = mins
                if (neig != nod): #if the node being checked is NOT the neighbour being checked
                    try: #In case the DV is non existant
                        d2 = NEIGHBOURS[neig].DV[tupl] + NEIGHBOURS[neig].distance
                    except:
                        a = 1
                        #print "Error in d2."
                else:
                         d2 = NEIGHBOURS[neig].distance
                if d2 < mins:
                    mins = d2
                    next_hop = "("+NEIGHBOURS[neig].ip+":"+str(NEIGHBOURS[neig].port)+")"

        try:
            NODES[nod].distance = mins
            if mins == Inf:
                next_hop = "No next hop currently."
            NODES[nod].next_hop = next_hop
            addtoDV(NODES[nod],nod)
        except:
            print "Bellman failed."
        if (min2 != mins) or (NODES[nod].next_hop != hopp): #If my distance vector has changed
            change = True
    #If there is any change in the distance vector
    if change:
        Broadcast()

    #Broadcast()
    #print "Entered Bellman"


#This function is to mark a particular link as down when 3*TIMEOUT happens.
def LINKDOWN2(node_id):
    if(node_id != -1):
        global NEIGHBOURS,NODES
        NEIGHBOURS[node_id].distance = Inf
        NEIGHBOURS[node_id].status = "DOWN"
        NEIGHBOURS[node_id].DV = {}


        NODES[node_id].distance = Inf
        NODES[node_id].next_hop = "No next hop currently."
        addtoDV(NODES[node_id],node_id)
        bellman()


#This function is used to send route updates to all active neighbours.
def Broadcast():
    for nod in NEIGHBOURS:
        if NEIGHBOURS[nod].status == "UP":
            remote_IP = NEIGHBOURS[nod].ip
            remote_port = NEIGHBOURS[nod].port
            neig = "("+remote_IP+":"+str(remote_port)+")"
            neig_tupl = remote_IP + str(remote_port)
            dv = str(localport)

            #Implementing poisoned reverse
            for key in DV:
                node = NODES[key]
                ip = node.ip
                port = str(node.port)
                if (node.next_hop == neig) and (neig_tupl != node.tupl): # i.e. If I go to this 'key' node via this neighbour and 'key' node is not this neighbour
                    distance = Inf #poison reverse
                else:
                    info = extract_DV(DV[key])
                    distance = str(info[2]) #normal distance

                dv = dv + ',' + ip + ',' + port + ',' + str(distance)

            send_dv = dv + "," + remote_IP
            broadcastSocket.sendto(send_dv,(remote_IP, remote_port))


#Updates the DV.
def addtoDV(node,node_id):
    global DV

    ip = node.ip
    port = str(node.port)
    distance = str(node.distance)

    dv_this = ip + ',' + port + ',' + distance

    DV[node_id] = dv_this

#Extract info from a given DV string.
def extract_DV(dv_this):
    return dv_this.split(',')

#Create new link to a non-neighbour
def add_new_link(node_id):

    ip = NODES[node_id].ip
    port = int(NODES[node_id].port)
    distance = float(raw_input("\nEnter new link cost: "))

    NEIGHBOURS[node_id] = neighbour(ip,port,distance)

    NEIGHBOUR_UPDATE_TOGGLE[node_id] = 1
    timerz = Thread(target = timeout_linkdown,args = (node_id,1))
    timerz.start()

#Invoked when new non-neighbour nodes enter the network
def add_new_neighbour(addr,dv_received):
    global DV, NEIGHBOURS, NODES, NEIGHBOUR_UPDATE_TOGGLE, NODE_ID,my_ip
    #print "Here in da hood."
    ip = addr[0]
    port = int(dv_received[0])
    print "New neighbour detected. IP: " + ip + ", port: " + str(port) + "\n"
    l = len(dv_received)
    my_ip = dv_received[l-1]
    count = 0

    #Finding my distance from this new neighbour
    for strings in dv_received: #For all entries in the DV
        if (strings == my_ip) and count <(l-1): #If entry is my ip
            if int(dv_received[count+1]) == localport: #if entry after that is my port
                distance = float(dv_received[count+2]) #then extract my distance from this neighbour

        count += 1
    node_id2 = get_node_id(ip,str(port))

    #Check if this is new neighbour is also a new node in the network
    if node_id2 == -1:
        #if not, make new node_id and add to both lists

        #make a new NODE_ID
        NODE_ID += 1

        #update NEIGHBOURS, NODES, DV
        NEIGHBOURS[NODE_ID] = neighbour(ip,port,distance)
        NODES[NODE_ID] = node(ip,port,distance)
        NODES[NODE_ID].next_hop = "("+ip+":"+str(port)+")"
        addtoDV(NODES[NODE_ID],NODE_ID)
        id_now = NODE_ID
    else:
        NEIGHBOURS[node_id2] = neighbour(ip,port,distance)
        id_now = node_id2

    #Start timeout for this new neighbour.
    NEIGHBOUR_UPDATE_TOGGLE[id_now] = 1
    timerz = Thread(target = timeout_linkdown,args = (id_now,1))
    timerz.start()
    bellman()




#Initialisation function:
    #All neighbours get a NODE_ID
    #NODES and NEIGHBOURS Dictionaries get updated
    #ALL neigbours get their toggle state set to 1 and start timer for ALL neighbours. (This is to help check if the neighbour is regularly sending route updates.)
    #DV gets updated with ALL nodes in NODES
def initialise():#This function takes system arguments and enters them into
                 #all three structures, i.e. DV, NODES, and NEIGHBOURS
    global DV, NEIGHBOURS, NODES, NEIGHBOUR_UPDATE_TOGGLE, NODE_ID
    count = 3

    try:
        while(True):
            ip = str(sys.argv[count])
            if ip == 'localhost':
                ip = gethostbyname('localhost')
            port = int(sys.argv[count+1])
            distance = float(sys.argv[count+2])
            NODE_ID += 1

            #update NEIGHBOURS, NODES, DV
            NEIGHBOURS[NODE_ID] = neighbour(ip,port,distance)
            NODES[NODE_ID] = node(ip,port,distance)
            NODES[NODE_ID].next_hop = "("+ip+":"+str(port)+")"
            addtoDV(NODES[NODE_ID],NODE_ID)
            #print NODES[NODE_ID]

            #Toggle to  1 and Start timer for ALL neighbours.
            NEIGHBOUR_UPDATE_TOGGLE[NODE_ID] = 1
            timerz = Thread(target = timeout_linkdown,args = (NODE_ID,1))
            timerz.start()
            count = count + 3
    except:
        a = 1
        #print "Initialisation failed."
        #print "Something went wrong while initialising."

#################################
#COMMAND LINE FUNCTIONS
#################################



def LINKDOWN(nod):
    remote_IP = NEIGHBOURS[nod].ip
    remote_port = NEIGHBOURS[nod].port
    dv = "D" + "," + str(localport)
    if NEIGHBOURS[nod].status == "UP":
        broadcastSocket.sendto(dv,(remote_IP, remote_port))
    LINKDOWN2(nod)
    NEIGHBOURS[nod].downed = True

def LINKUP(nod):
    NEIGHBOURS[nod].distance = NEIGHBOURS[nod].cost
    NEIGHBOURS[nod].status = "UP"
    NEIGHBOURS[nod].DV = {}
    NEIGHBOURS[nod].downed = False

    NODES[nod].distance = NEIGHBOURS[nod].cost
    addtoDV(NODES[nod],nod)

    timerz = Thread(target = timeout_linkdown,args = (nod,1))
    timerz.start()

def SHOWRT():
    global NODES
    now = now = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    print "\n"+ str(now)+ ' Distance vector list is:\n'
    for nod in NODES:
        if nod in NEIGHBOURS:
            print "Destination = " + NODES[nod].ip+":"+str(NODES[nod].port) + ", Cost = " + str(NODES[nod].distance) + ", Link = " + str(NODES[nod].next_hop) + "  [Destination is a NEIGHBOUR]"
        else:
            print "Destination = " + NODES[nod].ip+":"+str(NODES[nod].port) + ", Cost = " + str(NODES[nod].distance) + ", Link = " + str(NODES[nod].next_hop)
    print "\nYour port number: " + str(localport)




#################################
#TIMER THREADS
#################################

#The following function is used to send ROUTE UPDATES TO all neighbours periodically.
def timeout_broadcast(a,b):
    global timer
    while not CLOSE :
        if timer == "START":# If timer has just been restarted
            now = datetime.datetime.now()
            timer = "STARTED"
        if timer == "STARTED":
            later = datetime.datetime.now()
            elapsed = int(abs(now - later).seconds)
            if elapsed > TIMEOUT:
                #print "\n\nBroadcast."
                Broadcast()
                now = datetime.datetime.now()
        time.sleep(TIMEOUT/10) #Allow the cpu to catch a breath.




def timeout_linkdown(node_id,a):
    now = datetime.datetime.now()
    update_state = NEIGHBOUR_UPDATE_TOGGLE[node_id]

    while (not CLOSE) and (not NEIGHBOURS[node_id].status == 'DOWN'):
        #Everytime an update is received from a neighbour, the value corresponding
        #to the key 'node_id' will be changed by the thread listening for updates.
        #At this point, this thread will detect that an update is received by
        #checking the condition below. Both countdown time(now) and update_state
        #are updated if this is true.
        if update_state != NEIGHBOUR_UPDATE_TOGGLE[node_id]:
            now = datetime.datetime.now()
            update_state = NEIGHBOUR_UPDATE_TOGGLE[node_id]

        later = datetime.datetime.now()
        elapsed = int(abs(now - later).seconds)
        if elapsed > TIMEOUT*3:# If more than 3*TIMEOUT seconds have elapsed since the last update
            #print "\n\n" + str(node_id) + ": Link down. \nLast assumed active at: " + str(now) +"\nCurrent time: " + str(later) + "\n\n"
            #Clearly the link is down.
            LINKDOWN2(node_id)
            #SET dv, node and neighbour to infinity
            #Clearly the link is down.
        time.sleep(TIMEOUT/4) #Allow the cpu to catch a breath.


#################################
#UDP THREADS
#################################

#If a neighbour has implemented "LINKDOWN"
def UDP_Down_Handle(addr,data):
    dv_received = extract_DV(data)
    node_id = get_node_id(addr[0],dv_received[1])
    LINKDOWN2(node_id)

#Handle various UDP messages received
def UDP_Handle(addr,data):

    global DV, NEIGHBOURS, NODES, NEIGHBOUR_UPDATE_TOGGLE, NODE_ID,my_ip
    dv_received = extract_DV(data)
    node_id = get_neig_node_id(addr[0],dv_received[0])

    #if this node isn't a neighbour
    if(node_id == -1):
        add_new_neighbour(addr,dv_received) #make it a neighbour

    #Else if it's a neigbhbour and this particular neighbour isn't LINKDOWN
    elif not NEIGHBOURS[node_id].downed:
        #print "Node id = " + str(node_id)
        NEIGHBOUR_UPDATE_TOGGLE[node_id] = 1 - NEIGHBOUR_UPDATE_TOGGLE[node_id]

        #If this neighbour was marked as DOWN earlier, update to UP, start timer, and update distance to neighbour.
        if NEIGHBOURS[node_id].status == "DOWN":
            NEIGHBOURS[node_id].status = "UP"
            NEIGHBOURS[node_id].distance = NEIGHBOURS[node_id].cost
            #print "Status changed to: " + NEIGHBOURS[node_id].status + "node id: "+ str(node_id)
            timerz = Thread(target = timeout_linkdown,args = (node_id,1))
            timerz.start()

        #Checking for new nodes from neighbours and simultaneously make the DV to be stored for the neighbour
        count = 1
        l = len(dv_received)
        my_ip = dv_received[l-1]
        neig_DV = {} #DV for the neighbour
        #print "l = " + str(l)
        while (count < l-3 ):
            ip = dv_received[count]
            port =  dv_received[count+1]
            keyl = ip+port #key tuple for DV of neighbour
            neig_DV[keyl] = float(dv_received[count+2])
            #print "Ip = "+ip+ ", port = "+ port
            node_id2 = get_node_id(ip,port)


            #print "Port: "+port + ", count = "+str(count) + ", l-1 = "+ str(l-1)
            #if the given tuple is NOT in my list and its NOT me
            if node_id2 == -1 and not (int(port) == localport and ip == my_ip):
                #add this newly discovered node to my list of network nodes.
                NODE_ID += 1
                NODES[NODE_ID] = node(ip,port,Inf)
                NODES[NODE_ID].next_hop = "No next hop currently."
                addtoDV(NODES[NODE_ID],NODE_ID)
            count += 3
        NEIGHBOURS[node_id].DV = neig_DV

        #Run bellman's algorithm.
        bellman()


def UDP_Listen(listenSocket,b):
    global NEIGHBOUR_UPDATE_TOGGLE,CLOSE
    inputs = [listenSocket]
    try:
        while not CLOSE:
            inputready,outputready,exceptready = select(inputs,[],[])
            for s in inputready:
                if s == listenSocket:
                    data,addr = s.recvfrom(8000)
                    if data[0] == "D": #If LINKDOWN received from neighbour, handle accordingly
                        UDP_Down_Handle(addr,data)
                    else:#else handle normally
                        UDP_Handle(addr,data)
            time.sleep(TIMEOUT/20)



    except:
        #print "UDP Listen thread failed."
        #print "Parallel processing error. Please run this again."
        CLOSE = True


#################################
#MAIN PROGRAM STARTS
#################################

print "\n\n*******DISTRIBUTED BellmanFord*******"

#Initialise broadcast and listen threads.
broadcast = Thread(target = timeout_broadcast,args = (1,1))
listen = Thread(target = UDP_Listen,args = (listenSocket,1))

#Initialise
initialise()

#Start UDP listen and broadcast threads.
listen.start()
broadcast.start()

#start timer for broadcast
timer = "START"


#Prompt user to control commands.
try:
    while not CLOSE:
        print '\n\nEnter one of the following commands: \n'
        print "LINKDOWN <IP> <port>    (to 'down' a link)"
        print "LINKUP <IP> <port>      (to 'restore' a link)"
        print 'SHOWRT                  (to display the routing table)'
        print 'CLOSE                   (to close the client)'
        print 'NEWLINK                 (to add a new link)\n\n'
        s = str(raw_input(">> "))
        if s == 'SHOWRT' or s == 'showrt':
            #print "here"
            SHOWRT()

        elif s == 'CLOSE' or s == 'close':
            CLOSE = True

        elif s[:5] == 'LINKD' or s[:5] == 'linkd' :
            err = False
            s = [s.replace(' ', ',') for char in s][-1]
            try:
                ip = extract_DV(s)[1]
                port = extract_DV(s)[2]
            except:
                print "Incorrect command format."
                err = True
            if not err:
                neig_id = get_neig_node_id(ip,port)
                if(neig_id == -1):
                    print "No such link available."
                elif NEIGHBOURS[neig_id].downed:
                    print "Link is already down."
                else:
                    LINKDOWN(neig_id)
                    print "Link successfully taken down."

        elif s[:5] == 'LINKU' or s[:5] == 'linku':
            err = False
            s = [s.replace(' ', ',') for char in s][-1]
            try:
                ip = extract_DV(s)[1]
                port = extract_DV(s)[2]
            except:
                print "Incorrect string."
                err = True
            if not err:
                neig_id = get_neig_node_id(ip,port)
                if(neig_id == -1):
                    print "No such link available."
                elif not NEIGHBOURS[neig_id].downed:
                    print "Link is already up."
                else:
                    LINKUP(neig_id)
                    print "Link successfully restored."

        elif s == "NEWLINK" or s == "newlink":
            print "Available NODES to convert to NEIGHBOURS..\n"
            counts = 0
            temp_list = []
            for nodes in NODES:
                if nodes not in NEIGHBOURS:
                    print str(nodes) + ". IP = "+ NODES[nodes].ip + ", port = " + str(NODES[nodes].port)
                    counts += 1
                    temp_list.append(nodes)
            if counts > 0:
                err = True
                while(err):
                    try:
                        nod_id = int(raw_input("\nChoose a node from above: (Enter the corresponding index number)\n\n"))
                        err = False
                    except:
                        print "Incorrect entry. Please enter an integer corresponding to the available choices."
                try:
                    if nod_id in temp_list:
                        add_new_link(nod_id)
                        print "New link successfully created."
                    else:
                        print "Incorrect choice"
                except:
                    print "Incorrect choice. "
            else:
                print "None. There isn't such a node currently in your network. "
        else:
            print "Wrong command format"
except:
    #print "Main failed."
    CLOSE = True


print "\n\nClosing the client."
listenSocket.close()
broadcastSocket.close()

print "Program terminated."
