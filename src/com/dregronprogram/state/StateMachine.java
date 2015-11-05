package com.dregronprogram.state;

import com.dregronprogram.display.Display;

public enum StateMachine {

	MENU, GAMEMODE;
	
	StateMachine(){
		Display.map.put(name(), false);
	}
	
}
