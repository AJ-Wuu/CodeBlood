# Networking
## Data Transfer
* Unit: bps = bits per seconds
  * network protocols are written in binary
* Duplex
  * half duplex: send OR receive at any given time
  * full duplex: can send and receive simutaneously
    * doubles the available bandwidth
* Connection Topology
  * shared bus: every computer connects to a single electrical wire
  * star: each computer connects to the hub via a dedicated port
  * switch: each port has its own electrical bus and **collision domain**
    * will NOT forward a frame onto a wire with a transition already happening
    * will queue the frame until ready
    * builds an **address table**
    * **CSMA/CD** (Carrier Sense Multiple Access with Collision Detection) is an Ethernet standard mechanism for collision detection
      * ensure that collisions do not occur = switch (queue incoming frames) + full duplex devices + cabling
  * broadcast: address from or to is unknown
    * traffic is increased significantly
  * router: segment networks into separate **broadcast domains**
    * never forwards the broadcast packets to other network segments
    * summarizes the addresses in each segment into a **routing table**
    * goal is to find the **best path** to get closer to the destination with only **one routed hop closer each time**

## Packet - the core unit of network data transmission
* OSI Model (theoretical only)
  * L7 - Application: HTTP, SSH, Telnet, L7 Firewalls
  * L6 - Presentation
  * L5 - Session
  * L4 - Transport: TCP, UDP, L4 Firewalls
  * L3 - Network: IP, Routers
  * L2 - Data Link: Ethernet, Switches
  * L1 - Physical: Hardware, Copper, Fiber
* TCP/IP Model (real-world)
  * L7 - Application: HTTP, SSH, Telnet, L7 Firewalls
  * L4 - Transport: TCP, UDP, L4 Firewalls
  * L3 - Network / Internet: IP, Routers
  * L1 & L2 - Network Access & Network Interface: Hardware, Copper, Fiber, Ethernet, Switches
* Layer
  * split by goal
    * **payload** = L7 = the actual information
    * **header** = L1 ~ L6 = the connection information
  * split by terminology
    * frame = L2 + L3 + L4 + L7
    * packet = L3 + L4 + L7
    * segment = L4 + L7
* Address
  * local: L2 with Ethernet = where to go next
    * in the format of `Dest.MAC | Src.MAC`
    * MAC (Media Access Control) = 48 bits, e.g. `00:11:22:AA:BB:CC`
      * first half (`00:11:22`) is the OUI assigned to a manufacturer of network equipment
      * second half (`AA:BB:CC`) is given out by that vendor and put on each network interface
      * intended to be globally unique even for local addressing
  * global: L3 with IP = where the ultimate destination is
    * in the format of `Src.IP | Dest.IP`
    * IP (Internet Protocol) = 32 bits, e.g. `0.1.2.255`
      * should be globally unique across the entire Internet
      * some are reserved by RFC (Request For Comment)
* Consumption
  * device: read in a full Ethernet packet (technically, a frame) during transmission
  * switch: only looks at L2
  * router
     1. looks at L2, makes sure the packet is destined for it, then **removes** L2
     2. looks at L3, and figures out where to send the packet next
     3. leaves L3 **unmodified**, and places a **new** L2 back on the packet
  * firewall: typically looks at at least L4
    * some goes through L7
