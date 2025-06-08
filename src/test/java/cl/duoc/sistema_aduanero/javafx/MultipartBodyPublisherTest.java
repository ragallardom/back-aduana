package cl.duoc.sistema_aduanero.javafx;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class MultipartBodyPublisherTest {

  @Test
  void onSubscribeCalledOnlyOnce() {
    MultipartBodyPublisher.MultipartBuilder builder =
        MultipartBodyPublisher.builder();
    builder.addPart("field", "value");
    MultipartBodyPublisher publisher = builder.build();

    AtomicInteger count = new AtomicInteger();

    Flow.Subscriber<ByteBuffer> subscriber = new Flow.Subscriber<>() {
      @Override
      public void onSubscribe(Flow.Subscription subscription) {
        count.incrementAndGet();
        // request all items
        subscription.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(ByteBuffer item) {
        // no-op
      }

      @Override
      public void onError(Throwable throwable) {
        // no-op
      }

      @Override
      public void onComplete() {
        // no-op
      }
    };

    publisher.subscribe(subscriber);

    assertEquals(1, count.get(), "onSubscribe should be called exactly once");
  }
}
