;;; Sierra Script 1.0 - (do not remove this comment)
(script# 192)
(include game.sh)
(use Main)
(use Game)
(use Intrface)
(use Motion)
(use Sound)
(use User)
(use Actor)
(use System)

(public
	rm192 0
)

;NOTE: This is room 92's Lolotte dialog split from that script.
; This was done to ensure that room doesn't run out of heap.
(local
	lipTimer
	lolotteSpeaking
	printObj
	stolenInventory
	node
)

(procedure (LotPrint)
	;new procedure that simplifies Lolotte's dialog
	(cls)
	(= printObj
		(Print &rest
			#at 5 15
			#font smallFont
			#width 125
			#draw
			#dispose
		)
	)
	(= lolotteSpeaking TRUE)
)

(instance theMusic of Sound
	(properties
		number 44
		priority 15
	)
)

(instance rm192 of Room
	(properties
		picture 92
	)
	
	(method (init)
		(self setRegions: LOLOTTE)
;;;		(Load VIEW 82)
;;;		(Load VIEW 121)
;;;		(Load VIEW 132)
;;;		(Load VIEW 681)
;;;		(Load VIEW 81)
;;;		(Load VIEW 145)
;;;		(Load VIEW 141)
		;(Load VIEW 634)
		;(Load VIEW 512)
		(super init:)
;;;		(addToPics
;;;			add: aSconce1 aSconce2
;;;			doit:
;;;		)
;;;		(aFlame1
;;;			init:
;;;			setCycle: Forward
;;;		)
;;;		(aFlame2
;;;			init:
;;;			setCycle: Forward
;;;		)
		(lolotte
			init:
			setScript: lipLooper
		)
		(edgar init:)
		(User canControl: FALSE canInput: FALSE)
		(theMusic play:)
		(= inCutscene TRUE)
		(if (== prevRoomNum 86)	;out of the cell
			(ego
				posn: 251 131
				view: 81
				setLoop: 1
				init:
				setCycle: Walk
			)
			(lotTalk2 start: 0)
			(self setScript: lotTalk2)
		else
			(ego
				posn: 153 156
				view: 82
				setStep: 4 2
				setLoop: 0
				init:
				setCycle: Walk
			)
			(henchman
				setCycle: Walk
				init:
			)
			(curRoom setScript: walkIn)
		)
	)
	
	(method (handleEvent event)
		(if
			(and
				lolotteSpeaking
				(== (event type?) keyDown)
				(== (event message?) ENTER)
			)
			((self script?) seconds: 1)
			(= lipTimer 0)
			(= lolotteSpeaking FALSE)
		)
	)
	
	(method (newRoom n)
		(if (or (== n 80) (== n 30))
			((ScriptID LOLOTTE) keep: FALSE)
			(= noWearCrown FALSE)
		)
		(super newRoom: n)
	)
)

(instance walkIn of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(ego setMotion: MoveTo 153 137 self)
			)
			(1
				(ego cel: 1)
				(switch gamePhase
					(startingOut
						(= lolotteAlive TRUE)
						(curRoom setScript: lotTalk1)
					)
					(getTheUnicorn
						(curRoom setScript: lotTalk3)
					)
					(getTheHen
						(curRoom setScript: lotTalk4)
					)
					(getPandoraBox
						(curRoom setScript: lotTalk5)
					)
					(else
						;fail-safe error.
						(Print 92 16)
					)
				)
			)
		)
	)
)

