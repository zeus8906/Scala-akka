package org.home.pi.message

class Result(iValue: Double) {
  private val value = iValue;

  def getValue(): Double = {
    value
  }
}