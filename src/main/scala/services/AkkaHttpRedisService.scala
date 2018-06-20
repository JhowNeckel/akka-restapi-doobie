//package services
//
//import akka.actor.{ActorRef, ActorSystem}
//import akka.event.{Logging, LoggingAdapter}
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
//import akka.http.scaladsl.model.StatusCodes.{InternalServerError, NoContent, OK}
//import akka.http.scaladsl.server.Directives._
//import akka.http.scaladsl.server.Route
//import akka.http.scaladsl.server.directives.Credentials
//import akka.pattern.ask
//import akka.stream.{ActorMaterializer, Materializer}
//import akka.util.Timeout
//import com.typesafe.config.{Config, ConfigFactory}
//import impl.RedisRepoImpl
//import model.{UpsertRequest, UserHandler, UserPwd}
//import model.UserHandler._
//import redis.RedisClient
////import reposiory.{ConcreteRedis, RedisRepoImpl}
//import spray.json.DefaultJsonProtocol
//
//import scala.concurrent.{ExecutionContextExecutor, Future}
//
//import scala.concurrent.{ExecutionContextExecutor, Future}
//
//trait Protocols extends DefaultJsonProtocol {
//  implicit val delUserFormat = jsonFormat1(UserDeleted.apply)
//  implicit val uNotFoundFormat = jsonFormat1(UserNotFound.apply)
//  implicit val idFormat = jsonFormat1(UserPwd.apply)
//  implicit val usrFormat = jsonFormat2(User.apply)
//  implicit val userLogin = jsonFormat2(UpsertRequest.apply)
//}
//
//trait Service extends Protocols {
//
//  import scala.concurrent.duration._
//
//  implicit val system: ActorSystem
//  implicit val materializer: Materializer
//
//  implicit def executor: ExecutionContextExecutor
//
//  val logger: LoggingAdapter
//
//  def config: Config
//
//  def userHandler: ActorRef
//
//  implicit def requestTimeout = Timeout(5 seconds)
//
//  def userAuthenticate(credentials: Credentials): Future[Option[UserPwd]] = {
//    credentials match {
//      case p@Credentials.Provided(userName) =>
//        fetchUserId(userName).map {
//          case Some(UserPwd(id)) if p.verify(id) =>
//            Some(UserPwd(id))
//          case _ => None
//
//        }
//      case _ =>
//        Future.successful(None)
//    }
//
//  }
//
//  def fetchUserId(userName: String): Future[Option[UserPwd]] = {
//
//    (userHandler ? GetUser(userName)).map {
//      case User(_, p) => Some(UserPwd(p))
//      case _ => None
//    }
//  }
//
//  val unsecuredRoutes: Route = {
//    pathPrefix("api/user") {
//      path("register") {
//        put {
//          entity(as[UpsertRequest]) { u =>
//            complete {
//              (userHandler ? UserHandler.Register(u.username, u.password)).map {
//                case true => OK -> s"Thank you ${u.username}" //plain text response
//                case _ => InternalServerError -> "Failed to complete your request. please try later"
//              }
//            }
//          }
//        }
//      }
//    }
//  }
//
//  val routes: Route = pathPrefix("api/user") {
//    path(Segment) { id =>
//      get {
//        complete {
//          (userHandler ? GetUser(id)).mapTo[User]
//        }
//      }
//    } ~
//      path(Segment) { id =>
//        post {
//          entity(as[UpsertRequest]) { u =>
//            complete {
//              (userHandler ? UserHandler.Update(u.username, u.password)).map {
//                case false => InternalServerError -> s"Could not update user $id"
//                case _ => NoContent -> ""
//              }
//            }
//          }
//        }
//      }
//  }
//}
//
//object AkkaHttpRedisService extends App with Service {
//  override implicit val system = ActorSystem()
//  override implicit val executor = system.dispatcher
//  override implicit val materializer = ActorMaterializer()
//  val prodDb = new RedisRepoImpl {
//    override def db = RedisClient()
//  }
//
//  override val config = ConfigFactory.load()
//  override val logger = Logging(system, getClass)
//  val userHandler = system.actorOf(UserHandler.props(prodDb))
//
//  Http().bindAndHandle(unsecuredRoutes, config.getString("http.interface"), config.getInt("http.port"))
//}
