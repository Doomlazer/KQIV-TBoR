;;; Sierra Script 1.0 - (do not remove this comment)
(script# 62)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use Actor)
(use Invent)
(use System)

(public
	Room62 0
)
(synonyms
	(room bedroom)
)

(local
	condom
	
)

(instance Room62 of Room
	(properties
		picture 62
		style (| BLACKOUT IRISOUT)
	)
	
	(method (init)
		(self setRegions: HAUNTED_HOUSE)
		(Load VIEW 536)
		(super init:)
		(if isNightTime
			((Prop new:)
				view: 536
				loop: 1
				cel: 0
				posn: 114 77
				init:
				setPri: 4
				setCycle: Forward
			)
			((Prop new:)
				view: 536
				loop: 2
				cel: 1
				posn: 205 77
				init:
				setPri: 4
				setCycle: Forward
			)
		)
		(if (and ((Inventory at: iCondom) ownedBy: 62) (== condomRotation 1))
			(= condom (Prop new:))
			(condom
				view: 1
				loop: 1
				cel: 0
				posn: 116 119
				init:
			;	setPri: 4
			;	setCycle: Forward
			)	
		)
		(if (== prevRoomNum 59)
			(ego posn: 47 129 view: 4 xStep: 4 yStep: 2 init:)
		)
		(if (== prevRoomNum 68)
			(ego posn: 244 157 view: 4 xStep: 4 yStep: 2 init:)
		)
		(if
			(and
				(< 0 mansionPhase)
				(< mansionPhase 255)
				(== ghostRoomNum curRoomNum)
			)
			(NotifyScript HAUNTED_HOUSE -1)
		)
	)
	
	(method (doit)
		(super doit:)
		(if (& (ego onControl: FALSE) $0040) (curRoom newRoom: 68))
		(if (& (ego onControl: FALSE) $0020) (curRoom newRoom: 59))
	)
	
	(method (handleEvent event)
		(return
			(cond 
				((event claimed?) (return TRUE))
				((== (event type?) saidEvent)
					(cond 
						((Said 'look>')
							(cond 
								((Said '<under/bed')
									(if ((Inventory at: iHairpin) ownedBy: 97) 
										(if (ego inRect:100 105 222 145)
											(getHairpin changeState: 0)
										else
											(Print {Fuck, get closer to the bed first.})
										)
									else
										(Print 62 0)
									)
								)
								((Said '/bed') (Print 62 1))
								((Said '/window') (Print 62 2))
								((Said '<in/chest,dresser,drawer') (Print 62 3))
								((Said '/chest,dresser') (if (< (ego x?) 150) (Print 62 4) else (Print 62 5)))
								((Said '/wall') (Print 62 6))
								((Said '/chandelier') (Print 62 7))
								((or (Said '/dirt') (Said '<down')) 
									(if (and ((Inventory at: iCondom) ownedBy: 62) (== condomRotation 1))
										(if (ego inRect: 20 10 130 180)
											(Print {You notice a sealed condom in a purple wrapper lying on the floor, near the bed.})
										else
											(Print {You don't see much from here.})
										)
									else
										(Print 62 8)
									)
								)
								((Said '/mirror')
									(if
										(or
											(ego inRect: 19 148 80 168)
											(ego inRect: 191 133 271 162)
										)
										(Print 62 9)
									else
										(Print 62 10)
									)
								)
								((Said '/carpet') (Print 62 11))
								((Said '[<around][/room]') (Print 62 12))
							)
						)
						((Said 'open/chest,dresser,drawer') (Print 62 3))
						(
							(or
								(Said 'get[<in,on]/bed')
								(Said 'lay,sleep[<in,on,down]')
							)
							(Print 62 13)
						)
						((Said 'get/condom')
							(if (and ((Inventory at: iCondom) ownedBy: 62) (== condomRotation 1))
								(if	(< (ego distanceTo: condom) 8)
									(getCondom changeState: 0)
								else
									(Print {Not close enough.})
								)
							else
								(Print {Aren't you a bit young to have dementia, Rosella?})		
							)				
						)	
					)
				)
			)
		)
	)
)

(instance getCondom of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(ego view: 40)
				(FaceObject ego condom)
				(ego setCycle: EndLoop self)
			)
			(1
				(ego setCycle: BegLoop self)
				(Print 62 14 #icon 1 0 1)
				((Inventory at: iCondom) moveTo: ego)
				(= gotItem 1)
				(theGame changeScore: 5)
				(condom dispose:)
			)
			(2
				(ego view: 4 setScript: 0 setCycle: Walk)
				(HandsOn)
			)
		)
	)
)

(instance getHairpin of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(ego
					view: 40
					loop: (if (> (ego x?) 160) 1 else 0)
					setCycle: EndLoop self
				)
			)
			(1
				(ego 
					view: 49
					loop: (if (> (ego x?) 160) 1 else 0)
					setCycle: EndLoop self
				)
			)
			(2
				(Print 62 15 #icon 590 0 0)
				(ego get: iHairpin)
				(theGame changeScore: 20) 
				(= gotItem 1)
				(self cue:)	
			)
			(3
				(ego setCycle: BegLoop self)

			)
			(4
				(ego
					view: 41
					loop: (if (> (ego x?) 160) 1 else 0)
					setCycle: EndLoop self
				)	
			)
			(5
				(ego view: 4
					setScript: 0
					setCycle: Walk
				)
				(HandsOn)
			)
		)
	)
)