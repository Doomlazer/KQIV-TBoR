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
	spaceObjLoop
	spaceObjY
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
							(Print {Holy Cow!___You have discovered the whole King's Quest IV programming staff, and then some.} #font 605 #at 10 5 #width 280)
							(Print {Vu Nguyen is the dark haired guy in the upper right hand corner.  He worked on much of the artwork for King's Quest IV.} #font 605 #at 10 5 #width 280)
							(Print {Chris Hoyt is the brown haired guy with the moustache next to Vu.  He programmed half of this version King's Quest IV.  This version is known as AGI and is low resolution, as compared to the high resolution SCI.} #font 605 #at 10 5 #width 280)
							(Print {John Hamilton is the brown haired guy at the right front of the room.  He programmed the other half of the AGI version of King's Quest IV.} #font 605 #at 10 5 #width 280)
							(Print {Teresa Baker is the blond with the pony tail.  She programmed the beginning and ending cartoons plus a few other miscellanous rooms.} #font 605 #at 10 5 #width 280)
							(Print {Sol Ackerman is the balding man standing in front of the console.  He organized how the game was to be put on floppy disks for shipping.} #font 605 #at 10 5 #width 280)
							(Print {Doug Oldfield is the guy with brown hair and a moustache stading below Sol.  He helped find some the trickier bugs in King's Quest IV.} #font 605 #at 10 5 #width 280)
							(Print {Ken Koch is the man with the graying hair in the lower left corner.  He programmed half of the SCI version of King's Quest IV.  His name is often abbeviated to Kinky.} #font 605 #at 10 5 #width 280)
							(Print {Jim Heintz is the guy with the beard just above Ken. He just wanted to be  in this room.} #font 605 #at 10 5 #width 280)
							(Print {Chane Fullmer is the blond man with glasses in the upper left corner.  He programmed half of the SCI version of King's Quest IV.} #font 605 #at 10 5 #width 280)
						)
						((Said 'look>')
							(cond 
								((Said '/console') 
									(Print {The console has nothing on it.  Somebody must have forgot to finish the picture.} #font 605)
								)
								((or (Said '/out/window')(Said '/window,space')) 
									(Print {You see ships and hamburgers and computers...WAIT!  Tou must be seeing things.  How about this, You just see space ships flying by.} #font 605 #width 227)
								)
								((Said '/out') 
									(Print {You see nothing special.} #font 605)
								)
							)
						)
						((Said 'converse')
							(Print {Vu says, "My girlfriend's name is not Dorothy!"} #font 605)
							(Print {Chris says, "My computer keeps locking up!"} #font 605)
							(Print {John says, "It's not a bug.  It's an added feature."} #font 605)
							(Print {Teresa says, "So did they just install a bowling alley upstairs or what?!"} #font 605)
							(Print {Sol says, "See ya later.  I'm going swimming in my lake."} #font 605)
							(Print {Doug says, "The Ogre walked up the walls.  Honest!"} #font 605)
							(Print {Ken says, "Quit calling me Kinky!"} #font 605)
							(Print {Jim says, "Hey guys, be sure and put me in this picture."} #font 605)
							(Print {Chane says, "Has anybody seen my B.B. King tape?"} #font 605)
						)
						((Said 'beam/me')
							(ego setScript: beamOut)
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
				
				(= spaceObjY (Random 85 100))
				(= spaceObjLoop (Random 2 7))
				;(Print (Format @str {spaceObjY__%d)} spaceObjY))
				(if (== 0 (mod spaceObjLoop 2))
					(spaceObj
						setLoop: spaceObjLoop
						posn: 40 spaceObjY
						setPri: 3
						setCycle: Forward
						setMotion: MoveTo 280 spaceObjY self
					)
				else
					(spaceObj
						loop: spaceObjLoop
						posn: 280 spaceObjY
						setPri: 3
						setCycle: Forward
						setMotion: MoveTo 40 spaceObjY self
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
