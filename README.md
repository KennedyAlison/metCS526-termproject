# metCS526-termproject
Java implementation of a small computer system process scheduler simulation 

# Project Overview 
This project is an implementation of a small simulation program. It is a simulation of a process scheduler of a computer system. This simulated scheduler is a very small, simplified version which reflects some of the basic operations of a typical process scheduler. This project focuses on learning how to define and use a **user-defined comparator**, use **Java's Priority Queue**, and how to write a **small simulation program**.

The scheduling system that is simulated will work as follows:
- Processes arrive at a computer system and the computer system executes the processes one at a time based on a priority criterion. Each process has a *process_id*, *priority*, *arrival time*, and *duration*. The *duration* of a process is the amount of time it takes to completely execute the process. The system keeps a priority queue to keep arriving processes and prioritize the execution of processes. When a process arrives, it is inserted into the priority queue. Then, each time the system is ready to execute a process, the system removes a process with the **_smallest priority_** from the priority queue and executes it for the *duration* of the process. Once the system starts executing a process, it finishes the execution of the process completely without any interruption. 
- Suppose the a process *p* with a very large *priority* arrives at the system while the system is executing another process. While *p* is waiting in the queue, another process *q* with a smaller *priority* arrives. After the execution of the current process is finished, the system will remove and execute *q* (because *q* has a smaller *priority*), and *p* must wait until *q* finished. If another process with a smaller *priority* arrives while *q* is being executed, *p* will have to wait again after *q* is completed. If this is repeated, *p* will have to wait for a very long time. This can be prevented as follows: If a process has waited longer than a predetermined maximum wait time,the *priority* of the process is updated by decreasing the priority by one. For example, if the current priority of the process 5, then it is updated to 4. Technically, we have to perform this update at each (logical) time. To make the simulation program simple, this update will be done only when the system finishes the execution of a process. 

# Assumptions 
- We use the priority queue implemented in Java's *PriorityQueue*
- Each entry in the priority queue keeps a *process* object, which represents a process
- Each process must have, at the minimum the following attributes:
  - _pr:_ Integer              // priority of the process
  - _id:_ integer              // process id
  - _arrivalTime:_ integer     // the time when the process arrives at the system 
  - _duration:_ integer        // execution of the process takes this amount of time 
- The simulation program uses a logical time to keep track of the simulation process and the same logical time is used to represent the _arrivalTime_ and _duration_. The simulation goes through a series of iterations and each iteration represents the passage of one logical time unit. At the beginning, the current time is set to time 0. Each iteration implements what occurs during one time unit and, at the end of each iteration, the current time is incremented 

# Class descriptions
- _Process.java:_
  - A separate class defined for Process objects that have the following attributes:
      - _pr:_ integer - priority of the Process
      - _id:_ integer - Process id
      - _arrivalTime:_ integer - the time when the Process arrives at the system
      - _duration:_ integer - execution of the Process takes this amount of time 
  - Contains both get and set methods to retrieve attributes of each Process object as well as set them
  - Implements Comparable<Process> with overridden compareTo method allowing the redefintion of sorting to be based on the arrivalTime associated to each Process. This will help when identifying the minimum Process in the _ProcessScheduling_ class to insert into the Priority Queue
- _ProcessComparator.java_
  - Implements a Comparator interface for Process objects to be used to override the default priority given to Process objects are prioritized within the Priority Queue of the _ProcessScheduling_ class 
  - Contains overridden compare method to override the default priority to now compare the priority attribute of each Process Object
- _ProcessScheduling.java_
  - Main Process scheduling file taking an input file of Process information
  - Creates Process objects based on Process class definitions and loads them into an ArrayList
  - Creates Comparator from _ProcessComparator_ class definition 
  - Creates a PriorityQueue of Process objects using Process comparator 
  - Takes items from the ArrayList of Process objects based on their arrivalTime and places them into the Priority Queue based on their priority 
  - Outputs data to output file detailing sequencing, timing, and priority of objects added and removed to PriorityQueue
