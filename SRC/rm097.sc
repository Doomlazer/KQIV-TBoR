;;; Sierra Script 1.0 - (do not remove this comment)
(script# 97)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use User)
(use Actor)
(use System)
(use Invent)
(use Sound)

(public
	Room097 0
)

(synonyms
	(sonny man)
	(marie woman)
)

(local	
	marie
	sonny

	waiting
	blood
	blob
	briefcase
	heart
	
	choice
	talked
)

(instance Room097 of Room
	(properties
		picture 97
		north 0
		east 0
		south 100
		west 0
	)
	

	(method (init)
		(super init:)
		(self setScript: RoomScript)
		(if (not sdead) (musicSound play:))
		(switch prevRoomNum
			(else 
				(ego posn: 240 160 loop: 1)
			)
		)
		
		(ego view: 4 init:)
		
		;chair
		((Prop new:)
				view: 558
				loop: 3
				posn: 210 111
				setPri: 7
				init:
			)
		(if marieUntied
			((= marie (Actor new:))
				;setScript: SonnyScript
				;setScript: 0
				view: 558
				loop: 1
				cel: 5
				posn: 210 111
				setPri: 7
				init:
				;setCycle: Forward
				;cycleSpeed: 5
			)
		else
			((= marie (Actor new:))
				;setScript: SonnyScript
				;setScript: 0
				view: 558
				loop: 2
				cel: 0
				posn: 210 111
				setPri: 7
				init:
				setCycle: Forward
				cycleSpeed: 5
			)
		)
		;is sonny still on a rampage?
		(if sdead
			(if arrowed
				((= sonny (Actor new:))
					;setScript: SonnyScript
					view: 561
					loop: 2
					posn: 185 111
					setPri: 7
					init:
					setCycle: Forward
					cycleSpeed: 2
				)
			else
				((= sonny (Actor new:))
					;setScript: SonnyScript
					view: 559
					loop: 4
					cel: 10
					posn: 185 111
					setPri: 7
					init:
					;setCycle: Forward
					;cycleSpeed: 2
				)
				((= blood (Prop new:))
					view: 558
					loop: 6
					cel: 8
					posn: 165 80
					setPri: 6
					init:
					;setCycle: EndLoop
					;cycleSpeed: 1
				)
			)

		else
			((= sonny (Actor new:))
				setScript: SonnyScript
				view: 560
				loop: 0
				cel: 0
				posn: 185 111
				setPri: 7
				init:
				;setCycle: EndLoop
				cycleSpeed: 2
			)
		)
		
		;blob
		((= blob (Prop new:))
				view: 563
				loop: 3
				cel: 0
				posn: 95 115
				setPri: 9
				init:
				setCycle: EndLoop
				cycleSpeed: 1
		)
		(if (== ((inventory at: iBriefcase) owner?) 97)
		((= briefcase (Actor new:))
				view: 574
				loop: 1
				posn: 125 132
				;setPri: 10
				init:
		)
		)
		
	)
	
)


(instance gunSound of Sound
	(properties
		number 590
	)
)

(instance musicSound of Sound
	(properties
		number 591
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit)
		(super doit:)
		(if waiting (if (not sdead)  (FaceObject sonny ego) ) )
		(if (& (ego onControl:) ctlNAVY)
        (curRoom newRoom: 579) ;was 100 fucked up pic street
		)
	)
	

		
	
	(method (handleEvent event &tmp inventorySaidMe)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((or
						(Said 'look[<around][/!*]')
						(Said 'look/room,house')
					)
						(if (== ((inventory at: iBriefcase) owner?) 97)
							(if sdead
								(Print 97 46)
							else	
								(Print 97 1)
							)
							(Print 97  17) ; briefcase
						else 
							(Print 97 1)
						)	
					)
					((Said 'converse/sonny') 
						(if (and (not talked)(not sdead))
							(= talked 1)	
							(talkScript changeState:1)
						else
							(Print 97 41)
						)
					)
					((Said 'converse')
						(if (not sdead)
							(Print {Who the fuck are you trying to talk to, Rosella?})
						else
							(if marieUntied
								(Print 97 43)
							else
								(Print {Untie Marie if you want her to respond.})
							)
						)	
					)
					((Said 'look/kitchen') 
						(if (ego inRect: 110 90 170 115)
							(Print 97 55)
						else
							(Print 97 59)
						)	
					)
					((Said 'converse/alien') (Print 97 42))
					((Said 'look/alien') (Print 97 22))
					((Said 'look/couch') (Print 97 21))
					((Said 'look/tv') (Print 97 23))
					((Said 'look/marie') (Print 97 2))
					((Said 'look/briefcase') (Print 97 18))
					((Said 'look/sonny') 
						(if sdead
							(if arrowed
								(Print 97 44)	
							else
								(Print 97 45)
							)
						else	
							(Print 97 5)
						)
					)
					
					((Said 'kiss/marie') (if sdead (Print 97 16) else (Print 97 3)))
					((Said 'kiss/sonny') (if sdead (Print 97 6) else (Print 97 7)))
					((Said 'show/breasts[/sonny]') 
						(if sdead
							(Print 97 47)
						else
							(Print 97 8)
						)
					)
					((Said 'show/breasts/marie') (Print 97 9))
					((Said 'shoot,kill/marie[/bow]') (Print 97 48))
					((Said 'shoot,kill/alien[/bow]') (Print 97 49))
					((Said 'shoot,kill/sonny[/bow]')
						(if sdead
							(Print 97 50)
						else
							(if (and (ego has: iCupidBow) (< ((Inventory at: iCupidBow) loop?) 2))
								((Inventory at: iCupidBow) loop: (+ ((Inventory at: iCupidBow) loop?) 1))
								(SonnyScript changeState: 20)
								(= arrowed 1)
							else
								(Print 97 51)
							)	
						)
					)
					((Said 'untie,release/marie')
						(if sdead
							(if (< (ego distanceTo: marie) 10) 
								(MarieScript changeState: 20)
							else
								(Print 97 53)
							)
						else
							(Print 97 54)
						)
					)
					((Said 'get/briefcase') 
							(if ((Inventory at: iBriefcase) ownedBy: 97)
								
								(if (ego inRect: 90 125 160 166)
								;; sonny and marie need to react to this if they are still alive
									(if sdead
										(ego setScript: getCase)
									else
										(Print {"You just signed your death warrant. Hasta la vista, little lady."})
										(SonnyScript changeState: 777)
									)	
									
								
								else
									(Print {Fuck you, get closer first.})
								)
								
							else
								(Print {Are you high?"})
							)
					)
				)
			else
				0
			)
		)
	)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0 ; Handle state changes
			)
		)
	)
)


