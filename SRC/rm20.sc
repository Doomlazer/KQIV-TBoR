;;; Sierra Script 1.0 - (do not remove this comment)
(script# 20)
(include game.sh)
(use Main)
(use Intrface)
(use Game)
(use Sound)

(public
	Room20 0
)

(instance Room20 of Room
	(properties
		picture 20
	)
	
	(method (init)
		(= north 14)
		(= south 26)
		(= east 21)
		(= west 19)
		(= horizon 68)
		(= isIndoors FALSE)
		(if isNightTime
			(nightSound init: play:)
		else
			(daySound init: play:)
		)
		(if isNightTime (= picture 120))
		(if (ego has: iTooth) (= picture 320))
		(super init:)
		(if (& (ego has: iTooth) isNightTime)
			(curRoom overlay: 420)
		)
		(ego view: 2 init:)
		(self setRegions: MEADOW UNICORN)
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if
				(and
					(== (event type?) saidEvent)
					(or (Said 'look/room') (Said 'look[<around][/!*]'))
				)
				(Print 20 0)
			else
				FALSE
			)
		)
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