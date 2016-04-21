package inti.recon;

import inti.recon.backend.BillImage;
import inti.recon.backend.ImageTestFactory;

public class AndroidImageTestFactory implements ImageTestFactory {

	@Override
	public AndroidImageTest newTest(BillImage needle, BillImage candidateImage) {
		return new AndroidImageTest(needle, candidateImage);
	}
}
