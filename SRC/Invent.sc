;;; Sierra Script 1.0 - (do not remove this comment)
;;;;
;;;;	INVENT.SC
;;;;	(c) Sierra On-Line, Inc, 1988
;;;;
;;;;	Author: Bob Heitman
;;;;
;;;;	Classes to manage inventory (items in the possession of other objects
;;;;	in an adventure game).
;;;;
;;;;	Classes:
;;;;		InvItem
;;;;		Inventory
;;;;
;;;;	Objects:
;;;;		invDialog


(script# INVENT)
(include game.sh)
(use Main)
(use Intrface)
(use Save)
(use System)


(local
	yesI
)

; a stock list will be able to handle the scanning required to:
; find items by parsed name (firstTrue: #saidMe:)
; build status dialog (first, next)
; find items in room (firstTrue: #ownedBy)
; reference items by number (at: enumName)



(class InvItem kindof Object
	;;; An InvItem is something which can be owned by an object in an
	;;; adventure game.

	(properties
		-info- $8004		;(| CLASSBIT NODISPLAY)
		name "InvI"			;my literal name
		said 0				;said spec which user can type to identify this item
		description 0		;long text description
		owner 0				;who owns this item
		view 0				;picture of the item
		loop 0
		cel 0
		script 0				;a script that can control the item
	)

;;;	(methods
;;;		saidMe				;does user input match the said spec?
;;;		ownedBy				;return TRUE if owned by given object
;;;		showSelf				;display this item
;;;		moveTo				;change ownership of this item
;;;		changeState
;;;	)



	(method (saidMe)
		;; Return TRUE if my "said" was "parsed"

		(return (Said said))
	)


	(method (ownedBy id)
		;; Return TRUE if owned by this ID.

		(return (== owner id))
	)
	

	(method (moveTo id)
		;; Set my "owner" to passed ID.

		(= owner id)
		(return self)
	)


	(method (showSelf)
		;; Display this object.

		(ShowView 
			(if description
				description
			else
				name
			)
			view loop cel
		)
	)


	(method (changeState newState)
		(if script
			(script changeState:newState)
		)
	)
)




(class Inventory kindof Set
	;;; This is the set of all inventory items in the game.

	(properties
		name "Inv"
		carrying	"You are carrying:"
			;Title of the inventory display when the object in question
			;has some inventory items.
		empty "You are carrying nothing!"
			;Title of the inventory display when the object in question
			;has no inventory items.
	)

;;;	(methods
;;;		showSelf			;display inventory owned by an object
;;;		saidMe			;return InvItem matching user input
;;;		ownedBy			;return InvItem owned by an object
;;;	)


	(method (init)
		(= inventory self)
	)


	(method (saidMe)
		;; Return the ID of the first item in the inventory whose said
		;; spec matches user input.

		(return (self firstTrue: #saidMe:))
	)


	(method (ownedBy whom)
		;; Return the first item in inventory which is owned by 'whom'.

		(return (self firstTrue: #ownedBy: whom))
	)


	(method (showSelf whom)
		;; Show the possessions of 'whom'.

		(invDialog text:carrying, doit:whom)
	)
)




(instance invDialog of Dialog
	(properties
		name "invD"
	)


	(method (init whom &tmp lastX lastY widest num el node obj)

		;Init positioning vars.
		(= widest (= lastX (= lastY MARGIN)))
		(= num 0)

		(for 	((= node (inventory first:)))
				node
				((= node (inventory next: node)))

			(= obj (NodeValue node))
	
			;Does this character own this thing.
			(if (obj ownedBy: whom)	
				(++ num)
				(self add: 
					((= el (DText new:))
						value: obj, 
						text: (obj name?), 
						nsLeft: lastX, 
						nsTop: lastY,
						state:(| dActive dExit),
						font: smallFont,
						setSize:,
						yourself:
					)
				)

				;Keep track of widest item.
				(if (< widest (- (el nsRight?) (el nsLeft?)))
					(=  widest (- (el nsRight?) (el nsLeft?)))
				)

				;Bump lastY by height of character this item.
				(+= lastY (+ (- (el nsBottom?) (el nsTop?)) 1))

				;Wrap to next column.
				(if (> lastY 140)
					(= lastY MARGIN)
					(+= lastX (+ widest 10))
					(= widest 0)
				)
			)
		)
 
		;If no items owned, bag it.
		(if (not num)
			(self dispose:)
			(return 0)
		)

		; give ourself the class SysWindow as our window
		(= window SysWindow)

		;Size dialog and add button to lower right
		(self setSize:)
		(= yesI (DButton new:))
		(yesI 
			text: "OK", 
			setSize:,
			moveTo: 
				(- nsRight (+ MARGIN (yesI nsRight?)))
				nsBottom
		)
		(yesI
			move: (- (yesI nsLeft?) (yesI nsRight?)) 0
		)

		;Add button and resize the dialog.
		(self add: yesI, setSize:, center:)

		(return num)
	)


	(method (doit whom &tmp el)
		;Initialize the dialog. If we have nothing, tell user.
		(if (not (self init: whom))
			(Print (inventory empty?))
			(return)
		)
	
		;Call the dialog with exit as default
		(self open: wTitled 15)
		(= el yesI)

		(repeat
			(= el (super doit:el))

			;These returns signal end of dialog
			(if (or (not el) (== el -1) (== el yesI))
				(break)
			)

			((el value?) showSelf:)
		)
	
		;Dispose of everything
		(self dispose:)
	)


	(method (handleEvent event &tmp msg typ)
		(= msg (event message?))
		(= typ (event type?))

		(switch typ
			(keyDown
				(switch msg
					(UPARROW
						(= msg SHIFTTAB)
					)
					(DOWNARROW
						(= msg TAB)
					)
				)
			)
			(direction
				(switch msg
					(dirN
						(= msg SHIFTTAB)
						(= typ keyDown)
					)
					(dirS
						(= msg TAB)
						(= typ keyDown)
					)
				)
			)
		)

		(event
			type: typ,
			message: msg
		)

		(return (super handleEvent:event))
	)
)
