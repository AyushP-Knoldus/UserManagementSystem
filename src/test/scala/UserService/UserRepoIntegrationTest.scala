package UserService

import Database.UserDatabase
import org.scalatest.funsuite.AnyFunSuiteLike
import java.util.UUID
import Model.User
import Model.UserType.{Admin, Customer}

class UserRepoIntegrationTest extends AnyFunSuiteLike {

  val userDatabase = new UserDatabase
  val userRepo = new UserRepo(userDatabase)

  val user1: User = User(UUID.randomUUID(), "Ayush", 25, "Noida", "ayush.pathak@gmail.com", Admin)
  val user2: User = User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)

  test("Add a user") {
    assert(userRepo.add(user1) == "Added user Ayush to DB.")
  }

  test("Get a user based on id") {
    userRepo.add(user2)

    assert(userRepo.getById(user2.id) == s"id: ${user2.id} ,Name: Sachin ,Age: 21 ,Address: Delhi ,EmailId: sachin.sharma@gmail.com ,UserType: Customer")
  }

  test("Get list of users") {
    val userDatabase1 = new UserDatabase
    val userRepo1 = new UserRepo(userDatabase1)
    userRepo1.add(user1)
    userRepo1.add(user2)

    assert(userRepo1.getAll == List(user1, user2))
  }

  test("Update user based on id") {
    userRepo.add(user1)

    assert(userRepo.updateById(user1.id, "Rahul") == s"Username updated at the id ${user1.id}")
  }

  test("Display message if id to be updated is not found") {
    userRepo.add(user2)

    assert(userRepo.updateById(UUID.randomUUID(), "Rahul") == "Id not found.")
  }

  test("Delete a user based on id") {
    userRepo.add(user1)

    assert(userRepo.deleteById(user1.id) == s"User with ID ${user1.id} deleted from DB.")
  }

  test("Display message if id to be deleted is not found") {
    val id = UUID.randomUUID()
    userRepo.add(user2)

    assert(userRepo.deleteById(id) == s"No user found with ID $id in DB.")
  }

  test("Delete all users.") {
    userRepo.add(user1)
    userRepo.add(user2)

    assert(userRepo.deleteAll() == List.empty[User])

  }

}
