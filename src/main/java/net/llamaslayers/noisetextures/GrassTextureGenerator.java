package net.llamaslayers.noisetextures;

import org.bukkit.util.noise.SimplexOctaveGenerator;

public class GrassTextureGenerator extends AbstractTextureGenerator {
	//private final SimplexOctaveGenerator light;
	//private final SimplexOctaveGenerator medium;
	//private final SimplexOctaveGenerator heavy;

	public GrassTextureGenerator(long seed) {
		super(seed);
	}

	@Override
	public byte getTone(double x, double y) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected byte[] generate(int x, int y, int width, int height, int step) {
		//byte[] b = new byte[width * height * step * step];

		throw new UnsupportedOperationException("Not supported yet.");

		//return b;
	}
}
