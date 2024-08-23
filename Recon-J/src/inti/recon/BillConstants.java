package inti.recon;
import android.annotation.SuppressLint;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class BillConstants {
    private static final String dosPesos = "Dos pesos.";
    private static final String cincoPesos = "Cinco pesos.";
    private static final String diezPesos = "Diez pesos.";
    private static final String veintePesos = "Veinte pesos.";
    private static final String cincuentaPesos = "Cincuenta pesos.";
    private static final String cienPesos = "Cien pesos.";
    private static final String doscientosPesos = "Doscientos pesos.";
    private static final String quinientosPesos = "Quinientos pesos.";
    private static final String milPesos = "Mil pesos.";
    final String notFound = "Intente nuevamente.";
    final Map<Integer,String> idTextMap;
    protected final ArrayList<Integer> ID_List;

    BillConstants() {
        ID_List = new ArrayList<Integer>();
        Class raw = R.raw.class;
        Field[] fields = raw.getFields();
        for (Field field : fields) {
            try {
                this.ID_List.add(field.getInt(null));
            } catch(IllegalAccessException e) {
                Log.e("REFLECTION", String.format("%s threw IllegalAccessException.",
                        field.getName()));
            }
        }
        this.idTextMap = generateIdTextMap();
    }

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer,String> generateIdTextMap(){
        HashMap<Integer,String> idTextMap = new HashMap<Integer, String>();
        idTextMap.put(R.raw.dosp, dosPesos);
        idTextMap.put(R.raw.dospd, dosPesos);
        idTextMap.put(R.raw.cincop, cincoPesos);
        idTextMap.put(R.raw.cincopd, cincoPesos);
        idTextMap.put(R.raw.diezp, diezPesos);
        idTextMap.put(R.raw.diezpd, diezPesos);
        idTextMap.put(R.raw.veintep, veintePesos);
        idTextMap.put(R.raw.veintepd, veintePesos);
        idTextMap.put(R.raw.cincuentap, cincuentaPesos);
        idTextMap.put(R.raw.cincuentapd, cincuentaPesos);
        idTextMap.put(R.raw.cincuentamalp, cincuentaPesos);
        idTextMap.put(R.raw.cincuentamalpd, cincuentaPesos);
        idTextMap.put(R.raw.cienp, cienPesos);
        idTextMap.put(R.raw.cienpd, cienPesos);
        idTextMap.put(R.raw.cienevp, cienPesos);
        idTextMap.put(R.raw.cienevpd, cienPesos);
        idTextMap.put(R.raw.quinientosp, quinientosPesos);
        idTextMap.put(R.raw.quinientospd, quinientosPesos);
        idTextMap.put(R.raw.cinconp, cincoPesos);
        idTextMap.put(R.raw.cinconpd, cincoPesos);
        idTextMap.put(R.raw.dieznp, diezPesos);
        idTextMap.put(R.raw.dieznpd, diezPesos);
        idTextMap.put(R.raw.doscientosp, doscientosPesos);
        idTextMap.put(R.raw.doscientospd, doscientosPesos);
        idTextMap.put(R.raw.milp, milPesos);
        idTextMap.put(R.raw.milpd, milPesos);

        return idTextMap;
    }
}
