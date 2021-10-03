;;; Sierra Script 1.0 - (do not remove this comment)
(script# 703)
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
	Room703 0
)
(synonyms
	(room cell)
)

(local
	henchman
	window
	printObj
	lsTop
	lsRight
	lsBottom
	lsLeft
	saveWindow
	rapMsgNum
	rapLoop
)
(instance Room703 of Room
	(properties
		picture 703
		style (| BLACKOUT IRISOUT)
	)
	
	(method (init)
		(if (!= systemWindow Sq1Window)
			(= saveWindow systemWindow)
			(= systemWindow Sq1Window)
		)
		(Load VIEW 649)
		(Load VIEW 634)
		(Load VIEW 512)
		(self setRegions: LOLOTTE)
		(super init:)
		(= rapMsgNum 19)
		((Prop new:)
			view: 703
			loop: 6
			posn: 35 66
			setPri: 3
			init:
			setCycle: Forward
		)
		((Prop new:)
			view: 703	
			loop: 6
			posn: 267 65
			setPri: 3
			init:
			setCycle: Forward
		)
		(ego
			posn: 150 170
			view: 705
			illegalBits: cWHITE
			loop: 3
			xStep: 4
			yStep: 2
			init:
		)
	)
	
	(method (doit)
		(super doit:)
		(if (& (ego onControl: 0) cBROWN)
			(if (!= systemWindow saveWindow)
				(= systemWindow saveWindow)
				(Sq1Window dispose:)
			)
			(curRoom newRoom: 86)
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
								(Said 'look/room,castle')
							)
							(Print 703 0 #font 605)
						)
						((Said 'look>')
							(cond 
								((Said '/skeleton,bone,james') ;skelly was called james in the AGI version for some reason.
									(Print 703 1 #font 605)
								)
								((Said '/machine')
									(Print 703 2 #font 605)
								)
								((Said '/whip')
									(Print 703 3 #font 605)
								)
								((Said '/chain')
									(Print 703 35 #font 605) ;different from SCI version
								)
								((Said '/window')
									(Print 703 5 #font 605)
								)
								((Said '/wall')
									(Print 703 6 #font 605)
								)
								((or (Said '/dirt') (Said '<down'))
									(Print 703 7 #font 605)
								)
							)
						)
						((or (Said 'use,(turn<on)/machine') (Said 'turn/wheel'))
							(Print 703 8 #font 605)
						)
						((Said 'get/whip')
							(Print 703 9 #font 605)
						)
						((Said 'get/chain')
							(Print 703 4 #font 605)
						)
						((Said 'open/window')
							(Print 703 10 #font 605)
						)
						((Said 'break/window')
							(Print 703 11 #font 605)
						)
						((Said 'open/door')
							(Print 703 13 #font 605) ;different in SCI?	
						)
						((Said 'unlatch/door')
								(Print 703 13 #font 605)
						)
						((or (Said 'call/help')(Said 'save/me')) ;different from SCI version
							(Print 703 16 #font 605)
						)
						((Said 'rap/kq')
							(if (ego inRect: 100 110 200 180)
								(ego setScript: rapScript)
							else 
								(Print {The middle of the room looks like a great place to get down and rap.  You need to move closer.} #font 605)
							)
						)
						(else 
							(Print {Try saying that another way.} #font 605 )
							(event claimed: TRUE)
						)
					)
				)
			)
		)
	)
)

(instance danceScript of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				
			)
			(1
				(= rapLoop (Random 1 5))
				(ego 
					view: 703
					loop: rapLoop
					setCycle: EndLoop self
				)
			)
			(2
				(ego 
					view: 703
					loop: rapLoop
					setCycle: BegLoop self
				)
			)
			(3
				(ego 
					view: 703
					loop: rapLoop
					setCycle: EndLoop self
				)
			)
			(4
				(ego 
					view: 703
					loop: rapLoop
					setCycle: BegLoop self
				)
			)
			(5
				(= state 0)
				(= cycles 1) ;-1
			)
		)
	)
)

(instance rapScript of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(ego setMotion: MoveTo 160 140 self)
			)
			(1
				(curRoom setScript: danceScript)
				(danceScript changeState: 1)
				(= cycles 1)
			)
			(2
				(if (< rapMsgNum 34)		
					(Print 703 rapMsgNum #dispose #time 8 #font 605 #at 10 5)
					(= seconds 8)
				else 
					(= rapMsgNum 19)
					(danceScript dispose:)
					(ego 
						view: 705
						setCycle: Walk
						;cel: 0
						setMotion: 0
					)
					(HandsOn)
				)

			)
			(3
				(++ rapMsgNum)
				(= state 1) ;-1
				(= cycles 1)	
			)
		)
	)
)

(class Sq1Window of SysWindow
  (properties
    underBits 0
    pUnderBits 0
    bordWid 3
  )
 
  (method (dispose)
    (SetPort 0)
    (kernel_112 grRESTORE_BOX underBits)
    (kernel_112 grRESTORE_BOX pUnderBits)
    (kernel_112 grREDRAW_BOX lsTop lsLeft lsBottom lsRight)
    (super dispose:)
  )
 
  (method (open &tmp port temp1)
    (= color 0); gColor)
    (= back 15);gBack)
    (= type 128)
    (super open:)
    (= port (SetPort 0))
    (= temp1 1)
    (if (!= priority -1) (= temp1 (| temp1 $0002)))
    (= lsTop (- top bordWid))
    (= lsLeft (- left bordWid))
    (= lsRight (+ right bordWid))
    (= lsBottom (+ bottom bordWid))
    (= underBits (kernel_112 grSAVE_BOX lsTop lsLeft lsBottom lsRight 1))
    (if (!= priority -1)
      (= pUnderBits (kernel_112 grSAVE_BOX lsTop lsLeft lsBottom lsRight 2))
    )
    ; Draw the background
    (kernel_112 grFILL_BOX lsTop lsLeft lsBottom lsRight temp1 back priority)
    ; Draw the border
    (kernel_112 grDRAW_LINE (+ lsTop 1) (+ lsLeft 1) (+ lsTop 1) (- lsRight 2) 3 priority) ;global131 priority)
    (kernel_112 grDRAW_LINE (- lsBottom 2) (+ lsLeft 1) (- lsBottom 2) (- lsRight 2) 3 priority)
    (kernel_112 grDRAW_LINE (+ lsTop 1) (+ lsLeft 1) (- lsBottom 2) (+ lsLeft 1) 3 priority)
    (kernel_112 grDRAW_LINE (+ lsTop 1) (- lsRight 2) (- lsBottom 2) (- lsRight 2) 3 priority)
    (kernel_112 grUPDATE_BOX lsTop lsLeft lsBottom lsRight 1)
    ; Open a logical window for the contents to be drawn into
	(SetPort port)
  )
)