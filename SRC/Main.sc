;;; Sierra Script 1.0 - (do not remove this comment)
(script# 0)
(include game.sh) (include menu.sh)
(use Intrface)
(use Sound)
(use Save)
(use Motion)
(use Game)
(use Invent)
(use User)
(use Menu)
(use Actor)
(use System)

(public
	KQ4 0
	smallBase 1
	IsObjectOnControl 2
	FaceObject 3
	timer1 4
	timer2 5
	timer3 6
	NormalEgo 7
	HandsOff 8
	HandsOn 9
	NotifyScript 10
	SetEgoView 11
	IsHeapFree 12
	PrintNotCloseEnough 13
	PrintAlreadyTookIt 14
	PrintNothingSpecial 15
	PrintCantDoThat 16
	PrintDontHaveIt 17
	AnimateCast 18
	proc0_19 19
	LanternIsOn 20
	cls 21
)

(local
	ego
	theGame
	curRoom
	speed =  6
	quit
	cast
	regions
	timers
	sounds
	inventory
	addToPics
	curRoomNum
	prevRoomNum
	newRoomNum
	debugOn
	score
	possibleScore
	showStyle =  IRISOUT
	aniInterval
	theCursor
	normalCursor =  ARROW_CURSOR
	waitCursor =  HAND_CURSOR
	userFont =  USERFONT
	smallFont =  4
	lastEvent
	modelessDialog
	bigFont =  USERFONT
	volume =  12
	version =  {newInvItem}
	locales
	curSaveDir
		global31
		global32
		global33
		global34
		global35
		global36
		global37
		global38
		global39
		global40
		global41
		global42
		global43
		global44
		global45
		global46
		global47
		global48
		global49
	aniThreshold =  10
	perspective
	features
	sortedFeatures
	useSortedFeatures
	demoScripts
	egoBlindSpot
	overlays =  -1
	doMotionCue
	systemWindow
	demoDialogTime
	currentPalette
	;62-99 are unused
		global62
		global63
		global64
		global65
		global66
		global67
		global68
		global69
		global70
		global71
		global72
		global73
		global74
		global75
		global76
		global77
		global78
		global79
		global80
		global81
		global82
		global83
		global84
		global85
		global86
		global87
		global88
		global89
		global90
		global91
		global92
		global93
		global94
		global95
		global96
		global97
		global98
	lastSysGlobal
	isNightTime
	isIndoors
	dwarfHouseState
	global103
	global104
	currentStatus
	global106
	global107
	oldHorizon
	gamePhase
	frogPrinceState
	global111
	choppedScaryTree
	timesUsedShovel
	global114
	cleaningUpHouse
	fishermanState
	global117
	whereIsMinstrel
	minstrel
	timedMessage
	ogre
	dwarfBouncesEgo
	unicornState
	unicornRoom
	unicorn
	trollAttacks
	dead		;if TRUE, bring up death message
	shakespeareBookKnown
	lookedAtStrangeMansionPainting
	hiddenMansionLatchKnown
	mansionSecretDoorOpen
	laidDownBoard
	ghostRoomNum
	mansionPhase
	ghostWandering
	global136
	global137
	gNewPropX
	global139
	global140
	global141
	global142
	global143
	global144
	global145
	global146
	global147
	global148
	global149
	global150
	global151
	global152
	global153
	global154
	global155
	global156
	global157
	gameSeconds
	gameMinutes
	gameHours
	global161
	global162
	ogressIsHome
	oldEgoScript
	ogreState
	ogreCameHome
	enteredOgreKitchen
	global168
	lolotteAlive
	ropeLadderLowered
	global171
	pan
	ogreY
	cryptDoorState
	henchChasingEgo
	ateSoup
	dwarfTableClean
	global178
	global179
	gNewProp
	oldEgoView
	gotItem
	swallowedByWhale
	timesTalkedToMinstrel
	oldEgoBaseSetter
	numZombies
	global187
	nightRoom
	noWearCrown
	global190
	shotUnicorn
	global192
	playedOrgan
	global194
	logEntries
	writingLog
	minuteMetFisherman
	secondMetFisherman
	hourMetFisherman
	woreFrogCrown
	putBoardOverSwamp
	global202
	crow
	isHandsOff
	inCutscene
	talkedToFishermanWife
	witchesTossedScarab
	scarabX
	scarabY
	ogreFrontDoorOpen
	hourLastMetPan
	minutesLastMetPan
	hourLastMetMinstrel
	minutesLastMetMinstrel
	debugging
	global216
	blewWhistle
	gotPandorasBox
	global219
	unlockedLolotteDoor
	global221
	howFast
	detailLevel
	inCinematic		;this is set in the intro and ending cinematics
	ghostHaunts
	introScript
	lolotteDoorOpen
	scoredTooth
	
		;;TBoR mod globals
		wifeGraveDugUp
		rogerDead 
		bondsEntered
		wifeDead
		shouldknowwifedead
		sdead
		arrowed
		marieUntied
		sequenceBreakNight
		fairyFlip
		trollDead
		rodeDolphin
		ogressShot
		condomRotation
)
(procedure (IsObjectOnControl param1 param2)
	(if (< argc 2) (= param2 5))
	(switch (param1 loop?)
		(0
			(OnControl
				4
				(param1 x?)
				(param1 y?)
				(+ (param1 x?) param2)
				(+ (param1 y?) 1)
			)
			(return)
		)
		(1
			(OnControl
				4
				(- (param1 x?) param2)
				(param1 y?)
				(param1 x?)
				(+ (param1 y?) 1)
			)
			(return)
		)
		(2
			(OnControl
				4
				(param1 x?)
				(param1 y?)
				(+ (param1 x?) 1)
				(+ (param1 y?) param2)
			)
			(return)
		)
		(3
			(OnControl
				4
				(param1 x?)
				(- (param1 y?) param2)
				(+ (param1 x?) 1)
				(param1 y?)
			)
			(return)
		)
	)
)

(procedure (FaceObject who whom)
	(DirLoop
		who
		(GetAngle
			(who x?)
			(who y?)
			(whom x?)
			(whom y?)
		)
	)
	(if (== argc 3)
		(DirLoop
			whom
			(GetAngle
				(whom x?)
				(whom y?)
				(who x?)
				(who y?)
			)
		)
	)
)

(procedure (NormalEgo theLoop theView)
	(if (> argc 0)
		(ego loop: theLoop)
		(if (> argc 1) (ego view: theView))
	)
	(ego
		setLoop: -1
		setPri: -1
		setMotion: 0
		setCycle: Walk
		illegalBits: cWHITE
		cycleSpeed: 0
		moveSpeed: 0
		ignoreActors: 0
	)
	(User canControl: TRUE canInput: TRUE)
)

(procedure (HandsOff)
	(User canControl: FALSE canInput: FALSE)
	(ego setMotion: 0)
	(= isHandsOff TRUE)
	(= global114 noWearCrown)
	(= noWearCrown 1)
)

(procedure (HandsOn)
	(User canControl: TRUE canInput: TRUE)
	(ego setMotion: 0)
	(= isHandsOff FALSE)
	(= noWearCrown global114)
)

(procedure (NotifyScript script &tmp temp0)
	(= temp0 (ScriptID script))
	(temp0 notify: &rest)
)

(procedure (SetEgoView)
	(return (if (== (ego view?) 2) else (== (ego view?) 4)))
)

(procedure (IsHeapFree memSize)
	(return (> (MemoryInfo FreeHeap) memSize))
)

(procedure (PrintNotCloseEnough)
	(Print 0 130)
)

(procedure (PrintAlreadyTookIt)
	(Print 0 131)
)

(procedure (PrintNothingSpecial)
	(Print 0 132)
)

(procedure (PrintCantDoThat)
	(Print 0 133)
)

(procedure (PrintDontHaveIt)
	(Print 0 134)
)

(procedure (AnimateCast)
	(Animate (cast elements?) FALSE)
)

(procedure (proc0_19 param1 param2)
	(param1 loop: param2 changeState:)
)

(procedure (LanternIsOn newState &tmp theState)
	(= theState (liteState state?))
	(if argc (liteState changeState: newState))
	(return theState)
)

(procedure (cls)
	(if modelessDialog (modelessDialog dispose:))
)

(class newInvItem of InvItem
	
	(method (showSelf)
		(Print 0 0 #title name #icon view loop cel)
	)
)

(instance statusCode of Code
	(properties)
	
	(method (doit strg)
		(Format strg 0 1 score possibleScore
			{ KQ\n__The Breasts of Rosella}
		)
	)
)

(instance egoObj of Ego
	(properties
		name "ego"
	)
)

(instance getItemMusic of Sound
	(properties
		number 48
		priority 2
		owner -1
	)
)

(instance tweet of Sound
	(properties
		number 76
		priority -1
		owner -1
	)
)

(instance KQ4 of Game
	(properties)
	
	(method (init)
		(= systemWindow SysWindow)
		(super init:)
		(= ego egoObj)
		(User alterEgo: ego)
		(= aniThreshold 7)
		(= global221 0)
		(Inventory
			add:
				Silver_Flute
				Diamond_Pouch
				Talisman
				Lantern__unlit_
				Pandora_s_Box
				Gold_Ball
				Witches__Glass_Eye
				Obsidian_Scarab
				Peacock_Feather
				Lute
				Small_Crown
				Frog
				Silver_Baby_Rattle
				Gold_Coins
				Cupid_s_Bow
				Shovel
				Axe
				Fishing_Pole
				Shakespeare_Book
				Worm
				Skeleton_Key
				Golden_Bridle
				Board
				Bone
				Dead_Fish
				Magic_Fruit
				Sheet_Music
				Silver_Whistle
				Locket
				Medal
				Toy_Horse
				Glass_Bottle
				Gold_Key
				Magic_Hen
				Rose
				Note
				Tooth
				Virginity
				Decoder_Ring
				Briefcase
				Skull
				Condom
				Hairpin
		)

		;(ego get: iTooth)
		;(ego get: iBone)
		;(ego get: iSkull)
		;(ego get: iVirginity)
		;(ego get: iSmallCrown)
		;(ego get: iCupidBow)
		;(ego get: iAxe)
		;(ego get: iPandorasBox)
		;(= isNightTime 1)
		;(= gamePhase getTheUnicorn)
		;(= gamePhase getPandoraBox)
		
		
		(= condomRotation (Random 1 3))
		
		(= showStyle HSHUTTER)
		(= userFont (= bigFont USERFONT))
		(= lolotteAlive TRUE)
		(= version {1.666.003})
		(liteState init: Lantern__unlit_) 
		(TheMenuBar init:)
		(getItemMusic init:)
		(tweet init:)
		(= whereIsMinstrel (Random 1 3))
		(User canInput: FALSE canControl: FALSE echo: 32)
		(= inCutscene TRUE)
		(StatusLine code: statusCode)
		(= possibleScore 230)
		(= fishermanState fisherGoneFishing)
		(= global157 0)
		(= currentStatus egoNormal)
		(= gameHours 8)
		(ego view: 2 x: 100 y: 120)
		(if (GameIsRestarting)
			(TheMenuBar draw:)
			(StatusLine enable:)
			(self newRoom: 99)
			(= userFont bigFont)
		else
		(self newRoom: 701) ;Set to 700 to skip the CP -- Kawa
		;(self newRoom: 700)
		)
	)
	
	(method (doit)
		(cond 
			((and inCinematic (!= global221 2))
				(= global221 2)
				(self setCursor: 666 (HaveMouse))
			)
			((and (== global221 2) (not inCinematic))
				(self setCursor: normalCursor (HaveMouse))
				(= global221 0)
			)
			((and (== (User controls?) FALSE) (== global221 0))
				(= global221 1)
				(self setCursor: normalCursor TRUE)
			)
			((and (== global221 1) (== (User controls?) 1))
				(self setCursor: normalCursor (HaveMouse))
				(= global221 0)
			)
		)
		(if dead
			(sounds eachElementDo: #dispose)
			((Sound new:) number: 49 play:)
			(self setCursor: normalCursor (HaveMouse))
			(if (IsHeapFree DeathSize)
				(repeat
					(switch
						(Print 0 2
							#icon 588 0 0 ;#icon 100 0 0
							#mode teJustCenter
							#title {Roberta says:}
							#button {__Restore__} 1
							#button {__Restart__} 2
							#button {___Quit___} 3
						)
						(1
							(theGame restore:)
						)
						(2
							(theGame restart:)
						)
						(3
							(= quit TRUE)
							(break)
						)
					)
				)
			else
				(Print 0 3)
				(theGame restart:)
			)
		else
			(if gotItem
				(= gotItem FALSE)
				(getItemMusic play:)
			)
			(if (!= (= global156 (GetTime TRUE)) global157)
				(= global157 global156)
				(if (>= (+= gameSeconds 4) 60)
					(++ gameMinutes)
					(-= gameSeconds 60)
					(if (and (== gameHours 31) (== gameMinutes 59))
						;time's up at 8 a.m. of the second day
						(curRoom setScript: (ScriptID 302 0))
					)
					(if (and (== gameHours 20) (== (mod gameMinutes 15) 0))
						;night falls at 8 p.m.
						(Print 0 4)
					)
					(if (== gameMinutes 60)
						(++ gameHours)
						(= gameMinutes 0)
					)
				)
			)
		)
		(super doit:)
	)
	
	(method (replay)
		(= userFont bigFont)
		(TheMenuBar draw:)
		(StatusLine enable:)
		(SetMenu soundI p_text
			(if (DoSound SoundOn) {Turn Off} else {Turn On})
		)
		(super replay:)
	)
	
	(method (newRoom roomNum)
		(if
			(or
				isHandsOff
				(and (not inCutscene) (== (User canControl:) FALSE))
			)
			(return)
		)
		;is it time to turn to night?
		(if (and (== isNightTime FALSE) (== isIndoors 0))
			(if
				(and
					(not (if (< 30 roomNum) (< roomNum 77)))
					(< roomNum 300)
					(or
						(== sequenceBreakNight 1) ;;;mod sequence breaks need to make it night
						(and (< 20 gameHours) (< gameHours 30))
						(and
							(>= gamePhase getPandoraBox)
							(ego has: iObsidianScarab)
							(ego has: iMagicFruit)
							(< gameHours 30)
						)
						
					)
				)
				(= isNightTime TRUE)
				(= nightRoom roomNum)
				(if (< gameHours 21)
					(= gameHours 21)
					(= gameMinutes 0)
				)
				(= roomNum 697)
			)
		)
		(super newRoom: roomNum)
	)
	
	(method (startRoom roomNum &tmp region)
		(if (and global216 (IsHeapFree 1200))
			(= global216 0)
			((= global202 (ScriptID 801)) init:)
		)
		(DisposeScript AVOIDER)
		(if debugOn (= debugOn FALSE) (SetDebug))
		(if
			(= region
				(switch roomNum
					(68 HAUNTED_HOUSE)
					(120 INTRO)
					(54 DWARF_HOUSE)
					(49 OGRE_HOUSE)
					(92 LOLOTTE)
					(71 TROLL_CAVE)
					(73 TROLL_CAVE)
					(55 DWARF_MINE)
					(else  0)
				)
			)
			((ScriptID region) init:)
		)
		(super startRoom: roomNum)
	)
	
		(method (handleEvent event &tmp temp0 index obj evt [str 50] [buf 50])
		;EO: This method has been successfully decompiled!
		(if
			(and
				debugging
				(not (event claimed?))
				(== (event type?) mouseDown)
			)
			(cond 
				((& (event modifiers?) shiftDown)
					(event claimed: TRUE)
					(= obj
						(Print
							(Format @buf 0 10 (event x?) (event y?))
							#at 150 100
							#font 999
							#dispose
						)
					)
					(while (!= mouseUp ((= evt (Event new:)) type?))
						(evt dispose:)
					)
					(obj dispose:)
					(evt dispose:)
				)
				((& (event modifiers?) ctrlDown)
					(event claimed: TRUE)
					(while (!= mouseUp ((= evt (Event new:)) type?))
						((User alterEgo?)
							posn: (evt x?) (- (evt y?) 10)
							setMotion: 0
						)
						(Animate (cast elements?) FALSE)
						(evt dispose:)
					)
					(evt dispose:)
				)
				((& (event modifiers?) altDown)
					(event claimed: TRUE)
					((User alterEgo?) showSelf:)
				)
			)
			(if (event claimed?)
				(return TRUE)
			)
		)
		(if (== curRoomNum newRoomNum)
			(super handleEvent: event)
		)
		(return
			(if (== (event type?) saidEvent)
				(if (Said 'get/mem')
					(theGame showMem:)
				)
				(if debugging
					(cond 
						((Said 'enter/night')
							(= ogressIsHome TRUE)
							(= gamePhase getPandoraBox)
							(= mansionSecretDoorOpen TRUE)
							(ego get: iLantern)
							(ego get: iAxe)
							(ego get: iShovel)
							(ego get: iMagicFruit)
							(ego get: iCupidBow)
							(= isNightTime TRUE)
							((Inventory at: iCupidBow) loop: 1 cel: 0)
							(= lolotteAlive TRUE)
							(= gameHours 21)
							(= gameMinutes 1)
							(= frogPrinceState 5)
							(= whereIsMinstrel -1)
							(= unicornState uniCAPTURED)
							(= unicornRoom 99)
							(Print 0 11)
						)
						((Said 'tp')
							(= str 0)
							(Print {TP to:}
								#at -1 20
								#edit @str 6
							)
							(= newRoomNum (ReadNumber @str))
						)
					)
				)
				;start saidEvents
				(cond 
					((or (Said 'ass') (Said '/ass') (Said '//ass'))
						(Print 0 12)
					)
					((Said 'blow/whistle')
						(if (ego has: iSilverWhistle)
							(tweet play:)
							(Print 0 13)
						else
							(PrintDontHaveIt)
						)
					)
					((or (Said 'open,(look<in)/bottle') (Said 'get/letter'))
						(cond 
							((ego has: iNote)
								(Print 0 14)
							)
							((ego has: iGlassBottle)
								(Print 0 15)
								((Inventory at: iGlassBottle) cel: 0)
								(= gotItem TRUE)
								(ego get: iNote)
							)
							(else
								(PrintDontHaveIt)
							)
						)
					)
					((Said 'drink/bottle')
						(if (ego has: iGlassBottle)
							(Print 0 16)
						else
							(PrintDontHaveIt)
						)
					)
					(
						(or
							(Said 'break,hit,chop/branch,forest,arm,branch')
							(Said 'swing,wave/ax')
						)
						(if (ego has: iAxe)
							(Print 0 17)
						else
							(PrintCantDoThat)
						)
					)
					((and (ego has: iAxe) (Said 'chop,hit'))
						(Print 0 18)
					)
					(
						(or
							(Said 'detach,(get<off)/dress')
							(Said 'undress')
							(Said 'get/undressed')
						)
						(Print 0 19)
					)
					((Said 'play,bounce/ball')
						(if (ego has: iGoldBall)
							(Print 0 20)
						else
							(PrintDontHaveIt)
						)
					)
					((Said 'dig')
						(if (ego has: iShovel)
							(Print 0 17)
						else
							(Print 0 21)
						)
					)
					((Said 'launch')
						(cond 
							((not (ego has: iCupidBow))
								(Print 0 22)
							)
							((>= ((Inventory at: iCupidBow) loop?) 2)
								(Print 0 23)
							)
							((and (not noWearCrown)
									(SetEgoView)
									(IsHeapFree BowSize)
								)
								(= oldEgoScript (ego script?))
								(ego setScript: (ScriptID 305 0))
							)
							(else
								(PrintCantDoThat)
							)
						)
					)
					((Said 'polish>')
						(cond 
							((not (= index (inventory saidMe:)))
								(if (Said '/me')	;EO: fixed said spec
									(Print 0 24)
								else
									(Print 0 25)
								)
							)
							((not (ego has: (inventory indexOf: index)))
								(PrintDontHaveIt)
							)
							(else
								(switch (inventory indexOf: index)
									(iTalisman
										(Print 0 26)
									)
									(iLantern
										(Print 0 27)
									)
									(iWitchGlassEye
										(Print 0 28)
									)
									(iObsidianScarab
										(Print 0 29)
									)
									(else
										(Print 0 30)
									)
								)
							)
						)
						(event claimed: TRUE)
					)
					((Said 'kiss>')
						(= index (inventory saidMe:))
						(event claimed: TRUE)
						(cond 
							((not index)
								(Print 0 31)
							)
							((not (ego has: (inventory indexOf: index)))
								(Print 0 32)
							)
							((== (inventory indexOf: index) 2)
								(Print 0 33)
							)
							(else
								(Print 0 31)
							)
						)
					)
					((Said 'wish>')
						(cond 
							((not (= index (inventory saidMe:)))
								(Print 0 34)
							)
							((not (ego has: (inventory indexOf: index)))
								(PrintCantDoThat)
							)
							((== (inventory indexOf: index) iTalisman)
								(Print 0 35)
							)
							(else
								(Print 0 36)
							)
						)
						(event claimed: TRUE)
					)
					(
						(or
							(Said 'bait/hook,pole')
							(Said 'put/earthworm/hook,pole')
						)
						(if (and (ego has: iWorm) (ego has: iFishingPole))
							(Print 0 37)
							((Inventory at: iWorm) moveTo: 666)
							((Inventory at: iFishingPole) loop: 1)
							(theGame changeScore: 1)
						else
							(Print 0 38)
						)
					)
					(else
						(if (and (ego has: iDeadFish) (Said '/fish>'))
							(cond
								((Said 'smell')
									(Print 0 39)
								)
								((Said 'eat')
									(Print 0 40)
								)
							)
						)
						(cond 
							((Said 'eat/earthworm')
								(if (ego has: iWorm)
									(Print 0 41)
								else
									(PrintDontHaveIt)
								)
							)
							((Said 'eat/bone')	;EO: fixed said spec
								(if (ego has: iBone)
									(Print 0 42)
								else
									(PrintDontHaveIt)
								)
							)
							((Said 'eat/fruit')
								(if (ego has: iMagicFruit)
									(Print 0 43)
									(ego put: iMagicFruit 999)
									(theGame changeScore: -10)
								else
									(PrintDontHaveIt)
								)
							)
							((and (ego has: iLocket) (Said 'wear,(put<on)/locket'))
								(Print 0 44)
							)
							((and (ego has: iMedal) (Said 'wear,(put<on)/badge'))
								(Print 0 45)
							)
							((Said 'wear,(put<on)/amulet')
								(if (ego has: iTalisman)
									(Print 0 46)
								else
									(PrintDontHaveIt)
								)
							)
							((Said 'wear,(put<on)/crown')
								(cond 
									((not (ego has: iSmallCrown))
										(PrintDontHaveIt)
									)
									(noWearCrown
										(Print 0 47)
									)
									((and (IsHeapFree CrownSize) (SetEgoView))
										(= oldEgoScript (ego script?))
										(ego setScript: (ScriptID 301 0))
									)
									(else
										(PrintCantDoThat)
									)
								)
							)
							((Said 'read/letter')
								(cond 
									((not (ego has: iNote))
										(PrintDontHaveIt)
									)
									((IsHeapFree ReadSize)
										((ScriptID 306 1) cue:)
									)
									(else
										(PrintCantDoThat)
									)
								)
							)
							((Said 'play/flute')
								(cond 
									((not (ego has: iSilverFlute))
										(PrintDontHaveIt)
									)
									(noWearCrown
										(Print 0 48)
									)
									((and (SetEgoView) (IsHeapFree InstrumentSize))
										(= oldEgoScript (ego script?))
										(ego setScript: (ScriptID 304 0))
									)
									(else
										(PrintCantDoThat)
									)
								)
							)
							((Said 'play/lute')
								(cond 
									((not (ego has: iLute)) (PrintDontHaveIt))
									(noWearCrown (PrintCantDoThat))
									((and (SetEgoView) (IsHeapFree InstrumentSize))
										(= oldEgoScript (ego script?))
										(ego setScript: (ScriptID 303 0))
									)
									(else
										(PrintCantDoThat)
									)
								)
							)
							((Said 'play,shake/rattle')
								(if (ego has: iSilverBabyRattle)
									(Print 0 49)
								else
									(PrintDontHaveIt)
								)
							)
							((Said 'unlatch/door')
								(if (or (ego has: iSkeletonKey) (ego has: iGoldKey))
									(Print 0 50)
								else
									(Print 0 51)
								)
							)
							(else
								(if (Said '/music>')
									(if (Said 'read,open')
										(if (ego has: iSheetMusic)
											(Print 0 52)
										)
										(PrintDontHaveIt)
									)
									(if (Said 'play')
										(Print 0 53)
									)
								)
								(if (and (ego has: iToyHorse) (Said 'play/horse'))
									(Print 0 54)
									(Print 0 55)
								else
									(if (Said '/book>')
										(if (Said 'close')
											(if (not (ego has: iShakespeareBook))
												(Print 0 56)
											)
											(if global154
												(Print 0 46)
												(= global154 FALSE)
											)
											(Print 0 57)
										)
										(if (Said 'read')
											(cond 
												((not (ego has: iShakespeareBook))
													(Print 0 58)
												)
												((IsHeapFree ReadSize)
													((ScriptID 306 0) changeState: (Random 1 30))
												)
												(else
													(PrintCantDoThat)
												)
											)
										)
									)
									(cond 
										((Said 'smell/rose')
											(if (ego has: iRose)
												(Print 0 59)
											else
												(PrintDontHaveIt)
											)
										)
										((Said 'get,detach/thorn')
											(if (ego has: iRose)
												(Print 0 60)
											else
												(PrintDontHaveIt)
											)
										)
										(
											(or
												(Said 'lay[/noword]')
												(Said 'lay,get,rob/egg')
												(Said 'command/chicken')
											)
											(if (ego has: iMagicHen)
												(Print 0 61)
											else
												(PrintDontHaveIt)
											)
										)
										((Said 'converse/chicken')
											(if (ego has: iMagicHen)
												(Print 0 62
													#title {Magic Hen}
													#icon 431 0 0
												)
											else
												(PrintDontHaveIt)
											)
										)
										((Said 'eat/chicken')
											(if (ego has: iMagicHen)
												(Print 0 63)
											else
												(PrintDontHaveIt)
											)
										)
										((Said 'eat')
											(Print 0 64)
										)
										(else
											(if (Said '/chandelier,lantern[<oil]>')
												(if (Said 'light,ignite,(turn<on)')	;EO: fixed said spec
													(cond 
														((not (ego has: iLantern))
															(Print 0 65)
														)
														((LanternIsOn)
															(Print 0 66)
														)
														((SetEgoView)
															(LanternIsOn TRUE)
														)
														(else
															(Print 0 67)
														)
													)
													1
												)
												(if (Said 'extinguish,(turn<off)')
													(if (ego has: iLantern)
														(if (LanternIsOn)
															(LanternIsOn FALSE)
														else
															(Print 0 68)
														)
													)
												)
											)
											(if (and (ego has: iWitchGlassEye) (Said '/eye>'))
												(if (Said 'look<through,in')
													(Print 0 69) 
												)
												(if (Said 'break')
													(Print 0 70)
												)
											)
											(cond 
												((and (ego has: iObsidianScarab) (Said 'wear,put/charm'))
													(Print 0 71)
												)
												((Said 'hop')
													(Print 0 34)
												)
												((Said 'kill')
													(Print 0 72)
												)
												((Said 'rob')
													(Print 0 73)
												)
												((Said 'hit')
													(Print 0 74)
												)
												((Said 'climb')
													(Print 0 75)
												)
												((Said 'laugh')
													(Print 0 76)
												)
												((Said 'throw')
													(Print 0 16)
												)
												((Said 'converse')
													(switch (Random 1 2)
														(1 (Print 0 77))
														(2 (Print 0 78))
													)
												)
												((Said 'close/door')
													(Print 0 79)
												)
												((Said 'listen')
													(Print 0 80)
												)
												((Said 'sit')
													(Print 0 81)
												)
												((Said 'smell')
													(Print 0 82)
												)
												((Said 'open,(look<in)>')
													(= index (inventory saidMe:))
													(cond 
														((Said '[/noword]')
															(Print 0 83)
														)
														((not index)
															(event claimed: TRUE)
															(PrintCantDoThat)
														)
														((not (ego has: (inventory indexOf: index)))
															(PrintDontHaveIt)
														)
														(else
															(switch (inventory indexOf: index)
																(iDiamondPouch
																	(Print 0 84
																		#icon 401 0 0
																	)
																)
																(iLantern
																	(Print 0 85)
																)
																(iSilverBabyRattle
																	(Print 0 86)
																)
																(iGoldCoins
																	(Print 0 87)
																)
																(iLocket
																	(Print 0 88)
																)
																(iPandorasBox
																	(if
																		(or
																			(and (!= (ego view?) 2) (!= (ego view?) 4))
																			(< (MemoryInfo FreeHeap) PandoraSize)
																			noWearCrown
																		)
																		(Print 0 89)
																	else
																		(ego loop: 0)
																		(if (& (= global168 (IsObjectOnControl ego 30)) $8000)
																			(Print 0 90)
																		else
																			(ego setScript: (ScriptID 307 0))
																		)
																	)
																)
																(iShakespeareBook
																	(Print 0 91)
																	(= global154 TRUE)
																)
																(else
																	(Print 0 92)
																)
															)
														)
													)
													(event claimed: TRUE)
												)
												((Said 'look>')
													(cond 
														((Said '/me')	;EO: fixed said spec
															(Print 0 93)
														)
														((Said '/letter')
															(cond 
																((ego has: iNote)
																	(Print 0 94
																		#icon 435 0 0
																	)
																)
																((ego has: iGlassBottle)
																	(Print 0 95)
																)
																(else
																	(PrintDontHaveIt)
																)
															)
														)
														((Said '/key')
															(event claimed: FALSE)
															(cond 
																((Said '/anyword<gold')
																	(if (ego has: iGoldKey)
																		((Inventory at: iGoldKey) showSelf:)
																	else
																		(Print 0 96)
																	)
																)
																((Said '/anyword<skeleton')
																	(if (ego has: iSkeletonKey)
																		((Inventory at: iSkeletonKey) showSelf:)
																	else
																		(Print 0 96)
																	)
																)
																((Said '/anyword[<noword]')
																	(cond 
																		((and (ego has: iSkeletonKey) (ego has: iGoldKey))
																			(Print 0 97)
																		)
																		((ego has: iSkeletonKey)
																			((Inventory at: iSkeletonKey) showSelf:)
																		)
																		((ego has: iGoldKey)
																			((Inventory at: iGoldKey) showSelf:)
																		)
																		(else
																			(Print 0 98)
																		)
																	)
																)
															)
														)
														((Said '/moon,moon')
															(cond 
																(isIndoors
																	(Print 0 99)
																)
																(isNightTime
																	(Print 0 100)
																)
																(else
																	(Print 0 100)
																)
															)
														)
														((Said '/cloud')
															(cond 
																(isIndoors
																	(Print 0 101)
																)
																(isNightTime
																	(Print 0 102)
																)
																(else
																	(Print 0 103)
																)
															)
														)
														((Said '/wall')
															(Print 0 104)
														)
														((Said '<in/bottle')
															(cond 
																((not (ego has: iGlassBottle))
																	(PrintCantDoThat)
																)
																((ego has: iNote)
																	(Print 0 105)
																)
																(else
																	(Print 0 106)
																)
															)
														)
														((or (Said '/dirt,dirt') (Said '<down'))
															(Print 0 107)
														)
														((or (Said '<up') (Said '/sky'))
															(cond 
																(isIndoors
																	(Print 0 108)
																)
																(isNightTime
																	(Print 0 109)
																)
																(else
																	(Print 0 110)
																)
															)
														)
														(
															(and
																(ego has: iWorm)
																(== ((Inventory at: iWorm) loop?) 1)
																(Said '/earthworm')
															)
															(Print 0 111)
														)
														((= index (inventory saidMe:))
															(if (ego has: (inventory indexOf: index))
																(index showSelf:)
															else
																(PrintDontHaveIt)
															)
														)
														(
															(or
																(Said '/troll,bard,dwarf,pan,giant,goon,person,man')
																(Said '/hag,woman,fairies,genesta,lolotte,giantess,woman')
																(Said '/unicorn,bird,bulldog,fisherman,whale')
															)
															(Print 0 112)
														)
														(else
															(Print 800 4)
															(event claimed: TRUE)
														)
													)
												)
												((Said 'use>')
													(cond 
														((not (= index (inventory saidMe:)))
															(Print 0 113)
														)
														((not (ego has: (inventory indexOf: index)))
															(PrintDontHaveIt)
														)
														(else
															(Printf 0 114 (index name?))
															;(Print (Format @str 0 114 (index name?)))
														)
													)
													(event claimed: TRUE)
												)
												((Said 'deliver>')
													(cond 
														((Said '/anyword[/noword]')
															(Print 0 115)
														)
														((Said '[/noword]')
															(Print 0 116)
														)
														((= index (inventory saidMe:))
															(if (not (ego has: (inventory indexOf: index)))
																(PrintDontHaveIt)
															else
																(Print 0 117)
															)
														)
														(else
															(Print 0 118)
															(event claimed: TRUE)
														)
													)
												)
												((Said 'get>')
													(cond 
														((Said '/water')
															(Print 0 119)
														)
														((Said '[/noword]')
															(Print 0 120)
														)
														((not (= index (inventory saidMe:)))
															(event claimed: TRUE)
															(Print 0 121)
														)
														((ego has: (inventory indexOf: index))
															(switch (inventory indexOf: index)
																(iDiamondPouch
																	(PrintAlreadyTookIt)
																)
																(iGoldCoins
																	(Print 0 122)
																)
																(else
																	(Print 800 0)
																)
															)
														)
														(else
															(switch (inventory indexOf: index)
																(iLantern
																	(Print 0 123)
																)
																(else
																	(Print 0 124)
																)
															)
														)
													)
												)
												(
													(and
														(Said 'show>')
														(= index (inventory saidMe:))
													)
													(if (ego has: (inventory indexOf: index))
														(Print 0 125)
													else
														(PrintDontHaveIt)
													)
												)
												((Said 'overtime/nosleep')
													(= debugging TRUE)
													(= global216 1)
													(PrintNothingSpecial)
												)
												((= index (inventory saidMe:))
													(if (not (ego has: (inventory indexOf: index)))
														(PrintDontHaveIt)
													)
													(PrintCantDoThat)
												)
											)
										)
									)
								)
							)
						)
					)
				)
			else
				FALSE
			)
		)
	)
	
	(method (wordFail word)
		;(Print (Format @str 0 5 word))
		(Printf 0 5 word)
	)
	
	(method (syntaxFail)
		(Print 0 6)
	)
	
	(method (pragmaFail)
		(switch (Random 1 3)
			(1 (Print 0 7))
			(2 (Print 0 8))
			(3 (Print 0 9))
		)
	)
)

(instance Silver_Flute of newInvItem
	(properties
		said '/flute'
		owner 201
		view 413
		name "Silver Flute"
	)
)

(instance Diamond_Pouch of newInvItem
	(properties
		said '/(pouch[<diamond]),diamond'
		view 401
		name "Diamond Pouch"
	)
)

(instance Talisman of newInvItem
	(properties
		said '/amulet,amulet'
		owner 82
		view 411
	)
)

(instance Lantern__unlit_ of newInvItem
	(properties
		said '/lantern,chandelier'
		owner 56
		view 428
		name "Lantern (unlit)"
	)
)

(instance Pandora_s_Box of newInvItem
	(properties
		said '/box'
		owner 69
		view 425
		name "Pandora's Box"
	)
)



(instance Gold_Ball of newInvItem
	(properties
		said '/ball'
		owner 21
		view 400
		name "Gold Ball"
	)
)


(instance Witches__Glass_Eye of newInvItem
	(properties
		said '/eye'
		owner 57
		view 423
		name "Witches' Glass Eye"
	)
)

(instance Obsidian_Scarab of newInvItem
	(properties
		said '/charm'
		owner 57
		view 403
		name "Obsidian Scarab"
	)
)

(instance Peacock_Feather of newInvItem
	(properties
		said '/feather'
		owner 888
		view 429
		name "Peacock Feather"
	)
)

(instance Lute of newInvItem
	(properties
		said '/lute'
		owner 203
		view 414
	)
)

(instance Small_Crown of newInvItem
	(properties
		said '/crown'
		owner 200
		view 402
		name "Small Crown"
	)
)

(instance Frog of newInvItem
	(properties
		said '/frog'
		owner 15
		view 432
	)
)

(instance Silver_Baby_Rattle of newInvItem
	(properties
		said '/rattle'
		owner 88
		view 405
		name "Silver Baby Rattle"
	)
)

(instance Gold_Coins of newInvItem
	(properties
		said '/gold,(bag[<gold]),(bag[<gold,bag])'
		owner 88
		view 406
		name "Gold Coins"
	)
)

(instance Cupid_s_Bow of newInvItem
	(properties
		said '/arrow[<cupid]'
		owner 202
		view 415
		name "Cupid's Bow"
	)
)

(instance Shovel of newInvItem
	(properties
		said '/shovel'
		owner 66
		view 420
	)
	
	(method (showSelf)
		(Print 0 0
			#title (if (self loop?) {Broken Shovel} else {Shovel})
			#icon view loop cel
		)
	)
	
	(method (ownedBy state)
		(if loop
			(= name {Broken Shovel})
		else
			(= name {Shovel})
		)
		(super ownedBy: state)
	)
)

(instance Axe of newInvItem
	(properties
		said '/ax'
		owner 48
		view 418
	)
)

(instance Fishing_Pole of newInvItem
	(properties
		said '/pole[<fish]'
		owner 204
		view 421
		name "Fishing Pole"
	)
	
	(method (showSelf)
		(Print 0 0
			#title (if (self loop?) {Baited Fishing Pole} else {Fishing Pole})
			#icon view loop cel
		)
	)
	
	(method (ownedBy state)
		(if loop
			(= name {Baited Fishing Pole})
		else
			(= name {Fishing Pole})
		)
		(super ownedBy: state)
	)
)

(instance Shakespeare_Book of newInvItem
	(properties
		said '/book[<shakespeare]'
		owner 67
		view 416
		name "Shakespeare Book"
	)
)

(instance Worm of newInvItem
	(properties
		said '/earthworm'
		owner 206
		view 433
	)
)

(instance Skeleton_Key of newInvItem
	(properties
		said '/key<skeleton'
		owner 58
		view 424
		name "Skeleton Key"
	)
)

(instance Golden_Bridle of newInvItem
	(properties
		said '/bit'
		owner 43
		view 426
		name "Golden Bridle"
	)
)

(instance Tooth of newInvItem
	(properties
		said '/tooth,toof'
		owner 3
		view 1
		name "Tooth"
	)
)

(instance Condom of newInvItem
	(properties
		said '/condom'
		owner 62
		view 1
		cel 1
		name "Condom"
	)
)


(instance Board of newInvItem
	(properties
		said '/board'
		owner 70
		view 430
	)
)

(instance Bone of newInvItem
	(properties
		said '/bone'
		owner 71
		view 419
	)
)

(instance Dead_Fish of newInvItem
	(properties
		said '/fish[<dead]'
		owner 95
		view 422
		name "Dead Fish"
	)
)

(instance Magic_Fruit of newInvItem
	(properties
		said '/fruit'
		owner 78
		view 412
		name "Magic Fruit"
	)
)

(instance Sheet_Music of newInvItem
	(properties
		said '/music'
		owner 63
		view 417
		name "Sheet Music"
	)
)

(instance Silver_Whistle of newInvItem
	(properties
		said '/whistle'
		owner 207
		view 427
		name "Silver Whistle"
	)
)

(instance Locket of newInvItem
	(properties
		said '/locket'
		owner 88
		view 404
	)
)

(instance Medal of newInvItem
	(properties
		said '/badge'
		owner 88
		view 407
	)
)

(instance Toy_Horse of newInvItem
	(properties
		said '/toy,(horse[<toy])'
		owner 88
		view 408
		name "Toy Horse"
	)
)

(instance Glass_Bottle of newInvItem
	(properties
		said '/bottle'
		owner 44
		view 434
		cel 1
		name "Glass Bottle"
	)
)

(instance Gold_Key of newInvItem
	(properties
		said '/key<gold'
		owner 83
		view 410
		name "Gold Key"
	)
)

(instance Virginity of newInvItem
	(properties
		said '/virginity'
		owner 45
		view 571
		name "Larry's Virginity"
	)
)

(instance Briefcase of newInvItem
	(properties
		said '/briefcase'
		owner 97
		view 574
		name "Briefcase full of cash"
	)
)

(instance Skull of newInvItem
	(properties
		said '/skull'
		owner 8
		view 575
		name "Wife's Skull"
	)
)

(instance Hairpin of newInvItem
	(properties
		said '/hairpin'
		owner 97
		view 575
		name "Hairpin"
	)
)

(instance Decoder_Ring of newInvItem
	(properties
		said '/decoder'
		owner 19
		view 573
		name "MB Decoder Ring"
	)
)

(instance Magic_Hen of newInvItem
	(properties
		said '/chicken'
		owner 48
		view 431
		name "Magic Hen"
	)
)

(instance Rose of newInvItem
	(properties
		said '/rose'
		description {What a beautiful red rose! What's this?! Why, there is a little gold key attached to this rose!}
		owner 85
		view 409
	)
	
	(method (showSelf)
		(if loop
			(Print 0 126 #icon view loop cel)
		else
			(Print 0 127 #icon view loop cel)
		)
	)
)

(instance Note of newInvItem
	(properties
		said '/letter'
		owner 999
		view 435
	)
)

(instance liteState of Script
	(properties
		state $0000
	)
	
	(method (init theClient)
		(= client theClient)
		(client script: self)
	)
	
	(method (changeState newState)
		(if (!= state newState)
			(switch (= state newState)
				(FALSE
					(Print 0 128)
					(client
						description: {The lantern is off.\nThe oil lantern looks well-used.}
						name: {Lantern (unlit)}
						loop: 0
					)
				)
				(TRUE
					(Print 0 129)
					(client
						description: {The lantern is on.\nThe oil lantern looks well-used.}
						name: {Lantern (lit)}
						loop: 1
					)
				)
			)
		)
	)
)

(instance smallBase of Code
	(properties)
	
	(method (doit param1)
		(param1 brTop: (- (param1 y?) (param1 yStep?)))
		(param1 brLeft: (- (param1 x?) (/ (param1 xStep?) 2)))
		(param1 brBottom: (param1 y?))
		(param1
			brRight: (+ (param1 x?) (/ (param1 xStep?) 2))
		)
	)
)

(instance timer1 of Timer
	(properties)
)

(instance timer2 of Timer
	(properties)
)

(instance timer3 of Timer
	(properties)
)
