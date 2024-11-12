package my.example.processor;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import my.example.model.NaturalFactorization;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.concurrent.ThreadPoolExecutor;

@ApplicationScoped
public class FactorizationProcessor {

    @Channel("factorization")
    Emitter<NaturalFactorization> factorizationEmitter;

    @Incoming("natural")
    @Blocking
    public void process(Long number) {
        Thread processThread = new Thread(() -> {
            factorizationEmitter.send(new NaturalFactorization(number));
        });
        processThread.start();
    }
}
