package inj

import module.DoobiePostgresModule

class ApiModule {
  lazy val db = DoobiePostgresModule.apply
}

object ApiModule {
  def apply() = new ApiModule
}