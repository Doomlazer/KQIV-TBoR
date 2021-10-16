;;; Sierra Script 1.0 - (do not remove this comment)
(script# 502)
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

(public
	Room502 0
)

(local
	fakerose
	hatch
	light
)

(instance Room502 of Room
	(properties
		picture 501
		;north 0
		;east 24
		;south 24
		;west 24
	)
	
	(method (init)
		(super init:)
		(self setScript: RoomScript)
		;(musicSound play:)
		(switch prevRoomNum
			(else 
				(ego 
					;setPri: 4 
					view: 2
					posn: 160 260 
					loop: 2
				)
			)
		)

		(ego init:)
		((Sound new:) number: 58 play:)
		
		(= fakerose (Actor new:))
		(fakerose
			;isExtra: TRUE
			setScript: propScript
			view: 2
			loop: 2
			;cel: 0
			posn: 145 230
			setPri: 7
			;setCycle: Forward
			cycleSpeed:15
			ignoreActors:
			init:
		)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit)
		(super doit:)
		; code executed each game cycle
	)
	
		; handle Said's, etc...
		(method (handleEvent event &tmp inventorySaidMe)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((Said 'look') (Print 100 1))
					((Said 'look/house') (Print 100 2))
	
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
				;(HandsOff)
				;(ego setMotion: MoveTo 160 80)
				;(= seconds 4)
			)
			(1 
				;(HandsOn)
				;(curRoom newRoom: 500) 	
			)
		)
	)
)

;;;(instance musicSound of Sound
;;;	(properties
;;;		number 591
;;;	)
;;;)
;;;


(instance propScript of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(HandsOff)	
				(= hatch (Actor new:))
				(hatch
					view: 564
					ignoreActors:
					setLoop: 0
					setCel: 0
					setPri: 5
					posn: 108 102
					setCycle: EndLoop self
					cycleSpeed: 5
					init:
				)
			)
			(1
						
				(= light (Actor new:))
				(light
					;setScript: propScript
					view: 565
					loop: 0
					posn: 110 189 
					setPri: 6
					ignoreActors:
					setCycle: EndLoop self
					init:
				)
				;(self cue:)
			)
			(2
				(fakerose loop: 2 cycleSpeed:5 setMotion: MoveTo 145 80 self)
				;(= seconds 4)
			)
			(3 
				(HandsOn)
				(curRoom newRoom: 500) 	
			)
		)
	)
	
	(method (handleEvent pEvent)
		(super handleEvent: pEvent)
	)
)

