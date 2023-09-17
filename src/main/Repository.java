package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import main.model.Album;
import main.model.Artist;
import main.model.Track;

public class Repository {

	private final String artistCSVFilePath = "/files/artists.csv";
	private final String albumCSVFilePath = "/files/albums.csv";
	private final String trackCSVFilePath = "/files/tracks.csv";

	private final FileRead fileRead = new FileRead();
	private final FileWriter fw = new FileWriter();

	private Set<Track> trackSet;
	private Set<Album> albumSet;
	private Set<Artist> artistSet;

	public Repository() throws Exception {
		this.artistSet = findAllArtists();
		this.albumSet = findAllAlbums();
		this.trackSet = findAllTracks();
	}

	public void saveTrackSetToFile(Set<Track> trackSet) {
		List<Artist> artists = new ArrayList<Artist>();
		List<Album> albums = trackSet.stream().collect(Collectors.toList()).stream().map(Track::getAlbum)
				.collect(Collectors.toList());

		trackSet.stream().collect(Collectors.toList()).stream().map(Track::getArtists).forEachOrdered(artists::addAll);
		albums.stream().map(Album::getArtists).forEachOrdered(artists::addAll);

		saveAllArtists(artists.stream().collect(Collectors.toSet()));
		saveAllAlbums(albums.stream().collect(Collectors.toSet()));
		saveAllTracks(trackSet);

		writeAllFiles();
	}

	private void writeAllFiles() {

		writeArtistFile();
		writeAlbumFile();
		writeTrackFile();

	}

	private void writeTrackFile() {
		StringBuilder sb = new StringBuilder();
		this.trackSet.stream().forEach(
				t -> sb.append(t.getId() + "," + t.getType() + "," + t.getName() + "," + t.getPopularity().toString()
						+ "," + t.getAlbum().getId() + ",[" + getArtistString(t.getArtists()) + "]\n"));

		fw.writeCsvFile(sb.toString(), trackCSVFilePath);
	}

	private void writeArtistFile() {
		StringBuilder sb = new StringBuilder();
		this.artistSet.stream().forEach(t -> sb.append(t.getId() + "," + t.getType() + "," + t.getName() + "\n"));

		fw.writeCsvFile(sb.toString(), artistCSVFilePath);
	}

	private void writeAlbumFile() {
		StringBuilder sb = new StringBuilder();
		this.albumSet.stream().forEach(t -> sb.append(t.getId() + "," + t.getType() + "," + t.getName() + ","
				+ t.getAlbumType() + ",[" + getArtistString(t.getArtists()) + "]\n"));

		fw.writeCsvFile(sb.toString(), albumCSVFilePath);

	}

	private String getArtistString(List<Artist> artists) {
		return artists.stream().map(Artist::getId).collect(Collectors.joining(";"));
	}

	private void saveAllTracks(Set<Track> tracks) {
		this.trackSet.removeAll(tracks);
		this.trackSet.addAll(tracks);
	}

	private void saveAllAlbums(Set<Album> albums) {
		this.albumSet.removeAll(albums);
		this.albumSet.addAll(albums);
	}

	private void saveAllArtists(Set<Artist> artists) {
		this.artistSet.removeAll(artists);
		this.artistSet.addAll(artists);

	}

	private Set<Artist> findAllArtists() throws Exception {

		Set<Artist> artists = new HashSet<Artist>();

		this.fileRead.csvFileRead(artistCSVFilePath).stream().forEach(t -> {
			String[] artistLine = t.split(",");
			artists.add(Artist.Builder//
					.newInstance()//
					.setId(artistLine[0])//
					.setType(artistLine[1])//
					.setName(artistLine[2])//
					.build());
		});

		return artists;
	}

	private Set<Album> findAllAlbums() throws Exception {
		Set<Album> albums = new HashSet<Album>();

		this.fileRead.csvFileRead(albumCSVFilePath).stream().forEach(t -> {
			String[] albumLine = t.split(",");
			albums.add(Album.Builder.newInstance().setId(albumLine[0]).setType(albumLine[1]).setName(albumLine[2])
					.setAlbumType(albumLine[3]).setArtists(parseArtistList(albumLine[4])).build());
		});

		return albums;
	}

	private Set<Track> findAllTracks() throws Exception {
		Set<Track> tracks = new HashSet<Track>();

		this.fileRead.csvFileRead(trackCSVFilePath).stream().forEach(t -> {
			String[] trackLine = t.split(",");
			tracks.add(Track.Builder.newInstance()//
					.setId(trackLine[0])//
					.setType(trackLine[1])//
					.setName(trackLine[2])//
					.setPopularity(parsePopularity(trackLine[3]))//
					.setAlbum(parseAlbum(trackLine[4]))//
					.setArtists(parseArtistList(trackLine[5]))//
					.build());
		});

		return tracks;
	}

	private List<Artist> parseArtistList(String artistIDlist) {
		return getArtistList(Arrays.asList(artistIDlist.replace("[", "").replace("]", "").split(";")));
	}

	private List<Artist> getArtistList(List<String> artistIDlist) {

		List<Artist> artistList = new ArrayList<Artist>();

		artistIDlist.stream()
				.forEach(id -> artistList.add(this.artistSet.stream().collect(Collectors.toList()).stream()
						.filter(t -> t.getId().equals(id)).findFirst()//
						.orElse(Artist.Builder.newInstance().setId(id).build())//
				));

		return artistList;
	}

	private Album parseAlbum(String albumID) {
		return this.albumSet.stream().collect(Collectors.toList()).stream().filter(t -> t.getId().equals(albumID))
				.findFirst().orElse(Album.Builder.newInstance().setId(albumID).build());
	}

	private Integer parsePopularity(String string) {
		return string != "" ? Integer.parseInt(string) : null;
	}

}
