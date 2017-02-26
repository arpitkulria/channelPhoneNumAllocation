import java.util.UUID

import scala.collection.mutable.{MutableList => MList}
import DataStore._

/**
  * Created by arpit on 26/2/17.
  */
class Helpers {

  def check(userId: UUID): Map[Option[String], MList[Channel]] = {
    val channelID: MList[UUID] = followings.filter(_.userId == userId).map(_.channelId)
    (channelID flatMap { id =>
      channels.filter(_.id == id)
    }).groupBy(_.phoneNumber).filter(_._2.length > 1)
  }


  def addFollowings(following: Following): MList[Following] = {
    followings += following
    val collisionData: Map[Option[String], MList[Channel]] = check(following.userId)
    if(collisionData.size > 0) resolve(collisionData) else (followings :+ following).distinct
  }

  def resolve(collisionData: Map[Option[String], MList[Channel]]) :MList[Following] = {

    val dataMap: Map[Option[String], MList[Channel]] = channels.groupBy(_.phoneNumber)

    val allAssignedNums: List[String] = (dataMap map {
      case (k, v) => k
    }).map(_.fold("")(identity)).toList

    val unassignedNums: MList[String] = phoneNumbers.map(_.number).diff(allAssignedNums)

    (collisionData.flatMap {
      case (num, channelList) =>
        val toBeChanged: Channel = getLessFrequencyChannel(channelList)
        channels = channels.diff(channels.filter(_.id == toBeChanged.id))

        val conflictOrNot: List[Boolean] = (dataMap map {
          case (k, v) => resolve2(toBeChanged.id, k.fold("")(identity), v)
        }).toList

        channels += toBeChanged.copy(phoneNumber = if(conflictOrNot.contains(true)) unassignedNums.headOption else num)
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

  //Mocked function to get less frequency channel
  def getLessFrequencyChannel(channels: MList[Channel]) = channels.head

}
