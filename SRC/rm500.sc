;;; Sierra Script 1.0 - (do not remove this comment)
(script# 500)
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
(use File)

(public
	Room500 0
)

(local
	bridge
	edgar
	larry
	file
	frog
)

(instance Room500 of Room
	(properties
		picture 500
		;north 0
		;east 24
		;south 24
		;west 24
	)
	
	(method (init)
		(super init:)
		(self setScript: RoomScript)
		;(musicSound play:)
		(switch prevRoomNum
			(else 
				(ego 
					;setPri: 4 
					view: 2
					posn: 160 240 
					loop: 2
					setScript: rosellaActions
					ignoreActors:
				)
			)
		)

		(ego init:)
		
		(windowLeft init:)
		(windowCenter init:)
		(windowRight init:)
		(doorLeft init:)
		(doorRear init:)
		(doorRight init:)
		(addToPics add: progsLeft progsRear progsRight)
		(addToPics doit:)
		
		(if (not (>= frogPrinceState 5)) ;frog not kissed
			((= frog (Actor new:))
				;setScript: SonnyScript
				setScript: 0
				view: 370
				loop: 2
				illegalBits: 0
				cel: 0
				posn: 218 23
				;setPri: 4
				cycleSpeed: 10
				setCycle: Forward
				init:
			)	
		)
		
			((= edgar (Actor new:))
			;setScript: SonnyScript
			setScript: 0
			view: 107
			loop: 0
			cel: 0
			posn: 160 75
			setPri: 4
			setCycle: EndLoop
			cycleSpeed: 2
			ignoreActors: 1
		)
	)
)


(instance RoomScript of Script
	(properties)
	
	(method (doit)
		(super doit:)
		; code executed each game cycle
	)
	
		; handle Said's, etc...
		(method (handleEvent event &tmp inventorySaidMe)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((Said 'look') (Print 500 1))
					((Said 'kill/edgar') (Print {That's not implemented yet. This will be a 'bad' ending in the final release.})) ;(rosellaActions changeState: 555))
					((Said 'kill/self') (rosellaActions changeState: 666))
					((Said 'show/breasts') (Print 500 18))
	
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
			(1 

			)
		)
	)
)

(instance rosellaActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(musicSound play:)
				(= bridge (Prop new:))
				(bridge
					;setScript: propScript
					view: 567
					loop: 0
					posn: 160 180
					setPri: 6
					ignoreActors: 1
					cycleSpeed: 5
					setCycle: EndLoop self
					init:
				)

;;;				(ego
;;;					view: 2
;;;					loop: 2
;;;					x: 160
;;;					y: 220
;;;				)
			)
			(1
				(ego setMotion: MoveTo 160 120 self) 	
			)
			(2 
				(Print {Fuck. Whatever this is, it doesn't look good, Rosella!})
				(self cue:)
			)
			(3
				(windowLeft dispose:)
				(windowCenter dispose:)
				(windowRight dispose:)
				
				(doorRear setCycle: EndLoop self)
			
			)	
			(4
				(edgar
					loop: 0
					init:
					setCycle: EndLoop self
				)
				
			)
			(5
				(edgar
					loop: 1
					init:
					setCycle: EndLoop self
				)
			)
			(6
				(Print {Suddenly, Edgar materializes out of thin air.})
				(Print 500 1)
				(self cue:)	
			)
			(7
				(Print 500 2)
				(Print 500 3)
				(Print 500 16)
				(self cue:)
			)
			(8
				(doorRight 
					setCycle: EndLoop self
					)	
			)
			(9
				((= larry (Actor new:))
			view: 569
			posn: 300 104
			loop: 0
			illegalBits: 0
			;setCycle: Forward
			;setScript: larryScript
			init:
				)
				(self cue:)
			)
			(10 
				(larry setCycle: Forward setMotion: MoveTo 275 104 self)	
			)
			(11
				(larry setMotion: 0)
				(Print 500 9)
				
				(if (not (>= frogPrinceState 5)) ;frog not kissed
					(Print {"She didn't even bother breaking my curse. She just took my crown and tossed me back into the pond!"} #title {Frog Prince} #at 10 10 #font smallFont	)
				)
				(Print 500 17)
				(self cue:)	
			)
			(12
					(larry setMotion: MoveTo 300 104 self)	
			)
			(13
				(Print 500 10)
				(larry stopUpd:)
					
				(doorRight 
					setCycle: BegLoop
					)
					(= seconds 3)
				
			)
			(14
				(larry dispose:)
				(self cue:)	
			)
			
			
			(15
				
				(Print 500 8)
				(Print 500 4)
				(self cue:)	
			)
			(16
				(edgar
					loop: 2
					setCycle: EndLoop
				)	
				(= seconds 3)
			)
			(17	
				(Print 500 5)
				(Print {Rosella interupts, "No, I don't think that's what happens!"})
				(Print 500 11)
				(Print 500 12)
				(Print 500 13)
				(Print 500 14)
				(Print {Rosella interupts again, "Eww, no we don't!"})
				(Print 500 15)
				(Print 500 6)
				(Print 500 7)
				(HandsOn)
				;(self cue:)
			)
			
			(555
				(= file (File new:))
					(if (file name: "crash.txt" open: fOPENFAIL)
         			; file exisits, kill edgar
         			
					)	
				;;;NOT WORKING maybe save text in file to check	
				(if file
					(Print {allowed to kill egar})
					 else
					;file didn't exist - crash game if killing edgar
					(file name: "crash.txt" open: fOPENCREATE)
					(Print { file didnt exist. This should going to crash the game!})
					(ego illegalBits: illegalBits:)
				)
					
				(file close: dispose:)
			)
			
			(666
				(musicSound stop:)
				(gunSound play:)
				(ego 
					view: 572
					loop: 0
					setCycle: EndLoop 
				)
				(= seconds 5)	
			)
			(667
					(Print {higer consiousnuess achived!})
					(Print {this will be the 'good' ending in the final release.})
					(Print {Congratulations on beating the secret end of KQIV!"})
					(Print {As this is a beta version, please feel free to send feedback to DL@THESPR3.COM})
					(Print {Now fuck off!})
					(= dead 1)
			)
		)
	)
)

(instance gunSound of Sound
	(properties
		number 590
	)
)


(instance windowLeft of Prop
	(properties)
	
	(method (init)
		(super init:)
		(self
			setPri: 5
			view: 566
			loop: 0
			cel: 0
			posn: 80 32
			stopUpd:
		)
	)
)

(instance windowCenter of Prop
	(properties)
	
	(method (init)
		(super init:)
		(self
			setPri: 5
			view: 566
			loop: 0
			cel: 1
			posn: 160 26
			stopUpd:
		)
	)
)

(instance windowRight of Prop
	(properties)
	
	(method (init)
		(super init:)
		(self
			setPri: 5
			view: 566
			loop: 0
			cel: 2
			posn: 239 32
			stopUpd:
		)
	)
)

(instance progsLeft of PicView
	(properties
		y 30
		x 61
		view 566
		loop 1
	)
)

(instance progsRear of PicView
	(properties
		y 21
		x 152
		view 566
		loop 1
		cel 1
	)
)

(instance progsRight of PicView
	(properties
		y 29
		x 251
		view 566
		loop 1
		cel 2
	)
)

(instance doorLeft of Prop
	(properties)
	
	(method (init)
		(super init:)
		(self
			cycleSpeed: 1
			setPri: 3
			view: 567
			loop: 4
			cel: 0
			posn: 54 125
			ignoreActors: 1
			stopUpd:
		)
	)
)

(instance doorRear of Prop
	(properties)
	
	(method (init)
		(super init:)
		(self
			cycleSpeed: 1
			setPri: 3
			view: 567
			loop: 3
			cel: 0
			posn: 159 84
			ignoreActors: 1
			stopUpd:
		)
	)
)

(instance doorRight of Prop
	(properties)
	
	(method (init)
		(super init:)
		(self
			cycleSpeed: 1
			setPri: 3
			view: 567
			loop: 5
			cel: 0
			posn: 266 124
			ignoreActors: 1
			stopUpd:
		)
	)
) 

(instance doorSound of Sound
	(properties
		number 81
		priority 1
	)
) 

(instance musicSound of Sound
	(properties
		number 58
	)
)