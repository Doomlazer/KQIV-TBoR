;;; Sierra Script 1.0 - (do not remove this comment)
(script# 23)
(include game.sh)
(use Main)
(use Intrface)
(use Sound)
(use Motion)
(use Game)
(use Invent)
(use Actor)
(use System)

(public
	Room23 0
)

(local
	local0
	local1
	bird
	birdTimer
	wormTimer
	worm
	gEgoViewer
	ripple4
	local8
	ripple1
	ripple2
	ripple3
)
(instance fallSound of Sound
	(properties
		number 51
	)
)

(instance dizzySound of Sound
	(properties
		number 80
	)
)

(instance Room23 of Room
	(properties
		picture 23
		north 17
		east 24
		south 29
		west 22
	)
	
	(method (init)
		(= north 17)
		(= south 29)
		(= east 24)
		(= west 22)
		(= horizon 74)
		(= isIndoors FALSE)
		(ego edgeHit: 0)
		(if isNightTime (= picture 123))
		(if (ego has: iTooth) (= picture 323))
		(super init:)
		(if (& (ego has: iTooth) isNightTime)
			(curRoom overlay: 423)
		)
		(self setRegions: FOREST WATER RIVER)
		(Load VIEW 17)
		(Load VIEW 18)
		(Load SOUND 51)
		(Load SOUND 80)
		(if ((Inventory at: iWorm) ownedBy: 206)
			(Load VIEW 21)
			(Load VIEW 348)
		)
		(= ripple1 (Prop new:))
		(= ripple2 (Prop new:))
		(= ripple3 (Prop new:))
		(ripple1
			isExtra: TRUE
			view: 654
			loop: 2
			cel: 1
			posn: 75 157
			setPri: 0
			setCycle: Forward
			ignoreActors:
			init:
		)
		(ripple2
			isExtra: TRUE
			view: 654
			loop: 3
			cel: 2
			posn: 101 164
			setPri: 0
			setCycle: Forward
			ignoreActors:
			init:
		)
		(ripple3
			isExtra: TRUE
			view: 654
			loop: 4
			cel: 2
			posn: 224 115
			setPri: 0
			setCycle: Forward
			ignoreActors:
			init:
		)
		(= ripple4 (Prop new:))
		(ripple4
			isExtra: TRUE
			view: 654
			loop: 0
			cel: 0
			posn: 146 153
			setPri: 0
			setCycle: Forward
			ignoreActors:
			init:
		)
		(switch prevRoomNum
			(west (ego posn: 1 (ego y?)))
			(east
				(ego x: 318)
				(if (<= (ego y?) 109) (ego y: 99))
			)
			(north
				(ego posn: 120 (+ horizon 1))
			)
			(south (ego x: 106 y: 188))
			(0 (ego x: 180 y: 188))
		)
		(= gEgoViewer (ego viewer?))
		(if
			(and
				(<= (Random 1 100) 50)
				((Inventory at: iWorm) ownedBy: 206)
			)
			(= bird (Actor new:))
			(bird
				view: 348
				loop: 2
				cel: 0
				setScript: birdActions
				posn: 275 158
				setCycle: Forward
				illegalBits: 0
				ignoreActors:
				ignoreHorizon:
				init:
			)
			(curRoom setScript: wormActions)
		)
		(ego init:)
	)
	
	(method (doit)
		(super doit:)
		(if
			(and
				(!= (= local0 (ego onControl: 0)) local1)
				(== (ego script?) 0)
			)
			(= local1 local0)
			(if (& local0 $0010) (ego setScript: fallRed))
			(if (& local0 $0004) (ego setScript: fallSouth))
			(if (& local0 $1000) (ego setScript: shortFall))
		)
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					(
						(or
							(Said 'look/around')
							(Said 'look/room')
							(Said 'look[<around][/!*]')
						)
						(Print 23 0)
					)
					((Said 'look/cottage') (Print 23 1))
					((Said 'capture,get/earthworm')
						(if (cast contains: worm)
							(if (< (ego distanceTo: worm) 15)
								(FaceObject ego worm)
								(wormActions changeState: 10)
							else
								(Print 800 1)
							)
						else
							(Print 23 2)
						)
					)
					((Said 'look/crow,crow') (event claimed: FALSE))
					((Said 'look/robin,bird')
						(cond 
							((cast contains: bird)
								(if (== (birdActions state?) FALSE)
									(Print 23 3)
								else
									(Print 23 4)
								)
							)
							((cast contains: crow) (event claimed: FALSE))
							(else (Print 23 5))
						)
					)
					((Said 'converse/robin,bird')
						(if (== (birdActions state?) 0)
							(Print 23 6)
						else
							(Print 23 7)
						)
					)
					((Said 'kill/robin,bird') (Print 23 8))
					((Said 'capture,get,kiss/robin,bird')
						(if
						(or (cast contains: bird) (cast contains: crow))
							(Print 23 9)
						else
							(Print 23 5)
						)
					)
					((Said 'help/robin,bird') (Print 23 10))
					((Said 'deliver')
						(if (cast contains: bird)
							(Print 23 11)
						else
							(Print 23 12)
						)
					)
					((Said 'look/earthworm')
						(cond 
							(
								(and
									(cast contains: bird)
									((Inventory at: iWorm) ownedBy: 206)
									(== (birdActions state?) 0)
								)
								(Print 23 3)
							)
							(((Inventory at: iWorm) ownedBy: 23) (Print 23 13))
							((ego has: iWorm) ((Inventory at: iWorm) showSelf:))
							(else (Print 23 5))
						)
					)
					((Said 'look/dirt')
						(cond 
							(((Inventory at: iWorm) ownedBy: 23) (Print 23 13))
							(
								(and
									(== (birdActions state?) 0)
									(cast contains: bird)
								)
								(Print 23 3)
							)
							(else (Print 23 14))
						)
					)
				)
			else
				FALSE
			)
		)
	)
	
	(method (newRoom newRoomNumber)
		(timers eachElementDo: #dispose 84)
		(if ((Inventory at: iWorm) ownedBy: 23)
			((Inventory at: iWorm) moveTo: 206)
		)
		(super newRoom: newRoomNumber)
	)
)

