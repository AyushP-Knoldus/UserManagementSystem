package Model

import java.util.UUID

case class User(
                 id: UUID,
                 name: String,
                 age: Int,
                 address: String,
                 emailId: String,
                 userType: UserType
               )
