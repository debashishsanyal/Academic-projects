< Debashish Hemant Sanyal >
< UNI dhs2143 >
CSEE 4119: Computer Networks, Fall 2015
Programming Assignment 3: Distributed Bellman­Ford
< 12/05/2015 >

Submission deadline: < 12/09/2015 >

***Distributed Bellman­Ford***

NOTE: Here ‘client’ and ‘node’ are terms used interchangeably.

################################
INSTRUCTIONS ON HOW TO RUN THE CODE
################################

1. Install “python 2” on Mac

2. Open Terminal on Mac. Make sure the the computer is connected to the Internet.

3. Change your directory in Terminal to the folder in which the program code is stored.

4. To run bfclient, type in Terminal:

% python bfclient.py <localport> <timeout in seconds> <[ipaddress1 port1 linkweight1 ipaddress2 port2 linkweight2  …]>

e.g.1 python bfclient.py 4115 3 128.59.196.2 4116 5.0 128.59.196.2 4118 30.0

e.g.2 python bfclient.py 4116 1 localhost 4115 5.0 localhost 4119 5.0 localhost 4117 10.0

################################
DETAILS ON THE DEVELOPING ENVIRONMENT.
################################

The assignment was coded in ‘python’
IDE used: Canopy 1.5.4
sys.version 2.7.9 
Terminal on Mac was used for all testing purposes.


################################
(a) Program features
################################

1. Dynamic addition of nodes.
2. Distance Vectors converge as expected (shortest path).
3. Poisoned reverse implemented.

################################
(b) A brief description of how the program handles various events:
################################

#1. Client Initialised:
	#All neighbours get a NODE_ID
	#NODES and NEIGHBOURS Dictionaries get updated
	#Start timer thread for ALL neighbours.
	#Distance Vector gets updated with ALL nodes in NODES
    
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
        #such that distance to ALL nodes that have this neighbour as an “next_hop” 
        #is converted to infinity in the distance vector being sent.

#4. TIMEOUT FOR A NEIGHBOUR:
    #Set the distance to that neighbour as infinity
    #Perform BellmanFord algorithm.
    #Broadcast
    
#5. User enters a command
    #Handle command as described in the assignment.


################################
(b) Usage scenarios
################################

SCENARIO 1: New node created.
	All neighbours of this node receive broadcast message(ROUTE_UPDATE)
	Some neighbours may add this new node to their list of neighbours,
	while other neighbours just update that cost of the link to this node
	from ‘Infinity’.

SCENARIO 2: Existing node ‘CLOSES’/‘CRASHES’
	All neighbouring nodes mark their distance to this node as Infinity.

SCENARIO 3: New link can be created between 2 non-neighbours.

SCENARIO 4:. User enters a command
    #Handle command as described in the assignment.
	

################################
(c) Protocol Specification
################################

Every node has the following main attributes:

a) 2 LIST OF OBJECTS:
	i. List of NEIGHBOURS objects <— Contains a list of all known neighbours
	ii. List of NODES objects <- contains a list of all known nodes in the
	network(including neighbours)

	—> These lists are implemented as ‘dictionaries’, with the ‘key’ being the assigned 
	“NODE_ID”.
	—> Within each node, “NODE_ID”s are assigned to each known network node arbitrarily.
	—> NEIGHBOURS and NODES objects with the same NODE_ID correspond to the same client
	within the network.

	NEIGHBOURS object attributes: ip, port, cost(link_cost, fixed), distance(current distance to 
	neighbour, variable), status(“UP”,”DOWN”), DV(DV of neighbour),
	‘downed’(Boolean to indicate that this link has been “LINKDOWN”-ed).

	NODES object attributes: ip, port, distance, tupl (<IP,port> identifier tuple), next_hop.

	

b) Distance Vector (DV)
	The distance vector is stored as a list in the program and is converted to a 
	string each each time it is sent to a neighbour.
	The format of the string sent over UDP is:

	“<sendingport>,<ip of dest1>,<port of dest1>,<distance to dest1>,<ip of dest2>,
	<port of dest2>,<distance to dest2>…..,<ip of destn>,<port of destn>,
	<distance to destn>,<ip of receiving neighbour>”

	i.e. each entry in the string is separated from the other by a comma (“,”).
	The <ip of receiving neighbour> at the end of the string allows the receiving
	neighbour (say, B) to know the B’s own public IP address. B may then use this
	information and its own port number to identify its own information in the received
	distance vector.

c) A single “UPD_Listen” thread with a single listening socket that listens for incoming “DVs” 
(route updates).

d) A single “Broadcast” thread, that sends the “DV” to all the neighbours over a single UDP 
socket. 
This performs the function of “ROUTE_UPDATE”.

e) A separate “timeout” thread for EACH neighbour. 

f) “Bellman” function to update DVs.

g) “Main” portion that allows user interaction.


################################
(d) User interface
################################

#########EXAMPLE 1. SHOWRT

##When presented with the following prompt..

Terminal:
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Enter one of the following commands: 

LINKDOWN <IP> <port>    (to 'down' a link)
LINKUP <IP> <port>      (to 'restore' a link)
SHOWRT                  (to display the routing table)
CLOSE                   (to close the client)
NEWLINK                 (to add a new link)


>> 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

##.. simply enter your chosen option in the given format like so:

Terminal:
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Enter one of the following commands: 

LINKDOWN <IP> <port>    (to 'down' a link)
LINKUP <IP> <port>      (to 'restore' a link)
SHOWRT                  (to display the routing table)
CLOSE                   (to close the client)
NEWLINK                 (to add a new link)


>> SHOWRT

2015-12-04 22:04:22 Distance vector list is:

Destination = 127.0.0.1:4115, Cost = 5.0, Link = (127.0.0.1:4115)  [Destination is a NEIGHBOUR]
Destination = 127.0.0.1:4119, Cost = 5.0, Link = (127.0.0.1:4119)  [Destination is a NEIGHBOUR]
Destination = 127.0.0.1:4117, Cost = 10.0, Link = (127.0.0.1:4117)  [Destination is a NEIGHBOUR]

Your port number: 4116


Enter one of the following commands: 

LINKDOWN <IP> <port>    (to 'down' a link)
LINKUP <IP> <port>      (to 'restore' a link)
SHOWRT                  (to display the routing table)
CLOSE                   (to close the client)
NEWLINK                 (to add a new link)


>> 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


#########EXAMPLE 2. LINKDOWN

##When presented with these options..

Terminal:
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Enter one of the following commands: 

LINKDOWN <IP> <port>    (to 'down' a link)
LINKUP <IP> <port>      (to 'restore' a link)
SHOWRT                  (to display the routing table)
CLOSE                   (to close the client)
NEWLINK                 (to add a new link)


>> 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

##..enter “LINKDOWN” in the given format..

Terminal:
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Enter one of the following commands: 

LINKDOWN <IP> <port>    (to 'down' a link)
LINKUP <IP> <port>      (to 'restore' a link)
SHOWRT                  (to display the routing table)
CLOSE                   (to close the client)
NEWLINK                 (to add a new link)


>> LINKDOWN 127.0.0.1 4115
Link successfully taken down.


Enter one of the following commands: 

LINKDOWN <IP> <port>    (to 'down' a link)
LINKUP <IP> <port>      (to 'restore' a link)
SHOWRT                  (to display the routing table)
CLOSE                   (to close the client)
NEWLINK                 (to add a new link)


>> 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


#END.








 





