;;; Sierra Script 1.0 - (do not remove this comment)
(script# 50)
(include game.sh)
(use Main)
(use Intrface)
(use Avoider)
(use Sound)
(use Motion)
(use Game)
(use Actor)
(use System)
(use Invent)

(public
	Room50 0
)

(local
	ogress
	local1
	smoke
	candle1
	candle2
	heart
	calcValue
)
(instance theSelection of Sound
	(properties)
)

(instance Room50 of Room
	(properties
		picture 50
		style (| BLACKOUT IRISOUT)
	)
	
	(method (init)
		(ogressChaseMusic init:)
		(ogressCatchMusic init:)
		(Load VIEW 247)
		(Load VIEW 245)
		(Load VIEW 48)
		(Load VIEW 540)
		(Load VIEW 647)
		(self setRegions: OGRE_HOUSE)
		(super init:)
		(if isNightTime
			((View new:) view: 647 loop: 2 posn: 244 95 addToPic:)
		)
		((= smoke (Prop new:))
			view: 540
			loop: 0
			cel: 1
			posn: 160 86
			init:
			cycleSpeed: 2
			setCycle: Forward
		)
		((= candle1 (Prop new:))
			view: 540
			loop: 2
			posn: 227 82
			init:
			setCycle: Forward
		)
		((= candle2 (Prop new:))
			view: 540
			loop: 2
			posn: 266 89
			init:
			setCycle: Forward
		)
		((= ogress (Actor new:)) setScript: ogressChase)
		(ego posn: 77 132 view: 2 setStep: 3 2 init:)
	)
	
	(method (doit)
		(super doit:)
		(if (& (ego onControl: FALSE) $0040) (self newRoom: 49))
	)
	
	(method (handleEvent event &tmp inventorySaidMe)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((Said 'look>')
						(cond 
							((Said '<under/table') (Print 50 0))
							((Said '/table') (Print 50 1))
							((Said '/stove') (Print 50 2))
							((Said '/window') (Print 50 3))
							((Said '/buck') (Print 50 4))
							((Said '/caldron') (Print 50 5))
							((Said '/wall') (Print 50 6))
							((or (Said '/dirt') (Said '<down')) (Print 50 7))
							((Said '/giantess') (Print 50 8))
							((Said '[<around,at][/room,cottage,kitchen]') (Print 50 9))
						)
					)
					(
					(or (Said 'converse/giantess') (Said 'converse[/noword]'))
						(if (== (ogressChase state?) 0)
							(Print 50 10)
							(ogressChase seconds: 0 changeState: 2)
						else
							(Print 50 11)
						)
					)
					((Said 'show/breasts') 
						(if (== (ogressChase state?) 0)
							(Print 50 20)
							(ogressChase seconds: 0 changeState: 2)
						else
							(Print 50 21)
						)
					)
					((Said 'get,rob/buck') (Print 50 12))
					;((Said 'kill/giantess') (Print 50 13))
					((or (Said 'shoot,kill/giantess[/bow]')(Said 'shoot/bow[/giantess]'))
						(if (== (ogressChase state?) 0)
							(if (and (ego has: iCupidBow) (< ((Inventory at: iCupidBow) loop?) 2))
								((Inventory at: iCupidBow) loop: (+ ((Inventory at: iCupidBow) loop?) 1))
								(= enteredOgreKitchen TRUE)
								(ego setScript: ogressBowScript)
								(ogressBowScript changeState: 20)
							else
								(if (ego has: iCupidBow) 
									(Print {You're out of arrows.})
								else
									(Print {Maybe try bring the bow next time?})
								)
							)	
						else
							(Print 50 24)
						)
					)
					
					((Said 'get,capture/giantess') (Print 50 14))
					((Said 'help,save/buck') (Print 50 15))
					(
						(and
							(Said 'deliver>')
							(= inventorySaidMe (inventory saidMe:))
						)
						(if (inventorySaidMe ownedBy: ego)
							(Print 50 16)
						else
							(event claimed: TRUE)
							(Print 50 17)
						)
					)
				)
			else
				FALSE
			)
		)
	)
)

(instance ogressChase of Script
	(properties)
	
	(method (doit)
		(super doit:)
		(if
		(and (< (ego distanceTo: client) 25) (== state 0) (< (ogressBowScript state?) 20))
			(= seconds 0)
			(self cue:)
		)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(client
					view: 247
					loop: 0
					posn: 173 121
					cycleSpeed: 3
					setCycle: Forward
					init:
				)
				(= seconds 5)
			)
			(1 
				(if (< (ogressBowScript state?) 20)
					(Print 50 18) 
					(self cue:)
				)
			)
			(2
				(theSelection number: 10 loop: 1 play:)
				(= enteredOgreKitchen TRUE)
				(ogress
					view: 245
					cycleSpeed: 0
					illegalBits: 0
					setCycle: Walk
					setAvoider: Avoider
					setMotion: Chase ego 15 self
				)
			)
			(3
				(theSelection number: 11 loop: 1 play:)
				(ego hide:)
				(ogress
					view: 48
					setCycle: Walk
					setMotion: MoveTo 173 125 self
				)
			)
			(4
				(Print 50 19 #at -1 10)
				(= seconds 5)
			)
			(5 (= dead TRUE))
		)
	)
)

(instance ogressChaseMusic of Sound
	(properties
		number 10
	)
)

(instance ogressCatchMusic of Sound
	(properties
		number 11
	)
)

;;;(instance playMusic of Script
;;;	(properties)
;;;	
;;;	(method (changeState newState)
;;;		(switch (= state newState)
;;;			(0
;;;				(switch theSelection
;;;					(10
;;;						(ogressChaseMusic play: self)
;;;					)
;;;					(11
;;;						(ogressCatchMusic play: self)
;;;					)
;;;				)
;;;			)
;;;			(1
;;;				(if (!= theSelection 11) (= state -1) (self cue:))
;;;			)
;;;		)
;;;	)
;;;)


(instance ogressBowScript of Script 
(properties)
	
	(method (changeState newState)
		(switch (= state newState)	
			(20
				(= seconds 0)
				(FaceObject ego ogress)
				(cls)
				(Print 50 22)
				(ego
					view: 68
					setCycle: EndLoop self
				)	
			)
			(21
				(ego view: 4 setMotion: 0 setCycle: Walk)
				(= gotItem 1)
				(= ogressShot (+ ogressShot 1))
				(= calcValue (/ 500 ogressShot))
				(theGame changeScore: calcValue)
				(= heart (Prop new:))
				(heart
					view: 681
					cel: 0
					loop: 0
					setPri: 15
					posn: (- (ogress x?) 5) (- (ogress y?) 25)
					setCycle: EndLoop self
					init:
				)
			)
			(22
				(heart dispose:)
				(= seconds 1)
			)
			(23
				(FaceObject ogress ego)
				(Print 50 23)
				(self cue:)
			
			)
			(24
				(ogressChase seconds: 0 changeState: 2)
			)
		)
	)
)