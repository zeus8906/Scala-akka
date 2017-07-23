package org.home.pi.message

class Work(iStart: Int, iNoOfElements: Int) {
  private var start = iStart;
  private var noOfElement = iNoOfElements;
 
  def getStart():Int = {
    start
  }
  def getNoOfElements(): Int = {
    iNoOfElements
  }
  
  
}