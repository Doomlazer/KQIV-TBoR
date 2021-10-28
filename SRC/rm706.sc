;;; Sierra Script 1.0 - (do not remove this comment)
(script# 706)
(include system.sh) ;for p_at, etc.
(include keys.sh)
;(include sci.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use User)
(use Actor)
(use System)

(public
	Room706 0
)

(local
	;[local0 100]
	[str 41] ;pCommandString
	eventMessage
	;local142 unused?
	local143
	local144 ;currentFolder (personnel:2, homicide:4, vice:7, burglary:5, firearms:6)
	local145
	local146 ;Change Director
	local147 ;inPersonnelDIR
	local148 ;inCriminalDIR
	local149 ;inSierraDIR
	local150 ;ready for input?
	local151 ;cue computerScript
	local152 ;selectedRecordNum
	;local153 unused?
	local154 ;inputCursorX
)
(procedure (localproc_000c &tmp newEvent) ;clean up DIR CD listing?
	(while ((= newEvent (Event new:)) type?)
		(newEvent dispose:)
	)
	(newEvent dispose:)
)

(procedure (localproc_0031 param1) ;letter to uppercase
	(return
		(if (and (<= 97 param1) (<= param1 122))
			(return (- param1 32))
		else
			(return param1)
		)
	)
)

(procedure (localproc_01c0) ;display record
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
		(Load VIEW 591)
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
		(shaw 
			view: 591 
			loop: 3 
			cel: 0 
			posn: 83 150 
			init: 
			addToPic:
		)
		(Format @str 706 0) ;;empty line
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
				((event claimed?) (return 1))
				((== (event type?) saidEvent)
					(cond 
						((Said 'look/book,instruction,cocksucker') (Print 706 1)) ;cocksucker = DOS in vocab
						((Said 'look/switch,button,power') (Print 706 2))
						((Said 'look/computer') (Print 706 3))
						((Said 'exit,walk,go,quit') (HandsOn) (curRoom newRoom:705))
						((Said 'type,enter,cd,cd') (Print 706 4))
						(
							(or
								(Said 'turn,log<on[/computer,power,button]')
								(Said 'activate,use,logon[/computer]')
								(Said 'flip,push,activate,press[/button,power,switch]')
							)
							(lite1 hide:)
							(lite2 hide:)
							(Room706 setScript: computerScript)
						)
						((Said '[<around,at][/(!*)]') (Print 706 5))
						(else 
							(Print 704 22)
							(event claimed: 1)
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
			(self changeState: 1)
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
					view: 9
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
					(Display 8 6
						p_at 73 local143
						p_font 7
						p_color 0
						p_back 0
					)
					(= local143 (+ local143 10))
				)
				(if (cast contains: fileCursor)
					(fileCursor dispose:)
				)
				(if (not local146)
					(self cue:)
				)
			)
			(2
				(= local150 1)
				(Display 8 7 
					p_at 73 14 
					p_font 7 
					p_color 0
				)
				(Display 8 8
					p_at 73 14
					p_font 7
					p_color 9
					p_back 0
				)
			)
			(3
				(Display 8 9
					p_at 73 14
					p_font 7
					p_color 0
					p_back 0
				)
				(Display 8 9
					p_at 73 15
					p_font 7
					p_color 0
					p_back 0
				)
				(Display 8 10 ;session complete
					p_at 73 14
					p_font 7
					p_color 14
					p_back 0
				)
				(= newRoomNum prevRoomNum)
			)
		)
	)
	
	(method (handleEvent event &tmp [temp0 4])
		(switch (event type?)
			(direction ;evJOYSTICK
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
								((== (fileCursor x?) 71)
									(= local152 11)
									(fileCursor posn: 162 33)
								)
								(else (= local152 1)
									(fileCursor posn: 71 33)
								)
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
								((== (fileCursor x?) 71)
									(= local152 20)
									(fileCursor posn: 162 123)
								)
								(else (= local152 10)
									(fileCursor posn: 71 123)
								)
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
			(keyDown ;evKEYBOARD
				(if
					(or
						(== eventMessage KEY_F6)
						(== eventMessage KEY_F8)
						(== eventMessage KEY_F10)
					)
					(Print 8 11) ;disable gun
					(event claimed: 1)
				)
				(if local150
					(event claimed: 1)
					(= local154 (StrLen @str))
					(cond 
						(
							(and
								(< KEY_SPACE (event message?))
								(< (event message?) KEY_DELETE) ;127
								(< local154 13)
							)
							(StrAt @str local154 (localproc_0031 eventMessage))
							(++ local154)
							(StrAt @str local154 0)
							(Display (Format @temp0 {%c} eventMessage)
								p_at (compCursor x?) (- (compCursor y?) 8)
								p_font 7
								p_color 9
								p_back 0
							)
							(compCursor x: (+ (compCursor x?) 6))
						)
						((and (== JOY_UPLEFT eventMessage) local154)
							(-- local154)
							(StrAt @str local154 0)
							(compCursor x: (- (compCursor x?) 6))
							(Display 8 12
								p_at (compCursor x?) (- (compCursor y?) 8)
								p_font 7
								p_color 0
								p_back 0
							)
						)
						((== eventMessage KEY_RETURN)
							(Display 8 13
								p_at 123 14
								p_color 0
								p_font 7
								p_back 0
							)
							(Display 8 13
								p_at 123 15
								p_color 0
								p_font 7
								p_back 0
							)
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
											(self changeState: 1))
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
										(else (= local145 0)
											(= local146 0)
											(self changeState: 1)
										)
									)
									(if local148
										(Display 8 14
											p_at 73 14
											p_color 0
											p_font 7
											p_back 0
										)
										(Display 8 15
											p_at 73 14
											p_color 9
											p_font 7
											p_back 0
										)
									)
								)
								(local148
									(= local148 0)
									(cond 
										(
										(and (not (StrCmp @str {ICECREAM})) (== local144 4))
											(= local145 local144))
										(
										(and (not (StrCmp @str {PISTACHIO})) (== local144 2))
											(= local145 local144)
											;(SolvePuzzle 2 120)
										)
										((and (not (StrCmp @str {MIAMI})) (== local144 7))
											(= local145 local144)
											;(SolvePuzzle 2 121)
										)
										(else
											(Print 8 16 #time 3)
										)
									)
									(self changeState: 1)
								)
								(local149
									(switch local152
										(1 (localproc_01c0 8 17))
										(2 (localproc_01c0 8 18))
										(3 (localproc_01c0 8 19))
										(4 (localproc_01c0 8 20))
										(5
											(localproc_01c0 8 21)
											(localproc_01c0 8 22)
										)
										(6 (localproc_01c0 8 23))
										(7
											(localproc_01c0 8 24)
											(localproc_01c0 8 25)
										)
										(8 (localproc_01c0 8 26))
										(9 (localproc_01c0 8 27))
										(10 (localproc_01c0 8 28))
										(11 (localproc_01c0 8 29))
										(12 (localproc_01c0 8 30))
										(13 (localproc_01c0 8 31))
										(14 (localproc_01c0 8 32))
										(15 (localproc_01c0 8 33))
									)
								)
								(local147
									(switch local145
										(4
											(switch local152
												(1
													(localproc_01c0 8 34)
													(localproc_01c0 8 35)
												)
												(2 (localproc_01c0 8 36))
												(3
													(localproc_01c0 8 37)
													(localproc_01c0 8 38)
												)
												(4 (localproc_01c0 8 39))
												(5 (localproc_01c0 8 40))
												(6
													(localproc_01c0 8 41)
													(localproc_01c0 8 42)
												)
												(7 (localproc_01c0 8 43))
												(11
													(localproc_01c0 8 44)
													(localproc_01c0 8 45)
												)
												(12
													(localproc_01c0 8 46)
													(localproc_01c0 8 47)
												)
												(13 (localproc_01c0 8 48))
												(14 (localproc_01c0 8 49))
												(15 (localproc_01c0 8 50))
												(16 (localproc_01c0 8 51))
											)
										)
										(2
											(switch local152
												(1
													(localproc_01c0 8 52)
													(localproc_01c0 8 53)
												)
												(2
													(localproc_01c0 8 54)
													(localproc_01c0 8 55)
													(localproc_01c0 8 56)
												)
												(3
													(localproc_01c0 8 57)
													(localproc_01c0 8 58)
													(localproc_01c0 8 59)
												)
												(4
													(localproc_01c0 8 60)
													(localproc_01c0 8 61)
													(localproc_01c0 8 62)
												)
												(5
													(localproc_01c0 8 63)
													(localproc_01c0 8 64)
												)
												(6
													(localproc_01c0 8 65)
													(localproc_01c0 8 66)
												)
												(7
													(localproc_01c0 8 67)
													(localproc_01c0 8 68)
												)
												(8
													(localproc_01c0 8 69)
													(localproc_01c0 8 70)
													(localproc_01c0 8 71)
												)
												(9
													(localproc_01c0 8 72)
													(localproc_01c0 8 73)
												)
												(10
													(localproc_01c0 8 74)
													(localproc_01c0 8 75)
													(localproc_01c0 8 76)
													;(Bset 56) ;read Pratt record
												)
												(11
													(localproc_01c0 8 77)
													(localproc_01c0 8 78)
												)
												(12
													(localproc_01c0 8 79)
													(localproc_01c0 8 80)
													(localproc_01c0 8 81)
												)
												(13
													(localproc_01c0 8 82)
													(localproc_01c0 8 83)
													(localproc_01c0 8 84)
													(localproc_01c0 8 85)
												)
												(14
													(localproc_01c0 8 86)
													(localproc_01c0 8 87)
												)
												(15
													(localproc_01c0 8 88)
													(localproc_01c0 8 89)
												)
												(16
													(localproc_01c0 8 90)
													(localproc_01c0 8 91)
												)
												(17 (localproc_01c0 8 92))
											)
										)
										(7
											(switch local152
												(1
													(localproc_01c0 8 93)
													(localproc_01c0 8 94)
												)
												(2
													(localproc_01c0 8 95)
													(localproc_01c0 8 96)
												)
												(3
													(localproc_01c0 8 97)
													(localproc_01c0 8 98)
												)
												(4
													(localproc_01c0 8 99)
													(localproc_01c0 8 100)
												)
												(5
													(localproc_01c0 8 101)
													(localproc_01c0 8 102)
												)
												(6
													(localproc_01c0 8 103)
													(localproc_01c0 8 104)
												)
												(7
													(localproc_01c0 8 105)
													(localproc_01c0 8 106)
												)
												(8
													(localproc_01c0 8 107)
													(localproc_01c0 8 108)
												)
												(9
													(localproc_01c0 8 109)
													(localproc_01c0 8 110)
												)
												(11
													(localproc_01c0 8 111)
													(localproc_01c0 8 112)
												)
												(12
													(localproc_01c0 8 113)
													(localproc_01c0 8 114)
												)
												(13
													(localproc_01c0 8 115)
													(localproc_01c0 8 116)
												)
												(14
													(localproc_01c0 8 117)
													(localproc_01c0 8 118)
												)
												(15
													(localproc_01c0 8 119)
													(localproc_01c0 8 120)
												)
												(16
													(localproc_01c0 8 121)
													(localproc_01c0 8 122)
												)
											)
										)
									)
								)
							)
							(Format @str 8 0)
						)
					)
					(cond 
						(
							(and
								(not (StrCmp @str {DIR}))
								(not local146)
								(not local148)
							)
							(Format @str 8 0)
							(Display 8 13
								p_at 123 14
								p_color 0
								p_font 7
								p_back 0
							)
							(Display 8 13
								p_at 123 15
								p_color 0
								p_font 7
								p_back 0
							)
							(compCursor x: 123)
							(switch local145
								(0
									(Display 8 123
										p_at 73 24
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 124
										p_at 73 34
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 125
										p_at 73 44
										p_color 9
										p_font 7
										p_back 0
									)
								)
								(1
									(Display 8 126
										p_at 73 24
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 127
										p_at 73 34
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 128
										p_at 73 44
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 129
										p_at 73 54
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 130
										p_at 73 64
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 131
										p_at 73 74
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 132
										p_at 73 84
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 133
										p_at 73 94
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 134
										p_at 73 104
										p_color 9
										p_font 7
										p_back 0
									)
									;(Display (Format @local0 8 135)
									(Display 8 135
										p_at 73 114
										p_color 9
										p_font 7
										p_back 0
									)
									;(Display (Format @local0 8 136)
									(Display 8 136
										p_at 160 24
										p_color 9
										p_font 7
										p_back 0
									)
									;(Display (Format @local0 8 137)
									(Display 8 137
										p_at 160 34
										p_color 9
										p_font 7
										p_back 0
									)
									;(Display (Format @local0 8 138)
									(Display 8 138
										p_at 160 44
										p_color 9
										p_font 7
										p_back 0
									)
									;(Display (Format @local0 8 139)
									(Display 8 139
										p_at 160 54
										p_color 9
										p_font 7
										p_back 0
									)
									;(Display (Format @local0 8 140)
									(Display 8 140
										p_at 160 64
										p_color 9
										p_font 7
										p_back 0
									)
									(= local149 1)
									(= local152 1)
									(fileCursor view: 9 loop: 1 posn: 71 32 init:)
								)
								(2
									;(Display (Format @local0 8 141)
									(Display 8 141
										p_at 73 24
										p_color 9
										p_font 7
									)
									;(Display (Format @local0 8 142)
									(Display 8 142
										p_at 73 34
										p_color 9
										p_font 7
									)
									;(Display (Format @local0 8 143)
									(Display 8 143
										p_at 73 44
										p_color 9
										p_font 7
									)
									;(Display (Format @local0 8 144)
									(Display 8 144
										p_at 73 54
										p_color 9
										p_font 7
									)
									;(Display (Format @local0 8 145)
									(Display 8 145
										p_at 73 64
										p_color 9
										p_font 7
									)
									;(Display (Format @local0 8 146)
									(Display 8 146
										p_at 73 74
										p_color 9
										p_font 7
									)
									(Display 8 147
										p_at 73 84
										p_color 9
										p_font 7
									)
									(Display 8 148
										p_at 73 94
										p_color 9
										p_font 7
									)
									(Display 8 149
										p_at 73 104
										p_color 9
										p_font 7
									)
									(Display 8 150
										p_at 73 114
										p_color 9
										p_font 7
									)
									(Display 8 151
										p_at 155 24
										p_color 9
										p_font 7
									)
									(Display 8 152
										p_at 155 34
										p_color 9
										p_font 7
									)
									(Display 8 153
										p_at 155 44
										p_color 9
										p_font 7
									)
									(Display 8 154
										p_at 155 54
										p_color 9
										p_font 7
									)
									(Display 8 155
										p_at 155 64
										p_color 9
										p_font 7
									)
									(Display 8 156
										p_at 155 74
										p_color 9
										p_font 7
									)
									(Display 8 157
										p_at 155 84
										p_color 9
										p_font 7
									)
									(= local147 1)
									(= local152 1)
									(fileCursor view: 9 loop: 1 posn: 71 32 init:)
								)
								(3
									(Display 8 158
										p_at 73 24
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 159
										p_at 73 34
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 160
										p_at 73 44
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 161
										p_at 73 54
										p_color 9
										p_font 7
										p_back 0
									)
								)
								(4
									(Display 8 162
										p_at 73 24
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 163
										p_at 73 34
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 164
										p_at 73 44
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 165
										p_at 73 54
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 166
										p_at 73 64
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 167
										p_at 73 74
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 168
										p_at 73 84
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 169
										p_at 158 24
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 170
										p_at 158 34
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 171
										p_at 158 44
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 172
										p_at 158 54
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 173
										p_at 158 64
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 174
										p_at 158 74
										p_color 9
										p_font 7
										p_back 0
									)
									(= local147 1)
									(= local152 1)
									(fileCursor view: 9 loop: 1 posn: 71 32 init:)
								)
								(7
									(Display 8 175
										p_at 73 24
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 176
										p_at 73 34
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 177
										p_at 73 44
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 178
										p_at 73 54
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 179
										p_at 73 64
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 180
										p_at 73 74
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 181
										p_at 73 84
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 182
										p_at 73 94
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 183
										p_at 73 104
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 184
										p_at 154 24
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 185
										p_at 154 34
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 186
										p_at 154 44
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 187
										p_at 154 54
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 188
										p_at 154 64
										p_color 9
										p_font 7
										p_back 0
									)
									(Display 8 189
										p_at 154 74
										p_color 9
										p_font 7
										p_back 0
									)
									(= local147 1)
									(= local152 1)
									(fileCursor view: 9 loop: 1 posn: 71 32 init:)
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
							(Format @str 8 0)
							(Display 8 14
								p_at 73 14
								p_color 0
								p_font 7
								p_back 0
							)
							(Display 8 190
								p_at 73 14
								p_color 9
								p_font 7
								p_back 0
							)
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
