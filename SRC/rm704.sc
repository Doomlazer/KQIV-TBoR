;;; Sierra Script 1.0 - (do not remove this comment)
(script# 704)
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
	Room704 0
)
(synonyms
	(room cell)
)

(local

	;window
	;printObj
	lsTop
	lsRight
	lsBottom
	lsLeft
	saveWindow
	spaceObjY
	spaceObjLoop
)
(instance Room704 of Room
	(properties
		picture 704
		style (| BLACKOUT IRISOUT)
	)
	
	(method (init)
		(if (!= systemWindow Sq1Window)
			(= saveWindow systemWindow)
			(= systemWindow Sq1Window)
		)
		(Load VIEW 705)
		(Load VIEW 581)
		(self setRegions: LOLOTTE)
		(super init:)
		(spaceObj init:)
		(curRoom setScript: spaceWindow)
		((Prop new:)
			view: 581
			loop: 1
			cel: 0
			posn:  284 144
			;setPri: 3
			init:
		)
		((Prop new:)
			view: 581
			loop: 1
			cel: 1
			posn: 165 156
			;setPri: 3
			init:
		)
		((Prop new:)
			view: 581
			loop: 1
			cel: 2
			posn: 219 181
			;setPri: 3
			init:
		)
		((Prop new:)
			view: 581
			loop: 1
			cel: 3
			posn: 236 143
			;setPri: 3
			init:
		)
		((Prop new:)
			view: 581
			loop: 1
			cel: 4
			posn: 64 140
			;setPri: 3
			init:
		)
		((Prop new:)
			view: 581
			loop: 1
			cel: 5
			posn: 42 160
			;setPri: 3
			init:
		)
		((Prop new:)
			view: 581
			loop: 1
			cel: 6
			posn: 89 159
			;setPri: 3
			init:
		)
		((Prop new:)
			view: 581
			loop: 1
			cel: 7
			posn: 15 175
			;setPri: 3
			init:
		)
		((Prop new:)
			view: 581
			loop: 1
			cel: 8
			posn: 69 182
			;setPri: 3
			init:
		)
		(ego
			posn: 300 159
			view: 581
			setScript: beamIn
			illegalBits: cWHITE
			loop: 8
			cel: -1
			xStep: 4
			yStep: 2
			init:
		)
	)
	
	(method (doit)
		(super doit:)
		(if (and (& (ego onControl: 0) cBROWN)(== (ego script?) 0))
			(ego setScript: beamOut)
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
								(Said 'look/room')
							)
							(Print 704 0 #font 605 #at 10 5 #width 280)
							(Print 704 1 #font 605 #at 10 5 #width 280)
							(Print 704 2 #font 605 #at 10 5 #width 280)
							(Print 704 3 #font 605 #at 10 5 #width 280)
							(Print 704 4 #font 605 #at 10 5 #width 280)
							(Print 704 5 #font 605 #at 10 5 #width 280)
							(Print 704 6 #font 605 #at 10 5 #width 280)
							(Print 704 7 #font 605 #at 10 5 #width 280)
							(Print 704 8 #font 605 #at 10 5 #width 280)
							(Print 704 9 #font 605 #at 10 5 #width 280)
						)
						((Said 'look>')
							(cond 
								((Said '/console') 
									(Print 704 10 #font 605)
								)
								((or (Said '/out/window')(Said '/window,space')) 
									(Print 704 11 #font 605 #width 227)
								)
								((Said '/out') 
									(Print 704 12 #font 605)
								)
							)
						)
						((Said 'converse')
							(Print 704 13 #font 605)
							(Print 704 14 #font 605)
							(Print 704 15 #font 605)
							(Print 704 16 #font 605)
							(Print 704 17 #font 605)
							(Print 704 18 #font 605)
							(Print 704 19 #font 605)
							(Print 704 20 #font 605)
							(Print 704 21 #font 605)
						)
						((Said 'beam/me')
							(ego setScript: beamOut)
						)
						(else 
							(Print 704 22 #font 605 )
							(event claimed: TRUE)
						)
					)
				)
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
    (= color 0) ;gColor
    (= back 15) ;gBack
    (= type 128)
    (super open:)
    (= port (GetPort))
    (SetPort 0)
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
    (kernel_112 grDRAW_LINE (+ lsTop 1) (+ lsLeft 1) (+ lsTop 1) (- lsRight 2) 6 priority) ;line color
    (kernel_112 grDRAW_LINE (- lsBottom 2) (+ lsLeft 1) (- lsBottom 2) (- lsRight 2) 6 priority)
    (kernel_112 grDRAW_LINE (+ lsTop 1) (+ lsLeft 1) (- lsBottom 2) (+ lsLeft 1) 6 priority)
    (kernel_112 grDRAW_LINE (+ lsTop 1) (- lsRight 2) (- lsBottom 2) (- lsRight 2) 6 priority)
    (kernel_112 grUPDATE_BOX lsTop lsLeft lsBottom lsRight 1)

	(SetPort port)
  )
)

(instance beamIn of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 
				(HandsOff)
				(Print {Beaming in!} #font 605 #time 3 #dispose)
				(ego setCycle: BegLoop self)
			
			)	
			(1
				(ego 
					view: 705
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

(instance spaceObj of Actor
	(properties
		view 581
		illegalBits $0000	
	)	
)

(instance beamOut of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 
				(HandsOff)
				(Print {Beaming out!} #font 605 #time 2 #dispose)
				(ego 
					view: 581
					loop: 0
					cel: 0
					setMotion: 0
					setCycle: EndLoop)
					
				(= seconds 2)
			 
			)	
			(1
				(HandsOn)
				(if (!= systemWindow saveWindow)
					(= systemWindow saveWindow)
					(Sq1Window dispose:)
				)
				(curRoom newRoom: 86)
			)
		)
	)
)

(instance spaceWindow of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= cycles 1)
			)
			(1
				
				(= spaceObjY (Random 80 100))
				(= spaceObjLoop (Random 2 7))
	
				(if (== 0 (mod spaceObjLoop 2))
					(spaceObj
						setLoop: spaceObjLoop
						posn: 40 spaceObjY
						setPri: 3
						setCycle: Forward
						setMotion: MoveTo 300 spaceObjY self
					)
				else
					(spaceObj
						setLoop: spaceObjLoop
						posn: 280 spaceObjY
						setPri: 3
						setCycle: Forward
						setMotion: MoveTo 20 spaceObjY self
					)
				)
			)	
			(2
				(= state 0)
				(= cycles 1)
			)
		)
	)	
)
