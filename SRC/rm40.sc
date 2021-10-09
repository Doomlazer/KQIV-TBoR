;;; Sierra Script 1.0 - (do not remove this comment)
(script# 40)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	Room40 0
)

(local
	theWave
	ripple
	door
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

(instance Room40 of Room
	(properties
		picture 40
	)
	
	(method (init)
		(if isNightTime (= picture 140))
		(= north 37)
		(= south 32)
		(= east 41)
		(= west 39)
		(= horizon 98)
		(= isIndoors FALSE)
		(super init:)
		(self setRegions: GENESTA WATER BEACH GULL)
		(ego edgeHit: 0)
		(= ripple (Prop new:))
		(= door (View new:))
		(wave1
			view: 664
			loop: 3
			cel: 0
			posn: 40 177
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(wave2
			view: 664
			loop: 4
			cel: 2
			posn: 280 177
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(waves add: wave1 wave2)
		(wave1 setScript: waveActions)
		(ripple
			isExtra: TRUE
			view: 650
			loop: 2
			cel: 2
			posn: 61 84
			setPri: 0
			ignoreActors:
			setCycle: Forward
			init:
		)
		(if (== isNightTime 1)
			(door
				view: 613
				loop: 1
				cel: 1
				posn: 156 77
				setPri: 4
				init:
				addToPic:
			)
		else
			(door
				view: 613
				loop: 1
				cel: 0
				posn: 156 77
				setPri: 4
				init:
				addToPic:
			)
		)
		(cond 
			((== prevRoomNum 32) (ego x: 160 y: 188))
			((== prevRoomNum 39) (ego x: 1 y: (ego y?)))
			((== prevRoomNum 41) (ego x: 318 y: (ego y?)))
			((== prevRoomNum 37) (ego x: 158 y: (+ horizon (ego yStep?) 1)))
		)
		(ego xStep: 3 yStep: 2 init:)
		((ego viewer?) doit:)
	)
	
	(method (dispose)
		(waves dispose: delete:)
		(super dispose:)
	)
	
	(method (handleEvent event)
		(if
			(and
				(== (event type?) saidEvent)
				(Said 'look[<around][/room,island]')
			)
			(Print 40 0)
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
				(if detailLevel
					(self changeState: 1)
				else
					(waves eachElementDo: #addToPic)
				)
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
