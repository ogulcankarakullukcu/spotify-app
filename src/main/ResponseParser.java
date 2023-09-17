package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.model.Album;
import main.model.Artist;
import main.model.Track;

public class ResponseParser {

	private String response;
	private Repository repo;

	public ResponseParser(String response) throws Exception {
		this.response = response;
		this.repo = new Repository();
	}

	public void parseTrackResponse() throws JSONException {

		Set<Track> trackSet = new HashSet<Track>();

		JSONObject file = new JSONObject(response);

		JSONObject tracks = file.getJSONObject("tracks");
		JSONArray items = tracks.getJSONArray("items");

		for (int i = 0; i < items.length(); i++) {

			JSONObject object = (JSONObject) items.get(i);
			trackSet.add(parseTrackObject(object));

		}

		this.repo.saveTrackSetToFile(trackSet);

	}

	private Track parseTrackObject(JSONObject trackJSONObject) throws JSONException {

		return Track.Builder.newInstance()//
				.setId(trackJSONObject.getString("id"))//
				.setType(trackJSONObject.getString("type"))//
				.setName(trackJSONObject.getString("name"))//
				.setPopularity(trackJSONObject.getInt("popularity"))
				.setAlbum(parseAlbumObject(trackJSONObject.getJSONObject("album")))//
				.setArtists(parseArtistArray(trackJSONObject.getJSONArray("artists")))//
				.build();

	}

	private Album parseAlbumObject(JSONObject albumJSONObject) throws JSONException {

		return Album.Builder.newInstance().setId(albumJSONObject.getString("id"))//
				.setType(albumJSONObject.getString("type"))//
				.setName(albumJSONObject.getString("name"))//
				.setAlbumType(albumJSONObject.getString("album_type"))
				.setArtists(parseArtistArray(albumJSONObject.getJSONArray("artists")))//
				.build();

	}

	private List<Artist> parseArtistArray(JSONArray artistJSONArray) throws JSONException {
		List<Artist> artistList = new ArrayList<Artist>();

		for (int i = 0; i < artistJSONArray.length(); i++) {

			JSONObject object = (JSONObject) artistJSONArray.get(i);
			artistList.add(parseArtistObject(object));
		}

		return artistList;
	}

	private Artist parseArtistObject(JSONObject artistJSONObject) throws JSONException {

		return Artist.Builder.newInstance().setId(artistJSONObject.getString("id"))//
				.setType(artistJSONObject.getString("type"))//
				.setName(artistJSONObject.getString("name"))//
				.build();

	}

}