(instance lotTalk1 of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= lipTimer 3)
				(LotPrint 92 17)
				(= seconds 7)
			)
			(1
				(= lipTimer 0)
				(LotPrint 92 18)
				(= seconds 7)
			)
			(2
				(= lipTimer 9)
				(LotPrint 92 19)
				(= seconds 10)
			)
			(3
				(LotPrint 92 20)
				(= seconds 16)
			)
			(4
				(LotPrint 92 21)
				(= seconds 8)
			)
			(5
				(= lipTimer 8)
				(LotPrint 92 22)
				(= seconds 9)
			)
			(6
				(LotPrint 92 23)
				(lolotte loop: 1 cel: 255 setCycle: EndLoop)
				(= seconds 4)
			)
			(7 ; changed from duplicate (6
				(= lolotteSpeaking FALSE)
				(ego
					view: 81
					setLoop: 0
					illegalBits: 0
					setMotion: MoveTo 325 132
				)
				(= seconds 5)
			)
			(8 ; (7 to (8 to make room for renaming duplicate (6 to (7
				;(aFlame2 setCycle: Forward)
				(cls)
				(curRoom newRoom: 86)
			)
		)
	)
)

(instance lotTalk2 of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(ego setMotion: MoveTo 153 137 self)
			)
			(1
				(ego view: 82 loop: 0 cel: 1)
				(= lipTimer 4)
				(LotPrint 92 24)
				(= seconds 7)
			)
			(2
				(= lipTimer 7)
				(LotPrint 92 25)
				(= seconds 10)
			)
			(3
				(LotPrint 92 26)
				(edgar view: 132 loop: 2 setCycle: EndLoop)
				(= seconds 6)
			)
			(4
				(= lipTimer 5)
				(edgar setCycle: BegLoop)
				(LotPrint 92 27)
				(= seconds 6)
			)
			(5
				(= lipTimer 5)
				(LotPrint 92 28)
				(= seconds 7)
			)
			(6
				(= lipTimer 3)
				;(aFlame2 setCycle: Forward)
				(LotPrint 92 29)
				(lolotte loop: 2 cel: 255 setCycle: EndLoop)
				(= seconds 6)
			)
			(7
				(= lolotteSpeaking FALSE)
				(cls)
				(= gamePhase getTheUnicorn)
				(User canControl: TRUE canInput: TRUE)
				(= inCutscene FALSE)
				(curRoom newRoom: 30)
			)
		)
	)
)

(instance lotTalk3 of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= lipTimer 6)
				(LotPrint 92 30)
				(= unicornState 99)
				(theGame changeScore: 7)
				(edgar view: 132 loop: 2 setCycle: EndLoop)
				(= seconds 7)
			)
			(1
				(= lipTimer 8)
				(LotPrint 92 31)
				(= seconds 8)
			)
			(2
				(edgar setCycle: BegLoop)
				(LotPrint 92 32)
				(= seconds 7)
			)
			(3
				(= lipTimer 12)
				(LotPrint 92 33)
				(= seconds 12)
			)
			(4
				(= lipTimer 6)
				(LotPrint 92 34)
				(= seconds 6)
			)
			(5
				;(aFlame2 setCycle: Forward)
				(LotPrint 92 35)
				(lolotte loop: 2 cel: 255 setCycle: EndLoop)
				(= seconds 5)
			)
			(6
				(cls)
				(= gamePhase getTheHen)
				(User canControl: TRUE canInput: TRUE)
				(= inCutscene FALSE)
				(curRoom newRoom: 30)
			)
		)
	)
)

(instance lotTalk4 of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= lipTimer 8)
				(LotPrint 92 36)
				(ego put: iMagicHen 84)
				(theGame changeScore: 7)
				(edgar view: 132 loop: 2 setCycle: EndLoop)
				(= seconds 10)
			)
			(1
				(LotPrint 92 37)
				(= seconds 4)
			)
			(2
				(edgar setCycle: BegLoop)
				(= lipTimer 6)
				(LotPrint 92 38)
				(= seconds 8)
			)
			(3
				(= lipTimer 8)
				(LotPrint 92 39)
				(= seconds 8)
			)
			(4
				(LotPrint 92 40)
				(= seconds 4)
			)
			(5
				(= lipTimer 6)
				(LotPrint 92 41)
				(= seconds 10)
			)
			(6
				(= lipTimer 7)
				(LotPrint 92 42)
				(= seconds 10)
			)
			(7
				(LotPrint 92 43)
				(= seconds 6)
			)
			(8
				(LotPrint 92 44)
				(= seconds 8)
			)
			(9
				(= lipTimer 8)
				(LotPrint 92 45)
				(= seconds 10)
			)
			(10
				(LotPrint 92 46)
				(lolotte loop: 2 cel: 255 setCycle: EndLoop)
				(= seconds 6)
			)
			(11
				;(aFlame2 setCycle: Forward)
				(cls)
				(= gamePhase getPandoraBox)
				(User canControl: TRUE canInput: TRUE)
				(= inCutscene FALSE)
				(curRoom newRoom: 30)
			)
		)
	)
)

