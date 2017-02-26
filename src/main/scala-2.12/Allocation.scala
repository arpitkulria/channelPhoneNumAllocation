import DataStore._

/**
  * Created by arpit on 21/2/17.
  */
object Allocation {
  val helper = new Helpers

  def main(args: Array[String]): Unit = {
    println(s"channel1UUID $channel1UUID")
    println(s"channel2UUID $channel2UUID")
    println(s"channel3UUID $channel3UUID")
    println(s"channel4UUID $channel4UUID")
    println(s"user1UUID $user1UUID")
    println(s"user2UUID $user2UUID")

    println(s"Before adding collision >> $channels")
    helper.addFollowings(Following(channel2UUID, user2UUID))
    println(s"After adding collision >> $channels")
  }
}
