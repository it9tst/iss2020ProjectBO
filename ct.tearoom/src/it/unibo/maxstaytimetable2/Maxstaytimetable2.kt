/* Generated by AN DISI Unibo */ 
package it.unibo.maxstaytimetable2

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Maxstaytimetable2 ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val MaxStayTime 	= 100000L
				var StartTime 		= 0L
				var TimerDone 		= 0L
				var TimerGlobalDone = 0L
				var TimeAfterResume = 0L
				var TimerToReturn   = 0L
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("MAXSTAYTIMETABLE2 | Start")
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("wait") { //this:State
					action { //it:State
						println("MAXSTAYTIMETABLE2 | Wait")
					}
					 transition(edgeName="t064",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t065",targetState="wait",cond=whenDispatch("stopTimer"))
					transition(edgeName="t066",targetState="resume",cond=whenDispatch("resumeTimer"))
					transition(edgeName="t067",targetState="endWork",cond=whenDispatch("end"))
					transition(edgeName="t068",targetState="returnTimerDone",cond=whenRequest("maxStayTimerLeftRequestToTable"))
				}	 
				state("newTimer") { //this:State
					action { //it:State
						println("MAXSTAYTIMETABLE2 | newTimer")
						StartTime = getCurrentTime()
						
									TimerGlobalDone = 0
						stateTimer = TimerActor("timer_newTimer", 
							scope, context!!, "local_tout_maxstaytimetable2_newTimer", MaxStayTime )
					}
					 transition(edgeName="t169",targetState="timerExpired",cond=whenTimeout("local_tout_maxstaytimetable2_newTimer"))   
					transition(edgeName="t170",targetState="stop",cond=whenDispatch("stopTimer"))
					transition(edgeName="t171",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t172",targetState="returnTimerDone",cond=whenRequest("maxStayTimerLeftRequestToTable"))
				}	 
				state("stop") { //this:State
					action { //it:State
						println("MAXSTAYTIMETABLE2 | stop")
						TimerDone = getDuration(StartTime)
						
									TimerGlobalDone += TimerDone
					}
					 transition(edgeName="t273",targetState="resume",cond=whenDispatch("resumeTimer"))
					transition(edgeName="t274",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t275",targetState="returnTimerDoneStop",cond=whenRequest("maxStayTimerLeftRequestToTable"))
				}	 
				state("resume") { //this:State
					action { //it:State
						println("MAXSTAYTIMETABLE2 | resume")
						
									TimeAfterResume = MaxStayTime - TimerGlobalDone
						StartTime = getCurrentTime()
						stateTimer = TimerActor("timer_resume", 
							scope, context!!, "local_tout_maxstaytimetable2_resume", TimeAfterResume )
					}
					 transition(edgeName="t376",targetState="timerExpired",cond=whenTimeout("local_tout_maxstaytimetable2_resume"))   
					transition(edgeName="t377",targetState="stop",cond=whenDispatch("stopTimer"))
					transition(edgeName="t378",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t379",targetState="returnTimerDone",cond=whenRequest("maxStayTimerLeftRequestToTable"))
				}	 
				state("timerExpired") { //this:State
					action { //it:State
						println("MAXSTAYTIMETABLE2 | timerExpired")
						forward("maxStayTimerExpired", "maxStayTimerExpired(2)" ,"maxstaytime" ) 
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("returnTimerDone") { //this:State
					action { //it:State
						println("MAXSTAYTIMETABLE2 | returnTimerDone")
						TimerDone = getDuration(StartTime)
						 
									TimerGlobalDone += TimerDone
									TimerToReturn = MaxStayTime - TimerGlobalDone
						answer("maxStayTimerLeftRequestToTable", "maxStayTimerLeftReplyFromTable", "maxStayTimerLeftReplyFromTable($TimerToReturn)"   )  
					}
					 transition( edgeName="goto",targetState="resume", cond=doswitch() )
				}	 
				state("returnTimerDoneStop") { //this:State
					action { //it:State
						println("MAXSTAYTIMETABLE2 | returnTimerDoneStop")
						 
									TimerToReturn = MaxStayTime - TimerGlobalDone
						answer("maxStayTimerLeftRequestToTable", "maxStayTimerLeftReplyFromTable", "maxStayTimerLeftReplyFromTable($TimerToReturn)"   )  
					}
					 transition(edgeName="t480",targetState="resume",cond=whenDispatch("resumeTimer"))
					transition(edgeName="t481",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t482",targetState="returnTimerDoneStop",cond=whenRequest("maxStayTimerLeftRequestToTable"))
				}	 
				state("endWork") { //this:State
					action { //it:State
						println("MAXSTAYTIMETABLE2 | End work")
						terminate(0)
					}
				}	 
			}
		}
}