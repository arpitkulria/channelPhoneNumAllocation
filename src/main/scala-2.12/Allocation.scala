import java.util.UUID

/**
  * Created by arpit on 21/2/17.
  */
object Allocation {

  def main(args: Array[String]): Unit = {
    println(DataStore.check(DataStore.user2UUID))

    DataStore.addFollowings(Following(DataStore.channel2UUID, DataStore.user2UUID))
    println(s"ANSSS >>>> ${DataStore.followings}")
  }
}


case class Channel(id: UUID, name: String, phoneNumber: Option[String])
case class User(id: UUID, name: String)
case class Following(channelId: UUID, userId: UUID)
case class PhoneNumber(number: String)
