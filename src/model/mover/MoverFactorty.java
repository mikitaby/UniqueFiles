package model.mover;

public class MoverFactorty {
	public static Mover getMover(MoverType moverType, final String basePath) {
		switch (moverType) {
		case Default:
			return new DefaultMover(basePath);
		default:
			throw new RuntimeException(String.format("Can't load mover: bad mover type {0}", moverType));
		}
	}
}
