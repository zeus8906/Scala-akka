package org.home.pi

import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.actor.Props
import org.home.pi.actor.Listener
import org.home.pi.actor.Master
import org.home.pi.message.Calculate

object App {

  def main(args: Array[String]) ={
    print("Start")
    calculate(4,10,10);
    
  }
  
  private def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int) = {
    var system = ActorSystem.create("PiSystem");
    val listener: ActorRef = system.actorOf(Props[Listener], "listener");
    var master: ActorRef = system.actorOf(Props(new Master(nrOfWorkers, nrOfMessages, nrOfElements, listener)), "master");
    master.tell(new Calculate, master);
  }
}