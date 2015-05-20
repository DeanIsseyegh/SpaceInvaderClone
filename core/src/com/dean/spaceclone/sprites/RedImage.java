package com.dean.spaceclone.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.dean.receiptmaker.model.Coordinate;
import com.dean.receiptmaker.model.Pixel;

/**
 * Responsible for reading in a image and converting its detected pixels into
 * coordinates. These coordinates can later be used to draw the picture.
 * 
 * Please note that it will read the image from the top left corner downwards.
 * That means the top left corner of the picture will return coordinates (0,0).
 * If you would like to flip the coordinates, simply call the flipCoordinates()
 * method.
 * 
 * @author Dean
 *
 */
public class RedImage {

	Array<Coordinate> coordinates = new Array<>();
	BufferedImage img;

	/**
	 * Note that it expects the coordinates of the pixels wanted to be Red
	 * (255,0,0).
	 * 
	 * @param handle
	 */
	public RedImage(FileHandle handle) {
		try {
			img = ImageIO.read(handle.read());
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Pixel pixel = new Pixel(img.getRGB(x, y));
				if (pixel.isRed()) {
					Coordinate coordinate = new Coordinate(x, y);
					coordinates.add(coordinate);
				}
			}
		}
	}

	/**
	 * Will invert the coordinates. If the top left corner was (0,0), now it the
	 * bottom right corner is (0,0).
	 */
	public void flipCoordinates() {
		Array<Coordinate> flippedCoordinates = new Array<>();
		for (Coordinate coordinate : coordinates) {
			Coordinate flippedCoordinate = new Coordinate(img.getWidth() - coordinate.getX(), img.getHeight() - coordinate.getY());
			flippedCoordinates.add(flippedCoordinate);
		}
		coordinates = flippedCoordinates;
	}

	/**
	 * Will only invert the Y coordinates.
	 */
	public void flipCoordinatesY() {
		Array<Coordinate> flippedCoordinates = new Array<>();
		for (Coordinate coordinate : coordinates) {
			Coordinate flippedCoordinate = new Coordinate(coordinate.getX(), img.getHeight() - coordinate.getY());
			flippedCoordinates.add(flippedCoordinate);
		}
		coordinates = flippedCoordinates;
	}

	/**
	 * Will only invert the X coordinates.
	 */
	public void flipCoordinatesX() {
		Array<Coordinate> flippedCoordinates = new Array<>();
		for (Coordinate coordinate : coordinates) {
			Coordinate flippedCoordinate = new Coordinate(img.getWidth() - coordinate.getX(), coordinate.getY());
			flippedCoordinates.add(flippedCoordinate);
		}
		coordinates = flippedCoordinates;
	}

	public Array<Coordinate> getCoordinates() {
		return coordinates;
	}

	public Array<Coordinate> getCoordinatesWithOffset(float startXPos, float startYPos) {
		Array<Coordinate> coordinatesWithOffset = new Array<>();
		for (Coordinate coordinate : coordinates) {
			Coordinate coordWithOffset = new Coordinate(coordinate.getX() + startXPos, coordinate.getY() + startYPos);
			coordinatesWithOffset.add(coordWithOffset);
		}
		return coordinatesWithOffset;
	}

	public BufferedImage getImg() {
		return img;
	}

}
