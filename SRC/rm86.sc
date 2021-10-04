;;; Sierra Script 1.0 - (do not remove this comment)
(script# 86)
(include game.sh)
(use Main)
(use Intrface)
(use Sound)
(use Motion)
(use Game)
(use User)
(use Actor)
(use System)

(public
	Room86 0
)

(local
	h1
	h2
	candle1
	candle2
	candle3
	door
)
(instance theMusic of Sound)

(instance doorOpenMusic of Sound
	(properties
		number 300
	)
)

(instance Room86 of Room
	(properties
		picture 86
		style (| BLACKOUT IRISOUT)
	)
	
	(method (init)
		(Load VIEW 81)
		(Load VIEW 82)
		(Load VIEW 147)
		(Load VIEW 607)
		(Load VIEW 141)
		(Load VIEW 633)
		(Load VIEW 581) ;beam
		(super init:)
		(self setRegions: LOLOTTE)
		((= door (Prop new:))
			view: 607
			posn: 173 117
			init:
			stopUpd:
		)
		((= candle1 (Prop new:))
			posn: 281 64
			view: 633
			cel: 2
			init:
			setCycle: Forward
		)
		((= candle2 (Prop new:))
			posn: 289 75
			view: 633
			cel: 0
			init:
			setCycle: Forward
		)
		((= candle3 (Prop new:))
			posn: 296 64
			view: 633
			cel: 1
			init:
			setCycle: Forward
		)
		(if (or (== prevRoomNum 87) (== prevRoomNum 0))
			(ego posn: 303 141 view: 4 xStep: 4 yStep: 2 init:)
			(if henchChasingEgo
				((= h1 (Actor new:))
					view: 141
					posn: (+ (ego x?) 40) (ego y?)
					setCycle: Walk
					illegalBits: 0
					init:
					setScript: henchChase
				)
				(theMusic number: 41 loop: 1 init:)
			)
		)
		(if (== prevRoomNum 83)
			(if (== gamePhase startingOut)
				(User canControl: FALSE canInput: FALSE)
				(ego
					view: 81
					loop: 2
					posn: 190 120
					init:
					setCycle: Walk
					setScript: toThrone
				)
			else
				(ego posn: 190 120 view: 4 setStep: 4 2 init:)
			)
		)
		(if (== prevRoomNum 92)
			(ego
				view: 81
				loop: 1
				posn: 293 141
				setStep: 4 2
				init:
				setCycle: Walk
			)
			(User canControl: FALSE canInput: FALSE)
			(self setScript: intoDungeon)
		)
		(if (== prevRoomNum 703)
			(if (< gamePhase trappedInCastle)
				(= gamePhase trappedInCastle)
			)
			(ego
				view: 4
				loop: 2
				posn: 190 125
				setStep: 4 2
				init:
				setCycle: Walk
			) 
			(door cel: -1 setCycle: BegLoop self)
			(doorOpenMusic play:)
		)
		(if (== prevRoomNum 704) ;beam me
			(if (< gamePhase trappedInCastle)
				(= gamePhase trappedInCastle)
			)
			(ego posn: 130 141 view: 581 loop: 8 cel: -1 xStep: 4 yStep: 2 setScript: beamIn init:)
		)
	)
	
	(method (doit)
		(super doit:)
		(if (and (& (ego onControl:) cBROWN) (!= gamePhase startingOut))
			(curRoom newRoom: 87)
		)
	)
	
	(method (handleEvent event)
		(return
			(cond 
				((event claimed?) (return TRUE))
				((== (event type?) saidEvent)
					(cond 
						(
							(or
								(Said 'look[<around][/noword]')
								(Said 'look/room,castle,hall')
							)
							(Print
								(Format @str 86 0
									(if (== (door cel?) 0) {a closed} else {an open})
								)
							)
						)
						((Said 'look>')
							(cond 
								((Said '/door')
									(Print 86 1)
								)
								((Said '/table')
									(Print 86 2)
								)
								((Said '/candelabra,candle')
									(Print 86 3)
								)
								((Said '/chair')
									(Print 86 4)
								)
								((Said '/wall')
									(Print 86 5)
								)
								((or (Said '/dirt') (Said '<down'))
									(Print 86 6)
								)
							)
						)
						((Said 'bang/door')
							(if (ego inRect: 171 115 204 122)
								(Print 86 7)
							else
								(Print 800 1)
							)
						)
						((Said 'open/door')
							(if (not (ego inRect: 171 115 204 122))
								(Print 800 1)
								
							else
								;(Print 86 8)
								(self setScript: openDoor)
							)
						)
						((Said 'close/door')
							(Print 86 9)
						)
						((Said 'unlatch/door')
							(if (ego inRect: 171 115 204 122)
								(Print 86 8)
							else
								(Print 800 1)
							)
						)
						((Said 'sit/chair')
							(Print 86 10)
						)
						((Said 'get/candelabra')
							(Print 86 11)
						)
						((Said 'beam/me')
							(curRoom setScript: beamOut)
						)
						
					)
				)
			)
		)
	)
)

(instance openDoor of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(door setCycle: EndLoop self)
				(door ignoreActors: TRUE)
				(doorOpenMusic play:)
			)
			(1
				(HandsOn)
				(if (>= gamePhase getTheUnicorn)
					(curRoom newRoom: 703)
				else
					(curRoom newRoom: 83)
				)
			)
		)
	)
)

