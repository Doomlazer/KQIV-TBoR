;;; Sierra Script 1.0 - (do not remove this comment)
(script# WATER)
(include game.sh)
(use Main)
(use Motion)
(use Game)
(use System)

(public
	waterReg 0
)

(local
	waterDepth
	local1
)
(instance waterReg of Region
	(properties
		name "Water Region"
	)
	
	(method (init)
		(super init:)
		(Load VIEW 5)
		(Load VIEW 6)
		(Load VIEW 7)
		(Load VIEW 8)
		(ego viewer: water)
	)
)

(instance water of Script
	(properties)
	
	(method (doit)
		(if (!= (= waterDepth (ego onControl: 1)) local1)
			(= local1 waterDepth)
			(if (!= currentStatus egoRidingDolphin)
				(ego setCycle: Walk)
				(switch waterDepth
					(1
						(= currentStatus egoNormal)
						(ego view: 2 setStep: 3 2)
					)
					(2048
						(= currentStatus egoInShallowWater)
						(ego view: 5)
					)
					(512
						(= currentStatus egoInKneeDeepWater)
						(ego view: 6)
					)
					(8
						(ego view: 7)
						(= currentStatus egoInWaistDeepWater)
					)
					(2
						(ego view: 8)
						(= currentStatus egoSwimming)
						(ego setCycle: Forward)
					)
				)
			)
		)
	)
)
