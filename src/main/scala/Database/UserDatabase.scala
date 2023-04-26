package Database

import Dao.DAO
import Model.User
import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

class UserDatabase extends DAO {

  private val userList = ListBuffer.empty[User]

  def add(user: User): Future[String] = {
    Future {
      userList.addOne(user)
      s"Added user ${user.name} to DB."
    }
  }

  def getById(id: UUID): Future[Option[User]] = {
    Future {
      userList.find(_.id == id)
    }
  }

  def getAll: Future[ListBuffer[User]] = {
    Future {
      userList
    }
  }

  def updateById(id: UUID, newName: String): Future[String] = {
    Future {
      val userIndex = userList.indexWhere(_.id == id)
      val userDetails = Await.result(getById(id), 1.millisecond)

      userDetails match {
        case Some(user) => userList.update(userIndex, user.copy(name = newName))
          s"Username updated at the id $id"

        case None => "Id not found."
      }
    }
  }

  def deleteById(id: UUID): Future[String] = {
    Future {
      val userIndex = userList.indexWhere(_.id == id)

      if (userIndex != -1) {
        userList.remove(userIndex)
        s"User with ID $id deleted from DB."
      }
      else {
        s"No user found with ID $id in DB."
      }
    }
  }

  def deleteAll(): Future[ListBuffer[User]] = {
    Future {
      userList.clear()
      userList
    }
  }
}