;;; Sierra Script 1.0 - (do not remove this comment)
(script# 19)
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
	Room19 0
)

(local
	saveViewer
	i
	meetTime
	decoder
)
(instance wave1 of Prop)

(instance wave2 of Prop)

(instance wave3 of Prop)

(instance waves of List)

(instance fallSound of Sound)

(define cFALL	$31d4)	;(| cLMAGENTA cLRED clGREY cGREY cRED cBROWN cGREEN)

(instance Room19 of Room
	(properties
		picture 19
	)
	
	(method (init)
		(= north 13)
		(= south 25)
		(= east 20)
		(= west 31)
		(= horizon 104)
		(= isIndoors FALSE)
		(ego edgeHit: 0)
		;(gullSound init: play:)
		(if isNightTime (= picture 119))
		(if (ego has: iTooth) (= picture 319))
		(super init:)
		(if (and (ego has: iTooth) isNightTime)
			(curRoom overlay: 419)
		)
		(= meetTime
			(+
				(* (- gameHours hourLastMetMinstrel) 60)
				(- gameMinutes minutesLastMetMinstrel)
			)
		)
		(if (and ((Inventory at: iLute) ownedBy: 203) (>= meetTime 3))
			(= whereIsMinstrel
				(/ (= whereIsMinstrel (Random 1 30)) 10)
			)
		)
		(if (== whereIsMinstrel minstrel19)
			((= minstrel (Actor new:))
				view: 174
				loop: 2
				setCel: 0
				illegalBits: 0
				posn: 293 116
				setPri: 14
				init:
			)
		)
		(self setRegions: MINSTREL BEACH WATER GULL)
		(Load VIEW 174)
		(Load VIEW 17)
		(Load VIEW 33)
		
		(if (== ((inventory at: iDecoderRing) owner?) 19)
			((= decoder (Actor new:))
			view: 573
			loop: 1
			;cel: 0
			posn: 235 180
			;setPri: 7
			init:
			setCycle: Forward
			cycleSpeed: 5
			ignoreActors:
			)
		)
		
		(wave1
			view: 666
			loop: 0
			cel: 0
			posn: 183 73
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(wave2
			view: 666
			loop: 1
			cel: 0
			posn: 188 107
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(wave3
			view: 666
			loop: 2
			cel: 0
			posn: 189 152
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(waves add: wave1 wave2 wave3)
		(wave1 setScript: waveActions)
		(Load VIEW 2)
		(Load VIEW 17)
		(Load VIEW 33)
		(if (== (ego view?) 2)
			(= currentStatus egoNormal)
		else
			(= global107 0)
		)
		(ego illegalBits: (| cWHITE cYELLOW))
		(switch currentStatus
			(egoNormal
				(switch prevRoomNum
					(25
						(if (> (ego x?) 210)
							(ego x: 293 y: 188)
							(ego illegalBits: cWHITE setPri: 14)
							(= global107 11)
						else
							(ego x: 212 y: 188)
							(= global107 0)
						)
					)
					(13
						(if (< (ego x?) 245)
							(= global107 0)
							(ego x: 215 y: (+ horizon (ego yStep?) 1))
						else
							(ego x: 286 y: (+ horizon (ego yStep?) 1))
							(ego setPri: 14 illegalBits: cWHITE)
							(= global107 11)
						)
					)
					(20
						(ego setPri: 14 illegalBits: cWHITE)
						(= global107 11)
						(= currentStatus egoNormal)
						(if (< (ego y?) horizon)
							(ego x: 318 y: (+ horizon 2))
						else
							(ego x: 318)
						)
					)
					(else 
						(= global107 11)
						(ego x: 318 y: 160)
						(ego illegalBits: cWHITE)
						(ego setPri: 14)
					)
				)
			)
			(egoInShallowWater
				(switch prevRoomNum
					(25
						(= global107 0)
						(ego x: 151 y: 188)
					)
					(13
						(= global107 0)
						(ego x: 180 y: (+ horizon (ego yStep?) 1))
					)
				)
			)
			(egoInKneeDeepWater
				(switch prevRoomNum
					(25
						(= global107 0)
						(ego x: 88 y: 188)
					)
					(13
						(= global107 0)
						(ego x: 152 y: (+ horizon (ego yStep?) 1))
					)
				)
			)
			(egoInWaistDeepWater
				(switch prevRoomNum
					(25
						(= global107 0)
						(ego x: 31 y: 188)
					)
					(13
						(= global107 0)
						(ego x: 128 y: (+ horizon (ego yStep?) 1))
					)
				)
			)
			(egoSwimming
				(switch prevRoomNum
					(25
						(ego x: 5 y: 188)
					)
					(13
						(ego x: 104 y: (+ horizon (ego yStep?) 1))
					)
					(31
						(if (< (ego y?) horizon)
							(ego x: 1 y: (+ 1 horizon))
						else
							(ego x: 1)
						)
					)
				)
			)
		)
		(ego init:)
		(= saveViewer (ego viewer?))
	)
	
	(method (doit &tmp thisControl)
		(super doit:)
		(if (== global107 11)
			(cond 
				((ego inRect: 254 104 316 115)
					(ego setPri: 12)
				)
				((and (!= currentStatus egoFalling) (== (ego edgeHit?) 0))
					(ego setPri: 14)
				)
			)
		)
		(if
			(and
				(== (curRoom script?) 0)
				(== global107 11)
				(& (= thisControl (ego onControl: 0)) cFALL)
			)
			(doFall doit:)
			(self
				setScript:
					(cond 
						((& thisControl cLMAGENTA) fallLmagenta)
						((& thisControl cLRED) fallLred)
						((& thisControl cGREY) fallGrey)
						((& thisControl cLGREY) fallLgrey)
						((& thisControl cRED) fallRed)
						((& thisControl cBROWN) fallBrown)
						((& thisControl cGREEN) fallGreen)
					)
			)
		)
	)
	
	(method (dispose)
		(waves dispose:)
		(ego setPri: -1)
		(super dispose:)
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if
			(== (event type?) saidEvent)
				(cond 
					((Said 'look/beach') 
						(if (== ((inventory at: iDecoderRing) owner?) 19)	
							(Print 19 8)
							else 
							(Print 19 2)
						)
					)
					((Said 'look/decoder') 
						(if (== ((inventory at: iDecoderRing) owner?) 19)	
							(Print 19 9)
							else
							(Print 19 9) ; do the same thing until I can open inventory items 	
							;;(iDecoderRing show:)
						)
					)
					((Said 'get/decoder') 
							(if ((Inventory at: iDecoderRing) ownedBy: 19)
								(if (ego inRect: 210 155 245 265)	
									(ego setScript: pickUpScript)
								else
									(Print {Fuck you, get closer first.})
								)						
							else
								(Print "Are you high or something, Rosella?")
							)
						)
					
					((Said 'look/grass') (Print 19 0))
					((Said 'look/cliff') (Print 19 1))
					((Said 'look/[<around][/room]') (Print 19 2))
				)
			else
				FALSE
			)
		)
	)
	
	(method (newRoom n)
		(if (cast contains: minstrel)
			(= hourLastMetMinstrel gameHours)
			(= minutesLastMetMinstrel gameMinutes)
		)
		(if (!= currentStatus egoFalling)
			(ego setPri: -1 illegalBits: cWHITE)
			(super newRoom: n)
		)
	)
)

(instance pickUpScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(ego view: 23 loop: 0 cel: 255 setCycle: EndLoop self)
				;(theGame changeScore: -667)
			)
			(1
				
				(ego get: iDecoderRing)
				(decoder dispose:)
				(Print 19 10 #icon 573 0 0 #draw)
				(ego loop: 2 cel: 255 setCycle: EndLoop self)
				(theGame changeScore: -69)
				(= gotItem 1)
			)
			(2
				(ego view: 2 setCycle: Walk)
				;(ego viewer: gEgoViewer)
				(HandsOn)
					
			)
		)
	)
)

