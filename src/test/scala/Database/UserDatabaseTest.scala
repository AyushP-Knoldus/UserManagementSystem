package Database

import Model.User
import Model.UserType.{Admin, Customer}
import org.scalatest.funsuite.AnyFunSuiteLike
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global

class UserDatabaseTest extends AnyFunSuiteLike {

  val userDb = new UserDatabase
  val user1: User = User(UUID.randomUUID(), "Ayush", 25, "Noida", "ayush.pathak@gmail.com", Admin)
  val user2: User = User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)

  userDb.add(user1)

  test("Add a user to the database") {
    val result = userDb.add(user2)

    result.foreach { value =>
      assert(value == s"Added user ${user2.name} with Id-> ${user2.id} to Database.")
    }
  }

  test("Get a user by ID from the database") {
    val userDetailsFuture = userDb.getById(user1.id)

    userDetailsFuture.foreach { userDetails =>
      assert(userDetails.contains(user1))
    }
  }

  test("Get all users from the database") {
    val userListFuture = userDb.getAll

    userListFuture.foreach { userList =>
      assert(userList == List(user1, user2))
    }
  }

  test("Update a user by ID in the database") {
    val updatedUserFuture = userDb.updateById(user2.id, "Rahul")

    updatedUserFuture.foreach { updatedUser =>
      assert(updatedUser == s"Username updated at the id ${user2.id}")
    }
  }
  test("Delete a user by ID from the database") {
    val deletedUserFuture = userDb.deleteById(user2.id)

    deletedUserFuture.foreach { deletedUser =>
      assert(deletedUser == s"User with ID ${user2.id} deleted from DB.")
    }
  }

  test("Delete all users from the database") {
    val result = userDb.deleteAll()

    result.foreach { element =>
      assert(element == "Deleted All entries in table.")
    }
  }
}
