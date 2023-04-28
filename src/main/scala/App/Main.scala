package App

import Database.UserDatabase
import Model.User
import Model.UserType.{Admin, Customer}
import UserService.UserRepo
import org.slf4j.{Logger, LoggerFactory}
import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object Main extends App {

  private val userDB = new UserDatabase
  private val userRepo = new UserRepo(userDB)
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  //Create 2 users
  private val firstUser = User(UUID.randomUUID(), "Ayush", 23, "noida", "ayush.pathak@gmail.com", Admin)
  private val secondUser = User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)
  logger.info(Await.result(userRepo.add(firstUser), 100.millisecond))
  logger.info(Await.result(userRepo.add(secondUser), 100.millisecond))

  //List all user
  logger.info(Await.result(userRepo.getAll, 500.millisecond).toString())

  //Update Username of Admin
  logger.info(Await.result(userRepo.updateById(firstUser.id, "Rahul"), 500.millisecond))

  //Delete Customer
  logger.info(Await.result(userRepo.deleteById(secondUser.id), 500.millisecond))

}