(instance intoDungeon of Script
	(method (init who)
		(super init: who)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(ego setMotion: MoveTo 187 122 self)
			)
			(1
				(ego view: 82 loop: 0)
				(self cue:)
			)
			(2
				((= h1 (Actor new:))
					view: 147
					loop: 3
					posn: (- (ego x?) 15) (ego y?)
					init:
					moveSpeed: 1
					setCycle: EndLoop
				)
				((= h2 (Actor new:))
					view: 147
					loop: 4
					posn: (+ (ego x?) 15) (ego y?)
					setPri: (+ (ego priority?) 1)
					init:
					moveSpeed: 1
					setCycle: EndLoop
				)
				(door setCycle: EndLoop self)
			)
			(3
				(ego cel: 255 loop: 1 setCycle: EndLoop self)
			)
			(4
				(User canControl: TRUE canInput: TRUE)
				(ego setLoop: -1)
				(curRoom newRoom: 83)
			)
		)
	)
)

(instance toThrone of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(ego setMotion: MoveTo 329 130 self)
				(door cel: 4)
			)
			(1
				(= inCutscene TRUE)
				(curRoom newRoom: 92)
			)
		)
	)
)

(instance henchChase of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(theMusic number: 41 loop: 10 play:)
				(client setMotion: Chase ego 15 self)
			)
			(1
				(User canControl: 0 canInput: 0)
				(ego moveSpeed: 0 setMotion: 0)
				(theMusic number: 42 loop: 1 play:)
				(= seconds 3)
			)
			(2
				(= inCutscene TRUE)
				(curRoom newRoom: 81)
			)
		)
	)
)

(instance beamIn of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 
				(HandsOff)
				(Print {Beaming in!} #font 605 #time 3 #dispose)
				(ego 
					view: 581
					loop: 8
					cel: 6
					;posn: 130 140
					setCycle: BegLoop self
				)
			
			)	
			(1
				(ego 
					view: 4
					loop: 2
					cel: 0
					setCycle: Walk
					setMotion: 0
					setScript: 0
				)
				(HandsOn)
			)
		)
	)
)

(instance beamOut of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 
				(Print {Beaming out!} #time 2 #dispose)
				(ego 
					view: 581
					loop: 8
					cel: 0
					setMotion: 0
					setCycle: EndLoop)
					(= seconds 2)
			
			)	
			(1
				(curRoom newRoom: 704)
			)
		)
	)
)
