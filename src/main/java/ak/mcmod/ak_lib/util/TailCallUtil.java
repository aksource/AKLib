package ak.mcmod.ak_lib.util;

/**
 * Created by A.K. on 2018/11/01.
 */
public class TailCallUtil {
  public static <T> TailCall<T> nextCall(TailCall<T> function) {
    return function;
  }

  public static <T> TailCall<T> complete(T value) {
    return new TailCall<T>() {
      @Override
      public TailCall<T> apply() {
        throw new RuntimeException("error");
      }

      @Override
      public boolean isComplete() {
        return true;
      }

      @Override
      public T getResult() {
        return value;
      }
    };
  }
}
