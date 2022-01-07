;;; Sierra Script 1.0 - (do not remove this comment)
(script# 706)
(include system.sh) ;for p_at, etc.
(include keys.sh)
(include game.sh)
;(include sci.sh)
(use Main)
(use Intrface)
(use Motion)
(use Game)
(use User)
(use Actor)
(use System)
(use Sound)

(public
	Room706 0
)

(local
	;[local0 100]
	[str 41] ;pCommandString
	eventMessage
	;local142 unused?
	textCol
	cursorView
	local143
	local144 ;currentFolder (personnel:2, homicide:4, vice:7, burglary:5, firearms:6)
	local145
	local146 ;Change Directory?
	local147 ;inPersonnelDIR
	local148 ;requires password
	local149 ;inSierraDIR
	local150 ;ready for input?
	local151 ;cue computerScript
	local152 ;selectedRecordNum
	;local153 unused?
	local154 ;inputCursorX
	fontNum
	gotBookkeepingPoints
)
(procedure (localproc_000c &tmp newEvent) ;clean up DIR CD listing?
	(while ((= newEvent (Event new:)) type?)
		(newEvent dispose:)
	)
	(newEvent dispose:)
)

(procedure (LetterToUppercase param1) ;letter to uppercase
	(return
		(if (and (<= 97 param1) (<= param1 122))
			(return (- param1 32))
		else
			(return param1)
		)
	)
)

(procedure (LocPrint) ;display record
	(Print &rest #font fontNum #width 168 #at 70 10) ;font was 7
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
		(= fontNum 7)
		(if (ego has: iTooth)
			(= cursorView 593)
			(= textCol 10) ;green
		else
			(= cursorView 591)
			(= textCol 9) ;blue
		)
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
		(if (not (ego has: iTooth))
			(shaw 
				view: cursorView 
				loop: 3 
				cel: 0 
				posn: 83 150 
				init: 
				addToPic:
			)
		)
		(Format @str 706 0) ;empty line
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
		(if (> local151 1)
			(-- local151)
		)
		(if (== local151 1) 
			(= local151 0)
			(self cue:)
		)
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
					view: cursorView
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
					(Display 706 6
						p_at 73 local143
						p_font fontNum
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
				(Display 706 7 
					p_at 73 14 
					p_font fontNum 
					p_color 0
				)
				(Display 706 8
					p_at 73 14
					p_font fontNum
					p_color textCol
					p_back 0
				)
			)
			(3
				(Display 706 9
					p_at 73 14
					p_font fontNum
					p_color 0
					p_back 0
				)
				(Display 706 9
					p_at 73 15
					p_font fontNum
					p_color 0
					p_back 0
				)
				(Display 706 10 ;session complete
					p_at 73 14
					p_font fontNum
					p_color 14
					p_back 0
				)
				(HandsOn)
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
						(== (= eventMessage (event message?)) KEY_F6)
						(== eventMessage KEY_F8)
						(== eventMessage KEY_F10)
					)
					(Print 706 11) ;disable gun
					(event claimed: 1)
				)
				(if local150
					(event claimed: 1)
					(= local154 (StrLen @str))
					(cond 
						(
							(and
								(< KEY_SPACE (event message?))
								(< (event message?) 127) ;127
								(< local154 13)
							)
							(StrAt @str local154 (LetterToUppercase (event message?)))
							(++ local154)
							(StrAt @str local154 0)
							(Display (Format @temp0 {%c} eventMessage)
								p_at (compCursor x?) (- (compCursor y?) 8)
								p_font fontNum
								p_color textCol
								p_back 0
							)
							(compCursor x: (+ (compCursor x?) 6))
						)
						((and (== JOY_UPLEFT eventMessage) local154)
							(-- local154)
							(StrAt @str local154 0)
							(compCursor x: (- (compCursor x?) 6))
							(Display 706 12
								p_at (compCursor x?) (- (compCursor y?) 8)
								p_font fontNum
								p_color 0
								p_back 0
							)
						)
						((== eventMessage KEY_RETURN) ;process player command
							(Display 706 13
								p_at 123 14
								p_color 0
								p_font fontNum
								p_back 0
							)
							(Display 706 13
								p_at 123 15
								p_color 0
								p_font fontNum
								p_back 0
							)
							(= local154 0)
							(compCursor x: 123)
							(cond 
								(local146
									(cond 
										((not (StrCmp @str {WORK}))
											(= local145 3)
											(= local146 0)
											(self changeState: 1)
										)
										((not (StrCmp @str {GAMES}))
											(= local145 1)
											(= local146 0)
											(self changeState: 1))
										((not (StrCmp @str {BOOKKEEPING})) ;pistachio
											(= local144 2)
											(= local146 0)
											(= local148 1)
											(self changeState: 1)
										)
										((and (not (StrCmp @str {SUPPLIES})) (== local145 3))
											(= local144 4)
											(= local146 0)
											(= local148 1)
											(self changeState: 1)
										)
										((and (not (StrCmp @str {SURVEILLANCE})) (== local145 3)) ;miami
											(= local144 7)
											(= local146 0)
											(= local148 1)
											(self changeState: 1)
										)
										((and (not (StrCmp @str {RECIPIES})) (== local145 3))
											(= local144 5)
											(= local146 0)
											(= local148 1)
											(self changeState: 1)
										)
										((and (not (StrCmp @str {GENESTA})) (== local145 3)) ;god
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
										(Display 706 14
											p_at 73 14
											p_color 0
											p_font fontNum
											p_back 0
										)
										(Display 706 15
											p_at 73 14
											p_color textCol
											p_font fontNum
											p_back 0
										)
									)
								)
								(local148
									(= local148 0)
									(cond 
										(
;;;										(and (not (StrCmp @str {SEX})) (== local144 4)) ;supplies pw. No content here for now
;;;											(= local145 local144))
;;;										(
										(and (not (StrCmp @str {MONEY})) (== local144 2)) ;bookkeeping
											(= local145 local144)
											;(SolvePuzzle 2 120)
										)
										((and (not (StrCmp @str {SECRET})) (== local144 7)) ;SURVALIANCE
											(= local145 local144)
											;(SolvePuzzle 2 121)
										)
										((and (not (StrCmp @str {GEOFF})) (== local144 6)) ;Genesta Quest
											;(= local145 local144)
											;(SolvePuzzle 2 121)
											(HandsOn)
											(curRoom newRoom: 707)
										)
										(else
											(Print 706 16 #time 3)
										)
									)
									(self changeState: 1)
								)
								(local149
									(switch local152
										(1 (LocPrint 706 17))
										(2 (LocPrint 706 18))
										(3 (LocPrint 706 19))
										(4 (LocPrint 706 20))
										(5
											(LocPrint 706 21)
											(LocPrint 706 22)
										)
										(6 (LocPrint 706 23))
										(7
											(LocPrint 706 24)
											(LocPrint 706 25)
										)
										(8 (LocPrint 706 26))
										(9 (LocPrint 706 27))
										(10 (LocPrint 706 28))
										(11 (LocPrint 706 29))
										(12 (LocPrint 706 30))
										(13 (LocPrint 706 31))
										(14 (LocPrint 706 32))
										(15 (LocPrint 706 33))
									)
								)
								(local147
									(switch local145
										(4
											(switch local152
												(1
													(LocPrint 706 34)
													(LocPrint 706 35)
												)
												(2 (LocPrint 706 36))
												(3
													(LocPrint 706 37)
													(LocPrint 706 38)
												)
												(4 (LocPrint 706 39))
												(5 (LocPrint 706 40))
												(6
													(LocPrint 706 41)
													(LocPrint 706 42)
												)
												(7 (LocPrint 706 43))
												(11
													(LocPrint 706 44)
													(LocPrint 706 45)
												)
												(12
													(LocPrint 706 46)
													(LocPrint 706 47)
												)
												(13 (LocPrint 706 48))
												(14 (LocPrint 706 49))
												(15 (LocPrint 706 50))
												(16 (LocPrint 706 51))
											)
										)
										(2
											(switch local152
												(1
													(LocPrint 706 52) ;Fisherman
													(LocPrint 706 53)
												)
												(2
													(LocPrint 706 54)
													(LocPrint 706 55)
													(LocPrint 706 56)
												)
												(3
													(LocPrint 706 57)
													(LocPrint 706 58)
													(LocPrint 706 59)
												)
												(4
													;(LocPrint 706 60) ;blair witchhazel
													(LocPrint 706 61)
													(LocPrint 706 62)
												)
												(5
													(LocPrint 706 63) ;cave troll
													(LocPrint 706 64)
												)
												(6
													(LocPrint 706 65) ;lolotte bookeeping
													(LocPrint 706 66)
													(if (not gotBookkeepingPoints)
														(theGame changeScore: 20)
														((Sound new:) number: 690 play:)
														(= gotBookkeepingPoints 1)
														(Print 706 191)
													)
												)
;;;												(7
;;;													(LocPrint 706 67)
;;;													(LocPrint 706 68)
;;;												)
;;;												(8
;;;													(LocPrint 706 69)
;;;													(LocPrint 706 70)
;;;													(LocPrint 706 71)
;;;												)
;;;												(9
;;;													(LocPrint 706 72)
;;;													(LocPrint 706 73)
;;;												)
;;;												(10
;;;													(LocPrint 706 74)
;;;													(LocPrint 706 75)
;;;													(LocPrint 706 76)
;;;													;(Bset 56) ;read Pratt record
;;;												)
;;;												(11
;;;													(LocPrint 706 77)
;;;													(LocPrint 706 78)
;;;												)
;;;												(12
;;;													(LocPrint 706 79)
;;;													(LocPrint 706 80)
;;;													(LocPrint 706 81)
;;;												)
;;;												(13
;;;													(LocPrint 706 82)
;;;													(LocPrint 706 83)
;;;													(LocPrint 706 84)
;;;													(LocPrint 706 85)
;;;												)
;;;												(14
;;;													(LocPrint 706 86)
;;;													(LocPrint 706 87)
;;;												)
;;;												(15
;;;													(LocPrint 706 88)
;;;													(LocPrint 706 89)
;;;												)
;;;												(16
;;;													(LocPrint 706 90)
;;;													(LocPrint 706 91)
;;;												)
;;;												(17 
;;;													(LocPrint 706 92)
;;;												)
											)
										)
										(7
											(switch local152
												(1 ;cassima
													(LocPrint 706 93)
												;	(LocPrint 706 94)
												)
												(2 ;kate
													(LocPrint 706 95)
													;(LocPrint 706 96)
												)
												(3 ;cherubs
													(LocPrint 706 97)
													;(LocPrint 706 98)
												)
												(4 ;grace nakimura
													(LocPrint 706 99)
													;(LocPrint 706 100)
												)
												(5 ;roger wilco
													(LocPrint 706 101)
													(LocPrint 706 102)
												)
												(6 ;Fenrus
													(LocPrint 706 103)
													;(LocPrint 706 104)
												)
												(7 ;Cetus
													(LocPrint 706 105)
													;(LocPrint 706 106)
												)
												(8 ;larry laffer
													(LocPrint 706 107)
													;(LocPrint 706 108)
												)
												(9 ;geoff
													(LocPrint 706 109)
													(LocPrint 706 110)
												)
												(11 ;gabriel knight
													(LocPrint 706 111)
													;(LocPrint 706 112)
												)
												(12 ;Rosella
													(LocPrint 706 113)
													;(LocPrint 706 114)
												)
												(13 ;marie 
													(LocPrint 706 115)
													;(LocPrint 706 116)
												)
												(14 ;Sonny Bonds
													(LocPrint 706 117)
													(LocPrint 706 118)
													(if (not readSonnysFile)
														(theGame changeScore: 50)
														(= readSonnysFile 1)
														((Sound new:) number: 690 play:)
														(Print {Hmm, I guess Sonny is a bad apple!})
													)
												)
												(15 ;heros quest guy
													(LocPrint 706 119)
													;(LocPrint 706 120)
												)
												(16 ;sludge vohaul
													(LocPrint 706 121)
													;(LocPrint 706 122)
												)
											)
										)
									)
								)
							)
							(Format @str 706 0)
						)
					)
					(cond 
						(
							(and
								(not (StrCmp @str {DIR}))
								(not local146)
								(not local148)
							)
							(Format @str 706 0)
							(Display 706 13
								p_at 123 14
								p_color 0
								p_font fontNum
								p_back 0
							)
							(Display 706 13
								p_at 123 15
								p_color 0
								p_font fontNum
								p_back 0
							)
							(compCursor x: 123)
							(switch local145
								(0
									(Display 706 123
										p_at 73 24
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 124
										p_at 73 34
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 125
										p_at 73 44
										p_color textCol
										p_font fontNum
										p_back 0
									)
								)
								(1
									(Display 706 126
										p_at 73 24
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 127
										p_at 73 34
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 128
										p_at 73 44
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 129
										p_at 73 54
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 130
										p_at 73 64
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 131
										p_at 73 74
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 132
										p_at 73 84
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 133
										p_at 73 94
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 134
										p_at 73 104
										p_color textCol
										p_font fontNum
										p_back 0
									)
									;(Display (Format @local0 8 135)
									(Display 706 135
										p_at 73 114
										p_color textCol
										p_font fontNum
										p_back 0
									)
									;(Display (Format @local0 8 136)
									(Display 706 136
										p_at 160 24
										p_color textCol
										p_font fontNum
										p_back 0
									)
									;(Display (Format @local0 8 137)
									(Display 706 137
										p_at 160 34
										p_color textCol
										p_font fontNum
										p_back 0
									)
									;(Display (Format @local0 8 138)
									(Display 706 138
										p_at 160 44
										p_color textCol
										p_font fontNum
										p_back 0
									)
									;(Display (Format @local0 8 139)
									(Display 706 139
										p_at 160 54
										p_color textCol
										p_font fontNum
										p_back 0
									)
									;(Display (Format @local0 8 140)
									(Display 706 140
										p_at 160 64
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(= local149 1)
									(= local152 1)
									(fileCursor view: 591 loop: 1 posn: 71 32 init:)
								)
								(2 ;;Bookkeeping listing
									(Display 706 141 ;fisherman
										p_at 73 24
										p_color textCol
										p_font fontNum
									)
									(Display 706 142
										p_at 73 34
										p_color textCol
										p_font fontNum
									)
									(Display 706 143
										p_at 73 44
										p_color textCol
										p_font fontNum
									)
									(Display 706 144
										p_at 73 54
										p_color textCol
										p_font fontNum
									)
									(Display 706 145 ;cave troll
										p_at 73 64
										p_color textCol
										p_font fontNum
									)
									(Display 706 146 ;lolotte
										p_at 73 74
										p_color textCol
										p_font fontNum
									)
;;;									(Display 706 147
;;;										p_at 73 84
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 148
;;;										p_at 73 94
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 149
;;;										p_at 73 104
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 150
;;;										p_at 73 114
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 151
;;;										p_at 155 24
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 152
;;;										p_at 155 34
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 153
;;;										p_at 155 44
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 154
;;;										p_at 155 54
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 155
;;;										p_at 155 64
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 156
;;;										p_at 155 74
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
;;;									(Display 706 157
;;;										p_at 155 84
;;;										p_color textCol
;;;										p_font fontNum
;;;									)
									(= local147 1)
									(= local152 1)
									(fileCursor view: 591 loop: 1 posn: 71 32 init:)
								)
								(3
									(Display 706 158
										p_at 73 24
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 159
										p_at 73 34
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 160
										p_at 73 44
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 161
										p_at 73 54
										p_color textCol
										p_font fontNum
										p_back 0
									)
								)
								(4
									(Display 706 162
										p_at 73 24
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 163
										p_at 73 34
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 164
										p_at 73 44
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 165
										p_at 73 54
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 166
										p_at 73 64
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 167
										p_at 73 74
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 168
										p_at 73 84
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 169
										p_at 158 24
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 170
										p_at 158 34
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 171
										p_at 158 44
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 172
										p_at 158 54
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 173
										p_at 158 64
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 174
										p_at 158 74
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(= local147 1)
									(= local152 1)
									(fileCursor view: 591 loop: 1 posn: 71 32 init:)
								)
								(7
									(Display 706 175
										p_at 73 24
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 176
										p_at 73 34
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 177
										p_at 73 44
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 178
										p_at 73 54
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 179
										p_at 73 64
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 180
										p_at 73 74
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 181
										p_at 73 84
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 182
										p_at 73 94
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 183
										p_at 73 104
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 184
										p_at 154 24
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 185
										p_at 154 34
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 186
										p_at 154 44
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 187
										p_at 154 54
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 188
										p_at 154 64
										p_color textCol
										p_font fontNum
										p_back 0
									)
									(Display 706 189
										p_at 154 74
										p_color textCol
										p_font fontNum
										p_back 0
									)
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
							(Display 706 14
								p_at 73 14
								p_color 0
								p_font fontNum
								p_back 0
							)
							(Display 706 190
								p_at 73 14
								p_color textCol
								p_font fontNum
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

