# Networking
## Data Transfer
* Unit: bps = bits per seconds
  * network protocols are written in binary
* Model
  * shared bus: every computer connects to a single electrical wire
  * star: each computer connects to the hub via a dedicated port
  * switch: each port has its own electrical bus and **collision domain**
    * will NOT forward a frame onto a wire with a transition already happening
    * will queue the frame until ready
    * builds an **address table**
  * broadcast: address from or to is unknown
    * traffic is increased significantly
  * router: segment networks into separate **broadcast domains**
    * never forwards the broadcast packets to other network segments
    * summarizes the addresses in each segment into a **routing table**
    * goal is to find the **best path** to get closer to the destination with only **one routed hop closer each time**
* Duplex
  * half duplex: send OR receive at any given time
  * full duplex: can send and receive simutaneously
    * doubles the available bandwidth
    * ensure that collisions do not occur = switch (queue incoming frames) + full duplex devices + cabling
## Collision Detection
* CSMA/CD (Carrier Sense Multiple Access with Collision Detection): Ethernet standard mechanism
