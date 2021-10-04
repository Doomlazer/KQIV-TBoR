;;; Sierra Script 1.0 - (do not remove this comment)
(script# 702)
(include game.sh) (include menu.sh)
(use Intrface)
(use Sound)
(use Save)
(use Motion)
(use Game)
(use Invent)
(use User)
(use Menu)
(use Actor)
(use System)
(use Main)


(public
	rm702 0
)

(local
	randomPick
	lsTop
	lsRight
	lsBottom
	lsLeft
	tempPort
	myTextColor
)

(instance drunkenSailor of Sound
	(properties
		number 599	
	)
)

(instance drunkScript of Script
	(properties)
	
	(method (init param1)
		(super init: param1)
	)
	
	(method (changeState newState)
		(super changeState: newState)
		(switch (= state newState)
			(0
	
			)
			(1
				(drunkenSailor play: 599 self)
				(= quit TRUE)
			)
		)
	)

)


(instance rm702 of Room
	(properties
		picture 991
	)

	
	(method (init)
		(if (!= systemWindow Sq1Window)
			;(= saveWindow systemWindow)
			(= systemWindow Sq1Window)
		
		)
		
		(= myTextColor 4)
		(Load VIEW 582)
		(super init:)
		(= randomPick (Random 0 1))
		(drunkScript changeState: 1)
		((View new:)
			view: 582 ;582
			loop: randomPick
			cel: 0
			posn: 150 160
			;setPri: 10
			init:
			startUpd:
		)
		(Print {Your privileges to the game have been suspended because
			you are a pirate!\n\n________"Yo ho ho!"} #time 12 #font 605 #at 40 30 #width 220)
	)
	
	(method (doit)
		(= inCutscene FALSE)
		
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
    (kernel_112 grDRAW_LINE (+ lsTop 1) (+ lsLeft 1) (+ lsTop 1) (- lsRight 2) 4 priority) ;global131 priority)
    (kernel_112 grDRAW_LINE (- lsBottom 2) (+ lsLeft 1) (- lsBottom 2) (- lsRight 2) 4 priority)
    (kernel_112 grDRAW_LINE (+ lsTop 1) (+ lsLeft 1) (- lsBottom 2) (+ lsLeft 1) 4 priority)
    (kernel_112 grDRAW_LINE (+ lsTop 1) (- lsRight 2) (- lsBottom 2) (- lsRight 2) 4 priority)
    (kernel_112 grUPDATE_BOX lsTop lsLeft lsBottom lsRight 1)
    ; Open a logical window for the contents to be drawn into
	(SetPort port)


;version for scummvm?
;;;		(SetPort 0)
;;;		(= color 0); gColor)
;;;		(= back 15);gBack)
;;;		(= temp1 1)
;;;		(if (!= priority -1) (= temp1 (| temp1 $0002)))
;;;		(= lsTop (- top bordWid))
;;;		(= lsLeft (- left bordWid))
;;;		(= lsRight (+ right bordWid))
;;;		(= lsBottom (+ bottom bordWid))
;;;		(= underBits
;;;			(kernel_112 GSaveBits lsTop lsLeft lsBottom lsRight 1)
;;;		)
;;;		(if (!= priority -1)
;;;			(= pUnderBits
;;;				(kernel_112 GSaveBits lsTop lsLeft lsBottom lsRight 2)
;;;			)
;;;		)
;;;		(kernel_112
;;;			GFillRect
;;;			lsTop
;;;			lsLeft
;;;			lsBottom
;;;			lsRight
;;;			temp1
;;;			back
;;;			priority
;;;		)
;;;		(kernel_112
;;;			GDrawLine
;;;			(+ lsTop 1)
;;;			(+ lsLeft 1)
;;;			(+ lsTop 1)
;;;			(- lsRight 2)
;;;			myTextColor
;;;			priority
;;;		)
;;;		(kernel_112
;;;			GDrawLine
;;;			(- lsBottom 2)
;;;			(+ lsLeft 1)
;;;			(- lsBottom 2)
;;;			(- lsRight 2)
;;;			myTextColor
;;;			priority
;;;		)
;;;		(kernel_112
;;;			GDrawLine
;;;			(+ lsTop 1)
;;;			(+ lsLeft 1)
;;;			(- lsBottom 2)
;;;			(+ lsLeft 1)
;;;			myTextColor
;;;			priority
;;;		)
;;;		(kernel_112
;;;			GDrawLine
;;;			(+ lsTop 1)
;;;			(- lsRight 2)
;;;			(- lsBottom 2)
;;;			(- lsRight 2)
;;;			myTextColor
;;;			priority
;;;		)
;;;		(kernel_112 GShowBits lsTop lsLeft lsBottom lsRight 1)
;;;		(= type 128)
;;;		(super open:)

  )
)