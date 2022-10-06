# Advanced Topics in Programming - Maze Game - Groot's Quest Parts A&B

This repository includes the **first two parts** of the final coding project created as part of the Advanced Topics In Programming course at the Ben-Gurion University of the Negev.

The course combines theoretical and practical aspects of software development for the creation of windows-based applications (AKA form-based applications) which are also integrated with databases. During the course theoretical subjects are reviewed such as classical **User Interface Design Patterns** (e.g., the Observer pattern) and also practical issues such as overview of the WindowsForms class library. The main topics that are covered include Eventbased programming, using User controls to create friendly user interfaces, creation of custom user controls, Visual inheritance, and MultiDocument Interfaces (MDI). In addition, advanced topics such as using of Webserivces, Deployment issues and Versioning will also be introduced.

## Maze Game - Groot's Quest
### General Description

The implemented appplication generates a maze according to a user’s settings. The user can then try to solve it theirselves or ask the program to solve it for them. The project is written from scratch in java 8 using IntelliJ IDEA. We implemented multiple algorithms for maze generation and maze solving, along with client-server architecture using the SOLID software design concepts.


The project included 3 phases of programing:

### First Phase – Generation of the maze
The maze is generated using a randomized variation of the Prim algorithm.

For the computation of the solution of the maze the following algorithms were implemented to solve it:
<ul>
  <li>Breadth First Search</li> 
  <li>Depth First Search</li> 
  <li>Best First Search</li>
</ul>
 

### Second Phase – Threads and Streams
The project includes a server with two seperate functions:
<ol>
  <li>Generate a maze according to the user’s settings - the client sends all the relevant parameters, the server generates the maze and sends it back to the client as an object.</li> 
  <li>Solving the maze</li> 
</ol>

To reduce the communication overheads, we used compression using the Decorator design pattern. 
The server supportes caching; if the server has to solve a maze which has already been solved in the past, it retrieves the solution from a file and returns the answer to the client without solving it again.
The client-server architecture includes a thread-pool mechanism. The server can support multiple clients in parralel.  
To set the optimal settings (number of threads, best algorithm to solve the maze, etc..) we defined a static class called Configurations.

### Third Phase – Desktop application by MVVM architecture, GUI [Repo link](https://github.com/yiftachsa/Groots_Quest-Maze_Game-Advanced_Topics_In_Programming)

<ul>
  <li>The <b>Model</b> layer is responsible for the business logic (<b>The server side</b>).</li> 
  <li>The <b>ViewModel</b> layer is responsible for the presentation logic and to mediate between the two other layers.</li> 
  <li>The <b>View</b> layer is responsible for the FXML and the UI logic (<b>The client side</b>).</li>
</ul>

Using the GUI the user can create a new maze, save the current state of the game, and load an existing game.

 
We also used Maven with Log4j2 library;indications are written to the log file such as valid input, calling to the server, details about the solution, etc..


<p align="center">
<img src="https://in.bgu.ac.il/marketing/DocLib/Pages/graphics/heb-en-arabic-logo-small.png">
</p>
