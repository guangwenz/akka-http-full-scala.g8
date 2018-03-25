package $package$.application

import akka.cluster.sharding.ShardRegion

object ClusterShardingSupport {

  sealed trait WithEntityId {
    def entityId: Int
  }

  trait EntityEnvelop extends WithEntityId

}

/**
  * Cluster sharding common functions
  */
trait ClusterShardingSupport {

  import ClusterShardingSupport._

  /**
    * Number of shard for this type
    *
    * @return
    */
  def numberOfShards: Int = 100

  /**
    * shard type name
    *
    * @return
    */
  def shardTypeName: String

  /**
    * extract entity id from message
    *
    * @return
    */
  def extractEntityId: ShardRegion.ExtractEntityId = {
    case msg: EntityEnvelop => (msg.entityId.toString, msg)
  }

  /**
    * extract shard id from message
    *
    * @return
    */
  def extractShardId: ShardRegion.ExtractShardId = {
    case msg: EntityEnvelop => (msg.entityId % numberOfShards).toString
    case ShardRegion.StartEntity(id) =>
      (id.toLong % numberOfShards).toString
  }
}