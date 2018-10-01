package $package$.application

import akka.cluster.sharding.ShardRegion

object ClusterShardingSupport {
  type Msg = Any
  sealed trait WithEntityId {
    def entityId: String
  }

  trait EntityEnvelop extends WithEntityId {
    def body: Msg
  }

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
    case msg: EntityEnvelop => msg.body match {
      case Some(cmd) => (msg.entityId, cmd)
      case _ => (msg.entityId, msg)
    }
  }

  /**
   * extract shard id from message
   *
   * @return
   */
  def extractShardId: ShardRegion.ExtractShardId = {
    case msg: EntityEnvelop => (msg.entityId.hashCode % numberOfShards).toString
    case ShardRegion.StartEntity(id) =>
      (id.hashCode % numberOfShards).toString
  }
}
