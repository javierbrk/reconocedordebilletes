package inti.recon.backend;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ImageDirectoryBillFactory implements BillFactory {
	private BillImageFactory billImageFactory;

	@Override
	public Bill createFromFile(File directory) {
		// usar algœn patr—n en el nombre del directorio para identificar el valor/id ?
		// Por ejemplo <valor>/<motivo>_<lado>: 100/Roca_frente.png

		Bill bill = new Bill();
		bill.setId(directory.getName());
		bill.setImages(buildImageList(directory));

		return bill;
	}

	protected List<BillImage> buildImageList(File directory) {
		List<BillImage> images = new LinkedList<BillImage>();
		File[] filesList = directory.listFiles();
		for (File file : filesList) {
		    if (file.isFile()) {
		    	// create image from file and add it to the bill image list
		    	BillImage image = billImageFactory.createFromImageFile(file);
		    	images.add(image);
		    }
		}
		return images;
	}

}
