;;; Sierra Script 1.0 - (do not remove this comment)
(script# 39)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	Room39 0
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

(instance Room39 of Room
	(properties
		picture 39
	)
	
	(method (init)
		(= south 32)
		(= east 40)
		(= west 31)
		(= horizon 94)
		(= isIndoors FALSE)
		(ego edgeHit: 0)
		(super init:)
		(wave1
			isExtra: 1
			view: 663
			loop: 3
			cel: 3
			posn: 146 112
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(wave2
			isExtra: 1
			view: 663
			loop: 4
			cel: 3
			posn: 214 143
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(wave3
			isExtra: 1
			view: 663
			loop: 5
			cel: 1
			posn: 275 169
			setPri: 0
			ignoreActors:
			cycleSpeed: 3
			init:
		)
		(waves add: wave1 wave2 wave3)
		(wave1 setScript: waveActions)
		(switch prevRoomNum
			(37 (ego x: 307 y: 100))
			(36 (ego x: 302 y: 100))
			(32 (ego x: 160 y: 188))
			(31 (ego posn: 40 119))
			(33
				(cond 
					((== (ego view?) 2) (ego posn: 188 (+ horizon 2)))
					((== (ego view?) 8) (ego posn: 95 (+ horizon 2)))
					((== (ego view?) 6) (ego posn: 165 (+ horizon 2)))
					((== (ego view?) 5) (ego posn: 175 (+ horizon 2)))
					((== (ego view?) 7) (ego posn: 135 (+ horizon 2)))
					(else (ego posn: 161 (+ horizon 2)))
				)
			)
			(40 (ego posn: 318 (ego y?)))
		)
		(ego xStep: 3 yStep: 2 init:)
		(self setRegions: 505 501 503 504)
	)
	
	(method (doit)
		(cond 
			((>= (ego y?) 189) (= newRoomNum 32))
			((>= (ego x?) 319) (= newRoomNum 40))
			((<= (ego x?) 0) (= newRoomNum 31))
			((<= (ego y?) horizon)
				(if (> (ego x?) 292)
					(= newRoomNum 36)
				else
					(= newRoomNum 33)
				)
			)
		)
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
			(Print 39 0)
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
