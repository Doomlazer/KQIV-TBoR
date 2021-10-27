;;; Sierra Script 1.0 - (do not remove this comment)
(script# 705)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use User)
(use Actor)
(use System)
(use Save)

(public
	Room705 0
)

(instance Room705 of Room
	(properties
		picture scriptNumber
		north 0
		east 0
		south 47
		west 0
	)
	
	(method (init)
		(super init:)
		(self setScript: RoomScript)
		(switch prevRoomNum
			(706
				(ego posn:230 153 loop: 0)
			)
			(else 
				(ego posn: 88 170 loop: 1)
			)
		)
		(ego init:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit)
		(super doit:)
		; code executed each game cycle
	)
		
	(method (handleEvent event)
		(return
			(cond 
				((event claimed?) (return TRUE))
				((== (event type?) saidEvent)
					(cond 
						((or (Said 'look[<around][/noword]') (Said 'look/room'))
							(Print 705 0)
						)
						((Said 'use/computer')
							(if (& (ego onControl: 0) ctlYELLOW)
								;(Print {Fuck yeah.})
								(curRoom newRoom: 706)
							else
								(PrintNotCloseEnough)
							)
						)
						(else 
							(Print 704 22) ;#font 605 )
							(event claimed: TRUE)
						)
					)
				)
			)
		)
	)
)
