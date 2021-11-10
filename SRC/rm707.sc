;;; Sierra Script 1.0 - (do not remove this comment)
(script# 707)
(include game.sh)
(use Main)
(use Intrface)
(use Game)
(use Actor)
(use Sound)
(use Motion)
(use Invent)
(use System)

(public
	Room707 0
)

(local

)

(instance shaw of View
	(properties)
)

(instance Room707 of Room
	(properties
		picture 707
	)
	
	(method (init)
;;;		(= north 2)
;;;		(= south 14)
;;;		(= east 9)
;;;		(= west 7)
;;;		(= horizon 68)
		(= isIndoors FALSE)
		(super init:)
		(if (ego has: iTooth)
			(shaw 
				view: 593
				loop: 3 
				cel: 0 
				posn: 83 150 
				init: 
				addToPic:
			)
		)
		(ego view: 592 posn: 330 115 init:)
		(musicSound play:)
		(Room707 setScript: rosellaScript)
		
	
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 				
					((Said 'look>')
						(event claimed: 1)
						(Print {Genesta looks out over the ocean and admires the rugged, natural beauty of Tamir.} #at -1 50)
					)
					((Said 'get/sand')
						(event claimed: 1)
						(Print {Genesta fills her dress pockets with sand.} #at -1 50)
						(= gotItem 1)
					)
					((Said 'exit,quit,close')
						(event claimed: 1)
						(Print {Yeah, This game sucks.} #at -1 50)
						(HandsOn)
						(curRoom newRoom: 705)
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

(instance musicSound of Sound
	(properties
		number 599
	)
)

(instance rosellaScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				;(ego setMotion: MoveTo 29 142 self)
				;(Print {this print work s})
				(= seconds 5)
				
			)
			(1
				(curRoom drawPic: 708 DISSOLVE)
				(ego view: 592 setMotion: MoveTo 220 115 self setCycle: Walk)

			)
			(2
				(HandsOn)
				(Print {Welcome to Genesta Quest!} #at -1 50)	
				(Print {By Genesta of Tamir} #at -1 50)
				(Print {Beta v0.1.4.32} #at -1 50)
			)
		)
	)
)