(instance SonnyScript of Script 
(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(= seconds 2)	
			)
			(1
				;(FaceObject sonny ego)
				(if bondsEntered else (Print 97 11))
				(= seconds 2)
				
			)
			(2
				(sonny loop: 2 setCycle: EndLoop)
				(= seconds 2)
			)
			(3
				(if bondsEntered (Print {"Welcome back to the party, gal!"}) else (Print 97 10))
				(= bondsEntered 1)
				(HandsOn)
				(= seconds 1)
			)
			(4
				(Print 97 12)
				(= waiting 1)
				(= seconds 20)	
			)
			(5
				(Print 97 13)
				(self cue:)	
			)
			(6
				(HandsOff)
				(FaceObject sonny ego)
				(musicSound stop:)
				(gunSound play:)
				(blob
				view: 563
				loop: 2
				setCycle: EndLoop
				cycleSpeed: 1
				)
				(= sdead 1)
				

				
				(sonny
					view: 559
					setCycle: EndLoop self
						
				)
				
	
			)
			(7
				(ego view: 42 loop: 1)
				(sonny 
					view: 560
					loop: 0
					cel: 6)
					(FaceObject sonny marie)
				(= seconds 3)	
			)
			(8
			
				;(FaceObject sonny ego)
				(gunSound stop:)
				
				(gunSound play:)
				(sonny
					view: 559
					;loop: 4
					setCycle: EndLoop self
						
				)
				(blob
				view: 563
				loop: 2
				setCycle: EndLoop
				cycleSpeed: 1
				)
					;blood splatter
		
				((= blood (Prop new:))
				view: 558
				loop: 6
				cel: 0
				posn: 225 94
				setPri: 14
				init:
				setCycle: EndLoop
				cycleSpeed: 1
				)
			
									
				
				
	
			)
			(9
				(marie loop: 5 setCycle: EndLoop)
				(= seconds 2)
				
			)
		
			(10
				(gunSound stop:)
				
				(gunSound play:)
				(sonny 
					view: 559
					loop: 4
					setCycle: EndLoop
					cycleSpeed: 1
				)
				(blob
				view: 563
				loop: 2
				setCycle: EndLoop
				cycleSpeed: 1
				)
				((= blood (Prop new:))
				view: 558
				loop: 6
				cel: 0
				posn: 165 80
				setPri: 6
				init:
				setCycle: EndLoop
				cycleSpeed: 1
				)	
				(= seconds 3)
				
			)	
			(11
				;blob goes back to watching
				(blob
				view: 563
				loop: 3
				setCycle: EndLoop
				cycleSpeed: 1
				)
				(= seconds 5)
			)
			(12	
				(= dead 1)
					
			)	
			(20
				(FaceObject ego sonny)
				(Print 97 20)
				(ego
					view: 68
					setCycle: EndLoop self
				)
				(= sdead 1)
				
				
			)
			(21
				(ego view: 4 setMotion: 0 setCycle: Walk)
				(= heart (Prop new:))
				(heart
					view: 681
					cel: 0
					loop: 0
					setPri: 15
					posn: (sonny x?) (- (sonny y?) 15)
					setCycle: EndLoop self
					init:
				)
				(marie setCycle: EndLoop)
					
			)
			(22
				(sonny
					view: 561
					loop: 0
					setCycle: EndLoop self
				)
				(heart dispose:)
			)
			(23
				(sonny
					loop: 1
					setCycle: EndLoop self
						
				)	
			)
			(24
				(Print 97 19)
				(sonny
					view: 561
					loop: 2
					setCycle: Forward
				)
				
			)
			(663
				(musicSound stop:)
				(HandsOff)
				(sonny 
					view: 559
					loop: 4
					cel: 2
					cycleSpeed: 5
				)
				(Print 97 31 #title {Sonny})	
				(= seconds 4)	
			)
			(664
				
				(sonny 
					view: 560
					loop: 0
					cel: 6
					setCycle: BegLoop self
				)
					(Print 97 32 #title {Sonny})
					
			)
			(665
				(= seconds 3)
			)
			(666
				(sonny 
					view: 560
					loop: 0
					cel: 0
					setCycle: EndLoop self
					cycleSpeed: 1
					)	
			)
			(667
				(Print 97 33 #title {Sonny}) 
				(self cue:)	
			)
			(668
				(sonny 
					view: 559
					loop: 4
					cel: 2)
					
				(= seconds 1)	
			)
			
			(669
				
				
				(gunSound play:)
				(sonny 
					view: 559
					loop: 4
					setCycle: EndLoop
					cycleSpeed: 1
				)
				(blob
				view: 563
				loop: 2
				setCycle: EndLoop
				cycleSpeed: 1
				)
				((= blood (Prop new:))
				view: 558
				loop: 6
				cel: 0
				posn: 165 80
				setPri: 6
				init:
				setCycle: EndLoop
				cycleSpeed: 1
				)	
				(= seconds 3)
				
			)
			(670
				(HandsOn)	
			)
			(777
				(HandsOff)
				(FaceObject sonny ego)
				(musicSound stop:)
				(gunSound play:)
				(blob
				view: 563
				loop: 2
				setCycle: EndLoop
				cycleSpeed: 1
				)
				;(= sdead 1)		
				(sonny
					view: 559
					setCycle: EndLoop
					cycleSpeed: 4	
				)
				(= seconds 2)
			)
			(778
				(ego view: 42 loop: 1)
				(sonny 
					view: 560
					loop: 2 setCycle: BegLoop)
					(FaceObject sonny marie)
				(= seconds 3)	
			)
			(779
				(Print {How was that one-liner, Marie? Was that a good one?} #title {Sonny})
				(= seconds 3)
			)
			(780
				(Print {"hmmhmh... mmhhhmm} #title {Marie})
				(= seconds 3)
			)
			(781	
				(= dead 1)
			)
		)
	)
)

(instance MarieScript of Script 
(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
					
			)
			(20
				(Print 97 14)
				(self cue:)
			)
			(21
				(marie 
					loop: 1
					setCycle: EndLoop self
				)
				(= marieUntied 1)
			)
			(22
				(Print 97 15)
				
			)
				
		)
	)
)


(instance talkScript of Script 
(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
					
			)
			(1
				(if readSonnysFile
					(= choice (Print 97 24 #time 20 #button {Decieve} 1 #button {Distract} 2 #button {Honest} 3))
					(if (== choice 1)
						(self changeState: 2)
					)
					(if (== choice 2)
						(self changeState: 3)
					)
					(if (== choice 3)
						(self changeState: 4)
					)
				else
					(= choice (Print 97 24 #time 20 #button {Distract} 1 #button {Honest} 2))
					(if (== choice 1)
						(self changeState: 3)
					)
					(if (== choice 2)
						(self changeState: 4)
					)
				)				
			)
			(2
				(Print 97 25 #title {Rosella})
				(Print 97 26 #title {Sonny})
				(Print 97 27 #title {Rosella})
				(Print 97 28 #title {Sonny})
				(Print 97 29 #title {Rosella})
				(Print 97 30 #title {Sonny})
				(= sdead 1)
				(SonnyScript changeState: 663)
			)
			(3
				(Print {Avon calling!} #title {Rosella})
				(Print 97 40 #title {Sonny})
				(SonnyScript changeState: 777)	
			)
			(4
				(Print 97 34 #title {Rosella})
				(Print 97 35 #title {Rosella})
				(Print 97 36 #title {Rosella})
				(Print 97 37 #title {Sonny})
				(Print 97 38 #title {Rosella})
				(Print 97 39 #title {Sonny})
			)
		)
	)
)

(instance getCase of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(ego view: 40)
				(FaceObject ego briefcase)
				(ego setCycle: EndLoop self)
			)
			(1
				(ego setCycle: BegLoop self)
				(briefcase dispose:)
				(theGame changeScore: 69)
				((Inventory at: iBriefcase) moveTo: ego)
				(= gotItem 1)
				(Print 97 60 #icon 574 0 0 #dispose)
				(if marieUntied
					(Print 97 52 #title {Marie})	
				)
			)
			(2
				(ego view: 4 setScript: 0 setCycle: Walk)
				(HandsOn)
			)
		)
	)
)