;;; Sierra Script 1.0 - (do not remove this comment)
(script# 994)
(include sci.sh)
(use Main)
(use Intrface)
(use Sound)
(use Save)
(use Motion)
(use Invent)
(use User)
(use System)


(procedure (localproc_0b98 param1 &tmp temp0 [temp1 40] [temp41 40] [temp81 40])
	(= temp0 1)
	(DeviceInfo 0 curSaveDir @temp1)
	(DeviceInfo 1 @temp41)
	(if
		(and
			(DeviceInfo 2 @temp1 @temp41)
			(DeviceInfo 3 @temp41)
		)
		(Format
			@temp81
			994
			6
			(if param1 {SAVE GAME} else {GAME})
			@temp41
		)
		(if
			(==
				(= temp0
					(if param1
						(Print
							@temp81
							#font
							0
							#button
							{OK}
							1
							#button
							{Cancel}
							0
							#button
							{Change Directory}
							2
						)
					else
						(Print @temp81 #font 0 #button {OK} 1)
					)
				)
				2
			)
			(= temp0 (GetDirectory curSaveDir))
		)
	)
	(return temp0)
)

;;;; GAME OBJECTS
;;;; These are static objects which are used in the generalized game.  Game
;;;; specific objects will be defined in module 0.

(instance theCast of EventHandler
	(properties
		name "cast"
	)
)
(instance theFeatures of EventHandler
	(properties
		name "features"
	)
)
(instance theSortedFeatures of EventHandler
	(properties
		name "sFeatures"
	)
)
(instance theSounds of EventHandler
	(properties
		name "sounds"
	)
)
(instance theRegions of EventHandler
	(properties
		name "regions"
	)
)
(instance theLocales of EventHandler
	(properties
		name "locales"
	)
)
(instance theAddToPics of EventHandler
	(properties
		name "addToPics"
	)
	;; Call kernel to draw the current list of PicViews
	;; They will not be seen until the next Animate call
	(method (doit)
		(AddToPic elements)
	)
)
(instance roomControls of Controls
	(properties
		name "controls"
	)
)
(instance theTimers of Set
	(properties
		name "timers"
	)
)

(class Game of Object
	(properties
		script 0
	)
	
	(method (init &tmp theMotion)
		(= theMotion Motion)
		(= theMotion Sound)
		(= theMotion Save)
		((= cast theCast) add:)
		((= features theFeatures) add:)
		((= sortedFeatures theSortedFeatures) add:)
		((= sounds theSounds) add:)
		((= regions theRegions) add:)
		((= locales theLocales) add:)
		((= addToPics theAddToPics) add:)
		((= timers theTimers) add:)
		(= curSaveDir (GetSaveDir))
		(Inventory init:)
		(User init:)
	)
	
	(method (doit)
		(sounds eachElementDo: #check)
		(timers eachElementDo: #doit)
		(Animate (cast elements?) 1)
		(if doMotionCue
			(= doMotionCue 0)
			(cast eachElementDo: #motionCue)
		)
		(if script (script doit:))
		(regions eachElementDo: #doit)
		(User doit:)
		(if (!= newRoomNum curRoomNum)
			(self newRoom: newRoomNum)
		)
		(timers eachElementDo: #delete)
		(GameIsRestarting 0)
	)
	
	(method (showSelf)
		(regions showSelf:)
	)
	
	(method (play)
		(= theGame self)
		(= curSaveDir (GetSaveDir))
		(if (not (GameIsRestarting)) (GetCWD curSaveDir))
		(self setCursor: waitCursor 1)
		(self init:)
		(self setCursor: normalCursor (HaveMouse))
		(while (not quit)
			(self doit:)
			(= aniInterval (Wait speed))
		)
	)
	
	(method (replay)
		(if lastEvent (lastEvent dispose:))
		(sortedFeatures release:)
		(if modelessDialog (modelessDialog dispose:))
		(cast eachElementDo: #perform RU)
		(theGame setCursor: waitCursor 1)
		(DrawPic (curRoom curPic?) dpOPEN_TOP TRUE 0)
		(if (!= overlays -1)
			(DrawPic overlays dpOPEN_TOP FALSE 0)
		)
		(if (curRoom controls?) ((curRoom controls?) draw:))
		(addToPics doit:)
		(theGame setCursor: normalCursor (HaveMouse))
		(StatusLine doit:)
		(DoSound sndRESUME)
		(Sound pause: 0)
		(while (not quit)
			(self doit:)
			(= aniInterval (Wait speed))
		)
	)
	
	(method (newRoom newRoomNumber &tmp [temp0 4] temp4 temp5)
		(addToPics dispose:)
		(features eachElementDo: #dispose)
		(cast eachElementDo: #dispose)
		(cast eachElementDo: #delete)
		(timers eachElementDo: #delete)
		(regions eachElementDo: #perform DNKR)
		(regions release:)
		(locales eachElementDo: #dispose)
		(Animate 0)
		(= prevRoomNum curRoomNum)
		(= curRoomNum newRoomNumber)
		(= newRoomNum newRoomNumber)
		(FlushResources newRoomNumber)
		(= temp4 (self setCursor: waitCursor 1))
		(self
			startRoom: curRoomNum
			checkAni:
			setCursor: temp4 (HaveMouse)
		)
		(SetSynonyms regions)
		(while ((= temp5 (Event new: 3)) type?)
			(temp5 dispose:)
		)
		(temp5 dispose:)
	)
	
	(method (startRoom param1)
		(if debugOn (SetDebug))
		(regions
			addToFront: ((= curRoom (ScriptID param1)) init: yourself:)
		)
	)
	
	(method (restart)
		(if modelessDialog (modelessDialog dispose:))
		(RestartGame)
	)
	
	(method (restore &tmp [temp0 20] temp20 temp21 temp22)
		(Load rsFONT smallFont)
		(= temp21 (self setCursor: normalCursor))
		(= temp22 (Sound pause: 1))
		(if (localproc_0b98 1)
			(if modelessDialog (modelessDialog dispose:))
			(if (!= (= temp20 (Restore doit: &rest)) -1)
				(self setCursor: waitCursor 1)
				(if (CheckSaveGame name temp20 version)
					(cast eachElementDo: #dispose)
					(cast eachElementDo: #delete)
					(RestoreGame name temp20 version)
				else
					(Print 994 1 #font 0 #button {OK} 1)
					(self setCursor: temp21 (HaveMouse))
				)
			)
			(localproc_0b98 0)
		)
		(Sound pause: temp22)
	)
	
	(method (save &tmp [temp0 20] temp20 temp21 temp22)
		(Load rsFONT smallFont)
		(Load rsCURSOR waitCursor)
		(= temp22 (Sound pause: 1))
		(if (localproc_0b98 1)
			(if modelessDialog (modelessDialog dispose:))
			(if (!= (= temp20 (Save doit: @temp0)) -1)
				(= temp21 (self setCursor: waitCursor 1))
				(if (not (SaveGame name temp20 @temp0 version))
					(Print 994 0 #font 0 #button {OK} 1)
				)
				(self setCursor: temp21 (HaveMouse))
			)
			(localproc_0b98 0)
		)
		(Sound pause: temp22)
	)
	
	(method (changeScore param1)
		(= score (+ score param1))
		(StatusLine doit:)
	)
	
	(method (handleEvent event)
		(cond 
			(
				(or
					(regions handleEvent: event)
					(locales handleEvent: event)
				)
			)
			(script (script handleEvent: event))
		)
		(event claimed?)
	)
	
	(method (showMem)
		(Printf
			{Free Heap: %u Bytes\nLargest ptr: %u Bytes\nFreeHunk: %u KBytes\nLargest hunk: %u Bytes}
			(MemoryInfo 1)
			(MemoryInfo 0)
			(>> (MemoryInfo 3) $0006)
			(MemoryInfo 2)
		)
	)
	
	(method (setSpeed newSpeed &tmp theSpeed)
		(= theSpeed speed)
		(= speed newSpeed)
		(return theSpeed)
	)
	
	(method (setCursor cursorNumber param2 &tmp theTheCursor)
		(= theTheCursor theCursor)
		(= theCursor cursorNumber)
		(if (== argc 1)
			(SetCursor cursorNumber)
		else
			(SetCursor cursorNumber param2)
		)
		(return theTheCursor)
	)
	
	(method (checkAni &tmp temp0)
		(Animate (cast elements?) 0)
		(Wait 0)
		(Animate (cast elements?) 0)
		(while (> (Wait 0) aniThreshold)
			(breakif (== (= temp0 (cast firstTrue: #isExtra)) 0))
			(temp0 addToPic:)
			(Animate (cast elements?) 0)
			(cast eachElementDo: #delete)
		)
	)
	
	(method (notify)
	)
	
	(method (setScript theScript)
		(if script (script dispose:))
		(if (= script theScript)
			((= script theScript) init: self &rest)
		)
	)
	
	(method (cue)
		(if script (script cue:))
	)
	
	(method (wordFail param1 &tmp [temp0 100])
		(Printf 994 2 param1)
		(return 0)
	)
	
	(method (syntaxFail)
		(Print 994 3)
	)
	
	(method (semanticFail)
		(Print 994 4)
	)
	
	(method (pragmaFail)
		(Print 994 5)
	)
)

(class Region kindof Object
	(properties
		script 0
		number 0
		timer 0
		keep 0
		initialized 0
	)
	
	(method (init)
		(if (not initialized)
			(= initialized 1)
			(if (not (regions contains: self))
				(regions addToEnd: self)
			)
			(super init:)
		)
	)
	
	(method (doit)
		(if script (script doit:))
	)
	
	(method (dispose)
		(regions delete: self)
		(if (IsObject script) (script dispose:))
		(if (IsObject timer) (timer dispose:))
		(sounds eachElementDo: #clean self)
		(DisposeScript number)
	)
	
	(method (handleEvent event)
		(if script (script handleEvent: event))
		(event claimed?)
	)
	
	(method (setScript theScript)
		(if (IsObject script) (script dispose:))
		(if (= script theScript)
			((= script theScript) init: self &rest)
		)
	)
	
	(method (cue)
		(if script (script cue:))
	)
	
	(method (newRoom)
	)
	
	(method (notify)
	)
)

(class Room kindof Region
	(properties
		script 0
		number 0
		timer 0
		keep 0
		initialized 0
		picture 0
		style $ffff
		horizon 0
		controls 0
		north 0
		east 0
		south 0
		west 0
		curPic 0
		picAngle 0
		vanishingX 160
		vanishingY -30000
	)
	
	(method (init &tmp temp0)
		(= number curRoomNum)
		(= controls roomControls)
		(= perspective picAngle)
		(if picture (self drawPic: picture))
		(switch ((User alterEgo?) edgeHit?)
			(1 ((User alterEgo?) y: 188))
			(4
				((User alterEgo?)
					x: (- 319 ((User alterEgo?) xStep?))
				)
			)
			(3
				((User alterEgo?)
					y: (+ horizon ((User alterEgo?) yStep?))
				)
			)
			(2 ((User alterEgo?) x: 1))
		)
		((User alterEgo?) edgeHit: 0)
	)
	
	(method (doit &tmp temp0)
		(if script (script doit:))
		(if
			(= temp0
				(switch ((User alterEgo?) edgeHit?)
					(1 north)
					(2 east)
					(3 south)
					(4 west)
				)
			)
			(self newRoom: temp0)
		)
	)
	
	(method (dispose)
		(if controls (controls dispose:))
		(super dispose:)
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			(controls (controls handleEvent: event))
		)
		(event claimed?)
	)
	
	(method (newRoom newRoomNumber)
		(regions
			delete: self
			eachElementDo: #newRoom newRoomNumber
			addToFront: self
		)
		(= newRoomNum newRoomNumber)
		(super newRoom: newRoomNumber)
	)
	
	(method (setRegions region &tmp temp0 theRegion temp2)
		(= temp0 0)
		(while (< temp0 argc)
			(= theRegion [region temp0])
			((= temp2 (ScriptID theRegion)) number: theRegion)
			(regions add: temp2)
			(if (not (temp2 initialized?)) (temp2 init:))
			(++ temp0)
		)
	)
	
	(method (setFeatures feature &tmp temp0 [temp1 2])
		(= temp0 0)
		(while (< temp0 argc)
			(features add: [feature temp0])
			(++ temp0)
		)
	)
	
	(method (setLocales locale &tmp temp0 theLocale temp2)
		(= temp0 0)
		(while (< temp0 argc)
			(= theLocale [locale temp0])
			((= temp2 (ScriptID theLocale)) number: theLocale)
			(locales add: temp2)
			(temp2 init:)
			(++ temp0)
		)
	)
	
	(method (drawPic picNumber picAnimation)
		(addToPics dispose:)
		(= curPic picNumber)
		(= overlays -1)
		(DrawPic
			picNumber
			(cond 
				((== argc 2) picAnimation)
				((!= style -1) style)
				(else showStyle)
			)
			TRUE
			0
		)
	)
	
	(method (overlay picNumber picAnimation)
		(= overlays picNumber)
		(DrawPic
			picNumber
			(cond 
				((== argc 2) picAnimation)
				((!= style -1) style)
				(else showStyle)
			)
			FALSE
			0
		)
	)
)

(class Locale of Object
	(properties
		number 0
	)
	
	(method (dispose)
		(locales delete: self)
		(DisposeScript number)
	)
	
	(method (handleEvent event)
		(event claimed?)
	)
)

(class StatusLine of Object
	(properties
		state $0000
		code 0
	)
	
	(method (doit &tmp [temp0 41])
		(if code
			(code doit: @temp0)
			(DrawStatus (if state @temp0 else 0))
		)
	)
	
	(method (enable)
		(= state 1)
		(self doit:)
	)
	
	(method (disable)
		(= state 0)
		(self doit:)
	)
)

(instance RU of Code
	(properties)
	
	(method (doit param1 &tmp temp0)
		(if (param1 underBits?)
			(= temp0
				(&
					(= temp0 (| (= temp0 (param1 signal?)) $0001))
					$fffb
				)
			)
			(param1 underBits: 0 signal: temp0)
		)
	)
)

(instance DNKR of Code
	(properties)
	
	(method (doit param1)
		(if (not (param1 keep?)) (param1 dispose:))
	)
)
