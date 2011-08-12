package net.llamaslayers.noisetextures;

import java.util.Random;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class TileTextureGenerator extends AbstractTextureGenerator {
	public static final int TILE_SIZE = 128;
	private final SimplexOctaveGenerator noise;
	private final SimplexOctaveGenerator dirty;
	private final SimplexOctaveGenerator dirt;
	private final SimplexOctaveGenerator color;

	public TileTextureGenerator(long seed) {
		super(seed);
		Random r = getSeed();
		color = getNoise(r, 1);
		dirty = getNoise(r, 1);
		dirt = getNoise(r, 8);
		dirt.setScale(1 / 16.0);
		noise = getNoise(r, 8);
		noise.setScale(1 / 32.0);
	}

	@Override
	public byte getTone(double x, double y) {
		int initial = (int) Math.min(Math.max((color.noise(NoiseGenerator.floor(x / TILE_SIZE), NoiseGenerator.floor(y / TILE_SIZE), 0.5, 0.5, true) / 2
				+ noise.noise(x, y, 0.5, 0.5, true) / 24) * 64 + 128, 0), 255);
		if (dirty.noise(NoiseGenerator.floor(x / TILE_SIZE), NoiseGenerator.floor(y / TILE_SIZE), 0.5, 0.5) < 0) {
			return (byte) initial;
		}

		int distance = (int) Math.min(Math.min(Math.min(TILE_SIZE - mod(x, TILE_SIZE), mod(x, TILE_SIZE)),
											   Math.min(TILE_SIZE - mod(y, TILE_SIZE), mod(y, TILE_SIZE))), TILE_SIZE / 4);
		return (byte) Math.min(Math.max(distance + initial
				+ dirt.noise(x, y, 0.5, 0.5, true) * 8, 0), 255);
	}

	@Override
	protected byte[] generate(int x, int y, int width, int height, int step) {
		byte[] b = new byte[width * height * step * step];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				for (int xStep = 0; xStep < step; xStep++) {
					for (int yStep = 0; yStep < step; yStep++) {
						b[((i * step + xStep) * width + j) * step + yStep] = (byte) ((color.noise(NoiseGenerator.floor((double) (x + i) / TILE_SIZE), NoiseGenerator.floor((double) (y + j) / TILE_SIZE), 0.5, 0.5, true) / 2
								+ noise.noise(x + i + ((double) xStep / step), y + j + ((double) yStep / step), 0.5, 0.5, true) / 24) * 64 + 128);
					}
				}
			}
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (dirty.noise(NoiseGenerator.floor((double) (x + i) / TILE_SIZE),
								NoiseGenerator.floor((double) (y + j) / TILE_SIZE), 0.5, 0.5) < 0) {
					continue;
				}
				int distance = Math.min(Math.min(Math.min(TILE_SIZE - mod(x + i, TILE_SIZE), mod(x + i, TILE_SIZE)),
												 Math.min(TILE_SIZE - mod(y + j, TILE_SIZE), mod(y + j, TILE_SIZE))), TILE_SIZE / 4);
				for (int xStep = 0; xStep < step; xStep++) {
					for (int yStep = 0; yStep < step; yStep++) {
						int tone = b[((i * step + xStep) * width + j) * step + yStep] < 0
								? b[((i * step + xStep) * width + j) * step + yStep] + 256
								: b[((i * step + xStep) * width + j) * step + yStep];
						b[((i * step + xStep) * width + j) * step + yStep] = (byte) Math.min(Math.max(distance + tone
								+ dirt.noise(x + i + xStep / (double) step, y + j + yStep / (double) step, 0.5, 0.5, true) * 8, 0), 255);
					}
				}
			}
		}

		return b;
	}
}
