;;; Sierra Script 1.0 - (do not remove this comment)
(script# 24)
(include game.sh)
(use Main)
(use Intrface)
(use Avoider)
(use Sound)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	Room24 0
)
(synonyms
	(pool pool lake)
)

(local
	local0
	egoFrog
	local2
	local3
	local4
	ripple1
	cascade4
	cascade5
	cascade1
	splash1
	splash2
	local11
	cascade2
	cascade3
)
(instance poofSound of Sound
	(properties
		number 59
	)
)

(instance Room24 of Room
	(properties
		picture 24
	)
	
	(method (init)
		(= north 18)
		(= south 30)
		(= west 23)
		(= east 70)
		(= horizon 85)
		(= isIndoors FALSE)
		(ego edgeHit: 0)
		(= inCutscene FALSE)
		(if isNightTime (= picture 124))
		(super init:)
		(self setRegions: WATER RIVER MOUNTAIN FOREST) ;remove forest to save mem it mostly crow.
		(Load VIEW 8)
		(if (ego has: iSmallCrown)
			(Load VIEW 680)
			(Load VIEW 370)
			(Load VIEW 371)
			(Load VIEW 372)
			(Load VIEW 377)
		)
		(= splash1 (Prop new:))
		(= splash2 (Prop new:))
		(if 
			(== detailLevel 2) 
	
			(= ripple1 (Prop new:))
			(= cascade1 (Prop new:))
			(ripple1
				isExtra: TRUE
				view: 651
				loop: 0
				cel: 2
				posn: 273 30
				setPri: 0
				setCycle: Forward
				ignoreActors:
				init:
			)
			(cascade1
				isExtra: TRUE
				view: 651
				loop: 3
				cel: 2
				posn: 251 100
				setPri: 0
				setCycle: Forward
				ignoreActors:
				init:
			)
			(= cascade2 (Prop new:))
			(= cascade3 (Prop new:))
			(cascade2
				isExtra: TRUE
				view: 653
				loop: 1
				cel: 0
				posn: 79 151
				setPri: 0
				setCycle: Forward
				ignoreActors:
				init:
			)
			(cascade3
				isExtra: TRUE
				view: 653
				loop: 2
				cel: 1
				posn: 107 158
				setPri: 0
				setCycle: Forward
				ignoreActors:
				init:
			)
		)
		(= cascade4 (Prop new:))
		(cascade4
			isExtra: TRUE
			view: 651
			loop: 1
			cel: 3
			posn: 272 57
			setPri: 0
			setCycle: Forward
			ignoreActors:
			init:
		)
		(= cascade5 (Prop new:))
		(cascade5
			isExtra: TRUE
			view: 651
			loop: 2
			cel: 2
			posn: 268 94
			setPri: 0
			setCycle: Forward
			ignoreActors:
			init:
		)
		(splash1
			isExtra: TRUE
			view: 651
			loop: 4
			cel: 2
			posn: 262 145
			setPri: 0
			setCycle: Forward
			ignoreActors:
			init:
		)
		(splash2
			isExtra: TRUE
			view: 651
			loop: 5
			cel: 2
			posn: 249 165
			setPri: 0
			setCycle: Forward
			ignoreActors:
			init:
		)
		(switch prevRoomNum
			(north
				(ego view: 2 x: 47 y: (+ horizon (ego yStep?) 1))
			)
			(west
				(ego view: 2 x: 1)
				(if (<= (ego y?) horizon)
					(ego y: (+ horizon (ego yStep?) 1))
				)
			)
			(south
				(ego view: 2 y: 188)
				(if (> (ego x?) 214) (ego x: 214))
			)
			(east
				(ego view: 8 loop: 1 posn: 207 159)
			)
			(579
				;from police quest world
				(ego setScript: egoFrogActions)
				(egoFrogActions changeState: 40)
			)
			(0 
				(ego x: 200 y: 188)
			)
		)
		(ego init:)
	)
	
	(method (doit)
		(super doit:)
		(if
			(and
				(& (ego onControl: 0) $0010)
				(== (ego view?) 8)
				(== (ego script?) 0)
			)
			(ego setScript: swept)
		)
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((Said 'look>')
						(cond 
							((Said '<in/falls') (Print 24 0))
							((Said '<under/pool,water,falls') (if (!= (ego view?) 8) (Print 24 1) else (Print 24 2)))
							((Said '<in,in/water,pool') (Print 24 3))
							((Said '<behind/falls') (Print 24 4))
							((Said '/falls') (Print 24 5))
							((Said '<in/pool') (Print 24 6))
							((Said '/pool') (Print 24 7))
							((Said '[<around][/room]') (Print 24 8))
						)
					)
					((Said 'enter<behind/falls') (Print 24 9))
					((Said 'bathe,dive<under/pool,water,falls')
						(cond 
							((== (ego view?) 8) (Print 24 10))
							((!= (ego view?) 2) (Print 24 11))
							(else (Print 24 12))
						)
					)
					((Said 'bathe,dive,wade')
						(cond 
							((== (ego view?) 2) (Print 24 13))
							((== (ego view?) 8) (Print 24 14))
							(else (Print 24 15))
						)
					)
					((Said 'dennis,place/crown')
						(if (ego has: iSmallCrown)
							(if (== (ego view?) 2)
								(ego setScript: egoFrogActions)
								(egoFrogActions changeState: 1)
								(if (== woreFrogCrown FALSE)
									(theGame changeScore: 5)
									(= woreFrogCrown TRUE)
								)
							else
								(Print 24 16)
							)
						else
							(PrintDontHaveIt)
						)
					)
				)
			else
				FALSE
			)
		)
	)
	
	(method (newRoom newRoomNumber)
		(ego viewer: 0)
		(super newRoom: newRoomNumber)
	)
)

