package Database

import org.scalatest.funsuite.AnyFunSuiteLike

class ConnectionTest extends AnyFunSuiteLike {

  test("Method establishConnectionWithMySql should return Some if connection is established") {
    val result = Connection.establishConnectionWithMySql()
    assert(result match {
      case Some(_) => true
      case None => false
    })
  }
}