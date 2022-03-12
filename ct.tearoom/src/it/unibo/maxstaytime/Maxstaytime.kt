/* Generated by AN DISI Unibo */ 
package it.unibo.maxstaytime

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Maxstaytime ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var Table = 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("MAXSTAYTIME | Start")
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("wait") { //this:State
					action { //it:State
						println("MAXSTAYTIME | wait")
					}
					 transition(edgeName="t043",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t044",targetState="resume",cond=whenDispatch("resumeTimer"))
					transition(edgeName="t045",targetState="stop",cond=whenDispatch("stopTimer"))
					transition(edgeName="t046",targetState="timerExpired",cond=whenDispatch("maxStayTimerExpired"))
					transition(edgeName="t047",targetState="timerLeft",cond=whenRequest("maxStayTimerLeftRequest"))
					transition(edgeName="t048",targetState="endWork",cond=whenDispatch("end"))
				}	 
				state("newTimer") { //this:State
					action { //it:State
						println("MAXSTAYTIME | newTimer")
						if( checkMsgContent( Term.createTerm("startTimer(TABLE)"), Term.createTerm("startTimer(TABLE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0).toInt() == 1  
								 ){forward("startTimer", "startTimer(1)" ,"maxstaytimetable1" ) 
								}
								if(  payloadArg(0).toInt() == 2  
								 ){forward("startTimer", "startTimer(2)" ,"maxstaytimetable2" ) 
								}
						}
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("resume") { //this:State
					action { //it:State
						println("MAXSTAYTIME | resume")
						if( checkMsgContent( Term.createTerm("resumeTimer(TABLE)"), Term.createTerm("resumeTimer(TABLE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0).toInt() == 1  
								 ){forward("resumeTimer", "resumeTimer(1)" ,"maxstaytimetable1" ) 
								}
								if(  payloadArg(0).toInt() == 2  
								 ){forward("resumeTimer", "resumeTimer(2)" ,"maxstaytimetable2" ) 
								}
						}
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("stop") { //this:State
					action { //it:State
						println("MAXSTAYTIME | stop")
						if( checkMsgContent( Term.createTerm("stopTimer(TABLE)"), Term.createTerm("stopTimer(TABLE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0).toInt() == 1  
								 ){forward("stopTimer", "stopTimer(1)" ,"maxstaytimetable1" ) 
								}
								if(  payloadArg(0).toInt() == 2  
								 ){forward("stopTimer", "stopTimer(2)" ,"maxstaytimetable2" ) 
								}
						}
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("timerExpired") { //this:State
					action { //it:State
						println("MAXSTAYTIME | timerExpired")
						if( checkMsgContent( Term.createTerm("maxStayTimerExpired(TABLE)"), Term.createTerm("maxStayTimerExpired(TABLE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Table = payloadArg(0).toInt()
								forward("maxStayTimerExpired", "maxStayTimerExpired($Table)" ,"waitermind" ) 
						}
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("timerLeft") { //this:State
					action { //it:State
						println("MAXSTAYTIME | timerLeft")
						if( checkMsgContent( Term.createTerm("maxStayTimerLeftRequest(TABLE)"), Term.createTerm("maxStayTimerLeftRequest(TABLE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0).toInt() == 1  
								 ){request("maxStayTimerLeftRequestToTable", "maxStayTimerLeftRequestToTable(1)" ,"maxstaytimetable1" )  
								}
								if(  payloadArg(0).toInt() == 2  
								 ){request("maxStayTimerLeftRequestToTable", "maxStayTimerLeftRequestToTable(2)" ,"maxstaytimetable2" )  
								}
						}
					}
					 transition(edgeName="t149",targetState="forwardRemainingTime",cond=whenReply("maxStayTimerLeftReplyFromTable"))
				}	 
				state("forwardRemainingTime") { //this:State
					action { //it:State
						println("MAXSTAYTIME | forwardRemainingTime")
						if( checkMsgContent( Term.createTerm("maxStayTimerLeftReplyFromTable(TIMERLEFT)"), Term.createTerm("maxStayTimerLeftReplyFromTable(TIMERLEFT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								answer("maxStayTimerLeftRequest", "maxStayTimerLeftReply", "maxStayTimerLeftReply(${payloadArg(0)})"   )  
						}
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("endWork") { //this:State
					action { //it:State
						println("MAXSTAYTIME | End work")
						terminate(0)
					}
				}	 
			}
		}
}