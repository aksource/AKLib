package ak.mcmod.ak_lib.util;

import java.util.stream.Stream;

/**
 * Created by A.K. on 2018/11/01.
 */
@FunctionalInterface
public interface TailCall<T> {
  TailCall<T> apply();

  default boolean isComplete() {
    return false;
  }

  default T getResult() {
    throw new RuntimeException("error");
  }

  default T call() {
    return Stream.iterate(this, TailCall::apply)
            .filter(TailCall::isComplete)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("error"))
            .getResult();
  }
}
