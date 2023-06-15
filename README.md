
# User Management System


---

The project consists of two different types of users :-  
1-Customer  
2-Admin  

## User
The user data is passed in the form of case class which consists of the following fields :-
```
case class User(
                 id: UUID,
                 name: String,
                 age: Int,
                 address: String,
                 emailId: String,
                 userType: UserType
               )
```

The users are provided functionality to perform CRUD operations on   
the database (We have used ListBuffer to imitate database).

The project contains 3 layers which include ->  
1-[Database layer](#1.Database-layer)  
2-[Service layer](#2.Service-layer)  
3-[Application layer](#3.Application-layer)  
  

## 1.Database layer

The layer contains implementation of DAO trait which performs CRUD  
operations (Create, Read, Update, Delete) on the ListBuffer. This layer  
is where actual communication between database takes place. We have also  
added a delay in each method to imitate the actual working of databse calls  
and have wrapped it up in Future.  

The follwing methods are implemented in this layer ->  
```
  def add(user: User): Future[String]

  def getById(id: UUID): Future[Option[User]]

  def getAll: Future[ListBuffer[User]]

  def updateById(id: UUID, newName: String): Future[String]

  def deleteById(id: UUID): Future[String]

  def deleteAll(): Future[ListBuffer[User]] 
```

## 2.Service layer

This layer provides a layer of abstraction to our database. It is used to  
call methods from our database layer. Generally all the validations are performed in this layer itself. The return type of the methods in service layer are same as that of the database layer ie; wrapped in Future. For instance , if the call to add method of service layer is made , its inner working would be -
```
def add(user: User): Future[String] = userDB.add(user)
```

## 3.Application layer

This is the last layer and the one which interacts with the user. The calls to service layer methods can be made using this layer. The result of the method calls are then displayed with the help of loggers. The Await method is purposedly used to wait for the future to complete.

If we need to add 2 different types of user ie; Admin and customer we will create the object of Database class and pass it as constructor injection to the object of UserRepo class(Service layer). The add method is then called with the User as the parameter.
```
  private val userDB = new UserDatabase
  private val userRepo = new UserRepo(userDB)

  private val firstUser = User(UUID.randomUUID(), "Ayush", 23, "noida", "ayush.pathak@gmail.com", Admin)
  private val secondUser = User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)
  logger.info(Await.result(userRepo.add(firstUser), 100.millisecond))
  logger.info(Await.result(userRepo.add(secondUser), 100.millisecond))
```

The  method would return -
```
[main] INFO App.Main$ - Added user Ayush to DB.
[main] INFO App.Main$ - Added user Sachin to DB.
```

## Getting Started

### Requirements
```
scala version "2.13.10" or higher
sbt version "1.8.2" or higher
```
### Running the project
The following instructions are for linux based systems.  
Open the terminal and execute the following commands

#### Clone Repository
```
git clone https://github.com/AyushP-Knoldus/UserManagementSystem.git
```
#### Open Project Directory
```
cd UserManagementSystem
```
#### Run Project
```
sbt run
```
#### Test Project
```
sbt test
```
#### Code Coverage
To check the code coverage of the test cases execute the following commands -
```
sbt clean coverage test
sbt coverageReport
```
The `sbt coverageReport` will generate an elaborative coverage report for the  
given project in directory `UserManagementSystem/target/scala-2.13/scoverage-report`.


