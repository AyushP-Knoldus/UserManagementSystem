package Database

import com.typesafe.config.ConfigFactory

import java.sql.{Connection, DriverManager}

object Connection {
  def establishConnectionWithMySql(): Option[Connection] = {

    Class.forName("com.mysql.cj.jdbc.Driver")
    val config = ConfigFactory.load()
    val url = config.getString("database.url")
    val username = config.getString("database.username")
    val password = config.getString("database.password")

    Some(DriverManager.getConnection(url, username, password))
  }
}
