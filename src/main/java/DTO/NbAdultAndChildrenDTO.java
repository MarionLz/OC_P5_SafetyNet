package DTO;

import lombok.Data;

@Data
public class NbAdultAndChildrenDTO {
	
	private int nbAdult;
	private int nbChildren;
	
	public NbAdultAndChildrenDTO(int nbAdult, int nbChildren) {
		this.nbAdult = nbAdult;
		this.nbChildren = nbChildren;
	}
}
