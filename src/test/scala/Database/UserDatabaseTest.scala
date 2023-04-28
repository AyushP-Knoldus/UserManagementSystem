package Database

import Model.User
import Model.UserType.{Admin, Customer}
import org.scalatest.funsuite.AnyFunSuiteLike
import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global

class UserDatabaseTest extends AnyFunSuiteLike {
  val userDb = new UserDatabase
  val user1: User = User(UUID.randomUUID(), "Ayush", 25, "Noida", "ayush.pathak@gmail.com", Admin)
  val user2: User = User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)

  test("Add a user to the database") {
    val result = userDb.add(user1)

    result.foreach { value =>
      assert(value == "Added user Ayush to DB.")
    }
  }

  test("Get a user by ID from the database") {
    userDb.add(user1)
    val userDetailsFuture = userDb.getById(user1.id)

    userDetailsFuture.foreach { userDetails =>
      assert(userDetails.contains(user1))
    }
  }

  test("Get all users from the database") {
    val userDb1 = new UserDatabase
    userDb1.add(user1)
    userDb1.add(user2)
    val userListFuture = userDb1.getAll

    userListFuture.foreach { userList =>
      assert(userList == ListBuffer(user1, user2))
    }
  }

  test("Update a user by ID in the database") {
    userDb.add(user2)
    val updatedUserFuture = userDb.updateById(user2.id, "Rahul")

    updatedUserFuture.foreach { updatedUser =>
      assert(updatedUser == s"Username updated at the id ${user2.id}")
    }
  }
  test("Delete a user by ID from the database") {
    userDb.add(user2)
    val deletedUserFuture = userDb.deleteById(user2.id)

    deletedUserFuture.foreach { deletedUser =>
      assert(deletedUser == s"User with ID ${user2.id} deleted from DB.")
    }
  }

  test("Delete all users from the database") {
    val userDb1 = new UserDatabase
    userDb1.add(user1)
    userDb1.add(user2)
    val userListFuture = userDb1.deleteAll()

    userListFuture.foreach { userList =>
      assert(userList == ListBuffer.empty[User])
    }
  }
}
