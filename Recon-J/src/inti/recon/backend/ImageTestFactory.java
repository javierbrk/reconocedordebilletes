package inti.recon.backend;

public interface ImageTestFactory {
	ImageTest newTest(BillImage needle, BillImage candidateImage);
}
