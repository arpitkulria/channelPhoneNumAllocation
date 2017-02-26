import java.util.UUID

import scala.collection.mutable.{MutableList => MList}

/**
  * Created by arpit on 21/2/17.
  */
object DataStore {
  val channel1UUID = UUID.randomUUID
  val channel2UUID = UUID.randomUUID
  val channel3UUID = UUID.randomUUID
  val channel4UUID = UUID.randomUUID
  val user1UUID = UUID.randomUUID
  val user2UUID = UUID.randomUUID

  val channels: MList[Channel] = MList(
    Channel(channel1UUID, "channel1", Some("num1")),
    Channel(channel2UUID, "channel2", Some("num1")),
    Channel(channel3UUID, "channel3", Some("num2")),
    Channel(channel4UUID, "channel4", Some("num2")))

  val users: MList[User] = MList(User(user1UUID, "user1"), User(user2UUID, "user2"))

  val followings: MList[Following] = MList(
    Following(channel3UUID, user1UUID), Following(channel2UUID, user1UUID),
    Following(channel1UUID, user2UUID), Following(channel4UUID, user2UUID)/*,
    Following(channel2UUID, user2UUID)*/)

  val phoneNumbers: MList[PhoneNumber] = MList(
    PhoneNumber("num1"), PhoneNumber("num2"), PhoneNumber("num3"))


  def check(userId: UUID  ): Map[Option[String], MList[Channel]] = {
    val channelID: MList[UUID] = followings.filter(_.userId == userId).map(_.channelId)
    val data = (channelID flatMap { id =>
      channels.filter(_.id == id)
    }).groupBy(_.phoneNumber).filter(_._2.length > 1)
    println(s"DATA >>> ${data}")
    data
  }

  def addFollowings(following: Following): MList[Following] = {

    println(s"channel1UUID $channel1UUID")
    println(s"channel2UUID $channel2UUID")
    println(s"channel3UUID $channel3UUID")
    println(s"channel4UUID $channel4UUID")
    println(s"user1UUID $user1UUID")
    println(s"user2UUID $user2UUID")

    println("before resolve >>>>>>>>" + followings :+ following)

    followings :+ following
    val collisionData: Map[Option[String], MList[Channel]] = check(following.userId)
    if(collisionData.size > 0) resolve(collisionData) else (followings :+ following).distinct
  }

  def resolve(collisionData: Map[Option[String], MList[Channel]]) :MList[Following] = {

    val dataMap: Map[Option[String], MList[Channel]] = channels.groupBy(_.phoneNumber)

    val allAssignedNums: List[String] = (dataMap map {
      case (k, v) => k
    }).map(_.fold("")(identity)).toList

    val unassigned: MList[String] = phoneNumbers.map(_.number).diff(allAssignedNums)

    (collisionData.flatMap {
      case (k, v) =>

        val toBeChanged: Channel = getLessFrequencyChannel(v)
        channels.diff(channels.filter(_.id == toBeChanged.id))

        val conflictOrNot: List[Boolean] = (dataMap map {
          case (k, v) => resolve2(toBeChanged.id, k.fold("")(identity), v)
        }).toList

        channels :+ toBeChanged.copy(phoneNumber = if(conflictOrNot.contains(true)) unassigned.headOption else k)
    }).toList

    followings
  }


  def resolve2(ch1: UUID, num: String, channels: MList[Channel]) = {
    var conflictOrNot: Boolean = false
    channels map { channel =>
      if(followings.filter(_.channelId == channel.id).map(_.channelId).contains(ch1)) {
        conflictOrNot = true
      }
    }
    conflictOrNot
  }


  //Moked function to get less frequency channel
  def getLessFrequencyChannel(channels: MList[Channel]) = channels.head
}
