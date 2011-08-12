package net.llamaslayers.noisetextures;

import java.util.Random;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class GrassTextureGenerator extends AbstractTextureGenerator {
	private final SimplexOctaveGenerator density;
	private final SimplexOctaveGenerator light;
	private final SimplexOctaveGenerator medium;
	private final SimplexOctaveGenerator heavy;

	public GrassTextureGenerator(long seed) {
		super(seed);

		Random r = getSeed();
		density = getNoise(r, 3);
		density.setScale(1.0 / 256);
		light = getNoise(r, 8);
		light.setScale(1.0 / 64);
		medium = getNoise(r, 8);
		light.setScale(1.0 / 16);
		heavy = getNoise(r, 8);
		light.setScale(1.0 / 4);
	}

	@Override
	public byte getTone(double x, double y) {
		return (byte) Math.min(Math.max(lerp3(light.noise(x, y, 0.5, 0.5, true) / 4,
											  medium.noise(x, y, 0.5, 0.5, true) / 2,
											  heavy.noise(x, y, 0.5, 0.5, true) / 1.5,
											  density.noise(x, y, 0.5, 0.5, true))
				* 128 + 128, 0), 255);
	}

	@Override
	protected byte[] generate(int x, int y, int width, int height, int step) {
		byte[] b = new byte[width * height * step * step];

		int i = 0;
		for (double _x = x; _x < x + width; _x += 1.0 / step) {
			for (double _y = y; _y < y + height; _y += 1.0 / step) {
				b[i] = (byte) Math.min(Math.max(lerp3(light.noise(_x, _y, 0.5, 0.5, true) / 4,
													  medium.noise(_x, _y, 0.5, 0.5, true) / 2,
													  heavy.noise(_x, _y, 0.5, 0.5, true) / 1.5,
													  density.noise(_x, _y, 0.5, 0.5, true))
						* 128 + 128, 0), 255);
				i++;
			}
		}

		return b;
	}

	private double lerp3(double a, double b, double c, double variant) {
		if (variant < 0)
			return -a * variant + b * (1 + variant);
		return b * variant + c * (1 - variant);
	}
}
