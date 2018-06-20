package module

import model.Persons

class DoobiePostgresModule {
  object person extends Persons
}

object DoobiePostgresModule {
  def apply = new DoobiePostgresModule()
}