;;; Sierra Script 1.0 - (do not remove this comment)
(script# 100)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use User)
(use Actor)
(use System)
(use Invent)
(use Sound)
(use Jump)

(public
	Room100 0
)

(local 
;;;	witch1
;;;	witch2
;;;	witch2Body
;;;	witch3Body
;;;	witch3	
	barTender
	barX
;	greeted
	pan
)

(instance panCage of Cage
	(properties)
)

(instance Room100 of Room
	(properties
		picture 578
		north 0
		east 0
		south 0
		west 6
	)
	
	(method (init)
;		(Load VIEW 631)
		(Load VIEW 584)
;;;			(Load VIEW 183)
;;;			(Load VIEW 184)
;;;			(Load VIEW 185)
;;;			(Load VIEW 186)
;;;			(Load VIEW 180)
	;		(Load VIEW 64)
	;		(Load VIEW 21)
	
	;	(Load VIEW 65)
	;	(Load VIEW 66)
		(super init:)
		(self setScript: RoomScript)
		(= isIndoors TRUE)
		(= noWearCrown TRUE)
		;(self setRegions: )
		(musicSound play:)
			((= barTender (Actor new:))
				view: 584
				loop: 2
				cel: 0
				posn: 135 180
				setLoop: 1
				;setCycle: Forward
				setPri: 15
				illegalBits: 0
				ignoreActors: 
				ignoreControl: 
				;ignoreHorizon: ;maybe?
				init:
				setScript: barTenderScript
				;stopUpd:
			)
;;;			(if (ego has: 9) (Load rsVIEW 54) (Load rsVIEW 150))
				(panCage
					left: 140
					right: 185
					bottom: 120
					top: 110
					init:
				)
				(= pan (Actor new:))
				(pan
					view: 150
					loop: 2
					cel: 0
					posn: 165 115
					xStep: 2
					yStep: 1
					setScript: panActions
					observeBlocks: panCage
					;setCycle: Forward
					;setMotion: Wander 15
				;	setMotion: 0
					illegalBits: 0
					;setCycle: Forward
					ignoreActors: 
					ignoreControl: 
					ignoreHorizon: 
					init:
					;yourself:
				)
;;;			((= witch2 (Actor new:))
;;;				view: 186
;;;				loop: 0
;;;				cel: 5
;;;				posn: 135 102
;;;				setLoop: 1
;;;				setCycle: Forward
;;;			;	ignoreControl:
;;;				;ignoreActors:
;;;				;ignoreHorizon: ;maybe?
;;;				illegalBits: 0
;;;				;init:
;;;				;stopUpd:
;;;			)
;;;			((= witch2Body (Prop new:))
;;;				view: 186
;;;				setLoop: 3
;;;				cel: 0
;;;				posn: 138 121
;;;				init:
;;;				stopUpd:
;;;			)
;;;			((= witch3 (Prop new:))
;;;				view: 184
;;;				loop: 0
;;;				cel: 5
;;;				posn: 165 100
;;;				setCycle: Forward
;;;				init:
;;;				;stopUpd:
;;;			)
;;;			((= witch3Body (Prop new:))
;;;				view: 184
;;;				setLoop: 2
;;;				cel: 0
;;;				posn: 165 120
;;;				init:
;;;				stopUpd:
;;;			)
		
		(switch prevRoomNum
			(6
				(ego view: 2 posn: 20 130 loop: 2)
			)
		)

		(ego init:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit)
		(super doit:)
		; code executed each game cycle

;;;		(if (& (ego onControl:) clYELLOW)
;;;			;;(if (ego inRect: 129 107 159 121)
;;;        (curRoom newRoom: 97) 
;;;		)
	)
	
		; handle Said's, etc...
		(method (handleEvent event &tmp inventorySaidMe)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((Said 'look/house') (Print 100 2))
					((Said 'look') (Print 100 1))
					;((Said 'use/axe') (axeScript changeState: 1))
					
					((Said 'talk/bartender,man') 
						(if (ego inRect: 98 165 218 185)
							(Print {"Welcome to the Space Bar. If you're low on arrows I can trade you one for 200 score points."} #title {Bartender})	
						
							(if (ego has: iCupidBow)
								(if (> ((Inventory at: iCupidBow) loop?) 0)
									(if (> score 200) 
										(if (== (Print {"Buy an arrow for 200 points?"} #button {Sure} 1 #button {Nah} 2 #title {Bartender}) 1)
											(if (> ((Inventory at: iCupidBow) loop?) 0)
												(theGame changeScore: -200)
												;(= gotItem 1) too noisy in bar for this
												((Inventory at: iCupidBow) loop: (- ((Inventory at: iCupidBow) loop?) 1))
												(Print {"Here you go. One cherub's arrow. No questions asked."} #title {Bartender})
											else
												(Print {"You already have max arrows."} #title {Bartender})
											)
										else	
											(Print {"Fuck off then."} #title {Bartender})
										)
									
									else
										(Print {"...but you don't have enough score to buy arrows."} #title {Bartender})
									)
								else
									(Print {"...but You already have two arrows. That's the legal limit. I've got to cut you off."} #title {Bartender})
								)
						else
							(Print {"...but you don't even have a cherub's bow. Come back when you get one."} #title {Bartender})
						)	
						
					else
						(Print {Get closer to the bar, Rosella. I know most people in Daventry drink in a ditch, but try to keep up!})	
					)
					)
				)
			else
				0
			)
		)
	)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0 ; Handle state changes
				
			)
		)
	)
)



(instance musicSound of Sound
	(properties
		number 595 ;688 ;595
		loop -1
	)
)


(instance barTenderScript of Script
	(properties)
	
	(method (changeState newState &tmp [temp0 2])
		(switch (= state newState)
			(0
				(= cycles 1)
			)
			(1 
				;(Print {1})
				(= seconds (Random 2 5))
			)
			(2
				;(Print {22})
				(= barX (Random 104 210))
				(if (< barX (barTender x?))
					(barTender
						setCycle: Walk
						setLoop: 1
						setMotion: MoveTo barX 180 self
					)
				else
					(barTender
						setCycle: Walk
						setLoop: 0
						setMotion: MoveTo barX 180 self
					)
				)
			)
			(3
				;(Print {333})
				(if (> (Random 0 100) 80)
					(= state 0) 
					(= cycles 1)
				else
					(= cycles 1)
				)
			)
			(4
				;(Print {4444})
				(if (> (Random 0 100) 50)
					(barTender setLoop: 3)
					(= state 0)
				else
					(barTender setLoop: 2)
					(= state 0)
				)
				;(self init:)
				(= seconds (Random 2 5))
			)
		)
	)
)

(instance panActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= seconds 4)
			)
			(1
				(pan
					observeBlocks: panCage
					view: 157
					setCycle: Forward
					setMotion: Wander 15
				)
			)
		)
	)
)
