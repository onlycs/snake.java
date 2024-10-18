import java.util.ArrayDeque;
import java.util.Optional;

public class Snake {
	public final int size = 30;
	ArrayDeque<Coordinate> snake;
	Coordinate apple;

	int direction = 0;
	ArrayDeque<Integer> dirqueue = new ArrayDeque<Integer>();

	private boolean end = false;
	private boolean success = false;

	public Snake() {
		snake = new ArrayDeque<Coordinate>();

		snake.add(new Coordinate(size / 2, (size / 2) + 1));
		snake.add(new Coordinate(size / 2, size / 2));
		apple();
	}

	private void apple() {
		do {
			apple = new Coordinate((int) (Math.random() * size), (int) (Math.random() * size));
		} while (snake.contains(apple));
	}

	int getLastDir() {
		return Optional.ofNullable(dirqueue.peekLast()).orElse(this.direction);
	}

	public void addKeypress(int direction) {
		if ((getLastDir() + direction) % 2 == 0 || dirqueue.size() >= 3) {
			return;
		}

		dirqueue.add(direction);
	}

	public void tick() {
		if (!dirqueue.isEmpty()) {
			direction = dirqueue.removeFirst();
		}

		Coordinate next = snake.getLast().next(direction);

		if (!next.inbounds(size) || snake.contains(next)) {
			end = true;
			return;
		}

		snake.add(next);
		
		if (next.equals(apple)) {
			if (snake.size() == size * size - 1) {
				success = true;
				end = true;
				return;
			}

			apple();
			return;
		}

		snake.removeFirst();
	}

	/// 0=empty, 1=snake, 2=head, 3=apple
	public int[][] render() {
		int[][] grid = new int[size][size];

		for (Coordinate c : snake) {
			grid[c.y][c.x] = 1;
		}

		grid[snake.getLast().y][snake.getLast().x] = 2;
		grid[apple.y][apple.x] = 3;

		return grid;
	}

	public boolean end() {
		return end;
	}

	public boolean success() {
		return success;
	}
}
