package gob.inti.reconocedordebilletes;

public enum EDenominacionBilletes {
	
	dosp(0),
	dospd (1),
	cincop(2),
	cincopd(3),
	diezp(4),
	diezpd(5),
	veintep(6),
	veintepd(7),
	cincuentap(8),
	cincuentapd(9),
	cienp(10),
	cienpd(11),
	cienevp(12),
	cienevpd(13);
	sindenominacion(14);
	
	private int value;

    private EDenominacionBilletes(int value) {
            this.value = value;
    }
    
    public int value()
    {
    	return value;
    }
};