(instance lotTalk5 of Script
	(method (init who)
		(super init: who)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= lipTimer 6)
				(LotPrint 92 47)
				(ego put: iPandorasBox 84)
				(theGame changeScore: 7)
				(edgar view: 132 loop: 2 setCycle: EndLoop)
				(= seconds 10)
			)
			(1
				;don't use LotPrint here
				(cls)
				(= printObj
					(Print 92 48
						#at 25 35
						#font smallFont
						#width -1
						#dispose
					)
				)
				(= lolotteSpeaking TRUE)
				(= seconds 4)
			)
			(2
				(= lipTimer 3)
				(cls)
				(edgar setCycle: BegLoop)
				(LotPrint 92 49)
				(= seconds 8)
			)
			(3
				(= lipTimer 4)
				(LotPrint 92 50)
				(= seconds 8)
			)
			(4
				(= lipTimer 6)
				(LotPrint 92 51)
				(lolotte loop: 2 cel: 255 setCycle: EndLoop)
				(= seconds 10)
			)
			(5
				(LotPrint 92 52)
				(= seconds 6)
			)
			(6
				(= seconds 1)
			)
			(7
				(heart
					posn: (edgar x?) (- (edgar y?) 35)
					init:
					setCycle: EndLoop self
				)
				(lolotte loop: 3 cel: 255 setCycle: EndLoop)
				(= lolotteSpeaking FALSE)
			)
			(8
				(heart dispose:)
				(lolotte loop: 3 cel: 3 setCycle: BegLoop)
				(= seconds 10)
			)
			(9
				(cls)
				(lolotte loop: 2 cel: 3 setCycle: BegLoop self)
			)
			(10
				(lolotte loop: 0)
				(= lipTimer 6)
				(LotPrint 92 53)
				(= seconds 10)
			)
			(11
				(cls)
				(lolotte loop: 0)
				(= lipTimer 6)
				(LotPrint 92 54)
				(= seconds 7)
			)
			(12
				(cls)
				(= lipTimer 5)
				(getShit changeState: 0)
				(LotPrint 92 55)
				(= lolotteSpeaking FALSE)
				(= seconds 10)
			)
			(13
				(cls)
				(LotPrint 92 56)
				(= node (inventory first:))
				(while node
					(if
						(and
							(= stolenInventory (NodeValue node))
							(== (stolenInventory owner?) ego)
						)
						(stolenInventory owner: 89)
					)
					(= node (inventory next: node))
				)
				(= lolotteSpeaking FALSE)
				(= seconds 8)
			)
			(14
				(= seconds 2)
			)
			(15
				(= lipTimer 6)
				;(aFlame2 setCycle: Forward)
				(LotPrint 92 57)
				(= lipTimer 3)
				(= seconds 6)
			)
			(16
				(ego
					view: 81
					illegalBits: 0
					setLoop: 1
					setMotion: MoveTo -5 132 self
				)
			)
			(17
				(cls)
				(= gamePhase endGame)
				(ego hide: illegalBits: cWHITE)
				(curRoom newRoom: 81)
			)
		)
	)
)

(instance lipLooper of Script
	(method (doit)
		(if
			(and
				(> lipTimer 0)
				(== (lolotte loop?) 0)
				(== (lolotte cycler?) 0)
			)
			(-- lipTimer)
			(lolotte setCycle: EndLoop)
		)
	)
)

(instance getShit of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(henchman posn: 290 129 setMotion: MoveTo 153 129 self)
			)
			(1
				(henchman loop: 2)
				(henchman setMotion: MoveTo 320 130 self)
			)
			(2
				(henchman dispose:)
			)
		)
	)
)

(instance lolotte of Prop
	(properties
		x 162
		y 102
		view 121
		cycleSpeed 1
		loop 0
		signal stopUpdOn
	)
)

(instance edgar of Prop
	(properties
		x 210
		y 94
		view 132
		loop 2
		cel 0
		signal stopUpdOn
	)
)

(instance henchman of Actor
	(properties
		view 141
		illegalBits 0
		x 350
		y 129
		xStep 4
		yStep 2
		signal (| ignrAct stopUpdOn)
	)
)

(instance heart of Prop
	(properties
		view 681
		cel 255
		cycleSpeed 1
	)
)

;;;(instance aSconce1 of PicView
;;;	(properties
;;;		view 634
;;;		loop 1
;;;		cel 0
;;;		x 32
;;;		y 79
;;;		priority 10
;;;		signal (| startUpdOn fixPriOn)
;;;	)
;;;)
;;;
;;;(instance aSconce2 of PicView
;;;	(properties
;;;		view 634
;;;		loop 1
;;;		cel 1
;;;		x 289
;;;		y 80
;;;		priority 10
;;;		signal fixPriOn
;;;	)
;;;)
;;;
;;;(instance aFlame1 of Prop
;;;	(properties
;;;		view 512
;;;		loop 0
;;;		x 289
;;;		y 68
;;;		priority 10
;;;		signal fixPriOn
;;;	)
;;;)
;;;
;;;(instance aFlame2 of Prop
;;;	(properties
;;;		view 512
;;;		loop 0
;;;		x 34
;;;		y 67
;;;		priority 10
;;;		signal fixPriOn
;;;	)
;;;)