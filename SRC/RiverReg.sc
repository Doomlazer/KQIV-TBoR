;;; Sierra Script 1.0 - (do not remove this comment)
(script# RIVER)
(include game.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use System)

(public
	riverReg 0
)
(synonyms
	(brook brook brook)
)

(local
	local0
	oldEgoViewer
)
(instance riverReg of Region
	(properties
		name "River Region"
	)
	
	(method (init)
		(super init:)
		(self setScript: riverActions)
		(Load VIEW 21)
	)
	
	(method (dispose)
		((ScriptID 0 4) dispose: delete:)
		(super dispose:)
	)
	
	(method (handleEvent event)
		(super handleEvent: event)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((Said 'look<in/brook')
						(if
							(or
								(& (= local0 (IsObjectOnControl ego 20)) $0008)
								(& local0 $0800)
								(& local0 $0002)
								(& local0 $0200)
							)
							(Print 512 0)
						else
							(PrintNotCloseEnough)
						)
					)
					((or (Said 'look/brook') (Said 'look/water')) (Print 512 1))
					(
						(or
							(Said 'bathe/brook')
							(Said 'bathe')
							(Said 'enter/bathe')
						)
						(Print 512 2)
					)
					(
						(or
							(Said 'fish[/!*]')
							(Said 'fish<enter')
							(Said 'look,capture/fish')
							(Said 'cast/pole')
						)
						(Print 512 3)
					)
					((Said 'wade/brook')
						(if (== currentStatus egoNormal)
							(Print 512 4)
						else
							(Print 512 5)
						)
					)
					((or (Said 'drink') (Said 'get/drink'))
						(cond 
							((!= currentStatus egoNormal) (Print 512 6))
							(
								(or
									(& (= local0 (IsObjectOnControl ego 10)) $0008)
									(& local0 $0800)
									(& local0 $0002)
									(& local0 $0200)
								)
								(= timedMessage (Print 512 7 #at -1 10 #dispose))
								(riverActions changeState: 1)
							)
							(else (PrintNotCloseEnough))
						)
					)
					((Said 'get/water') (Print 512 8))
				)
			else
				FALSE
			)
		)
	)
)

(instance riverActions of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(1
				(HandsOff)
				(= oldEgoViewer (ego viewer?))
				(ego viewer: 0 view: 21 cel: 0 setCycle: EndLoop self)
			)
			(2
				((ScriptID 0 4) setReal: self 6)
			)
			(3 (ego setCycle: BegLoop self))
			(4
				(ego view: 2 setCycle: Walk viewer: oldEgoViewer)
				(cls)
				(HandsOn)
			)
		)
	)
)
