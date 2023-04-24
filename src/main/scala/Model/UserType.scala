package Model

sealed trait UserType

object UserType {
  case object Admin extends UserType

  case object Customer extends UserType
}