/* Generated by AN DISI Unibo */ 
package it.unibo.smartbell

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Smartbell ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val Temp_max = 37.5
				var Client_temp = 0.0
				var Id_client = 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Smartbell start")
					}
					 transition( edgeName="goto",targetState="waitRing", cond=doswitch() )
				}	 
				state("waitRing") { //this:State
					action { //it:State
						println("Smartbell wait ring")
					}
					 transition(edgeName="t00",targetState="checkTempClient",cond=whenRequest("enter_request_client"))
					transition(edgeName="t01",targetState="endWork",cond=whenDispatch("end"))
				}	 
				state("checkTempClient") { //this:State
					action { //it:State
						println("Smartbell check temp client")
						if( checkMsgContent( Term.createTerm("enter_request_client(TEMP)"), Term.createTerm("enter_request_client(TEMP)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Client_temp = $payloadArg(0)  
						}
						if(  Client_temp < Temp_max && Client_temp != 0.0  
						 ){println("Puoi entrare")
						request("smartbell_enter_request", "smartbell_enter_request($Id_client)" ,"waiter" )  
						}
						else
						 {println("Non puoi entrare")
						 answer("enter_request_client", "enter_reply_from_smartbell_n", "enter_reply_from_smartbell_n(NONE)"   )  
						 }
					}
					 transition(edgeName="t12",targetState="clientEnter",cond=whenReply("client_accept"))
					transition(edgeName="t13",targetState="endWork",cond=whenReply("client_accept_n"))
				}	 
				state("clientEnter") { //this:State
					action { //it:State
						answer("enter_request_client", "enter_reply_from_smartbell", "enter_reply_from_smartbell($Id_client)"   )  
						 Id_client++  
					}
				}	 
				state("endWork") { //this:State
					action { //it:State
						println("Smartbell end work")
						terminate(0)
					}
				}	 
			}
		}
}
