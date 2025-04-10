import java.util.Random;

class Minefield {
	Field[][] field;
	boolean gameOver = false;
	boolean gameWon = false;
	int mines = 0;
	Minefield(int height, int length, int mineCount){
		if(height > 0 || length > 0) {
			field = new Field[height][length];
			fillField();
			mines = mineCount;
			//autoFillMines(mineCount);
			//scanMines();
			if (mineCount > height * length) {
				System.out.println("Too many mines");
			}
		}
		else{
			System.out.println("Field is too small");
		}
	}
	//builds the Field
	void fillField() {
		for (int i = 0; i < field.length; i++) {
			for (int k = 0; k < field[0].length; k++) {
				field[i][k] = new Field(false);
			}
		}
	}
	//randomly fills the field with mines
	void autoFillMines(int mineCount, int height, int length) {
		if (mineCount < field.length * field[0].length - 9) {
			int usedMines = 0;
			Random random = new Random();
			while (usedMines < mineCount) {
				int mineHeight = random.nextInt(field.length);
				int mineLength = random.nextInt(field[0].length);
				if (!field[mineHeight][mineLength].getMine()&&!(nearbyField(height,length,mineHeight,mineLength))) {
					field[mineHeight][mineLength].setMine();
					usedMines++;
				}
			}
		}
		else {
            for (Field[] fields : field) {
                for (int j = 0; j < field[0].length; j++) {
                    fields[j].setMine();
                }
            }
		}
	}
	//gives every field the nearbyMines info
	void scanMines() {
		for(int i = 0; i < field.length; i++) {
			for(int k = 0; k < field[0].length; k++) {
				if(field[i][k].getMine()) {
					for(int j = i-1; j <= i+1; j++) {
						for(int l = k-1; l <= k+1; l++) {
							if(j < field.length && l < field[0].length && j >= 0 && l >= 0) {
								field[j][l].setNearbyMines();
							}
						}
					}
				}
			}
		}
	}
	//Opens field for Coordinates, ignores blocked and if field was a mine handles Game Over!
	void leftClicked(int height,int length) {
		if(openCounter()!=0) {
			if (!(field[height][length].getOpen())) {
				if (!field[height][length].getBlocked()) {
					field[height][length].setOpen(true);
					if (field[height][length].getMine()) {
						gameOver = true;
					} else {
						if (field[height][length].getNearbyMines() == 0) {
							for (int i = height - 1; i <= height + 1; i++) {
								for (int j = length - 1; j <= length + 1; j++) {
									if (i < field.length && j < field[0].length && i >= 0 && j >= 0) {
										leftClicked(i, j);
									}
								}
							}
						}
					}
				}
			}
			isGameWon();
		}
		else{
			autoFillMines(mines,height,length);
			scanMines();
			field[height][length].setOpen(true);
			for (int i = height - 1; i <= height + 1; i++) {
				for (int j = length - 1; j <= length + 1; j++) {
					if (i < field.length && j < field[0].length && i >= 0 && j >= 0) {
						leftClicked(i, j);
					}
				}
			}
		}
	}
	//Blocking field for Coordinates
	void rightClicked(int height, int length) {
		if(!field[height][length].getOpen()&&!field[height][length].getBlocked()) {
			field[height][length].setBlocked(true);
		}
		else {
			if (!field[height][length].getOpen()) {
				field[height][length].setBlocked(false);
			}
		}
	}
	//Nearby mines output !!!Mines also have a NrOfNearbyMines!!!
	int searchNearbyMines(int height, int length) {
		return field[height][length].getNearbyMines();
	}
	//Counter for Mine/Open/Blocked in the whole field
	int mineCounter() {
		int mineCount = 0;
		for (Field[] fields : field) {
			for (int j = 0; j < field[0].length; j++) {
				if (fields[j].getMine()) {
					mineCount++;
				}
			}
		}
		return mineCount;
	}
	int openCounter() {
		int openCount = 0;
		for (Field[] fields : field) {
			for (int j = 0; j < field[0].length; j++) {
				if (fields[j].getOpen()) {
					openCount++;
				}
			}
		}
		return openCount;
	}
	int blockedCounter() {
		int blockedCount = 0;
		for (Field[] fields : field) {
			for (int j = 0; j < field[0].length; j++) {
				if (fields[j].getBlocked()) {
					blockedCount++;
				}
			}
		}
		return blockedCount;
	}
	//Check for Open?? Mine?? Blocked??
	boolean getOpen(int height,int length) {return field[height][length].getOpen();}
	boolean isMine(int height,int length) {return field[height][length].getMine();}
	boolean isBlocked(int height,int length) {return field[height][length].getBlocked();}
	//Check is the Game won??
	void isGameWon() {
		int nrOfFields = field.length*field[0].length;
		for (Field[] fields : field) {
			for (int k = 0; k < field[0].length; k++) {
				if ((fields[k].getOpen()&&!fields[k].getMine())||(!fields[k].getOpen()&&fields[k].getMine())) {
					nrOfFields--;
				}
			}
		}
		if (nrOfFields == 0) {gameWon = true;}
	}
	//reset for a new game field
	void newGame(){
		gameWon = false;
		gameOver = false;
        for (Field[] fields : field) {
            for (int k = 0; k < field[0].length; k++) {
                fields[k].setOpen(false);
            }
        }
	}
	//(Test Only) placing Mines on specific positions
	void placeMines(int height, int length) {
		if(!field[height][length].getMine()) {
			field[height][length].setMine();
		}
	}
	//Visual output in the terminal (Test Only)
	void gameVisualOutput() {
		for (Field[] fields : field) {
			for (int j = 0; j < field[0].length; j++) {
				if (!fields[j].getOpen()) {
					System.out.print("▒ ");
				} else {
					if (fields[j].getMine()) {
						System.out.print("X ");
					} else {
						if (fields[j].getNearbyMines() > 0) {
							System.out.print(fields[j].getNearbyMines() + " ");
						} else {
							System.out.print("_" + " ");
						}
					}
				}
			}
			System.out.println(" ");
		}
	}
	//check that returns true for all nearby fields
	boolean nearbyField(int height, int length, int heightToTest, int lengthToTest) {
		for (int i = height-1; i <= height+1; i++) {
			for (int j = length-1; j <= length+1; j++) {
				if(i < field.length && j < field[0].length && i >= 0 && j >= 0) {
					if (field[heightToTest][lengthToTest] == field[i][j]) {
						return true;
					}
				}
			}
		}
        return false;
    }
}