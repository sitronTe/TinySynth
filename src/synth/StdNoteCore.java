package synth;

public enum StdNoteCore {
	E_1(-5), F_1(-4), FIS_1(-3), GES_1(-3), G_1(-2), GIS_1(-1), AS_1(-1), A_1(0), AIS_1(
			1), BES_1(1), B_1(2), C0(3), CIS0(4), DES0(4), D0(5), DIS0(6), ES0(
			6), E0(7), F0(8), FIS0(9), GES0(9), G0(10), GIS0(11), AS0(11), A0(
			12), AIS0(13), BES0(13), B0(14), C1(15), CIS1(16), DES1(16), D1(17), DIS1(
			18), ES1(18), E1(19), F1(20), FIS1(21), GES1(21), G1(22), GIS1(23), AS1(
			23), A1(24), AIS1(25), BES1(25), B1(26), C2(27), CIS2(28), DES2(28), D2(
			29), DIS2(30), ES2(30), E2(31), F2(32), FIS2(33), GES2(33), G2(34), GIS2(
			35), AS2(35), A2(36), AIS2(37), BES2(37), B2(38), C3(39), CIS3(40), DES3(
			40), D3(41), DIS3(42), ES3(42), E3(43), F3(44), FIS3(45), GES3(45), G3(
			46), GIS3(47), AS3(47), A3(48), AIS3(49), BES3(49), B3(50), C4(51), CIS4(
			52), DES4(52), D4(53), DIS4(54), ES4(54), E4(55), F4(56), FIS4(57), GES4(
			57), G4(58), GIS4(59), AS4(59), A4(60), AIS4(61), BES4(61), B4(62), C5(
			63), CIS5(64), DES5(64), D5(65), DIS5(66), ES5(66), E5(67), F5(68), FIS5(
			69), GES5(69), G5(70), GIS5(71), AS5(71), A5(72), AIS5(73), BES5(73), B5(
			74), C6(75), CIS6(76), DES6(76), D6(77), DIS6(78), ES6(78), E6(79), F6(
			80), FIS6(81), GES6(81), G6(82), GIS6(83), AS6(83), A6(84), AIS6(85), BES6(
			85), B6(86), C7(87), CIS7(88), DES7(88), D7(89), DIS7(90), ES7(90), E7(
			91), F7(92), FIS7(93), GES7(93), G7(94), GIS7(95), AS7(95), A7(96), AIS7(
			97), BES7(97), B7(98), C8(99), CIS8(100), DES8(100), D8(101), DIS8(
			102), ES8(102), E8(103), F8(104), FIS8(105), GES8(105), G8(106), GIS8(
			107), AS8(107), A8(108), AIS8(109), BES8(109), B8(110), C9(111), CIS9(
			112), DES9(112), D9(113), DIS9(114), ES9(114), E9(115), F9(116), FIS9(
			117), GES9(117), G9(118), GIS9(119), AS9(119), A9(120), AIS9(121), BES9(
			121), B9(122), C10(123), CIS10(124), DES10(124), D10(125), DIS10(
			126), ES10(126), E10(127);

	private final int absoluteNoteNumber;

	StdNoteCore(int noteNumber) {
		absoluteNoteNumber = noteNumber;
	}

	public double getFrequency() {
		return ((double) 440 * Math.pow(2,
				((double) (absoluteNoteNumber - 12 * 5) / 12)));
	}
	
	public StdNoteCore getNoteCoreRelativeDistance(int distance) {
		int absNoteNumber = absoluteNoteNumber + distance;
		for (StdNoteCore c: values())
			if (c.absoluteNoteNumber == absNoteNumber)
				return c;
		throw new IllegalArgumentException("Note out of range! Frequency would be out of range!");
	}
}
