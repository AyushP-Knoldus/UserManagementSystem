package App

import Database.UserDatabase
import Model.User
import Model.UserType.{Admin, Customer}
import UserService.UserRepo
import java.util.UUID

object Main extends App {

  private val userDB = new UserDatabase
  private val userRepo = new UserRepo(userDB)

  //Create 2 users
  private val firstUser = User(UUID.randomUUID(), "Ayush", 23, "noida", "ayush.pathak@gmail.com", Admin)
  private val secondUser = User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)
  println(userRepo.add(firstUser))
  println(userRepo.add(secondUser))

  //List all user
  println(userRepo.getAll)

  //Update Username of Admin
  println(userRepo.updateById(firstUser.id, "Rahul"))

  //Delete Customer
  println(userRepo.deleteById(secondUser.id))

}