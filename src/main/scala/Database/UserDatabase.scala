package Database

import Dao.DAO
import Database.Connection.establishConnectionWithMySql
import Model.User
import Model.UserType.{Admin, Customer}
import java.sql.ResultSet
import java.util.UUID
import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class UserDatabase extends DAO {

  private val connection = establishConnectionWithMySql()

  def add(user: User): Future[String] = {
    Future {
      handleExecuteUpdateQuery(
        executeQuery(
        s"""INSERT INTO USER
           |VALUES('${user.id.toString}','${user.name}','${user.age}','${user.address}','${user.emailId}','${user.userType.toString}')
           |""".stripMargin),s"Added user ${user.name} with Id-> ${user.id} to Database.")
    }
  }

  override def getById(id: UUID): Future[Either[String, List[User]]] = {
    Future {
      handleExecuteQuery(
        executeQuery(s"SELECT * FROM USER WHERE Id = '${id.toString}'"))
    }
  }

  def getAll: Future[Either[String, List[User]]] = {
    Future {
      handleExecuteQuery(
        executeQuery("SELECT * FROM USER"))
    }
  }

  def updateById(id: UUID, newName: String): Future[String] = {
    Future {
      handleExecuteUpdateQuery(
        executeQuery(
        s"""UPDATE USER
           | set Name = '$newName'
           | where Id = '${id.toString}'""".stripMargin), s"Updated entry at id ->$id in Database.")
    }
  }

  def deleteById(id: UUID): Future[String] = {
    Future {
      handleExecuteUpdateQuery(
        executeQuery(s"DELETE FROM USER where Id = '${id.toString}'"),
        s"Deleted entry with id ->$id from Database.")
    }
  }

  def deleteAll(): Future[String] = {
    Future {
      handleExecuteUpdateQuery(
        executeQuery("TRUNCATE TABLE USER"), s"Deleted All entries in table.")
    }
  }

  @tailrec
  private def displayUsers(resultSet: ResultSet, resultList: List[User]): List[User] = {
    if (resultSet.next()) {

      val id = UUID.fromString(resultSet.getString("Id"))
      val name = resultSet.getString("Name")
      val age = resultSet.getInt("Age")
      val address = resultSet.getString("Address")
      val emailId = resultSet.getString("EmailId")

      val userType = resultSet.getString("UserType") match {
        case "Admin" => Admin

        case "Customer" => Customer
      }
      displayUsers(resultSet, resultList :+ User(id, name, age, address, emailId, userType))
    }
    else resultList
  }

  private def executeQuery(query: String): Try[Either[Int, ResultSet]] = {
    Try {
      if (connection.isDefined) {

        val statement = connection.get.createStatement()
        val stackTrace = Thread.currentThread().getStackTrace
        val callerMethod = stackTrace(4).getMethodName

        if (callerMethod.contains("getAll") || callerMethod.contains("getById"))

          Right(statement.executeQuery(query))

        else

          Left(statement.executeUpdate(query))
      }

      else throw new Exception("Unable to connect to database")
    }
  }

  private def handleExecuteUpdateQuery(f: Try[Either[Int, ResultSet]], message: String): String = {
    f match {
      case Failure(exception) => s"${exception.getMessage}"

      case Success(value) => value match {

        case Left(_) => message
      }
    }
  }

  private def handleExecuteQuery(f: Try[Either[Int, ResultSet]]): Either[String, List[User]] = {
    f match {
      case Failure(exception) => Left(s"${exception.getMessage}")

      case Success(value) => value match {

        case Right(resultSet) => Right(displayUsers(resultSet, List.empty))
      }
    }
  }
}