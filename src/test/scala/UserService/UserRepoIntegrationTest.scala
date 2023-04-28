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

  test("Add a user") {
    val result = userRepo.add(user1)
    result.foreach { value =>
      assert(value == "Added user Ayush to DB.")
    }
  }

  test("Get a user based on id") {
    userRepo.add(user2)
    val result = userRepo.getById(user2.id)

    val condition = false

    result.foreach {
      case Some(value) => assert(value == user2)
      case None => assert(condition)
    }
  }

  test("Get list of users") {
    val userDatabase1 = new UserDatabase
    val userRepo1 = new UserRepo(userDatabase1)
    userRepo1.add(user1)
    userRepo1.add(user2)

    val result = userRepo1.getAll

    result.foreach { value =>
      assert(value == List(user1, user2))
    }
  }

  test("Update user based on id") {
    userRepo.add(user1)

    val result = userRepo.updateById(user1.id, "Rahul")

    result.foreach { value =>
      assert(value == s"Username updated at the id ${user1.id}")
    }
  }

  test("Display message if id to be updated is not found") {
    userRepo.add(user2)

    val result = userRepo.updateById(UUID.randomUUID(), "Rahul")

    result.foreach { value =>
      assert(value == "Id not found.")
    }
  }

  test("Delete a user based on id") {
    userRepo.add(user1)

    val result = userRepo.deleteById(user1.id)

    result.foreach { value =>
      assert(value == s"User with ID ${user1.id} deleted from DB.")
    }
  }

  test("Display message if id to be deleted is not found") {
    val id = UUID.randomUUID()
    userRepo.add(user2)

    val result = userRepo.deleteById(id)

    result.foreach { value =>
      assert(value == s"No user found with ID $id in DB.")
    }
  }

  test("Delete all users.") {
    userRepo.add(user1)
    userRepo.add(user2)

    val result = userRepo.deleteAll()

    result.foreach { value =>
      assert(value == List.empty[User])
    }
  }
}
