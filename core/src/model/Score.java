package model;

public class Score {

	String Name;
	int Score;

	public Score() {
	}

	public Score(String name, int score) {

		this.Name = name;
		this.Score = score;
	}

	public String getName() {
		return Name;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		this.Score = score;
	}

	public void setName(String name) {
		this.Name = name;
	}
	
	
	public String getScoreText() {
		
		return "Score : " + Score;
	}
}
