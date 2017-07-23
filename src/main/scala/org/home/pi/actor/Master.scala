package org.home.pi.actor

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.Props
import akka.routing.ActorRefRoutee
import akka.routing.RoundRobinRoutingLogic
import akka.routing.Router
import org.home.pi.message.Work
import org.home.pi.message.Result
import org.home.pi.message.PiCalculation
import akka.routing.RoundRobinGroup
import akka.actor.UntypedActor
import org.home.pi.message.Calculate
import org.home.pi.message.Work

class Master(iNoOfWorkers: Int, iNrOfMessages: Int, iNrOfElements: Int, iListener: ActorRef) extends Actor {
  val noOfWorkers = iNoOfWorkers;
  val nrOfMessages = iNrOfMessages;
  val nrOfElements = iNrOfElements;
  var nrOfResults = 0;
  val listener = iListener;
  var pi: Double = 0.0;

  var router = {
    val routees = Vector.fill(5) {
      val r = context.actorOf(Props[Worker])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  def receive = {
    case w: Calculate => for (i <- 0 to nrOfMessages) {
      router.route(new Work(i, nrOfElements), sender)
    }
    case w: Result => {
      var result = w.asInstanceOf[Result];
      pi += result.getValue();
      nrOfResults += 1;
      printf("%s\n", nrOfResults.toString)
      if (nrOfResults == nrOfMessages) {
        printf("Telling Listener...")
        listener.tell(new PiCalculation(pi), self);
        context.stop(self);
      }
    }
    case w: Any => unhandled(w);
  }
}