(instance fallLmagenta of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(= currentStatus egoFalling)
				(ego
					viewer: 0
					illegalBits: 0
					setPri: 11
					yStep: 10
					setLoop: 1
					cel: 0
					view: 17
					posn: (ego x?) (- (ego y?) 4)
					setMotion: MoveTo (ego x?) (+ (ego y?) 40) self
				)
			)
			(1
				(ego view: 33 setLoop: 0 cel: 0)
				(Animate (cast elements?) FALSE)
				;(ShakeScreen 10 shakeSDown)
				(ShakeScreen 3 1)
				(= seconds 5)
			)
			(2
				(Print 19 3)
				(= dead TRUE)
			)
		)
	)
)

(instance fallLred of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(= currentStatus egoFalling)
				(ego
					viewer: 0
					setPri: 13
					yStep: 10
					setLoop: 1
					cel: 0
					view: 17
					illegalBits: 0
					setCycle: Forward
					posn: (- (ego x?) 12) (ego y?)
					setMotion: MoveTo 222 170 self
				)
			)
			(1
				(ego view: 33 setLoop: 0 cel: 0)
				(Animate (cast elements?) FALSE)
				(ShakeScreen 10 shakeSDown)
				(= seconds 5)
			)
			(2
				(Print 19 4)
				(= seconds 5)
			)
			(3
				(= dead TRUE)
			)
		)
	)
)

