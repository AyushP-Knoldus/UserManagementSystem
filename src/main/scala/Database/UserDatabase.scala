package Database

import Dao.DAO
import Model.User
import java.util.UUID
import scala.collection.mutable.ListBuffer

class UserDatabase extends DAO {

  private val userList = ListBuffer.empty[User]

  def add(user: User): String = {
    userList.addOne(user)
    s"Added user ${user.name} to DB."
  }

  def getById(id: UUID): Option[User] = {
    userList.find(_.id == id)
  }

  def getAll: ListBuffer[User] = {
    userList
  }

  def updateById(id: UUID, newName: String): String = {

    val userIndex = userList.indexWhere(_.id == id)

    getById(id) match {
      case Some(user) => userList.update(userIndex, user.copy(name = newName))
        s"Username updated at the id $id"

      case None => "Id not found."
    }
  }

  def deleteById(id: UUID): String = {

    val userIndex = userList.indexWhere(_.id == id)

    if (userIndex != -1) {
      userList.remove(userIndex)
      s"User with ID $id deleted from DB."
    }
    else {
      s"No user found with ID $id in DB."
    }
  }

  def deleteAll(): ListBuffer[User] = {
    userList.clear()
    userList
  }
}
