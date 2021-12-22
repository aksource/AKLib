package ak.mcmod.ak_lib.block;

import com.google.common.collect.Sets;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Comparator;
import java.util.Set;

/**
 * Created by A.K. on 2021/09/14.
 */
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class BlockUtils {
  private BlockUtils() {}

  public static int compareTo(BlockPos origin, BlockPos blockPos1, BlockPos blockPos2) {
    return compareTo(blockPos1.subtract(origin), blockPos2.subtract(origin));
  }

  public static int compareTo(BlockPos blockPos1, BlockPos blockPos2) {
    var manhattanDistance1 = BlockPos.ZERO.distManhattan(blockPos1);
    var manhattanDistance2 = BlockPos.ZERO.distManhattan(blockPos2);
    if (manhattanDistance1 == manhattanDistance2) {
      return blockPos1.compareTo(blockPos2);
    } else {
      return manhattanDistance1 - manhattanDistance2;
    }
  }

  public static Comparator<BlockPos> createComparatorWithOriginManhattanDistance(BlockPos origin) {
    return (e1, e2) -> compareTo(origin, e1, e2);
  }

  public static Set<BlockPos> getBlockPosListWithinManhattan(BlockPos origin, int manhattanDistance) {
    Set<BlockPos> set = Sets.newHashSet();
    for (var x = manhattanDistance * -1; x <= manhattanDistance; x++) {
      for (var y = manhattanDistance * -1; y <= manhattanDistance; y++) {
        for (var z = manhattanDistance * -1; z <= manhattanDistance; z++) {
          if (Math.abs(x) + Math.abs(y) + Math.abs(z) <= manhattanDistance) {
            set.add(origin.offset(x, y, z));
          }
        }
      }
    }
    return set;
  }
}
