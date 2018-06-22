package util

object StringUtils {

  def uperCamelToLowerSnake(name: String) = "([a-z])([A-Z]+)".r.replaceAllIn(name, "$1_$2").toLowerCase

}