(instance fallSouth of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(fallSound play:)
				(ego
					viewer: 0
					yStep: 6
					yStep: 6
					illegalBits: 0
					loop: (& (ego loop?) $0001)
					setCel: 0
					view: 17
					setCycle: EndLoop
					setMotion: MoveTo (ego x?) 141 self
				)
			)
			(1
				(ego xStep: 3 yStep: 2 view: 18 loop: 0 setCycle: Forward)
				(sounds eachElementDo: #stop 0)
				(dizzySound play:)
				(= seconds 4)
			)
			(2
				(ego view: 21 loop: 2 cel: 4 setCycle: BegLoop self)
			)
			(3
				(HandsOn)
				(ego
					illegalBits: -32768
					setCycle: Walk
					view: 2
					viewer: gEgoViewer
				)
				(ego setScript: 0)
			)
		)
	)
)

(instance fallRed of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(fallSound play:)
				(ego
					viewer: 0
					yStep: 6
					yStep: 6
					illegalBits: 0
					loop: (& (ego loop?) $0001)
					setCel: 0
					view: 17
					setCycle: EndLoop
					setMotion: MoveTo (ego x?) (+ (ego y?) 18) self
				)
			)
			(1
				(ego xStep: 3 yStep: 2 view: 18 loop: 0 setCycle: Forward)
				(sounds eachElementDo: #stop 0)
				(dizzySound play:)
				(= seconds 4)
			)
			(2
				(ego view: 21 loop: 2 cel: 4 setCycle: BegLoop self)
			)
			(3
				(HandsOn)
				(ego
					illegalBits: -32768
					setCycle: Walk
					view: 2
					viewer: gEgoViewer
				)
				(ego setScript: 0)
			)
		)
	)
)

(instance shortFall of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(fallSound play:)
				(ego
					viewer: 0
					view: 17
					loop: (& (ego loop?) $0001)
					setCel: 0
					setStep: 6 6
					illegalBits: 0
					setCycle: EndLoop
					setMotion: MoveTo (ego x?) (+ (ego y?) 11) self
				)
			)
			(1
				(ego view: 18 setStep: 3 2 loop: 0 setCycle: Forward)
				(sounds eachElementDo: #stop 0)
				(dizzySound play:)
				(= seconds 4)
			)
			(2
				(ego view: 21 loop: 2 cel: 4 setCycle: BegLoop self)
			)
			(3
				(ego
					illegalBits: -32768
					setCycle: Walk
					view: 2
					viewer: gEgoViewer
				)
				(HandsOn)
				(ego setScript: 0)
			)
		)
	)
)

(instance birdActions of Script
	(properties)
	
	(method (doit)
		(super doit:)
		(if
			(and
				(cast contains: bird)
				(< (bird distanceTo: ego) 30)
				(== (bird loop?) 2)
			)
			(if birdTimer (birdTimer dispose:))
			(self changeState: 1)
		)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= birdTimer (Timer setReal: self 15))
			)
			(1
				(bird cel: 255 loop: 3 setCycle: EndLoop self)
			)
			(2
				(= worm (Prop new:))
				(worm
					view: 348
					setLoop: 0
					setCycle: Forward
					ignoreActors:
					posn: (- (bird x?) 8) (bird y?)
					init:
				)
				(wormActions changeState: 1)
				(bird
					setPri: 12
					setLoop: 1
					xStep: 7
					yStep: 4
					setMotion: MoveTo 182 51 self
				)
			)
			(3
				(bird setMotion: MoveTo 20 -6 self)
			)
			(4
				(bird dispose:)
				(= bird 0)
			)
		)
	)
)

(instance wormActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				(= wormTimer (Timer setReal: self 12))
				((Inventory at: iWorm) moveTo: 23)
			)
			(2
				(worm dispose:)
				((Inventory at: iWorm) moveTo: 206)
			)
			(10
				(HandsOff)
				(wormTimer dispose:)
				(ego viewer: 0 view: 21 setCycle: EndLoop self)
			)
			(11
				(worm dispose:)
				(= worm 0)
				(ego setCycle: BegLoop self)
				(ego get: iWorm)
				(= gotItem TRUE)
				(theGame changeScore: 2)
			)
			(12
				(ego view: 2 setCycle: Walk)
				(ego viewer: gEgoViewer)
				(HandsOn)
			)
		)
	)
)
