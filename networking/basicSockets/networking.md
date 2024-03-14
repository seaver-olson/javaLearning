# Java.net Package

## Low Level API

- Deals with:
  - Addresses: network identifiers
    - IP Addresses: numerical label assigned to each device connected to a computer network that uses the Internet Protocol
      - IPV4(32 bits) Example: 192.168.0.0
      - IPV6(128 bits) Example: FE80::0202:B3FF:FE1E:8329
  - Sockets: bidirectional data communication mechanisms
    - Input and output streams are used to send and receive data.
  - Interfaces: network interfaces
    - A point of interconnection between a computer and a network
      - Hardware interface: Network Interface Card
      - Software interface: Loopback Interface

## High Level API

- Deals with:
  - URI: Universal Resource Identifiers
    - A sequence of characters and numbers that represent a logical or physical resource.
  - URL: Universal Resource Locators
    - A reference to the web resource that specifies its location on a network, a special kind of URI.
  - Connections: connections to the resource pointed to by the URL

## DNS - Domain Name System

- A system that has a database of public IP addresses and associated hostnames, can be viewed as a phonebook.
- When you want to find a webpage, the DNS is able to locate the location and name of the place you want to go to and send your request to them.

## Port Number

- A number that specifies a specific process or type of network service within a host.
- Range: 0 - 65535
- Some commonly used ports:
  - FTP: 21
  - SSH: 22
  - HTTP: 80 or 8080
  - HTTPS: 443

## Client Server Interaction

- Communication between hosts is two-way but the two hosts usually have different roles, i.e., client and server.
  - Note: These roles are interchangeable.
- Server registers on a known port with the host IP address.
- Server then waits for client to make request through listening for incoming connections.
- Client sends request, waits for server response, then establishes connection if request approved.
- Server responds with requested resource.
