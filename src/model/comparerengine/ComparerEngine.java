package model.comparerengine;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface ComparerEngine {

	public Collection<List<File>> getSameFiles(final String path);

}
