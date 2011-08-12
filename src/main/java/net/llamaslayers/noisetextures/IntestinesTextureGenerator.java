package net.llamaslayers.noisetextures;

import java.util.Random;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class IntestinesTextureGenerator extends AbstractTextureGenerator {
	private static final double ESPILON = 0.00001;
	private static final double[] ANGLE_MODIFIERS = {2, 1.5, 1, 0.5, 1, 1.5, 2, 2.5};
	private final SimplexOctaveGenerator noise;

	public IntestinesTextureGenerator(long seed) {
		super(seed);
		Random r = getSeed();
		noise = getNoise(r, 4);
		noise.setScale(1 / 16.0);
	}

	@Override
	public byte getTone(double x, double y) {
		return (byte) Math.min(Math.max(noise.noise(x, y, 0.5, 0.5, true) * 40
						* ANGLE_MODIFIERS[getUp(x, y)] + 120, 0), 255);
	}

	@Override
	protected byte[] generate(int x, int y, int width, int height, int step) {
		byte[] b = new byte[width * height * step * step];

		int i = 0;
		for (double _x = x; _x < x + width; _x += 1.0 / step) {
			for (double _y = y; _y < y + height; _y += 1.0 / step) {
				b[i] = (byte) Math.min(Math.max(noise.noise(_x, _y, 0.5, 0.5, true) * 40
						* ANGLE_MODIFIERS[getUp(_x, _y)] + 120, 0), 255);
				i++;
			}
		}

		return b;
	}

	private int getUp(double x, double y) {
		double[][] matrix = {
			{
				0,
				noise.noise(x, y - ESPILON, 0.5, 0.5, true),
				0
			},
			{
				noise.noise(x - ESPILON, y, 0.5, 0.5, true),
				0,
				noise.noise(x + ESPILON, y, 0.5, 0.5, true)
			},
			{
				0,
				noise.noise(x, y + ESPILON, 0.5, 0.5, true),
				0
			}
		};

		if (matrix[0][1] > matrix[2][1]) {
			if (matrix[1][0] > matrix[1][2]) {
				if (matrix[0][1] > matrix[1][0]) {
					return 2;
				}
				return 3;
			}
			if (matrix[0][1] > matrix[1][2]) {
				return 1;
			}
			return 0;
		}
		if (matrix[1][0] > matrix[1][2]) {
			if (matrix[2][1] > matrix[1][0]) {
				return 5;
			}
			return 4;
		}
		if (matrix[2][1] > matrix[1][2]) {
			return 6;
		}
		return 7;
	}
}
