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
		((Prop new:)
			view: 703 ;512
			loop: 6
			posn: 35 55
			setPri: 3
			init:
			setCycle: Forward
		)
		((Prop new:)
			view: 703		;512
			loop: 6
			posn: 267 54
			setPri: 3
			init:
			setCycle: Forward
		)
		(ego
			posn: 150 140
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
		(if (and (== gamePhase endGame) (& (ego onControl: 0) cBROWN))
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
							(Print 83 0)
						)
						((Said 'look>')
							(cond 
								((Said '/skeleton,bone')
									(Print 83 1)
								)
								((Said '/machine')
									(Print 83 2)
								)
								((Said '/whip')
									(Print 83 3)
								)
								((Said '/chain')
									(Print 83 4)
								)
								((Said '/window')
									(Print 83 5)
								)
								((Said '/wall')
									(Print 83 6)
								)
								((or (Said '/dirt') (Said '<down'))
									(Print 83 7)
								)
							)
						)
						((or (Said 'use,(turn<on)/machine') (Said 'turn/wheel'))
							(Print 83 8)
						)
						((Said 'get/whip')
							(Print 83 9)
						)
						((Said 'get/chain')
							(Print 83 4)
						)
						((Said 'open/window')
							(Print 83 10)
						)
						((Said 'break/window')
							(Print 83 11)
						)
						((Said 'open/door')
							(if (< gamePhase killedLolotte)
								(Print 83 12)
							else
								(Print 83 13)
							)
						)
						((Said 'unlatch/door')
							(if (< gamePhase killedLolotte)
								(Print 83 14)
							else
								(Print 83 15)
							)
						)
						((Said 'call/help,save')	;EO: fixed said spec
							(Print 83 16)
						)
					)
				)
			)
		)
	)
)

(instance takeBack of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= seconds 30)
			)
			(1
				(User canControl: FALSE canInput: FALSE)
				(ego setMotion: 0)
				(Print 83 17)
				(if (ego inRect: 123 142 193 180)
					(ego setMotion: MoveTo 150 130 self)
				else
					(self cue:)
				)
			)
			(2
				(ego loop: 2)
				((= henchman (Actor new:))
					view: 141
					posn: 150 194
					illegalBits: 0
					ignoreActors: TRUE
					init:
					setCycle: Walk
					setMotion: MoveTo 150 160 self
				)
			)
			(3
				(= printObj
					(Print 83 18
						#at -1 10
						#font smallFont
						#dispose
					)
				)
				(User canControl: FALSE canInput: FALSE)
				(ego illegalBits: 0 setMotion: MoveTo 160 (ego y?) self)
			)
			(4
				(ego illegalBits: 0 setLoop: 2 setMotion: Follow henchman 5)
				(self cue:)
			)
			(5
				(henchman setMotion: MoveTo 150 225 self)
			)
			(6
				(cls)
				(ego setLoop: -1 setAvoider: 0)
				(curRoom newRoom: 86)
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
    ;(SetPort 0)
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