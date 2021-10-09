;;; Sierra Script 1.0 - (do not remove this comment)
;
; * SCI Game Header
; *************************************************************************
; * Put all the defines specific to your game in here

(include system.sh) (include sci2.sh)


;Inventory items
(enum
	iSilverFlute		;0
	iDiamondPouch		;1
	iTalisman			;2
	iLantern			;3
	iPandorasBox		;4
	iGoldBall			;5
	iWitchGlassEye		;6
	iObsidianScarab		;7
	iPeacockFeather		;8
	iLute				;9
	iSmallCrown			;10
	iFrog				;11
	iSilverBabyRattle	;12
	iGoldCoins			;13
	iCupidBow			;14
	iShovel				;15
	iAxe				;16
	iFishingPole		;17
	iShakespeareBook	;18
	iWorm				;19
	iSkeletonKey		;20
	iGoldenBridle		;21
	iBoard				;22
	iBone				;23
	iDeadFish			;24
	iMagicFruit			;25
	iSheetMusic			;26
	iSilverWhistle		;27
	iLocket				;28
	iMedal				;29
	iToyHorse			;30
	iGlassBottle		;31
	iGoldKey			;32
	iMagicHen			;33
	iRose				;34
	iNote				;35	
	iTooth			;36
	iVirginity      ;37 
	iDecoderRing    ;38
	iBriefcase      ;39
	iSkull          ;40
)


;ego states
(enum
	egoNormal
	egoInShallowWater
	egoInKneeDeepWater
	egoInWaistDeepWater
	egoSwimming
)
(define egoFalling 10)
(define egoOnSwampGrass 12)
(define egoIsFrog 13)
(define egoRidingDolphin 14)
(define egoFallDownStairs 16)
(define egoZombie 17)
(define egoOnStairs 18)

;fisherman states
(enum
	fisherInit
	fisherGoneFishing
	fisherComingHome
	fisherAtHome
)

;minstrel locations
(enum 1
	minstrel13
	minstrel14
	minstrel19
)

;frog prince states
(enum
	frogInit
	frogUnderwater
	frogGettingBall
	frogHasBall
	frogHeldByEgo
	frogIsPrince
	frogPrinceLeft
)

;Unicorn states
(enum
	uniINITIAL
	uniSHOT
	uniBRIDLED
)

(define uniCAPTURED 99)
(define uniFINAL 999)

;Game phases
(enum
	startingOut
	getTheUnicorn
	getTheHen
	getPandoraBox
	trappedInCastle
	killedLolotte
)
(define endGame 99)

;Mansion ghost phases
(enum 1
	mansionBABY
	mansionMISER
	mansionLADY
	mansionLORD
	mansionBOY
)
(define mansionFINAL 255)

;Ogre states
(enum 1
	ogreAWAY
	ogreEATING
	ogreSLEEPING
	ogreSTATE4
	ogreLEAVING
)

;Dwarf house states
(enum
	houseDIRTY
	houseCLEAN
	houseKICKEDOUT
)

;Regions
(enum 501
	WATER			;501
	UNUSED_502		;502
	BEACH			;503
	GULL			;504
	GENESTA			;505
	MEADOW			;506
	WOODS			;507
	FOREST			;508
	SCARY_FOREST	;509
	CEMETERY		;510
	MOUNTAIN		;511
	RIVER			;512
	SWAMP			;513
	PAN				;514
	UNUSED_515		;515
	MINSTREL		;516
	OGRE			;517
	UNICORN			;518
	OCEAN			;519
	INTRO			;520
	END				;521
)

(enum 600
	DWARF_MINE		;600
	DWARF_HOUSE		;601
	OGRE_HOUSE		;602
	HAUNTED_HOUSE	;603
	LOLOTTE			;604
	TROLL_CAVE		;605
)

(define INVDESC	702)	;UPGRADE: adds inventory item descriptions from AGI version

;Required memory sizes in bytes
(define DeathSize		800)
(define InstrumentSize	800)
(define BowSize			850)
(define SaveSize		1028)
(define CrownSize		1400)
(define GaugeSize		1850)
(define ReadSize		2000)
(define InvSize			2348)
(define PandoraSize		4500)
