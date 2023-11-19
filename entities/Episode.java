package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Episode extends AudioFile {
	private String description;

	public Episode(String name, Integer duration, String description) {
		super.setName(name);
		super.setDuration(duration);
		this.description = description;
	}
}
