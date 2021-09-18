package ak.mcmod.ak_lib.block;

import com.google.common.collect.Sets;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.BlockPos;

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
  private BlockUtils(){}

  public static int distManhattan(BlockPos blockPos1, BlockPos blockPos2) {
    int x = blockPos1.getX() - blockPos2.getX();
    int y = blockPos1.getY() - blockPos2.getY();
    int z = blockPos1.getZ() - blockPos2.getZ();
    return x + y + z;
  }

  public static int compareTo(BlockPos origin, BlockPos blockPos1, BlockPos blockPos2) {
    return compareTo(blockPos1.subtract(origin), blockPos2.subtract(origin));
  }
  public static int compareTo(BlockPos blockPos1, BlockPos blockPos2) {
    int manhattanDistance1 = distManhattan(BlockPos.ORIGIN, blockPos1);
    int manhattanDistance2 = distManhattan(BlockPos.ORIGIN, blockPos2);
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
    for (int x = manhattanDistance * -1; x <= manhattanDistance; x++) {
      for (int y = manhattanDistance * -1; y <= manhattanDistance; y++) {
        for (int z = manhattanDistance * -1; z <= manhattanDistance; z++) {
          if (Math.abs(x) + Math.abs(y) + Math.abs(z) <= manhattanDistance) {
            set.add(origin.add(x, y, z));
          }
        }
      }
    }
    return set;
  }
}
