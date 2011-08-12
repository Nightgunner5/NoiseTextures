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

		long start = 0;
		long end = 0;

		start = System.nanoTime();
		gen = new TileTextureGenerator(0);
		end = System.nanoTime();
		System.out.println("Construction of TileTextureGenerator with seed 0 took " + ((end - start) / 1000000000.0) + " seconds.");
		start = System.nanoTime();
		img = gen.render(0, 0, 512, 512, 4, Color.GRAY);
		ImageIO.write(img, "png", new File("tile-0-gray.png"));
		end = System.nanoTime();
		System.out.println("Render of tile-0-gray.png took " + ((end - start) / 1000000000.0) + " seconds.");
		start = System.nanoTime();
		img = gen.render(0, 0, 512, 512, 4, new Color(0x9DCBE3));
		ImageIO.write(img, "png", new File("tile-0-blue.png"));
		end = System.nanoTime();
		System.out.println("Render of tile-0-blue.png took " + ((end - start) / 1000000000.0) + " seconds.");
		start = System.nanoTime();
		gen = new TileTextureGenerator(1);
		end = System.nanoTime();
		System.out.println("Construction of TileTextureGenerator with seed 1 took " + ((end - start) / 1000000000.0) + " seconds.");
		start = System.nanoTime();
		img = gen.render(0, 0, 512, 512, 4, Color.GRAY);
		ImageIO.write(img, "png", new File("tile-1-gray.png"));
		end = System.nanoTime();
		System.out.println("Render of tile-1-gray.png took " + ((end - start) / 1000000000.0) + " seconds.");

		start = System.nanoTime();
		gen = new DirtTextureGenerator(0);
		end = System.nanoTime();
		System.out.println("Construction of DirtTextureGenerator with seed 0 took " + ((end - start) / 1000000000.0) + " seconds.");
		start = System.nanoTime();
		img = gen.render(0, 0, 512, 512, 4, new Color(0x5E3C00));
		ImageIO.write(img, "png", new File("dirt-0-wet.png"));
		end = System.nanoTime();
		System.out.println("Render of dirt-0-wet.png took " + ((end - start) / 1000000000.0) + " seconds.");
		start = System.nanoTime();
		gen = new DirtTextureGenerator(1);
		end = System.nanoTime();
		System.out.println("Construction of DirtTextureGenerator with seed 1 took " + ((end - start) / 1000000000.0) + " seconds.");
		start = System.nanoTime();
		img = gen.render(0, 0, 512, 512, 4, new Color(0x96886E));
		ImageIO.write(img, "png", new File("dirt-1-dry.png"));
		end = System.nanoTime();
		System.out.println("Render of dirt-1-dry.png took " + ((end - start) / 1000000000.0) + " seconds.");

		start = System.nanoTime();
		gen = new IntestinesTextureGenerator(0);
		end = System.nanoTime();
		System.out.println("Construction of IntestinesTextureGenerator with seed 0 took " + ((end - start) / 1000000000.0) + " seconds.");
		start = System.nanoTime();
		img = gen.render(0, 0, 512, 512, 4, new Color(0xEF3728));
		ImageIO.write(img, "png", new File("intestines-0-eww.png"));
		end = System.nanoTime();
		System.out.println("Render of intestines-0-eww.png took " + ((end - start) / 1000000000.0) + " seconds.");

		start = System.nanoTime();
		gen = new GrassTextureGenerator(0);
		end = System.nanoTime();
		System.out.println("Construction of GrassTextureGenerator with seed 0 took " + ((end - start) / 1000000000.0) + " seconds.");
		start = System.nanoTime();
		img = gen.render(0, 0, 512, 512, 4, new Color(0x60AA25));
		ImageIO.write(img, "png", new File("grass-0-green.png"));
		end = System.nanoTime();
		System.out.println("Render of grass-0-green.png took " + ((end - start) / 1000000000.0) + " seconds.");
	}
}
