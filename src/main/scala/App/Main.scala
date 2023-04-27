package App

import Database.UserDatabase
import Model.User
import Model.UserType.{Admin, Customer}
import UserService.UserRepo
import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object Main extends App {

  private val userDB = new UserDatabase
  private val userRepo = new UserRepo(userDB)

  //Create 2 users
  private val firstUser = User(UUID.randomUUID(), "Ayush", 23, "noida", "ayush.pathak@gmail.com", Admin)
  private val secondUser = User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)
  println(Await.result(userRepo.add(firstUser),100.millisecond))
  println(Await.result(userRepo.add(secondUser),100.millisecond))

  //List all user
  println(Await.result(userRepo.getAll, 500.millisecond))

  //Update Username of Admin
  println(Await.result(userRepo.updateById(firstUser.id, "Rahul"),500.millisecond))

  //Delete Customer
  println(Await.result(userRepo.deleteById(secondUser.id),500.millisecond))

}