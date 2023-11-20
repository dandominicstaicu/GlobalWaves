package entities;

import lombok.Getter;
import lombok.Setter;

import fileio.input.LibraryInput;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MainPlayer {

	private Library library;

	public MainPlayer(LibraryInput libraryInput) {
		this.library = Library.initializeLibrary(libraryInput);
	}

}
