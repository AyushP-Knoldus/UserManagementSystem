package UserService

import Dao.DAO
import Model.User
import java.util.UUID


class UserRepo(userDB: DAO) {

  def add(user: User): String = userDB.add(user)

  def getById(id: UUID): String = {
    userDB.getById(id) match {
      case Some(user) => s"id: ${user.id} ,Name: ${user.name} ,Age: ${user.age} ,Address: ${user.address} ,EmailId: ${user.emailId} ,UserType: ${user.userType}"

      case None => s"Id $id not found in the db."
    }
  }

  def getAll: List[User] = userDB.getAll.toList

  def updateById(id: UUID, newName: String): String = userDB.updateById(id, newName)

  def deleteById(id: UUID): String = userDB.deleteById(id)

  def deleteAll(): List[User] = userDB.deleteAll().toList
}
