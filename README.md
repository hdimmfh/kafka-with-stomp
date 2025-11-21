# Project Overview

## 0. Output Example
> Sample Result  
> ![image](https://github.com/user-attachments/assets/7a715d55-92c9-48fd-9bd6-ccbcbbb8ca26)

## 1. Architecture

This project aims to implement a real-time chat system that supports **multi-user participation**, **high availability**, and **low-latency communication**.

1. **STOMP Connection**  
   Clients connect to the server’s STOMP WebSocket endpoint.

2. **Client STOMP**  
   Clients send chat messages through the STOMP socket.

3. **Kafka Producing**  
   Messages received over the socket are forwarded to the Kafka broker.

4. **Kafka Consuming**  
   Kafka consumers continuously poll messages from the topic.

5. **STOMP Registry**  
   Polled messages are delivered to subscribed clients via STOMP channels.

6. **Session History**  
   Polled messages are also stored in session memory.

STOMP (Simple Text Oriented Messaging Protocol) enables real-time communication between clients and the server.

---

## 2. What is STOMP?

STOMP is a text‑based messaging protocol built on top of WebSocket.  
The server prepares STOMP endpoints and configuration, and clients connect to these endpoints to subscribe to topics and receive messages in real time.

STOMP alone is sufficient to implement multi‑user real‑time chat functionality, even without Kafka.

> Message flow from the client:
![Group 22](https://github.com/user-attachments/assets/f7c2ddc9-4eef-4dfb-87fb-982926b9e25a)

**So why does this project use Kafka?**

---

## 3. What is Kafka?

Kafka is a distributed streaming platform designed for high‑throughput, real‑time data pipelines.  
It categorizes data into **topics**, where producers send messages and consumers read them.

Kafka allows servers to reliably store and distribute messages across distributed systems.

> Message flow through Kafka:
![msg_flow](https://github.com/user-attachments/assets/699d531b-e92a-4d5b-b564-11b60560abdc)

By routing messages through Kafka, STOMP communication can be managed independently across distributed server nodes.

In a distributed environment, clients in the same chat room may connect to different servers, each with its own STOMP session storage. Without Kafka, clients across servers cannot receive each other’s messages.

However, by routing messages through Kafka:
- Message processing becomes server‑independent  
- All servers receive consistent chat messages via Kafka consumers  
- Clients receive the same data regardless of which server they are connected to  

> Distributed message flow across multiple nodes:
![Group 26 (1)](https://github.com/user-attachments/assets/72a86b1c-b725-4cab-baba-fc949a539be3)

In this example, the **sender** is connected to Server 1 and the **receiver** is connected to Server 2.  
Messages bypass server‑local session memory and instead flow through Kafka, enabling consistent communication across server nodes.

---

## 4. Development Timeline
- **2024.07.10** — (BE) Kafka configuration implemented  
- **2024.07.12** — (BE) STOMP configuration implemented  
- **2024.09.07** — (BE) Additional components implemented  
- **2024.09.07** — (BE) API implementation  
- **2024.09.07** — (FE) Chat UI implemented  
- **2024.09.08** — (FE) Code refactoring & device compatibility fixes  

---

## 5. Usage Guide

1. Build the project using:
   ```
   ./gradlew build
   ```

2. Before running the server, execute the `Makefile` script to start Kafka and Zookeeper.  
   If your Kafka installation path differs or if using Docker/AWS MSK, adjust the script accordingly.

3. Review `KafkaConstant` before launching the server.  
   Confirm the Kafka broker IP and other configurations.

4. To build a Docker image for deployment, review and build using the provided `Dockerfile`.

5. Access `http://localhost:8080` and test the chat application to verify correct behavior.

