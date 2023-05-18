package Dao

import Model.User
import java.util.UUID
import scala.concurrent.Future

trait DAO {
  def add(user: User): Future[String]

  def getById(id: UUID): Future[Option[User]]

  def getAll: Future[List[User]]

  def updateById(id: UUID, newName: String): Future[String]

  def deleteById(id: UUID): Future[String]

  def deleteAll(): Future[String]

}
