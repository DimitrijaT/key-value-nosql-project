# NoSQL Java Project

Ова е проект по предметот неструктурирани бази на податоци на ФИНКИ каде што целта е да се направи споредби на Key-Value бази	на податоци

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Dependencies](#dependencies)
- [Docker Setup](#docker-setup)
- [License](#license)

## Prerequisites

- Java 17
- Maven 3.6.3 or higher
- Docker

## Installation

**Clone the repository**
   ```
   git clone https://github.com/your-repo/nosql-project.git
   cd nosql-project
   ```
   
## Dependencies

This project uses the following dependencies:

- **OpenCSV**: For CSV parsing (`com.opencsv:opencsv:4.1`)
- **Jedis**: A Redis client for Java (`redis.clients:jedis:5.0.0`)
- **Lombok**: For reducing boilerplate code (`org.projectlombok:lombok:1.18.28`)
- **Gson**: For JSON serialization and deserialization (`com.google.code.gson:gson:2.10.1`)
- **Riak Client**: For interacting with Riak (`com.basho.riak:riak-client:2.1.1`)
- **Log4j**: For logging (`org.apache.logging.log4j:log4j-api:2.20.0`, `org.apache.logging.log4j:log4j-core:2.20.0`, `org.apache.logging.log4j:log4j-slf4j-impl:2.20.0`)
- **Netty**: For network application framework (`io.netty:netty-all:4.1.63.Final`)

## Docker Setup

To start the required databases using Docker, execute the following commands:

1. **Start Riak**
   ```
   docker run --name=riak -d -p 8087:8087 -p 8098:8098 basho/riak-kv
   ```

2. **Start Redis Stack Server**
   ```
   docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest
   ```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
