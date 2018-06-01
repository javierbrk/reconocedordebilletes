package inti.recon.backend;

import java.io.File;

public interface BillFactory {

	Bill createFromFile(File directory);

}
