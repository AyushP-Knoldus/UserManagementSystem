package UserService

import Database.UserDatabase
import org.scalatest.funsuite.AnyFunSuiteLike
import java.util.UUID
import Model.User
import Model.UserType.{Admin, Customer}
import scala.concurrent.ExecutionContext.Implicits.global

class UserRepoIntegrationTest extends AnyFunSuiteLike {

  val userDatabase = new UserDatabase
  val userRepo = new UserRepo(userDatabase)

  val user1: User = User(UUID.randomUUID(), "Ayush", 25, "Noida", "ayush.pathak@gmail.com", Admin)
  val user2: User = User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)
  userRepo.add(user1)

  test("Add a user") {
    val result = userRepo.add(user2)
    result.foreach { value =>
      assert(value == s"Added user ${user2.name} with Id-> ${user2.id} to Database.")
    }
  }

  test("Get a user based on id") {

    val result = userRepo.getById(user2.id)

    val condition = false

    result.foreach {
      case Left(_) => assert(condition)
      case Right(userdata) => assert(userdata == user2)
    }
  }

  test("Get list of users") {
    val userDatabase1 = new UserDatabase
    val userRepo1 = new UserRepo(userDatabase1)
    userRepo1.add(user1.copy(id = UUID.randomUUID()))
    userRepo1.add(user2.copy(id = UUID.randomUUID()))

    val result = userRepo1.getAll

    result.foreach {
      case Right(userList) => assert(userList == List(user1, user2))
    }
  }

  test("Update user based on id") {

    val result = userRepo.updateById(user1.id, "Rahul")

    result.foreach { value =>
      assert(value == s"Updated entry at id ->${user1.id} in Database.")
    }
  }

  test("Display message if id to be updated is not found") {

    val result = userRepo.updateById(UUID.randomUUID(), "Rahul")

    result.foreach { value =>
      assert(value == "Id not found.")
    }
  }

  test("Delete a user based on id") {

    val result = userRepo.deleteById(user1.id)

    result.foreach { value =>
      assert(value == s"Deleted entry with id ->${user1.id} from Database.")
    }
  }

  test("Display message if id to be deleted is not found") {
    val id = UUID.randomUUID()

    val result = userRepo.deleteById(id)

    result.foreach { value =>
      assert(value == s"No user found with ID $id in DB.")
    }
  }

  test("Delete all users.") {

    val result = userRepo.deleteAll()

    result.foreach { value =>
      assert(value == "Deleted All entries in table.")
    }
  }
}
