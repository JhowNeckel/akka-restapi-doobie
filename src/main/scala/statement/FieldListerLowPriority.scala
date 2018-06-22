package statement

trait FieldListerLowPriority {

  import shapeless.labelled.FieldType
  import shapeless.{::, HList, Witness}

  implicit def primitiveFieldLister[K <: Symbol, H, T <: HList]
  (implicit witness: Witness.Aux[K], tLister: FieldLister[T]): FieldLister[FieldType[K, H] :: T] = new FieldLister[FieldType[K, H] :: T] {
    override val list: List[String] = witness.value.name :: tLister.list

    override def map(value: ::[FieldType[K, H], T]): Map[String, Any] = value match {
      case _ :: t => Map(witness.value.name -> value.head(t))
    }
  }
}
