;;; Sierra Script 1.0 - (do not remove this comment)
(script# 65)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use Actor)
(use Invent)
(use System)
(use Sound)

(public
	Room65 0
)
(synonyms
	(caldron caldron)
	(shelf cabinet)
)

(local
	toothHolder
	sparkle
)

(instance poofSound of Sound
	(properties
		number 40
	)
)

(instance candleView of Prop
	(properties
		view 536	
	)
)

(instance Room65 of Room
	(properties
		picture 65
		style (| BLACKOUT IRISOUT)
	)
	
	(method (init)
		(Load VIEW 648)
		(Load VIEW 536)
		(self setRegions: HAUNTED_HOUSE)
		(super init:)
		((candleView new:)
			view: 536
			loop: 5
			cel: 0
			posn: 238 80
			setPri: 4
			addToPic:
		)
		
		(if isNightTime
			((View new:)
				view: 648
				loop: 5
				cel: 0
				posn: 280 104
				setPri: 6
				addToPic:
			)
			(if (or ((inventory at: iTooth) ownedBy: ego) ((inventory at: iTooth) ownedBy: 65))
			else
					;candle 1 flame
					((candleView new:)
					view: 536
					loop: 1
					posn: 237 73
					setPri: 4
					init:
					setCycle: Forward
					)	
				)
		)
		
		
		

		(if (or ((inventory at: iTooth) ownedBy: ego) ((inventory at: iTooth) ownedBy: 65))
		;candle 2
		(poofSound play:)
		((candleView new:)
			view: 536
			loop: 5
			cel: 0
			posn: 215 80
			setPri: 4
			addToPic:
		)
		;candle 3
		((candleView new:)
			view: 536
			loop: 5
			cel: 0
			posn: 180 80
			setPri: 4
			addToPic:
		)
		
		(if ((inventory at: iBriefcase) ownedBy: 65)
			;candle 1 flame
				((candleView new:)
					view: 536
					loop: 1
					posn: 237 73
					setPri: 4
					init:
					setCycle: Forward
					)
		)
		(if ((inventory at: iSkull) ownedBy: 65)	
			;candle 2 flame
			((candleView new:)
				view: 536
				loop: 1
				posn: 214 73
				setPri: 4
				init:
				setCycle: Forward
			)
		)
		(if ((inventory at: iVirginity) ownedBy: 65)
			;candle 3 flame
			((candleView new:)
				view: 536
				loop: 1
				posn: 179 73
				setPri: 4
				init:
				setCycle: Forward
			)
		)
		
		
			
			(if ((inventory at: iTooth) ownedBy: 65)
				((= toothHolder (View new:))
					view: 577
					loop: 0
					cel: 1
					posn: 152 117
					setPri: 7
					init:
					startUpd:
				)
				((= sparkle (Prop new:))
					view: 580
					loop: 0
					cel: 1
					posn: 208 94
					;setPri: 7
					setCycle: Forward
					;setSpeed: 5
					init:
					startUpd:
				)
			else
				((= toothHolder (View new:))
					view: 577
					loop: 0
					cel: 0
					posn: 152 117
					setPri: 7
					init:
					startUpd:
				)
			)
		
		)
		(if (or (== prevRoomNum 64) (== prevRoomNum 0))
			(ego posn: 245 162 view: 4 xStep: 4 yStep: 2 init:)
		)
		(if
			(and
				(< 0 mansionPhase)
				(< mansionPhase 255)
				(== ghostRoomNum curRoomNum)
			)
			(NotifyScript HAUNTED_HOUSE -1)
		)
	)
	
	(method (doit)
		(super doit:)
		(if (& (ego onControl: FALSE) $0040) (curRoom newRoom: 64))
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
								(Said 'look/room,kitchen')
							)
							(Print 65 0)
							(if (or ((inventory at: iTooth) ownedBy: ego) ((inventory at: iTooth) ownedBy: 65)) 
								(Print 65 25)
							)
						)
						((Said 'look>')
							(cond 
								((Said '/pantry') (Print 65 1))
								 
								((Said '/fireplace')
									(if (or ((inventory at: iTooth) ownedBy: ego) ((inventory at: iTooth) ownedBy: 65)) 
									 (Print 65 24)
									else
									(Print 65 2)
									)
								)
								((Said '/caldron,pot') 
									(if (or ((inventory at: iTooth) ownedBy: ego) ((inventory at: iTooth) ownedBy: 65)) 
									(Print 65 23)
									else
									(Print 65 3)
									)
								)
								
								((Said '/display,case,pedestal,jar') 
									(if (or ((inventory at: iTooth) ownedBy: ego) ((inventory at: iTooth) ownedBy: 65)) 
										(if ((inventory at: iTooth) ownedBy: 65)
											(Print 65 27)
										else	
											(Print 65 28)
										)
									else 
										(Print 65 29)	
									)
								)
								
								((Said '/butterchurn') (Print 65 4))
								((Said '/window')
									(if (ego inRect: 229 124 277 146)
										(Print 65 5)
									else
										(Print 65 6)
									)
								)
								((Said '/barrel') (Print 65 7))
								((Said '/shelf') (Print 65 8))
								((Said '/chandelier') (Print 65 9))
								((Said '/wall') (Print 65 10))
								((or (Said '/dirt') (Said '<down')) (Print 65 11))
								((Said '/ladder') (Print 65 12))
								((Said '/crumbs') (Print 65 13))
								((Said '/bottle')
									(if (ego has: iGlassBottle)
										(event claimed: FALSE)
									else
										(Print 800 2)
									)
								)
								(else (event claimed: FALSE))
							)
						)
						
						
						((Said 'put,deliver>')
							(cond
								((Said '/tooth,toof[/case,jar,pedestal]') 
									(if ((inventory at: iTooth) ownedBy: ego)
										(if (ego inRect: 130 110 180 125)
											(ego setScript: jarActions)
											(jarActions changeState: 1)
											(Print 65 18)
										else
											(Print 65 16)
										)
									else
										(Print 65 30)	
									)
								)
								((Said '/case[/cauldron,pot]') 
									(if ((inventory at: iBriefcase) ownedBy: ego)
										;(if (and (ego inRect: 130 110 230 125)((inventory at: iTooth) ownedBy: 65))
										(if ((inventory at: iTooth) ownedBy: 65)
											(if (ego inRect: 130 110 230 125)
												(ego setScript: potActions)
												(potActions changeState: 1)
												(Print 65 20)
											else
												(Print 65 19)
											)
										else
											(Print 65 26)
										)	
									else
										(Print 65 31)	
									)
								)
								((Said '/skull,death[/cauldron,pot]') 
									(if ((inventory at: iSkull) ownedBy: ego)
										(if ((inventory at: iTooth) ownedBy: 65)
											(if (ego inRect: 130 110 230 125)
												(ego setScript: potActions)
												(potActions changeState: 2)
												(Print 65 21)
											else
												(Print 65 19)
											)
										else
											(Print 65 26)
										
										)
									else
										(Print 65 31)	
									)
								)
								((Said '/virginity,love[/cauldron,pot]') 
									(if ((inventory at: iVirginity) ownedBy: ego)
										(if ((inventory at: iTooth) ownedBy: 65)
											(if (ego inRect: 130 110 230 125)
												(ego setScript: potActions)
												(potActions changeState: 3)
												(Print 65 22)
											else
												(Print 65 19)
											)
										else
											(Print 65 26)
										)
									else
										(Print 65 31)	
									)
								)
								(else
									(Print {That's not an item you can thow into the void.})
									(event claimed: 1)
								)
							)
						)
						
						((or (Said 'shout/thespr3') (Said 'thespr3'));
							(if (and
								((inventory at: iVirginity) ownedBy: 65)
								((inventory at: iSkull) ownedBy: 65)
								((inventory at: iBriefcase) ownedBy: 65)
								((inventory at: iTooth) ownedBy: 65)
								)
							
								(ego setScript: jarActions)
								(jarActions changeState: 20)
							else
								(Print 65 32)
							)
						)
						((Said 'get>')
							(cond 
								((Said '/caldron') (Print 65 14))
								((Said '/butterchurn') (Print 65 4))
								((Said '/barrel') (Print 65 7))
								((Said '/crumbs') (Print 65 15))
								((Said '/tooth,toof') 
										(if ((inventory at: iTooth) ownedBy: 65)
											(if (ego inRect: 130 110 180 125)
												(Print 65 17)
												(ego setScript: jarActions)
												(jarActions changeState: 2)
											else
												(Print 65 16)
											)
										else
											(Print 65 33)
										)
								)
								(else (event claimed: FALSE))
							)
						)
					)
				)
			)
		)
	)
)

