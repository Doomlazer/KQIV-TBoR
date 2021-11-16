;;; Sierra Script 1.0 - (do not remove this comment)
(script# 83)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use User)
(use Actor)
(use System)
(use Invent)

(public
	Room83 0
)
(synonyms
	(room cell)
	(roger man)
)

(local
	hench
	window
	roomDialog
	rogerego
	kissed
	tooth
	randomPick
	toofmsg
	choice
	heart
)
(instance Room83 of Room
	(properties
		picture 83
		style (| BLACKOUT IRISOUT)
	)
	
	(method (init)
		(Load VIEW 649)
		(Load VIEW 634)
		(Load VIEW 512)
		(Load VIEW 137)
		(self setRegions: LOLOTTE)
		(super init:)
		
		
		((= rogerego (Actor new:))
			setScript: rogerActions
			view: 3
			loop: 2
			cel: 0
			posn: 100 132
			setPri: 9
			init:
		)
		
		((View new:)
			view: 634
			loop: 1
			cel: 0
			posn: 55 78
			setPri: 4
			init:
			addToPic:
		)
		((View new:)
			view: 634
			loop: 1
			cel: 1
			posn: 267 78
			setPri: 4
			init:
			addToPic:
		)
		(if isNightTime
			((Prop new:)
				view: 512
				loop: 0
				posn: 57 66
				setPri: 3
				init:
				setCycle: Forward
			)
			((Prop new:)
				view: 512
				loop: 0
				posn: 267 66
				setPri: 3
				init:
				setCycle: Forward
			)
		)
		(= isIndoors TRUE)
		(if isNightTime
			((= window (View new:))
				view: 649
				loop: 0
				cel: 0
				posn: 162 64
				init:
				stopUpd:
			)
		)
		(if (or (== prevRoomNum 86) (== prevRoomNum 0))
			(ego
				posn: 156 159
				view: 4
				illegalBits: cWHITE
				loop: 3
				xStep: 4
				yStep: 2
				init:
			)
			(if (== gamePhase startingOut)
				(Load VIEW 141)
				(self setScript: takeBack)
			)
		)
	)
	
	(method (doit)
		(super doit:)
		(if
		(and (== gamePhase endGame) (& (ego onControl: 0) $0040))
			(curRoom newRoom: 86)
		)
	)
	
	(method (handleEvent event)
		(return
			(cond 
				((event claimed?) (return TRUE))
				((== (event type?) saidEvent)
					(cond 
						(
							(or
								(Said 'look[<around][/noword]')
								(Said 'look/room,castle')
							)
							(Print 83 0)
						)
						((Said 'look>')
							(cond 
								((Said '/skeleton,james') (Print 83 1))
								((Said '/machine') (Print 83 2))
								((Said '/whip') (Print 83 3))
								((Said '/chain') (Print 83 4))
								((Said '/window') (Print 83 5))
								((Said '/wall') (Print 83 6))
								((or (Said '/dirt') (Said '<down')) (Print 83 7))
								((Said '/roger') 
									(if kissed 
										(Print 83 38) 
									else 
										(Print 83 19)
									)
								)
								((Said '/hole') 
									(if kissed 
										(if ((Inventory at: iTooth) ownedBy: 83) 
											(Print 83 35) 
										else 
											(Print 83 30)) 
									else 
										(Print 83 31)
									)
								)
							)
						)
						(
						(or (Said 'use,(turn<on)/machine') (Said 'turn/wheel')) (Print 83 8))
						((Said 'get/roger') (Print {Nobody truly 'gets' Roger Wilco. He's is own person, dig?}))
						((Said 'get/whip') (Print 83 9))
						((Said 'get/chain') (Print 83 4))
						((Said 'get/tooth,toof') 
							(if ((Inventory at: iTooth) ownedBy: 83)
								(if (ego inRect: 60 115 85 135)
									(ego get: iTooth)
									(if (not scoredTooth)
										(theGame changeScore: 256)
										(= gotItem 1)
										(= scoredTooth 1)
									)
									(Print 83 36 #icon 1 2 0)
									(if (not toofmsg) (Print 83 46)(= toofmsg 1)) ;try to make tooth function more obvious
								else 
									(Print {Fuck you, get closer first.})
								)	
							else 
								(Print "The tooth is not yours to take.")
							)
						)
						((Said 'open/window') (Print 83 10))
						((Said 'break/window') (Print 83 11))
						((Said 'open/door') 
							(if (< gamePhase killedLolotte) 
								(Print 83 12) 
							else 
								(Print 83 13)
								)
							)
						((Said 'unlatch/door') 
							(if (< gamePhase killedLolotte) 
								(Print 83 14) 
							else 
								(Print 83 15)
							)
						)
						((Said 'call,help') (Print 83 16))
						((Said 'kiss/roger')
							(if (ego inRect: 80 120 200 185)
								(rogerActions changeState: 8)
							else
								(Print 83 49)
							)
						)
						((Said 'show/breasts') (Print 83 28))
						((Said 'fuck/roger') (Print 83 50))
						((Said 'fuck/skeleton,james') (Print 83 33)) ;the skeleton in the whale is james, not this dude, but you could use it here in the AGI cell
						((Said 'kill/roger') (rogerActions changeState: 20))
						((Said 'deliver/tooth[/roger,man]')
							(if (ego has: iTooth) 
								(Print 83 37)
								((Inventory at: iTooth) moveTo:3)
							else 
								(Print 83 47)
							)
						)
						((Said 'knock[/door]')
							(Print 83 48)
							(takeBack changeState: 1)
						)
						
						((Said 'talk/roger,man')  
						(if (and (ego has: iTooth)(ego has: iDecoderRing) (ego has: iPeacockFeather)) 
							;sloppy logic tree
							(= choice 0)
							(switch (= randomPick (Random 1 3))	
									(1
										(= choice (Print 83 45 #time 10 #button {Lantern} 1 #button {Pandora's Box} 2))
										(if (== choice 1)
											(ego get: iLantern)
											(Print 83 51 #icon 428 0 0)
										)
										(if (== choice 2)
											(ego get: iPandorasBox)
											(Print {"Good choice! Fuck those ghosts, right?"} #icon 425 0 0)
										else
											(Print {"Neither than, I guess?"})
										)
									)
									(2									
										(= choice (Print 83 45 #button {Pan's Flute} 1 #button {Axe} 2))
										(if (== choice 1)
											(ego get: iSilverFlute)
											(Print {"I had to kill a Pan to get this. Those hooves can be dangerous!"} #icon 413 0 0)
										)
										(if (== choice 2)
											(ego get: iAxe)
											(Print {"This axe inspired the chainsaw in Silent Hill, at least in my dimension; not sure if that's true here."} #icon 418 0 0)
										else
											(Print {"Neither than, I guess?"})
										)
									)
									(3											
										(= choice (Print 83 45  #button {Magic Fruit} 1 #button {Magic Hen} 2))
										(if (== choice 1)
											(ego get: iMagicFruit)
											(Print {"hop hop hop hop hop hop hop hop hop hop hop"} #icon 412 0 0)
										)
										(if (== choice 2)
											(ego get: iMagicHen)
											(Print {"Legally I'm not allowed within 1,000 feet of this thing, so you can have it."} #icon 431 0 0)
										else
											(Print {"Neither than, I guess?"})
										)
									)
								
							)
				
						else
							(if (ego has: iGoldenBridle)
								(Print 83 25)
							else 
							
								(Print 83 20)
								(Print 83 21)
								(Print 83 22)
								(Print 83 23)
							
								;accept or decline the bridle
								
								(if (== (Print 83 43 #button {Yes} 1 #button {No} 2) 1 )
									(ego get: iGoldenBridle)
									(theGame changeScore: 3)
									(= gotItem 1)
									(Print 83 24 #icon 426 0 0)
									(Print 83 27)
								else
									(Print 83 44)
								)
								
								
							)
						)	   
						)
					)
				)
			)
		)
	)
)

(instance takeBack of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 (= seconds 120))
			(1
				(User canControl: FALSE canInput: FALSE)
				(ego setMotion: 0)
				(Print 83 17)
				(rogerego ignoreActors: 1) 
				(if (ego inRect: 123 142 193 180)
					(ego setMotion: MoveTo 150 130 self)
				else
					(= state 1)
					(= cycles 1)
				)
			)
			(2
				(if (ego has: iTooth)
					(Print 83 52 #at 0 50 #font smallFont #time 5 #title {Roger}) ;re-enforce the odd nature of the tooth
				)
				(ego loop: 2)
				((= hench (Actor new:))
					view: 141
					posn: 150 194
					illegalBits: 0
					ignoreActors: 1
					init:
					setCycle: Walk
					setMotion: MoveTo 150 160 self
				)
			)
			(3
				(= roomDialog
					(Print 83 18 #at -1 10 #font smallFont #dispose #title {Rosella})
				)
				(User canControl: FALSE canInput: FALSE)
				(ego illegalBits: 0 setMotion: MoveTo 160 (ego y?) self)
			)
			(4
				(ego illegalBits: 0 setLoop: 2 setMotion: Follow hench 5)
				(self cue:)
			)
			(5
				(hench setMotion: MoveTo 150 225 self)
			)
			(6
				
				(cls)
				(ego setLoop: -1 setAvoider: 0)
				(HandsOn)	
				(curRoom newRoom: 86)
			)
		)
	)
)


(instance rogerActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				(rogerego
					view: 3
					cel: 0
					loop: 2
				)
			)
			(8
				(HandsOff)
				(rogerego ignoreActors: 1) 
				(ego 
					setCycle: Walk
					setMotion: MoveTo 115 132 self
				)
			)
			(9
				(FaceObject ego rogerego)
				;(ego setMotion: MoveTo 120 132 self)
				(= state 9)
				(= cycles 5)
			)
			(10
				(if kissed (Print 83 32) else (Print 83 29))
				(ego 
					view: 40
					setCycle: EndLoop self
				)	
			)
			(11
				(= heart (Prop new:))
				(heart
					view: 681
					cel: 0
					loop: 0
					setPri: 14
					ignoreActors:
					posn: 105 120
					setCycle: EndLoop self
					init:
				)
			)
			(12	
				(heart dispose:)
				(rogerego
					view: 137
					cel: 0
					loop: 0
					cycleSpeed: 1
					setCycle: Forward
				)
				(ego setCycle: BegLoop)
				(rogerego setMotion: MoveTo 65 132 self)
			)
			(13
				(rogerego
					view: 137
					cel: 0
					loop: 1
					cycleSpeed: 1
					setCycle: EndLoop self
				)
				(if ((Inventory at: iTooth) ownedBy: 3)
					((Inventory at: iTooth) moveTo: 83)
				)
			)
			(14
				(rogerego
					view: 3
				)
				(rogerego setCycle: Walk setMotion: MoveTo 100 132 self)
			)
			(15
				(if kissed (Print 83 34) else (Print 83 26))
				(= kissed 1) 
				(= state 15)
				(= cycles 1)	
				
			)
			(16
				(ego view: 4 setScript: 0 setCycle: Walk)
				(rogerego ignoreActors: 0)
				(HandsOn)
				(rogerego
					view: 3
					cel: 0
					loop: 2
					ignoreActors: 0
				)
			)
			(20
				(if rogerDead (Print 83 41) else (Print 83 39))
				(= state 20)
				(= cycles 1)	 
				
			)
			(21
				(HandsOff)
				(rogerego
					view: 576
					cel: 0
					loop: 1
					cycleSpeed: 1
					setCycle: EndLoop self
				)
			)
			(22
				(rogerego
					view: 576
					cel: 0
					loop: 2
					cycleSpeed: 1
					setCycle: EndLoop self
				)
			)
			(23
				(rogerego
					view: 576
					cel: 0
					loop: 1
					cycleSpeed: 1
					setCycle: EndLoop self
				)
			)
			(24
				
			 	(HandsOn)	
				(if rogerDead (Print 83 42) else (Print 83 40))
				(= rogerDead 1)
				(= kissed 0)
				(rogerego
					view: 3
					cel: 0
					loop: 2
				) 				
			)
		)
	)
)
