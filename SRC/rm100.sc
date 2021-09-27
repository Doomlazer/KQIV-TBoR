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
	witch1
	witch2
	witch2Body
	witch3Body
	witch3	
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
		(Load VIEW 631)

			(Load VIEW 183)
			(Load VIEW 184)
			(Load VIEW 185)
			(Load VIEW 186)
			(Load VIEW 180)
			(Load VIEW 64)
			(Load VIEW 21)
	
		(Load VIEW 65)
		(Load VIEW 66)
		(super init:)
		(self setScript: RoomScript)
		(= isIndoors TRUE)
		(= noWearCrown TRUE)
		;(self setRegions: )
		(musicSound play:)
			((= witch2 (Actor new:))
				view: 186
				loop: 0
				cel: 5
				posn: 135 102
				setLoop: 1
				setCycle: Forward
			;	ignoreControl:
			ignoreActors:
			ignoreHorizon: ;maybe?
				init:
				;stopUpd:
			)
			((= witch2Body (Prop new:))
				view: 186
				setLoop: 3
				cel: 0
				posn: 138 121
				init:
				stopUpd:
			)
			((= witch3 (Prop new:))
				view: 184
				loop: 0
				cel: 5
				posn: 165 100
				setCycle: Forward
				init:
				;stopUpd:
			)
			((= witch3Body (Prop new:))
				view: 184
				setLoop: 2
				cel: 0
				posn: 165 120
				init:
				stopUpd:
			)
		
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
					((Said 'use/axe') (axeScript changeState: 1))
					
	
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

(instance axeScript of Script
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
;;;		(method (handleEvent event &tmp inventorySaidMe)
;;;		(if (event claimed?) (return TRUE))
;;;		(return
;;;			(if (== (event type?) saidEvent)
;;;				(cond 
;;;					((Said 'use/axe') (self changeState: 1))
;;;					;((Said 'look') (Print 100 1))
;;;					
;;;	
;;;				)
;;;			else
;;;				0
;;;			)
;;;		)
;;;	)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				)
			(1 ; Handle state changes
				(witch2 setMotion: JumpTo 115 180)
			)
		)
	)
)

(instance musicSound of Sound
	(properties
		number 595
	)
)
