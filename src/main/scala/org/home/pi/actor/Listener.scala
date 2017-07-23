package org.home.pi.actor

import akka.actor.Actor
import org.home.pi.message.PiCalculation

class Listener extends Actor {

  def receive = {
    case w: PiCalculation => {
      var piCalc = w.asInstanceOf[PiCalculation];
      printf("\n\tPi: %s", piCalc.getPi().toString());
    }
    case w: Any => {
      unhandled(w);
    }
  }
}