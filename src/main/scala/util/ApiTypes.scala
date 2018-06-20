package util

import doobie.util.fragment.Fragment
import entities.Person
import services.Service

object ApiTypes {

  type PersonService = Service[Fragment, Person]
}
