/* Generated by AN DISI Unibo */ 
package it.unibo.barman

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Barman ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val TimePrepareOrder = 15000L
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("BARMAN | Start")
					}
					 transition( edgeName="goto",targetState="waitOrder", cond=doswitch() )
				}	 
				state("waitOrder") { //this:State
					action { //it:State
						println("BARMAN | Wait order")
					}
					 transition(edgeName="t035",targetState="prepareOrder",cond=whenDispatch("send_order"))
					transition(edgeName="t036",targetState="endWork",cond=whenDispatch("end"))
				}	 
				state("prepareOrder") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("send_order(ID,ORD)"), Term.createTerm("send_order(ID,ORD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("BARMAN | Prepare order for client with ID: ${payloadArg(0)} and ORD: ${payloadArg(1)}")
								delay(TimePrepareOrder)
								println("BARMAN | Order ready for client with ID: ${payloadArg(0)} and ORD: ${payloadArg(1)}")
								forward("barman_complete_order", "barman_complete_order(${payloadArg(0)},${payloadArg(1)})" ,"waitermind" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitOrder", cond=doswitch() )
				}	 
				state("endWork") { //this:State
					action { //it:State
						println("BARMAN | End work")
						terminate(0)
					}
				}	 
			}
		}
}
