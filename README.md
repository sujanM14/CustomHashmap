# CustomHashmap

## Overview

`CustomHashmap` is a Spring Boot application that implements a custom hashmap with basic functionalities such as initialization, data insertion, retrieval, deletion, and dumping the current state of the hashmap. This project serves as a demonstration of how to build a simple data structure using Spring Boot and Java.


### Goal

Our aim is to create a serializable hashmap that allows for reading and writing to a file. This enables the hashmap's state to be easily stored and retrieved, facilitating persistence across application restarts and enhancing usability in real-world applications.

## Features

- Initialize the hashmap with a specified page size and number of pages.
- Insert key-value pairs into the hashmap.
- Retrieve values based on keys.
- Delete key-value pairs from the hashmap.
- Dump the current state of the hashmap.

## Technologies Used

- Java
- Spring Boot
- Maven

## Getting Started

### Prerequisites

- Java 17
- Maven

### Installation

1. Clone the repository:
   cd CustomHashmap
2. Build the project:
   mvn clean install
3. Run the application:
   mvn spring-boot:run
4. To run the test cases, execute:
   mvn test

