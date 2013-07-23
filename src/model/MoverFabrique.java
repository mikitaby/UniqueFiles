package model;


public class MoverFabrique {

	public static Mover getMover(String moverType, final String basePath) {
		if ("Default".equals(moverType))
			return new DefaultMover(basePath);
		return null;
	}
}
