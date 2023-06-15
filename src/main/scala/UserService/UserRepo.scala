package UserService

import Dao.DAO
import Model.User
import java.util.UUID
import scala.concurrent.Future

class UserRepo(userDB: DAO) {

  def add(user: User): Future[String] = userDB.add(user)

  def getById(id: UUID): Future[Either[String, List[User]]] = userDB.getById(id)

  def getAll: Future[Either[String, List[User]]] = userDB.getAll

  def updateById(id: UUID, newName: String): Future[String] = userDB.updateById(id, newName)

  def deleteById(id: UUID): Future[String] = userDB.deleteById(id)

  def deleteAll(): Future[String] = userDB.deleteAll()


}
