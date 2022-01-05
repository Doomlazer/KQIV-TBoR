;;; Sierra Script 1.0 - (do not remove this comment)
(script# 8)
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
	Room8 0
)

(local
	grave
	skull	
)

(instance Room8 of Room
	(properties
		picture 8
	)
	
	(method (init)
		(= north 2)
		(= south 14)
		(= east 9)
		(= west 7)
		(= horizon 68)
		(= isIndoors FALSE)
		(if isNightTime (= picture 108))
		(if (ego has: iTooth) (= picture 308))
		(super init:)
		(if (& (ego has: iTooth) isNightTime)
			(curRoom overlay: 408)
		)
		(ego view: 2 init:)
		(self setRegions: MEADOW PAN)
		
		;wife grave
		(if wifeDead
			(if shouldknowwifedead
				(if wifeGraveDugUp	
					(if isNightTime
						((= grave (Prop new:))
							view: 562
							loop: 1
							cel: 1
							posn: 35 150
							setPri: 9
							ignoreActors: TRUE
							init:	
						)
					else
						((= grave (Prop new:))
							view: 562
							loop: 0
							cel: 1
							posn: 35 150
							setPri: 9
							ignoreActors: TRUE
							init:
						)
					)
				else
					(if isNightTime	
						((= grave (Prop new:))
							view: 562
							loop: 1
							cel: 0
							posn: 35 150
							setPri: 9
							ignoreActors: TRUE
							init:	
						)
					else
						((= grave (Prop new:))
							view: 562
							loop: 0
							cel: 0
							posn: 35 150
							setPri: 9
							ignoreActors: TRUE
							init:
						)
					)
				)
				(if (not (cast contains: pan))
					(musicSound play:)
				)
			)
		)
		(if wifeDead (= shouldknowwifedead 1))
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
		
				(if (Said 'dig>')
					(cond 
						((Said '/grave')
							(if wifeDead
									(if wifeGraveDugUp
										(Print 8 8)
									else
										;;ADD check for shovel
										(if ((Inventory at: iShovel) ownedBy: ego)
											(ego setScript: rosellaScript)	
										else
											(Print 8 13)
										)
									)
							else
									(Print 8 14)
							)
						)
					)
				)

				
				(if (Said 'get>')
					(cond 
						((Said '/skull')(if wifeDead
									(if wifeGraveDugUp
										(if ((Inventory at: iSkull) ownedBy: 8)
											(ego setScript: digScript)
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
		(if (cast contains: pan)
			(= hourLastMetPan gameHours)
			(= minutesLastMetPan gameMinutes)
		)
		(sounds eachElementDo: #dispose)
		(super newRoom: newRoomNumber)
	)
)

(instance musicSound of Sound
	(properties
		number 58
	)
)

(instance rosellaScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(ego setMotion: MoveTo 29 142 self)
				
			)
			(1
				(ego
					view: 47
					loop: 0
					setCycle: Forward
				)
				(= seconds 3)
			)
			(2
				(ego
					view: 47
					loop: 0
					setCycle: EndLoop self
				)
				(grave 
					cel: 1	
				) 
				(= wifeGraveDugUp 1)
				((= skull (Actor new:))

					view: 575
					loop: 1
					cel: 0
					posn: 47 143
					;setPri: 9
					init:
				)
				(ego view: 2 setCycle: Walk)
				(self cue:)
			)
		)
	)
)

(instance digScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(ego setMotion: MoveTo 29 142 self)
				
			)
			(1
				(ego
					view: 40
					loop: 0
					setCycle: EndLoop self
				)
			)
			(2
				(skull dispose:)
				;(ego view: 2 setCycle: Walk)
				(Print 8 12)
				(self cue:)
				(ego get: iSkull)
				(theGame changeScore: 111)
				(= gotItem 1)
			)
			(3
				(ego
					view: 41
					loop: 0
					setCycle: EndLoop self
				)	
			)
			(4
				(ego view: 2 setCycle: Walk)
				(self cue:)
			)
		)
	)
)