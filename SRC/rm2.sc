;;; Sierra Script 1.0 - (do not remove this comment)
(script# 2)
(include game.sh)
(use Main)
(use Intrface)
(use Game)
(use Sound)

(public
	Room2 0
)

(instance Room2 of Room
	(properties
		picture 2
	)
	
	(method (init)
		(= north 26)
		(= south 8)
		(= east 3)
		(= west 1)
		(= horizon 68)
		(= isIndoors FALSE)
		(if isNightTime
			(nightSound init: play:)
		else
			(daySound init: play:)
		)
		(if isNightTime (= picture 102))
		(if (ego has: iTooth) (= picture 302))
		(super init:)
		(if (& (ego has: iTooth) isNightTime)
			(curRoom overlay: 402)
		)
		(ego view: 2 init:)
		(self setRegions: MEADOW PAN)
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if
			(and (== (event type?) saidEvent) (Said 'look>'))
				(cond 
					(
						(or
							(Said '/room')
							(Said '/around')
							(Said '[<around][/!*]')
						)
						(Print 2 0)
					)
					((Said '/brook') (Print 2 1))
				)
			else
				FALSE
			)
		)
	)
	
	(method (newRoom newRoomNumber)
		(if (cast contains: pan)
			(= hourLastMetPan gameHours)
			(= minutesLastMetPan gameMinutes)
		)
		(super newRoom: newRoomNumber)
	)
)

(instance daySound of Sound
	(properties
		number 607
		priority 1
	)
)

(instance nightSound of Sound
	(properties
		number 608
		priority 1
	)
)