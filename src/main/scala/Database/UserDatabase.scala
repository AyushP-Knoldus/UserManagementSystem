package Database

import Dao.DAO
import Database.Connection.establishConnectionWithMySql
import Model.User
import Model.UserType.{Admin, Customer}
import java.sql.ResultSet
import java.util.UUID
import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserDatabase extends DAO {

  private val connection = establishConnectionWithMySql()

  def add(user: User): Future[String] = {
    Future {
      Try {
        if (connection.isDefined) {
          val statement = connection.get.createStatement()
          val rows = statement.executeUpdate(
            s"""INSERT INTO USER
               |VALUES('${user.id.toString}','${user.name}','${user.age}','${user.address}','${user.emailId}','${user.userType.toString}')
               |""".stripMargin)
          rows
        }
        else throw new Exception("Unable to connect to database")
      } match {
        case Failure(exception) => s"${exception.getMessage}"
        case Success(value) if value > 0 => s"Added user ${user.name} with Id-> ${user.id} to Database."
      }
    }
  }

  def getById(id: UUID): Future[Option[User]] = {
    Future {
      Try {
        if (connection.isDefined) {
          val statement = connection.get.createStatement()
          val resultSet = statement.executeQuery(s"SELECT * FROM USER WHERE Id = '${id.toString}'")
          displayUsers(resultSet, List.empty).headOption
        }
        else throw new Exception("Unable to connect to database")
      } match {
        case Failure(exception) => println(exception.getMessage)
          None
        case Success(value) => value
      }
    }
  }

  def getAll: Future[List[User]] = {
    Future {
      val statement = connection.get.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM USER")
      displayUsers(resultSet, List.empty)
    }
  }

  def updateById(id: UUID, newName: String): Future[String] = {
    Future {
      Try {
        if (connection.isDefined) {
          val statement = connection.get.createStatement()
          val rows = statement.executeUpdate(
            s"""UPDATE USER
               | set Name = '$newName'
               | where Id = '${id.toString}'""".stripMargin)
          rows
        }
        else throw new Exception("Unable to connect to database")
      } match {
        case Failure(exception) => s"${exception.getMessage}"
        case Success(value) if value > 0 => s"Updated entry at id ->$id in Database."
      }
    }
  }

  def deleteById(id: UUID): Future[String] = {
    Future {
      Try {
        if (connection.isDefined) {
          val statement = connection.get.createStatement()
          statement.executeUpdate(s"DELETE FROM USER where Id = '${id.toString}'")
        }
        else throw new Exception("Unable to connect to database")
      } match {
        case Failure(exception) => s"${exception.getMessage}"
        case Success(value) if value > 0 => s"Deleted entry with id ->$id from Database."
      }
    }
  }

  def deleteAll(): Future[String] = {
    Future {
      Try {
        if (connection.isDefined) {
          val statement = connection.get.createStatement()
          val rows = statement.executeUpdate("TRUNCATE TABLE USER")
          rows
        }
        else throw new Exception("Unable to connect to database")
      } match {
        case Failure(exception) => s"${exception.getMessage}"
        case Success(_) => s"Deleted All entries in table."
      }
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


}