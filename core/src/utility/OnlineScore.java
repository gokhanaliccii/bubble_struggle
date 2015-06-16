package utility;

import model.Score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class OnlineScore implements HttpResponseListener {

	private String app_id;
	private String app_key;

	public OnlineScore() {
		app_id = "Rlh3G4tyPrvA37LANeqwqBX8Y9YRuWnsZc4vr1z6";
		app_key = "JqeNAilWqJuQtN8LeJO8OJ6qv6qnDiTzE1pcsuJt";
	}

	public void addNewScore(Score score) {

		if(score== null)
			return ;

		Json json=new Json(OutputType.json);
		String scoreJson = json.toJson(score, score.getClass());
		
		
		
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("https://api.parse.com/1/classes/Score/");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("X-Parse-Application-Id", app_id);
		httpPost.setHeader("X-Parse-REST-API-Key", app_key);
		httpPost.setContent(scoreJson);

		Gdx.net.sendHttpRequest(httpPost, OnlineScore.this);
	}

	public void get_net_score() {

		HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
		httpGet.setUrl("https://api.parse.com/1/classes/Score/");
		httpGet.setHeader("Content-Type", "application/json");
		httpGet.setHeader("X-Parse-Application-Id", app_id);
		httpGet.setHeader("X-Parse-REST-API-Key", app_key);
		Gdx.net.sendHttpRequest(httpGet, OnlineScore.this);
	}

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		
		final int statusCode = httpResponse.getStatus().getStatusCode();
		
		
		System.out.println(statusCode + " " + httpResponse.getResultAsString());
	}

	@Override
	public void failed(Throwable t) {
		
	}

	@Override
	public void cancelled() {

	}
	
	
}
