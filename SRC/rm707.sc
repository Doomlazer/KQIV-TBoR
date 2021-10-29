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

(instance Room707 of Room
	(properties
		picture 707
	)
	
	(method (init)
		(= north 2)
		(= south 14)
		(= east 9)
		(= west 7)
		(= horizon 68)
		(= isIndoors FALSE)
		(super init:)

		(ego view: 592 init:)
		(musicSound play:)
		(ego setScript: rosellaScript)
		
	
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((Said '/door') (Print 8 0))
					((Said '/window>')
						(cond 
							((Said 'open') (Print 8 1))
							((Said 'break') (Print 8 2))
							((Said 'look')
								(if (ego inRect: 10 134 57 140)
									(Print 8 3)
								else
									(Print 800 1)
								)
							)
						)
					)
				)
		

				
				(if (Said 'get>')
					(cond 
						((Said '/skull')(if wifeDead
									(if wifeGraveDugUp
										(if ((Inventory at: iSkull) ownedBy: 8)
											;(ego setScript: digScript)
										else
											(Print 8 11)
										)
									else
										(Print {What skull?})	
									)
								else
									(Print {What skull?}))
						)
					)
				)
		
		
				(if (Said 'look>')
					(cond 
						((Said '/grave,cross')(if wifeDead
									(Print 8 6)
									(if wifeGraveDugUp
										(if ((Inventory at: iSkull) ownedBy: 8)
											(Print {your digging has unearthed the Fisherman's wife's skull.})
										)
									)
								else
									(Print {What grave, Nostradamus?}))
						)
						((Said '/skull')(if wifeGraveDugUp
									(if ((Inventory at: iSkull) ownedBy: 8)
									(Print 8 9 #icon 575 0 0)
									else
									(Print 8 10)
									)
								else
									(Print {What skull, Nostradamus?}))
						)
						((Said '/cottage') (Print 8 4))
						((Said '[<around][/room]') (if wifeDead
									(Print 8 7)
									(if wifeGraveDugUp
										(if ((Inventory at: iSkull) ownedBy: 8)
										(Print {your digging has unearthed the Fisherman's wife's skull.})
										)
									)
								else
									(Print 8 5)
								)
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
				;(ego setMotion: MoveTo 29 142 self)
				(Print {this print work s})
				(= seconds 5)
				
			)
			(2
				(Print {this print doesn;t work })
				(= seconds 1)
			)
			(1
				(curRoom drawPic: 708)
				(ego
					view: 47
					loop: 0
					setCycle: Forward
				)

				(ego view: 59 setCycle: Walk)

			)
		)
	)
)

