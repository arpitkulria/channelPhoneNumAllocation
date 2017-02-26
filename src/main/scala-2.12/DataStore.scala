import java.util.UUID

import scala.collection.mutable.{MutableList => MList}

/**
  * Created by arpit on 21/2/17.
  */
object DataStore {

  val channel1UUID = UUID.fromString("bf2438d6-1a76-4e2c-b1db-5714ef53fdec")
  val channel2UUID = UUID.fromString("88da8fab-99ab-4451-b695-7a4f18125973")
  val channel3UUID = UUID.fromString("e782254b-e2af-41b2-9a5f-b53e129cfe60")
  val channel4UUID = UUID.fromString("ad6cc858-ab36-4e43-bb90-32a7bc3065bd")
  val user1UUID = UUID.fromString("081b1463-64af-4908-beeb-e033102ea892")
  val user2UUID = UUID.fromString("ce358d3d-836e-4e34-a39a-5e7e54d44297")

  //TODO: DON'T USE VAR!!!
  var channels: MList[Channel] = MList(
    Channel(channel1UUID, "channel1", Some("num1")),
    Channel(channel2UUID, "channel2", Some("num1")),
    Channel(channel3UUID, "channel3", Some("num2")),
    Channel(channel4UUID, "channel4", Some("num2")))

  val users: MList[User] = MList(User(user1UUID, "user1"), User(user2UUID, "user2"))

  //TODO: DON'T USE VAR!!!
  var followings: MList[Following] = MList(
    Following(channel3UUID, user1UUID), Following(channel2UUID, user1UUID),
    Following(channel1UUID, user2UUID), Following(channel4UUID, user2UUID))

  val phoneNumbers: MList[PhoneNumber] = MList(
    PhoneNumber("num1"), PhoneNumber("num2"), PhoneNumber("num3"), PhoneNumber("num4"))

}
