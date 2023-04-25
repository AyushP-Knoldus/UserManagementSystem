package Database

import Model.User
import Model.UserType.{Admin, Customer}
import org.scalatest.funsuite.AnyFunSuiteLike
import java.util.UUID
import scala.collection.mutable.ListBuffer

class UserDatabaseTest extends AnyFunSuiteLike {
  val userDb = new UserDatabase
  val user1: User = User(UUID.randomUUID(), "Ayush", 25, "Noida", "ayush.pathak@gmail.com", Admin)
  val user2: User = User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)

  test("Add a user to the database") {
    assert(userDb.add(user1) == "Added user Ayush to DB.")
  }

  test("Get a user by ID from the database") {
    userDb.add(user1)

    assert(userDb.getById(user1.id).contains(user1))
  }

  test("Get all users from the database") {
    val userDb1 = new UserDatabase
    userDb1.add(user1)
    userDb1.add(user2)

    assert(userDb1.getAll == ListBuffer(user1, user2))
  }

  test("Update a user by ID in the database") {
    userDb.add(user2)

    assert(userDb.updateById(user2.id, "Rahul") == s"Username updated at the id ${user2.id}")
  }

  test("Delete a user by ID from the database") {
    userDb.add(user2)

    assert(userDb.deleteById(user2.id) == s"User with ID ${user2.id} deleted from DB.")
  }

  test("Delete all users from the database") {
    val userDb1 = new UserDatabase
    userDb1.add(user1)
    userDb1.add(user2)

    assert(userDb1.deleteAll() == ListBuffer.empty[User])
  }
}
