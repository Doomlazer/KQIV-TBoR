;;; Sierra Script 1.0 - (do not remove this comment)
(script# 78)
(include game.sh)
(use Main)
(use Intrface)
(use Sound)
(use Jump)
(use Motion)
(use Game)
(use Invent)
(use User)
(use Actor)
(use System)

(public
	Room78 0
)
(synonyms
	(kiss kiss embrace)
	(cobra cobra)
)

(local
	jumpNum
	jumpIndex
	[jumpArea 18]
	gEgoViewer
	snake
	snakeState
	fruit
	board
	ripple1
	ripple2
	newProp_4
	[str 200]
)
(instance gotFruit of Sound
	(properties)
)

(instance theme of Sound
	(properties)
)

(instance charm of Sound
	(properties
		number 55
	)
)

(instance Room78 of Room
	(properties
		picture 78
	)
	
	(method (init)
		(= east 78)
		(= west 77)
		(= horizon 40)
		(= isIndoors FALSE)
		(if isNightTime (= picture 178))
		(super init:)
		(self setRegions: SWAMP)
		(Load VIEW 330)
		(Load VIEW 69)
		(Load VIEW 59)
		(if (ego has: iSilverFlute)
			(Load VIEW 888)
			(Load VIEW 55)
			(Load SOUND 55)
		)
		(if (ego has: iBoard) (Load VIEW 21) (Load VIEW 515))
		(Load SOUND 50)
		(Load SOUND 39)
		(Load SOUND 40)
		(Load VIEW 508)
		(= ripple1 (Prop new:))
		(= ripple2 (Prop new:))
		(ripple1
			isExtra: TRUE
			view: 650
			loop: 3
			cel: 1
			posn: 126 87
			setPri: 0
			setCycle: Forward
			init:
		)
		(ripple2
			isExtra: 1
			view: 650
			loop: 5
			cel: 0
			posn: 74 142
			setPri: 0
			setCycle: Forward
			init:
		)
		(= currentStatus egoOnSwampGrass)
		(switch prevRoomNum
			(west
				(ego
					view: 2
					loop: 0
					cel: 0
					posn: 3 166
					setCycle: Walk
					setScript: boardActions
				)
			)
			(0
				(ego
					view: 2
					loop: 0
					cel: 0
					posn: 3 166
					setCycle: Walk
					setScript: boardActions
				)
			)
		)
		(ego view: 2 xStep: 3 yStep: 2 init:)
		(ego edgeHit: 0)
		(= [jumpArea 0] 5)
		(= [jumpArea 1] 167)
		(= [jumpArea 2] 27)
		(= [jumpArea 3] 170)
		(= [jumpArea 4] 42)
		(= [jumpArea 5] 167)
		(= [jumpArea 6] 61)
		(= [jumpArea 7] 169)
		(= [jumpArea 8] 79)
		(= [jumpArea 9] 166)
		(= [jumpArea 10] 98)
		(= [jumpArea 11] 169)
		(= [jumpArea 12] 117)
		(= [jumpArea 13] 170)
		(= [jumpArea 14] 124)
		(= [jumpArea 15] 162)
		(if ((Inventory at: iMagicFruit) ownedBy: 78)
			(= fruit (Prop new:))
			(fruit
				view: 508
				posn: 187 146
				setLoop: 0
				cycleSpeed: 1
				setPri: 12
				setScript: fruitActions
				init:
			)
		)
		(= snake (Actor new:))
		(snake
			view: 330
			posn: 196 163
			cel: 0
			setLoop: 0
			setScript: snakeActions
			init:
		)
		(if ((Inventory at: iBoard) ownedBy: 78)
			((= board (View new:))
				view: 515
				cel: 0
				loop: 0
				ignoreActors:
				setPri: 12
				posn: 121 168
				init:
			)
			(boardActions state: 3)
		)
		(curRoom setScript: jump)
		(= inCutscene TRUE)
		(User canInput: TRUE)
	)
	
	(method (dispose)
		(DisposeScript JUMP)
		(super dispose:)
	)

	(method (handleEvent event &tmp index)
		;EO: this method has been successfully decompiled!
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((Said 'hop,hop')
						(cond 
							((> (ego x?) 109)
								(Print 78 0)
							)
							((and (== jumpNum 5) ((Inventory at: iBoard) ownedBy: 78))
								(if (and (ego inRect: 92 164 109 172) (!= (ego loop?) 1))
									(Print 78 1)
								else
									(jump changeState: 1)
								)
							)
							((== currentStatus egoIsFrog)
								(Print 78 2)
							)
							(else
								(jump changeState: 1)
							)
						)
					)
					((Said 'play/flute')
						(if (== (ego view?) 2)
							(if (ego has: 0)
								(if (== (snakeActions state?) 21)
									(snakeActions seconds: 0)
									(Print 78 3)
									(snakeActions seconds: 15)
								else
									(snakeActions changeState: 20)
								)
								(if (not global155) ;charmed snake
									(theGame changeScore: 4)
									(= global155 TRUE)
								)
							else
								(Print 800 2)
							)
						else
							(Print 800 3)
						)
					)
					((Said 'get/fruit')
						(cond 
							((ego has: iMagicFruit)
								(Print 78 4)
							)
							((and (== snakeState 2) (ego inRect: 171 157 195 167))
								(if ((Inventory at: iMagicFruit) ownedBy: 78)
									(Print 78 5)
									(gotFruit number: 50 play:)
									(fruit dispose:)
									((Inventory at: iMagicFruit) moveTo: ego)
									(theGame changeScore: 10)
								else
									(Print 78 6)
								)
							)
							(else
								(PrintNotCloseEnough)
							)
						)
					)
					((Said 'get/board')
						(cond 
							((not ((Inventory at: iBoard) ownedBy: 78))
								(Print 78 7)
							)
							((> (ego x?) 109)
								(Print 78 0)
							)
							((or (!= (ego onControl: origin) cLGREEN) (!= jumpNum 5))
								(Print 78 8)
							)
							(else
								(boardActions changeState: 10)
							)
						)
					)
					((Said 'lay,place/board')
						(cond 
							((not (ego has: iBoard))
								(PrintDontHaveIt)
							)
							((> (ego x?) 109)
								(Print 78 0)
							)
							((or (!= (ego onControl: origin) cLGREEN) (!= jumpNum 5))
								(Print 78 8)
							)
							(else
								(boardActions changeState: 1)
							)
						)
					)
					((Said 'deliver/anyword[/cobra]>')
						(if (= index (inventory saidMe:))
							(if (ego has: (inventory indexOf: index))
								(Print 78 9)
							else
								(PrintDontHaveIt)
							)
						else
							(Print 78 10)
							(event claimed: TRUE)
						)
					)
					(
						(and
							(Said 'throw/*[/cobra]>')
							(= index (inventory saidMe:))
						)
						(if (ego has: (inventory indexOf: index))
							(Print 78 11)
						else
							(event claimed: FALSE)
						)
					)
					((Said 'look>')
						(cond 
							((Said '/cobra')
								(cond 
									((== (snakeActions state?) 21)
										(Print 78 12)
									)
									(((Inventory at: iMagicFruit) ownedBy: 78)
										(Print 78 13)
									)
									(else
										(Print 78 14)
									)
								)
							)
							((Said '/forest')
								(if ((Inventory at: iMagicFruit) ownedBy: 78)
									(Print 78 15)
								else
									(Print 78 16)
								)
							)
							((Said '/fruit')
								(cond 
									(((Inventory at: iMagicFruit) ownedBy: 78)
										(Print 78 17)
									)
									((ego has: iMagicFruit)
										((Inventory at: iMagicFruit) showSelf:)
									)
									(else
										(Print 78 18)
									)
								)
							)
							((Said '/island')
								(if ((Inventory at: iMagicFruit) ownedBy: 78)
									(Print 78 19)
								else
									(Print 78 20)
								)
							)
							((Said '/grass,tuft') (Print 78 21))
							((Said '[<around][/marsh,room]')
								(Print
									(Format @str 78 22
										(if ((Inventory at: iMagicFruit) ownedBy: 78)
											{A large, glistening fruit hangs from a small branch.}
										else
											{}
										)
									)
								)
							)
						)
					)
					((Said 'anyword[/cobra]>')
						(if (Said 'converse')
							(Print 78 23)
						)
						(if (Said 'kill')
							(Print 78 24)
						)
						(if (Said 'get,capture/anyword')
							(Print 78 25)
						)
						(if (Said 'kiss')
							(Print 78 26)
						)
						(if (Said 'hit/anyword')
							(Print 78 27)
						)
					)
				)
			else
				FALSE
			)
		)
	)
)

