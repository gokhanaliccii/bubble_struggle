package enums;

public enum Way {

	NORTH(0), NORTH_EAST(1), EAST(2), 
	SOUTH_EAST(3), SOUTH(4), SOUTH_WEST(5), 
	WEST(6), NORTH_WEST(7);

	private Way(int way) {

		this.way=way;
	}

	int way;

	public int getWay() {

		return way;
	}
	
	public static Way getWay(int way){
		
		switch (way) {

		case 0:	 return NORTH;
		case 1:	 return NORTH_EAST;
		case 2:	 return EAST;
		case 3:	 return SOUTH_EAST;
		case 4:	 return SOUTH;
		case 5:	 return SOUTH_WEST;
		case 6:	 return WEST;
		case 7:	 return NORTH_WEST;
		
		default: break;
		}
		
		return NORTH;
	}
}
