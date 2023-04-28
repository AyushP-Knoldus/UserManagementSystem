package UserService

import Database.UserDatabase
import org.scalatest.funsuite.AnyFunSuiteLike
import org.mockito.MockitoSugar._
import Model.User
import Model.UserType.{Admin, Customer}
import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserRepoUnitTest extends AnyFunSuiteLike {

  val mockUserDatabase: UserDatabase = mock[UserDatabase]
  val userRepo = new UserRepo(mockUserDatabase)
  val id: UUID = UUID.randomUUID()

  test("Add user to database") {

    val user = User(UUID.randomUUID(), "Ayush", 25, "Noida", "ayush.pathak@gmail.com", Admin)
    when(mockUserDatabase.add(user)).thenReturn(Future("Added user Ayush to DB."))

    val result = userRepo.add(user)
    result.foreach { value =>
      assert(value == "Added user Ayush to DB.")
      verify(mockUserDatabase).add(user)
    }
  }

  test("Get a user based on id from database") {

    val user = User(id, "Ayush", 25, "Noida", "ayush.pathak@gmail.com", Admin)
    when(mockUserDatabase.getById(id)).thenReturn(Future(Some(user)))

    userRepo.add(user)
    val result = userRepo.getById(user.id)

    val condition = false
    result.foreach {
      case Some(value) => assert(value == user)
      case None => assert(condition)
    }
    verify(mockUserDatabase).getById(id)
  }

  test("Get the list of users from database.") {

    val user = ListBuffer(
      User(UUID.randomUUID(), "Ayush", 23, "noida", "ayush.pathak@gmail.com", Admin),
      User(UUID.randomUUID(), "Sachin", 21, "Delhi", "sachin.sharma@gmail.com", Customer)
    )
    when(mockUserDatabase.getAll).thenReturn(Future(user))

    val result = userRepo.getAll
    result.foreach { value =>
      assert(value == user.toList)
    }
    verify(mockUserDatabase).getAll
  }

  test("Update user based on Id from database.") {

    val nameToBeUpdated = "Rahul"
    when(mockUserDatabase.updateById(id, nameToBeUpdated)).thenReturn(Future(s"Username updated at the id $id"))

    val result = userRepo.updateById(id, nameToBeUpdated)

    result.foreach { value =>
      assert(value == s"Username updated at the id $id")
    }
    verify(mockUserDatabase).updateById(id, nameToBeUpdated)
  }

  test("Delete a user based on Id from the database.") {

    when(mockUserDatabase.deleteById(id)).thenReturn(Future(s"User with ID $id deleted from DB."))

    val result = userRepo.deleteById(id)

    result.foreach { value =>
      assert(value == s"User with ID $id deleted from DB.")
    }
    verify(mockUserDatabase).deleteById(id)
  }

  test("Delete all the users from the database.") {
    when(mockUserDatabase.deleteAll()).thenReturn(Future(ListBuffer.empty[User]))

    val result = userRepo.deleteAll()

    result.foreach { value =>
      assert(value == List.empty[User])
    }
    verify(mockUserDatabase).deleteAll()
  }
}
