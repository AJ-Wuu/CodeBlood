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
  * global: L3 with IP = where the ultimate destination is (**via DNS**)
    * in the format of `Src.IP | Dest.IP`
    * IP (Internet Protocol) = 32 bits, e.g. `0.1.2.255`
      * should be globally unique across the entire Internet
      * some are reserved by RFC (Request For Comment)
* Consumption
  * device: read in a full Ethernet packet during transmission
    * technically, this is a frame
    * frame and packet can be used "interchangeably", as L2 keeps changing but the rest part remains the same
  * switch: only looks at L2
  * router
     1. looks at L2, makes sure the packet is destined for it, then **removes** L2
     2. looks at L3, and figures out where to send the packet next
     3. leaves L3 **unmodified**, and places a **new** L2 back on the packet (**provided by the Address Resolution Protocol**)
  * firewall: typically looks at at least L4
    * some goes through L7

## IP Subnet
* IP = Network.Host/Subnet Mask, e.g. `[192.168.11].[22]/[24]`
  * Network ≈ Zip Code
  * Host ≈ Street Address
  * Subnet Mask specifies that the first X bits of the address refers to Network and the last (32 - X) bits refers to Host
```txt
address     = 11000000 10101000 00001011 00010110 = 192.168.11.22
              [         Network        ] [ Host ]
subnet mask = 11111111 11111111 11111111 00000000 = 255.255.255.0 = (abbreviatedly) /24
```
* Subnetwork
  * usage
    * route traffic logically to a network rather than having to know specific information for each host in there
    * create logical groups of devices
  * rule
    * within my subnet: encapsulate my L3 packet with a new L2 header to send it directly
    * beyond my subnet: must use a router, must construct a L2 header with the information for the "next hop" and prepend it to my packet
      * **default gateway** = the router we know of
  * class: **deprecated** because lack of flexibility
    * Class A = 255.0.0.0 = 1.0.0.0/8 ~ 126.255.255.255/8
    * Class B = 255.255.0.0 = 128.0.0.0/16 ~ 191.255.255.255/16
    * Class C = 255.255.255.0 = 193.0.0.0/24 ~ 223.255.255.255/24
  * range
    * **shared Network, varied Host**
    * the **lowest** in range (e.g. `.0`) is set aside for the network address
    * the **highest** in range (e.g. `.255`) is set aside for the broadcast address

## Virtual Local Area Networks (VLAN)
* Goal: any traffic (unicast, multicast or broadcast) is only allowed to be sent to other ports in the same VLAN
  * VLAN can talk to each other via a router interface
  * the router can be external of a new hardware, or internal of the same switch
  * indicated by a dedicated VLAN field in MAC address table
* Typically assign one subnet to each VLAN
  * can have multiple subnets to one VLAN though
* Usage
  * security: separate traffic so the devices don't see each other's traffic unless they first go through a router or firewall
  * segmentation: separate different types of devices
    * can use VLAN ID to **prioritize** different types of traffic for VoIP calls
    * VLAN ID is a 12 bit value with 0 and 4095 not permitted

## Trunk
* Goal: allow frames from multiple VLANs to be carried across
  * VLAN 22 in switch A can send information to VLAN 22 in switch B
* Tagging to indicate which frames belong to which VLANs
  * the other switch can add those devices VLAN membership into its own table
  * in the layer 2 portion of the packet
  * format in 802.1Q
  * can allow packets without this tag to be associated with a particular ("native", "default", or "untagged") VLAN
