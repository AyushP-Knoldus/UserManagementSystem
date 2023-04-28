package UserService

import Dao.DAO
import Model.User
import scala.util.{Failure, Success}
import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class UserRepo(userDB: DAO) {

  def add(user: User): Future[String] = userDB.add(user)

  def getById(id: UUID): Future[Option[User]] = userDB.getById(id)

  def getAll: Future[List[User]] = convertListBufferToList(userDB.getAll)

  def updateById(id: UUID, newName: String): Future[String] = userDB.updateById(id, newName)

  def deleteById(id: UUID): Future[String] = userDB.deleteById(id)

  def deleteAll(): Future[List[User]] = convertListBufferToList(userDB.deleteAll())

  private def convertListBufferToList(listFuture: Future[ListBuffer[User]]): Future[List[User]] = {
    listFuture.transform {
      case Success(value) => Success(value.toList)
      case Failure(exception) => Failure(exception)
    }
  }
}
