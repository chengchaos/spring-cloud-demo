package org.example

import scala.beans.BeanProperty



class MyBean() {
  @BeanProperty
  var name: String = _
  @BeanProperty
  var age: Int = _
  @BeanProperty
  var email: String = _


}
object MyBean {
  def main(args: Array[String]) : Unit = {
    val bean : MyBean = new MyBean

    bean.setName("chengchao")
    bean.setAge(30)
  }
}
