class Field {
	boolean isOpen = false;
	boolean isBlocked = false;
	int nearbyMines;
	boolean isMine;
	
	Field(boolean mine){isMine = mine;}
	void setMine() {isMine = true;}
	boolean getMine(){return isMine;}
	void setNearbyMines(){nearbyMines++;}
	int getNearbyMines(){return nearbyMines;}
	void setOpen(boolean open) {isOpen = open;}
	boolean getOpen(){return isOpen;}
	void setBlocked(boolean blocked) {isBlocked = blocked;}
	boolean getBlocked(){return isBlocked;}
}