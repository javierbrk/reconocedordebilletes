package inti.recon.backend;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileBillDAO implements BillDAO {	
	private String path;
	private BillFactory billFactory;

	public FileBillDAO(String path) {
		this.path = path;
	}

	@Override
	public List<Bill> loadAll() {
		List<Bill> list = new LinkedList<Bill>();
		File directory = new File(path);
		File[] dirContents = directory.listFiles();

		for (File file : dirContents) {
		    if (file.isDirectory()) {
		    	list.add(billFactory.createFromFile(file));
		    }
		}

		return list;
	}

}
