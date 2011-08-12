package net.llamaslayers.noisetextures;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public abstract class AbstractTextureGenerator {
	private final long seed;

	public AbstractTextureGenerator(long seed) {
		this.seed = seed;
	}

	protected final Random getSeed() {
		return new Random(seed);
	}

	protected final SimplexOctaveGenerator getNoise(Random seed, int octaves) {
		return new SimplexOctaveGenerator(seed, octaves);
	}

	public final BufferedImage render(int x, int y, int width, int height, int step, Color base) {
		BufferedImage image = new BufferedImage(width * step, height * step, BufferedImage.TYPE_INT_RGB);
		int r = base.getRed();
		int g = base.getGreen();
		int b = base.getBlue();

		byte[] tones = generate(x, y, width, height, step);

		for (int i = 0; i < width * step; i++) {
			for (int j = 0; j < height * step; j++) {
				image.setRGB(i, j, getRGB(r, g, b, tones[i * width * step + j]));
			}
		}

		return image;
	}

	public final BufferedImage render3D(Color base, int width, int height,
										double cameraX, double cameraY, double cameraZ,
										double pitch, double yaw, double scale) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		if (cameraZ <= 0) {
			return image;
		}

		int r = base.getRed();
		int g = base.getGreen();
		int b = base.getBlue();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double tx = x / scale - cameraX;
				double ty = y / scale - cameraY;
				double tz = -cameraZ;
				double distance;
				double angle;

				// pitch
				distance = Math.hypot(tx, tz);
				angle = Math.atan2(tz, tx);
				tx = Math.cos(pitch + angle) * distance;
				tz = Math.sin(pitch + angle) * distance;

				// yaw
				distance = Math.hypot(tx, ty);
				angle = Math.atan2(ty, tx);
				tx = Math.cos(yaw + angle) * distance;
				ty = Math.sin(yaw + angle) * distance;

				double transformedX = tx / tz * scale * scale;
				double transformedY = ty / tz * scale * scale;

				//System.out.println(x + ", " + y + " -> " + transformedX + ", " + transformedY);

				image.setRGB(x, y, getRGB(r, g, b, getTone(transformedX, transformedY)));
			}
		}

		return image;
	}

	private int getRGB(int r, int g, int b, byte tone) {
		int t = tone;
		if (tone < 0) {
			tone += 256;
		}
		tone -= 128;
		return (Math.min(Math.max(r + tone, 0), 255) << 16)
				| (Math.min(Math.max(g + tone, 0), 255) << 8)
				| (Math.min(Math.max(b + tone, 0), 255));
	}

	public final int transformRGB(int rgb, byte tone) {
		rgb = rgb & 0xffffff;
		return getRGB(rgb >> 16, (rgb >> 8) & 0xff, rgb & 0xff, tone);
	}
	protected final int mod(int n, int m) {
		return ((n % m) + m) % m;
	}
	protected final double mod(double n, double m) {
		return ((n % m) + m) % m;
	}

	public abstract byte getTone(double x, double y);
	protected abstract byte[] generate(int x, int y, int width, int height, int step);
}