(instance jarActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				;((inventory at: iTooth) moveTo: 65)
				((= sparkle (Prop new:))
					view: 580
					loop: 0
					cel: 1
					posn: 208 94
					;setPri: 7
					setCycle: Forward
					;setSpeed: 5
					init:
					startUpd:
				)
				((Inventory at: iTooth) moveTo: 65)
				(toothHolder view: 577
					loop: 0
					cel: 1
					;startUpd:
					)
			)
			(2
				(toothHolder view: 577
					loop: 0
					cel: 0
					;startUpd:
					)
				((Inventory at: iTooth) moveTo: ego)
				(sparkle dispose:)
			)
			(20
				;shake 
				(ShakeScreen 5 2)
				(Print 65 34 #title {Rosella})
				
				(self cue:)
			)
			(21
				(ShakeScreen 10 3)
				(Print 65 35)
				(curRoom newRoom: 502)	
			)
		)
	)
)

(instance potActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				((Inventory at: iBriefcase) moveTo: 65)
				;candle 1 flame
				((Prop new:)
					view: 536
					loop: 1
					posn: 237 73
					setPri: 4
					init:
					setCycle: Forward
				)
			)
			(2
				((Inventory at: iSkull) moveTo: 65)
				;candle 2 flame
				((Prop new:)
					view: 536
					loop: 1
					posn: 214 73
					setPri: 4
					init:
					setCycle: Forward
				)
			)
			(3
				((Inventory at: iVirginity) moveTo: 65)
				;candle 3 flame
				((Prop new:)
					view: 536
					loop: 1
					posn: 179 73
					setPri: 4
					init:
					setCycle: Forward
				)
			)
			
		)
	)
)