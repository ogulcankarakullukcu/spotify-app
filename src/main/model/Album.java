package main.model;

import java.util.List;

public class Album {

	private String id, type, name, albumType;
	private List<Artist> artists;

	public Album(Builder builder) {
		this.id = builder.id;
		this.type = builder.type;
		this.name = builder.name;
		this.albumType = builder.albumType;
		this.artists = builder.artists;
	}

	public static class Builder {

		private String id, type, name, albumType;
		private List<Artist> artists;

		public static Builder newInstance() {
			return new Builder();
		}

		private Builder() {
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setType(String type) {
			this.type = type;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setAlbumType(String albumType) {
			this.albumType = albumType;
			return this;
		}

		public Builder setArtists(List<Artist> artists) {
			this.artists = artists;
			return this;
		}

		public Album build() {
			return new Album(this);
		}

	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getAlbumType() {
		return albumType;
	}

	public List<Artist> getArtists() {
		return artists;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", type=" + type + ", name=" + name + ", albumType=" + albumType + ", artists="
				+ artists + "]";
	}

}
