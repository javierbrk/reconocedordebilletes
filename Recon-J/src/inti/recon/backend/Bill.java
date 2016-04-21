package inti.recon.backend;

import java.util.List;

public class Bill {
	String id;
	List<BillImage> images;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<BillImage> getImages() {
		return images;
	}
	public void setImages(List<BillImage> images) {
		this.images = images;
	}
}
