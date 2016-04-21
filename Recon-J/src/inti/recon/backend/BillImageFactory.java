package inti.recon.backend;

import java.io.File;

public interface BillImageFactory {

	BillImage createFromImageFile(File file);

}
