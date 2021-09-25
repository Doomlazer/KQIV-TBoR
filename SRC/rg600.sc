;;; Sierra Script 1.0 - (do not remove this comment)
(script# DWARF_MINE)
(include game.sh)
(use Main)
(use Intrface)
(use Sound)
(use Game)

(public
	rg600 0
)
(synonyms
	(dwarf ass dwarf man person)
)

(instance dwarfMusic of Sound
	(properties
		number 30
		loop -1
	)
)

(instance rg600 of Region
	(properties)
	
	(method (init)
		(if initialized (return))
		(= keep TRUE)
		(super init:)
		(= noWearCrown TRUE)
		(dwarfMusic owner: self play:)
	)
	
	(method (dispose)
		(if (== keep FALSE) (= noWearCrown FALSE))
		(super dispose:)
	)
	
	(method (handleEvent event &tmp inventorySaidMe)
		(return
			(cond 
				((event claimed?) (return TRUE))
				((== (event type?) saidEvent)
					(cond 
						((Said 'look>')
							(cond 
								((Said '/dwarf') (Print 600 0))
								((Said '/mine[<diamond]') (Print 600 1))
								((Said '/boulder[<gray]') (Print 600 2))
								((Said '[<down]/dirt[<mine]') (Print 600 3))
								((Said '/passageway[<mine]') (Print 600 4))
								((Said '/path') (Print 600 5))
								((Said '/wall') (Print 600 6))
								((Said '/bucket') (Print 600 7))
								((Said '/diamond') (Print 600 8))
							)
						)
						((Said 'get>')
							(cond 
								((Said '/boulder') (Print 600 9))
								((Said '/diamond') (Print 600 10))
								((Said '/dwarf') (Print 600 11))
							)
						)
						((Said 'rob/diamond') (Print 600 12))
						((Said 'climb/boulder') (Print 600 13))
						(
						(or (Said 'converse/dwarf') (Said 'converse[/!*]')) (Print 600 14))
						((Said 'kill/dwarf') (Print 600 15))
						((or (Said 'kiss/dwarf') (Said 'kiss[/!*]')) (Print 600 16))
						((Said 'hit/dwarf') (Print 600 17))
						((Said 'help/dwarf') (Print 600 18))
						(
							(and
								(Said 'deliver,return>')
								(= inventorySaidMe (inventory saidMe:))
							)
							(if (ego has: (inventory indexOf: inventorySaidMe))
								(Print 600 19)
							else
								(PrintDontHaveIt)
							)
						)
					)
				)
			)
		)
	)
)
