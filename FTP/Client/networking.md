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

## DNS - Domain Na