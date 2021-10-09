;;; Sierra Script 1.0 - (do not remove this comment)
(script# 5)
(include game.sh)
(use Main)
(use Intrface)
(use Sound)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	Room5 0
)

(local
	[treeArm 9]
	local9
	treeEye1
	local11
	treeEye2
)
(instance gMusic of Sound
	(properties
		number 65
	)
)

(instance walkingMusic of Sound
	(properties
		number 64
	)
)

(instance Room5 of Room
	(properties
		picture 5
	)
	
	(method (init)
		(= north 29)
		(= south 11)
		(= east 6)
		(= west 4)
		(= horizon 86)
		(= isIndoors FALSE)
		(Load SOUND 65)
		(if isNightTime (= picture 105))
		(ego edgeHit: 0)
		(self setRegions: SCARY_FOREST)
		(super init:)
		(= local9 1)
		(while (<= local9 7)
			(= [treeArm local9] (Prop new:))
			(++ local9)
		)
		([treeArm 1]
			view: 690
			loop: 0
			cel: 0
			cycleSpeed: 1
			ignoreActors:
			posn: 270 81
			setPri: 15
			init:
			stopUpd:
		)
		([treeArm 2]
			view: 690
			loop: 1
			cel: 0
			cycleSpeed: 1
			ignoreActors:
			posn: 270 81
			setPri: 15
			init:
			stopUpd:
		)
		([treeArm 3]
			view: 690
			loop: 2
			cel: 0
			cycleSpeed: 1
			ignoreActors:
			posn: 291 78
			setPri: 8
			init:
			stopUpd:
		)
		([treeArm 4]
			view: 690
			loop: 3
			cel: 0
			cycleSpeed: 1
			ignoreActors:
			posn: 311 150
			setPri: 7
			init:
			stopUpd:
		)
		([treeArm 5]
			view: 690
			loop: 3
			cel: 0
			cycleSpeed: 1
			ignoreActors:
			posn: 269 89
			setPri: 2
			init:
			stopUpd:
		)
		([treeArm 6]
			view: 691
			loop: 0
			cel: 0
			cycleSpeed: 1
			ignoreActors:
			posn: 115 76
			setPri: 8
			init:
			stopUpd:
		)
		([treeArm 7]
			view: 691
			loop: 1
			cel: 0
			cycleSpeed: 1
			ignoreActors:
			posn: 235 162
			setPri: 15
			init:
			stopUpd:
		)
		(= treeEye1 (Prop new:))
		(= treeEye2 (Prop new:))
		(treeEye1
			isExtra: TRUE
			view: 689
			loop: 0
			cel: 3
			ignoreActors:
			posn: 208 57
			setPri: 15
			setCycle: Forward
			cycleSpeed: 4
			init:
		)
		(treeEye2
			isExtra: TRUE
			view: 689
			loop: 1
			cel: 0
			ignoreActors:
			posn: 233 44
			setPri: 15
			setCycle: Forward
			cycleSpeed: 3
			init:
		)
		(switch prevRoomNum
			(4
				(if (<= (ego y?) 135)
					(ego posn: 1 120)
				else
					(ego posn: 1 (ego y?))
				)
			)
			(6
				(if (< (ego y?) 133)
					(ego posn: 318 133)
				else
					(ego posn: 318 (ego y?))
				)
			)
			(11
				(if (> (ego x?) 207)
					(ego posn: 207 187)
				else
					(ego posn: (ego x?) 187)
				)
			)
			(29
				(if (> (ego x?) 272) (ego x: 258))
				(ego posn: (ego x?) (+ horizon (ego yStep?)))
			)
			(0 (ego x: 290 y: 161))
		)
		(walkingMusic play:)
		(ego view: 2 init:)
	)
	
	(method (doit)
		(super doit:)
		(if
		(and (not choppedScaryTree) (== (curRoom script?) 0))
			(cond 
				((& (ego onControl: 0) $0040) (= local11 3) (= local9 1) (curRoom setScript: grabbed))
				((& (ego onControl: 0) $0020) (= local11 3) (= local9 2) (curRoom setScript: grabbed))
				((& (ego onControl: 0) $0010) (= local11 3) (= local9 3) (curRoom setScript: grabbed))
				((& (ego onControl: 0) $0004) (= local11 1) (= local9 7) (curRoom setScript: grabbed))
				((& (ego onControl: 0) $0400) (= local11 1) (= local9 4) (curRoom setScript: grabbed))
				((& (ego onControl: 0) $0200) (= local11 1) (= local9 5) (curRoom setScript: grabbed))
				((& (ego onControl: 0) $0002) (= local11 2) (= local9 6) (curRoom setScript: grabbed))
			)
		)
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return TRUE))
		(return
			(if (== (event type?) saidEvent)
				(cond 
					(
						(or
							(Said 'look/room')
							(Said 'look/around')
							(Said 'look[<around][/!*]')
						)
						(Print 5 0)
					)
					((Said 'look/cottage') (Print 5 1))
					((Said 'look/door') (Print 5 2))
					((Said 'look/window')
						(if (ego inRect: 0 130 32 138)
							(Print 5 3)
						else
							(Print 5 4)
						)
					)
					((Said 'break/window') (Print 5 5))
					((Said 'open/window') (Print 5 6))
				)
			else
				0
			)
		)
	)
)

(instance grabbed of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(walkingMusic dispose:)
				(gMusic play:)
				(HandsOff)
				([treeArm local9] startUpd: setCycle: CycleTo local11 1 self)
			)
			(1
				(ego dispose:)
				([treeArm local9] setCycle: EndLoop self)
			)
			(2
				(switch (Random 1 6)
					(1 (Print 5 7))
					(2 (Print 5 8))
					(3 (Print 5 9))
					(4 (Print 5 10))
					(5 (Print 5 11))
					(6 (Print 5 12))
				)
				(= seconds 5)
			)
			(3 (= dead TRUE))
		)
	)
)