(instance egoFrogActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				(HandsOff)
				(ego setMotion: 0)
				(poofSound play:)
				(= egoFrog (Prop new:))
				(egoFrog
					posn: (ego x?) (ego y?)
					setPri: (+ (ego priority?) 1)
					view: 680
					cel: 0
					loop: 0
					ignoreActors:
					setCycle: CycleTo 5 1 self
					init:
				)
			)
			(2
				(egoFrog setCycle: EndLoop self)
				(ego view: 370 setCycle: Forward setMotion: 0)
			)
			(3
				(Timer setReal: self 3)
				(egoFrog dispose:)
			)
			(4
				(ego view: 372 setCycle: Walk setAvoider: Avoider) ;;do I need avoider here? YES, I softlocked without it  
				(ego viewer: frogViewer)
				(cond 
					(
					(or (ego inRect: 0 84 54 137) (ego inRect: 0 84 78 96)) (ego setMotion: MoveTo 38 (ego y?) self))
					((ego inRect: 53 95 155 115) (ego setMotion: MoveTo 64 (ego y?) self))
					((ego inRect: 0 115 229 148) (ego setMotion: MoveTo 151 (ego y?) self))
					((ego inRect: 0 137 79 168)
						(ego setMotion: MoveTo 63 164 self)
						(self changeState: 10)
					)
					((> (ego y?) 171) (self changeState: 10))
					(else (self changeState: 20))
				)
			)
			(5
				(ego setMotion: MoveTo 151 141 self)
			)
			(6
				(ego setMotion: MoveTo 193 166 self)
			)
			(7 (self changeState: 20))
			(10
				(ego setMotion: MoveTo 217 182 self)
			)
			(11 (self changeState: 20))
			(20
				(ego setMotion: MoveTo 223 168 self)
			)
			(21
				
			(if (ego has: iTooth)
				(ego hide:)
				(= inCutscene TRUE)
				(HandsOn)
				(curRoom newRoom: 579) ;100
			else	
				(ego hide:)
				(= inCutscene TRUE)
				(HandsOn)
				(curRoom newRoom: 70)
			)
				
			)
			(40
				(ego setMotion: 0 posn: 135 135)
				(ego hide:)
				(HandsOff)
				(poofSound play:)
				(= egoFrog (Prop new:))
				(egoFrog
					posn: (ego x?) (ego y?)
					setPri: (+ (ego priority?) 1)
					view: 680
					cel: 0
					loop: 0
					ignoreActors:
					setCycle: CycleTo 5 1 self
					init:
				)
			)
			(41
				(egoFrog dispose:)
				(ego show:
					view: 2 loop: 2)
				(HandsOn)
				(self cue:)
			)
			(42
				(Print 24 18)	
			)
		)
	)
)

(instance frogViewer of Script
	(properties)
	
	(method (doit)
		(super doit:)
		(= local4 (= local3 (ego onControl: 1)))
		(if (& local3 $0001)
			(ego view: 371)
		else
			(ego view: 377)
		)
	)
)

(instance swept of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(if (< (ego x?) 255)
					(ego
						setLoop: 0
						xStep: 4
						setMotion: MoveTo (- (ego x?) 15) (ego y?) self
					)
				else
					(ego
						setLoop: 3
						illegalBits: 0
						xStep: 4
						yStep: 3
						setMotion: MoveTo (- (ego x?) 5) (+ (ego y?) 7) self
					)
				)
				(= local2 (Timer setReal: self 3))
			)
			(1
				(if (IsObject local2) (local2 dispose: delete:))
				(Print 24 17)
				(ego
					xStep: 3
					yStep: 2
					illegalBits: -32768
					setLoop: -1
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)
