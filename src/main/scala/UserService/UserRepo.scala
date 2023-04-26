package UserService

import Dao.DAO
import Model.User

import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt


class UserRepo(userDB: DAO) {

  def add(user: User): String = Await.result(userDB.add(user), 100.millisecond)

  def getById(id: UUID): String = {
    val userDetails = Await.result(userDB.getById(id), 100.millisecond)
    userDetails match {
      case Some(user) => s"id: ${user.id} ,Name: ${user.name} ,Age: ${user.age} ,Address: ${user.address} ,EmailId: ${user.emailId} ,UserType: ${user.userType}"

      case None => s"Id $id not found in the db."
    }
  }

  def getAll: List[User] = {
    val usersList = Await.result(userDB.getAll, 100.millisecond)
    usersList.toList
  }

  def updateById(id: UUID, newName: String): String = Await.result(userDB.updateById(id, newName), 100.millisecond)

  def deleteById(id: UUID): String = Await.result(userDB.deleteById(id), 100.millisecond)

  def deleteAll(): List[User] = {
    val usersList = Await.result(userDB.deleteAll(),100.millisecond)
    usersList.toList
  }
}
