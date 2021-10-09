;;; Sierra Script 1.0 - (do not remove this comment)
(script# 14)
(include game.sh)
(use Main)
(use Intrface)
(use Game)
(use Invent)
(use Actor)

(public
	Room14 0
)

(local
	local0
)
(instance Room14 of Room
	(properties
		picture 14
	)
	
	(method (init)
		(= north 8)
		(= south 20)
		(= east 15)
		(= west 13)
		(= horizon 68)
		(= isIndoors FALSE)
		(if (<= (ego y?) horizon)
			(ego posn: (ego x?) (- horizon 2))
		)
		(if (and (== prevRoomNum 20) (> (ego x?) 209))
			(ego x: 209)
		)
		(if isNightTime (= picture 114))
		(super init:)
		(= local0
			(+
				(* (- gameHours hourLastMetMinstrel) 60)
				(- gameMinutes minutesLastMetMinstrel)
			)
		)
		(if
		(and ((Inventory at: iLute) ownedBy: 203) (>= local0 3))
			(= whereIsMinstrel
				(/ (= whereIsMinstrel (Random 1 300)) 100)
			)
		)
		(if (== whereIsMinstrel 2)
			((= minstrel (Actor new:))
				view: 174
				loop: 2
				setCel: 0
				illegalBits: 0
				posn: 168 129
				init:
			)
		)
		(self setRegions: 506 516)
		(ego view: 2 init:)
		(if (== prevRoomNum 0) (ego posn: 149 188))
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
							(Said 'smell/blossom')
						)
						(Print 14 0)
					)
					((Said 'look/stump') (Print 14 1))
						 
				)
			else
				FALSE
			)
		)
	)
	
	(method (newRoom newRoomNumber)
		(if (cast contains: minstrel)
			(= hourLastMetMinstrel gameHours)
			(= minutesLastMetMinstrel gameMinutes)
		)
		(super newRoom: newRoomNumber)
	)
)
