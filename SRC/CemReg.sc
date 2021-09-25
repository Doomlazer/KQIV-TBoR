;;; Sierra Script 1.0 - (do not remove this comment)
(script# CEMETERY)
(include game.sh)
(use Main)
(use Intrface)
(use Game)
(use Invent)

(public
	cemReg 0
)
(synonyms
	(fence wall)
)

(instance cemReg of Region
	(properties
		name "Cemetery Region"
	)
	
	(method (handleEvent event &tmp inventorySaidMe)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					((Said 'look>')
						(cond 
							((Said '/flora') (Print 510 0))
							((Said '/boulder') (Print 510 1))
							((Said '/dirt,down') (Print 510 2))
							((Said '/grass') (Print 510 3))
							((Said '/bush') (Print 510 4))
							((Said '/blossom') (Print 510 5))
							((Said '/fence') (Print 510 6))
							((Said '/forest') (Print 510 7))
							((Said '/cemetery,cemetery') (Print 510 8))
							((Said '/grave,crypt') (Print 510 9))
							((Said '/gravestone') (Print 510 10))
							((Said '/monument') (Print 510 11))
							((Said '/ghoul')
								(if (and isNightTime numZombies)
									(Print 510 12)
								else
									(Print 510 13)
								)
							)
						)
					)
					((Said 'get/blossom') (Print 510 14))
					((Said 'dig[/grave,hole]')
						(if (not ((Inventory at: iShovel) owner: ego)) (Print 510 15))
						(if (== 5 timesUsedShovel) (Print 510 16))
					)
					((Said 'conceal<behind/grave,gravestone') (Print 510 17))
					((Said 'get/gravestone') (Print 510 18))
					((Said 'move/gravestone') (Print 510 19))
					((Said 'converse[/ghoul]')
						(if (and isNightTime numZombies)
							(Print 510 20)
						else
							(Print 510 21)
						)
					)
					((Said 'kill[/ghoul]')
						(if (and isNightTime numZombies)
							(Print 510 22)
						else
							(Print 510 13)
						)
					)
					((Said 'climb/boulder') (Print 510 23))
					((Said 'get,capture/ghoul')
						(if (and isNightTime numZombies)
							(Print 510 24)
						else
							(Print 510 13)
						)
					)
					((Said 'kiss[/ghoul]')
						(if (and isNightTime numZombies)
							(Print 510 25)
						else
							(event claimed: FALSE)
						)
					)
					(
						(and
							(Said 'deliver>')
							(= inventorySaidMe (inventory saidMe:))
						)
						(if (ego has: (inventory indexOf: inventorySaidMe))
							(if (== numZombies 0)
								(Print 510 21)
							else
								(Print 510 26)
							)
						else
							(PrintDontHaveIt)
						)
					)
				)
			else
				FALSE
			)
		)
	)
)
