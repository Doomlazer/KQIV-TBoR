;;; Sierra Script 1.0 - (do not remove this comment)
;;;;
;;;;	USER.SC
;;;;	(c) Sierra On-Line, Inc, 1988
;;;;
;;;;	Author: Jeff Stephenson
;;;;
;;;;	A User is an object which corresponds to the person playing the
;;;;	game and acts as the intermediary between the person and the
;;;;	other objects in the game.  In the current games there is only
;;;;	one User, and thus we use the class User rather than an instance
;;;;	of the class.
;;;;
;;;;	Classes:
;;;;		User


(script#	USER)
(include game.sh)
(use Main)
(use Intrface)
(use Sound)
(use SortCopy)
(use Motion)
(use Menu)
(use System)


(define	INPUTLEN		45)
(define INBUFSIZE		23) ;(define	INBUFSIZE	(+ (/ INPUTLEN 2) 1))

(local
	[inputLine INBUFSIZE]
	inputLen
)

(class User kindof Object
	(properties
		alterEgo 0					;the object ID of the Ego which User controls
		canInput 0					;can the User type input?
		controls 0					;boolean -- does User control alterEgo at present?
		echo SPACEBAR				;character to echo last input line
		prevDir 0					;previous direction in which alterEgo was moving
		prompt "Enter input"		;prompt for input window
		inputLineAddr 0			;address of User's input line
		x	-1							; upper/left
		y	-1							; of user window
		blocks	TRUE				; stops sounds by default
		mapKeyToDir TRUE			;map keys to dirs?
	)
	
;;;	(methods
;;;		canControl					;specifies whether user controls alterEgo
;;;		getInput						;collects input from the user
;;;		said							;passes parsed user input to handleEvent methods
;;;		handleEvent
;;;	)
	
	(method (init inLine length)
		(= inputLineAddr (if argc inLine else @inputLine))
		(= inputLen (if (== argc 2) length else INPUTLEN))
	)
	
	
	(method (doit &tmp event evType dir)
		;; See if there is an event.  If none, just return.  Otherwise
		;; pass the event to other objects in the game to see if they
		;; want it.

		(= event (Event new:))
		(if (event type?)
			(= lastEvent event)

			;Convert key events to direction events, if appropriate, but
			;remember what kind of event it was.
			(= evType (event type?))
			(if mapKeyToDir
				(MapKeyToDir event)
			)
			
			;Give the event to the menu first.
			(if TheMenuBar
				(TheMenuBar handleEvent: event)
			)
			
			;Correct y coord for current grafPort.
			(GlobalToLocal event)
			
			;Pass the Event to the game.
			(theGame handleEvent: event evType)

			(if (not (event claimed?))
				(switch (event type?)
					(mouseDown
						(if (and controls (IsObject alterEgo) (cast contains: alterEgo))
							(alterEgo setMotion: MoveTo (event x?) (event y?))
							(= prevDir 0)
							(event claimed: TRUE)
						)
					)
					(direction
						(if (and controls (IsObject alterEgo) (cast contains: alterEgo))
							(= dir (event message?))
							
							;Pressing the cursor key which started a motion a second
							;time should stop ego.
							(if
								(and
									(== evType keyDown)				;it's a key
									(== dir prevDir)					;same dir as before
									(IsObject (alterEgo mover?))	;ego is moving
								)
									(= dir 0)
							)
	
							;In the case of a keyDown event, keep the previous
							;direction, so we know what key stops ego.
							(= prevDir (if (== evType keyDown) dir else 0))
	
							;Set ego's direction.
							(alterEgo setDirection:dir)
							(event claimed:TRUE)
						)
					)
					(else 
						(cast handleEvent: event)
					)
				)
			)
			(if
				(and
					(not (event claimed?))
					(== (event type?) keyDown)
					(or
						(== (event message?) echo)
						(and
							(<= SPACEBAR (event message?))
							(<= (event message?) 127)
						)
					)
					canInput
					(self getInput: event)
					(Parse @inputLine event)
				)
				(event type: saidEvent)
				(self said: event)
			)
		)
		(event dispose:)
		(= lastEvent 0)
	)
	
	
	(method (getInput event &tmp oldPause ret)
		;; Put up the input window and collect a line of input from the user.
		
		; if this is NOT a key event we zero out the inputLine
		(if (!= (event type?) keyDown)
			(= inputLine 0)
		)
		
		;If this is not the echo character, replace the previous input
		;line with the character which was passed.
		(if (!= (event message?) echo)
			(Format @inputLine USER 0 (event message?))
		)
		
		;Let the user edit the input line.
		(= oldPause (Sound pause: blocks))
		(= ret (GetInput @inputLine inputLen prompt #at: x y))
		(Sound pause: oldPause)
		(return ret)
	)
	
	
	(method (canControl value)
		;; Doing a (User canControl:FALSE) prevents the user from controlling
		;; the alterEgo using the mouse, arrow keys, etc.  (User canControl:TRUE)
		;; reinstates user control.
		
		(if argc
			(= controls value)
			(= prevDir 0)
		)
		(return controls)
	)
	
	
	(method (said event)
		;; Pass a said event parsed from user input to the various elements of
		;; the game.
		
		(if TheMenuBar 
			(sortedFeatures addToFront: TheMenuBar)
		)
		
		(if useSortedFeatures
			(SortedAdd alterEgo sortedFeatures cast features)
		else
			(sortedFeatures add: cast features)
		)
																	;then cast and features
		(sortedFeatures 
			addToEnd:	theGame,		;then room, regions, locales and game last
			handleEvent: event
			release:
		)
		
		;If the event was not claimed by anyone, invoke pragmaFail: to let
		;the user know that it was not understood.
		(if (and (== (event type?) saidEvent) (not (event claimed?)))
			(theGame pragmaFail: @inputLine)
		)
	)
)




;;;;;; Sierra Script 1.0 - (do not remove this comment)
;;;(script# 996)
;;;(include game.sh)
;;;(use Main)
;;;(use Intrface)
;;;(use Sound)
;;;(use Motion)
;;;(use Menu)
;;;(use System)
;;;
;;;
;;;(local
;;;	[inputLine 23]
;;;	inputLen
;;;)
;;;(class User of Object
;;;	(properties
;;;		alterEgo 0
;;;		canInput 0
;;;		controls 0
;;;		echo 32
;;;		prevDir 0
;;;		prompt {Enter input}
;;;		inputLineAddr 0
;;;		x -1
;;;		y -1
;;;		blocks 1
;;;		mapKeyToDir 1
;;;	)
;;;	
;;;	(method (init param1 param2)
;;;		(= inputLineAddr (if argc param1 else @inputLine))
;;;		(= inputLen (if (== argc 2) param2 else 45))
;;;	)
;;;	
;;;	(method (doit &tmp newEvent newEventType newEventMessage)
;;;		(if ((= newEvent (Event new:)) type?)
;;;			(= lastEvent newEvent)
;;;			(= newEventType (newEvent type?))
;;;			(if mapKeyToDir (MapKeyToDir newEvent))
;;;			(if TheMenuBar (TheMenuBar handleEvent: newEvent))
;;;			(GlobalToLocal newEvent)
;;;			(theGame handleEvent: newEvent)
;;;			(if (not (newEvent claimed?))
;;;				(switch (newEvent type?)
;;;					(1
;;;						(if
;;;							(and
;;;								controls
;;;								(IsObject alterEgo)
;;;								(cast contains: alterEgo)
;;;							)
;;;							(alterEgo setMotion: MoveTo (newEvent x?) (newEvent y?))
;;;							(= prevDir 0)
;;;							(newEvent claimed: 1)
;;;						)
;;;					)
;;;					(64
;;;						(if
;;;							(and
;;;								controls
;;;								(IsObject alterEgo)
;;;								(cast contains: alterEgo)
;;;							)
;;;							(= newEventMessage (newEvent message?))
;;;							(if
;;;								(and
;;;									(== newEventType 4)
;;;									(== newEventMessage prevDir)
;;;									(IsObject (alterEgo mover?))
;;;								)
;;;								(= newEventMessage 0)
;;;							)
;;;							(= prevDir
;;;								(if (== newEventType 4) newEventMessage else 0)
;;;							)
;;;							(alterEgo setDirection: newEventMessage)
;;;							(newEvent claimed: 1)
;;;						)
;;;					)
;;;					(else 
;;;						(cast handleEvent: newEvent)
;;;					)
;;;				)
;;;			)
;;;			(if
;;;				(and
;;;					(not (newEvent claimed?))
;;;					(== (newEvent type?) 4)
;;;					(or
;;;						(== (newEvent message?) echo)
;;;						(and
;;;							(<= 32 (newEvent message?))
;;;							(<= (newEvent message?) 127)
;;;						)
;;;					)
;;;					canInput
;;;					(self getInput: newEvent)
;;;					(Parse @inputLine newEvent)
;;;				)
;;;				(newEvent type: 128)
;;;				(self said: newEvent)
;;;			)
;;;		)
;;;		(newEvent dispose:)
;;;		(= lastEvent 0)
;;;	)
;;;	
;;;	(method (canControl theControls)
;;;		(if argc (= controls theControls) (= prevDir 0))
;;;		(return controls)
;;;	)
;;;	
;;;	(method (getInput param1 &tmp temp0 temp1)
;;;		(if (!= (param1 type?) 4) (= inputLine 0))
;;;		(if (!= (param1 message?) echo)
;;;			(Format @inputLine 996 0 (param1 message?))
;;;		)
;;;		(= temp0 (Sound pause: blocks))
;;;		(= temp1 (GetInput @inputLine inputLen prompt 67 x y))
;;;		(Sound pause: temp0)
;;;		(return temp1)
;;;	)
;;;	
;;;	(method (said param1)
;;;		(if TheMenuBar (sortedFeatures addToFront: TheMenuBar))
;;;		(if useSortedFeatures
;;;			(__proc984_0 alterEgo sortedFeatures cast features)
;;;		else
;;;			(sortedFeatures add: cast features)
;;;		)
;;;		(sortedFeatures
;;;			addToEnd: theGame
;;;			handleEvent: param1
;;;			release:
;;;		)
;;;		(if
;;;		(and (== (param1 type?) 128) (not (param1 claimed?)))
;;;			(theGame pragmaFail: @inputLine)
;;;		)
;;;	)
;;;)
