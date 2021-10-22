;;; Sierra Script 1.0 - (do not remove this comment)
(script# 221)
(include game.sh)
(use Main)
(use Intrface)
(use Sound)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	Room221 0
)

(local
	soundIsOn
	rosellaFace
	rosella
	mirror
	genesta
	printObj
)
(instance openMusic of Sound
	(properties
		number 105
	)
)

(instance Room221 of Room
	(properties
		picture 201
		style DISSOLVE
	)
	
	(method (init)
		(Load VIEW 757)
		(Load VIEW 758)
		(Load VIEW 762)
		(Load VIEW 766)
		(Load VIEW 768)
		(Load VIEW 770)
		(Load VIEW 771)
		(Load PICTURE 201)
		(Load PICTURE 205)
		(Load PICTURE 207)
		(self setRegions: INTRO)
		(super init:)
		(= userFont smallFont)
		(curRoom setScript: PartTwo)
	)
	
	(method (newRoom n)
		(cls)
		((ScriptID INTRO) keep: 0)
		(super newRoom: n)
	)
)

(instance PartTwo of Script
	(method (changeState newState)
		(addToPics dispose:)
		(switch (= state newState)
			(0
				(= mirror
					((Actor new:)
						view: 768
						loop: 0
						posn: 158 64
						cycleSpeed: 4
						;stopUpd:
						init:
						yourself:
					)
				)
				((View new:)
					view: 769
					loop: 3
					posn: 48 139
					setPri: 0
					ignoreActors:
					addToPic:
				)
				((View new:)
					view: 767
					setCel: 3
					setLoop: 0
					posn: 300 144
					addToPic:
				)
				(= rosella
					((Actor new:)
						view: 764
						loop: 1
						posn: 62 117
						cycleSpeed: 4
						setCycle: Forward
						setPri: 8
						init:
						yourself:
					)
				)
				(= seconds 2)
			)
			(1
				(= printObj
					(Print 221 0
						#title {Rosella}
						#at 1 130
						#width 150
						#dispose
						#draw
					)
				)
				(= seconds 12)
			)
			(2
				(cls)
				(mirror setLoop: 1 setCycle: Forward)
				(= printObj
					(Print 221 1
						#at 180 40
						#width 110
						#dispose
					)
				)
				(= seconds 6)
			)
			(3
				(cls)
				(mirror setLoop: 0 setCycle: 0)
				(rosella setLoop: 2 cycleSpeed: 20 setCycle: Forward)
				(= printObj
					(Print 221 2
						#at 1 134
						#title {Rosella}
						#width 150
						#dispose
					)
				)
				(= seconds 10)
			)
			(4
				(cls)
				(mirror setLoop: 1 setCycle: Forward)
				(= printObj
					(Print 221 3
						#at 180 40
						#width 110
						#dispose
					)
				)
				(= seconds 12)
			)
			(5
				(cls)
				(cast eachElementDo: #dispose)
				(curRoom drawPic: 207)
				((View new:)
					view: 766
					loop: 0
					cel: 0
					posn: 230 120
					setPri: 0
					ignoreActors:
					addToPic:
				)
				((View new:)
					view: 763
					cel: 0
					loop: 0
					posn: 137 138
					ignoreActors:
					setPri: 0
					addToPic:
				)
				(= rosella
					((Actor new:)
						view: 763
						loop: 1
						cel: 0
						posn: 155 109
						setCycle: Forward
						setPri: 15
						cycleSpeed: 3
						init:
						yourself:
					)
				)
				(= genesta
					((Actor new:)
						view: 766
						loop: 1
						cel: 0
						posn: 230 106
						setPri: 14
						cycleSpeed: 0
						setCycle: Forward
						init:
						yourself:
					)
				)
				(= printObj
					(Print 221 4
						#at 1 151
						#title {Rosella}
						66 15
						#width 200
						#dispose
					)
				)
				(= seconds 6)
			)
			(6
				(if (== ((ScriptID INTRO 1) state?) 3)
					(-- state)
					(= seconds 2)
				else
					((ScriptID INTRO 1) dispose:)
					(openMusic
						;number: 105
						play:
					)
					(self cue:)
				)
			)
			(7
				(cls)
				(genesta dispose:)
				(rosella dispose:)
				(curRoom drawPic: 205)
				((View new:)
					view: 762
					loop: 0
					cel: 0
					posn: 149 110
					ignoreActors:
					setPri: 0
					addToPic:
				)
				(= genesta
					((Actor new:)
						view: 762
						cycleSpeed: 0
						loop: 1
						cel: 0
						posn: 148 96
						setPri: 6
						setCycle: Forward
						init:
						yourself:
					)
				)
				(sparkle init:)
				(sparkle setScript: doSparkle)
				(= printObj
					(Print 221 5
						#title {Genesta}
						#at -1 125
						#width 300
						#dispose
					)
				)
				(= seconds 10)
			)
			(8
				(cls)
				(= printObj
					(Print 221 6
						#title {Genesta}
						#at -1 125
						#width 300
						#dispose
					)
				)
				(= seconds 12)
			)
			(9
				(cls)
				(genesta dispose:)
				(sparkle setScript: 0 dispose:)
				(curRoom drawPic: 207)
				((View new:)
					view: 766
					loop: 0
					cel: 0
					posn: 230 120
					setPri: 0
					ignoreActors:
					addToPic:
				)
				((View new:)
					view: 763
					cel: 0
					loop: 0
					posn: 137 138
					ignoreActors:
					setPri: 0
					addToPic:
				)
				((View new:)
					view: 766
					cel: 0
					loop: 2
					posn: 230 109
					ignoreActors:
					setPri: 1
					addToPic:
				)
				(= rosellaFace
					((Actor new:)
						view: 763
						loop: 1
						cel: 0
						posn: 155 109
						setCycle: Forward
						cycleSpeed: 3
						setPri: 15
						init:
						yourself:
					)
				)
				(= printObj
					(Print 221 7
						#title {Rosella}
						#at 1 151
						#width 200
						#dispose
					)
				)
				(= seconds 9)
			)
			(10
				(cls)
				(rosellaFace view: 766 loop: 3 cel: 0 posn: 230 71 setPri: 2)
				(= printObj
					(Print 221 8
						#title {Genesta}
						#at 100 151
						#width 200
						#dispose
					)
				)
				(= seconds 12)
			)
			(11
				(cls)
				(rosellaFace
					view: 763
					setLoop: 1
					setCel: 0
					posn: 155 109
					setCycle: Forward
					cycleSpeed: 3
				)
				(= printObj
					(Print 221 9
						#title {Rosella}
						#at 1 151
						#width 200
						#dispose
					)
				)
				(= seconds 10)
			)
			(12
				(cls)
				(curRoom drawPic: 205)
				((View new:)
					view: 762
					loop: 0
					cel: 0
					posn: 149 110
					ignoreActors:
					setPri: 0
					addToPic:
				)
				((View new:)
					view: 762
					loop: 2
					cel: 0
					posn: 149 98
					ignoreActors:
					setPri: 1
					addToPic:
				)
				(rosellaFace
					view: 762
					setLoop: 3
					setCel: 0
					posn: 149 59
					setCycle: Forward
					cycleSpeed: 2
					setPri: 15
				)
				(sparkle
						init:
						yourself: 
				)
				(sparkle setScript: doSparkle)
				(= printObj
					(Print 221 10
						#title {Genesta}
						#at 1 135
						#width 300
						#dispose
					)
				)
				(= seconds 9)
			)
			(13
				(cls)
				(= printObj
					(Print 221 11
						#title {Genesta}
						#at 1 135
						#width 300
						#dispose
					)
				)
				(= seconds 8)
			)
			(14
				(cls)
				(sparkle setScript: 0 dispose:)
				(curRoom drawPic: 207)
				((View new:)
					view: 766
					loop: 0
					cel: 0
					posn: 230 120
					setPri: 0
					ignoreActors:
					addToPic:
				)
				((View new:)
					view: 763
					cel: 0
					loop: 0
					posn: 137 139
					ignoreActors:
					setPri: 0
					addToPic:
				)
				((View new:)
					view: 766
					cel: 0
					loop: 2
					posn: 230 109
					ignoreActors:
					setPri: 1
					addToPic:
				)
				(rosellaFace
					view: 763
					loop: 1
					cel: 0
					posn: 155 109
					setCycle: Forward
					cycleSpeed: 3
					setPri: 15
				)
				(shimmer
					setCycle: Forward
					init:
				)
				(= printObj
					(Print
						221 12
						#title {Rosella}
						#at 1 151
						#width 200
						#dispose
					)
				)
				(= seconds 10)
			)
			(15
				(cls)
				(rosellaFace view: 766 loop: 3 cel: 0 posn: 230 71 setPri: 2)
				(= printObj
					(Print 221 13
						#title {Genesta}
						#at 100 151
						#width 200
						#dispose
					)
				)
				(= seconds 8)
			)
			(16
				(cls)
				(rosellaFace dispose:)
				(shimmer dispose:)
				(curRoom drawPic: 201)
				(= mirror
					((Actor new:)
						view: 768
						loop: 1
						setCycle: Forward
						posn: 158 64
						cycleSpeed: 4
						init:
						yourself:
					)
				)
				((View new:)
					view: 769
					loop: 3
					posn: 48 139
					setPri: 0
					ignoreActors:
					addToPic:
				)
				((View new:)
					view: 767
					setCel: 3
					setLoop: 0
					posn: 300 144
					addToPic:
				)
				(= rosella
					((Actor new:)
						view: 764
						loop: 0
						posn: 172 94
						cycleSpeed: 4
						;stopUpd:
						init:
						yourself:
					)
				)
				(= seconds 3)
			)
			(17
				(= printObj
					(Print 221 14
						#title {Genesta}
						#at -1 130
						#width 300
						#dispose
					)
				)
				(= seconds 9)
			)
			(18
				(cls)
				(mirror
					setLoop: 2
					setCel: 255
					cycleSpeed: 2
					setCycle: EndLoop stopMirror
				)
				(= printObj
					(Print 221 15
						#title {Rosella}
						#at 215 40
						#dispose
					)
				)
				(= seconds 3)
			)
			(19
				(if
					(or
						(== (openMusic prevSignal?) -1)
						(>= (openMusic prevSignal?) 46)
						(>= (openMusic signal?) 46)
					)
					(self cue:)
				else
					(-- state)
					(= seconds 1)
				)
			)
			(20
				(cls)
				(poof
					posn: (rosella x?) (rosella y?)
					setCycle: EndLoop self
					init:
				)
			)
			(21
				(rosella dispose:)
				(poof setLoop: 1 setCycle: EndLoop self)
			)
			(22 (curRoom newRoom: 222))
		)
	)
	
	(method (handleEvent event)
		(cond 
			(
				(and
					(== (event type?) keyDown)
					(== (event message?) `#2)
				)
				(= soundIsOn (DoSound SoundOn))
				(DoSound SoundOn (not soundIsOn))
			)
			(
				(and
					(== (event type?) keyDown)
					(== (event message?) ENTER)
				)
				(theGame restart:)
			)
		)
	)
)

(instance stopMirror of Script
	(method (cue)
		;(mirror stopUpd:)
	)
)

(instance doSparkle of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= seconds (Random 1 4))
				(sparkle hide:)
			)
			(1
				(sparkle cel: 255 setCycle: EndLoop self show:)
			)
			(2
				(self changeState: 0)
			)
		)
	)
)

(instance poof of Actor
	(properties
		view 770
		loop 0
		priority 15
		signal (| ignrAct fixPriOn)
		cycleSpeed 2		
	)
)

(instance sparkle of Actor
	(properties
		view 762
		loop 4
		cel 0
		signal (| ignrHrz fixPriOn)
		x 142
		y 18
		priority 1
	)
)

(instance shimmer of Actor
	(properties
		view 766
		loop 4
		cel 0
		x 230
		y 106
		cycleSpeed 3
		priority 15
		signal fixPriOn
	)
)