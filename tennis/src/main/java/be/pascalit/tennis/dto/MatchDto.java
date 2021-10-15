package be.pascalit.tennis.dto;

/**
 * MatchDto - Bean Match for use by UI layer (full)
 * 
 * @author PascaL
 */
public final class MatchDto {

	private long id;

	private PlayerDto winner;

	private PlayerDto finalist;

	private EventFullDto event;
	
	private ScoreFullDto score;


	/**
	 * MatchDto constructor
	 * @param matchId
	 * @param winner
	 * @param finalist
	 */
	public MatchDto(long matchId, PlayerDto winner, PlayerDto finalist) {
		this.id = matchId;
		this.winner = winner;
		this.finalist = finalist;
	}
	
	/**
	 * MatchDto constructor when matchId is unknown (e.g. create a new match with his scores)
	 * @param eventId
	 * @param winner
	 * @param finalist
	 * @param score
	 */
	public MatchDto(EventFullDto event, PlayerDto winner, PlayerDto finalist, ScoreFullDto score) {
		this.event = event;
		this.winner = winner;
		this.finalist = finalist;
		this.score = score;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PlayerDto getWinner() {
		return winner;
	}

	public void setWinner(PlayerDto winner) {
		this.winner = winner;
	}

	public PlayerDto getFinalist() {
		return finalist;
	}

	public void setFinalist(PlayerDto finalist) {
		this.finalist = finalist;
	}

	public EventFullDto getEvent() {
		return event;
	}

	public void setEvent(EventFullDto event) {
		this.event = event;
	}

	public ScoreFullDto getScore() {
		return score;
	}

	public void setScore(ScoreFullDto score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "MatchDto [id=" + id + ", winner=" + winner + ", finalist=" + finalist + ", event=" + event + ", score=" + score + "]";
	}


}
