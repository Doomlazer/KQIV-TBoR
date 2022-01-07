;;; Sierra Script 1.0 - (do not remove this comment)
(script# 27)
(include game.sh)
(use Main)
(use Intrface)
(use Game)
(use Sound)

(public
	Room27 0
)

(local
	[str 200]
)


(instance Room27 of Room
	(properties
		picture 27
	)
	
	(method (init)
		(= north 21)
		(= south 3)
		(= east 28)
		(= west 26)
		(= horizon 75)
		(= isIndoors FALSE)
;;;		(if isNightTime
;;;			(nightSound init: play:)
;;;		else
;;;			(daySound init: play:)
;;;		)
		(if isNightTime (= picture 127))
		(if (ego has: iTooth) (= picture 327))
		(super init:)
		(if (and (ego has: iTooth) isNightTime)
			(curRoom overlay: 427)
		)
		(ego view: 2 xStep: 3 yStep: 2 init:)
		(self setRegions: WOODS UNICORN)
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if
				(and
					(== (event type?) saidEvent)
					(or
						(Said 'look/around')
						(Said 'look/room')
						(Said 'look[<around][/!*]')
					)
				)
				(Print
					(Format @str 27 0
						(if (not isNightTime)
							{You hear birds chirping from the many trees.}
						else
							{_}
						)
					)
				)
			else
				FALSE
			)
		)
	)
	
	(method (newRoom newRoomNumber)
		(super newRoom: newRoomNumber)
	)
)

;;;(instance daySound of Sound
;;;	(properties
;;;		number 607
;;;		priority 1
;;;	)
;;;)
;;;
;;;(instance nightSound of Sound
;;;	(properties
;;;		number 608
;;;		priority 1
;;;	)
;;;)