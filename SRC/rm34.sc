;;; Sierra Script 1.0 - (do not remove this comment)
(script# 34)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	Room34 0
)

(local
	theWave
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

(instance Room34 of Room
	(properties
		picture 34
	)
	
	(method (init)
		(if isNightTime (= picture 134))
		(= north 31)
		(= east 35)
		(= west 33)
		(= horizon 80)
		(= isIndoors FALSE)
		(= oldHorizon horizon)
		(ego edgeHit: 0)
		(super init:)
		(self setRegions: GENESTA WATER BEACH GULL)
		(wave1
			view: 661
			isExtra: TRUE
			loop: 2
			cel: 0
			posn: 66 110
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(wave2
			view: 661
			isExtra: TRUE
			loop: 3
			cel: 0
			posn: 143 109
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(wave3
			view: 661
			isExtra: TRUE
			loop: 4
			cel: 0
			posn: 214 107
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(waves add: wave1 wave2 wave3)
		(wave1 setScript: waveActions)
		(switch prevRoomNum
			(31
				(ego posn: (ego x?) (+ horizon 2))
			)
			(33 (ego posn: 2 (ego y?)))
			(35 (ego posn: 317 (ego y?)))
		)
		(ego xStep: 3 yStep: 2 init:)
	)
	
	(method (dispose)
		(waves dispose: delete:)
		(super dispose:)
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if
				(and
					(== (event type?) saidEvent)
					(Said 'look[<around][/room,island]')
				)
				(if (== (ego view?) 2) (Print 34 0) else (Print 34 1))
			else
				FALSE
			)
		)
	)
)

(instance waveActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= theWave 0)
				(while (< theWave (waves size?))
					((View new:)
						view: ((waves at: theWave) view?)
						loop: ((waves at: theWave) loop?)
						cel: 0
						setPri: 0
						ignoreActors:
						x: ((waves at: theWave) x?)
						y: ((waves at: theWave) y?)
						init:
						addToPic:
						yourself:
					)
					(++ theWave)
				)
				(= theWave 0)
				(self changeState: 1)
			)
			(1
				((waves at: theWave) cel: 0 show: setCycle: EndLoop self)
			)
			(2
				((waves at: theWave) hide:)
				(if (< theWave (- (waves size?) 1))
					(++ theWave)
				else
					(= theWave 0)
				)
				(waveActions changeState: 1)
			)
		)
	)
)
