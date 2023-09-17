package main.model;

public class Artist {

	private String id, type, name;

	public Artist(Builder builder) {
		this.id = builder.id;
		this.type = builder.type;
		this.name = builder.name;
	}

	public static class Builder {
		private String id, type, name;

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

		public Artist build() {
			return new Artist(this);
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
		Artist other = (Artist) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", type=" + type + ", name=" + name + "]";
	}

}
