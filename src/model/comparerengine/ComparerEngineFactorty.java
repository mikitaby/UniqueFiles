package model.comparerengine;

import model.comparerengine.hash.HashFileComparor;

public class ComparerEngineFactorty {

	public static ComparerEngine getComparerEngine(ComparerEngineType comparerEngineType) {
		switch (comparerEngineType) {
		case Default:
			return new HashFileComparor();
		default:
			throw new RuntimeException(String.format("Can't load comparor engine: bad engine type {0}", comparerEngineType));
		}
	}
}
