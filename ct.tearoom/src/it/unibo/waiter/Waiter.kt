/* Generated by AN DISI Unibo */ 
package it.unibo.waiter

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Waiter ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var Is_free = true
				var Table1_free = true
				var Table1_clean = true
				var Table2_free = true
				var Table2_clean = true
				var Max_waiting_time = 10
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Waiter start")
					}
					 transition( edgeName="goto",targetState="rest", cond=doswitch() )
				}	 
				state("rest") { //this:State
					action { //it:State
						if(  Is_free  
						 ){println("PAUSE")
						}
						else
						 {println("MOVE")
						 }
					}
					 transition(edgeName="t00",targetState="accept",cond=whenRequest("smartbell_enter_request"))
				}	 
				state("accept") { //this:State
					action { //it:State
						 Is_free = false  
						if(  Table1_free && Table1_clean || Table2_free && Table2_clean  
						 ){answer("smartbell_enter_request", "client_accept", "client_accept(TABLE)"   )  
						}
						else
						 {answer("smartbell_enter_request", "client_accept_n", "client_accept_n($Max_waiting_time)"   )  
						 }
					}
					 transition( edgeName="goto",targetState="endWork", cond=doswitch() )
				}	 
				state("endService") { //this:State
					action { //it:State
						 Is_free = true 
					}
					 transition( edgeName="goto",targetState="endWork", cond=doswitch() )
				}	 
				state("endWork") { //this:State
					action { //it:State
						println("Waiter end work")
						terminate(0)
					}
				}	 
			}
		}
}