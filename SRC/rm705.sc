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

;;;(instance Robert of Feature
;;;	(properties)
;;;	
;;;	(method (handleEvent event)
;;;		(cond 
;;;			(
;;;				(or
;;;					(event claimed?)
;;;					(!= (event type?) saidEvent)
;;;				)
;;;				(return)
;;;			)
;;;			((not (ego inRect: 56 120 120 146))
;;;				(cond 
;;;					((not (Said '/goon,henchman'))
;;;						(return)
;;;					)
;;;					(else
;;;						(PrintNotCloseEnough)
;;;					)
;;;				)
;;;			)
;;;			((Said 'look/desk')
;;;				(Print {There is nothing of interest on the desk.})
;;;			)
;;;			(
;;;				(or
;;;					(Said '/goon,henchman>')
;;;					(Said 'hello>')
;;;				)
;;;				(Print {The goon is busy and ignoring you.})
;;;			)
;;;		)
;;;	)
;;;)

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
		((View new:) ;robert
				view: 594
				posn: 92 105
				loop: 1
				cel: 0
				init:
				addToPic:
			)
		(switch prevRoomNum
			(706
				(ego view: 4 posn:220 153 loop: 1 ignoreControl: 0)
			)
			(707
				(ego view: 4 posn:220 153 loop: 1 ignoreControl: 0)
			)
			(else 
				(ego posn: 88 165 loop: 3)
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
						((Said 'look/goon,henchman')
							(Print {The goon stares blankly into space. Paper work must have fried his brain.})
						)
						((Said 'converse/goon,henchman')
							(Print {Completely unresponsive. Another casulaty of the workplace I guess.})
						)
						((Said 'look/desk')
							(Print {Nothing of interest is on the desks, except for maybe that computer.})	
						)
						((Said 'look/computer')
							(Print {Kind of looks like the computer your Father has in Daventry. He doesn't let you use it because that's where he keeps all his porn.})	
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