(instance jump of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				(if (== (ego loop?) 0)
					(++ jumpNum)
					(= jumpIndex (+ jumpIndex 2))
				else
					(-- jumpNum)
					(= jumpIndex (- jumpIndex 2))
				)
				(if (== jumpNum -1) (curRoom newRoom: 77) (return))
				(HandsOff)
				(= gEgoViewer (ego viewer?))
				(ego viewer: 0)
				(ego view: 69 cel: 255 setCycle: EndLoop self)
			)
			(2 (ego setCycle: CycleTo 1 -1 self))
			(3
				(ego xStep: 6 yStep: 4)
				(cond 
					((== (ego loop?) 0)
						(ego
							setMotion:
								JumpTo
								[jumpArea jumpIndex]
								[jumpArea (+ jumpIndex 1)]
								setLoop: 2 cel: 255 setCycle: EndLoop self
						)
					)
					((== (ego loop?) 1)
						(ego
							setMotion:
								JumpTo
								[jumpArea jumpIndex]
								[jumpArea (+ jumpIndex 1)]
								setLoop: 3 cel: 255 setCycle: EndLoop self
						)
					)
					((== (ego loop?) 2)
						(ego viewer: gEgoViewer)
						(ego setMotion: JumpTo (ego x?) (+ (ego y?) 6))
					)
					((== (ego loop?) 3)
						(ego viewer: gEgoViewer)
						(ego setMotion: JumpTo (ego x?) (- (ego y?) 6))
					)
				)
			)
			(4
				(if (== jumpNum 6) (ego viewer: gEgoViewer))
				(if (== (ego loop?) 2)
					(ego cel: 255 setLoop: -1 loop: 4 setCycle: EndLoop self)
				else
					(ego cel: 255 setLoop: -1 loop: 5 setCycle: EndLoop self)
				)
			)
			(5
				(if (== (ego loop?) 4)
					(ego view: 2 loop: 0 cel: 0 xStep: 3 yStep: 2)
				else
					(ego view: 2 loop: 1 cel: 0 xStep: 3 yStep: 2)
				)
				(HandsOn)
				(ego viewer: gEgoViewer)
				(ego view: 2 setCycle: Walk)
				(if (and (== jumpNum 5) (== snakeState 0))
					(snakeActions changeState: 1)
				)
			)
			(10 (Print 78 0))
		)
	)
)

