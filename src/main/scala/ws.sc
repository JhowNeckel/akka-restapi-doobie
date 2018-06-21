import shapeless._
import shapeless.{HList, ::, HNil}


// -------------------
//     PRODUCTS
// -------------------

case class IceCream(name: String, numCherries: Int, inCone: Boolean)

val iceCreamGen = Generic[IceCream]

// -------------------
//    COPRODUCTS
// -------------------

sealed trait Shape
final case class Circle(radius: Double) extends Shape
final case class Rectangle(width: Double, height: Double) extends Shape

val shapeGen = Generic[Shape]

// ------------------------------------
// AUTOMATICALLY DERIVING TYPE CLASSES
// ------------------------------------

case class Employee(name: String, number: Int, manager: Boolean)

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

object CsvEncoder {
  def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] = enc
  def instance[A](func: A => List[String]): CsvEncoder[A] = new CsvEncoder[A] {
    override def encode(value: A): List[String] = func(value)
  }

  implicit def genericEncoder[A, R](implicit gen: Generic.Aux[A, R], enc: CsvEncoder[R]): CsvEncoder[A] =
    instance(a => enc.encode(gen.to(a)))

  implicit def hlistEncoder[H, T <: HList](implicit hEnc: CsvEncoder[H], tEnc: CsvEncoder[T]): CsvEncoder[H :: T] = instance {
    case h :: t => hEnc.encode(h) ++ tEnc.encode(t)
  }

  implicit val stringEncoder: CsvEncoder[String] =
    instance(str => List(str))

  implicit val intEncoder: CsvEncoder[Int] =
    instance(num => List(num.toString))

  implicit val longEncoder: CsvEncoder[Long] =
    instance(long => List(long.toString))

  implicit val booleanEncoder: CsvEncoder[Boolean] =
    instance(bool => List(if(bool) "yes" else "no"))

  implicit val hnilEncoder: CsvEncoder[HNil] =
    instance(hnil => Nil)
}

def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String =
  values.map(value => enc.encode(value).mkString(",")).mkString(";")

val employees: List[Employee] = List(
  Employee("Bill", 1, true),
  Employee("Peter", 2, false),
  Employee("Milton", 3, false)
)

val iceCreams: List[IceCream] = List(
  IceCream("Sundae", 1, false),
  IceCream("Cornetto", 0, true),
  IceCream("Banana Split", 0, true),
)

writeCsv(employees)
writeCsv(iceCreams)
//writeCsv(employees zip iceCreams)
//
// ------------------------------------
//   DERIVING INSTANCES FOR PRODUCTS
// ------------------------------------
//
//def createEncoder[A](func: A => List[String]): CsvEncoder[A] = new CsvEncoder[A] {
//  override def encode(value: A): List[String] = func(value)
//}
//
//implicit val stringEncoder: CsvEncoder[String] =
//  createEncoder(str => List(str))
//
//implicit val intEncoder: CsvEncoder[Int] =
//  createEncoder(num => List(num.toString))
//
//implicit val booleanEncoder: CsvEncoder[Boolean] =
//  createEncoder(bool => List(if(bool) "yes" else "no"))
//
//implicit val hnilEncoder: CsvEncoder[HNil] =
//  createEncoder(hnil => Nil)
//
//implicit def hlistEncoder[H, T <: HList](implicit hEnc: CsvEncoder[H], tEnc: CsvEncoder[T]): CsvEncoder[H :: T] =
//  createEncoder {
//    case h :: t => hEnc.encode(h) ++ tEnc.encode(t)
//  }
//
// ------------------------------------
//   INSTANCES FOR CONCRETE PRODUCTS
// ------------------------------------
//val reprEncoder: CsvEncoder[String :: Int :: String :: HNil] =
//  implicitly
//
//reprEncoder.encode("abc" :: 1 :: "abc" :: HNil)
//
//implicit val iceCreamEncoder: CsvEncoder[IceCream] = {
//  val gen = Generic[IceCream]
//  val enc = CsvEncoder[gen.Repr]
//  createEncoder(iceCream => enc.encode(gen.to(iceCream)))
//}
//
//implicit def genericEncoder[A, R](implicit gen: Generic.Aux[A, R], enc: CsvEncoder[R]): CsvEncoder[A] =
//  createEncoder(a => enc.encode(gen.to(a)))

//writeCsv(iceCreams)

case class Pessoa(id: Long, nome: String, idade: Int, email: String)

val jhow = Pessoa(1,"Jhonatan",21,"test@mail.com")

val pessoas = List(
  Pessoa(1,"Jhonatan",21,"test@mail.com"),
  Pessoa(2,"Erick",28,"test@mail.com"),
  Pessoa(3,"Lucião",40,"test@mail.com")
)

writeCsv(pessoas)