package statement

import shapeless.{::, Generic, HList, HNil}

trait StatementGenerator[A] {
  def select(value: A): List[String]
  def insert(value: A): List[String]
  def update(value: A): List[String]
}

object StatementGenerator {
  def apply[A](implicit stm: StatementGenerator[A]): StatementGenerator[A] = stm

  def instance[A](func: A => List[String]): StatementGenerator[A] = new StatementGenerator[A] {
    override def select(value: A): List[String] = func(value)

    override def insert(value: A): List[String] = func(value)

    override def update(value: A): List[String] = func(value)
  }

  implicit def genericEncoder[A, R](implicit gen: Generic.Aux[A, R], enc: StatementGenerator[R]): StatementGenerator[A] =
    instance(a => enc.select(gen.to(a)))

  implicit def hlistEncoder[H, T <: HList](implicit hEnc: StatementGenerator[H], tEnc: StatementGenerator[T]): StatementGenerator[H :: T] = instance {
    case h :: t => hEnc.select(h) ++ tEnc.select(t)
  }

  implicit val stringEncoder: StatementGenerator[String] =
    instance(str => List(str))

  implicit val intEncoder: StatementGenerator[Int] =
    instance(num => List(num.toString))

  implicit val longEncoder: StatementGenerator[Long] =
    instance(long => List(long.toString))

  implicit val booleanEncoder: StatementGenerator[Boolean] =
    instance(bool => List(if(bool) "yes" else "no"))

  implicit val hnilEncoder: StatementGenerator[HNil] =
    instance(hnil => Nil)
}