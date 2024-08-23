package inti.recon.backend;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static inti.recon.backend.BillSearchConstants.SEARCH_TIMEOUT;
import static inti.recon.backend.BillSearchConstants.MAX_ACTIVE_THREADS;

public class SimpleBillSearch implements BillSearch {
    private final AtomicBoolean billeteEncontrado;
    private final AtomicReference<String> reultado;
    private final ThreadPoolExecutor threadPoolExecutor;

    public SimpleBillSearch() {
        billeteEncontrado = new AtomicBoolean(false);
        reultado = new AtomicReference<String>("");
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_ACTIVE_THREADS);
    }

    @Override
    public String search(Billete needle, List<Billete> haystack) {
        threadPoolExecutor.execute(new ReconocedorDorso(needle, haystack, this.billeteEncontrado, this.reultado));
        threadPoolExecutor.execute(new ReconocedorFrente(needle, haystack, this.billeteEncontrado, this.reultado));
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(SEARCH_TIMEOUT, TimeUnit.SECONDS);
            threadPoolExecutor.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reultado.get();
    }
}
