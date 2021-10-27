;;; Sierra Script 1.0 - (do not remove this comment)
(script# 706)
;(include sci.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use User)
(use Actor)
(use System)
(include game.sh)
(use Save)

(public
	Room706 0
)

(local
	[local0 100]
	[str 41]
	eventMessage
	local142
	local143
	local144
	local145
	local146
	local147
	local148
	local149
	local150
	local151
	local152
	local153
	local154
)
(procedure (localproc_000c &tmp newEvent)
	(while ((= newEvent (Event new:)) type?)
		(newEvent dispose:)
	)
	(newEvent dispose:)
)

(procedure (localproc_0031 param1)
	(return
		(if (and (<= 97 param1) (<= param1 122))
			(return (- param1 32))
		else
			(return param1)
		)
	)
)

(procedure (localproc_01c0)
	(Print &rest #font 7 #width 168 #at 70 10)
)

(instance compCursor of Prop
	(properties)
)

(instance fileCursor of Prop
	(properties)
)

(instance lite1 of View
	(properties)
)

(instance lite2 of View
	(properties)
)

(instance shaw of View
	(properties)
)

(instance Room706 of Room
	(properties
		picture 706
		style $0007
	)
	
	(method (init)
		(super init:)
		(HandsOff)
		(User canInput: 1)
		(Load rsVIEW 591)
		(lite1
			view: 591
			loop: 2
			cel: 0
			posn: 256 178
			init:
			stopUpd:
		)
		(lite2
			view: 591
			loop: 2
			cel: 1
			posn: 234 143
			init:
			stopUpd:
		)
		(shaw view: 591 loop: 3 cel: 0 posn: 83 150 init: addToPic:)
		(Format @str 706 0) ;;empty line?
		(self setScript: RoomScript)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit)
		(super doit:)
		; code executed each game cycle
	)
		
	(method (handleEvent event)
		(return
			(cond 
				((event claimed?) (return TRUE))
				((== (event type?) saidEvent)
					(cond 
						((Said 'look/book,instruction,cocksucker') (Print 706 1))
						((Said 'look/switch,button,power') (Print 706 2))
						((Said 'look/computer') (Print 706 3))
						((Said 'exit,walk,go,quit') (HandsOn) (curRoom newRoom:705))
						((Said 'type,enter,cd,cd') (Print 706 4))
						(
							(or
								(Said 'turn,log<on[/computer,power,button]')
								(Said 'activate,use,logon[/computer]')
								(Said 'flip,press,activate,press[/button,power,switch]')
							)
							(lite1 hide:)
							(lite2 hide:)
							(Room706 setScript: computerScript)
						)
						((Said '[<around,at][/(!*)]') (Print 706 5))
						(else 
							(Print 704 22) ;#font 605 )
							(event claimed: TRUE)
						)
					)
				)
			)
		)
	)
)

(instance computerScript of Script
	(properties)
	
	(method (doit)
		(if (> local151 1) (-- local151))
		(if (== local151 1) (= local151 0) (self cue:))
		(if
			(and
				(not local149)
				(not local147)
				(cast contains: fileCursor)
			)
			(fileCursor dispose:)
			(self changeState: 1) ;exit os
		)
		(cond 
			((<= (compCursor x?) 123) 
				(compCursor x: 123)
			)
			((>= (compCursor x?) 236) 
				(compCursor x: 236)
			)
		)
		(super doit:)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(compCursor
					view: 591
					posn: 123 22
					cycleSpeed: 3
					setCycle: Forward
					init:
				)
				(= local151 5)
			)
			(1
				(= local143 24)
				(while (<= local143 114)
					(Display 706 6 dsCOORD 73 local143 dsFONT 7 dsCOLOR 0 dsBACKGROUND 0) ;empty line
					(= local143 (+ local143 10))
				)
				(if (cast contains: fileCursor) (fileCursor dispose:))
				(if (not local146) (self cue:))
			)
			(2
				(= local150 1)
				(Display 706 7 dsCOORD 73 14 dsFONT 7 dsCOLOR 0 ) ;empty line
				(Display 706 8 dsCOORD 73 14 dsFONT 7 dsCOLOR 9 dsBACKGROUND 0) ;COMMAND> 
			)
			(3
				(Display 706 9 dsCOORD 73 14 dsFONT 7 dsCOLOR 0 dsBACKGROUND 0) ;empty line
				(Display 706 9 dsCOORD 73 15 dsFONT 7 dsCOLOR 0 dsBACKGROUND 0) ;empty line
				(Display 706 10 dsCOORD 73 14 dsFONT 7 dsCOLOR 14 dsBACKGROUND 0) ;Session Complete.
				(HandsOn)
				(curRoom newRoom: 705)
				;(= newRoomNum prevRoomNum)
			)
		)
	)
	
	(method (handleEvent event &tmp [temp0 4])
		(switch (event type?)
			(evJOYSTICK
				(if (or local147 local149)
					(event claimed: 1)
					(switch (event message?)
						(JOY_UP
							(cond 
								((> (fileCursor y?) 40)
									(fileCursor
										posn: (fileCursor x?) (- (fileCursor y?) 10)
									)
									(-- local152)
								)
								((== (fileCursor x?) 71) (= local152 11) (fileCursor posn: 162 33))
								(else (= local152 1) (fileCursor posn: 71 33))
							)
						)
						(JOY_DOWN
							(cond 
								((< (fileCursor y?) 114)
									(++ local152)
									(fileCursor
										posn: (fileCursor x?) (+ (fileCursor y?) 10)
									)
								)
								((== (fileCursor x?) 71) (= local152 20) (fileCursor posn: 162 123))
								(else (= local152 10) (fileCursor posn: 71 123))
							)
						)
						(else 
							(if (== (fileCursor x?) 71)
								(= local152 (+ local152 10))
								(fileCursor posn: 162 (fileCursor y?))
							else
								(= local152 (- local152 10))
								(fileCursor posn: 71 (fileCursor y?))
							)
						)
					)
				)
			)
			(evKEYBOARD
				(if
					(or
						(== (= eventMessage (event message?)) 16384)
						(== eventMessage KEY_F8)
						(== eventMessage KEY_F10)
					)
					(Print 706 11) ;Calm down. You have no need of your gun here.
					(event claimed: 1)
				)
				(if local150
					(event claimed: 1)
					(= local154 (StrLen @str))
					(cond 
						(
							(and
								(< KEY_SPACE (event message?))
								(< (event message?) 127)
								(< local154 13)
							)
							(StrAt @str local154 (localproc_0031 eventMessage))
							(++ local154)
							(StrAt @str local154 0)
							(Display (Format @temp0 {%c} eventMessage) dsCOORD (compCursor x?) (- (compCursor y?) 8) dsFONT 7 dsCOLOR 9 dsBACKGROUND 0)
							(compCursor x: (+ (compCursor x?) 6))
						)
						((and (== JOY_UPLEFT eventMessage) local154)
							(-- local154)
							(StrAt @str local154 0)
							(compCursor x: (- (compCursor x?) 6))
							(Display 706 12 dsCOORD (compCursor x?) (- (compCursor y?) 8) dsFONT 7 dsCOLOR 0 dsBACKGROUND 0) ;cursor probably, 12 is an empty line
						)
						((== eventMessage KEY_RETURN)
							(Display 706 13 dsCOORD 123 14 dsCOLOR 0 dsFONT 7 dsBACKGROUND 0) ;empty line
							(Display 706 13 dsCOORD 123 15 dsCOLOR 0 dsFONT 7 dsBACKGROUND 0) ;empty line
							(= local154 0)
							(compCursor x: 123)
							(cond 
								(local146
									(cond 
										((not (StrCmp @str {CRIMINAL}))
											(= local145 3)
											(= local146 0)
											(self changeState: 1)
										)
										((not (StrCmp @str {SIERRA}))
											(= local145 1)
											(= local146 0)
											(self changeState: 1)
										)
										((not (StrCmp @str {PERSONNEL}))
											(= local144 2)
											(= local146 0)
											(= local148 1)
											(self changeState: 1)
										)
										((and (not (StrCmp @str {HOMICIDE})) (== local145 3))
											(= local144 4)
											(= local146 0)
											(= local148 1)
											(self changeState: 1)
										)
										((and (not (StrCmp @str {VICE})) (== local145 3))
											(= local144 7)
											(= local146 0)
											(= local148 1)
											(self changeState: 1)
										)
										((and (not (StrCmp @str {BURGLARY})) (== local145 3))
											(= local144 5)
											(= local146 0)
											(= local148 1)
											(self changeState: 1)
										)
										((and (not (StrCmp @str {FIREARMS})) (== local145 3))
											(= local144 6)
											(= local146 0)
											(= local148 1)
											(self changeState: 1)
										)
										(else 
											(= local145 0)
											(= local146 0)
											(self changeState: 1)
										)
									)
									(if local148
										(Display 706 14 dsCOORD 73 14 dsCOLOR 0 dsFONT 7 dsBACKGROUND 0) ;empty line
										(Display 706 15 dsCOORD 73 14 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;PASSWORD:
									)
								)
								(local148
									(= local148 0)
									(cond 
										((and (not (StrCmp @str {ICECREAM})) 
											(== local144 4)) 
											(= local145 local144)
										)
										((and (not (StrCmp @str {PISTACHIO}))
											(== local144 2))
											(= local145 local144) 
											;(SolvePuzzle 2 120)
										)
										((and (not (StrCmp @str {MIAMI}))
											(== local144 7))
											(= local145 local144) 
											;(SolvePuzzle 2 121)
										)
										(else 
											(Print 706 16 #time 3)
										)
									)
									(self changeState: 1)
								)
								(local149
									(switch local152
										(1 (localproc_01c0 706 17))
										(2 (localproc_01c0 706 18))
										(3 (localproc_01c0 706 19))
										(4 (localproc_01c0 706 20))
										(5
											(localproc_01c0 706 21)
											(localproc_01c0 706 22)
										)
										(6 (localproc_01c0 706 23))
										(7
											(localproc_01c0 706 24)
											(localproc_01c0 706 25)
										)
										(8 (localproc_01c0 706 26))
										(9 (localproc_01c0 706 27))
										(10 (localproc_01c0 706 28))
										(11 (localproc_01c0 706 29))
										(12 (localproc_01c0 706 30))
										(13 (localproc_01c0 706 31))
										(14 (localproc_01c0 706 32))
										(15 (localproc_01c0 706 33))
									)
								)
								(local147
									(switch local145
										(4
											(switch local152
												(1
													(localproc_01c0 706 34)
													(localproc_01c0 706 35)
												)
												(2 (localproc_01c0 706 36))
												(3
													(localproc_01c0 706 37)
													(localproc_01c0 706 38)
												)
												(4 (localproc_01c0 706 39))
												(5 (localproc_01c0 706 40))
												(6
													(localproc_01c0 706 41)
													(localproc_01c0 706 42)
												)
												(7 (localproc_01c0 706 43))
												(11
													(localproc_01c0 706 44)
													(localproc_01c0 706 45)
												)
												(12
													(localproc_01c0 706 46)
													(localproc_01c0 706 47)
												)
												(13 (localproc_01c0 706 48))
												(14 (localproc_01c0 706 49))
												(15 (localproc_01c0 706 50))
												(16 (localproc_01c0 706 51))
											)
										)
										(2
											(switch local152
												(1
													(localproc_01c0 706 52)
													(localproc_01c0 706 53)
												)
												(2
													(localproc_01c0 706 54)
													(localproc_01c0 706 55)
													(localproc_01c0 706 56)
												)
												(3
													(localproc_01c0 706 57)
													(localproc_01c0 706 58)
													(localproc_01c0 706 59)
												)
												(4
													(localproc_01c0 706 60)
													(localproc_01c0 706 61)
													(localproc_01c0 706 62)
												)
												(5
													(localproc_01c0 706 63)
													(localproc_01c0 706 64)
												)
												(6
													(localproc_01c0 706 65)
													(localproc_01c0 706 66)
												)
												(7
													(localproc_01c0 706 67)
													(localproc_01c0 706 68)
												)
												(8
													(localproc_01c0 706 69)
													(localproc_01c0 706 70)
													(localproc_01c0 706 71)
												)
												(9
													(localproc_01c0 706 72)
													(localproc_01c0 706 73)
												)
												(10
													(localproc_01c0 706 74)
													(localproc_01c0 706 75)
													(localproc_01c0 706 76)
													;(Bset 56)  -some puzzle-solve flag
												)
												(11
													(localproc_01c0 706 77)
													(localproc_01c0 706 78)
												)
												(12
													(localproc_01c0 706 79)
													(localproc_01c0 706 80)
													(localproc_01c0 706 81)
												)
												(13
													(localproc_01c0 706 82)
													(localproc_01c0 706 83)
													(localproc_01c0 706 84)
													(localproc_01c0 706 85)
												)
												(14
													(localproc_01c0 706 86)
													(localproc_01c0 706 87)
												)
												(15
													(localproc_01c0 706 88)
													(localproc_01c0 706 89)
												)
												(16
													(localproc_01c0 706 90)
													(localproc_01c0 706 91)
												)
												(17 (localproc_01c0 706 92))
											)
										)
										(7
											(switch local152
												(1
													(localproc_01c0 706 93)
													(localproc_01c0 706 94)
												)
												(2
													(localproc_01c0 706 95)
													(localproc_01c0 706 96)
												)
												(3
													(localproc_01c0 706 97)
													(localproc_01c0 706 98)
												)
												(4
													(localproc_01c0 706 99)
													(localproc_01c0 706 100)
												)
												(5
													(localproc_01c0 706 101)
													(localproc_01c0 706 102)
												)
												(6
													(localproc_01c0 706 103)
													(localproc_01c0 706 104)
												)
												(7
													(localproc_01c0 706 105)
													(localproc_01c0 706 106)
												)
												(8
													(localproc_01c0 706 107)
													(localproc_01c0 706 108)
												)
												(9
													(localproc_01c0 706 109)
													(localproc_01c0 706 110)
												)
												(11
													(localproc_01c0 706 111)
													(localproc_01c0 706 112)
												)
												(12
													(localproc_01c0 706 113)
													(localproc_01c0 706 114)
												)
												(13
													(localproc_01c0 706 115)
													(localproc_01c0 706 116)
												)
												(14
													(localproc_01c0 706 117)
													(localproc_01c0 706 118)
												)
												(15
													(localproc_01c0 706 119)
													(localproc_01c0 706 120)
												)
												(16
													(localproc_01c0 706 121)
													(localproc_01c0 706 122)
												)
											)
										)
									)
								)
							)
							(Format @str 706 0) ;empty line
						)
					)
					(cond 
						(
							(and
								(not (StrCmp @str {DIR}))
								(not local146)
								(not local148)
							)
							(Format @str 706 0);(Format @str 8 0)
							(Display 706 13 dsCOORD 123 14 dsCOLOR 0 dsFONT 7 dsBACKGROUND 0) ;empyt line
							(Display 706 13 dsCOORD 123 15 dsCOLOR 0 dsFONT 7 dsBACKGROUND 0) ;empty line
							(compCursor x: 123)
							(switch local145
								(0
									(Display 706 123 dsCOORD 73 24 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;Criminal
									(Display 706 124 dsCOORD 73 34 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;Sierra
									(Display 706 125 dsCOORD 73 44 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;personnel
								)
								(1
									(Display 706 126 dsCOORD 73 24 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;PQ
									(Display 706 127 dsCOORD 73 34 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;KQ
									(Display 706 128 dsCOORD 73 44 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;KQII
									(Display 706 129 dsCOORD 73 54 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;KQIII
									(Display 706 130 dsCOORD 73 64 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;KQIV
									(Display 706 131 dsCOORD 73 74 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;Silpheed
									(Display 706 132 dsCOORD 73 84 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;manhunter
									(Display 706 133 dsCOORD 73 94 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;thexder
									(Display 706 134 dsCOORD 73 104 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;mother Goose
									(Display 706 135 dsCOORD 73 114 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;Gold Rush
									(Display 706 136 dsCOORD 160 24 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;SQI
									(Display 706 137 dsCOORD 160 34 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;SQII
									(Display 706 138 dsCOORD 160 44 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;SQIII
									(Display 706 139 dsCOORD 160 54 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;lSLI
									(Display 706 140 dsCOORD 160 64 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;LSLII
									(= local149 1)
									(= local152 1)
									(fileCursor view: 591 loop: 1 posn: 71 32 init:)
								)
								(2
									(Display 706 141 dsCOORD 73 24 dsCOLOR 9 dsFONT 7)
									(Display 706 142 dsCOORD 73 34 dsCOLOR 9 dsFONT 7)
								 	(Display 706 143 dsCOORD 73 44 dsCOLOR 9 dsFONT 7)
									(Display 706 144 dsCOORD 73 54 dsCOLOR 9 dsFONT 7)
									(Display 706 145 dsCOORD 73 64 dsCOLOR 9 dsFONT 7)
									(Display 706 146 dsCOORD 73 74 dsCOLOR 9 dsFONT 7)
									(Display 706 147 dsCOORD 73 84 dsCOLOR 9 dsFONT 7)
									(Display 706 148 dsCOORD 73 94 dsCOLOR 9 dsFONT 7)
									(Display 706 149 dsCOORD 73 104 dsCOLOR 9 dsFONT 7)
									(Display 706 150 dsCOORD 73 114 dsCOLOR 9 dsFONT 7)
									(Display 706 151 dsCOORD 155 24 dsCOLOR 9 dsFONT 7)
									(Display 706 152 dsCOORD 155 34 dsCOLOR 9 dsFONT 7)
									(Display 706 153 dsCOORD 155 44 dsCOLOR 9 dsFONT 7)
									(Display 706 154 dsCOORD 155 54 dsCOLOR 9 dsFONT 7)
									(Display 706 155 dsCOORD 155 64 dsCOLOR 9 dsFONT 7)
									(Display 706 156 dsCOORD 155 74 dsCOLOR 9 dsFONT 7) ;sonny bonds
									(Display 706 157 dsCOORD 155 84 dsCOLOR 9 dsFONT 7)
									(= local147 1)
									(= local152 1)
									(fileCursor view: 591 loop: 1 posn: 71 32 init:)
								)
								(3
									(Display 706 158 dsCOORD 73 24 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 159 dsCOORD 73 34 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 160 dsCOORD 73 44 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 161 dsCOORD 73 54 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
								)
								(4
									(Display 706 162 dsCOORD 73 24 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 163 dsCOORD 73 34 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 164 dsCOORD 73 44 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 165 dsCOORD 73 54 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 166 dsCOORD 73 64 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 167 dsCOORD 73 74 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 168 dsCOORD 73 84 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 169 dsCOORD 158 24 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 170 dsCOORD 158 34 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 171 dsCOORD 158 44 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 172 dsCOORD 158 54 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 173 dsCOORD 158 64 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 174 dsCOORD 158 74 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(= local147 1)
									(= local152 1)
									(fileCursor view: 591 loop: 1 posn: 71 32 init:)
								)
								(7
									(Display 706 175 dsCOORD 73 24 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 176 dsCOORD 73 34 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 177 dsCOORD 73 44 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 178 dsCOORD 73 54 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 179 dsCOORD 73 64 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 180 dsCOORD 73 74 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 181 dsCOORD 73 84 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 182 dsCOORD 73 94 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 183 dsCOORD 73 104 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 184 dsCOORD 154 24 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 185 dsCOORD 154 34 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 186 dsCOORD 154 44 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;marie wilkans
									(Display 706 187 dsCOORD 154 54 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 188 dsCOORD 154 64 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(Display 706 189 dsCOORD 154 74 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0)
									(= local147 1)
									(= local152 1)
									(fileCursor view: 591 loop: 1 posn: 71 32 init:)
								)
							)
							(localproc_000c)
						)
						(
							(or
								(not (StrCmp @str {QUIT}))
								(not (StrCmp @str {LOGOUT}))
								(not (StrCmp @str {EXIT}))
								(not (StrCmp @str {BYE}))
							)
							(self changeState: 3)
						)
						((not (StrCmp @str {CD}))
							(Format @str 706 0)
							(Display 706 14 dsCOORD 73 14 dsCOLOR 0 dsFONT 7 dsBACKGROUND 0) ;empty line
							(Display 706 190 dsCOORD 73 14 dsCOLOR 9 dsFONT 7 dsBACKGROUND 0) ;dir
							(= local149 0)
							(= local147 0)
							(= local146 1)
							(localproc_000c)
						)
					)
				)
			)
		)
	)
)