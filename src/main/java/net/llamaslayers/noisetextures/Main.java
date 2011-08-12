package net.llamaslayers.noisetextures;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) throws IOException {
		AbstractTextureGenerator gen;
		BufferedImage img;

		gen = new TileTextureGenerator(0);
		img = gen.render(0, 0, 512, 512, 4, Color.GRAY);
		ImageIO.write(img, "png", new File("tile-0-gray.png"));
		img = gen.render(0, 0, 512, 512, 4, new Color(0x9DCBE3));
		ImageIO.write(img, "png", new File("tile-0-blue.png"));
		gen = new TileTextureGenerator(1);
		img = gen.render(0, 0, 512, 512, 4, Color.GRAY);
		ImageIO.write(img, "png", new File("tile-1-gray.png"));

		gen = new DirtTextureGenerator(0);
		img = gen.render(0, 0, 512, 512, 4, new Color(0x5E3C00));
		ImageIO.write(img, "png", new File("dirt-0-wet.png"));
		gen = new DirtTextureGenerator(1);
		img = gen.render(0, 0, 512, 512, 4, new Color(0x96886E));
		ImageIO.write(img, "png", new File("dirt-1-dry.png"));

		gen = new IntestinesTextureGenerator(0);
		img = gen.render(0, 0, 512, 512, 4, new Color(0xEF3728));
		ImageIO.write(img, "png", new File("intestines-0-eww.png"));

		//gen = new GrassTextureGenerator(0);
		//img = gen.render(0, 0, 512, 512, 4, new Color(0x70CC25));
		//ImageIO.write(img, "png", new File("grass-0-green.png"));
	}
}
