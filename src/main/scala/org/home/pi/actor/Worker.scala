package org.home.pi.actor

import akka.actor.UntypedActor
import org.home.pi.message.{ Work, Result };
import akka.actor.Actor

class Worker extends Actor {

   def receive = {
    case w: Work => {
      var work: Work = w.asInstanceOf[Work];
      var result = calculatePiFor(work.getStart(), work.getNoOfElements());
      sender().tell(new Result(result), self);
    }
    case w: Any => {
      unhandled(w);
    }
  }

  private def calculatePiFor(start: Int, nrOfElements: Int): Double = {
    var acc = 0.0;
    for (i: Int <- (start * nrOfElements) to ((start + 1) * nrOfElements - 1)) {
      acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
    }
    return acc;
  }
}