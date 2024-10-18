public class Coordinate {
	int x;
	int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Coordinate) {
			Coordinate c = (Coordinate) obj;
			return x == c.x && y == c.y;
		}
		return false;
	}

	public Coordinate next(int direction) {
		int x2 = x;
		int y2 = y;
		
		switch (direction) {
			case 0 -> y2--;
			case 1 -> x2++;
			case 2 -> y2++;
			case 3 -> x2--;
		}

		return new Coordinate(x2, y2);
	}

	public boolean inbounds(int size) {
		return x >= 0 && y >= 0 && x < size && y < size;
	}
}