(instance fallGrey of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(= currentStatus egoFalling)
				(ego
					viewer: 0
					illegalBits: 0
					setPri: 13
					yStep: 10
					setLoop: 1
					cel: 0
					view: 17
					setCycle: Forward
					posn: (- (ego x?) 8) (ego y?)
					setMotion: MoveTo 263 173 self
				)
			)
			(1
				(ego view: 33 setLoop: 0 cel: 0)
				(Animate (cast elements?) FALSE)
				(ShakeScreen 10 shakeSDown)
				(= seconds 5)
			)
			(2
				(Print 19 5)
				(= seconds 5)
			)
			(3
				(= dead TRUE)
			)
		)
	)
)

(instance fallLgrey of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(= currentStatus egoFalling)
				(ego
					viewer: 0
					illegalBits: 0
					setPri: 13
					yStep: 10
					xStep: 8
					setLoop: 1
					cel: 0
					view: 17
					setCycle: Forward
					posn: (ego x?) (- (ego y?) 5)
					setMotion: MoveTo 270 173 self
				)
			)
			(1
				(ego view: 33 setLoop: 0 cel: 0)
				(Animate (cast elements?) FALSE)
				(ShakeScreen 10 shakeSDown)
				(= seconds 5)
			)
			(2
				(Print 19 4)
				(= seconds 5)
			)
			(3
				(= dead TRUE)
			)
		)
	)
)

(instance fallRed of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(= currentStatus egoFalling)
				(ego
					viewer: 0
					illegalBits: 0
					setPri: 13
					yStep: 10
					xStep: 8
					setLoop: 1
					cel: 0
					view: 17
					setCycle: Forward
					posn: (ego x?) (- (ego y?) 5)
					setMotion: MoveTo (ego x?) 178 self
				)
			)
			(1
				(ego view: 33 setLoop: 0 cel: 0)
				(Animate (cast elements?) FALSE)
				(ShakeScreen 10 shakeSDown)
				(= seconds 5)
			)
			(2
				(Print 19 4)
				(= seconds 5)
			)
			(3
				(= dead TRUE)
			)
		)
	)
)

(instance fallBrown of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(= currentStatus egoFalling)
				(ego
					viewer: 0
					illegalBits: 0
					setPri: 14
					yStep: 10
					setLoop: 1
					cel: 0
					view: 17
					setCycle: Forward
					posn: (- (ego x?) 8) (ego y?)
					setMotion: MoveTo 239 187 self
				)
			)
			(1
				(ego view: 33 setLoop: 0 cel: 0)
				(Animate (cast elements?) FALSE)
				(ShakeScreen 10 shakeSDown)
				(= seconds 3)
			)
			(2
				(Print 19 6)
				(= seconds 5)
			)
			(3
				(= dead TRUE)
			)
		)
	)
)

(instance fallGreen of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(= currentStatus egoFalling)
				(ego
					viewer: 0
					illegalBits: 0
					setPri: 14
					yStep: 10
					setLoop: 1
					cel: 0
					view: 17
					setCycle: Forward
					posn: (- (ego x?) 8) (ego y?)
					setMotion: MoveTo 240 219 self
				)
			)
			(1
				(ego view: 33 setLoop: 0 cel: 0)
				(Animate (cast elements?) FALSE)
				(ShakeScreen 10 shakeSDown)
				(= seconds 5)
			)
			(2
				(Print 19 7)
				(= seconds 5)
			)
			(3
				(= dead TRUE)
			)
		)
	)
)

(instance waveActions of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(for ((= i 0)) (< i (waves size?)) ((++ i))
					((View new:)
						view: ((waves at: i) view?)
						loop: ((waves at: i) loop?)
						cel: 0
						setPri: 0
						ignoreActors:
						x: ((waves at: i) x?)
						y: ((waves at: i) y?)
						init:
						addToPic:
						yourself:
					)
				)
				(= i 0)
				(if howFast
					(self changeState: 1)
				else
					(waves eachElementDo: #addToPic)
				)
			)
			(1
				((waves at: i) cel: 0 show: setCycle: EndLoop self)
			)
			(2
				((waves at: i) hide:)
				(if (< i (- (waves size?) 1))
					(++ i)
				else
					(= i 0)
				)
				(waveActions changeState: 1)
			)
		)
	)
)

(instance doFall of Code
	(method (doit)
		(sounds eachElementDo: #stop 0)
		(fallSound number: 51 play:)
		(if (cast contains: minstrel)
			(minstrel setCycle: 0)
		)
	)
)

;;;(instance gullSound of Sound
;;;	(properties
;;;		number 606
;;;		priority 1
;;;	)
;;;)