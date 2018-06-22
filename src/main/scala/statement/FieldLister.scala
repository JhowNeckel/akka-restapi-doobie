package statement

import shapeless.labelled.FieldType
import shapeless.{::, HList, HNil, LabelledGeneric, Lazy}

trait FieldLister[A] {
  val list: List[String]

  def map(value: A): Map[String, Any]
}

object FieldLister extends FieldListerLowPriority {

  implicit def genericLister[A, R](implicit gen: LabelledGeneric.Aux[A, R], lister: Lazy[FieldLister[R]]): FieldLister[A] =
    new FieldLister[A] {
      override val list: List[String] = lister.value.list

      override def map(value: A): Map[String, Any] = lister.value.map(gen.to(value))
    }

  implicit val hnilLister: FieldLister[HNil] = new FieldLister[HNil] {
    override val list: List[String] = Nil

    override def map(value: HNil): Map[String, Any] = Map.empty[String, Any]
  }

  implicit def hconsLister[K, H, T <: HList](implicit hLister: Lazy[FieldLister[H]], tLister: FieldLister[T]): FieldLister[FieldType[K, H] :: T] =
    new FieldLister[FieldType[K, H] :: T] {
      override val list: List[String] = hLister.value.list ++ tLister.list

      override def map(value: FieldType[K, H] :: T): Map[String, Any] = value match {
        case h :: t => hLister.value.map(h) ++ tLister.map(t)
      }
    }

}
