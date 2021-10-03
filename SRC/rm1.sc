;;; Sierra Script 1.0 - (do not remove this comment)
(script# 1)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	Room1 0
)

(local
	dolphin
	wave
	wavesAt
	gEgoViewer
)
(instance waves of List
	(properties)
)

(instance wave1 of Prop
	(properties)
)

(instance wave2 of Prop
	(properties)
)

(instance wave3 of Prop
	(properties)
)

(instance Room1 of Room
	(properties
		picture 1
	)
	
	(method (init)
		(= north 25) ;25
		(= south 7)
		(= west 31)
		(= east 2)
		(= horizon 100)
		(= isIndoors FALSE)
		(ego edgeHit: 0)
		(super init:)
		(if wifeDead (= shouldknowwifedead 1))
		(if isNightTime (curRoom overlay: 101))
		(self setRegions: BEACH WATER GULL MEADOW)
		(= gEgoViewer (ego viewer?))
		(if (== prevRoomNum 2) (= currentStatus egoNormal))
		(= wave (Prop new:))
		(wave1
			isExtra: TRUE
			view: 665
			loop: 0
			cel: 0
			posn: 203 75
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(wave2
			isExtra: TRUE
			view: 665
			loop: 1
			cel: 0
			posn: 191 115
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(wave3
			isExtra: TRUE
			view: 665
			loop: 2
			cel: 0
			posn: 191 188
			setPri: 0
			ignoreActors:
			init:
			cycleSpeed: 3
			init:
		)
		(wave
			isExtra: TRUE
			view: 650
			loop: 1
			cel: 1
			posn: 69 61
			setPri: 0
			ignoreActors:
			setCycle: Forward
			init:
		)
		(waves add: wave1 wave2 wave3)
		(wave1 setScript: waveActions)
		(switch currentStatus
			(egoNormal
				(switch prevRoomNum
					(south
						(if (> (ego x?) 128)
							(ego posn: 300 187)
						else
							(ego posn: 225 187)
						)
					)
					(north
						(ego x: 225 y: (+ horizon (ego yStep?) 1))
					)
					(0 (ego x: 220 y: 135))
					(east
						(if (<= (ego y?) horizon)
							(ego x: 318 y: (+ horizon (ego yStep?) 1))
						else
							(ego x: 318)
						)
					)
				)
			)
			(egoInShallowWater
				(switch prevRoomNum
					(south (ego x: 186 y: 187))
					(north
						(ego x: 180 y: (+ horizon (ego yStep?) 1))
					)
				)
			)
			(egoInKneeDeepWater
				(switch prevRoomNum
					(south (ego x: 145 y: 187))
					(north
						(ego x: 167 y: (+ horizon (ego yStep?) 1))
					)
				)
			)
			(egoInWaistDeepWater
				(switch prevRoomNum
					(south (ego x: 100 y: 187))
					(north
						(ego x: 149 y: (+ horizon (ego yStep?) 1))
					)
				)
			)
			(egoSwimming
				(switch prevRoomNum
					(south
						(cond 
							((> (ego x?) 78) (ego x: 30 y: 187))
							((< (ego x?) 20) (ego x: 20 y: 187))
							(else (ego x: (ego x?) y: 187))
						)
					)
					(25
						(if (> (ego x?) 78)
							(ego x: 78 y: (+ horizon (ego yStep?) 1))
						else
							(ego x: 95 y: (+ horizon (ego yStep?) 1))
						)
					)
					(31 (ego x: 1))
				)
			)
			(egoRidingDolphin
				(= inCutscene FALSE)
				(ego viewer: 0 view: 312 setScript: rideDolphin)
				(rideDolphin changeState: 1)
			)
		)
		(ego init:)
	)
	
	(method (dispose)
		(waves dispose:)
		(super dispose:)
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if
			(and (== (event type?) saidEvent) (Said 'look>'))
				(cond 
					((Said '/grass') (Print 1 0))
					((Said '/brook') (Print 1 1))
					((Said '[<around][/room]') (Print 1 2))
				)
			else
				FALSE
			)
		)
	)
)

(instance rideDolphin of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				(HandsOff)
				(ego setMotion: MoveTo 66 136 self)
			)
			(2
				(= dolphin (Actor new:))
				(= currentStatus egoSwimming)
				(ego viewer: gEgoViewer)
				(ego setStep: 3 2)
				(dolphin
					view: 311
					posn: (ego x?) (- (ego y?) 5)
					setLoop: 2
					xStep: 2
					yStep: 1
					setCycle: Forward
					init:
				)
				(= seconds 4)
			)
			(3
				(dolphin
					xStep: 4
					yStep: 3
					setLoop: 5
					ignoreHorizon:
					ignoreActors:
					moveSpeed: 1
					setMotion: MoveTo -10 100 self
				)
				(HandsOn)
				(= inCutscene FALSE)
			)
			(4
				(dolphin dispose:)
				(ego setScript: 0)
			)
		)
	)
)

(instance waveActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= wavesAt 0)
				(while (< wavesAt (waves size?))
					((View new:)
						view: ((waves at: wavesAt) view?)
						loop: ((waves at: wavesAt) loop?)
						cel: 0
						setPri: 0
						ignoreActors:
						x: ((waves at: wavesAt) x?)
						y: ((waves at: wavesAt) y?)
						init:
						addToPic:
						yourself:
					)
					(++ wavesAt)
				)
				(= wavesAt 0)
				(self changeState: 1)
			)
			(1
				((waves at: wavesAt) cel: 255 show: setCycle: EndLoop self)
			)
			(2
				((waves at: wavesAt) hide:)
				(if (< wavesAt (- (waves size?) 1))
					(++ wavesAt)
				else
					(= wavesAt 0)
				)
				(waveActions changeState: 1)
			)
		)
	)
)
