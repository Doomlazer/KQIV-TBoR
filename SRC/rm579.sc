;;; Sierra Script 1.0 - (do not remove this comment)
(script# 579)
(include game.sh)
(use Main)
(use Intrface)
(use Avoider)
(use Sound)
(use Motion)
(use Game)
(use Invent)
(use Actor)
(use System)

(public
	Room579 0
)

(local 
	car
	tire
	local2
	poof	
)

(instance poofSound of Sound
	(properties
		number 40
	)
)

(instance Room579 of Room
	(properties
		picture 579
		north 0
		east 24
		south 24
		west 24
	)
	
	(method (init)
		(= isIndoors TRUE)
		(super init:)
		(= car(Prop new:)) ;
				(car
					view: 563
					loop: 0
					cel: 0
					posn: 40 125
					init:
					;addToPic:
					;startUpd:
				)
				(= tire(Prop new:)) 
				(tire
					view: 563
					loop: 0
					cel: 1
					posn: 102 125
					init:
					;addToPic:
					;startUpd:
				)
		
		(if (== prevRoomNum 24)
			(ego setScript: swimIn illegalBits: cWHITE)
		else
			(ego
				view: 2
				loop: 2
				setStep: 3 2
				illegalBits: -32768
				posn: 145 115
				init:
			)
		)
		(ego init:)
		(= inCutscene FALSE)
		(if bondsEntered else (Print 100 0))
	)
	
	(method (doit)
		(super doit:)
		(= local2 (ego onControl: 1))
		(if (== (ego script?) 0)
			(cond 
;;;				(
;;;					(or
;;;						(ego inRect: 142 110 166 125)
;;;						(ego inRect: 49 117 111 128)
;;;					)
;;;					(ego setScript: sweptOut)
;;;				)



				((& local2 $0001) (= currentStatus egoNormal) (ego setCycle: Walk) (ego view: 2))
				((& local2 $0800) (ego setCycle: Walk) (ego view: 7) (= currentStatus egoInWaistDeepWater))
				((& local2 $0002) (ego view: 8) (= currentStatus egoSwimming) (ego setCycle: Forward))
				((& (ego onControl:) ctlYELLOW) (curRoom newRoom: 97))
			)
			
			;;mine
			;(if (& (ego onControl:) ctlNAVY)
;;;			(if (& (ego onControl:) clYELLOW)
;;;      			  (curRoom newRoom: 97) 
;;;				)
			;/mine	
				
		;	(if (ego inRect: 209 112 225 120)
		;		(ego baseSetter: (ScriptID 0 1))
		;	else
				(ego baseSetter: 0)
		;	)
		)
		;(if (& (ego onControl:) $0040) (curRoom newRoom: 71))
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
	
	(method (newRoom newRoomNumber)
		(HandsOn)
		(ego baseSetter: 0)
		(super newRoom: newRoomNumber)
	)
)

(instance swimIn of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(ego
					view: 372 ;377
					posn: 158 190 ;119
					setCycle: Walk
					setAvoider: Avoider
					setMotion: MoveTo 167 163 self ;167n123
				)
			)
			(1
				(ego setMotion: MoveTo 167 163 self) ;123
			)
			(2
				(ego setMotion: MoveTo 183 160 self) ;122
			)
			(3
				(ego
					view: 372 ;378
					cel: 255
					setCycle: EndLoop
					setMotion: MoveTo 207 150 self ;207 120
				)
			)
			(4
				(poofSound number: 59 play:)
				(= poof (Prop new:))
				(poof
					view: 680
					posn: (ego x?) (ego y?)
					loop: 1
					setPri: (+ (ego priority?) 1)
					cel: 9
					setCycle: CycleTo 2 -1 self
					init:
				)
			)
			(5
				(ego view: 2 setAvoider: 0 setCycle: Walk)
				(poof setCycle: BegLoop self)
			)
			(6
				(poof dispose:)
				(Print 70 21)
				(ego setScript: 0)
				(HandsOn)
			)
		)
	)
)