(instance snakeActions of Script
	(properties)
	
	(method (doit)
		(super doit:)
		(if
		(and (== snakeState 1) (ego inRect: 170 157 217 170))
			(= snakeState 3)
			(snakeActions changeState: 10)
		)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				(theme number: 39 play:)
				(snake setCycle: EndLoop self)
			)
			(2
				(snake setLoop: 1 setCycle: Forward)
				(= snakeState 1)
			)
			(10
				(HandsOff)
				(sounds eachElementDo: #stop 0)
				(theme number: 40 play:)
				(ego viewer: 0)
				(if (< (ego x?) 194)
					(snake cel: 255 setLoop: 2 setCycle: EndLoop self)
				else
					(snake cel: 255 setLoop: 3 setCycle: EndLoop self)
				)
			)
			(11
				(ego
					view: 59
					illegalBits: 0
					ignoreActors:
					cel: 255
					setCycle: EndLoop self
				)
			)
			(12
				(Print 78 28)
				(snake setLoop: 1 setCycle: Forward)
				(= seconds 5)
			)
			(13 (= dead TRUE))
			(20
				(= seconds 0)
				(HandsOff)
				(= currentStatus egoNormal)
				(ego view: 55 setMotion: 0 loop: 0 setCycle: Forward)
				((= newProp_4 (Prop new:))
					view: 888
					setPri: (+ (ego priority?) 1)
					cycleSpeed: 1
					setCycle: Forward
					posn: (+ (ego x?) 10) (- (ego y?) 27)
					init:
				)
				(sounds eachElementDo: #stop 0)
				(charm play: self)
				(snake setLoop: 4 setCycle: Forward)
				(= snakeState 2)
			)
			(21
				(Print 78 29 #at -1 10)
				(ego view: 2 setCycle: Walk)
				(newProp_4 dispose:)
				(= currentStatus egoOnSwampGrass)
				(= seconds 15)
				(HandsOn)
			)
			(22 (self changeState: 2))
		)
	)
)

(instance boardActions of Script
	(properties)
	
	(method (doit)
		(super doit:)
		(if (== state 3)
			(if
				(ego
					inRect: 101 (- (board y?) 3) 150 (+ (board y?) 3)
				)
				(= laidDownBoard TRUE)
			else
				(= laidDownBoard FALSE)
			)
		else
			(= laidDownBoard FALSE)
		)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				(HandsOff)
				(ego view: 21 loop: 0 cel: 255 setCycle: EndLoop self)
				(if (not putBoardOverSwamp)
					(= putBoardOverSwamp TRUE)
					(theGame changeScore: 2)
				)
			)
			(2
				(= board (View new:))
				(board
					view: 515
					cel: 0
					loop: 0
					ignoreActors:
					setPri: 12
					posn: 121 (ego y?)
					init:
				)
				((Inventory at: iBoard) moveTo: 78)
				(ego setCycle: BegLoop self)
			)
			(3
				(ego view: 2 setCycle: Walk)
				(HandsOn)
			)
			(10
				(HandsOff)
				(ego view: 21 loop: 0 cel: 255 setCycle: EndLoop self)
			)
			(11
				(board dispose:)
				((Inventory at: iBoard) moveTo: ego)
				(ego setCycle: BegLoop self)
			)
			(12
				(ego view: 2 loop: 1 setCycle: Walk)
				(HandsOn)
			)
		)
	)
)

(instance fruitActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 (= seconds (Random 2 10)))
			(1
				(fruit cel: 255 setCycle: EndLoop self)
			)
			(2
				(fruit cel: 0)
				(self changeState: 0)
			)
		)
	)
)
