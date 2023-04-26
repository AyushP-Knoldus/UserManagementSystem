package Dao

import Model.User

import java.util.UUID
import scala.collection.mutable.ListBuffer

trait DAO {
  def add(user: User): String

  def getById(id: UUID): Option[User]

  def getAll: ListBuffer[User]

  def updateById(id: UUID, newName: String): String

  def deleteById(id: UUID): String

  def deleteAll(): ListBuffer[User]

}
